package Lecturer.fileUtils;

import java.io.*;
import java.util.*;

public class AcceptBooking {
   String rejectedFile = "data/booking/rejected/rejected.txt";
   String availabilityFile = "data/booking/availability.txt";

   public void acceptBooking(String filePath, String bookingID) {
      File tempFile = new File("data/booking/pending/tempBookingDetails.txt");
      File originalFile = new File(filePath);
      File rejectedBookings = new File(rejectedFile);
      File availability = new File(availabilityFile);

      try (
      BufferedReader reader = new BufferedReader(new FileReader(originalFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
      BufferedWriter rejectedWriter = new BufferedWriter(new FileWriter(rejectedBookings, true));
      BufferedWriter availabilityWriter = new BufferedWriter(new FileWriter(availability, true))
   ) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(bookingID)) {
               rejectedWriter.write(line);
               rejectedWriter.newLine();
               if (data.length >= 8) {
                  String lecID = data[2];
                  String day = data[3];
                  String date = data[4];
                  String startTime = data[5];
                  String endTime = data[6];
                  availabilityWriter.write(lecID + "," + day + "," + date + "," + startTime + "," + endTime);
                  availabilityWriter.newLine();
               }
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
