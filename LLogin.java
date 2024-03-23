package Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LLogin extends DashBoard {
    public LLogin(){
                JFrame loginFrame = new JFrame("Login Page");
                loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loginFrame.setSize(800, 600);
                
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - loginFrame.getWidth()) / 2;
                int y = (screenSize.height - loginFrame.getHeight()) / 2;
                loginFrame.setLocation(x, y);

                JPanel loginPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

                JLabel usernameLabel = new JLabel("Username:");
                gbc.gridx = 0;
                gbc.gridy = 0;
                loginPanel.add(usernameLabel, gbc);

                JTextField usernameField = new JTextField(20);
                gbc.gridx = 1;
                loginPanel.add(usernameField, gbc);

                JLabel passwordLabel = new JLabel("Password:");
                gbc.gridx = 0;
                gbc.gridy = 1;
                loginPanel.add(passwordLabel, gbc);

                JPasswordField passwordField = new JPasswordField(20);
                gbc.gridx = 1;
                loginPanel.add(passwordField, gbc);

                JButton loginButton = new JButton("Login");
                gbc.gridx = 1;
                gbc.gridy = 2;
                loginPanel.add(loginButton, gbc);

                loginButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        String passwordlogin = new String(passwordField.getPassword());
                        String usernamelogin = usernameField.getText();

                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from users where username = ? AND password = ?");
                            preparedStatement.setString(1, usernamelogin);
                            preparedStatement.setString(2, passwordlogin);
                            ResultSet resultSet = preparedStatement.executeQuery();

                            if (resultSet.next()) {
                                name = resultSet.getString("username");
                                username = usernamelogin;
                                balance = resultSet.getDouble("balance");
                                OpenWindow o=new OpenWindow();
                            } else {
                                loginAttempts++;

                                if (loginAttempts >= 3) {
                                    JOptionPane.showMessageDialog(loginPanel, "Authentication failed. You've exceeded the maximum login attempts.\nPlease register to continue.");
                                    JButton j = new JButton("Forgot Password?");
                                    gbc.gridx = 0;
                                    gbc.gridy = 3;

                                    loginPanel.add(j, gbc);
                                    loginFrame.revalidate();
                                    loginFrame.repaint();

                                    JButton r2 = new JButton("Register");
                                    gbc.gridx = 2;
                                    gbc.gridy = 3;

                                    loginPanel.add(r2, gbc);
                                    loginFrame.revalidate();
                                    loginFrame.repaint();
                                    
                                    r2.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent ae) {
                                        new Register();
                                       
                                    }
                                       
                                });

                                    j.addActionListener(new ActionListener() {
                                        public static boolean usernameExists;
                                        public static ResultSet resultSet;
                                        public void actionPerformed(ActionEvent ae) {
                                        String usernamelogin = usernameField.getText();
                                           

                                    try {
                                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
                                        preparedStatement.setString(1, usernamelogin);
                                        ResultSet resultSet = preparedStatement.executeQuery();
         
                                        if (resultSet.next()) { 
                                                JFrame resetPasswordFrame = new JFrame("Reset Password");
                                                resetPasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                                resetPasswordFrame.setSize(400, 200);

                                                JPanel resetPasswordPanel = new JPanel(new GridBagLayout());
                                                GridBagConstraints gbc = new GridBagConstraints();
                                                gbc.insets = new Insets(5, 5, 5, 5);

                                                JLabel newPasswordLabel = new JLabel("New Password:");
                                                gbc.gridx = 0;
                                                gbc.gridy = 0;
                                                resetPasswordPanel.add(newPasswordLabel, gbc);

                                                JPasswordField newPasswordField = new JPasswordField(20);
                                                gbc.gridx = 1;
                                                resetPasswordPanel.add(newPasswordField, gbc);

                                                JLabel reenterPasswordLabel = new JLabel("Re-enter New Password:");
                                                gbc.gridx = 0;
                                                gbc.gridy = 1;
                                                resetPasswordPanel.add(reenterPasswordLabel, gbc);

                                                JPasswordField reenterPasswordField = new JPasswordField(20);
                                                gbc.gridx = 1;
                                                resetPasswordPanel.add(reenterPasswordField, gbc);

                                                JButton resetPasswordButton = new JButton("Reset Password");
                                                gbc.gridx = 1;
                                                gbc.gridy = 2;
                                                resetPasswordPanel.add(resetPasswordButton, gbc);

                                                resetPasswordButton.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent ae) {
                                                        String newPassword = new String(newPasswordField.getPassword());
                                                        String reenteredPassword = new String(reenterPasswordField.getPassword());

                                                        if (newPassword.equals(reenteredPassword)) {
                                                            // Update the database with the new password for the current user (username)
                                                            if (newPassword.length() < 8) {
                                                                JOptionPane.showMessageDialog(resetPasswordPanel, "New password should have a minimum length of 8.");
                                                            } else {
                                                                try {
                                                                    
                                                                    PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE users SET password=? WHERE username=?");
                                                                    preparedStatement2.setString(1, newPassword);
                                                                    preparedStatement2.setString(2, usernamelogin);
                                                                    preparedStatement2.executeUpdate();

                                                                    JOptionPane.showMessageDialog(resetPasswordPanel, "Password reset successful.");
                                                                    resetPasswordFrame.dispose(); // Close the password reset window
                                                                    connection.close();
                                                                } catch (SQLException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(resetPasswordPanel, "Passwords do not match. Please re-enter the same password.");
                                                        }
                                                    }
                                                });

                                                resetPasswordFrame.add(resetPasswordPanel);
                                                resetPasswordFrame.setVisible(true);
                                            } else {
                                                JOptionPane.showMessageDialog(loginPanel, "Username not found in the database. Please check the username.");
                                            }
                                            }
                                            catch(SQLException e){}
                                        }
                                    });
                                } else {
                                    JOptionPane.showMessageDialog(loginPanel, "Authentication failed. Invalid username or password.");
                                }
                            }

                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

                loginFrame.add(loginPanel);
                loginFrame.setVisible(true);
            }

}
