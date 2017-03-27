
//Title:       Your Product Name
//Version:
//Copyright:   Copyright (c) 1998
//Author:      Your Name
//Company:     Your Company
//Description: Your description
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import layout.XYConstraints;
import layout.XYLayout;
public class visualizador extends miJApplet {
     boolean isStandalone = false;
     XYLayout xYLayout1 = new XYLayout();
     miJTabbedPane tabPanel = new miJTabbedPane(this);
     JButton anadirCalcWindow = new JButton();
     JButton anadirPantalla = new JButton();
     private int numCalcs=1, numPants = 1;
     JButton quitarCalcWindow = new JButton();
     JButton quitarPantalla = new JButton();
     java.util.ArrayList<Component> redimensionables=new java.util.ArrayList<Component>();
    //Get a parameter value

     public String getParameter(String key, String def) {
      return isStandalone ? System.getProperty(key, def) :
          (getParameter(key) != null ? getParameter(key) : def);
     }

     //Construct the applet

     public visualizador() {
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
     static {
       try {
           //UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
           //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
     //    UIManager.setLookAndFeel(new javax.swing.plaf.windows.WindowsLookAndFeel());
           UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
           //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());
         // UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
           //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
           //MetalLookAndFeel.setCurrentTheme(new TestTheme());
       }
       catch (Exception e) {}
     }
    //Component initialization

     private void jbInit() throws Exception {
      //xYLayout1.setHeight(700);
      tabPanel.setAutoscrolls(true);
      tabPanel.setDoubleBuffered(true);
      //tabPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red));
              //createEmptyBorder(0, -35, -35, -35));
      anadirCalcWindow.setText("Nuevo Panel Calculadora");
      anadirCalcWindow.setMargin(new Insets(0, 0, 0, 0));
      anadirCalcWindow.setFont(new Font("Dialog", 1, 11));
      anadirCalcWindow.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        anadirCalcWindow_actionPerformed(e);
       }
      });
      anadirPantalla.setFont(new Font("Dialog", 1, 11));
      quitarCalcWindow.setMargin(new Insets(0, 0, 0, 0));
      quitarCalcWindow.setText("Quitar Panel Calculadora");
      quitarCalcWindow.setFont(new Font("Dialog", 1, 11));
      quitarPantalla.setFont(new Font("Dialog", 1, 11));
      quitarPantalla.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        quitarPantalla_actionPerformed(e);
       }
      });
      quitarPantalla.setMargin(new Insets(0, 0, 0, 0));
      quitarPantalla.setText("Quitar Panel Dibujo Funciones");
      quitarCalcWindow.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        quitarCalcWindow_actionPerformed(e);
       }
      });
      anadirPantalla.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        anadirPantalla_actionPerformed(e);
       }
      });
      anadirPantalla.setText("Nuevo Panel Dibujo Funciones");
      anadirPantalla.setMargin(new Insets(0, 0, 0, 0));
      /*xYLayout1.setWidth(855);
      this.getContentPane().setLayout(xYLayout1);*/
      this.getContentPane().setLayout(new BorderLayout());
      this.setSize(1015,695);
      //this.getContentPane().add(tabPanel, new XYConstraints(0, 0, 845, 632));
      this.getContentPane().add(tabPanel,BorderLayout.CENTER);
        /*this.getContentPane().add(anadirCalcWindow, new XYConstraints(5, 630, 192, 28));
        this.getContentPane().add(anadirPantalla, new XYConstraints(197, 630, 224, 28));
        this.getContentPane().add(quitarCalcWindow, new XYConstraints(421, 630, 195, 28));
        this.getContentPane().add(quitarPantalla, new XYConstraints(616, 630, 224, 28));*/
      int desp=628;
      JPanel panelSur=new JPanel();
      //XYLayout panelSurLayout=new XYLayout();
      //panelSurLayout.setWidth(855); panelSurLayout.setHeight(36);
      //panelSur.setLayout(panelSurLayout);
      panelSur.add(anadirCalcWindow,new XYConstraints(5, 630-desp, 192, 28));
      panelSur.add(anadirPantalla, new XYConstraints(197, 630-desp, 224, 28));
      panelSur.add(quitarCalcWindow, new XYConstraints(421, 630-desp, 195, 28));
      panelSur.add(quitarPantalla, new XYConstraints(616, 630-desp, 224, 28));
      panelSur.setAlignmentX(JPanel.CENTER_ALIGNMENT);
      this.getContentPane().add(panelSur,BorderLayout.SOUTH);
      panelCalc c=new panelCalc();
      
      tabPanel.addTab("Calculadora", c);
      tabPanel.setMnemonicAt(0, KeyEvent.VK_A);
      pantalla p=new pantalla();
      tabPanel.addTab("Gráficas de Funciones", p);
      tabPanel.setMnemonicAt(1, KeyEvent.VK_G);
      tabPanel.setSelectedIndex(0);
      tabPanel.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
               if (tabPanel.getSelectedComponent() instanceof panelCalc) {
                  ((panelCalc)tabPanel.getSelectedComponent()).expresionSetFocus();
               }
            }
         });
      //abPanel.getComponent(0).requestFocus();
      //this.redimensionables.add(tabPanel);
      //this.redimensionables.add(c);
      //this.redimensionables.add(p);
      //this.redimensionables.add(this);
      
      
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
    /* public void setSize(int w,int h) {
         Container padre=this.getParent();
         if (w<padre.getWidth()) {
            //w=padre.getWidth();
         }
         if (h<padre.getHeight()) {
            //h=padre.getHeight();
         }
         super.setSize(w,h);
         //this.tabPanel.setSize(w, h);

     }*/

     public static void main(String[] args) {
      //miJFrame frame = new miJFrame(applet);
      JFrame frame=new JFrame();
      
      frame.setTitle("Applet Frame");
      visualizador applet = new visualizador();
      applet.isStandalone = true;
      frame.getContentPane().add(applet, BorderLayout.CENTER);
      frame.setTitle("Calculadora Científica/Programable (c) 1999-2012 F.J.Criado");
      
      applet.init();
      applet.start();
      frame.setSize(1015,695);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      //frame.getContentPane().setBackground(Color.green);
      //applet.redimensionables.add(frame.getContentPane());
      final  visualizador v=applet;
      frame.setVisible(true);
      ((panelCalc)applet.tabPanel.getComponent(0)).expresionSetFocus();
     }
     void anadirCalcWindow_actionPerformed(ActionEvent e) {
        numCalcs++;
        tabPanel.addTab("Calculadora "+numCalcs, new panelCalc());
        tabPanel.setMnemonicAt(tabPanel.getTabCount()-1,'0'+tabPanel.getTabCount()-1);
        repaint();
     }
     void anadirPantalla_actionPerformed(ActionEvent e) {
        numPants++;
        tabPanel.addTab("Gráficas de Funciones"+numPants, new pantalla());
        tabPanel.setMnemonicAt(tabPanel.getTabCount()-1,'0'+tabPanel.getTabCount()-1);
        repaint();
     }
     void quitarCalcWindow_actionPerformed(ActionEvent e) {
        if (numCalcs>1) {
           for(int i=tabPanel.getTabCount()-1;i>1;i--) {
              if (tabPanel.getComponentAt(i) instanceof panelCalc) {
                 tabPanel.removeTabAt(i);
                 numCalcs--;
                 return;
              }
           }
           //tabPanel.removeTabAt(numCalcs--);
           repaint();
        } else {
           mieval.dialogo.cuadro_mensaje("No se puede eliminar la Calculadora Principal","");
        }
     }
     void quitarPantalla_actionPerformed(ActionEvent e) {
        if (numPants>1) {
           for(int i=tabPanel.getTabCount()-1;i>1;i--) {
              if (tabPanel.getComponentAt(i) instanceof pantalla) {
                 tabPanel.removeTabAt(i);
                 numPants--;
                 return;
              }
           }

           //tabPanel.removeTabAt(numPants--);
           repaint();
        } else {
           mieval.dialogo.cuadro_mensaje("No se puede eliminar el Panel de Representación de Funciones Principal","");
        }
     }
}

class miJFrame extends JFrame {
    visualizador v=null;
    /*
    public void setSize(Dimension d) {
        super.setSize(d);
        System.out.println("aquí estamos: "+this.getSize());
    }
    public void setSize(int w,int h) {
        super.setSize(w,h);
        System.out.println("aquí estamos: "+this.getSize());
    }
    public void repaint() {
        this.repaint();
        System.out.println("aquí estamos en repaint: "+this.getSize());
    }
    public void resize() {
        this.resize();
        System.out.println("aquí estamos en resize: "+this.getSize());
    }
    public void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        System.out.println("aquí estamos en processWindowEvent: "+e);
    }*/

    public miJFrame(visualizador vi) {
        super();
        this.v=vi;
        //this.setBackground(Color.orange);
        
    }
}
class miJApplet extends JApplet {
    public miJApplet() {
        super();
        //this.setBackground(Color.yellow);
        
    }
    public void setSize(int w,int h) {
        super.setSize(w,h);
        //System.out.println("Cambiado miJApplet: W: "+this.getWidth()+" H: "+this.getHeight());
    }
}


class miJTabbedPane extends JTabbedPane {
    public miJTabbedPane(Container c) {
        super();
        //this.setBackground(Color.blue);
    }
    /*
    public void setSize(int w,int h) {
        Container padre=this.getParent();
        if (padre!=null) {
            if (w<padre.getWidth()) {
               //w=padre.getWidth();
            }
            if (h<padre.getHeight()) {
               //h=padre.getHeight();
            }
        }
        super.setSize(w,h);
        System.out.println("Cambiado miJtabbedPane: "+this.getWidth()+" H: "+this.getHeight());
    }*/
}

