
//Title:        Your Product Name
//Version:      
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package mieval;

import java.awt.*;
import javax.swing.*;
import borland.jbcl.layout.*;

public class Frame2 extends JFrame {
 XYLayout xYLayout1 = new XYLayout();
 javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
 javax.swing.JList jList1 = new javax.swing.JList();

 
 public Frame2() {
  try  {
   jbInit();
  }
  catch (Exception e) {
   e.printStackTrace();
  }
 }

 private void jbInit() throws Exception {
  this.getContentPane().setLayout(xYLayout1);
  this.getContentPane().add(jScrollPane1, new XYConstraints(62, 97, 168, 76));
  jScrollPane1.getViewport().add(jList1, null);
 }
}

      
