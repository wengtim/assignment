package Lecturer.fileUtils;

import java.io.*;
import java.util.*;

public class RejectsBooking {
   String rejectedFile = "data/booking/rejected/rejected.txt";
   String moveBooking = null;

   List<String> updatedLines = new ArrayList<>();

   public void rejectBooking(String filePath, String bookingID) {
      File tempFile = new File("data/booking/pending/tempBookingDetails.txt");
      File originalFile = new File(filePath);
      File rejectedBookings = new File(rejectedFile);

      try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
      BufferedWriter rejectedWriter = new BufferedWriter(new FileWriter(rejectedBookings, true))) {

         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(bookingID)) {
               rejectedWriter.write(line);
               rejectedWriter.newLine();
               continue;
            }
            writer.write(line);
            writer.newLine();
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      if (originalFile.delete()) {
         tempFile.renameTo(originalFile);
      }
   }
}
