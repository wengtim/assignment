package Lecturer.option;

import java.util.*;
import java.util.HashMap;
import Lecturer.*;
import Lecturer.fileUtils.*;
import Lecturer.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class EditTime {
   private boolean isHovered = false;

   JFrame frame = new JFrame("Edit Time Slots");

   JLabel dateLabel = new JLabel("Date: ");
   JTextField dateField = new JTextField();

   JLabel dayLabel = new JLabel("Day: ");
   JTextField dayField = new JTextField();

   JTextField startTimeField = new JTextField();
   JLabel startTimeLabel = new JLabel("Start Time: ");

   JLabel endTimeLabel = new JLabel("End Time: ");
   JTextField endTimeField = new JTextField();

   JButton saveButton = new JButton("Save");

   public EditTime(String lecID, String day, String date, String startTime, String endTime, String lecName, HashMap<String, String>loginInfoOriginal) {
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(new Color(0xf9f7f0));

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
            ManageTime manageTime = new ManageTime(lecID, lecName, loginInfoOriginal);
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

      dateLabel.setBounds(160, 15, 100, 100);
      dateLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      dateField.setBounds(270, 15, 200, 30);
      dateField.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      dateField.setText(date);

      dayLabel.setBounds(160, 100, 100, 100);
      dayLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      dayField.setBounds(270, 100, 200, 30);
      dayField.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      dayField.setText(day);

      startTimeLabel.setBounds(160, 185, 200, 100);
      startTimeLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      startTimeField.setBounds(270, 185, 200, 30);
      startTimeField.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      startTimeField.setText(startTime);

      endTimeLabel.setBounds(160, 270, 200, 100);
      endTimeLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      endTimeField.setBounds(270, 270, 200, 30);
      endTimeField.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      endTimeField.setText(endTime);

      saveButton.setBounds(270, 365, 100, 40);
      saveButton.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
      saveButton.setText("Save");
      saveButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String newDate = dateField.getText().trim();
            String newDay = dayField.getText().trim();
            String newStartTime = startTimeField.getText().trim();
            String newEndTime = endTimeField.getText().trim();

            if (newDate.isEmpty() || newDay.isEmpty() || newStartTime.isEmpty() || newEndTime.isEmpty()) {
               JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
               return;
            }

            File file = new File("data/booking/availability.txt");
            File tempFile = new File("data/booking/temp_availability.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

               String line;
               boolean isUpdated = false;

               while ((line = reader.readLine()) != null) {
                  String[] data = line.split(",");
                  if (data.length == 5 && data[0].equals(lecID) && data[1].equals(day) && data[2].equals(date)
                  && data[3].equals(startTime) && data[4].equals(endTime)) {
                     writer.write(lecID + "," + newDay + "," + newDate + "," + newStartTime + "," + newEndTime);
                     writer.newLine();
                     isUpdated = true;
                  } else {
                     writer.write(line);
                     writer.newLine();
                  }
               }

               if (isUpdated) {
                  JOptionPane.showMessageDialog(frame, "Time slot updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
               } else {
                  JOptionPane.showMessageDialog(frame, "Failed to update the time slot. Original entry not found.", "Error", JOptionPane.ERROR_MESSAGE);
               }

               if (!file.delete() || !tempFile.renameTo(file)) {
                  throw new IOException("Failed to update the file.");
               }

            } catch (IOException ioException) {
               JOptionPane.showMessageDialog(frame, "An error occurred while updating the time slot.", "Error", JOptionPane.ERROR_MESSAGE);
               ioException.printStackTrace();
            }
            frame.dispose();
            ManageTime manageTime = new ManageTime(lecID, lecName, loginInfoOriginal);
         }
      });

      frame.add(backPanel);
      frame.add(dateLabel);
      frame.add(dateField);
      frame.add(dayLabel);
      frame.add(dayField);
      frame.add(startTimeLabel);
      frame.add(startTimeField);
      frame.add(endTimeLabel);
      frame.add(endTimeField);
      frame.add(saveButton);
      frame.setVisible(true);
   }
}
