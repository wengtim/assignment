import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage implements ActionListener {
   JFrame frame = new JFrame("Login / Register");
   JButton loginButton = new JButton("Login");
   JButton registerButton = new JButton("Register");
   JButton resetButton = new JButton("Reset");
   JTextField userIDField = new JTextField();
   JPasswordField userPasswordField = new JPasswordField();
   JLabel userIDLabel = new JLabel("User ID");
   JLabel userPasswordLabel = new JLabel("Password");
   JLabel messageLabel = new JLabel();

   HashMap<String, String> logininfo = new HashMap<String, String>();

   LoginPage(HashMap<String, String> loginInfoOriginal) {

      logininfo = loginInfoOriginal;

      userIDLabel.setBounds(50, 100, 75, 25);
      userPasswordLabel.setBounds(50, 150, 75, 25);

      messageLabel.setBounds(85, 250, 1000, 100);
      messageLabel.setFont(new Font(null, Font.ITALIC, 15));

      userIDField.setBounds(125, 100, 200, 25);
      userPasswordField.setBounds(125, 150, 200, 25);

      loginButton.setBounds(40, 200, 100, 30);
      loginButton.addActionListener(this);
      loginButton.setFocusable(false);
      registerButton.setBounds(165, 200, 100, 30);
      registerButton.addActionListener(this);
      registerButton.setFocusable(false);
      resetButton.setBounds(290, 200, 100, 30);
      resetButton.addActionListener(this);
      resetButton.setFocusable(false);

      frame.add(userIDLabel);
      frame.add(userPasswordLabel);
      frame.add(userIDField);
      frame.add(userPasswordField);
      frame.add(loginButton);
      frame.add(registerButton);
      frame.add(resetButton);
      frame.add(messageLabel);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(420, 420);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == resetButton) {
         userIDField.setText("");
         userPasswordField.setText("");
         messageLabel.setText("");
      }

      if (e.getSource() == registerButton) {
         if (userIDField.getText().equals("") || String.valueOf(userPasswordField.getPassword()).equals("")) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("Please enter UserID and Password");
            return;
         }

         if (logininfo.containsKey(userIDField.getText())) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID already exists. Please Login");
         } else {
            logininfo.put(userIDField.getText(), String.valueOf(userPasswordField.getPassword()));
            messageLabel.setForeground(Color.green);
            frame.dispose();
            RegisterPage registerPage = new RegisterPage(logininfo);
         }
      }

      if (e.getSource() == loginButton) {
         if (userIDField.getText().equals("") || String.valueOf(userPasswordField.getPassword()).equals("")) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("Please enter UserID and Password");
            return;
         }

         String userID = userIDField.getText();
         String password = String.valueOf(userPasswordField.getPassword());

         if (logininfo.containsKey(userID)) {
            if (logininfo.get(userID).equals(password)) {
               messageLabel.setForeground(Color.green);
               messageLabel.setText("Login Successful");
               frame.dispose();
               HomePage homepage = new HomePage(userID);
            } else {
               messageLabel.setForeground(Color.red);
               messageLabel.setText("Wrong Password");
            }
         } else {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID not found. Please Register");
         }
      }
   }
}
