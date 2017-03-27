package jnapster;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import jnapster.NapsterClient;
import javax.swing.*;
//import com.sun.java.swing.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.Vector;
import jnapster.NapsterSong;
import util.*;

//import com.sun.java.swing.UIManager;
public class miNapster extends JApplet implements lineFormater{
   boolean isStandalone = false;
   NapsterClient np;
   JPanel jPanel1 = new JPanel();
   JTextField user = new JTextField();
   JLabel jLabel1 = new JLabel();
   JPasswordField passWord = new JPasswordField();
   JLabel jLabel2 = new JLabel();
   JLabel jLabel3 = new JLabel();
   JButton jButton1 = new JButton();
   JLabel jLabel4 = new JLabel();
   JTextField cancion = new JTextField();
   miJList jList2 = new miJList();
   miJList jList1 = new miJList();
   JLabel jLabel5 = new JLabel();
   JTextField excepto = new JTextField();
   JLabel jLabel6 = new JLabel();
   miJList status = new miJList();
 javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
 javax.swing.JTextField tEspera = new javax.swing.JTextField();
 javax.swing.JLabel jLabel8 = new javax.swing.JLabel();

//Get a parameter value

   public String getParameter(String key, String def) {
      return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
   }

   //Construct the applet

   public miNapster() {
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
   String spaces(int n) {
      String res="";
      for(int i=0; i<n; i++) {
         res+=' ';
      }
      return res;
   }

   public String formatLine(String l) {
       String res="";
       StringTokenizer st = new StringTokenizer(l, " \t");
       res+=st.nextToken();
       res+=spaces(30-res.length());
       res+=' ';
       String dirIp=""+st.nextToken();
       res+=dirIp;
       res+=spaces(15-dirIp.length());
       res+=' ';
       String puerto=""+st.nextToken();
       res+=puerto;
       res+=spaces(6-puerto.length());
       res+=' ';
       return res;
   }




   private void jbInit() throws Exception {
      this.getContentPane().setLayout(null);
      this.setSize(630,460);
      jPanel1.setBounds(new Rectangle(0, 0, 457, 29));
      user.setText("fjsagan");
      jLabel1.setText("Nombre de Usuario:");
      passWord.setText("ancrimo1");
      jLabel2.setText("Password:");
      jLabel3.setText("Lista de Servidores:");
      jButton1.setText("Buscar");
      jButton1.setMargin(new Insets(0, 0, 0, 0));
      jButton1.setBounds(new Rectangle(560, 152, 50, 18));
      jButton1.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
        jButton1_actionPerformed(e);
       }
      });
      jLabel4.setText("Buscar:");
      jLabel4.setBounds(new Rectangle(9, 152, 47, 18));
      cancion.setText("Camel");
      cancion.setBounds(new Rectangle(55, 152, 496, 18));
      jList2.setFont(new Font("Monospaced", 0, 14));
      jList2.setBounds(new Rectangle(9, 198, 603, 147));
      jList1.setFont(new Font("Monospaced", 0, 14));
      jList1.setBounds(new Rectangle(9, 30, 603, 121));
      jLabel5.setText("Status:");
      jLabel5.setBounds(new Rectangle(7, 345, 83, 17));
      excepto.setBounds(new Rectangle(84, 175, 399, 18));
      status.setFont(new Font("Monospaced", 0, 14));
      status.setBounds(new Rectangle(8, 360, 602, 67));
      jLabel6.setText("No contenga:");
      jLabel6.setBounds(new Rectangle(6, 175, 76, 18));
      tEspera.setHorizontalAlignment(JTextField.RIGHT);
  tEspera.setText("20");
  this.getContentPane().add(jPanel1, null);
      jPanel1.add(jLabel1, null);
      jPanel1.add(user, null);
      jPanel1.add(jLabel2, null);
      jPanel1.add(passWord, null);
      jPanel1.add(jLabel3, null);
      this.getContentPane().add(jButton1, null);
      this.getContentPane().add(jLabel4, null);
      this.getContentPane().add(cancion, null);
      this.getContentPane().add(jList2, null);
      this.getContentPane().add(jList1, null);
  this.getContentPane().add(jLabel5, null);
  this.getContentPane().add(excepto, null);
      this.getContentPane().add(jLabel6, null);
  this.getContentPane().add(status, null);
  this.getContentPane().add(jLabel7, null);
  this.getContentPane().add(tEspera, null);
  this.getContentPane().add(jLabel8, null);
      File f=new File("napster_servers.lst");
  jLabel8.setText("s.");
  jLabel8.setBounds(new Rectangle(599, 175, 22, 18));
  tEspera.setBounds(new Rectangle(552, 175, 43, 18));
  jLabel7.setText("T.Espera.:");
  jLabel7.setBounds(new Rectangle(486, 175, 61, 18));
      if (f.exists()) {
         this.jList1.cargaFichero("napster_servers.lst");
      } else {
         this.jList1.cargaFichero();
      }
      this.jList1.sort();
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
      miNapster applet = new miNapster();
      applet.isStandalone = true;
      JFrame frame = new JFrame();
      frame.setTitle("Applet Frame");
      frame.getContentPane().add(applet, BorderLayout.CENTER);
      applet.init();
      applet.start();
      frame.setSize(630,460);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      frame.setVisible(true);
   }
   Vector formaLista(String s) {
      StringTokenizer st = new StringTokenizer(s, ",;\t");
      Vector res=new Vector();

      while(st.hasMoreTokens()) {
         res.addElement(st.nextToken());
      }
      return res;
   }
   boolean estaEnExcep(String fn, Vector lista) {
      String FN=fn.toUpperCase();

      for(int i=0; i<lista.size(); i++) {
         if (FN.indexOf(((String) lista.elementAt(i)).toUpperCase())>=0) {
            return true;
         }
      }
      return false;
   }         






   void buscaCancion(String cancion, String server) {
      String excpt=null;
      int tespera=20;
      Vector listaExcpt=new Vector();

      if (excepto.getText()!=null && excepto.getText()!="") {
         excpt=excepto.getText();
      }
      this.status.addItem("... Buscando en el Servidor: "+server+" ...");
      StringTokenizer st=new StringTokenizer(server);
      System.out.println("----------------------------------------------------");
      System.out.println("Buscando en: "+st.nextToken());
      System.out.println("----------------------------------------------------");
      try {
         np=new NapsterClient("find "+"\""+cancion+"\"");
         np.server=st.nextToken();
         np.port=Integer.parseInt(st.nextToken());
         np.user=this.user.getText();
         np.password=this.passWord.getPassword().toString();
         np.comienzo(this.status);
         np.start();
         String s=this.tEspera.getText();
         if (s!=null && s.length()>0) {
            tespera=Integer.parseInt(s);
         }
         for(int i=0; i<Integer.parseInt(this.tEspera.getText()); i++) {
            Thread.currentThread().sleep(1000); // a dormir 1 s.
            if (np.terminado) {
               break;
             }
         } // en total esperamos 10 s. máximo
      } catch (Exception ex) {
      } finally {
         if (!np.terminado) {
            np.stop();
         } else {
            np.setCmdLine("quit");
         }
         if (np.songs!=null) {
            this.jList2.addItem(" Resultado búsqueda servidor "+server);
            this.jList2.addItem("=====================================================");
            if (excpt!=null && excpt.length()>0) {
               listaExcpt=formaLista(excpt);
            }
            for(int i=0; i<np.songs.size(); i++) {
               String fileName=((NapsterSong) np.songs.elementAt(i)).filename;
               if (listaExcpt.size()>0 && estaEnExcep(fileName, listaExcpt)) {
               } else {
                  this.jList2.addItem(((NapsterSong) np.songs.elementAt(i)).filename);
                  System.out.println(((NapsterSong) np.songs.elementAt(i)).filename);
               }
            }
            this.jList2.addItem("=====================================================");
//            this.jList2.sort();
         } else {
            this.jList2.addItem(server);
            this.jList2.addItem("Tiempo de búsqueda agotado: "+tespera+" s.");
         }
         this.repaint();
      }
   }




 void jButton1_actionPerformed(ActionEvent e) {
    this.jList2.borrar();
    if (cancion.getText()!=null) {
       for(int i=0; i<this.jList1.getItemCount(); i++) {
          if (this.jList1.isSelectedIndex(i)) {
             try {
                buscaCancion(cancion.getText(), this.jList1.getItem(i));
             } catch (Exception ex) {}   
          }   
       }
    }
    util.ventanaDialogo.vMensaje("Búsqueda Terminada");
 }
}
