/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bank;

import static Bank.DashBoard.balance;
import static Bank.DashBoard.username;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
class Deposit extends DashBoard{
    public Deposit(){
    boolean valid = false;
                while (!valid) {
                    String depositAmountStr = JOptionPane.showInputDialog(Frame, "Enter the deposit amount:");

                    if (depositAmountStr == null) {
                        valid = true;
                        return;
                    }

                    try {
                        double depositAmount = Double.parseDouble(depositAmountStr);

                        if (depositAmount <= 0) {
                            JOptionPane.showMessageDialog(Frame, "Deposit amount must be greater than 0.");
                        } else if (balance < 500 && depositAmount < 500) {
                            JOptionPane.showMessageDialog(Frame, "The initial deposit must be at least 500.");
                        } else {
                            valid = true;
                            balance += depositAmount;
                            JOptionPane.showMessageDialog(Frame, "Deposit of Rs" + depositAmount + " was successful.");

                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                                PreparedStatement retrieveStatement = connection.prepareStatement("SELECT balance FROM users WHERE username=?");
                        retrieveStatement.setString(1, username);
                        ResultSet resultSet = retrieveStatement.executeQuery();
                        if (resultSet.next()) {
                            double currentBalance = resultSet.getDouble("balance");
                            currentBalance += depositAmount;
                            // Update the balance in the database
                            PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET balance=? WHERE username=?");
                            updateStatement.setDouble(1, currentBalance);
                            updateStatement.setString(2, username);
                            updateStatement.executeUpdate();
                            connection.close();
                            } }catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(Frame, "Invalid input. Please enter a valid amount.");
                    }
                }
                
                
    
}
}