package Students.Schedule;

import Students.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class SchedulePage {
   private boolean isHovered = false;

   String userID;
   HashMap<String, String> loginInfoOriginal;
   Font font = new Font(null, Font.PLAIN, 13);

   File rejectedFile = new File("data/booking/rejected/rejected.txt");
   File acceptedFile = new File("data/booking/accepted/accepted.txt");

   JFrame frame = new JFrame("Schedule");
   JLabel loggedInfo = new JLabel();

   public SchedulePage(String name, String userID, HashMap<String, String> loginInfoOriginal) {
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
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/accepted/accepted.txt"));
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
               String status = "Accepted";

               if (studentID.equals(userID)) {
                  JPanel schedulePanel = createSchedulePanel(bookingID, studentID, lecID, date, startTime, endTime, status);
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
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = "Rejected";

               if (studentID.equals(userID)) {
                  JPanel schedulePanel = createSchedulePanel(bookingID, studentID, lecID, date, startTime, endTime, status);
                  contentPanel.add(schedulePanel);
               }
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      showPendingBookings(contentPanel);
      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      mainPanel.add(scrollPane);

      frame.add(mainPanel);
      frame.add(backPanel);
      frame.add(loggedInfo);
      frame.setVisible(true);
   }

   private JPanel createSchedulePanel(String bookingID, String studentID, String lecID, String date, String startTime, String endTime, String status) {

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
      });

      buttonPanel.add(rescheduleBut);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      buttonPanel.add(Box.createVerticalGlue());

      panel.add(buttonPanel, BorderLayout.EAST);

      return panel;
   }

   public void showPendingBookings(JPanel contentPanel) {
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
                  });

                  JButton rescheduleButton = new JButton("Reschedule");
                  rescheduleButton.setFocusable(false);
                  rescheduleButton.setPreferredSize(new Dimension(100, 45));
                  rescheduleButton.setFont(new Font("Poppins", Font.BOLD, 14));
                  rescheduleButton.setBackground(new Color(0xfce1c5));
                  rescheduleButton.addActionListener(e -> {
                  });

                  //buttonPanel.add(Box.createVerticalGlue());
                  //buttonPanel.add(rescheduleButton);
                  buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                  buttonPanel.add(cancelButton);
                  buttonPanel.add(Box.createVerticalGlue());

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
}
