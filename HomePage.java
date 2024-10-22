import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;

public class HomePage implements ActionListener {

   public static void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   JFrame frame = new JFrame("Home");
   JLabel loggedInfo = new JLabel();
   JLabel message = new JLabel();
   JButton logoutButton = new JButton("Log Out");

   public HomePage(String userID) {

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(new Color(0xf9f7f0));

      message.setBounds(200, 0, 400, 35);
      message.setFont(new Font(null, Font.PLAIN, 13));

      loggedInfo.setBounds(825, 500, 233, 21);
      loggedInfo.setFont(new Font(null, Font.PLAIN, 13));
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
         LoginPage loginPage = new LoginPage(new HashMap<String, String>());
      }
   }
}
