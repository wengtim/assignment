import java.util.HashMap;

public class IDandPassword {

   HashMap<String, String> logininfo = new HashMap<String, String>();

   public IDandPassword() {
      logininfo.put("wengtim", "nice");
      logininfo.put("brocode", "anothernice");
   }

   protected HashMap<String, String> getLoginInfo() {
      return logininfo;
   }
}
