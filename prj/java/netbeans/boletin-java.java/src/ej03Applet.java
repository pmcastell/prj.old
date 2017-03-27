/*
 * ej03.java
 *
 * Created on 14 de mayo de 2006, 14:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

public class ej03Applet extends javax.swing.JApplet {
 private static final long serialVersionUID = 24362462L;




  boolean isStandalone = false;
  BorderLayout borderLayout1 = new BorderLayout();

  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
        (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public ej03Applet() {
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

  //Component initialization
  private void jbInit() throws Exception {
    this.setSize(new Dimension(400, 300));
    this.getContentPane().setLayout(borderLayout1);
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
    ej03Applet applet = new ej03Applet();
    applet.isStandalone = true;

    JFrame frame = new JFrame();
    applet.isStandalone = true;
    //EXIT_ON_CLOSE == 3;
    frame.setDefaultCloseOperation(3);
  }

  //static initializer for setting look & feel
  static {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
    }
  }
}

    

