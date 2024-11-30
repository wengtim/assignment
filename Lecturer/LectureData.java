package Lecturer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class LectureData {

   HashMap<String, String> lectureInfo = new HashMap<String, String>();

   public LectureData(String filePath) {
      loadLoginInfo(filePath);
   }

   public void loadLoginInfo(String filePath) {
      try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 4) {
               String username = parts[1].trim();
               String password = parts[2].trim();
               lectureInfo.put(username, password);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public HashMap<String, String> getLoginInfo() {
      return lectureInfo;
   }
}
