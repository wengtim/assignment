import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HomePage {
   JFrame frame = new JFrame("Home");
   JLabel welcomeLabel = new JLabel("Welcome Page");

   HomePage(String userID) {

      welcomeLabel.setBounds(0, 0, 400, 35);
      welcomeLabel.setFont(new Font(null, Font.PLAIN, 15));
      welcomeLabel.setText("Logged In as: " + userID);

      frame.add(welcomeLabel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(500, 500);
      frame.setLayout(null);
      frame.setVisible(true);
   }
}
