/******************************************************************************/
/******************         leerVar.java        *******************************/
/******************************************************************************/
package mieval;
import mieval.tipo.variable;

import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import com.borland.jbcl.layout.XYLayout;
import com.borland.jbcl.layout.XYConstraints;
import javax.swing.*;

/******************************************************************************/
/******************  esta clase ha sido generada casi automáticamente *********/
/******************  con la ayuda de herramientas RAD, se utiliza como*********/
/******************  base para las ventanas de lectura de variables   *********/
/******************************************************************************/
public class leerVar extends JDialog {
 JPanel panel1 = new JPanel();
 XYLayout xYLayout1 = new XYLayout();
 JLabel mensaje = new JLabel("",JLabel.CENTER);
 JTextField val = new JTextField();
 JButton button1 = new JButton();
 String titulo="";

 public leerVar(String title, boolean modal) {
  super(new JFrame(), title, modal);
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
 public String getValor()
 {
    return val.getText();
 }
 public void setMensaje(String s)
 {
    mensaje.setText(s);
 }

 public leerVar() {
  this("", false);
 }


 public leerVar(boolean modal) {
  this("", modal);
 }


 public leerVar(String title) {
  this(title, false);
 }

 void jbInit() throws Exception {
  panel1.setSize(new Dimension(503, 111));
  xYLayout1.setHeight(125);
  xYLayout1.setWidth(503);
  mensaje.setFont(new Font("Dialog", 1, 12));
//  mensaje.setAlignment(1);
  val.setFont(new Font("Dialog", 1, 12));
  val.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    val_actionPerformed(e);
   }
  });
  button1.setFont(new Font("Dialog", 1, 12));
  button1.setText("Aceptar");
  button1.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    button1_actionPerformed(e);
   }
  });
  panel1.setLayout(xYLayout1);
  panel1.add(mensaje, new XYConstraints(13, 11, 482, 25));
  panel1.add(val, new XYConstraints(17, 51, 475, 28));
  panel1.add(button1, new XYConstraints(158, 92, 177, 22));
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

 void val_actionPerformed(ActionEvent e) {
  cancel();
 }

 void button1_actionPerformed(ActionEvent e) {
  cancel();
 }

}


