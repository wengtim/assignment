import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LectureLogin implements ActionListener {

   Color color = new Color(0xf9f7f0);

   JFrame frame = new JFrame("Lecture Login");
   JButton loginButton = new JButton("Login");
   JTextField userIDField = new JTextField();
   JPasswordField userPasswordField = new JPasswordField();
   JLabel userIDLabel = new JLabel("User ID");
   JLabel userPasswordLabel = new JLabel("Password");
   JLabel messageLabel = new JLabel();
   JButton backButton = new JButton("Back");

   HashMap<String, String> lectureInfo = new HashMap<String, String>();

   LectureLogin(HashMap<String, String> loginInfoOriginal) {

      lectureInfo = loginInfoOriginal;

      userIDLabel.setBounds(50, 100, 75, 25);
      userPasswordLabel.setBounds(50, 150, 75, 25);

      messageLabel.setBounds(85, 230, 1000, 100);
      messageLabel.setFont(new Font(null, Font.ITALIC, 15));

      userIDField.setBounds(125, 100, 200, 25);
      userPasswordField.setBounds(125, 150, 200, 25);

      loginButton.setBounds(70, 210, 120, 35);
      loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
      loginButton.addActionListener(this);
      loginButton.setFocusable(false);

      backButton.setBounds(200, 210, 120, 35);
      backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
      backButton.addActionListener(this);
      backButton.setFocusable(false);

      frame.add(userIDField);
      frame.add(userPasswordField);
      frame.add(loginButton);
      frame.add(userIDLabel);
      frame.add(userPasswordLabel);
      frame.add(messageLabel);
      frame.add(backButton);

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

      if (e.getSource() == backButton) {
         frame.dispose();
         new StartPage();
      }

      if (e.getSource() == loginButton) {
         if (username.equals("admin") && password.equals("admin")) {
            frame.dispose();
            AdminPage adminPage = new AdminPage(lectureInfo);
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

         if (lectureInfo.containsKey(username)) {
            if (lectureInfo.get(username).equals(password)) {
               messageLabel.setForeground(Color.green);
               frame.dispose();
               LecturerPage lecturePage = new LecturerPage(username, lectureInfo);
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