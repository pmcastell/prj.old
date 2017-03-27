
//Title:       Your Product Name
//Version:
//Copyright:   Copyright (c) 1998
//Author:      Your Name
//Company:     Your Company
//Description: Your description

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;
public class BonoLoto extends Applet {
 boolean isStandalone = false;
 JLabel jLabel1 = new JLabel();
 JLabel jLabel2 = new JLabel();
 JTextField combGanadora = new JTextField();
 JScrollPane jScrollPane1 = new JScrollPane();
 JLabel jLabel3 = new JLabel();
 JTextField complementario = new JTextField();
 JLabel jLabel4 = new JLabel();
 JTextField reintegro = new JTextField();
 JButton comprobar = new JButton();
 JLabel jLabel5 = new JLabel();
 JTextField Premios = new JTextField();
 JTextArea combJugadas = new JTextArea();
 JButton bonoLoto = new JButton();
 JButton Primitiva = new JButton();
 JButton gordo = new JButton();
 JButton limpiar = new JButton();
  TitledBorder titledBorder1;
//Get a parameter value

 public String getParameter(String key, String def) {
  return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
 }

 //Construct the applet

 public BonoLoto() {
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
 //    //UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
 //    //UIManager.setLookAndFeel(new javax.swing.plaf.motif.MotifLookAndFeel());
 //    UIManager.setLookAndFeel(new javax.swing.plaf.windows.WindowsLookAndFeel());
 //  }
 //  catch (Exception e) {}
 //}
//Component initialization

 private void jbInit() throws Exception {
  titledBorder1 = new TitledBorder("");
    this.setSize(new Dimension(460, 350));
  jLabel1.setText("Comb.Ganadora:");
  jLabel1.setBounds(new Rectangle(6, 4, 107, 18));
  jLabel2.setText("Comb.Jugadas:");
  jLabel2.setBounds(new Rectangle(5, 37, 104, 19));
  combGanadora.setBounds(new Rectangle(116, 4, 188, 21));
  combGanadora.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    combGanadora_actionPerformed(e);
   }
  });
  jScrollPane1.setBounds(new Rectangle(114, 43, 295, 186));

  comprobar.setBorder(BorderFactory.createRaisedBevelBorder());
    comprobar.setMargin(new Insets(0, 0, 0, 0));
    comprobar.setText("Comprobar");
  comprobar.setBounds(new Rectangle(192, 264, 95, 19));
  comprobar.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    comprobar_actionPerformed(e);
   }
  });
  comprobar.setMnemonic(KeyEvent.VK_C);
  jLabel5.setText("Premios");
  jLabel5.setBounds(new Rectangle(14, 289, 91, 16));
  Premios.setBounds(new Rectangle(115, 285, 320, 22));
  bonoLoto.setBorder(BorderFactory.createRaisedBevelBorder());
    bonoLoto.setMargin(new Insets(0, 0, 0, 0));
    bonoLoto.setText("BonoLoto");
  bonoLoto.setBounds(new Rectangle(40, 236, 89, 19));
  bonoLoto.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    bonoLoto_actionPerformed(e);
   }
  });
  Primitiva.setBounds(new Rectangle(140, 236, 89, 19));
  Primitiva.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    Primitiva_actionPerformed(e);
   }
  });
  gordo.setBounds(new Rectangle(241, 236, 89, 19));
  limpiar.setBounds(new Rectangle(339, 236, 89, 19));
  limpiar.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    limpiar_actionPerformed(e);
   }
  });
  limpiar.setBorder(BorderFactory.createRaisedBevelBorder());
    limpiar.setMargin(new Insets(0, 0, 0, 0));
    limpiar.setText("Limpiar");
  gordo.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    gordo_actionPerformed(e);
   }
  });
  gordo.setBorder(BorderFactory.createRaisedBevelBorder());
    gordo.setMargin(new Insets(0, 0, 0, 0));
    gordo.setText("Gordo Prim.");
  Primitiva.setBorder(BorderFactory.createRaisedBevelBorder());
    Primitiva.setMargin(new Insets(0, 0, 0, 0));
    Primitiva.setText("Primitiva");
  combJugadas.addMouseListener(new java.awt.event.MouseAdapter() {
   public void mouseClicked(MouseEvent e) {
    combJugadas_mouseClicked(e);
   }
  });
  combJugadas.setText("");
  Premios.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    Premios_actionPerformed(e);
   }
  });
  jLabel3.setText("C.:");
  jLabel3.setBounds(new Rectangle(309, 4, 18, 18));
  complementario.setBounds(new Rectangle(330, 4, 29, 21));
  jLabel4.setText("R.:");
  jLabel4.setVisible(false);
  jLabel4.setBounds(new Rectangle(358, 20, 18, 18));
  reintegro.setVisible(false);
  reintegro.setBounds(new Rectangle(379, 20, 28, 21));
  this.setLayout(null);
  this.setSize(new Dimension(460, 350));
  this.add(jLabel1, null);
  this.add(jLabel2, null);
  this.add(combGanadora, null);
  this.add(jScrollPane1, null);
  jScrollPane1.getViewport().add(combJugadas, null);
  this.add(jLabel3, null);
  this.add(complementario, null);
  this.add(jLabel4, null);
  this.add(reintegro, null);
    this.add(bonoLoto, null);
    this.add(Primitiva, null);
    this.add(gordo, null);
    this.add(limpiar, null);
    this.add(comprobar, null);
    this.add(jLabel5, null);
    this.add(Premios, null);
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

  BonoLoto applet = new BonoLoto();
  applet.isStandalone = true;
  JFrame frame = new JFrame();
  frame.setTitle("Applet Frame");
  frame.getContentPane().add(applet, BorderLayout.CENTER);
  applet.init();
  applet.start();
  frame.setSize(460,350);
  Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
  frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
  frame.addWindowListener(
     new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
           System.exit(0);
        }
     }
  );
  frame.setVisible(true);
 }

 void combGanadora_actionPerformed(ActionEvent e) {

 }

 void combJugadas_mouseClicked(MouseEvent e) {
  System.out.println("Bien");
 }

 void Premios_actionPerformed(ActionEvent e) {

 }
 static int numItems(String s) {
    int i=0, res=0, len=s.length();

    while (i<len) {
       if (s.charAt(i)!=' ' && s.charAt(i)!='-') {
          res++;
          while (i<len && s.charAt(i)!=' ' && s.charAt(i)!='-') {
             i++;
          }
       }
       i++;
    }
    return res;
 }
 static int [] cadenaMatriz(String s) {
    int i=0, actual=0, len=s.length();
    //int []res= new int[numItems(s)];
    int []res= new int[6];
    int j=0,r=0;
    System.out.println("hola"+s);
    while (i<len && actual<res.length) {
       if (Character.isDigit(s.charAt(i))) {
          r=0;
          j=1;
          while (i<len && Character.isDigit(s.charAt(i)) && j<=2 && actual<6) {
             r=r*10+((int) (s.charAt(i) - '0'));
             i++;
             j++;
          }
          res[actual++]=r;
          System.out.println("i: "+i+" j: "+j+" r: "+r);
       }
       if (i<len && !Character.isDigit(s.charAt(i))) {
          i++;
       }
    }
    return res;
 }
 static int numLineas(String s) {
    int res=1;
    if (s==null || s=="") {
       return 0;
    }
    for (int i=0; i<s.length(); i++) {
       if (s.charAt(i)=='\n') {
          res++;
       }
    }
    return res;
 }
 static String linea(String s, int ind) {
    int crlf=0, i=0, comienzo=0, fin=0, len=s.length();
    while (crlf<ind && i<len) {
       if (s.charAt(i)=='\n') {
          crlf++;
       }
       i++;
    }
    comienzo= i>0 ? i-1:i;
    while (i<len && s.charAt(i)!='\n') {
       i++;
    }
    fin=i;
    return s.substring(comienzo, fin);
 }


 static int [][] cadenaMatriz2(String s) {
    int n=numLineas(s);
    int [][]res=new int[n][];
    for (int i=0; i<n; i++) {
       res[i]=cadenaMatriz(linea(s,i));
    }
    return res;
 }
 boolean esEspacio(char c) {
     if (c==' ' || c=='\t' || c=='\n' || c<'0' || c>'9') {
         return true;
     }
     return false;
 }

 String combinacionGanadora() {
     String cgaux=new String(combGanadora.getText().trim()), res="";
     int i=0;

     while(i<cgaux.length()) {
         if (esEspacio(cgaux.charAt(i))) {
             res+=' ';
         }
         while(i<cgaux.length() && esEspacio(cgaux.charAt(i))) {
             i++;
         }
         if (i<cgaux.length()) {
             res += cgaux.charAt(i);
             i++;
         }
     }
     return res;
 }

 void comprobar_actionPerformed(ActionEvent e) {
  String premios="";
  String cg=getClipboardContents();
  String cj=combJugadas.getText();
  if (cg!=null) {
     combGanadora.setText(cg);
  }
  cg=combinacionGanadora();
  combGanadora.setText(cg);
  boolean acertados[]=new boolean[6];

  Premios.setText("");
  if (cj!=null && cg!=null && cj!="" && cg!="") {
     int [][] jugadas=cadenaMatriz2(cj);
     int [] premiada=cadenaMatriz(cg);
     int aciertos;
     for(int i=0; i<jugadas.length; i++) {
        aciertos=0;
        for(int j=0; j<6; j++) {
           acertados[j]=false;
        }
        for(int j=0; j<6; j++) {
           for(int k=0; k<6; k++) {
              if (jugadas[i][j]==premiada[k]) {
                 aciertos++;
                 acertados[j]=true;
              }
           }
        }
        if (aciertos>=3) {
           premios+="fila "+(i+1)+": "+aciertos;
           if (aciertos==5) {
              try {
                 int comp=Integer.parseInt(complementario.getText());
                 for(int j=0; j<6; j++) {
                    if (!acertados[j]) {
                       if (jugadas[i][j]==comp) {
                          premios+="+C";
                       }
                    }
                 }
              } catch (Exception ex) {
                  Premios.setText("Tienes una de 5.Te has olvida poner el complementario!!!");
                  return;
              }
           }
           premios+="; ";
        }
     }
     if (premios.length()<=0) {
        premios="Sin Premio";
     }
     Premios.setText(premios);
  }
 }

 void bonoLoto_actionPerformed(ActionEvent e) {
    String combinaciones="03 06 09 12 14 30\n";
          combinaciones+="02 11 13 24 25 31\n";
          combinaciones+="03 14 24 25 36 37\n";
          combinaciones+="21 23 24 27 39 48\n";
          combinaciones+="01 26 33 38 44 46\n";
          combinaciones+="14 22 24 26 37 44\n";
          combinaciones+="07 15 19 23 41 49\n";
          combinaciones+="13 25 27 29 36 43\n";
          combinaciones+="25 28 30 34 42 44\n";
          combinaciones+="25 27 33 39 40 45\n";
          combinaciones+="13 18 36 38 44 47\n";
          combinaciones+="18 24 35 37 42 47\n";
          combinaciones+="26 36 37 41 44 47\n";
          combinaciones+="24 27 33 35 40 48\n";
          combinaciones+="16 28 36 42 45 47\n";
          combinaciones+="13 27 35 39 43 46\n";
          combinaciones+="23 25 29 37 46 48\n";
          combinaciones+="14 22 26 29 44 48\n";
          combinaciones+="02 27 32 39 44 46\n";
          combinaciones+="18 20 24 29 37 42\n";
          combinaciones+="09 13 23 38 43 46\n";
          combinaciones+="04 16 22 37 43 49\n";
          combinaciones+="15 22 28 36 42 47\n";
          combinaciones+="12 18 22 24 27 35\n";
          combinaciones+="13 24 26 30 39 43\n";
          combinaciones+="13 22 23 34 37 43\n";
          combinaciones+="13 18 21 24 26 30\n";
          combinaciones+="04 12 32 38 42 44\n";
          combinaciones+="13 17 26 34 44 46\n";
          combinaciones+="11 29 31 37 38 47\n";
          combinaciones+="15 25 26 28 29 35\n";
          combinaciones+="08 15 20 28 34 37\n";
          combinaciones+="03 09 20 23 25 42\n";
          combinaciones+="19 22 34 42 46 47\n";
          combinaciones+="06 07 23 26 27 42\n";
          combinaciones+="05 15 20 23 25 27\n";
          combinaciones+="10 11 14 18 29 35\n";
          combinaciones+="09 28 30 41 45 46\n";
          combinaciones+="09 10 16 22 31 38\n";
          combinaciones+="12 14 27 29 32 33\n";
          combinaciones+="08 19 27 29 33 43\n";
          combinaciones+="19 25 26 27 39 44\n";
          combinaciones+="02 17 18 21 27 48\n";
          combinaciones+="03 17 20 22 33 42\n";
          combinaciones+="09 19 29 33 44 48\n";
          combinaciones+="10 24 27 38 43 47\n";
          combinaciones+="15 18 22 32 35 42\n";
          combinaciones+="01 08 16 24 28 39\n";
          combinaciones+="12 24 25 26 41 47\n";
          combinaciones+="10 11 19 34 45 47\n";
          combinaciones+="09 13 22 24 26 34\n";
          combinaciones+="05 11 15 33 35 44\n";
          combinaciones+="28 30 31 35 37 38\n";
          combinaciones+="04 06 08 13 14 24\n";
          combinaciones+="01 06 07 14 25 33\n";
          combinaciones+="03 09 16 22 23 28\n";
          combinaciones+="05 08 19 20 23 30\n";
          combinaciones+="03 06 29 32 39 42\n";
          combinaciones+="23 25 32 36 43 46\n";
          combinaciones+="02 15 19 23 35 47\n";
          combinaciones+="08 13 21 25 30 32\n";
          combinaciones+="15 24 28 32 37 40\n";
          combinaciones+="01 17 26 29 38 44\n";
          combinaciones+="08 10 17 18 31 45\n";
          combinaciones+="09 10 13 16 18 29\n";
          combinaciones+="03 05 21 26 36 42\n";
          combinaciones+="04 06 27 31 33 45\n";
          combinaciones+="01 08 20 35 39 42\n";
          combinaciones+="01 11 14 16 33 35\n";
          combinaciones+="03 09 18 29 36 45\n";
          combinaciones+="04 06 07 23 24 38\n";
          combinaciones+="02 13 14 15 32 43";		  

		  
          
          

          //combinaciones+="14 17 24 26 33 38\n";
          //combinaciones+="14 22 25 27 38 44\n";
          //combinaciones+="12 17 21 25 38 45\n";
          //combinaciones+="14 25 27 32 33 49\n";
          //combinaciones+="04 08 31 39 43 46\n";
          //combinaciones+="19 22 23 27 42 46\n";
          //combinaciones+="16 24 28 32 43 46\n";
          //combinaciones+="09 21 22 26 39 47";

    combJugadas.setText(combinaciones);

 }

 void Primitiva_actionPerformed(ActionEvent e) {
    String combinaciones="03 06 09 12 14 30";

    combJugadas.setText(combinaciones);
 }

 void gordo_actionPerformed(ActionEvent e) {
   String combinaciones="03 06 09 12 14 30";

   combJugadas.setText(combinaciones);
 }

 void limpiar_actionPerformed(ActionEvent e) {

    combJugadas.setText("");
    Premios.setText("");
 }
 
 public String getClipboardContents() {
    String result = "";
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    //odd: the Object param of getContents is not currently used
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText =
      (contents != null) &&
      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
    ;
    if ( hasTransferableText ) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      }
      catch (UnsupportedFlavorException ex){
        //highly unlikely since we are using a standard DataFlavor
        System.out.println(ex);
        ex.printStackTrace();
      }
      catch (IOException ex) {
        System.out.println(ex);
        ex.printStackTrace();
      }
    }
    System.out.println("Clip: "+result);
    return result;
  }
/*public void setClipboardContents( String aString ){
    StringSelection stringSelection = new StringSelection( aString );
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents( stringSelection, this );
  }*/


}



