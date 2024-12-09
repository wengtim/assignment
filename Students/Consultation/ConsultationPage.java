package Students.Consultation;

import java.awt.*;
import Students.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import java.util.HashMap;

public class ConsultationPage implements ActionListener {

   private boolean isHovered = false;
   String userID;

   HashMap<String, String> loginInfoOriginal;

   JFrame frame = new JFrame("Consultation Page");
   JLabel loggedInfo = new JLabel();
   Font font = new Font(null, Font.PLAIN, 13);

   public ConsultationPage(String name, String userID, HashMap<String, String> loginInfoOriginal) {

      this.userID = userID;
      this.loginInfoOriginal = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(new Color(0xf9f7f0));

      loggedInfo.setBounds(10, 500, 233, 21);
      loggedInfo.setFont(font);
      loggedInfo.setText("<html>Logged in as: <span style='color:#57915d;'>" + userID + "</span></html>");

      JPanel backPanel = new JPanel() {
         @Override
         public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            ImageIcon image = new ImageIcon("image/backButton.png");
            if (isHovered) {
               g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            g.drawImage(image.getImage(), 0, 0, 75, 35, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
         }
      };
      backPanel.setBounds(10, 10, 75, 35);
      backPanel.setOpaque(false);
      backPanel.setFocusable(false);
      backPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            new StudentPage(name, userID, loginInfoOriginal);
         }

         @Override
         public void mouseEntered(MouseEvent e) {
            isHovered = true;
            backPanel.repaint();
         }

         @Override
         public void mouseExited(MouseEvent e) {
            isHovered = false;
            backPanel.repaint();
         }
      });

      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());
      mainPanel.setBounds(100, 50, 800, 430);

      JPanel contentPanel = new JPanel();
      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
      contentPanel.setBackground(new Color(0xf9f7f0));

      try {
         BufferedReader reader = new BufferedReader(new FileReader("data/credentials/lecturerInfo.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length >= 4) {
               String lecName = data[0];
               String lectID = data[1];
               String status = data[3];
               JPanel lecDetails = createLecturerPanel(lecName, lectID,  status);
               contentPanel.add(lecDetails);
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      mainPanel.add(scrollPane);
      frame.add(mainPanel);
      frame.add(backPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
   }

   private JPanel createLecturerPanel(String lecName, String lecID, String status) {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setPreferredSize(new Dimension(750, 100));
      panel.setMaximumSize(new Dimension(750, 100));
      panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
      panel.setBackground(new Color(0xf9f7f0));

      JLabel iconLabel = new JLabel(new ImageIcon(
            new ImageIcon("image/person.png")
                  .getImage()
                  .getScaledInstance(130, 100, Image.SCALE_SMOOTH)
      ));
      iconLabel.setPreferredSize(new Dimension(100, 100));
      panel.add(iconLabel, BorderLayout.WEST);

      JPanel detailsPanel = new JPanel();
      detailsPanel.setLayout(new GridLayout(2, 1));
      detailsPanel.setBackground(new Color(0xf9f7f0));
      JLabel nameLabel = new JLabel("<html>Lecturer Name: <span style='color:#636261;'>" + lecName + "</span></html>");
      nameLabel.setFont(new Font("Poppins", Font.BOLD, 17));
      JLabel statusLabel = new JLabel("<html>Status: <span style='color:#57915d;'>" + status + "</span></html>");
      statusLabel.setFont(new Font("Poppins", Font.PLAIN, 17));
      detailsPanel.add(nameLabel);
      detailsPanel.add(statusLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      JButton bookButton = new JButton("Book");
      bookButton.setFocusable(false);
      bookButton.setPreferredSize(new Dimension(100, 25));
      bookButton.setFont(new Font("Poppins", Font.BOLD, 14));
      bookButton.setBackground(new Color(0xfce1c5));
      bookButton.addActionListener(e -> {
         frame.dispose();
         new Booking(lecName, lecID,  userID,  loginInfoOriginal);
      });
      panel.add(bookButton, BorderLayout.EAST);

      return panel;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
   }
}
