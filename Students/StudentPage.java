package Students;

import Students.Consultation.ConsultationPage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.util.HashMap;

public class StudentPage implements ActionListener {

   private boolean isHovered = false;

   Color color = new Color(0xf9f7f0);
   Font font = new Font(null, Font.PLAIN, 13);

   JFrame frame = new JFrame("Home (Student)");
   JLabel loggedInfo = new JLabel();
   JLabel message = new JLabel();

   HashMap<String, String> loginInfoOriginal;

   public StudentPage(String name, String userID,  HashMap<String, String> loginInfoOriginal) {
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

      JPanel logoutPanel = new JPanel() {

         @Override
         public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            ImageIcon image = new ImageIcon("image/logoutButton.png");
            if (isHovered) {
               g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            g.drawImage(image.getImage(), 3, 3, 50, 50, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

         }
      };
      logoutPanel.setBounds(900, 10, 55, 55);
      logoutPanel.setOpaque(false);
      logoutPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            StudentLogin studentLogin = new StudentLogin(loginInfoOriginal);
         }

         public void mouseEntered(MouseEvent e) {
            logoutPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            logoutPanel.repaint();
         }

         public void mouseExited(MouseEvent e) {
            logoutPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            logoutPanel.repaint();
         }
      });

      JPanel panel = new JPanel();
      panel.setBounds(220, 110, 550, 310);
      panel.setLayout(null);
      panel.setOpaque(false);

      JPanel consultPanel = new JPanel() {
         @Override
         public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            ImageIcon image = new ImageIcon("image/consultButton.png");
            Graphics2D g2d = (Graphics2D) g;
            if (isHovered) {
               g2d.setColor(new Color(0x8cb9d8));
            } else {
               g2d.setColor(new Color(0xCEDBE4));
            }
            g2d.fillRoundRect(12, 12, 230, 130, 50, 50);
            g2d.setStroke(new BasicStroke(2));

            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(12, 12, 230, 130, 50, 50);
            g.drawImage(image.getImage(), 20, 37, 75, 75, null);
         }
      };
      consultPanel.setBounds(21, 0, 250, 150);
      consultPanel.setOpaque(false);
      consultPanel.setLayout(null);
      JLabel consultLabel = new JLabel("Consultation");
      consultLabel.setFont(new Font(null, Font.BOLD, 16));
      consultLabel.setBounds(90, 25, 130, 100);
      consultPanel.add(consultLabel);
      consultPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            ConsultationPage consultationPage = new ConsultationPage(name, userID,  loginInfoOriginal);
         }

         public void mouseEntered(MouseEvent e) {
            consultPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            consultPanel.repaint();
         }

         public void mouseExited(MouseEvent e) {
            consultPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            consultPanel.repaint();
         }
      });

      JPanel historyPanel = new JPanel() {
         @Override
         public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            ImageIcon image = new ImageIcon("image/historyButton.png");
            Graphics2D g2d = (Graphics2D) g;
            if (isHovered) {
               g2d.setColor(new Color(0x8cb9d8));
            } else {
               g2d.setColor(new Color(0xCEDBE4));
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.fillRoundRect(14, 12, 230, 130, 50, 50);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(14, 12, 230, 130, 50, 50);
            g.drawImage(image.getImage(), 35, 37, 78, 70, null);
         }
      };
      historyPanel.setBounds(280, 0, 260, 150);
      historyPanel.setOpaque(false);
      historyPanel.setLayout(null);
      JLabel historyLabel = new JLabel("History");
      historyLabel.setFont(new Font(null, Font.BOLD, 16));
      historyLabel.setBounds(110, 25, 130, 100);
      historyPanel.add(historyLabel);
      historyPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            System.out.println("History...");
         }

         public void mouseEntered(MouseEvent e) {
            historyPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            historyPanel.repaint();
         }

         public void mouseExited(MouseEvent e) {
            historyPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            historyPanel.repaint();
         }
      });

      JPanel schedulePanel = new JPanel() {
         @Override
         public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            ImageIcon image = new ImageIcon("image/scheduleButton.png");
            Graphics2D g2d = (Graphics2D) g;
            if (isHovered) {
               g2d.setColor(new Color(0x8cb9d8));
            } else {
               g2d.setColor(new Color(0xCEDBE4));
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.fillRoundRect(14, 12, 230, 130, 50, 50);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(14, 12, 230, 130, 50, 50);
            g.drawImage(image.getImage(), 36, 53, 50, 50, null);
         }
      };
      schedulePanel.setBounds(21, 160, 245, 150);
      schedulePanel.setLayout(null);
      schedulePanel.setOpaque(false);
      JLabel scheduleLabel = new JLabel("Schedule");
      scheduleLabel.setFont(new Font(null, Font.BOLD, 16));
      scheduleLabel.setBounds(100, 25, 130, 100);
      schedulePanel.add(scheduleLabel);
      schedulePanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            System.out.println("Schedule...");
         }

         public void mouseEntered(MouseEvent e) {
            schedulePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            schedulePanel.repaint();
         }

         public void mouseExited(MouseEvent e) {
            schedulePanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            schedulePanel.repaint();
         }
      });

      JPanel feedBackPanel = new JPanel() {
         @Override
         public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            ImageIcon image = new ImageIcon("image/feedBackButton.png");
            Graphics2D g2d = (Graphics2D) g;
            if (isHovered) {
               g2d.setColor(new Color(0x8cb9d8));
            } else {
               g2d.setColor(new Color(0xCEDBE4));
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.fillRoundRect(14, 12, 230, 130, 50, 50);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(14, 12, 230, 130, 50, 50);
            g.drawImage(image.getImage(), 42, 39, 65, 65, null);
         }
      };
      feedBackPanel.setBounds(280, 160, 245, 150);
      feedBackPanel.setLayout(null);
      feedBackPanel.setOpaque(false);
      JLabel feedBackLabel = new JLabel("FeedBack");
      feedBackLabel.setFont(new Font(null, Font.BOLD, 16));
      feedBackLabel.setBounds(107, 25, 130, 100);
      feedBackPanel.add(feedBackLabel);
      feedBackPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            System.out.println("FeedBack...");
         }

         public void mouseEntered(MouseEvent e) {
            feedBackPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            feedBackPanel.repaint();
         }

         public void mouseExited(MouseEvent e) {
            feedBackPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            feedBackPanel.repaint();
         }
      });

      panel.add(consultPanel);
      panel.add(historyPanel);
      panel.add(schedulePanel);
      panel.add(feedBackPanel);

      frame.add(panel);
      frame.add(logoutPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
   }
}