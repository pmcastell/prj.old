package pruebas;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import util.miJList;
import java.io.*;

//import com.sun.java.swing.UIManager;
public class verFichero extends JApplet {
 boolean isStandalone = false;
 miJList jList1 = new miJList();
 javax.swing.JButton cargarFichero = new javax.swing.JButton();
 javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
 javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
//Get a parameter value

 public String getParameter(String key, String def) {
  return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
 }

 //Construct the applet

 public verFichero() {
 }
//Initialize the applet

 public void init() {
  try {
  jbInit();
  }
  catch (Exception e) {
  e.printStackTrace();
  }
 }
 //static {
 //  try {
 //    //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.metal.MetalLookAndFeel());
 //    //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
 //    UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
 //  }
 //  catch (Exception e) {}
 //}
//Component initialization

 private void jbInit() throws Exception {
  this.setSize(400,300);
  cargarFichero.setText("cargarFichero");
  jTextArea1.setText("jTextArea1");
  cargarFichero.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    cargarFichero_actionPerformed(e);
   }
  });
  this.getContentPane().add(jList1, BorderLayout.NORTH);
  this.getContentPane().add(cargarFichero, BorderLayout.SOUTH);
  this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
  jScrollPane1.getViewport().add(jTextArea1, null);
 }
//Start the applet

 public void start() {
 }
//Stop the applet

 public void stop() {
 }
//Destroy the applet

 public void destroy() {
 }
//Get Applet information

 public String getAppletInfo() {
  return "Applet Information";
 }
//Get parameter info

 public String[][] getParameterInfo() {
  return null;
 }
//Main method

 public static void main(String[] args) {
  verFichero applet = new verFichero();
  applet.isStandalone = true;
  JFrame frame = new JFrame();
  frame.setTitle("Applet Frame");
  frame.getContentPane().add(applet, BorderLayout.CENTER);
  applet.init();
  applet.start();
  frame.addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent e) {
      System.exit(0);
   }
  });
  frame.setSize(400,320);
  Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
  frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
  frame.setVisible(true);
 }
    public String cargaFichero() {
      Frame f=new Frame();
      FileDialog fichero=new FileDialog(f,"Abrir Fichero: ",FileDialog.LOAD);
      fichero.show();
      String file=fichero.getDirectory()+fichero.getFile();
      String aux="";
      BufferedReader b;
      StringBuffer r=new StringBuffer();
      try {
         b=new BufferedReader(new FileReader(file));
         while (true) {
            aux=b.readLine();
            if (r==null) {
               break;
            }
            r.append(aux);
         }
         b.close();
      } catch (Exception ex) {}
      return r.toString();
   }

 void cargarFichero_actionPerformed(ActionEvent e) {
//  this.jList1.cargaFichero();
    this.jTextArea1.setText(this.cargaFichero());
 }
}


