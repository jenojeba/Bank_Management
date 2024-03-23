/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class OtherBankTransferDialog extends DashBoard{
    public OtherBankTransferDialog(){
    JTextField accountNumberField;
    JTextField bankNameField;
    JTextField amountField;
    JComboBox<String> reasonComboBox;
    JFrame jj=new JFrame("Other banks");
        //super(parentFrame, "Other Banks Transfer", true);

        JPanel transferPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblAccountNumber = new JLabel("Account Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        transferPanel.add(lblAccountNumber, gbc);

        accountNumberField = new JTextField(20);
        gbc.gridx = 1;
        transferPanel.add(accountNumberField, gbc);

        JLabel lblBankName = new JLabel("Bank Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        transferPanel.add(lblBankName, gbc);

        bankNameField = new JTextField(20);
        gbc.gridx = 1;
        transferPanel.add(bankNameField, gbc);

        JLabel lblAmount = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        transferPanel.add(lblAmount, gbc);

        amountField = new JTextField(20);
        gbc.gridx = 1;
        transferPanel.add(amountField, gbc);

        JLabel lblReason = new JLabel("Reason:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        transferPanel.add(lblReason, gbc);

        reasonComboBox = new JComboBox<>();
        reasonComboBox.addItem("Payment");
        reasonComboBox.addItem("Transfer");
        reasonComboBox.addItem("Education purpose");
        reasonComboBox.addItem("Loan");
        reasonComboBox.addItem("Other");
        gbc.gridx = 1;
        transferPanel.add(reasonComboBox, gbc);

        JButton transferButton = new JButton("Transfer");
        gbc.gridx = 1;
        gbc.gridy = 4;
        transferPanel.add(transferButton, gbc);

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String accountNumber = accountNumberField.getText();
                String bankName = bankNameField.getText();
                String transferAmountStr = amountField.getText();
                String transferReason = reasonComboBox.getSelectedItem().toString();

                if (isAmountValid(transferAmountStr)) {
                    double transferAmount = Double.parseDouble(transferAmountStr);
                if (performTransfer(accountNumber, transferAmount)) {
                        JOptionPane.showMessageDialog(jj, "Amount Successfully Transferred");
                        jj.dispose();
                    } else {
                        JOptionPane.showMessageDialog(jj, "Transfer failed. Please check the account information.");
                    }
                } else {
                    JOptionPane.showMessageDialog(jj, "Invalid amount. Please enter a valid numeric amount.");
                }
            }
        });
        
        jj.setVisible(true);
        jj.add(transferPanel);
        jj.setSize(400, 250);
        jj.setLocationRelativeTo(null);
        
    }

    private boolean isAmountValid(String amountStr) {
        try {
            double transferAmount = Double.parseDouble(amountStr);
            return transferAmount >= 0; // Ensure that the amount is a non-negative number
        } catch (NumberFormatException e) {
            return false; // Invalid input, not a valid numeric amount
        }
    }
    
    private boolean performTransfer(String accountNumber, double transferAmount) {
        String updateBalanceQuery = "UPDATE users SET balance = balance - ? WHERE username = ?";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

            PreparedStatement senderBalanceStatement = connection.prepareStatement(updateBalanceQuery);
            senderBalanceStatement.setDouble(1, transferAmount);
            senderBalanceStatement.setString(2, username);
            senderBalanceStatement.executeUpdate();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
