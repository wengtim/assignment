import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StudentPage implements ActionListener {

   Color color = new Color(0xf9f7f0);
   Font font = new Font(null, Font.PLAIN, 13);

   JFrame frame = new JFrame("Home");
   JLabel loggedInfo = new JLabel();
   JLabel message = new JLabel();
   JButton logoutButton = new JButton("Log Out");

   HashMap<String, String> loginInfoOriginal;

   public StudentPage(String userID, HashMap<String, String> loginInfoOriginal) {
      this.loginInfoOriginal = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);

      message.setBounds(200, 0, 400, 35);
      message.setFont(font);

      loggedInfo.setBounds(10, 500, 233, 21);
      loggedInfo.setFont(font);
      loggedInfo.setText("<html>Logged in as: <span style='color:#57915d;'>" + userID + "</span></html>");

      logoutButton.setBounds(900, 10, 75, 35);
      logoutButton.setFocusable(false);
      logoutButton.addActionListener(this);

      frame.add(loggedInfo);
      frame.add(logoutButton);

      frame.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == logoutButton) {
         frame.dispose();
         new StudentLogin(loginInfoOriginal);
      }
   }
}
