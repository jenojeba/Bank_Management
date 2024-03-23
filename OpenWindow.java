
package Bank;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class OpenWindow extends DashBoard{
     
    public OpenWindow(){
        JFrame Frame = new JFrame("BANK");
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel b = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton b1 = new JButton("1. Balance");
        b.add(b1);
        JButton b2 = new JButton("2. Deposit");
        b.add(b2);
        JButton b3 = new JButton("3. Transfer");
        b.add(b3);
        JButton b4 = new JButton("4. Recharge");
        b.add(b4);
        JButton b5 = new JButton("5. Exit");
        b.add(b5);
        Frame.add(b);

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Deposit d=new Deposit();
            }
        });

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");

            // Check sender's balance
            PreparedStatement senderBalanceStatement = connection.prepareStatement("SELECT balance FROM users WHERE username = ?");
            senderBalanceStatement.setString(1, username);
            ResultSet senderBalanceResult = senderBalanceStatement.executeQuery();
            senderBalanceResult.next();
            double senderBalance = senderBalanceResult.getDouble("balance");
                JOptionPane.showMessageDialog(Frame, "Balance Amount: Rs" + senderBalance);
                }
            catch(SQLException e){}
                
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                
                String[] options = { "JJ Bank", "Other Banks" };
                int selectedOption = JOptionPane.showOptionDialog(Frame, "Select a Bank to Transfer to:", "Bank Selection",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                if (selectedOption == 0) {
                    // If JJ Bank is selected, open a new window for JJ Bank transfer
                    JJBankTransfer jjBankTransfer = new JJBankTransfer();
                } else if (selectedOption == 1) {
                    OtherBankTransferDialog otherBankTransferDialog = new OtherBankTransferDialog();
                    
                }
            }

        });

        
        b4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            Recharge re=new Recharge();
        }});
        
            b5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JOptionPane.showMessageDialog(Frame, "Vist us Again.Thank You");
                    System.exit(0);
                }
            });
 
            Frame.setSize(800, 600);
            Frame.setLocationRelativeTo(null); 

            Frame.setVisible(true);
        }  
    }



