import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminPage implements ActionListener {

   Color color = new Color(0xf9f7f0);

   String[] columns = { "Username", "Password" };
   Object[][] data = getData();

   JFrame frame = new JFrame("Admin");

   JMenuBar menuBar = new JMenuBar();
   JMenu mode = new JMenu("Mode");
   JMenuItem viewMenuItem = new JMenuItem("View");
   JMenuItem addMenuItem = new JMenuItem("Add");
   JMenuItem deleteMenuItem = new JMenuItem("Delete");
   JMenuItem updateMenuItem = new JMenuItem("Update");
   JMenuItem logoutMenuItem = new JMenuItem("Log out");

   JButton addButton = new JButton("Add");
   JButton deleteButton = new JButton("Delete");
   JButton updateButton = new JButton("Update");

   JLabel usernameLabel = new JLabel("Username: ");
   JLabel passwordLabel = new JLabel("Password: ");
   JTextField usernameField = new JTextField();
   JPasswordField passwordField = new JPasswordField();

   JLabel modeStatus = new JLabel();

   JTable table;
   JScrollPane scrollPane;

   HashMap<String, String> logininfo = new HashMap<>();

   public AdminPage() {

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 580);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);

      addButton.setBounds(145, 300, 100, 40);
      addButton.setFocusable(false);
      addButton.addActionListener(this);
      addButton.setVisible(false);

      deleteButton.setBounds(145, 300, 100, 40);
      deleteButton.setFocusable(false);
      deleteButton.addActionListener(this);
      deleteButton.setVisible(false);

      updateButton.setBounds(145, 300, 100, 40);
      updateButton.setFocusable(false);
      updateButton.addActionListener(this);
      updateButton.setVisible(false);

      usernameLabel.setBounds(35, 130, 100, 40);
      passwordLabel.setBounds(35, 190, 100, 40);

      usernameField.setBounds(110, 130, 200, 40);
      passwordField.setBounds(110, 190, 200, 40);

      menuBar.add(mode);
      mode.add(viewMenuItem);
      mode.add(addMenuItem);
      mode.add(deleteMenuItem);
      mode.add(updateMenuItem);
      mode.add(logoutMenuItem);

      viewMenuItem.addActionListener(this);
      addMenuItem.addActionListener(this);
      deleteMenuItem.addActionListener(this);
      updateMenuItem.addActionListener(this);
      logoutMenuItem.addActionListener(this);

      table = new JTable(data, columns);
      scrollPane = new JScrollPane(table);
      scrollPane.setBounds(372, 14, 600, 500);

      modeStatus.setBounds(15, 480, 200, 40);
      modeStatus.setFont(new Font(null, Font.ITALIC, 16));
      modeStatus.setText("<html>Mode: <span style='color:#ad6853;'>View Mode</span></html>");

      frame.setJMenuBar(menuBar);
      frame.add(modeStatus);
      frame.add(usernameLabel);
      frame.add(passwordLabel);
      frame.add(usernameField);
      frame.add(passwordField);
      frame.add(addButton);
      frame.add(deleteButton);
      frame.add(updateButton);
      frame.add(scrollPane);
      frame.setVisible(true);
   }

   private Object[][] getData() {
      ArrayList<Object[]> dataList = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(new FileReader("data/logininfo.txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
               dataList.add(new Object[] { parts[0], parts[1] });
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

      Object[][] dataArray = new Object[dataList.size()][2];
      for (int i = 0; i < dataList.size(); i++) {
         dataArray[i] = dataList.get(i);
      }

      return dataArray;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == logoutMenuItem) {
         frame.dispose();
         LoginPage loginPage = new LoginPage(new HashMap<String, String>());
      }

      if (e.getSource() == viewMenuItem) {
         addButton.setVisible(false);
         deleteButton.setVisible(false);
         updateButton.setVisible(false);
         modeStatus.setText("<html>Mode: <span style='color:#ad6853;'>View Mode</span></html>");
      }

      if (e.getSource() == addMenuItem) {
         addButton.setVisible(true);
         deleteButton.setVisible(false);
         updateButton.setVisible(false);
         modeStatus.setText("<html>Mode: <span style='color:#ad6853;'>Add Mode</span></html>");
      }

      if (e.getSource() == deleteMenuItem) {
         addButton.setVisible(false);
         deleteButton.setVisible(true);
         updateButton.setVisible(false);
         modeStatus.setText("<html>Mode: <span style='color:#ad6853;'>Delete Mode</span></html>");
      }

      if (e.getSource() == updateMenuItem) {
         addButton.setVisible(false);
         deleteButton.setVisible(false);
         updateButton.setVisible(true);
         modeStatus.setText("<html>Mode: <span style='color:#ad6853;'>Update Mode</span></html>");
      }

      if (e.getSource() == addButton) {
      }

      if (e.getSource() == deleteButton) {
      }

      if (e.getSource() == updateButton) {
      }
   }
}
