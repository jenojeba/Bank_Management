package Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class JJBankTransfer extends DashBoard{
    public JJBankTransfer() {
        JFrame transferFrame = new JFrame("JJ Bank Transfer");
        transferFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel transferPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblRecipientUsername = new JLabel("Recipient Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        transferPanel.add(lblRecipientUsername, gbc);

        JTextField txtRecipientUsername = new JTextField(20);
        gbc.gridx = 1;
        transferPanel.add(txtRecipientUsername, gbc);

        JLabel lblAmount = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        transferPanel.add(lblAmount, gbc);

        JTextField txtAmount = new JTextField(20);
        gbc.gridx = 1;
        transferPanel.add(txtAmount, gbc);

        JLabel lblReason = new JLabel("Reason:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        transferPanel.add(lblReason, gbc);

        JComboBox<String> reasonComboBox = new JComboBox<>();
        reasonComboBox.addItem("Payment");
        reasonComboBox.addItem("Transfer");
        reasonComboBox.addItem("Education purpose");
        reasonComboBox.addItem("Loan");
        reasonComboBox.addItem("Other");
        gbc.gridx = 1;
        transferPanel.add(reasonComboBox, gbc);

        JButton transferButton = new JButton("Transfer");
        gbc.gridx = 1;
        gbc.gridy = 3;
        transferPanel.add(transferButton, gbc);

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String senderUsername = username; // Assuming sender is the currently logged-in user
                String recipientUsername = txtRecipientUsername.getText();
                String transferAmountStr = txtAmount.getText();

                if (isRecipientUsernameValid(recipientUsername) && isAmountValid(transferAmountStr)) {
                    double transferAmount = Double.parseDouble(transferAmountStr);

                    if (performTransfer(senderUsername, recipientUsername, transferAmount)) {
                        JOptionPane.showMessageDialog(transferFrame, "Transfer successful.");
                        transferFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(transferFrame, "Transfer failed. Please check your balance.");
                    }
                } else {
                    if (!isRecipientUsernameValid(recipientUsername)) {
                        JOptionPane.showMessageDialog(transferFrame, "Recipient username not found in the database. Please enter a valid username.");
                    } else {
                        JOptionPane.showMessageDialog(transferFrame, "Invalid amount. Please enter a valid numeric amount.");
                    }
                }
            }
        });

        transferFrame.add(transferPanel);
        transferFrame.setSize(400, 250);
        transferFrame.setLocationRelativeTo(null);
        transferFrame.setVisible(true);
    }

    private boolean isRecipientUsernameValid(String recipientUsername) {
        boolean isValid = false;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
            PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            checkStatement.setString(1, recipientUsername);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                isValid = true;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }

    private boolean isAmountValid(String amountStr) {
        try {
            double transferAmount = Double.parseDouble(amountStr);
            return transferAmount >= 0; // Ensure that the amount is a non-negative number
        } catch (NumberFormatException e) {
            return false; // Invalid input, not a valid numeric amount
        }
    }

    private boolean performTransfer(String senderUsername, String recipientUsername, double amount) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

            // Check sender's balance
            PreparedStatement senderBalanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
            senderBalanceStatement.setString(1, senderUsername);
            ResultSet senderBalanceResult = senderBalanceStatement.executeQuery();
            senderBalanceResult.next();
            double senderBalance = senderBalanceResult.getDouble("balance");

            if (senderBalance >= amount) {
                // Deduct the amount from sender's account
                senderBalance -= amount;

                // Update sender's balance
                PreparedStatement updateSenderBalanceStatement = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
                updateSenderBalanceStatement.setDouble(1, senderBalance);
                updateSenderBalanceStatement.setString(2, senderUsername);
                updateSenderBalanceStatement.executeUpdate();

                // Add the amount to recipient's account
                PreparedStatement recipientBalanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
                recipientBalanceStatement.setString(1, recipientUsername);
                ResultSet recipientBalanceResult = recipientBalanceStatement.executeQuery();
                recipientBalanceResult.next();
                double recipientBalance = recipientBalanceResult.getDouble("balance");
                recipientBalance += amount;

                // Update recipient's balance
                PreparedStatement updateRecipientBalanceStatement = connection.prepareStatement("UPDATE users SET balance = ? WHERE username = ?");
                updateRecipientBalanceStatement.setDouble(1, recipientBalance);
                updateRecipientBalanceStatement.setString(2, recipientUsername);
                updateRecipientBalanceStatement.executeUpdate();

                connection.close();
                return true; // Transfer successful
            } else {
                connection.close();
                return false; // Insufficient balance
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Transfer failed due to an error
        }
    }
}
