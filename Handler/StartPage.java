package Handler;

import Lecturer.*;
import Students.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartPage implements ActionListener {
   Color mainColor = new Color(0xB3EBF2);
   Font font = new Font("Times New Roman", Font.BOLD, 20);

   JFrame frame = new JFrame("Student / Lecturer");
   JLabel label = new JLabel("Please select your role");
   JLabel welcomeLabel = new JLabel("Welcome to APU Consultation");
   JButton studentButton = new JButton("Student");
   JButton lecturerButton = new JButton("Lecturer");

   public StartPage() {

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(400, 400);
      frame.setResizable(false);
      frame.setLayout(null);
      frame.getContentPane().setBackground(mainColor);

      label.setBounds(50, 60, 300, 30);
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setFont(font);

      welcomeLabel.setBounds(50, 20, 300, 30);
      welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
      welcomeLabel.setFont(font);

      studentButton.setBounds(50, 120, 300, 42);
      studentButton.setFocusable(false);
      studentButton.addActionListener(this);

      lecturerButton.setBounds(50, 180, 300, 42);
      lecturerButton.setFocusable(false);
      lecturerButton.addActionListener(this);

      frame.add(label);
      frame.add(welcomeLabel);
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
