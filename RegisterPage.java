import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.HashMap;

public class RegisterPage implements ActionListener {

   public static void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public static void writeToFile(String username, String password) {
      try {
         FileWriter writer = new FileWriter("data/logininfo.txt", true);
         writer.write(username + ":" + password + "\n");
         writer.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   HashMap<String, String> logininfo = new HashMap<String, String>();

   Color color = new Color(0xf9f7f0);
   JFrame frame = new JFrame("Register");

   JLabel nameLabel = new JLabel("Name: ");
   JLabel usernameLabel = new JLabel("Username: ");
   JLabel passwordLabel = new JLabel("Password: ");
   JButton registerButton = new JButton("Register");
   JLabel messageLabel = new JLabel();

   JTextField nameField = new JTextField();
   JTextField usernameField = new JTextField();
   JPasswordField passwordField = new JPasswordField();

   public RegisterPage(HashMap<String, String> loginInfoOriginal) {
      logininfo = loginInfoOriginal;

      messageLabel.setBounds(75, 255, 1000, 100);
      messageLabel.setFont(new Font(null, Font.BOLD, 15));

      nameLabel.setBounds(50, 100, 75, 25);
      nameField.setBounds(125, 100, 200, 25);

      usernameLabel.setBounds(50, 150, 75, 25);
      usernameField.setBounds(125, 150, 200, 25);

      passwordLabel.setBounds(50, 200, 75, 25);
      passwordField.setBounds(125, 200, 200, 25);

      registerButton.setBounds(155, 250, 120, 35);
      registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
      registerButton.setFocusable(false);
      registerButton.addActionListener(this);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(420, 420);
      frame.setLayout(null);
      frame.setResizable(false);
      frame.getContentPane().setBackground(color);

      frame.add(nameLabel);
      frame.add(usernameLabel);
      frame.add(passwordLabel);
      frame.add(nameField);
      frame.add(usernameField);
      frame.add(passwordField);
      frame.add(registerButton);
      frame.add(messageLabel);

      frame.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String name = nameField.getText();
      String username = usernameField.getText();
      String password = String.valueOf(passwordField.getPassword());

      if (e.getSource() == registerButton) {
         if (name.equals("") || name.charAt(0) == ' ') {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("Name cannot be empty");
            return;
         }

         else if (username.equals("")) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID cannot be empty");
            return;
         }

         else if (username.contains(" ")) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID cannot contain spaces");
            return;
         }

         else if (username.length() > 10) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID cannot be longer than 10 characters");
            return;
         }

         else if (password.equals("")) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("Password cannot be empty");
            return;
         }

         else if (password.contains(" ")) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("Password cannot contain spaces");
            return;
         }

         else if (logininfo.containsKey(username)) {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID already exists");
            return;
         }

         else {
            if (logininfo.containsKey(username)) {
               messageLabel.setForeground(Color.red);
               messageLabel.setText("UserID already exists");
               return;
            }

            logininfo.put(username, password);
            writeToFile(username, password);
            frame.dispose();
            LoginPage loginPage = new LoginPage(logininfo);
         }
      }
   }
}
