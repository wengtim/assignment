import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class HomePage implements ActionListener, ListSelectionListener {

   public static void timesleep(int time) {
      try {
         Thread.sleep(time);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   Color color = new Color(0xf9f7f0);

   JFrame frame = new JFrame("Home");
   JLabel loggedInfo = new JLabel();
   JLabel message = new JLabel();
   JButton logoutButton = new JButton("Log Out");
   JPanel searchPanel = new JPanel();
   JLabel searchLabel = new JLabel("Search: ");
   JTextField searchField = new JTextField();
   DefaultListModel<String> listModel = new DefaultListModel<>();
   JList<String> searchList = new JList<>(listModel);


   public HomePage(String userID) {

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(985, 555);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(color);

      searchLabel.setBounds(10, 0, 75, 35);
      searchLabel.setFont(new Font(null, Font.PLAIN, 13));

      searchField.setBounds(65, 8, 200, 20);
      searchField.setFont(new Font(null, Font.PLAIN, 13));

      message.setBounds(200, 0, 400, 35);
      message.setFont(new Font(null, Font.PLAIN, 13));

      loggedInfo.setBounds(825, 500, 233, 21);
      loggedInfo.setFont(new Font(null, Font.PLAIN, 13));
      loggedInfo.setText("<html>Logged in as: <span style='color:#57915d;'>" + userID + "</span></html>");

      logoutButton.setBounds(900, 10, 75, 35);
      logoutButton.setFocusable(false);
      logoutButton.addActionListener(this);

      searchPanel.setBounds(585, 10, 285, 100);
      searchPanel.setBackground(color);
      searchPanel.setLayout(null);

      listModel.addElement("item 1");
      listModel.addElement("item 2");
      listModel.addElement("item 3");
      listModel.addElement("item 4");

      searchList.setBounds(68, 28, 193, 70);
      searchList.setBackground(color);
      searchList.setFont(new Font(null, Font.PLAIN, 13));
      searchList.addListSelectionListener(this);
      searchList.setVisible(false);

      searchPanel.add(searchLabel);
      searchPanel.add(searchField);
      searchPanel.add(searchList);
      frame.add(searchPanel);
      frame.add(loggedInfo);
      frame.add(logoutButton);

      frame.setVisible(true);
   }

   @Override
   public void valueChanged(ListSelectionEvent e) {
      if (e.getValueIsAdjusting()) {
         String selectedValue = searchList.getSelectedValue();
         searchField.setText(selectedValue);
         searchList.setVisible(false);
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == logoutButton) {
         frame.dispose();
         LoginPage loginPage = new LoginPage(new HashMap<String, String>());
      }
   }


}
