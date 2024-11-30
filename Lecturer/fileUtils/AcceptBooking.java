package Lecturer.fileUtils;

import java.io.*;
import java.util.*;

public class AcceptBooking {
   String acceptedFile = "data/booking/accepted/accepted.txt";
   String moveBooking = null;

   List<String> updatedLines = new ArrayList<>();

   public void acceptBooking(String filePath, String bookingID) {
      File tempFile = new File("data/booking/pending/tempBookingDetails.txt");
      File originalFile = new File(filePath);
      File acceptedBookings = new File(acceptedFile);

      try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
      BufferedWriter acceptWriter = new BufferedWriter(new FileWriter(acceptedBookings, true))) {

         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(bookingID)) {
               acceptWriter.write(line);
               acceptWriter.newLine();
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
