/******************************************************************************/
/******************         dialogo.java        *******************************/
/******************************************************************************/

import java.awt.*;
import java.awt.event.*;

import borland.jbcl.layout.*;


/******************************************************************************/
/******************  esta clase ha sido generada casi automáticamente *********/
/******************  con la ayuda de herramientas RAD, se utiliza como*********/
/******************  base para las ventanas de mensajes ***********************/
/******************************************************************************/
public class dialogo extends Dialog {
 Panel panel1 = new Panel();
 XYLayout xYLayout1 = new XYLayout();
 Button button1 = new Button();
 Label mensaje = new Label("",Label.CENTER);


 public dialogo(Frame frame, String title, boolean modal) {
  super(frame, title, modal);
  enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  try  {
   jbInit();
   add(panel1);
   pack();
  }
  catch (Exception ex) {
   ex.printStackTrace();
  }
 }
 public dialogo(String m, Frame f, String t, boolean mod)
 {
    this(f,t,mod);
    mensaje.setText(m);
 }


 public dialogo(Frame frame) {
  this(frame, "", false);
 }

 
 public dialogo(Frame frame, boolean modal) {
  this(frame, "", modal);
 }

 
 public dialogo(Frame frame, String title) {
  this(frame, title, false);
 }

 void jbInit() throws Exception {
  button1.setFont(new Font("Dialog", 1, 12));
  button1.setLabel("Aceptar");
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
 public static void cuadro_mensaje(String s, String tipo)
 {
    Frame frame=new Frame();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    dialogo dlg=new dialogo(s,frame,tipo,true);
    dlg.setLocation((d.width - dlg.getSize().width) / 2,
                                      (d.height - dlg.getSize().height) / 2);
    dlg.show();
 }

}

                          
