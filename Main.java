public class Main {
   public static void main(String[] args) {
      IDandPassword idandpassword = new IDandPassword("data/logininfo.txt");
      LoginPage loginPage = new LoginPage(idandpassword.getLoginInfo());
   }
}
