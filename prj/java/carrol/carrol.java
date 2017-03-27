import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.lang.Math;
import java.util.Vector;
import com.borland.jbcl.layout.*;



/**
 * Título:
 * Descripcion:
 * Copyright:    Copyright (c) 2001
 * Empresa:
 * @author
 * @version 1.0
 */

public class carrol extends Applet {
  buscador busc=null;
  informador infor=null;

  boolean isStandalone = false;
  JButton inicio = new JButton();
  JLabel jLabel1 = new JLabel();
  JTextField maximo = new JTextField();
  miJList listaTriangulos = new miJList();
  JLabel jLabel3 = new JLabel();
  JLabel numeroActual = new JLabel();
  JLabel jLabel4 = new JLabel();
  JTextField minimo = new JTextField();
  JLabel jLabel5 = new JLabel();
  JTextField comienzo = new JTextField();
  JCheckBox salvarFichero = new JCheckBox();
 JButton parar = new JButton();

  /**Obtener el valor de un parámetro*/
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  /**Construir el applet*/
  public carrol() {
  }
  /**Inicializar el applet*/
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Inicialización de componentes*/
  private void jbInit() throws Exception {
    inicio.setMargin(new Insets(0, 0, 0, 0));
    inicio.setText("Inicio");
    inicio.setBounds(new Rectangle(187, 340, 63, 27));
    inicio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        inicio_actionPerformed(e);
      }
    });
    this.setSize(new Dimension(600,420));
    this.setLayout(null);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel1.setText("Número Actual:");
    jLabel1.setBounds(new Rectangle(100, 278, 118, 20));
    maximo.setText("1000");
    maximo.setHorizontalAlignment(SwingConstants.RIGHT);
    maximo.setBounds(new Rectangle(233, 43, 107, 21));
    maximo.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        maximo_actionPerformed(e);
      }
    });
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel3.setText("Final");
    jLabel3.setBounds(new Rectangle(91, 44, 117, 20));
    listaTriangulos.setBounds(new Rectangle(91, 106, 366, 127));
    numeroActual.setBounds(new Rectangle(88, 281, 118, 20));
    numeroActual.setBounds(new Rectangle(203, 282, 118, 20));
    numeroActual.setFont(new java.awt.Font("Dialog", 1, 14));
    numeroActual.setText("  ");
    jLabel4.setBounds(new Rectangle(55, 17, 117, 20));
    jLabel4.setBounds(new Rectangle(51, 53, 117, 20));
    jLabel4.setText("Min. Triangulos:");
    jLabel4.setFont(new java.awt.Font("Dialog", 1, 14));
    minimo.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        minimo_actionPerformed(e);
      }
    });
    minimo.setBounds(new Rectangle(184, 16, 107, 21));
    minimo.setHorizontalAlignment(SwingConstants.RIGHT);
    minimo.setText("3");
    minimo.setBounds(new Rectangle(186, 51, 107, 21));
    minimo.setBounds(new Rectangle(184, 16, 107, 21));
    minimo.setBounds(new Rectangle(233, 71, 107, 21));
    jLabel5.setBounds(new Rectangle(91, 44, 117, 20));
    jLabel5.setBounds(new Rectangle(91, 15, 117, 20));
    jLabel5.setText("Comienzo");
    jLabel5.setFont(new java.awt.Font("Dialog", 1, 14));
    comienzo.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        comienzo_actionPerformed(e);
      }
    });
    comienzo.setBounds(new Rectangle(235, 43, 107, 21));
    comienzo.setHorizontalAlignment(SwingConstants.RIGHT);
    comienzo.setText("1");
    comienzo.setBounds(new Rectangle(233, 15, 107, 21));
    comienzo.setBounds(new Rectangle(235, 43, 107, 21));
    comienzo.setBounds(new Rectangle(233, 13, 107, 21));
    salvarFichero.setSelected(true);
    salvarFichero.setText("Salvar en Fichero:");
    salvarFichero.setFont(new java.awt.Font("Dialog", 1, 12));
    salvarFichero.setBounds(new Rectangle(92, 245, 160, 28));
    parar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        parar_actionPerformed(e);
      }
    });
  parar.setBounds(new Rectangle(239, 340, 63, 27));
  parar.setText("Parar");
  parar.setMargin(new Insets(0, 0, 0, 0));
  parar.setBounds(new Rectangle(285, 339, 63, 27));
  parar.setBounds(new Rectangle(239, 340, 63, 27));
  parar.setBounds(new Rectangle(283, 340, 63, 27));
  this.add(listaTriangulos, null);
    this.add(jLabel1, null);
    this.add(numeroActual, null);
    this.add(jLabel4, null);
    this.add(minimo, null);
    this.add(salvarFichero, null);
    this.add(jLabel5, null);
    this.add(maximo, null);
    this.add(jLabel3, null);
    this.add(comienzo, null);
  this.add(inicio, null);
  this.add(parar, null);



    this.setSize(new Dimension(600, 385));
    this.setVisible(true);
    numeroActual.setBounds(new Rectangle(88, 281, 118, 20));
    numeroActual.setBounds(new Rectangle(225, 278, 118, 20));
    jLabel4.setBounds(new Rectangle(55, 17, 117, 20));
    jLabel4.setBounds(new Rectangle(91, 72, 117, 20));
    jLabel5.setBounds(new Rectangle(91, 44, 117, 20));
    jLabel5.setBounds(new Rectangle(91, 14, 117, 20));


  }
  /**Iniciar el applet*/
  public void start() {
  }
  /**Detener el applet*/
  public void stop() {
  }
  /**Destruir el applet*/
  public void destroy() {
  }
  /**Obtener información del applet*/
  public String getAppletInfo() {
    return "Información del applet";
  }
  /**Obtener información del parámetro*/
  public String[][] getParameterInfo() {
    return null;
  }
  /**Método Main*/
  public static void main(String[] args) {
    carrol applet = new carrol();
    applet.isStandalone = true;
    Frame frame = new Frame();
    //EXIT_ON_CLOSE == 3
    frame.setTitle("Marco del applet");
    frame.add(applet, BorderLayout.CENTER);
    applet.init();
    applet.start();
    frame.setSize(600,420);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
    frame.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
          System.exit(0);
       }
    });

    frame.setVisible(true);
  }

  //inicializador estático definir el aspecto
  static {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch(Exception e) {
    }
  }




  void inicio_actionPerformed(ActionEvent e) {
     busc=new buscador();
     infor=new informador(busc);
  }
  void numTriangulos_actionPerformed(ActionEvent e) {

  }

  void maximo_actionPerformed(ActionEvent e) {

  }

  void minimo_actionPerformed(ActionEvent e) {

  }

  void comienzo_actionPerformed(ActionEvent e) {

  }
  class informador extends Thread {
     buscador b=null;
     public informador(buscador b) {
        this.b=b;
        this.start();
     }
     public void run() {
        while (!b.terminado) {
           numeroActual.setText(Long.toString(b.actual));
           try {
              this.sleep(1000);
           } catch (InterruptedException ex) {
           }
        }
        numeroActual.setText(Long.toString(b.actual));
        busc=null;
        infor=null;
     }
  }


  class buscador extends Thread  {
     public boolean terminado=false;
     public long actual=0;

     public buscador() {
        this.start(); // lanza el Thread
     }
     boolean esPrimo(long n) {
        long r=((long)Math.sqrt(n))+1;
        for(long j=2; j<r; j++) {
           if (n % j==0) {
              return false;
           }
        }
        return true;
     }




    void calcular() {
       long num, i, j, m, ab[]={0,0}, numTriangulos;
       int numPrimo=0;
       Vector triangulos=new Vector();
       long primos[];
       double c;

       num=Long.parseLong(maximo.getText());
 //      numTriangulos=Long.parseLong(maxTriangulos.getText());
       m=((long) (num/Math.log(num)) )+1; // máximo número de primos existentes entre 1 y num
       primos=new long[(int)m];

       for(i=2; i<num; i++) {
          if (esPrimo(i)) {
             primos[numPrimo++]=i;
             long maxCombinaciones=(long) Math.pow(2.0,numPrimo);
             for(j=0; j<maxCombinaciones; j++) {
                ab=obtenAB(primos,j);
                c=Math.sqrt(Math.pow(ab[0],2)+Math.pow(ab[1],2));
                if ((c-(long)c)==0.0) { //es entero
                   triangulos.addElement(""+ab[0]+";"+ab[1]+";"+c);
 //                  if (triangulos.size()>=numTriangulos) {
 //                     for(int k=0; k<triangulos.size(); k++) {
 //                        listaTriangulos.addItem(""+i+"->"+triangulos.elementAt(k));
 //                     }
 //                  }
                }
             }
          }
       }

    }

    long[] factoresPrimos(long num) {
       Vector f=new Vector();
       int i;

       long r=(long) (Math.sqrt(num) +1);
       for(i=2; i<=r; i++) {
          while (num % i==0) {
             f.addElement(new Long(i));
             num/=i;
          }
       }
       long []res=new long[f.size()];
       for (i=0; i<f.size(); i++) {
          res[i]=((Long)f.elementAt(i)).longValue();
       }
       return res;
    }
    char[] toBin(long numDecimal, int lon) {
       char[] resultado=new char[lon];
       int resto;
       long cociente;
       int i=0;
       for(int j=0; j<lon; j++) {
          resultado[i]='0';
       }
       while (numDecimal>=2) {
          resto =(int) (numDecimal % 2);
          if (resto==1) {
             numDecimal--;
          }
          cociente= numDecimal / 2;
          if (resto==0) {
             resultado[i++]='0';
          } else {
             resultado[i++]='1';
          }
          numDecimal=cociente;
       }
       resultado[i]=(char)numDecimal;
       return resultado;
    }
    long[] obtenAB(long[] primos, long combi) {
       long r[]={1,1};
       char binario[]=toBin(combi, primos.length);
       for(int i=0; i<binario.length; i++) {
          if (binario[i]=='0') {
             r[0]*= primos[i];
          } else {
             r[1]*=primos[i];
          }
       }
       return r;
    }

     void probarFactores(long num) {
         long[] factores=factoresPrimos(num), ab;
         Vector triActual=new Vector();

         long i;
         int k;
         long numCombinaciones=(long) Math.pow(2,factores.length)-1;
         long minTriangulos=Long.parseLong(minimo.getText());
         for(i=1; i<numCombinaciones; i++) {
            ab=obtenAB(factores, i);
            double c=Math.sqrt(Math.pow(ab[0],2)+Math.pow(ab[1],2));
            if (c - (long) c==0) {
               String elem=""+num+"->"+ab[0]+":"+ab[1]+":"+((long)c);
               String numero=""+ab[0];
               for (k=0; k<triActual.size(); k++) {
                  String m=(String) triActual.elementAt(k);
                  if (m.indexOf(numero)>=0) {
                     break;
                  }
               }
               if (k>=triActual.size()) {
                  triActual.addElement(elem);
               }
            }
         }
         if (triActual.size()>=minTriangulos) {
            for(k=0; k<triActual.size(); k++) {
               listaTriangulos.addItem((String) triActual.elementAt(k));
            }
         }
     }
     void buscar() {
        actual=Long.parseLong(comienzo.getText());
        long limite=Long.parseLong(maximo.getText());

        for(; actual<=limite & !terminado; actual++) {
           probarFactores(actual);
        }
        if (salvarFichero.isSelected()) {
           listaTriangulos.salvaFichero("resultado.carrol");
        }
     }

     public void run() {
        listaTriangulos.borrar();
        buscar();
        listaTriangulos.addItem("Terminado");
        terminado=true;
     }
  }

  void parar_actionPerformed(ActionEvent e) {
     if (busc!=null) {
        busc.terminado=true;
     }
     busc=null;
     infor=null;
     listaTriangulos.borrar();
     listaTriangulos.addItem("Terminado por el usuario");
  }


}
