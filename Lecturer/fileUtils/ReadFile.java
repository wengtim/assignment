package Lecturer.fileUtils;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class ReadFile {

   public List<String> ViewPendingLecturer(String lecID, String filePath) {
      List<String> pendingDetails = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
               String studentID = parts[0].trim();
               String date = parts[2].trim();
               String startTime = parts[3].trim();
               String endTime = parts[4].trim();
               String status = parts[5].trim();

               if ("Pending".equalsIgnoreCase(status) && parts[1].trim().equals(lecID)) {
                  pendingDetails.add(studentID + "," + date + "," + startTime + "," + endTime + "," + status);
               }
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return pendingDetails;
   }

   public void addTimeSlots(String lecID, String day, String date, String startTime, String endTime) {
      String filePath = "data/booking/availability.txt";
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
         String timeSlotData = lecID + "," + day + "," + date + "," + startTime + "," + endTime + "\n";
         writer.write(timeSlotData);
         JOptionPane.showMessageDialog(null, "New time slot added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(null, "An error occurred while saving the time slot.", "Error", JOptionPane.ERROR_MESSAGE);
      }
   }
}
