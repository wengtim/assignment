package Handler;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import java.util.HashMap;
import javax.swing.*;

// Check if booking is complete and ask for feedback

public class CheckBooking {

   File acceptedFile = new File("data/booking/accepted/accepted.txt");
   File feedBackFile = new File("data/feedback/feedback.txt");


   public CheckBooking(String bookingID, String userID, String lecID, String date, HashMap<String, String> loginInfoOriginal){

      try (BufferedReader reader = new BufferedReader(new FileReader(acceptedFile))) {
         String line;

         while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            if (data.length == 8 && data[0].equals(bookingID) && data[1].equals(userID) && data[2].equals(lecID) && data[3].equals(date)) {
               String bookingNo = data[0];
               String studentID = data[1];
               String lecturerID = data[2];
               String day = data[3];
               String userDate = data[4];
               String startTime = data[5];
               String endTime = data[6];
               String status = "Completed";

               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
               DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
               LocalDate bookingDate = LocalDate.parse(userDate, dateFormatter);
               LocalTime bookingEndTime = LocalTime.parse(endTime, timeFormatter);
               LocalDateTime bookingEndTimeLocal = LocalDateTime.of(bookingDate, bookingEndTime);

               if (LocalDateTime.now().isAfter(bookingEndTimeLocal)) {
                  // Booking is complete, ask for feedback
                  String feedback = "nice";
                  saveFeedback(bookingNo, studentID, lecID, day, date, startTime, endTime, status, feedback);
               }
            }
         }
         reader.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void saveFeedback(String bookingID, String studentID, String lecID, String day, String date, String startTime, String endTime, String status, String feedback) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(feedBackFile, true))) {
         writer.write(bookingID + "," + studentID + "," + lecID + "," + day + "," + date + "," + startTime + "," + endTime + "," + status + "," + feedback);
         writer.newLine();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
