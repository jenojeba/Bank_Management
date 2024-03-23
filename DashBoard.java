package Bank;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DashBoard {
    public static double balance;
    public static double senderBalance;
    public static String username;
    public static String name;
    public static int loginAttempts = 0;
    JFrame Frame; 
    public static String usernamelogin;
    public JPanel buttonPanel= new JPanel();
    public JFrame f= new JFrame("JJ BANK");
    public JPanel panel= new JPanel(new BorderLayout()); 
    
    void Dash(){
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.getContentPane().setBackground(Color.WHITE);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screenSize.width - f.getWidth()) / 2;
    int y = (screenSize.height - f.getHeight()) / 2;
    f.setLocation(x, y);
    
    ImageIcon bankIcon = new ImageIcon("C:/Users/USER/Downloads/bank.jpg");
    JLabel bankImageLabel = new JLabel();
     Image originalImage = bankIcon.getImage();
     int width = 300;
        int height = 300;
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
     bankImageLabel.setIcon(new ImageIcon(scaledImage));

    
    panel.add(bankImageLabel, BorderLayout.WEST);

        JLabel l = new JLabel("<html>"
                + "<div style='text-align: justify;'>"
                + "<div style='font-size: 14px;'>&nbsp;&nbsp;Banks play a crucial role in the global economy and offer a wide range of services that benefit individuals, businesses, and society as a &nbsp;&nbsp;whole.</div>"
                + "<div style='font-size: 14px;'>&nbsp;&nbsp;Banks provide a safe and secure place for individuals to store their money, ensuring it is protected from theft and loss.</div>"
                + "<div style='font-size: 14px;'>&nbsp;&nbsp;Banks offer a variety of savings and investment products, such as savings accounts, certificates of deposit, and mutual funds, which &nbsp;&nbsp;help individuals grow their wealth over time.</div>"
                + "<div style='font-size: 14px;'>&nbsp;&nbsp;Now all the money transfer and checking money balance can be done at home comfortably.</div>"
                + "<div style='font-size: 18px; font-weight: bold; text-align: center;'>Welcome to JJ BANK</div>"
                + "</div>"
                + "</html>");
        l.setHorizontalAlignment(JLabel.CENTER);
        panel.add(l, BorderLayout.CENTER);
        //JPanel buttonPanel = new JPanel();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new LLogin(); // Open the login window
            }
        });
        buttonPanel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new Register(); // Open the registration window
            }
        });
        buttonPanel.add(registerButton);
        f.add(panel, BorderLayout.CENTER);
        f.add(buttonPanel, BorderLayout.SOUTH);
        f.setVisible(true);
    }}
    
class Display{
public static void main(String[] args){
    DashBoard d=new DashBoard();
    d.Dash();
    
}}
