
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class dialogCarrera extends Dialog {
  Panel panel1 = new Panel();
  BorderLayout borderLayout1 = new BorderLayout();
  Panel panel2 = new Panel();
  Label label1 = new Label();
;
  FlowLayout flowLayout1 = new FlowLayout();
  Random r= new Random();
  static Frame f=new Frame();
   ArrayList<String> asignaturas = new ArrayList<String>();
   ArrayList<String> calificaciones = new ArrayList<String>();
   Button button1 = new Button();
   carrera c;
   Choice alum = new Choice();


  public dialogCarrera(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try  {
      jbInit();
      add(panel1);
//      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  public dialogCarrera(String titulo, carrera c) {
     this(f, titulo, true);
     Frame f=new Frame();
     Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
     Dimension p1= panel1.getSize();
     f.setSize(p1);
     this.setLocation((d.width - p1.width) / 2, (d.height - p1.height) / 2);
     this.setSize(p1);
     this.c=c;
     for(int i=0; i<c.alumnos.size(); i++) {
        alum.add(((alumno) c.alumnos.elementAt(i)).nombre);
     }
  }


  public dialogCarrera(Frame frame) {
    this(frame, "", false);
  }


  public dialogCarrera(Frame frame, boolean modal) {
    this(frame, "", modal);
  }


  public dialogCarrera(Frame frame, String title) {
    this(frame, title, false);
  }

  void jbInit() throws Exception {
    panel1.setSize(new Dimension(562, 300));
    panel2.setLayout(flowLayout1);
    label1.setFont(new Font("Dialog", 1, 12));
      label1.setText("Nombre del Alumno:");
      button1.setLabel("Volver a la Ventana Principal");
      alum.addItemArrayListener(new java.awt.event.ItemArrayListener() {
         public void itemStateChanged(ItemEvent e) {
            alum_itemStateChanged(e);
         }
      });
      button1.addActionArrayListener(new java.awt.event.ActionArrayListener() {
         public void actionPerformed(ActionEvent e) {
            button1_actionPerformed(e);
         }
      });
    panel1.setLayout(borderLayout1);
    panel1.add(panel2, BorderLayout.NORTH);
    panel2.add(label1, null);
      panel2.add(alum, null);
      panel1.add(asignaturas, BorderLayout.CENTER);
      panel1.add(calificaciones, BorderLayout.EAST);
      panel1.add(button1, BorderLayout.SOUTH);
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

   public void actualiza() {
      String alumno=this.alum.getSelectedItem();
      alumno a=c.busqueda(alumno);
      this.asignaturas.clear();
      this.calificaciones.clear();
      if (a!=null) {
//         this.alumno.setText(a.nombre);
         for(int i=0; i<a.asignaturas.size(); i++) {
            this.asignaturas.add((String) a.asignaturas.elementAt(i));
            this.calificaciones.add(Integer.toString(5+Math.abs(r.nextInt() % 6)));
         }
      } else {
         ventanaDialogo.vMensaje("Alumno no encontrado en esta Carrera, pruebe otra vez... ");
      }
   }   
   void alum_itemStateChanged(ItemEvent e) {
      actualiza();
   }
}

