import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.io.*;
import java.util.StringTokenizer;
import java.math.BigInteger;
class miJTextArea extends JTextArea {
   byte []contenido;
   public void setText(byte[] b) {
      super.setText(new String(b));
      contenido=b;
   }
   public void setText(String s) {
      setText(s.getBytes());
   }
   public byte[] getBytes() {
      return contenido;
   }
   public void actualiza() {
      contenido=this.getText().getBytes();
   }
}
public class RSAApplet extends JApplet {
  boolean isStandalone = false;
  long tiempo=0;
  byte [] encriptado;
  static final boolean SALVAR=true;
  static final boolean CARGAR=false;
 BorderLayout borderLayout1 = new BorderLayout();
 JPanel jPanel1 = new JPanel();
 JPanel jPanel2 = new JPanel();
 JLabel jLabel3 = new JLabel();
 JLabel jLabel1 = new JLabel();
 JComboBox lBits = new JComboBox();
 JLabel jLabel2 = new JLabel();
 JTextField p = new JTextField();
 JLabel jLabel4 = new JLabel();
 JTextField q = new JTextField();
 JLabel jLabel5 = new JLabel();
 JTextField n = new JTextField();
 JLabel jLabel6 = new JLabel();
 JTextField e = new JTextField();
 JLabel jLabel7 = new JLabel();
 JLabel jLabel8 = new JLabel();
 JLabel estado = new JLabel();
 JButton salvarClaves = new JButton();
 JButton cargarClaves = new JButton();

 JTextField d = new JTextField();
 JScrollPane jScrollPane1 = new JScrollPane();
 JScrollPane jScrollPane2 = new JScrollPane();
 JButton CargarFichero = new JButton();
 JButton encriptar = new JButton();
 miJTextArea textoPlano = new miJTextArea();
 miJTextArea textoCifrado = new miJTextArea();
 JButton cargarEncriptado = new JButton();
 JButton desencriptar = new JButton();
 JButton salvarPlano = new JButton();
 JButton salvarEncriptado = new JButton();
 JButton limpiarPlano = new JButton();
 JButton limpiarEncriptado = new JButton();
 JButton generarClaves = new JButton();
 RSAPrivateKey privada=null;
 RSAPublicKey publica=null;

   //Inicialización del componente
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    this.setSize(new Dimension(640, 460));
    jLabel3.setFont(new Font("Serif", 1, 20));
    jLabel1.setText("Longitud del Modulo. En Bits:");
    jLabel1.setForeground(Color.black);
    jLabel1.setFont(new Font("Serif", 1, 14));
    jLabel1.setBounds(new Rectangle(10, 0, 195, 21));
    lBits.setBounds(new Rectangle(209, 0, 70, 21));
    jLabel2.setText("p->");
    jLabel2.setForeground(Color.black);
    jLabel2.setFont(new Font("Serif", 1, 14));
    jLabel2.setBounds(new Rectangle(10, 23, 22, 21));
    p.setForeground(Color.black);
    p.setFont(new Font("Serif", 1, 14));
    p.setBounds(new Rectangle(36, 23, 593, 21));
    jLabel4.setText("q->");
    jLabel4.setForeground(Color.black);
    jLabel4.setFont(new Font("Serif", 1, 14));
    jLabel4.setBounds(new Rectangle(10, 55, 22, 21));
    q.setForeground(Color.black);
    q.setFont(new Font("Serif", 1, 14));
    q.setBounds(new Rectangle(36, 55, 593, 21));
    jLabel6.setText("e->");
    jLabel6.setForeground(Color.black);
    jLabel6.setFont(new Font("Serif", 1, 14));
    jLabel6.setBounds(new Rectangle(10, 117, 22, 21));
    e.setBounds(new Rectangle(36, 117, 593, 21));
    jLabel7.setText("d->");
    jLabel7.setForeground(Color.black);
    jLabel7.setFont(new Font("Serif", 1, 14));
    jLabel7.setBounds(new Rectangle(9, 148, 22, 21));
    d.setBounds(new Rectangle(34, 148, 593, 21));
    jScrollPane1.setBounds(new Rectangle(8, 224, 617, 83));
    jScrollPane2.setBounds(new Rectangle(8, 331, 617, 83));
    CargarFichero.setText("Cargar Fichero");
    CargarFichero.setBounds(new Rectangle(40, 201, 126, 20));
    CargarFichero.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
    CargarFichero_actionPerformed(e);
   }
  });
  encriptar.setText("Encriptar");
  encriptar.setBounds(new Rectangle(186, 201, 126, 20));
  cargarEncriptado.setText("Cargar Fichero");
  cargarEncriptado.setBounds(new Rectangle(40, 309, 126, 20));
  desencriptar.setText("Desencriptar");
  desencriptar.setBounds(new Rectangle(188, 309, 126, 20));
  salvarPlano.setText("Salvar");
  salvarPlano.setBounds(new Rectangle(330, 201, 126, 20));
  salvarEncriptado.setText("Salvar");
  salvarEncriptado.setBounds(new Rectangle(332, 309, 126, 20));
  limpiarPlano.setText("Limpiar");
  limpiarPlano.setBounds(new Rectangle(477, 201, 126, 20));
  limpiarEncriptado.setText("Limpiar");
  limpiarEncriptado.setBounds(new Rectangle(477, 309, 126, 20));
  generarClaves.setText("Generar Claves");
  generarClaves.setMargin(new Insets(0, 0, 0, 0));
  generarClaves.setBounds(new Rectangle(282, 0, 104, 21));
  jLabel8.setText("Tiempo Empleado en la ultima Operacion:");
  jLabel8.setForeground(Color.black);
  jLabel8.setFont(new Font("Serif", 1, 14));
  jLabel8.setBounds(new Rectangle(9, 175, 268, 21));
  estado.setBounds(new Rectangle(274, 175, 259, 21));
  salvarClaves.setMargin(new Insets(0, 0, 0, 0));
  salvarClaves.setSelected(true);
  salvarClaves.setText("Salvar Claves");
  salvarClaves.setBounds(new Rectangle(390, 0, 104, 21));
  cargarClaves.setMargin(new Insets(0, 0, 0, 0));
  cargarClaves.setText("Cargar Claves");
  cargarClaves.setBounds(new Rectangle(497, 0, 104, 21));
  cargarClaves.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    cargarClaves_actionPerformed(e);
   }
  });
  salvarClaves.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    salvarClaves_actionPerformed(e);
   }
  });
  estado.setFont(new Font("Serif", 1, 14));
  estado.setForeground(Color.black);
  generarClaves.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    generarClaves_actionPerformed(e);
   }
  });
  limpiarEncriptado.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    limpiarEncriptado_actionPerformed(e);
   }
  });
  limpiarPlano.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    limpiarPlano_actionPerformed(e);
   }
  });
  salvarEncriptado.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    salvarEncriptado_actionPerformed(e);
   }
  });
  salvarPlano.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    salvarPlano_actionPerformed(e);
   }
  });
  desencriptar.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    desencriptar_actionPerformed(e);
   }
  });
  cargarEncriptado.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    cargarEncriptado_actionPerformed(e);
   }
  });
  encriptar.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    encriptar_actionPerformed(e);
   }
  });
  d.setFont(new Font("Serif", 1, 14));
  d.setForeground(Color.black);
  e.setFont(new Font("Serif", 1, 14));
  e.setForeground(Color.black);
  jLabel5.setText("n->");
  jLabel5.setForeground(Color.black);
  jLabel5.setFont(new Font("Serif", 1, 14));
  jLabel5.setBounds(new Rectangle(10, 86, 22, 21));
  n.setBounds(new Rectangle(36, 86, 593, 21));
  n.setFont(new Font("Serif", 1, 14));
  n.setForeground(Color.black);
  lBits.addItem(" 128");
  lBits.addItem(" 256");
  lBits.addItem(" 512");
  lBits.addItem("1024");
  lBits.addItem("2048");
  jLabel3.setForeground(Color.black);
  jLabel3.setText("Generacion de Claves RSA");
  jPanel2.setLayout(null);
  this.getContentPane().add(jPanel1, BorderLayout.NORTH);
  jPanel1.add(jLabel3, null);
  this.getContentPane().add(jPanel2, BorderLayout.CENTER);
  jPanel2.add(jLabel1, null);
  jPanel2.add(lBits, null);
  jPanel2.add(jLabel2, null);
  jPanel2.add(p, null);
  jPanel2.add(jLabel4, null);
  jPanel2.add(q, null);
  jPanel2.add(jLabel5, null);
  jPanel2.add(n, null);
  jPanel2.add(jLabel6, null);
  jPanel2.add(e, null);
  jPanel2.add(jLabel7, null);
  jPanel2.add(d, null);
  jPanel2.add(jScrollPane1, null);
  jScrollPane1.getViewport().add(textoPlano, null);
  jPanel2.add(jScrollPane2, null);
  jScrollPane2.getViewport().add(textoCifrado, null);
  jPanel2.add(CargarFichero, null);
  jPanel2.add(encriptar, null);
  jPanel2.add(cargarEncriptado, null);
  jPanel2.add(desencriptar, null);
  jPanel2.add(salvarPlano, null);
  jPanel2.add(salvarEncriptado, null);
  jPanel2.add(limpiarPlano, null);
  jPanel2.add(limpiarEncriptado, null);
  jPanel2.add(generarClaves, null);
  jPanel2.add(jLabel8, null);
  jPanel2.add(estado, null);
  jPanel2.add(salvarClaves, null);
  jPanel2.add(cargarClaves, null);
  }


  //Construir el applet
  public RSAApplet() {
  }

  //Inicializar el applet
  public void init() {
    try  {
      jbInit();
    }
    catch(Exception e)  {
      e.printStackTrace();
    }
  }
  public String cargaFichero() {
      return cargaFichero(nombreFichero(CARGAR));
  }
   public String cargaFichero(String file) {
      BufferedReader b;
      String r="",l="";

      if (file!=null) {
         try {
            b=new BufferedReader(new FileReader(file));
            while (true) {
               l=b.readLine();
               if (l==null) {
                  break;
               } else {
                  r+=l+'\n';
               }
            }
            b.close();
         } catch (Exception ex) {}
      }
      return r;
   }
   public String nombreFichero(boolean salvar) {
      int accion= (salvar==SALVAR) ? FileDialog.SAVE : FileDialog.LOAD;
      String titulo= (salvar==SALVAR) ? "Salvar en: " : "Cargar Fichero: ";
      Frame f=new Frame();
      FileDialog fichero=new FileDialog(f,titulo,accion);
      fichero.show();
      String res=fichero.getDirectory()+fichero.getFile();
      if (res.equalsIgnoreCase("nullnull")) {
         return null;
      }
      return res;
   }
   public void salvaFichero(String contenido) {
      salvaFichero(nombreFichero(SALVAR), contenido);
   }
   public void salvaFichero(String file, String contenido) {
      FileWriter b;
      String r="",l="";

      try {
         b=new FileWriter(file);
         b.write(contenido);
         b.flush();
         b.close();
      } catch (Exception ex) {}
   }
   public void salvaFicheroBinario(byte s[]) {
      DataOutputStream f;
      try {
         f=new DataOutputStream(new FileOutputStream(nombreFichero(SALVAR)));
         f.write(s);
         f.flush();
         f.close();
      } catch (Exception ex) {}
   }
   public byte [] cargaFicheroBinario() {
      DataInputStream f;
      byte r[]=null;
      try {
         String nombreFichero=nombreFichero(CARGAR);
         File ff=new File(nombreFichero);
         if (ff.length()>65536) {
            ventanaDialogo.vMensaje("Error fichero demasiado largo.");
         }
         f=new DataInputStream(new FileInputStream(nombreFichero));
         int tam=(int) ff.length();
         r=new byte[tam];
         f.readFully(r);
         f.close();
      } catch (Exception ex) {}
      return r;
   }




  //Iniciar el applet
  public void start() {
  }

  //Detener el applet
  public void stop() {
  }

  //Destruir el applet
  public void destroy() {
  }

  //Obtener información del applet
  public String getAppletInfo() {
    return "Información del applet";
  }

  //Obtener información del parámetro
  public String[][] getParameterInfo() {
    return null;
  }

  //Método principal
  public static void main(String[] args) {
    RSAApplet applet = new RSAApplet();
    applet.isStandalone = true;
    JFrame frame = new JFrame();
    frame.setTitle("Generación de Claves RSA");
    frame.getContentPane().add(applet, BorderLayout.CENTER);
    applet.init();
    applet.start();
    frame.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
          System.exit(0);
       }
    });
    frame.setSize(applet.getSize().width, applet.getSize().height+50);
    frame.setResizable(false);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    frame.setVisible(true);
  }
  // static initializer for setting look & feel
  static {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {}
  }


 String limpia(String s) {
    String r=""; char ch;
    if (s==null) {
       return null;
    }
    for(int i=0; i<s.length(); i++) {
       ch=s.charAt(i);
       if ('0'<=ch && ch<='9') {
          r+=ch;
       }
    }
    return r;
 }



/* void lBits_keyTyped(KeyEvent e) {
    String s=lBits.getText();
    s=limpia(s);
    try {
       lBytes.setText(Integer.toString(Integer.parseInt(s)/8));
    } catch (Exception ex) {
       lBytes.setText("");
    }
 }*/

 void CargarFichero_actionPerformed(ActionEvent e) {
    byte []s=this.cargaFicheroBinario();
    if (s!=null) {
       this.textoPlano.setText(s);
    }
 }

 void encriptar_actionPerformed(ActionEvent e) {
    textoPlano.actualiza();
    byte[] texto=textoPlano.getBytes();

    iniciarTimer();
    if (texto.length<=0) {
       return;
    }
    if (privada!=null && publica!=null) {
       this.textoCifrado.setText(privada.encriptaBloque(texto));
    }
    this.escribeTiempo();
 }

 void salvarPlano_actionPerformed(ActionEvent e) {
    textoPlano.actualiza();
    this.salvaFicheroBinario(this.textoPlano.getBytes());
 }

 void limpiarPlano_actionPerformed(ActionEvent e) {
    this.textoPlano.setText("");
 }

 void cargarEncriptado_actionPerformed(ActionEvent e) {
    byte[] s=this.cargaFicheroBinario();
    if (s!=null) {
       this.textoCifrado.setText(s);
    }
 }
 boolean comparaArrays(byte[] a, byte[] b) {
    for(int i=0; i<a.length; i++) {
       if (a[i]!=b[i]) {
          ventanaDialogo.vMensaje("Error no coinciden matrices");
       }
    }
    return true;
 }

 void desencriptar_actionPerformed(ActionEvent e) {
    byte cifrado[]=textoCifrado.getBytes();

    this.iniciarTimer();
    if (cifrado.length<=0) {
       return;
    }
    if (privada!=null && publica!=null) {
       textoPlano.setText(publica.desencriptaBloque(cifrado));
    }
    this.escribeTiempo();
 }

 void salvarEncriptado_actionPerformed(ActionEvent e) {
    this.salvaFicheroBinario(this.textoCifrado.getBytes());
 }
 void limpiarEncriptado_actionPerformed(ActionEvent e) {
    this.textoCifrado.setText("");
 }
 void iniciarTimer() {
    tiempo=System.currentTimeMillis();
 }
 void escribeTiempo() {
     this.estado.setText(((System.currentTimeMillis()-tiempo)/1000)+" s.");
 }
 void generarClaves_actionPerformed(ActionEvent e) {
    RSAKey []k=new RSAKey[2];
    this.n.setText(""); this.n.repaint();
    this.p.setText(""); this.p.repaint();
    this.q.setText(""); this.q.repaint();
    this.e.setText(""); this.e.repaint();
    this.d.setText(""); this.e.repaint();
    String lonModulo=(String) this.lBits.getSelectedItem();
    genParClaves gpc=new genParClaves(Integer.parseInt(lonModulo.trim()));
    iniciarTimer();
    while (!gpc.terminado) {
       try {
          Thread.currentThread().sleep(1000);
       } catch (Exception ex) {}
    }
    escribeTiempo();
    privada=(RSAPrivateKey)gpc.claves[0];
    publica=(RSAPublicKey)gpc.claves[1];
    muestraValores();
 }
 void muestraValores() {
    this.n.setText(privada.modulo.toString());
    this.p.setText(privada.p.toString());
    this.q.setText(privada.q.toString());
    this.e.setText(privada.e.toString());
    this.d.setText(privada.exponente.toString());
 }

  void cargarClaves_actionPerformed(ActionEvent ex) {
     String t=this.cargaFichero(), linea;
     StringTokenizer stLin=new StringTokenizer(t, "\n");
     BigInteger p,q,n,e,d;
     p=q=n=e=d=null;
     while(stLin.hasMoreTokens()) {
        linea=stLin.nextToken();
        StringTokenizer stPalab=new StringTokenizer(linea, "=");
        String next;
        next=stPalab.nextToken();
        if (next.equalsIgnoreCase("p")) {
           p=new BigInteger(stPalab.nextToken());
        } else if (next.equalsIgnoreCase("q")) {
           q=new BigInteger(stPalab.nextToken());
        } else if (next.equalsIgnoreCase("n")) {
           n=new BigInteger(stPalab.nextToken());
        } else if (next.equalsIgnoreCase("e")) {
           e=new BigInteger(stPalab.nextToken());
        } else if (next.equalsIgnoreCase("d")) {
           d=new BigInteger(stPalab.nextToken());
        }
     }
     if (p!=null && q!=null && n!=null && e!=null &&  d!=null) {
        this.privada=new RSAPrivateKey(n,d,e,p,q);
        this.publica=new RSAPublicKey(n,e);
        this.muestraValores();
     } else {
        ventanaDialogo.vMensaje("Error leyendo fichero de claves");
     }

  }

  void salvarClaves_actionPerformed(ActionEvent e) {
     String t="";
     if (this.privada!=null && this.publica!=null) {
        t+="n="+this.privada.modulo+'\n';
        t+="p="+this.privada.p+'\n';
        t+="q="+this.privada.q+'\n';
        t+="e="+this.privada.e+'\n';
        t+="d="+this.privada.exponente+'\n';
        this.salvaFichero(t);
     } else {
        ventanaDialogo.vMensaje("Error no hay claves que salvar. Genere primero un par.");
     }

  }



}
