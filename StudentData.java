import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class StudentData {

   HashMap<String, String> studentInfo = new HashMap<String, String>();

   public StudentData(String filePath) {
      loadLoginInfo(filePath);
   }

   private void loadLoginInfo(String filePath) {
      try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
               String username = parts[0].trim();
               String password = parts[1].trim();
               studentInfo.put(username, password);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   protected HashMap<String, String> getLoginInfo() {
      return studentInfo;
   }
}
