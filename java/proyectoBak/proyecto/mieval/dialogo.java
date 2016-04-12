/******************************************************************************/
/******************         dialogo.java        *******************************/
/******************************************************************************/
package mieval;

import java.awt.*;
import java.awt.event.*;

import borland.jbcl.layout.*;
import javax.swing.*;


/******************************************************************************/
/******************  esta clase ha sido generada casi automáticamente *********/
/******************  con la ayuda de herramientas RAD, se utiliza como*********/
/******************  base para las ventanas de mensajes ***********************/
/******************************************************************************/
public class dialogo extends JDialog {
 JPanel panel1 = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 JButton button1 = new JButton();
 JLabel mensaje = new JLabel("",SwingConstants.CENTER);


 public dialogo(JFrame frame, String title, boolean modal) {
  super(frame, title, modal);
  enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  try  {
   jbInit();
   getContentPane().add(panel1);
   pack();
  }
  catch (Exception ex) {
   ex.printStackTrace();
  }
 }
 public dialogo(String m, JFrame f, String t, boolean mod)
 {
    this(f,t,mod);
    mensaje.setText(m);
 }


 public dialogo(JFrame frame) {
  this(frame, "", false);
 }

 
 public dialogo(JFrame frame, boolean modal) {
  this(frame, "", modal);
 }

 
 public dialogo(JFrame frame, String title) {
  this(frame, title, false);
 }

 void jbInit() throws Exception {
  button1.setFont(new Font("Dialog", 1, 12));
  button1.setText("Aceptar");
  mensaje.setFont(new Font("Dialog", 1, 12));
  button1.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    button1_actionPerformed(e);
   }
  });
  panel1.setSize(new Dimension(635, 146));
  xYLayout1.setHeight(105);
  xYLayout1.setWidth(652);
  panel1.setLayout(xYLayout1);
  panel1.add(button1, new XYConstraints(256, 67, 134, 26));
  panel1.add(mensaje, new XYConstraints(14, 15, 625, 30));
 }

 protected void processWindowEvent(WindowEvent e) {
  if (e.getID() == WindowEvent.WINDOW_CLOSING) {
   cancel();
  }
  super.processWindowEvent(e);
 }

 void cancel() {
  dispose();
 }

 void button1_actionPerformed(ActionEvent e) {
  cancel();
 }
}

                          
