package Students;

import Handler.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StudentLogin implements ActionListener {

   Color color = new Color(0xB3EBF2);

   JFrame frame = new JFrame("Student Login");
   JButton loginButton = new JButton("Login");
   JTextField userIDField = new JTextField();
   JPasswordField userPasswordField = new JPasswordField();
   JLabel registerLabel = new JLabel();
   JLabel infoLabel = new JLabel();
   JLabel userIDLabel = new JLabel("User ID");
   JLabel userPasswordLabel = new JLabel("Password");
   JLabel messageLabel = new JLabel();
   JButton backButton = new JButton("Back");

   HashMap<String, String> studentInfo = new HashMap<String, String>();

   public StudentLogin(HashMap<String, String> loginInfoOriginal) {

      studentInfo = loginInfoOriginal;

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
      frame.add(backButton);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(420, 420);
      frame.setResizable(false);
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
      String studentName = readFile(username);

      if (e.getSource() == backButton) {
         userIDField.setText("");
         userPasswordField.setText("");
         frame.dispose();
         new StartPage();
      }

      if (username.equals("")) {
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
            StudentPage studentPage = new StudentPage(studentName, username,  studentInfo);
         } else {
            messageLabel.setForeground(Color.red);
            messageLabel.setText("Invalid Password");
         }
      } else {
         messageLabel.setForeground(Color.red);
         messageLabel.setText("UserID not found");
      }
   }

   public String readLecID(String lecID) {
      String filePath = "data/credentials/lectureInfo.txt";

      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 3) {
               String userID = parts[1].trim();
               if (userID.equals(lecID)) {
                  String name = parts[0].trim();
                  return name;
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   public String readFile(String username) {
      String filePath = "data/credentials/studentInfo.txt";

      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 3) {
               String userID = parts[1].trim();
               if (userID.equals(username)) {
                  String name = parts[0].trim();
                  return name;
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }
}
