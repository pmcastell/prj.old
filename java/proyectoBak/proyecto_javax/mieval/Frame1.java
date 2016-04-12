
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package mieval;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import pantalla;
import CalcWindow;

public class Frame1 extends JFrame {
 String []prueba={"esto es una prueba",
                "vamos a ver como sale",
                "no",
                "si",
                "tal vez",
                "mañana más",
                "si no ya veremos",
                "bueno",
                "adiós",
                "hasta luego",
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                "g"};
 JScrollPane jScrollPane2 = new JScrollPane();
 CalcWindow calcWindow1 = new CalcWindow();


 public Frame1() {
  try  {
   jbInit();
  }
  catch (Exception e) {
   e.printStackTrace();
  }
 }

 public static void main(String[] args) {
  Frame1 frame11 = new Frame1();
  frame11.addWindowListener(
     new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
           System.exit(0);
        }
     }
  );         
  frame11.setVisible(true);
 }

 private void jbInit() throws Exception {
  this.getContentPane().setLayout(null);
  jScrollPane2.setBounds(new Rectangle(34, 44, 539, 451));
  this.getContentPane().add(jScrollPane2, null);
 }

 void jList1_mousePressed(MouseEvent e) {

 }
}

 
