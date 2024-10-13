public class Main {
   public static void main(String[] args) {
      IDandPassword idandpassword = new IDandPassword();
      LoginPage loginPage = new LoginPage(idandpassword.getLoginInfo());
   }
}
