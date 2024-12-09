package Lecturer;

import Lecturer.option.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.*;

public class LecturerPage {

   private boolean isHovered = false;

   Color color = new Color(0xf9f7f0);
   Font font = new Font(null, Font.PLAIN, 13);

   JFrame frame = new JFrame("Home (Lecturer)");
   JLabel loggedInfo = new JLabel();

   HashMap<String, String> loginInfoOriginal;

   public LecturerPage(String name, String lecID, HashMap<String, String> loginInfoOriginal) {
      this.loginInfoOriginal = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);

      loggedInfo.setBounds(10, 500, 233, 21);
      loggedInfo.setFont(font);
      loggedInfo.setText("<html>Logged in as: <span style='color:#57915d;'>" + lecID + "</span></html>");

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
            LectureLogin lectureLogin = new LectureLogin(loginInfoOriginal);
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
      panel.setLayout(null);
      panel.setOpaque(false);
      panel.setBounds(220, 110, 550, 310);

      JPanel viewPending = new JPanel() {
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
      viewPending.setBounds(21, 0, 250, 150);
      viewPending.setOpaque(false);
      viewPending.setLayout(null);
      JLabel pendingLabel = new JLabel("View Booking");
      pendingLabel.setFont(new Font(null, Font.BOLD, 16));
      pendingLabel.setBounds(90, 25, 130, 100);
      viewPending.add(pendingLabel);
      viewPending.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            ViewPending viewPending = new ViewPending(name, lecID, loginInfoOriginal);
         }

         public void mouseEntered(MouseEvent e) {
            viewPending.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            viewPending.repaint();
         }

         public void mouseExited(MouseEvent e) {
            viewPending.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            viewPending.repaint();
         }
      });

      JPanel manageTimeSlots = new JPanel() {
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
      manageTimeSlots.setBounds(280, 0, 260, 150);
      manageTimeSlots.setOpaque(false);
      manageTimeSlots.setLayout(null);
      JLabel manageLabel = new JLabel("Manage Time");
      manageLabel.setFont(new Font(null, Font.BOLD, 16));
      manageLabel.setBounds(110, 25, 130, 100);
      manageTimeSlots.add(manageLabel);
      manageTimeSlots.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            ManageTime manageTime = new ManageTime(lecID, name, loginInfoOriginal);
         }

         public void mouseEntered(MouseEvent e) {
            manageTimeSlots.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            isHovered = true;
            manageTimeSlots.repaint();
         }

         public void mouseExited(MouseEvent e) {
            manageTimeSlots.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            isHovered = false;
            manageTimeSlots.repaint();
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
            g.drawImage(image.getImage(), 36, 53, 50, 50, null);
         }
      };
      historyPanel.setBounds(21, 160, 245, 150);
      historyPanel.setLayout(null);
      historyPanel.setOpaque(false);
      JLabel historyLabel = new JLabel("History");
      historyLabel.setFont(new Font(null, Font.BOLD, 16));
      historyLabel.setBounds(100, 25, 130, 100);
      historyPanel.add(historyLabel);
      historyPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            History history = new History(name, lecID, loginInfoOriginal);
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
            frame.dispose();
            Feedback feedback = new Feedback(name, lecID, loginInfoOriginal);
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

      panel.add(viewPending);
      panel.add(manageTimeSlots);
      panel.add(historyPanel);
      panel.add(feedBackPanel);

      frame.add(panel);
      frame.add(logoutPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
   }

}
