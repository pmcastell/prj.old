
//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Vector;
import java.io.*;
import java.io.Reader;



//import com.sun.java.swing.UIManager;
public class Pid extends Applet {
 private boolean buscarBlancas=false;
 private boolean buscarNegras=true;
 private int negroAdyacencia=4;
 private int blancoAdyacencia=8;
  boolean isStandalone = false;
 Label label1 = new Label();
 TextArea matrizOriginal = new TextArea();
 TextArea matrizConexa = new TextArea();
 Button button1 = new Button();
 matrizCuadrada matriz=new matrizCuadrada();
 static String parEntrada;
 String fEntrada="prueba.txt";
 Button button3 = new Button();
 Button button4 = new Button();
 CheckboxGroup cboxGroupNegro = new CheckboxGroup();
 CheckboxGroup cboxGroupBlanco = new CheckboxGroup();
 Checkbox negro = new Checkbox();
 Checkbox blanco = new Checkbox();
 Checkbox negroAdy_4 = new Checkbox();
 Checkbox negroAdy_8 = new Checkbox();
 Checkbox blancoAdy_4 = new Checkbox();
 Checkbox blancoAdy_8 = new Checkbox();
//Get a parameter value

  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet

  public Pid() {
  }



  void ficheroToMatriz(String file)
  {
     BufferedReader r;
     try {
        r=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        matriz=matrizCuadrada.fileToMatrizCuadrada(r);
     } catch (Exception ex) {
        dialogo.cuadro_mensaje("Ocurrió una excepción: "+ex,"Error");
     }
  }



  void inicializa()
  {
     if (parEntrada!=null) {
        fEntrada=parEntrada;
        parEntrada=null;
     }
     try {
        ficheroToMatriz(fEntrada);
        if (matriz!=null) {
           matrizOriginal.setText(matriz.toString());
        }   
     } catch (Exception ex) {
        System.out.println("Ocurrió la siguiente excepción: "+ex);
        ex.printStackTrace();
     }
  }
//Initialize the applet

  public void init() {
    try {
    jbInit();
    inicializa();
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
  this.setLayout(null);
  this.setBackground(SystemColor.scrollbar);
  this.setSize(new Dimension(635, 439));
  label1.setFont(new Font("Dialog", 1, 16));
  label1.setBounds(new Rectangle(209, 21, 149, 28));
  label1.setText("Proyecto de P.I.D.");
  matrizOriginal.setFont(new Font("Monospaced", 1, 18));
  matrizOriginal.setBounds(new Rectangle(7, 51, 314, 319));
  matrizConexa.setFont(new Font("Monospaced", 1, 18));
  matrizConexa.setBounds(new Rectangle(328, 51, 297, 319));
  button1.setBounds(new Rectangle(438, 403, 187, 26));
  button1.setLabel("Buscar Componentes Conexas");
  Button button2 = new Button();
  button2.setBounds(new Rectangle(330, 403, 99, 26));
  button2.setLabel("Cargar Fichero");
  button3.setBounds(new Rectangle(5, 371, 84, 18));
  button3.setLabel("Limpiar");
  button3.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    button3_actionPerformed(e);
   }
  });
  button4.setLabel("Limpiar");
  button4.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    button4_actionPerformed(e);
   }
  });
  button4.setBounds(new Rectangle(540, 372, 84, 18));

  button2.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    button2_actionPerformed(e);
   }
  });
  button1.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    button1_actionPerformed(e);
   }
  });
  negro.setBounds(new Rectangle(5, 390, 57, 18));
  negro.setState(true);
  negro.setLabel("Negro");
  negro.addItemListener(new java.awt.event.ItemListener() {
   public void itemStateChanged(ItemEvent e) {
    negro_itemStateChanged(e);
   }
  });
  blanco.setBounds(new Rectangle(4, 413, 58, 18));
  blanco.setLabel("Blanco");
  negroAdy_4.setBounds(new Rectangle(64, 389, 95, 19));
  negroAdy_4.setLabel("4-Adyacencia");
  negroAdy_4.addItemListener(new java.awt.event.ItemListener() {
   public void itemStateChanged(ItemEvent e) {
    negroAdy_4_itemStateChanged(e);
   }
  });
  negroAdy_4.setCheckboxGroup(this.cboxGroupNegro);
  negroAdy_8.addItemListener(new java.awt.event.ItemListener() {
   public void itemStateChanged(ItemEvent e) {
    negroAdy_8_itemStateChanged(e);
   }
  });
  negroAdy_8.setCheckboxGroup(this.cboxGroupNegro);
  blancoAdy_4.setCheckboxGroup(this.cboxGroupBlanco);
  blancoAdy_8.setLabel("8-Adyacencia");
  blancoAdy_8.setBounds(new Rectangle(160, 413, 101, 18));
  blancoAdy_8.setCheckboxGroup(this.cboxGroupBlanco);
  blancoAdy_8.addItemListener(new java.awt.event.ItemListener() {
   public void itemStateChanged(ItemEvent e) {
    blancoAdy_8_itemStateChanged(e);
   }
  });
  blancoAdy_4.addItemListener(new java.awt.event.ItemListener() {
   public void itemStateChanged(ItemEvent e) {
    blancoAdy_4_itemStateChanged(e);
   }
  });
  blancoAdy_4.setLabel("4-Adyacencia");
  blancoAdy_4.setBounds(new Rectangle(64, 413, 95, 19));
  negroAdy_8.setBounds(new Rectangle(160, 389, 101, 18));
  negroAdy_8.setLabel("8-Adyacencia");
  blanco.addItemListener(new java.awt.event.ItemListener() {
   public void itemStateChanged(ItemEvent e) {
    blanco_itemStateChanged(e);
   }
  });
  this.add(label1, null);
  this.add(matrizOriginal, null);
  this.add(matrizConexa, null);
  this.add(button1, null);
  this.add(button2, null);
  this.add(button3, null);
  this.add(button4, null);
  this.add(negro, null);
  this.add(blanco, null);
  this.add(negroAdy_4, null);
  this.add(negroAdy_8, null);
  this.add(blancoAdy_4, null);
  this.add(blancoAdy_8, null);
  if (this.isStandalone) {
     button2.setVisible(true);
  } else {
     button2.setVisible(false);
  }
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

      /* Parte del Applet */
      Pid applet = new Pid();
      applet.isStandalone = true;
      Frame frame = new Frame();
      frame.setTitle("Applet Frame");
      frame.add(applet, BorderLayout.CENTER);
      if (args.length>0) {
         parEntrada=args[0];
      }
      applet.init();
      applet.start();
      frame.setSize(635, 460);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });

      frame.setVisible(true);
  }



   


 void button1_actionPerformed(ActionEvent e) {
     matriz=new matrizCuadrada(matrizOriginal.getText());
     quadArbol a=new quadArbol(matriz);
     if (buscarNegras) {
        bloque.setAdyacencia(this.negroAdyacencia);
        conjunto negras=a.buscaConexas('1');
        matriz.etiquetaConexas(negras);
     }
     if (buscarBlancas) {
        bloque.setAdyacencia(this.blancoAdyacencia);
        conjunto blancas=a.buscaConexas('0');
        matriz.etiquetaConexas(blancas);
     }   
     matrizConexa.setText(matriz.toString());
 }

 void button2_actionPerformed(ActionEvent e) {
        Frame frame=new Frame();
        FileDialog fichero=new FileDialog(frame,"Cargar Matriz desde: ",
                                                               FileDialog.LOAD);
        fichero.show();
        if (fichero.getFile()!=null) {
           ficheroToMatriz(fichero.getFile());
           matrizOriginal.setText(matriz.toString());
           matrizConexa.setText("");
        }
 }

 void button3_actionPerformed(ActionEvent e) {
  matrizOriginal.setText("");
 }

 void button4_actionPerformed(ActionEvent e) {
  matrizConexa.setText("");
 }

 void negroAdy_4_itemStateChanged(ItemEvent e) {
  if (negroAdy_4.getState()) {
     negroAdyacencia=4;
  }
 }

 void negroAdy_8_itemStateChanged(ItemEvent e) {
  if (negroAdy_8.getState()) {
     negroAdyacencia=8;
  }
 }
 void blancoAdy_4_itemStateChanged(ItemEvent e) {
  if (blancoAdy_4.getState()) {
     blancoAdyacencia=4;
  }
 }
 void blancoAdy_8_itemStateChanged(ItemEvent e) {
  if (blancoAdy_8.getState()) {
     blancoAdyacencia=8;
  }
 }

        

 void negro_itemStateChanged(ItemEvent e) {
  if (negro.getState()) {
     buscarNegras=true;
  } else {
     buscarNegras=false;
  }      
 }

 void blanco_itemStateChanged(ItemEvent e) {
  if (blanco.getState()) {
     buscarBlancas=true;
  } else {
     buscarBlancas=false;
  }      
 }
}
