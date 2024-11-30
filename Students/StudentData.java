package Students;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class StudentData {

   HashMap<String, String> studentInfo = new HashMap<String, String>();

   public StudentData(String filePath) {
      loadLoginInfo(filePath);
   }

   public void loadLoginInfo(String filePath) {
      try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 3) {
               String username = parts[1].trim();
               String password = parts[2].trim();
               studentInfo.put(username, password);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public HashMap<String, String> getLoginInfo() {
      return studentInfo;
   }
}
