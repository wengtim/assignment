import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.io.FileWriter;
import java.util.HashMap;

public class RegisterPage {
   HashMap<String, String> logininfo = new HashMap<String, String>();

   Color color = new Color(0xf9f7f0);

   JFrame frame = new JFrame("Registeration Status");
   JLabel userLabel = new JLabel("");
   JLabel passwordLabel = new JLabel("");
   JLabel statusLabel = new JLabel("");

   public static void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public RegisterPage(HashMap<String, String> loginInfoOriginal, String username, String password) {
      logininfo = loginInfoOriginal;

      try {
         FileWriter writer = new FileWriter("data/logininfo.txt", true);
         writer.write(username + ":" + password + "\n");
         writer.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 300);
      frame.setLayout(null);
      frame.setResizable(false);
      frame.getContentPane().setBackground(color);

      statusLabel.setBounds(125, 55, 400, 35);
      statusLabel.setFont(new Font(null, Font.PLAIN, 13));
      statusLabel.setText("<html>Status: <span style='color:green;'>Success</span></html>");

      userLabel.setBounds(125, 75, 400, 100);
      userLabel.setFont(new Font(null, Font.PLAIN, 13));
      userLabel.setText("<html>User Created: <span style='color:#57915d;'>" + username + "</span></html>");

      passwordLabel.setBounds(125, 100, 400, 100);
      passwordLabel.setFont(new Font(null, Font.PLAIN, 13));
      passwordLabel.setText("<html>Password: <span style='color:#57915d;'>" + password + "</span></html>");

      frame.add(statusLabel);
      frame.add(userLabel);
      frame.add(passwordLabel);
      frame.setVisible(true);

      new Thread(() -> {
         timesleep(3000);
         frame.dispose();
         LoginPage loginPage = new LoginPage(logininfo);
      }).start();

   }
}
