package Handler;

import Lecturer.*;
import Students.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartPage implements ActionListener {
   JFrame frame = new JFrame("Student / Lecturer");
   JLabel label = new JLabel("Please select your role");
   JButton studentButton = new JButton("Student");
   JButton lecturerButton = new JButton("Lecturer");

   public StartPage() {

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 400);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(new Color(0xf9f7f0));

      label.setBounds(50, 50, 300, 30);
      label.setFont(new Font(null, Font.BOLD, 20));

      studentButton.setBounds(50, 100, 300, 42);
      studentButton.setFocusable(false);
      studentButton.addActionListener(this);

      lecturerButton.setBounds(50, 150, 300, 42);
      lecturerButton.setFocusable(false);
      lecturerButton.addActionListener(this);

      frame.add(label);
      frame.add(studentButton);
      frame.add(lecturerButton);
      frame.setVisible(true);
   }

   public void paintComponent(Graphics g) {
      ImageIcon image = new ImageIcon("image/person.png");
      g.drawImage(image.getImage(), 9, 9, 100, 100, null);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == studentButton) {
         frame.dispose();
         StudentData studentData = new StudentData("data/credentials/studentInfo.txt");
         StudentLogin studentLogin = new StudentLogin(studentData.getLoginInfo());
      }

      if (e.getSource() == lecturerButton) {
         frame.dispose();
         LectureData lectureData = new LectureData("data/credentials/lecturerInfo.txt");
         LectureLogin lectureLogin = new LectureLogin(lectureData.getLoginInfo());
      }
   }
}
