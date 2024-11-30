package Students.Consultation;

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

public class Booking {

   private boolean isHovered = false;
   private boolean hasBooking = false;

   JFrame frame = new JFrame("Booking Page");
   JLabel loggedInfo = new JLabel();
   Font font = new Font("JetBrains Mono", Font.PLAIN, 13);
   JPanel contentPanel = new JPanel();
   String lecID;
   String userID;
   String lecName;
   HashMap<String, String> loginInfoOriginal;

   public Booking(String lecName, String lecID, String userID, HashMap<String, String> loginInfoOriginal) {

      this.lecName = lecName;
      this.lecID = lecID;
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
            new ConsultationPage(lecName, userID, loginInfoOriginal);
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
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/availability.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 5 && data[0].equals(lecID)) {
               String lecturerName = getLecName(lecID);
               String day = data[1];
               String date = data[2];
               String startTime = data[3];
               String endTime = data[4];

               JPanel showTime = showTimeDetails(lecturerName, day, date, startTime, endTime);
               contentPanel.add(showTime);
               hasBooking = true;
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      if (!hasBooking) {
         JLabel noAvailableTimeLabel = new JLabel("No Available Time");
         noAvailableTimeLabel.setFont(new Font("Poppins", Font.BOLD, 18));
         noAvailableTimeLabel.setForeground(Color.GRAY);
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
   }

   private JPanel showTimeDetails(String lecturerName, String day, String date, String startTime, String endTime) {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setPreferredSize(new Dimension(750, 100));
      panel.setMaximumSize(new Dimension(750, 100));
      panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
      panel.setBackground(new Color(0xf9f7f0));

      JPanel detailsPanel = new JPanel();
      detailsPanel.setLayout(new GridLayout(2, 1));
      detailsPanel.setBackground(new Color(0xf9f7f0));

      JLabel nameLabel = new JLabel("<html>Lecturer Name: <span style='color:#636261;'>" + lecturerName + "</span></html>");
      nameLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel dayLabel = new JLabel("<html>Day: <span style='color:#636261;'>" + day + "</span></html>");
      dayLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel dateLabel = new JLabel("<html>Booking Date: <span style='color:#636261;'>" + date + "</span></html>");
      dateLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel timeLabel = new JLabel("<html>Booking Time: <span style='color:#636261;'>" + startTime + "-" + endTime + "</span></html>");
      timeLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      detailsPanel.add(nameLabel);
      detailsPanel.add(dayLabel);
      detailsPanel.add(dateLabel);
      detailsPanel.add(timeLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      JButton bookButton = new JButton("Request");
      bookButton.setFocusable(false);
      bookButton.setPreferredSize(new Dimension(100, 35));
      bookButton.setFont(new Font("Poppins", Font.BOLD, 15));
      bookButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {

            try {
               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               LocalDate selectedDate = LocalDate.parse(date, dateFormatter);
               LocalTime selectedStartTime = LocalTime.parse(startTime);
               LocalTime selectedEndTime = LocalTime.parse(endTime);

               writeToFile(userID, lecID, selectedDate, selectedStartTime, selectedEndTime);
               hideBooking(panel);
               JOptionPane.showMessageDialog(frame, "Your request has been sent", "Success", JOptionPane.INFORMATION_MESSAGE);
               frame.dispose();
               new ConsultationPage(lecName, userID, loginInfoOriginal);
            } catch (DateTimeParseException ex) {
               System.out.println("Error parsing date: " + ex.getMessage());
            }
         }
      });
      panel.add(bookButton, BorderLayout.EAST);

      return panel;
   }

   private void hideBooking(JPanel bookingPanel) {
      contentPanel.remove(bookingPanel);
      contentPanel.revalidate();
      contentPanel.repaint();
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


   private void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   private void writeToFile(String userID, String lecID, LocalDate selectedDate, LocalTime selectedStartTime, LocalTime selectedEndTime) {
      String fileName = "data/booking/pending/bookingDetails.txt";

      int newId = 1;
      try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
         String lastLine = null, line;
         while ((line = reader.readLine()) != null) {
            lastLine = line;
         }
         if (lastLine != null) {
            String[] parts = lastLine.split(",");
            if (parts.length > 0) {
               newId = Integer.parseInt(parts[0]) + 1;
            }
         }
      } catch (IOException | NumberFormatException e) {
         System.out.println("Error reading booking details file: " + e.getMessage());
      }

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
         writer.write(newId + "," + userID + "," + lecID + "," + selectedDate + "," + selectedStartTime + "," + selectedEndTime + "," + "Pending" + "\n");
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
