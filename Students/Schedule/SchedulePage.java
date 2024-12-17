package Students.Schedule;

import Students.*;
import Students.Consultation.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.time.*;
import java.time.format.DateTimeFormatter;
import com.github.lgooddatepicker.components.*;

public class SchedulePage {

   private boolean isHovered = false;
   private boolean hasBooking = false;

   Color color = new Color(0xB3EBF2);
   String userID;
   String studentName;

   HashMap<String, String> loginInfoOriginal;
   Font font = new Font(null, Font.PLAIN, 13);

   File rejectedFile = new File("data/booking/rejected/rejected.txt");
   File acceptedFile = new File("data/booking/accepted/accepted.txt");

   JFrame frame = new JFrame("Schedule");
   JLabel loggedInfo = new JLabel();
   JPanel contentPanel = new JPanel();
   JScrollPane scrollPane = new JScrollPane(contentPanel);
   DatePicker datePicker = new DatePicker();
   TimePicker startTimePicker = new TimePicker();
   TimePicker endTimePicker = new TimePicker();

   public SchedulePage(String studentName, String userID, HashMap<String, String> loginInfoOriginal) {
      this.userID = userID;
      this.loginInfoOriginal = loginInfoOriginal;
      this.studentName = studentName;

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

      contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
      contentPanel.setBackground(color);

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
      panel.setBackground(color);

      JPanel detailsPanel = new JPanel();
      detailsPanel.setLayout(new GridLayout(2, 1));
      detailsPanel.setBackground(color);

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
      buttonPanel.setBackground(color);
      buttonPanel.setPreferredSize(new Dimension(100, 100));

      JButton rescheduleBut = new JButton("Reschedule");
      rescheduleBut.setFocusable(false);
      rescheduleBut.setPreferredSize(new Dimension(100, 45));
      rescheduleBut.setFont(new Font("Poppins", Font.BOLD, 14));
      rescheduleBut.setBackground(color);
      rescheduleBut.addActionListener(e -> {
         frame.dispose();
         new Booking(lecName, lecID, userID, loginInfoOriginal);
         removeRejected(lecID, userID, day, date, startTime, endTime);
      });

      if (status.equals("Rejected") || status.equals("Pending")){
         buttonPanel.add(rescheduleBut);
         buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
         buttonPanel.add(Box.createVerticalGlue());

         panel.add(buttonPanel, BorderLayout.EAST);
      } else {
         JPanel emptyPanel = new JPanel();
         emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
         emptyPanel.setBackground(color);
         emptyPanel.setPreferredSize(new Dimension(100, 100));

         panel.add(emptyPanel, BorderLayout.EAST);
      }

      return panel;
   }

   private void removeRejected(String lecID, String studentID, String day, String date, String startTime, String endTime) {
      File filePath = new File("data/booking/rejected/rejected.txt");
      File tempFile = new File("data/booking/rejected/tempRejected.txt");

      try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7 && data[1].equals(studentID) && data[2].equals(lecID) && data[3].equals(day) && data[4].equals(date) && data[5].equals(startTime) && data[6].equals(endTime)) {
               continue;
            }
            writer.write(line);
            writer.newLine();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      if (filePath.delete()) {
         tempFile.renameTo(filePath);
      }
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
                  LocalDate bookingDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                  LocalTime bookingStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));

                  LocalDate currentDate = LocalDate.now();
                  LocalTime currentTime = LocalTime.now();

                  if (bookingDate.isBefore(currentDate) || (bookingDate.isEqual(currentDate) && bookingStartTime.isBefore(currentTime))) {
                     continue;
                  }


                  String lecName = getLecName(lecID);

                  JPanel panel = new JPanel();
                  panel.setLayout(new BorderLayout());
                  panel.setPreferredSize(new Dimension(750, 100));
                  panel.setMaximumSize(new Dimension(750, 100));
                  panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
                  panel.setBackground(color);

                  JPanel detailsPanel = new JPanel();
                  detailsPanel.setLayout(new GridLayout(2, 1));
                  detailsPanel.setBackground(color);

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
                  buttonPanel.setBackground(color);
                  buttonPanel.setPreferredSize(new Dimension(100, 100));

                  JButton cancelButton = new JButton("Cancel");
                  cancelButton.setFocusable(false);
                  cancelButton.setPreferredSize(new Dimension(100, 45));
                  cancelButton.setFont(new Font("Poppins", Font.BOLD, 14));
                  cancelButton.setBackground(new Color(0xfce1c5));
                  cancelButton.addActionListener(e -> {
                     cancelBooking(bookingID);
                     refreshPanel(contentPanel, scrollPane);
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

   private void refreshPanel(JPanel contentPanel, JScrollPane scrollPane) {
      contentPanel.removeAll();
      contentPanel.revalidate();
      contentPanel.repaint();

      showPendingBookings(contentPanel, scrollPane);

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

      contentPanel.revalidate();
      contentPanel.repaint();
   }
}
