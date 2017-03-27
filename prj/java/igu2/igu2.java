import java.awt.*;
import java.awt.event.*;
import java.applet.*;



//import com.sun.java.swing.UIManager;
public class igu2 extends Applet {
   boolean isStandalone = false;
   superCanvas canvas = new superCanvas();
   BorderLayout borderLayout1 = new BorderLayout();
   Panel panel1 = new Panel();
   Scrollbar velocidad = new Scrollbar();
   Scrollbar angulo = new Scrollbar();
   TextField lVelocidad = new TextField();
   TextField lAngulo = new TextField();
   Label label1 = new Label();
   Label label2 = new Label();
   Label label3 = new Label();
   TextField lTiempo = new TextField();
  private TextField lPosInicial = new TextField();
  private Label label4 = new Label();
//Get a parameter value

   public String getParameter(String key, String def) {
      return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
   }

   //Construct the applet

   public igu2() {
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
      this.setSize(new Dimension(640, 480));
      canvas.setBackground(Color.white);
      canvas.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            canvas_mouseClicked(e);
         }
      });
      velocidad.setMaximum(1000);
      velocidad.setValue(10);
      velocidad.setOrientation(0);
      velocidad.setVisibleAmount(1);
      lVelocidad.setFont(new Font("Dialog", 1, 12));
      lVelocidad.setText("10");
      lVelocidad.addTextListener(new java.awt.event.TextListener() {
         public void textValueChanged(TextEvent e) {
            lVelocidad_textValueChanged(e);
         }
      });
      lAngulo.setFont(new Font("Dialog", 1, 12));
      lAngulo.setText("60");
      lAngulo.addTextListener(new java.awt.event.TextListener() {
         public void textValueChanged(TextEvent e) {
            lAngulo_textValueChanged(e);
         }
      });
      label1.setFont(new Font("Dialog", 1, 12));
      label1.setText("Angulo de Inclinacion:");
      label2.setFont(new Font("Dialog", 1, 12));
      label2.setText("Velocidad de Lanzamiento:");
      label3.setText("Tiempo:");
      lTiempo.setFont(new Font("Dialog", 1, 12));
      lTiempo.setText("     0    ");
      label3.setFont(new Font("Dialog", 1, 12));
      velocidad.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent e) {
            velocidad_adjustmentValueChanged(e);
         }
      });
      angulo.setMaximum(91);
      angulo.setValue(60);
      angulo.setOrientation(0);
      angulo.setVisibleAmount(1);
      angulo.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent e) {
            angulo_adjustmentValueChanged(e);
         }
      });
      this.setLayout(borderLayout1);
    lPosInicial.addTextListener(new java.awt.event.TextListener() {
         public void textValueChanged(TextEvent e) {
            lPosInicial_textValueChanged(e);
         }
      });
    lPosInicial.setText("-60");
    lPosInicial.setFont(new Font("Dialog", 1, 12));
    label4.setText("A:");
    label4.setFont(new Font("Dialog", 1, 12));
    this.add(canvas, BorderLayout.CENTER);
      this.add(panel1, BorderLayout.SOUTH);
    panel1.add(label4, null);
    panel1.add(lPosInicial, null);
    panel1.add(label1, null);
      panel1.add(angulo, null);
      panel1.add(lAngulo, null);
      panel1.add(label2, null);
      panel1.add(velocidad, null);
      panel1.add(lVelocidad, null);
      panel1.add(label3, null);
      panel1.add(lTiempo, null);
      actualiza();
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
      igu2 applet = new igu2();
      applet.isStandalone = true;
      Frame frame = new Frame();
      frame.setTitle("Applet Frame");
      frame.add(applet, BorderLayout.CENTER);
      applet.init();
      applet.start();
      frame.setSize(640, 480);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      frame.setVisible(true);
   }

   void velocidad_adjustmentValueChanged(AdjustmentEvent e) {
      lVelocidad.setText(Integer.toString(e.getValue()));
//      actualiza();
//      canvas.ejes();
   }

   void angulo_adjustmentValueChanged(AdjustmentEvent e) {
      lAngulo.setText(Integer.toString(e.getValue()));
//      actualiza();
//      canvas.ejes();
   }
   void actualiza() {
      int ang=Integer.parseInt(lAngulo.getText());
      int vel=Integer.parseInt(lVelocidad.getText());
      int pos=Integer.parseInt(lPosInicial.getText());
      canvas.setAngulo(ang); canvas.setVelocidad(vel);
      canvas.setPosInicial(pos);
   }

   void canvas_mouseClicked(MouseEvent e) {
      actualiza();
      canvas.setControlInfo(this.lTiempo);
      canvas.parabolico();
   }

   void lAngulo_textValueChanged(TextEvent e) {
      angulo.setValue(Integer.parseInt(lAngulo.getText()));
      actualiza();
      canvas.ejes();
   }

   void lVelocidad_textValueChanged(TextEvent e) {
      velocidad.setValue(Integer.parseInt(lVelocidad.getText()));
      actualiza();
      canvas.ejes();
   }
  void lPosInicial_textValueChanged(TextEvent e) {

  }
}

