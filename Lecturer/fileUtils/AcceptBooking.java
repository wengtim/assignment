package Lecturer.fileUtils;

import java.io.*;
import java.util.*;

public class AcceptBooking {
   String acceptedBooking = "data/booking/accepted/accepted.txt";
   String availabilityFile = "data/booking/availability.txt";

   public void acceptBooking(String filePath, String bookingID) {
      File tempFile = new File("data/booking/pending/tempBookingDetails.txt");
      File originalFile = new File(filePath);
      File acceptedBookings = new File(acceptedBooking);
      File availability = new File(availabilityFile);

      try (
      BufferedReader reader = new BufferedReader(new FileReader(originalFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
      BufferedWriter acceptedWriter = new BufferedWriter(new FileWriter(acceptedBookings, true));
      BufferedWriter availabilityWriter = new BufferedWriter(new FileWriter(availability, true))
   ) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(bookingID)) {
               acceptedWriter.write(line);
               acceptedWriter.newLine();
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
