package Lecturer.option;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import Lecturer.*;
import Lecturer.fileUtils.*;
import Lecturer.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import com.github.lgooddatepicker.components.*;

public class AddTime implements ActionListener {

   private boolean isHovered = false;

   Color color = new Color(0xB3EBF2);
   JFrame frame = new JFrame("Add Time Slots");
   ReadFile readFile = new ReadFile();
   Font font = new Font("JetBrains Mono", Font.PLAIN, 13);

   DatePicker datePicker;
   TimePicker startTimePicker;
   TimePicker endTimePicker;
   String lecID;
   String lecName;
   HashMap<String, String> loginInfoOriginal;

   public AddTime(String lecID, String day, String date, String startTime, String endTime, String lecName, HashMap<String, String>loginInfoOriginal) {
      this.lecID = lecID;
      this.lecName = lecName;
      this.loginInfoOriginal = loginInfoOriginal;

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.setSize(500, 400);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);

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

      JLabel dateLabel = new JLabel("Date: ");
      dateLabel.setBounds(130, 35, 100, 100);
      dateLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));

      datePicker = new DatePicker();
      datePicker.getComponentDateTextField().setFont(font);
      datePicker.setOpaque(false);
      datePicker.setBackground(color);
      datePicker.setFocusable(false);
      datePicker.setBounds(210, 73, 230, 30);


      JLabel startLabel = new JLabel("Start Time: ");
      startLabel.setBounds(75, 140, 150, 30);
      startLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));

      startTimePicker = new TimePicker();
      startTimePicker.setOpaque(false);
      startTimePicker.setBackground(color);
      startTimePicker.getComponentTimeTextField().setFont(font);
      startTimePicker.setFocusable(false);
      startTimePicker.setBounds(210, 143, 225, 30);

      JLabel endLabel = new JLabel("End Time: ");
      endLabel.setBounds(83, 200, 150, 30);
      endLabel.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));

      endTimePicker = new TimePicker();
      endTimePicker.setOpaque(false);
      endTimePicker.setBackground(color);
      endTimePicker.getComponentTimeTextField().setFont(font);
      endTimePicker.setFocusable(false);
      endTimePicker.setBounds(210, 203, 225, 30);

      JButton submitButton = new JButton("Add Time");
      submitButton.setBounds(200, 270, 150, 50);
      submitButton.setFont(font);
      submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      submitButton.addActionListener(this);

      frame.add(dateLabel);
      frame.add(datePicker);
      frame.add(startLabel);
      frame.add(startTimePicker);
      frame.add(endLabel);
      frame.add(endTimePicker);
      frame.add(submitButton);
      frame.add(backPanel);
      frame.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      LocalDate selectedDate = datePicker.getDate();
      LocalTime startTime = startTimePicker.getTime();
      LocalTime endTime = endTimePicker.getTime();

      if (selectedDate == null || startTime == null || endTime == null) {
         JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
         return;
      }

      String day = selectedDate.getDayOfWeek().toString().toLowerCase();
      day = day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();
      String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));
      String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));

      String lineToWrite = lecID + "," + day + "," + formattedDate + "," + formattedStartTime + "," + formattedEndTime;

      try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/booking/availability.txt", true))) {
         writer.write(lineToWrite);
         writer.newLine();
         JOptionPane.showMessageDialog(frame, "Time slot added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException ioException) {
         JOptionPane.showMessageDialog(frame, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
         ioException.printStackTrace();
      }
      frame.dispose();
      ManageTime manageTime = new ManageTime(lecID, lecName, loginInfoOriginal);
   }
}
