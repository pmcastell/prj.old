
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description



import java.awt.*;
import java.awt.event.*;

public class ventanaDialogo extends Dialog {
   Panel panel1 = new Panel();
   Label label1 = new Label();
   static Frame f=new Frame();
   BorderLayout borderLayout1 = new BorderLayout();
   Panel panel2 = new Panel();
   Button button1 = new Button();


   public ventanaDialogo(Frame frame, String title, boolean modal) {
      super(frame, title, modal);
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try  {
         jbInit();
         add(panel1);
//         pack();
      }
      catch (Exception ex) {
         ex.printStackTrace();
      }
   }


   public ventanaDialogo(Frame frame) {
      this(frame, "", false);
   }


   public ventanaDialogo(Frame frame, boolean modal) {
      this(frame, "", modal);
   }


   public ventanaDialogo(Frame frame, String title) {
      this(frame, title, false);
   }
   public ventanaDialogo(String mensaje) {
     this(f, "Ventana de Mensajes", true);
     Frame f=new Frame();
     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension p1= panel1.getSize();
     this.label1.setText(mensaje);
     panel1.setSize(p1.width*2, p1.height*4);
     f.setSize(p1.width*2, p1.height*4);
     this.setLocation((d.width - p1.width) / 2, (d.height - p1.height) / 2);
     this.setSize(p1);
  }
  public void center() {
     Dimension p1=panel1.getSize();
     Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
     this.setLocation((d.width - p1.width) / 2, (d.height - p1.height) / 2);
  }

  public static void vMensaje(String mensaje) {
     ventanaDialogo v=new ventanaDialogo("      "+mensaje+"      ");
     v.pack();
     v.center();
     v.setVisible(true);
  }



   void jbInit() throws Exception {
      label1.setFont(new Font("Dialog", 1, 16));
      label1.setText("");
      button1.setFont(new Font("Dialog", 1, 12));
      button1.setLabel("Aceptar");
      button1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            button1_actionPerformed(e);
         }
      });
      panel1.setLayout(borderLayout1);
      panel1.add(label1, BorderLayout.CENTER);
      panel1.add(panel2, BorderLayout.SOUTH);
      panel2.add(button1, null);
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
      dispose();
   }
}


