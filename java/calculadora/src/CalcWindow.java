/******************************************************************************/
/******************         CalcWindow.java     *******************************/
/************4******************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import layout.XYLayout;
import layout.XYConstraints;
import jclass.bwt.*;
import jclass.bwt.JCActionEvent;
import javax.swing.*;
import mieval.evaluador;
import mieval.tipo.variable;
import mieval.notificado;
import java.lang.String;
import java.io.*;
import java.util.Vector;
import mieval.SyntaxErrorException;
import mieval.ctes_eval;
import mieval.dialogo; 

/*class miJList extends JList {
   Vector actListeners=new Vector();
   Vector lista=new Vector();
   public miJList() {
      super();
      this.setListData(lista);
      lista.addElement("Mierda");
   }
   public void addActionListener(ActionListener a) {
      actListeners.addElement(a);
   }
   public void removeActionListener(ActionListener a) {
      actListeners.removeElement(a);
   }
   class miMouseAdapter extends MouseAdapter {
      public void mousePressed(MouseEvent e) {
         for(int i=0; i<actListeners.size(); i++) {
            ((ActionListener) actListeners.elementAt(i)).actionPerformed(
              new ActionEvent(miJList.this, ActionEvent.ACTION_PERFORMED, null));
         }
      }
   }
   public void addItem(String s) {
      lista.addElement(s);
      this.setListData(lista);
      repaint();
   }
   public String getSelectedItem() {
      return this.getSelectedValue().toString();
   }
   public int getItemCount() {
      return this.getModel().getSize();
   }
   public String getItem(int i) {
      return (String) lista.elementAt(i);
   }
   public void select(int i) {
      this.setSelectedIndex(i);
   }
   public void removeElement(int i) {
      lista.removeElementAt(i);
      this.setListData(lista);
      repaint();
   }
}*/
class miJList extends JScrollPane {
   Vector actListeners=new Vector();
   //Vector lista=new Vector();
   DefaultListModel lista = new DefaultListModel();
   JList jlista=new JList();
   public miJList() {
      super();
      getViewport().add(jlista, null);
      createHorizontalScrollBar();
      createVerticalScrollBar();
      setAutoscrolls(true);
      setDoubleBuffered(true);
      //jlista.setListData(lista);
      jlista.setModel(lista);
//      lista.addElement("   ");
      jlista.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            if (e.getClickCount()>=2) {
               for(int i=0; i<actListeners.size(); i++) {
                  ((ActionListener) actListeners.elementAt(i)).actionPerformed(
                    new ActionEvent(miJList.this, ActionEvent.ACTION_PERFORMED, null));
               }
            }
         }
      });

   }
   public void addActionListener(ActionListener a) {
      actListeners.addElement(a);
   }
   public void removeActionListener(ActionListener a) {
      actListeners.removeElement(a);
   }

   public void addItem(String s) {
      lista.addElement(s);
      jlista.repaint();
   }
   public String getSelectedItem() {
      return jlista.getSelectedValue().toString();
   }
   public int getItemCount() {
      return jlista.getModel().getSize();
   }
   public String getItem(int i) {
      return (String) lista.elementAt(i);
   }
   public void select(int i) {
      if (jlista!=null) {
         jlista.setSelectedIndex(i);
      }
   }
   public void removeElement(int i) {
      lista.removeElementAt(i);
      repaint();
   }
   public void setFont(Font f) {
      if (jlista!=null) {
         jlista.setFont(f);
      }
   }
   public void setBounds(Rectangle r) {
      if (jlista!=null) {
         jlista.setBounds(r);
      }
   }
   public int getSelectedIndex() {
      if (jlista!=null) {
         return jlista.getSelectedIndex();
      }
      return -1;
   }

}
/******************************************************************************/
/******************  esta clase ha sido generada casi automáticamente *********/
/******************  con la ayuda de herramientas RAD            **************/
/******************************************************************************/
public class CalcWindow extends JPanel implements mieval.impresor {
  XYLayout xYLayout1 = new XYLayout();
  boolean isStandalone = false;
  listaVariables funciones = new listaVariables("Función/Op","Ejemplo");
  listaVariables variables = new listaVariables("Variables","Valor");
  JTextField expresion = new JTextField();
  JTextField resultado = new JTextField();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  JLabel label4 = new JLabel();
  JLabel label5 = new JLabel();
  JButton historicoAdd = new JButton();
  JButton historicoConcatenar = new JButton();
  JButton funcionesConcatenar = new JButton();
  JButton funcionesAdd = new JButton();
  JButton calcular = new JButton();
  JButton borrarExpresion = new JButton();
  JButton borrarResultado = new JButton();
  JButton salvarVariables = new JButton();
  JButton cargarVariables = new JButton();
  JCheckBox actualizarVariables = new JCheckBox();
//  JScrollPane jScrollPane1 = new JScrollPane();
  miJList historico = new miJList();
  miJList salidaProgramas = new miJList();
  evaluador eval= new evaluador();
  int initWidth=1280;



  //Inicialización de Componentes
  public void jbInit() throws Exception{
    xYLayout1.setWidth(initWidth);
    xYLayout1.setHeight(600);
    this.setSize(initWidth,600);
    funciones.setFont(new Font("Dialog", 1, 12));

    String s="+ , 3+3->6 | 'ho'+'la'->'hola' \n++ , pre/post-incremento: ++a"
    +" | a++\n- , resta: 3- 3->0 | 'hola'-'la'->'hola'\n-- , pre/post-"
    +"decremento: --a | a--\n>= , mayor o igual: 3>=2->true\n"
    +"<= , menor o igual: 3<=2->false\nmod , módulo: 4 mod 3->1\n% , idem"
    +" anterior\nnot , negación lógica: not true->false | not false->true\n!"
    +" , idem anterior\n"
    +"or , 'o' lógico: true or false->true | false or false->false\n| , idem"
    +" anterior\n|| , idem anterior\n!= , distinto: 3!=3->false\n<> , idem"
    +" anterior\n== , comprobación igualdad: 4==5->false | 5==5->true\n"
    +"? , ultimo valor calculado: ? + 3\nabs , valor absoluto: abs(-5.5)->5.5"
    +"\nacos , arcocoseno: acos 0->pi/2\nand | && | & , 'y' logico: x and y\n"
    +"asen , arcoseno: asen 1->pi/2\n"
    +"atan , arcotangente: atan 1->pi/4\natan2 , atan2(3;4)\nceil , siguiente"
    +" entero: ceil 3.45->4\ncos , coseno: cos 0\ncosh , coseno hiperbolico: "
    +"cosh x\nderiv , derivada: deriv('x^3')\n"
    +"derivada , derivada: deriv('x^3')\ndim , crear matrices: dim a[3;3;3]\n"
    +"div , division entera: 4 div 3\ne , 2.718281828459045\neval , eval('3+5')"
    +"->8\nexec , ejecuta programa: exec('prg';a;b;c)\n"
    +"exp , exponencial: exp(2)->e^2\nfalse , falso: 4!=4->false\nfloor , "
    +"anterior entero: floor(4.5)->4\n"
    +"for , for(<lista-coma-insts.>;<condicion>;<lista-coma-insts>) { <lista-"
    +"instrs.> }\nif , if (<condicion>) { <lista-insts.> } [elseif { "
    +"<lista-insts.> }...] [else {<lista-insts.>}]\n"
    +"iif , funcion condicional: iif(3>5;3;5)->5\nint , parte entera: int 3.5"
    +" ->3\nintegral , integral definida: integral('x^2';0;2)->9\nlen , "
    +"longitud elemento: len('hola')->4\n"
    +"ln , logaritmo neperiano: ln e->1\nlog , logaritmo base: log(2;2^3)->3\n"
    +"pi , 3.141592653589793\nsen , seno: sen(pi/2)->1\nsenh , seno"
    +" hiperbólico: senh 1-> 1.1752011936\n"
    +"simp , simplifica expresiones: simp('x*x*x*x*x*x*x')->x^7\nsqrt , raíz"
    +" cuadrada: sqrt 2->1.4142135624\nsubstr , subcadena: substr('hola';0;1)"
    +"->'ho'\ntan , tangente: tan(pi/4)->1\n"
    +"tanh , tangente hiperbólica: tanh 1->0.761594156\ntolower , convierte"
    +" minúsculas: tolower('FRAN')->'fran'\ntoupper , convierte mayúsculas: "
    +"toupper('fran')->'FRAN'\n"
    +"true , verdadero: 3==4->true\ntype , tipo de la variable: type('hola')"
    +"->'S'\nwhile , while(<condicion>) { <lista-insts.> }\n";
    funciones.setItemsStrings(jclass.util.JCUtilConverter.toStringList(s, '\n'));
  funciones.addActionListener(new jclass.bwt.JCActionListener() {
   public void actionPerformed(JCActionEvent e) {
    funciones_actionPerformed(e);
   }
  });

    funciones.setColumnWidths(jclass.util.JCUtilConverter.toIntList(new
                                                      String("85\n650"), '\n'));
    variables.setColumnWidths(jclass.util.JCUtilConverter.toIntList(new
                                                   String("160\n30000"), '\n'));
    variables.setFont(new Font("Dialog", 1, 12));
  expresion.setFont(new java.awt.Font("Dialog", 1, 18));
  resultado.setFont(new java.awt.Font("Dialog", 1, 18));
    variables.addActionListener(new jclass.bwt.JCActionListener() {
      public void actionPerformed(JCActionEvent e) {
        variables_actionPerformed(e);
      }
    });
    expresion.addKeyListener(new CalcWindow_expresion_keyAdapter(this));
    expresion.addActionListener(new CalcWindow_expresion_actionAdapter(this));

    label2.setFont(new Font("Dialog", 1, 12));
    label2.setText("Histórico de Comandos");
    label3.setFont(new Font("Dialog", 1, 12));
    label3.setText("Comando:");
    label4.setFont(new Font("Dialog", 1, 12));
    label4.setText("Resultado:");
    label5.setFont(new Font("Dialog", 1, 12));
    label5.setText("Salida de los programas:");
    historicoAdd.setActionCommand("historicoAdd");
    historicoAdd.addActionListener(new CalcWindow_historicoAdd_actionAdapter(
                                                                         this));
    historicoAdd.setToolTipText("Sustituir Expresión por Selección");
    calcular.setActionCommand("calcular");
    calcular.addActionListener(new CalcWindow_calcular_actionAdapter(this));
    calcular.setFont(new Font("Dialog", 1, 12));
    calcular.setText("Calcular");
    calcular.setToolTipText("Evaluar la expresi¢n");
    borrarExpresion.setActionCommand("borrarExpresion");
    borrarExpresion.addActionListener(new
                                CalcWindow_borrarExpresion_actionAdapter(this));
    borrarExpresion.setFont(new Font("Dialog", 1, 12));
    borrarExpresion.setText("Borrar Expresión");
    borrarExpresion.setToolTipText("Limpia el campo de expresi¢n");
    borrarResultado.setActionCommand("borrarResultado");
    borrarResultado.setToolTipText("Limpia el campo de resultado");

    salvarVariables.setActionCommand("salvarVariables");
    salvarVariables.addActionListener(new
                                CalcWindow_salvarVariables_actionAdapter(this));
    salvarVariables.setToolTipText("Salva todas las variables de la calculadora");
    cargarVariables.setText("Cargar Variables");
    cargarVariables.setFont(new Font("Dialog", 1, 12));
    cargarVariables.setToolTipText("Carga las variables previamente salvadas de un fichero");
    cargarVariables.addActionListener(new
                                CalcWindow_cargarVariables_actionAdapter(this));
    cargarVariables.setActionCommand("cargarVariables");
    actualizarVariables.setFont(new Font("Dialog", 1, 12));
    actualizarVariables.setText(
                           "Actualizar Autom. Variables (enlentece ejecucion)");
//  jScrollPane1.setAutoscrolls(true);
//  jScrollPane1.setDoubleBuffered(true);
  historico.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(ActionEvent e) {
    historico_actionPerformed(e);
   }
  });
  historico.setAutoscrolls(true);
  historico.setFont(new Font("Dialog", 1, 12));
  historico.setBounds(new Rectangle(-70, 220, 316, 162));
  historico.createVerticalScrollBar();
  historico.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
  historico.jlista.setForeground(new Color(0,0,255));
  
  salidaProgramas.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
          salidaProgramas_actionPerformed(e);
      }
  });
  salidaProgramas.setAutoscrolls(true);
  salidaProgramas.setFont(new Font("Dialog",1,12));
  salidaProgramas.setBounds(new Rectangle(355,220,316,162));
  salidaProgramas.createVerticalScrollBar();
  salidaProgramas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
  salidaProgramas.jlista.setForeground(new Color(0,0,255));

    actualizarVariables.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        actualizarVariables_itemStateChanged(e);
      }
    });
    salvarVariables.setFont(new Font("Dialog", 1, 12));
    salvarVariables.setText("Salvar Variables");
    borrarResultado.addActionListener(new
                                CalcWindow_borrarResultado_actionAdapter(this));
    borrarResultado.setFont(new Font("Dialog", 1, 12));
    borrarResultado.setText("Borrar Resultado");
    historicoAdd.setFont(new Font("Dialog", 1, 12));
    historicoAdd.setText(">");
    historicoConcatenar.setFont(new Font("Dialog", 1, 12));
    historicoConcatenar.setActionCommand("historicoConcatenar");
    historicoConcatenar.addActionListener(new
                            CalcWindow_historicoConcatenar_actionAdapter(this));
    historicoConcatenar.setText(">>");
    historicoConcatenar.setToolTipText("A¤adir a la expresi¢n existente");
    funcionesConcatenar.setActionCommand("funcionesConcatenar");
  funcionesConcatenar.addActionListener(new
                            CalcWindow_funcionesConcatenar_actionAdapter(this));
    funcionesConcatenar.setFont(new Font("Dialog", 1, 12));
    funcionesConcatenar.setText(">>");
    funcionesConcatenar.setToolTipText("A¤adir a la expresi¢n existente");
    funcionesAdd.setActionCommand("funcionesAdd");
    funcionesAdd.addActionListener(new
                                   CalcWindow_funcionesAdd_actionAdapter(this));
    funcionesAdd.setFont(new Font("Dialog", 1, 12));
    funcionesAdd.setText(">");
    funcionesAdd.setToolTipText("Sustituir expresi¢n existente por selecci¢n");
    this.setLayout(xYLayout1);
  this.add(funciones, new XYConstraints(14, 19, 321, 201));
  //+++++this.add(variables, new XYConstraints(345, 19, 443, 408));
  this.add(variables, new XYConstraints(345, 19, 490, 201));
  this.add(expresion,  new XYConstraints(102, 460, 733, 40));
  this.add(label2, new XYConstraints(22, 251, 254, 16));
  this.add(label5, new XYConstraints(355, 251, 254, 16));
  this.add(historicoAdd, new XYConstraints(126, 435, 47, 20));
  this.add(historicoConcatenar, new XYConstraints(179, 435, -1, 20));
  this.add(funcionesConcatenar, new XYConstraints(173, 228, -1, 20));
  this.add(funcionesAdd, new XYConstraints(117, 228, 47, 20));
  //+++++this.add(actualizarVariables, new XYConstraints(349, 436, 322, 16));
  this.add(actualizarVariables, new XYConstraints(349, 230, 422, 16));
//  jScrollPane1.getViewport().add(historico, null);
//  jScrollPane1.createHorizontalScrollBar();
//  jScrollPane1.createVerticalScrollBar();
//  this.add(jScrollPane1, new XYConstraints(14, 268, 321, 151));
  this.add(historico, new XYConstraints(14, 268, 324, 164));
  this.add(salidaProgramas, new XYConstraints(345,268, 490, 180));
  this.add(resultado,  new XYConstraints(102, 509, 733, 39));
  this.add(salvarVariables,  new XYConstraints(472, 562, 151, 25));
  this.add(borrarResultado, new XYConstraints(313, 562, 156, 25));
  this.add(borrarExpresion, new XYConstraints(156, 562, 153, 25));
  this.add(calcular, new XYConstraints(54, 562, 98, 25));
  this.add(cargarVariables, new XYConstraints(626, 562, 154, 25));
  this.add(label3, new XYConstraints(16, 469, 81, 24));
  this.add(label4, new XYConstraints(16, 518, 81, 24));
  eval.addNotificado((notificado) variables);
 }


  public CalcWindow() {
    try  {
      jbInit();

    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /*public void setSize(int w,int h) {
      super.setSize(w,h);
      System.out.println("Cambiado calcwindow: W: "+this.getWidth()+" H: "+this.getHeight());
  }*/


  static public void main(String[] args) {
    CalcWindow miPanel = new CalcWindow();
    miPanel.isStandalone = true;
    JFrame frame = new JFrame();
    frame.setTitle("Calculadora Científica Programable");
    frame.getContentPane().add(miPanel, BorderLayout.CENTER);
    frame.pack();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation((d.width - frame.getSize().width) / 2,
                                       (d.height - frame.getSize().height) / 2);
    frame.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
          System.exit(0);
       }
    });
    frame.setVisible(true);
  }

  void funcionesAdd_actionPerformed(ActionEvent e) {
     expresion.setText("");
     funciones_traspasar();
  }

  String subStr(String s, String desde, String hasta)
  {
     int i,j;
     i=s.indexOf(desde)+desde.length();
     j=s.indexOf(hasta);
     if (i>=0 && i<j) {
        return s.substring(i,j);
     }
     return "";
  }

  void expresion_actionPerformed(ActionEvent e) {
     String s=new String();
     variable r=new variable();
     s=expresion.getText();
     expresion.setText("");
     if (s.length()>0) {
        resultado.setText("");
        r=eval.programa(s+(char) ctes_eval.FIN);
        if (r!=null) {
           resultado.setText(r.StringValue());
           if (r.tipo!='E') { // si no la expresión fue válida
              int n=historico.getItemCount();
              int i;
              for(i=0; i<n; i++) {
                 if (s.equalsIgnoreCase(historico.getItem(i))) {
                    historico.removeElement(i);
                    break;
                 }
              }
              if (s.charAt(s.length()-1)=='\n') {
                 s=s.substring(0,s.length()-1);
              }
              historico.addItem(s);
              historico.select(historico.getItemCount()-1);
              //Rectangle rect=new Rectangle(historico.getVisibleRect());
              //rect.setBounds(rect.x,rect.y+100,rect.width,rect.height+100);
              //historico.scrollRectToVisible(rect);
              historico.jlista.ensureIndexIsVisible(historico.getItemCount()-1);
           } else {
              if (r.toString().indexOf("exec")<0) {
                 expresion.setText(s);
                 expresion.setSelectionStart(eval.getUltimoToken());
                 expresion.setSelectionEnd(s.length());
              } else {
                 expresion.setText(s);
              }
           }
        }
     }
  }

  void historicoAdd_actionPerformed(ActionEvent e) {
     if (historico.getSelectedItem()!=null) {
        expresion.setText(historico.getSelectedItem());
     }
  }

  void historicoConcatenar_actionPerformed(ActionEvent e) {
    if (historico.getSelectedItem()!=null) {
       expresion.setText(expresion.getText()+historico.getSelectedItem());
    }
  }

  void calcular_actionPerformed(ActionEvent e) {
     expresion_actionPerformed(e);
  }

  void borrarExpresion_actionPerformed(ActionEvent e) {
    expresion.setText("");
  }

  void borrarResultado_actionPerformed(ActionEvent e) {
    resultado.setText("");
  }

  void funciones_traspasar()
  {
     int i=funciones.getSelectedIndex();
     if (i>=0 && i<funciones.countItems()) {
        String s=((Vector) funciones.getItem(i)).elementAt(0).toString();
        expresion.setText(expresion.getText()+s);
     }
  }

  void funciones_actionPerformed(JCActionEvent e) {
    funciones_traspasar();
  }
  static boolean ultimo=true;
  void expresion_keyPressed(KeyEvent e) {
     if (historico.getItemCount()>0) {
        if (e.getKeyCode()==KeyEvent.VK_UP) {
           if (historico.getSelectedIndex()<0) {
              historico.select(historico.getItemCount()-1);
              expresion.setText(historico.getSelectedItem());
           } else if (historico.getSelectedIndex()>=0) {
              if (!ultimo) {
                 historico.select(historico.getSelectedIndex()-1);
              } else {
                 ultimo=false;
              }
              expresion.setText(historico.getSelectedItem());
           } else {
              getToolkit().beep();
           }
        } else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
           if (historico.getSelectedIndex()<historico.getItemCount()-1) {
              if (historico.getSelectedIndex()<historico.getItemCount()-1) {
                 historico.select(historico.getSelectedIndex()+1);
              }
              expresion.setText(historico.getSelectedItem());
           } else {
              getToolkit().beep();
           }
        } else if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
           historico.select(-1);
           expresion.setText("");
        } else {
           ultimo=true;
        }

     }
  }

  void salvarVariables_actionPerformed(ActionEvent e) {
        DataOutputStream outStream;
        Frame frame=new Frame();
        FileDialog fichero=new FileDialog(frame,"Salvar variables en: ",
                                                               FileDialog.SAVE);
        fichero.show();
        if (fichero.getFile()!=null) {
           try {
              outStream= new DataOutputStream(new FileOutputStream(
                                                            fichero.getFile()));
              try {
                 Vector v;
                 for( int i=0; i<variables.countItems(); i++) {
                    v=(Vector) variables.getItem(i);
                    outStream.writeBytes(v.elementAt(0) + "="+
                                           v.elementAt(1)+(char) ctes_eval.FIN);
                 }
                 outStream.close();
              } catch (IOException ex) {
                 mieval.dialogo.cuadro_mensaje("Error escribiendo en "+
                                                              fichero.getFile(),
                                          "Mensaje de Error");
              }
           } catch (IOException ex) {
              mieval.dialogo.cuadro_mensaje("Error al Abrir Fichero "+
                                                              fichero.getFile(),
                                       "Mensaje de Error");
           }
        }
  }


  void cargarVariables_actionPerformed(ActionEvent e)
  {
        Frame frame=new Frame();
        FileDialog fichero=new FileDialog(frame,"Cargar Variables desde: ",
                                                               FileDialog.LOAD);
        fichero.show();
        if (fichero.getFile()!=null) {
           if (!eval.abrirFichero(fichero.getFile())) {
              mieval.dialogo.cuadro_mensaje("Error al abrir el fichero "+
                                                              fichero.getFile(),
                                       "Mensaje de Error");
              return;
           }
           String prg=eval.getContenidoFichero(fichero.getFile());
           variable r=eval.programa(prg);
           if (r.tipo=='E') {
             mieval.dialogo.cuadro_mensaje("Error al cargar variables del fichero: "+
                                           fichero.getFile()+" : "+r.toString(),
                             "Mensaje de Error");
           }
        }
  }


  void variables_actionPerformed(JCActionEvent e) {
     String s=((Vector) variables.getItem(
                         variables.getSelectedIndex())).elementAt(0).toString();
     expresion.setText(expresion.getText()+s);
  }

  void actualizarVariables_itemStateChanged(ItemEvent e) {
     this.eval.setNotificacion(actualizarVariables.isSelected());
     if (actualizarVariables.isSelected()) {
        int j=eval.getNumSimbolos();
        variable v;
        for(int i=0; i<j; i++) {
           v=eval.getSimbolo(i);
           variables.nuevaVariable(v.nombre,v.StringValue());
        }
     }

  }

  void historico_actionPerformed(ActionEvent e) {
     historicoConcatenar_actionPerformed(e);
  }
  void salidaProgramas_actionPerformed(ActionEvent e) {
    if (salidaProgramas.getSelectedItem()!=null) {
       expresion.setText(expresion.getText()+salidaProgramas.getSelectedItem());
    }
  }
 void funcionesConcatenar_actionPerformed(ActionEvent e) {
     funciones_traspasar();
 }
 public void clear() {
    this.salidaProgramas.removeAll();
 }
 public void imprime(String s) {
   this.salidaProgramas.addItem(s);
   salidaProgramas.select(salidaProgramas.getItemCount()-1);
   salidaProgramas.jlista.ensureIndexIsVisible(salidaProgramas.getItemCount()-1);
   //salidaProgramas.repaint();
 }
 public void imprimec(String color, String s) {
     Color c=new Color(0,0,0);
     
     if (color.equalsIgnoreCase("rojo")) {
         c=new Color(255,0,0);
         //System.out.println("color rojo");
     } else if (color.equalsIgnoreCase("verde")) {
         c=new Color(0,255,0);
     } else if (color.equalsIgnoreCase("azul")) {
         c=new Color(0,0,255);
     }
     //System.out.println(c);
     this.salidaProgramas.jlista.setForeground(c);
     this.imprime(s);
     //this.salidaProgramas.jlista.setForeground(new Color(0,0,0));
 }


}

class CalcWindow_funcionesAdd_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_funcionesAdd_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.funcionesAdd_actionPerformed(e);
  }
}

class CalcWindow_expresion_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_expresion_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.expresion_actionPerformed(e);
  }
}

class CalcWindow_historicoAdd_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_historicoAdd_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.historicoAdd_actionPerformed(e);
  }
}

class CalcWindow_historicoConcatenar_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_historicoConcatenar_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.historicoConcatenar_actionPerformed(e);
  }
}

class CalcWindow_calcular_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_calcular_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.calcular_actionPerformed(e);
  }
}

class CalcWindow_borrarExpresion_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_borrarExpresion_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.borrarExpresion_actionPerformed(e);
  }
}

class CalcWindow_borrarResultado_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;

  CalcWindow_borrarResultado_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.borrarResultado_actionPerformed(e);
  }
}

class CalcWindow_funciones_actionAdapter implements jclass.bwt.JCActionListener{
  CalcWindow adaptee;

  CalcWindow_funciones_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(JCActionEvent e) {
    adaptee.funciones_actionPerformed(e);
  }
}

class CalcWindow_expresion_keyAdapter extends java.awt.event.KeyAdapter {
  CalcWindow adaptee;

  CalcWindow_expresion_keyAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.expresion_keyPressed(e);
  }

}

class CalcWindow_salvarVariables_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;


  CalcWindow_salvarVariables_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.salvarVariables_actionPerformed(e);
  }
}

class CalcWindow_cargarVariables_actionAdapter
                                       implements java.awt.event.ActionListener{
  CalcWindow adaptee;


  CalcWindow_cargarVariables_actionAdapter(CalcWindow adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cargarVariables_actionPerformed(e);
  }
}

class CalcWindow_funcionesConcatenar_actionAdapter
                                      implements java.awt.event.ActionListener {
 CalcWindow adaptee;


 CalcWindow_funcionesConcatenar_actionAdapter(CalcWindow adaptee) {
  this.adaptee = adaptee;
 }

 public void actionPerformed(ActionEvent e) {
  adaptee.funcionesConcatenar_actionPerformed(e);
 }
 
}




