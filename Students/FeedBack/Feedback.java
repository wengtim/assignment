package Students.FeedBack;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.*;
import Students.*;

public class Feedback {

   private boolean isHovered = false;

   Color color = new Color(0xB3EBF2);
   String studentName;
   String userID;
   HashMap<String, String> loginInfoOriginal;

   JFrame frame = new JFrame("Feedback");
   JLabel loggedInfo = new JLabel();
   Font font = new Font(null, Font.PLAIN, 13);

   public Feedback(String name, String userID, HashMap<String, String> loginInfoOriginal) {
      this.studentName = name;
      this.userID = userID;
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
      contentPanel.setBackground(color);

      showCompletedBookings(contentPanel);

      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      mainPanel.add(scrollPane);

      frame.add(mainPanel);
      frame.add(backPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);
   }

   private void showCompletedBookings(JPanel contentPanel) {
      try (BufferedReader reader = new BufferedReader(new FileReader("data/booking/accepted/accepted.txt"))) {
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

               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
               LocalDate bookingDate = LocalDate.parse(date, dateFormatter);
               LocalTime bookingEndTime = LocalTime.parse(endTime, timeFormatter);
               LocalDateTime bookingEndTimeLocal = LocalDateTime.of(bookingDate, bookingEndTime);

               if (studentID.equals(userID) && LocalDateTime.now().isAfter(bookingEndTimeLocal)) {
                  JPanel schedulePanel = createFeedbackPanel(bookingID, studentID, lecID, day, date, startTime, endTime);
                  contentPanel.add(schedulePanel);
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private JPanel createFeedbackPanel(String bookingID, String userID, String lecID, String day, String date, String startTime, String endTime) {
      String lecName = getLecName(lecID);
      String status = "Completed";

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

      JLabel lecturerName = new JLabel("<html>Lecturer Name: <span style='color:#636261;'>" + lecName + "</span></html>");
      lecturerName.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel dayLabel = new JLabel("<html>Day: <span style='color:#636261;'>" + day + "</span></html>");
      dayLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel dateLabel = new JLabel("<html>Booking Date: <span style='color:#636261;'>" + date + "</span></html>");
      dateLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel timeLabel = new JLabel("<html>Booking Time: <span style='color:#636261;'>" + startTime + " - " + endTime + "</span></html>");
      timeLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      JLabel statusLabel = new JLabel("<html>Status: <span style='color:#5c9665;'>" + status + "</span></html>");
      statusLabel.setFont(new Font("Poppins", Font.BOLD, 17));

      detailsPanel.add(lecturerName);
      detailsPanel.add(dateLabel);
      detailsPanel.add(timeLabel);
      detailsPanel.add(dayLabel);
      detailsPanel.add(statusLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setBackground(color);

      JButton submitFeedback = new JButton("Submit Feedback");
      submitFeedback.setFocusable(false);
      submitFeedback.setPreferredSize(new Dimension(200, 50));
      submitFeedback.addActionListener(e -> {
         JTextArea textArea = new JTextArea(5, 30);
         JScrollPane scrollPane = new JScrollPane(textArea);

         int option = JOptionPane.showConfirmDialog(
            frame,
            scrollPane,
            "Enter your feedback:",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
         );

         String feedback = null;

         if (option == JOptionPane.OK_OPTION) {
            feedback = textArea.getText();
         }
         if (feedback != null && !feedback.isBlank()) {
            saveFeedback(bookingID, userID, lecID, day, date, startTime, endTime, status, feedback);
            JOptionPane.showMessageDialog(frame, "Feedback submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
         }
      });

      JButton viewFeedback = new JButton("View Feedback");
      viewFeedback.setFocusable(false);
      viewFeedback.setPreferredSize(new Dimension(200, 50));
      viewFeedback.addActionListener(e -> {
         String userFeedback = getUserFeedback(bookingID);
         String lecturerFeedback = getLecturerFeedback(bookingID);

         JDialog feedbackDialog = new JDialog(frame, "Details", true);
         feedbackDialog.setSize(400, 300);
         feedbackDialog.setBackground(color);
         feedbackDialog.setLayout(new BorderLayout());
         feedbackDialog.setLocationRelativeTo(frame);

         JTextArea feedbackArea = new JTextArea();
         feedbackArea.setEditable(false);
         feedbackArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
         feedbackArea.setText(
            "User Feedback:\n" +
         (userFeedback.isEmpty() ? "No feedback submitted yet." : userFeedback) +
            "\n\nLecturer Feedback:\n" +
         (lecturerFeedback.isEmpty() ? "No feedback from lecturer yet." : lecturerFeedback)
         );

         JScrollPane scrollPane = new JScrollPane(feedbackArea);
         feedbackDialog.add(scrollPane, BorderLayout.CENTER);

         JButton closeButton = new JButton("Close");
         closeButton.addActionListener(event -> feedbackDialog.dispose());

         JPanel buttonPanel2 = new JPanel();
         buttonPanel2.add(closeButton);

         feedbackDialog.add(buttonPanel2, BorderLayout.SOUTH);

         feedbackDialog.setVisible(true);
      });

      buttonPanel.add(submitFeedback);
      buttonPanel.add(viewFeedback);

      panel.add(buttonPanel, BorderLayout.EAST);

      return panel;
   }

   private String getUserFeedback(String bookingID) {
      try (BufferedReader reader = new BufferedReader(new FileReader("data/feedback/feedback.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            if (data.length >= 9 && data[0].equals(bookingID)) {
               return data[8];
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "";
   }

   private String getLecturerFeedback(String bookingID) {
      try (BufferedReader reader = new BufferedReader(new FileReader("data/feedback/lecFeedback.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            if (data.length >= 9 && data[0].equals(bookingID)) {
               return data[8];
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return "";
   }

   private void promptFeedback(String bookingID, String studentID, String lecID, String day, String date, String startTime, String endTime, String status) {
      String feedback = JOptionPane.showInputDialog(frame, "Please provide your feedback:");
      if (feedback != null && !feedback.trim().isEmpty()) {
         saveFeedback(bookingID, studentID, lecID, day, date, startTime, endTime, status, feedback);
         JOptionPane.showMessageDialog(frame, "Thank you for your feedback!", "Feedback Submitted", JOptionPane.INFORMATION_MESSAGE);
      } else {
         JOptionPane.showMessageDialog(frame, "Feedback cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   private void saveFeedback(String bookingID, String studentID, String lecID, String day, String date, String startTime, String endTime, String status, String feedback) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/feedback/feedback.txt", true))) {
         writer.write(bookingID + ";" + studentID + ";" + lecID + ";" + day + ";" + date + ";" + startTime + ";" + endTime + ";" + status + ";" + feedback);
         writer.newLine();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private boolean checkFeedbackSubmitted(String bookingID, String userID) {
      try (BufferedReader reader = new BufferedReader(new FileReader("data/feedback/feedback.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            if (data.length >= 2 && data[0].equals(bookingID) && data[1].equals(userID)) {
               return true;
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return false;
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

}
