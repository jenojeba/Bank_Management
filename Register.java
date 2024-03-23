package Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Register extends DashBoard {
    public Register(){
                JFrame jFrame = new JFrame("Register Page");
                jFrame.getContentPane().setBackground(Color.BLUE);
                
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (screenSize.width - jFrame.getWidth()) / 2;
                int y = (screenSize.height - jFrame.getHeight()) / 2;
                jFrame.setLocation(x, y);


                JPanel registerForm = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

                JLabel l = new JLabel("Name :");
                gbc.gridx = 0;
                gbc.gridy = 0;
                registerForm.add(l, gbc);

                JTextField t = new JTextField(20);
                gbc.gridx = 1;
                registerForm.add(t, gbc);

                JLabel l1 = new JLabel("Username :");
                gbc.gridx = 0;
                gbc.gridy = 1;
                registerForm.add(l1, gbc);

                JTextField t1 = new JTextField(20);
                gbc.gridx = 1;
                registerForm.add(t1, gbc);

                JLabel l2 = new JLabel("Password:");
                gbc.gridx = 0;
                gbc.gridy = 2;
                registerForm.add(l2, gbc);

                JPasswordField t2 = new JPasswordField(20);
                gbc.gridx = 1;
                registerForm.add(t2, gbc);

                JButton addButton = new JButton("Submit");
                gbc.gridx = 1;
                gbc.gridy = 3;
                registerForm.add(addButton, gbc);

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        String enteredName = t.getText();
                        String enteredUsername = t1.getText();
                        String enteredPassword = new String(t2.getPassword());

                        if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredName.isEmpty()) {
                            JOptionPane.showMessageDialog(registerForm, "Username, password, or name should not be empty");
                        } else if (enteredPassword.length() < 8) {
                            JOptionPane.showMessageDialog(registerForm, "Password should have a minimum length of 8");
                        } else {
                            try {
                                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                                PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
                        checkStatement.setString(1, enteredUsername);
                        ResultSet resultSet = checkStatement.executeQuery();
                        resultSet.next();
                        int count = resultSet.getInt(1);
                        if (count > 0) {
                            JOptionPane.showMessageDialog(registerForm, "Username already exists. Please choose a different username.");
                        } else {
                                //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
                                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, balance) VALUES (?, ?, ?)");
                                preparedStatement.setString(1, enteredUsername);
                                preparedStatement.setString(2, enteredPassword);
                                preparedStatement.setDouble(3, 0.0);
                                preparedStatement.executeUpdate();

                                //name = enteredName;
                                username = enteredUsername;
                                OpenWindow o=new OpenWindow();
                                connection.close();}
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                jFrame.add(registerForm);
                jFrame.setSize(800, 600);
                jFrame.setLocationRelativeTo(null);
                jFrame.setVisible(true);
            }
   
}