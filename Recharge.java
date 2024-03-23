/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Recharge extends DashBoard {
    public Recharge() {
        JFrame rechargeFrame = new JFrame("Recharge");
        rechargeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rechargeFrame.setSize(500, 500);

        JPanel rechargePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel phoneNumberLabel = new JLabel("Phone number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        rechargePanel.add(phoneNumberLabel, gbc);

        JTextField phoneNumberField = new JTextField(15); // Allowing for a 10-digit phone number
        gbc.gridx = 2;
        rechargePanel.add(phoneNumberField, gbc);

        JButton rechargeButton = new JButton("Recharge");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        rechargePanel.add(rechargeButton, gbc);

        JButton tp = new JButton("Top up");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        rechargePanel.add(tp, gbc);

        rechargeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String phoneNumber = phoneNumberField.getText();

                if (phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(rechargeFrame, "Enter a phone number.");
                } else if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
                    JOptionPane.showMessageDialog(rechargeFrame, "Invalid phone number.");
                } else {
                    JFrame plansFrame = new JFrame("Recharge Plans");
                    plansFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    plansFrame.setSize(400, 200);

                    JPanel plansPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);

                    JLabel selectPlanLabel = new JLabel("Select Recharge Plan:");
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    plansPanel.add(selectPlanLabel, gbc);

                    // Create a combo box with recharge plans and their prices
                    String[] plans = {"Rs. 279 - For 28 days (1.5 GB/day)", "Rs. 359 - For 28 days (2 GB/day)", "Rs. 459 - For 40 days (1.5 GB/day)"};
                    JComboBox<String> planComboBox = new JComboBox<>(plans);
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    plansPanel.add(planComboBox, gbc);

                    JButton confirmButton = new JButton("Confirm Recharge");
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 2;
                    plansPanel.add(confirmButton, gbc);

                    confirmButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            // Get the selected recharge plan
                            String selectedPlan = (String) planComboBox.getSelectedItem();
                            // Extract the recharge amount from the selected plan
                            double rechargeAmount = extractRechargeAmount(selectedPlan);

                            // Check if the user has enough balance for the recharge
                            if (balance < rechargeAmount) {
                                JOptionPane.showMessageDialog(plansFrame, "Insufficient balance. Your balance is Rs " + balance);
                            } else {
                                // Deduct the recharge amount from the balance
                                balance -= rechargeAmount;

                                // Record the recharge transaction in the database
                                try {
                                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                                    PreparedStatement retrieveStatement = connection.prepareStatement("SELECT balance FROM users WHERE username=?");
                                    retrieveStatement.setString(1, username);
                                    ResultSet resultSet = retrieveStatement.executeQuery();
                                    if (resultSet.next()) {
                                        double currentBalance = resultSet.getDouble("balance");
                                        currentBalance -= rechargeAmount;
                                        // Update the balance in the database
                                        PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET balance=? WHERE username=?");
                                        updateStatement.setDouble(1, currentBalance);
                                        updateStatement.setString(2, username);
                                        updateStatement.executeUpdate();
                                        connection.close();
                                    }
                                    // Perform any additional actions related to the recharge here

                                    // Display a success message
                                    JOptionPane.showMessageDialog(plansFrame, "Recharge of Rs " + rechargeAmount + " was successful.");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Close the plansFrame
                            plansFrame.dispose();
                        }
                    });
                    plansFrame.add(plansPanel);
                    plansFrame.setLocationRelativeTo(null);
                    plansFrame.setVisible(true);

                    rechargeFrame.dispose(); // Close the recharge window
                }
            }
        });

        tp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String phoneNumber = phoneNumberField.getText();

                if (phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(rechargeFrame, "Enter phone number.");
                } else if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d+")) {
                    JOptionPane.showMessageDialog(rechargeFrame, "Invalid phone number.");
                } else {
                    JFrame tplans = new JFrame("TopUp Plans");
                    tplans.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    tplans.setSize(400, 200);

                    JPanel plansPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(5, 5, 5, 5);

                    JLabel selectPlanLabel = new JLabel("Select Recharge TopUp Plan:");
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    plansPanel.add(selectPlanLabel, gbc);

                    // Create a combo box with recharge plans and their prices
                    String[] plans = {"Rs. 19 (2 GB/day)", "Rs. 25 - (3 GB/day)", "Rs. 55 - (6 GB/day)"};
                    JComboBox<String> planComboBox = new JComboBox<>(plans);
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    plansPanel.add(planComboBox, gbc);

                    JButton confirmButton = new JButton("Confirm TopUp");
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 2;
                    plansPanel.add(confirmButton, gbc);

                    confirmButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            // Get the selected recharge plan
                            String selectedPlan = (String) planComboBox.getSelectedItem();
                            // Extract the recharge amount from the selected plan
                            double rechargeAmount = extractRechargeAmount(selectedPlan);

                            // Check if the user has enough balance for the recharge
                            if (balance < rechargeAmount) {
                                JOptionPane.showMessageDialog(tplans, "Insufficient balance. Your balance is Rs " + balance);
                            } else {
                                // Deduct the recharge amount from the balance
                                balance -= rechargeAmount;

                                // Record the recharge transaction in the database
                                try {
                                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                                    PreparedStatement retrieveStatement = connection.prepareStatement("SELECT balance FROM users WHERE username=?");
                                    retrieveStatement.setString(1, username);
                                    ResultSet resultSet = retrieveStatement.executeQuery();
                                    if (resultSet.next()) {
                                        double currentBalance = resultSet.getDouble("balance");
                                        currentBalance -= rechargeAmount;
                                        // Update the balance in the database
                                        PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET balance=? WHERE username=?");
                                        updateStatement.setDouble(1, currentBalance);
                                        updateStatement.setString(2, username);
                                        updateStatement.executeUpdate();
                                        connection.close();
                                    }
                                    // Perform any additional actions related to the recharge here

                                    // Display a success message
                                    JOptionPane.showMessageDialog(tplans, "Recharge of Rs " + rechargeAmount + " was successful.");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Close the tplans frame
                            tplans.dispose();
                        }
                    });
                    tplans.add(plansPanel);
                    rechargeFrame.setLocationRelativeTo(null);
                    tplans.setLocationRelativeTo(null);
                    tplans.setVisible(true);
                    rechargeFrame.dispose(); // Close the recharge window
                }
            }
        });

        rechargeFrame.add(rechargePanel);
        rechargeFrame.setLocationRelativeTo(null);
        rechargeFrame.setVisible(true);
    }

    // Helper function to extract recharge amount from the selected plan
    private double extractRechargeAmount(String plan) {
        String[] parts = plan.split(" ");
        double rechargeAmount = Double.parseDouble(parts[1]);
        return rechargeAmount;
    }
}
