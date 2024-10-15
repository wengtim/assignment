import java.awt.Font;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HomePage {
   JFrame frame = new JFrame("Home");
   JLabel welcomeLabel = new JLabel("Welcome Page");
   JButton logoutButton = new JButton("Log Out");

   HomePage(String userID) {

      welcomeLabel.setBounds(0, 0, 400, 35);
      welcomeLabel.setFont(new Font(null, Font.PLAIN, 15));
      welcomeLabel.setText("Logged In as: " + userID);

      logoutButton.setBounds(200, 200, 100, 30);
      logoutButton.setFocusable(false);
      logoutButton.addActionListener(e -> {
         frame.dispose();
         LoginPage loginPage = new LoginPage(new HashMap<String, String>());
      });

      frame.add(welcomeLabel);
      frame.add(logoutButton);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 500);
      frame.setLayout(null);
      frame.setVisible(true);
   }
}
