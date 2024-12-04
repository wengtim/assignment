package Lecturer.option;

import java.util.HashMap;
import Lecturer.*;
import Lecturer.fileUtils.*;
import Lecturer.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class ManageTime {

   private boolean isHovered = false;
   private boolean hasSlots = false;

   String filePath = "data/booking/availability.txt";
   JFrame frame = new JFrame("Manage Time Slots");
   JLabel loggedInfo = new JLabel();
   ReadFile readFile = new ReadFile();
   JPanel contentPanel = new JPanel();
   Font font = new Font("JetBrains Mono", Font.PLAIN, 13);

   String lecID;
   String name;
   HashMap<String, String> loginInfoOriginal;

   public ManageTime(String lecID, String name, HashMap<String, String> loginInfoOriginal) {
      this.lecID = lecID;
      this.name = name;
      this.loginInfoOriginal = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(new Color(0xf9f7f0));

      loggedInfo.setBounds(10, 500, 233, 21);
      loggedInfo.setFont(font);
      loggedInfo.setText("<html>Logged in as: <span style='color:#57915d;'>" + lecID + "</span></html>");

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
      backPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      backPanel.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.dispose();
            LecturerPage lecturerPage = new LecturerPage(name, lecID, loginInfoOriginal);
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

      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
      contentPanel.setBackground(new Color(0xf9f7f0));

      try {
         BufferedReader reader = new BufferedReader(new FileReader(filePath));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5 && data[0].equals(lecID)) {
               String lecturerID = data[0];
               String day = data[1];
               String date = data[2];
               String startTime = data[3];
               String endTime = data[4];
               String lecName = getLecturerName(lecID);

               JPanel manageTime = manageTimeSlots(lecturerID, day, date, startTime, endTime, lecName);
               contentPanel.add(manageTime);
               hasSlots = true;
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      if (!hasSlots) {
         JPanel emptySlots = manageTimeSlots(lecID, "", "", "", "", "");
         emptySlots.setFont(font);
         contentPanel.add(emptySlots);
      }

      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

      mainPanel.add(scrollPane);

      frame.add(mainPanel);
      frame.add(backPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
   }

   private JPanel manageTimeSlots(String lecID, String day, String date, String startTime, String endTime, String lecName) {
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

      JLabel lecturerName = new JLabel("<html>Lecturer Name: <span style='color:#636261;'>" + lecName + "</span></html>");
      lecturerName.setFont(new Font("Poppins", Font.BOLD, 15));

      String dayText = (day.isEmpty()) ? "Day: N/A" : "<html>Day: <span style='color:#636261;'>" + day + "</span></html>";
      String dateText = (date.isEmpty()) ? "Date: N/A" : "<html>Date: <span style='color:#636261;'>" + date + "</span></html>";
      String timeText = (startTime.isEmpty() || endTime.isEmpty()) ? "Time: N/A" : "<html>Time: <span style='color:#636261;'>" + startTime + "-" + endTime + "</span></html>";

      JLabel dayLabel = new JLabel(dayText);
      dayLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel dateLabel = new JLabel(dateText);
      dateLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel timeLabel = new JLabel(timeText);
      timeLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      detailsPanel.add(lecturerName);
      detailsPanel.add(dayLabel);
      detailsPanel.add(dateLabel);
      detailsPanel.add(timeLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setBackground(new Color(0xf9f7f0));
      buttonPanel.setPreferredSize(new Dimension(100, 100));

      JButton editButton = new JButton("Edit");
      editButton.setVisible(false);
      editButton.setFocusable(false);
      editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      editButton.setPreferredSize(new Dimension(100, 45));
      editButton.setFont(new Font("Poppins", Font.BOLD, 14));
      editButton.setBackground(new Color(0xfce1c5));
      editButton.addActionListener(e -> {
         frame.dispose();
         EditTime editTime = new EditTime(lecID, day, date, startTime, endTime, lecName, loginInfoOriginal);
      });

      JButton addButton = new JButton("Add");
      addButton.setVisible(true);
      addButton.setFocusable(false);
      addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      addButton.setPreferredSize(new Dimension(100, 45));
      addButton.setFont(new Font("Poppins", Font.BOLD, 14));
      addButton.setBackground(new Color(0xfce1c5));
      addButton.addActionListener(e -> {
         frame.dispose();
         AddTime addTime = new AddTime(lecID, day, date, startTime, endTime, lecName, loginInfoOriginal);
      });

      if (isSlotAvailable(lecID, day, date, startTime, endTime)) {
         editButton.setVisible(true);
      } else {
         addButton.setVisible(true);
      }
      addButton.setBounds(810, 10, 90, 35);

      //buttonPanel.add(editButton);
      //panel.add(buttonPanel, BorderLayout.EAST);
      panel.add(editButton, BorderLayout.EAST);
      frame.add(addButton);

      return panel;
   }

   private String getLecturerName(String lecID) {
      String lecName = "";
      try (BufferedReader reader = new BufferedReader(new FileReader("data/credentials/lecturerInfo.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length == 4 && data[1].equals(lecID)) {
               lecName = data[0];
               break;
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return lecName;
   }

   private boolean isSlotAvailable(String lecID, String day, String date, String startTime, String endTime) {
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5) {
               if (data[0].equals(lecID) && data[1].equals(day) && data[2].equals(date) && data[3].equals(startTime) && data[4].equals(endTime)) {
                  return true;
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return false;
   }
}
