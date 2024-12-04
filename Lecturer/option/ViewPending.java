package Lecturer.option;

import Lecturer.fileUtils.*;
import Lecturer.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.awt.event.*;

public class ViewPending {

   private boolean isHovered = false;
   private boolean hasBookings = false;

   String filePath = "data/booking/pending/bookingDetails.txt";
   JFrame frame = new JFrame("Bookings Pending");
   JLabel loggedInfo = new JLabel();
   ReadFile readFile = new ReadFile();
   RejectsBooking rejectsBooking = new RejectsBooking();
   AcceptBooking acceptBooking = new AcceptBooking();
   JPanel contentPanel = new JPanel();

   Font font = new Font("JetBrains Mono", Font.PLAIN, 13);

   public ViewPending(String name, String lecID, HashMap<String, String> loginInfoOriginal) {

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
         BufferedReader reader = new BufferedReader(new FileReader("data/booking/pending/bookingDetails.txt"));
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 8 && data[2].equals(lecID)) {
               String bookingID = data[0];
               String userID = data[1];
               String lecturerID = data[2];
               String day = data[3];
               String date = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = data[7];
               String studentName = getStudentName(userID);

               JPanel showBooking = showBookingDetails(bookingID, userID, studentName, lecturerID, date, startTime, endTime, status, lecID);
               contentPanel.add(showBooking);

               hasBookings = true;
            }
         }
         if (!hasBookings) {
            JLabel noBookingLabel = new JLabel("<html><h2>No Booking Pending</h2></html>");
            noBookingLabel.setFont(new Font("Poppins", Font.BOLD, 23));
            noBookingLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(noBookingLabel);
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      JScrollPane scrollPane = new JScrollPane(contentPanel);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      mainPanel.add(scrollPane);
      frame.add(backPanel);
      frame.add(mainPanel);
      frame.add(loggedInfo);

      frame.setVisible(true);
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

   private JPanel showBookingDetails(String bookingID, String userID, String studentName, String lecturerID, String date, String startTime, String endTime, String status, String lecID) {
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

      JLabel nameLabel = new JLabel("<html>Student Name: <span style='color:#636261;'>" + studentName + "</span></html>");
      nameLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel dateLabel = new JLabel("<html>Booking Date: <span style='color:#636261;'>" + date + "</span></html>");
      dateLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel timeLabel = new JLabel("<html>Booking Time: <span style='color:#636261;'>" + startTime + "-" + endTime + "</span></html>");
      timeLabel.setFont(new Font("Poppins", Font.BOLD, 15));

      JLabel statusLabel = new JLabel("<html>Status: <span style='color:#57915d;'>" + status + "</span></html>");
      statusLabel.setFont(new Font("Poppins", Font.PLAIN, 15));

      detailsPanel.add(nameLabel);
      detailsPanel.add(statusLabel);
      detailsPanel.add(dateLabel);
      detailsPanel.add(timeLabel);
      panel.add(detailsPanel, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setBackground(new Color(0xf9f7f0));
      buttonPanel.setPreferredSize(new Dimension(100, 100));

      JButton rejectButton = new JButton("Reject");
      rejectButton.setFocusable(false);
      rejectButton.setPreferredSize(new Dimension(100, 45));
      rejectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      rejectButton.setFont(new Font("Poppins", Font.BOLD, 14));
      rejectButton.setBackground(new Color(0xfce1c5));
      rejectButton.addActionListener(e -> {
         rejectsBooking.rejectBooking(filePath, bookingID);
         JOptionPane.showMessageDialog(frame, "Booking has been rejected", "Success", JOptionPane.INFORMATION_MESSAGE);
         refreshBookingPage(lecID);
      });

      JButton acceptButton = new JButton("Accept");
      acceptButton.setFocusable(false);
      acceptButton.setPreferredSize(new Dimension(100, 45));
      acceptButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      acceptButton.setFont(new Font("Poppins", Font.BOLD, 14));
      acceptButton.setBackground(new Color(0xfce1c5));
      acceptButton.addActionListener(e -> {
         acceptBooking.acceptBooking(filePath, bookingID);
         JOptionPane.showMessageDialog(frame, "Booking has been accepted", "Success", JOptionPane.INFORMATION_MESSAGE);
         refreshBookingPage(lecID);
      });

      buttonPanel.add(Box.createVerticalGlue());
      buttonPanel.add(acceptButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
      buttonPanel.add(rejectButton);
      buttonPanel.add(Box.createVerticalGlue());

      panel.add(buttonPanel, BorderLayout.EAST);

      return panel;
   }

   private void refreshBookingPage(String lecID) {
      contentPanel.removeAll();

      SwingUtilities.invokeLater(() -> {
         boolean hasBookings = false;

         try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
               String[] data = line.split(",");
               if (data.length != 8) {
                  System.err.println("Invalid data line: " + line);
                  continue;
               }

               if (data[2].equals(lecID)) {
                  String bookingID = data[0];
                  String userID = data[1];
                  String lecturerID = data[2];
                  String day = data[3];
                  String date = data[4];
                  String startTime = data[5];
                  String endTime = data[6];
                  String status = data[7];
                  String studentName = getStudentName(userID);

                  JPanel showBooking = showBookingDetails(bookingID, userID, studentName, lecturerID, date, startTime, endTime, status, lecID);
                  contentPanel.add(showBooking);

                  hasBookings = true;
               }
            }

            if (!hasBookings) {
               JLabel noBookingLabel = new JLabel("<html><h2>No Booking Pending</h2></html>");
               noBookingLabel.setFont(new Font("Poppins", Font.BOLD, 23));
               noBookingLabel.setHorizontalAlignment(SwingConstants.CENTER);
               contentPanel.add(noBookingLabel);
            }

            JScrollPane scrollPane = findScrollPane(contentPanel);
            if (scrollPane != null) {
               scrollPane.revalidate();
               scrollPane.repaint();
            }
         } catch (IOException e) {
            JOptionPane.showMessageDialog(contentPanel, "Error reading booking file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
         }
      });
   }

   private JScrollPane findScrollPane(JPanel panel) {
      Container parent = panel.getParent();
      while (parent != null) {
         if (parent instanceof JScrollPane) {
            return (JScrollPane) parent;
         }
         parent = parent.getParent();
      }
      return null;
   }
}
