import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;

import java.util.HashMap;

public class RegisterPage {
   HashMap<String, String> logininfo = new HashMap<String, String>();

   JFrame frame = new JFrame("Registeration");
   JButton backButton = new JButton("Back");
   JLabel messageLabel = new JLabel("");

   public RegisterPage(HashMap<String, String> loginInfoOriginal) {
      logininfo = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 300);
      frame.setLayout(new GridBagLayout());
      frame.setResizable(false);

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(10, 10, 10, 10);
      gbc.anchor = GridBagConstraints.CENTER;

      backButton.setBounds(0, 0, 100, 30);
      backButton.setFocusable(false);
      messageLabel.setFont(new Font(null, Font.BOLD, 20));
      messageLabel.setText("Registeration Successful");
      gbc.gridx = 0;
      gbc.gridy = 0;
      frame.add(messageLabel, gbc);

      gbc.gridx = 0;
      gbc.gridy = 1;
      frame.add(backButton, gbc);

      frame.setVisible(true);

      backButton.addActionListener(e -> {
         frame.dispose();
         LoginPage loginPage = new LoginPage(logininfo);
      });
   }
}
