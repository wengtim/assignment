package Lecturer.option;

import Lecturer.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.*;

public class History {

   private boolean isRemoved = false;
   private boolean isHovered = false;
   private boolean hasBooking = false;

   Color color = new Color(0xB3EBF2);
   JFrame frame = new JFrame("History");
   JLabel loggedInfo = new JLabel();
   Font font = new Font("JetBrains Mono", Font.PLAIN, 13);
   JPanel contentPanel = new JPanel();
   String userID;
   String lecName;
   String lecID;
   HashMap<String, String> loginInfoOriginal;

   public History(String lecName, String lecID, HashMap<String, String> loginInfoOriginal) {

      this.lecName = lecName;
      this.lecID = lecID;
      this.loginInfoOriginal = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);

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
            new LecturerPage(lecName, lecID, loginInfoOriginal);
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
      contentPanel.setBackground(color);

      try (BufferedReader reader = new BufferedReader(new FileReader("data/booking/accepted/accepted.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7) {
               String bookingID = data[0];
               String studentID = data[1];
               this.lecID = lecID;
               this.lecName = lecName;
               String day = data[3];
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];

               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
               LocalDate bookingDate = LocalDate.parse(date, dateFormatter);
               LocalTime bookingEndTime = LocalTime.parse(endTime, timeFormatter);
               LocalDateTime bookingEndTimeLocal = LocalDateTime.of(bookingDate, bookingEndTime);

               if (studentID.equals(userID) && LocalDateTime.now().isAfter(bookingEndTimeLocal)) {
                  saveToHistory(bookingID, studentID, lecID, day, date, startTime, endTime, "Accepted");
                  JPanel schedulePanel = showTimeDetails(lecName, day, date, startTime, endTime, "Accepted");
                  contentPanel.add(schedulePanel);
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      try {
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/history.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            if (data.length == 8) {
               String studentID = data[1];
               String lecturerID = data[2];
               String studentName = getStudentName(studentID);
               String day = data[3];
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = data[7];

               if (lecturerID.equals(lecID)) {
                  JPanel showTime = showTimeDetails(studentName, day, date, startTime, endTime, status);
                  contentPanel.add(showTime);
                  hasBooking = true;
               }
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      if (!hasBooking) {
         JLabel noAvailableTimeLabel = new JLabel("No History Found");
         noAvailableTimeLabel.setFont(new Font("Poppins", Font.BOLD, 23));
         noAvailableTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
         contentPanel.add(noAvailableTimeLabel);
      }

      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      mainPanel.add(scrollPane);
      frame.add(mainPanel);
      frame.add(loggedInfo);
      frame.add(backPanel);
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);
   }

   private JPanel showTimeDetails(String studentName, String day, String date, String startTime, String endTime, String status) {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setPreferredSize(new Dimension(750, 100));
      panel.setMaximumSize(new Dimension(750, 100));
      panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
      panel.setBackground(color);

      JLabel iconLabel = new JLabel(new ImageIcon(
         new ImageIcon("image/person.png")
         .getImage()
         .getScaledInstance(130, 100, Image.SCALE_SMOOTH)
      ));
      iconLabel.setPreferredSize(new Dimension(100, 100));
      panel.add(iconLabel, BorderLayout.WEST);

      JPanel detailsPanel = new JPanel();
      detailsPanel.setLayout(new GridLayout(2, 1));
      detailsPanel.setBackground(color);

      JLabel lecLabel = new JLabel("<html>Student Name: <span style='color:#636261;'>" + studentName + "</span></html>");
      lecLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel dayLabel = new JLabel("<html>Day: <span style='color:#636261;'>" + day + "</span></html>");
      dayLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel dateLabel = new JLabel("<html>Booking Date: <span style='color:#636261;'>" + date + "</span></html>");
      dateLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel timeLabel = new JLabel("<html>Booking Time: <span style='color:#636261;'>" + startTime + "-" + endTime + "</span></html>");
      timeLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel statusLabel = new JLabel("<html>Status: <span style='color:#636261;'>" + status  + "</span></html>");
      statusLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      detailsPanel.add(lecLabel);
      detailsPanel.add(dayLabel);
      detailsPanel.add(timeLabel);
      detailsPanel.add(statusLabel);
      detailsPanel.add(dateLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      return panel;
   }

   private String getStudentName(String userID) {
      try {
         BufferedReader reader = new BufferedReader(new FileReader("data/credentials/studentInfo.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length == 3 && data[1].equals(userID)) {
               return data[0];
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "";
   }

   private String getLecName(String lecID) {
      try {
         BufferedReader reader = new BufferedReader(new FileReader("data/credentials/lecturerInfo.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length == 4 && data[1].equals(lecID)) {
               return data[0];
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "";
   }

   private void saveToHistory(String bookingID, String studentID, String lecID, String day, String date, String startTime, String endTime, String status) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/booking/history.txt", true))) {
         writer.write(bookingID + "," + studentID + "," + lecID + "," + day + "," + date + "," + startTime + "," + endTime + "," + status);
         writer.newLine();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }
}
