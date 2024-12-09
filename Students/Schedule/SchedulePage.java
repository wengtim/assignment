package Students.Schedule;

import Students.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SchedulePage {

   private boolean isHovered = false;
   private boolean hasBooking = false;

   String userID;
   String studentName;

   HashMap<String, String> loginInfoOriginal;
   Font font = new Font(null, Font.PLAIN, 13);

   File rejectedFile = new File("data/booking/rejected/rejected.txt");
   File acceptedFile = new File("data/booking/accepted/accepted.txt");

   JFrame frame = new JFrame("Schedule");
   JLabel loggedInfo = new JLabel();

   public SchedulePage(String studentName, String userID, HashMap<String, String> loginInfoOriginal) {
      this.userID = userID;
      this.loginInfoOriginal = loginInfoOriginal;
      this.studentName = studentName;

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
            new StudentPage(studentName, userID, loginInfoOriginal);
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
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/accepted/accepted.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7) {
               String bookingID = data[0];
               String studentID = data[1];
               String lecID = data[2];
               String day = data[3];
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = "Accepted";

               if (studentID.equals(userID)) {
                  JPanel schedulePanel = createSchedulePanel(bookingID, studentID, lecID, day, date, startTime, endTime, status);
                  contentPanel.add(schedulePanel);
               }
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      try {
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/rejected/rejected.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7) {
               String bookingID = data[0];
               String studentID = data[1];
               String lecID = data[2];
               String day = data[3];
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = "Rejected";

               if (studentID.equals(userID)) {
                  JPanel schedulePanel = createSchedulePanel(bookingID, studentID, lecID, day, date, startTime, endTime, status);
                  contentPanel.add(schedulePanel);
               }
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      showPendingBookings(contentPanel, scrollPane);


      mainPanel.add(scrollPane);

      frame.add(mainPanel);
      frame.add(backPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
   }

   private JPanel createSchedulePanel(String bookingID, String studentID, String lecID, String day, String date, String startTime, String endTime, String status) {

      String lecName = getLecName(lecID);

      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.setPreferredSize(new Dimension(750, 100));
      panel.setMaximumSize(new Dimension(750, 100));
      panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
      panel.setBackground(new Color(0xf9f7f0));

      JPanel detailsPanel = new JPanel();
      detailsPanel.setLayout(new GridLayout(2, 1));
      detailsPanel.setBackground(new Color(0xf9f7f0));

      JLabel lecturerName = new JLabel("<html>Lecturer Name: <span style='color:#636261;'>" + lecName + "</span></html>");
      lecturerName.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel dateLabel = new JLabel("<html>Booking Date: <span style='color:#636261;'>" + date + "</span></html>");
      dateLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel timeLabel = new JLabel("<html>Booking Time: <span style='color:#636261;'>" + startTime + "-" + endTime + "</span></html>");
      timeLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel statusLabel = new JLabel("<html>Status: <span style='color:#57915d;'>" + status + "</span></html>");
      statusLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      detailsPanel.add(lecturerName);
      detailsPanel.add(dateLabel);
      detailsPanel.add(timeLabel);
      detailsPanel.add(statusLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setBackground(new Color(0xf9f7f0));
      buttonPanel.setPreferredSize(new Dimension(100, 100));

      JButton rescheduleBut = new JButton("Reschedule");
      rescheduleBut.setFocusable(false);
      rescheduleBut.setPreferredSize(new Dimension(100, 45));
      rescheduleBut.setFont(new Font("Poppins", Font.BOLD, 14));
      rescheduleBut.setBackground(new Color(0xfce1c5));
      rescheduleBut.addActionListener(e -> {
         rescheduleBooking(bookingID, userID, lecID, day, date);
      });

      if (status.equals("Rejected") || status.equals("Pending")){
         buttonPanel.add(rescheduleBut);
         buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
         buttonPanel.add(Box.createVerticalGlue());

         panel.add(buttonPanel, BorderLayout.EAST);
      } else {
         JPanel emptyPanel = new JPanel();
         emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
         emptyPanel.setBackground(new Color(0xf9f7f0));
         emptyPanel.setPreferredSize(new Dimension(100, 100));

         panel.add(emptyPanel, BorderLayout.EAST);
      }

      return panel;
   }

   public void showPendingBookings(JPanel contentPanel, JScrollPane scrollPane) {
      try {
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/pending/bookingDetails.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7) {
               String bookingID = data[0];
               String studentID = data[1];
               String lecID = data[2];
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = data[7];

               if (studentID.equals(userID)) {
                  String lecName = getLecName(lecID);

                  JPanel panel = new JPanel();
                  panel.setLayout(new BorderLayout());
                  panel.setPreferredSize(new Dimension(750, 100));
                  panel.setMaximumSize(new Dimension(750, 100));
                  panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                  panel.setBackground(new Color(0xf9f7f0));

                  JPanel detailsPanel = new JPanel();
                  detailsPanel.setLayout(new GridLayout(2, 1));
                  detailsPanel.setBackground(new Color(0xf9f7f0));

                  JLabel lecturerName = new JLabel("<html>Lecturer Name: <span style='color:#636261;'>" + lecName + "</span></html>");
                  lecturerName.setFont(new Font("Poppins", Font.BOLD, 17));

                  JLabel dateLabel = new JLabel("<html>Booking Date: <span style='color:#636261;'>" + date + "</span></html>");
                  dateLabel.setFont(new Font("Poppins", Font.BOLD, 17));

                  JLabel timeLabel = new JLabel("<html>Booking Time: <span style='color:#636261;'>" + startTime + "-" + endTime + "</span></html>");
                  timeLabel.setFont(new Font("Poppins", Font.BOLD, 17));

                  JLabel statusLabel = new JLabel("<html>Status: <span style='color:#57915d;'>" + status + "</span></html>");
                  statusLabel.setFont(new Font("Poppins", Font.BOLD, 17));

                  detailsPanel.add(lecturerName);
                  detailsPanel.add(dateLabel);
                  detailsPanel.add(timeLabel);
                  detailsPanel.add(statusLabel);
                  panel.add(detailsPanel, BorderLayout.CENTER);

                  JPanel buttonPanel = new JPanel();
                  buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
                  buttonPanel.setBackground(new Color(0xf9f7f0));
                  buttonPanel.setPreferredSize(new Dimension(100, 100));

                  JButton cancelButton = new JButton("Cancel");
                  cancelButton.setFocusable(false);
                  cancelButton.setPreferredSize(new Dimension(100, 45));
                  cancelButton.setFont(new Font("Poppins", Font.BOLD, 14));
                  cancelButton.setBackground(new Color(0xfce1c5));
                  cancelButton.addActionListener(e -> {
                     cancelBooking(bookingID);
                     contentPanel.removeAll();
                     showPendingBookings(contentPanel, scrollPane);
                     scrollPane.setViewportView(contentPanel);
                     scrollPane.revalidate();
                     scrollPane.repaint();
                  });

                  buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                  buttonPanel.add(Box.createVerticalGlue());
                  buttonPanel.add(cancelButton);

                  panel.add(buttonPanel, BorderLayout.EAST);

                  contentPanel.add(panel);
               }
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public String getLecName(String lecID){
      try (BufferedReader reader = new BufferedReader(new FileReader("data/credentials/lecturerInfo.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(":");
            if (data.length >= 2 && data[1].equals(lecID)) {
               return data[0];
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
   }

   private void cancelBooking(String bookingID) {
      try {
         File bookingFile = new File("data/booking/pending/bookingDetails.txt");
         File availabilityFile = new File("data/booking/availability.txt");
         List<String> allEntries = new ArrayList<>();
         String canceledEntry = null;
         String[] canceledData = null;

         BufferedReader reader = new BufferedReader(new FileReader(bookingFile));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (!data[0].equals(bookingID)) {
               allEntries.add(line);
            } else {
               canceledEntry = line;
               canceledData = data;
            }
         }
         reader.close();

         BufferedWriter writer = new BufferedWriter(new FileWriter(bookingFile));
         for (String entry : allEntries) {
            writer.write(entry);
            writer.newLine();
         }
         writer.close();

         if (canceledEntry != null && canceledData != null) {
            BufferedWriter availabilityWriter = new BufferedWriter(new FileWriter(availabilityFile, true));
            availabilityWriter.write(canceledData[2] + "," + canceledData[3] + "," + canceledData[4] + "," + canceledData[5] + "," + canceledData[6]);
            availabilityWriter.newLine();
            availabilityWriter.close();
         }

         JOptionPane.showMessageDialog(frame, "Booking has been canceled", "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException ex) {
         ex.printStackTrace();
         JOptionPane.showMessageDialog(frame, "An error occurred while canceling the booking.", "Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   private void rescheduleBooking(String bookingID, String userID, String lecID, String day, String date) {
      File rejectedFile = new File("data/booking/rejected/rejected.txt");
      File tempRejectedFile = new File("data/booking/rejected/tempRejected.txt");
      File pendingFile = new File("data/booking/pending/bookingDetails.txt");
      File historyFile = new File("data/booking/history.txt");
      File availabilityFile = new File("data/booking/availability.txt");
      File tempAvailabilityFile = new File("data/booking/tempAvailability.txt");

      try (BufferedReader rejectedReader = new BufferedReader(new FileReader(rejectedFile));
      BufferedWriter tempRejectedWriter = new BufferedWriter(new FileWriter(tempRejectedFile));
      BufferedWriter pendingWriter = new BufferedWriter(new FileWriter(pendingFile, true));
      BufferedWriter historyWriter = new BufferedWriter(new FileWriter(historyFile, true));
      BufferedReader availabilityReader = new BufferedReader(new FileReader(availabilityFile));
      BufferedWriter tempAvailabilityWriter = new BufferedWriter(new FileWriter(tempAvailabilityFile))) {

         String rejectedLine;
         boolean bookingFound = false;

         while ((rejectedLine = rejectedReader.readLine()) != null) {
            String[] data = rejectedLine.split(",");

            if (data[0].equals(bookingID) && data[1].equals(userID) && data[2].equals(lecID) && data[3].equals(day) && data[4].equals(date)) {
               String rescheduledBooking = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + "," + data[6] + "," + data[7];
               pendingWriter.write(rescheduledBooking);
               pendingWriter.newLine();

               String insertRejected = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5] + "," + data[6] + "," + "Rejected";
               historyWriter.write(insertRejected);
               historyWriter.newLine();
               JOptionPane.showMessageDialog(frame, "Booking has been rescheduled", "Success", JOptionPane.INFORMATION_MESSAGE);
               bookingFound = true;
            } else {
               tempRejectedWriter.write(rejectedLine);
               tempRejectedWriter.newLine();
            }
         }

         if (!bookingFound) {
            System.out.println("Booking ID not found in rejected file.");
            return;
         }

         String availabilityLine;
         while ((availabilityLine = availabilityReader.readLine()) != null) {
            String[] availabilityData = availabilityLine.split(",");
            if (availabilityData[0].equals(lecID) && availabilityData[1].equals(day) && availabilityData[2].equals(date)) {
               tempAvailabilityWriter.write(availabilityData[0] + "," + availabilityData[1] + "," + availabilityData[2] + "," + availabilityData[3] + "," + availabilityData[4]);
               tempAvailabilityWriter.newLine();
            }
         }

         if (!rejectedFile.delete() || !tempRejectedFile.renameTo(rejectedFile)) {
            System.out.println("Error updating rejected file.");
         }
         if (!availabilityFile.delete() || !tempAvailabilityFile.renameTo(availabilityFile)) {
            System.out.println("Error updating availability file.");
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
