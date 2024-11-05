import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StudentLogin implements ActionListener {

   Color color = new Color(0xf9f7f0);

   JFrame frame = new JFrame("Student Login");
   JButton loginButton = new JButton("Login");
   JTextField userIDField = new JTextField();
   JPasswordField userPasswordField = new JPasswordField();
   JLabel registerLabel = new JLabel();
   JLabel infoLabel = new JLabel();
   JLabel userIDLabel = new JLabel("User ID");
   JLabel userPasswordLabel = new JLabel("Password");
   JLabel messageLabel = new JLabel();

   HashMap<String, String> studentInfo = new HashMap<String, String>();

   StudentLogin(HashMap<String, String> loginInfoOriginal) {

      studentInfo = loginInfoOriginal;

      userIDLabel.setBounds(50, 100, 75, 25);
      userPasswordLabel.setBounds(50, 150, 75, 25);

      messageLabel.setBounds(85, 230, 1000, 100);
      messageLabel.setFont(new Font(null, Font.ITALIC, 15));

      userIDField.setBounds(125, 100, 200, 25);
      userPasswordField.setBounds(125, 150, 200, 25);

      loginButton.setBounds(155, 210, 120, 35);
      loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
      loginButton.addActionListener(this);
      loginButton.setFocusable(false);

      infoLabel.setBounds(80, 310, 255, 50);
      infoLabel.setText("Does not have an Account? ");
      infoLabel.setFont(new Font(null, Font.PLAIN, 13));

      registerLabel.setBounds(255, 313, 83, 45);
      registerLabel.setText("Register here");
      registerLabel.setForeground(new Color(0x588db8));
      registerLabel.setFont(new Font(null, Font.ITALIC, 13));
      registerLabel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            RegisterPage registerPage = new RegisterPage(studentInfo);
         }

         @Override
         public void mouseEntered(MouseEvent e) {
            registerLabel.setForeground(new Color(0x74bdf7));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            registerLabel.setForeground(new Color(0x588db8));
         }
      });

      frame.add(userIDField);
      frame.add(userPasswordField);
      frame.add(loginButton);
      frame.add(userIDLabel);
      frame.add(userPasswordLabel);
      frame.add(infoLabel);
      frame.add(registerLabel);
      frame.add(messageLabel);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(420, 420);
      frame.setResizable(true);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);
      frame.setVisible(true);
   }

   public static void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String username = userIDField.getText();
      String password = String.valueOf(userPasswordField.getPassword());


      if (e.getSource() == loginButton) {
         if (username.equals("admin") && password.equals("admin")) {
            frame.dispose();
            AdminPage adminPage = new AdminPage(studentInfo);
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

         if (studentInfo.containsKey(username)) {
            if (studentInfo.get(username).equals(password)) {
               messageLabel.setForeground(Color.green);
               frame.dispose();
               HomePage homePage = new HomePage(username, studentInfo);
            } else {
               messageLabel.setForeground(Color.red);
               messageLabel.setText("Invalid Password");
            }
         } else {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("UserID not found");
         }
      }
   }
}
