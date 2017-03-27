
/******************************************************************************/
/******************         pantalla.java       *******************************/
/******************************************************************************/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import layout.XYConstraints;
import layout.XYLayout;
import mieval.SyntaxErrorException;
import mieval.arbol_binario;

/*class miSpinner extends JPanel {
BasicArrowButton up, down;
Spinner s;
public miSpinner() {
up=new BasicArrowButton();
down=new BasicArrowButton();
s=new Spinner();
s.setValue(0);
add(s);
add(up);
add(down);
}
public miSpinner(int i) {
this();
s.setValue(i);
}
}*/
/******************************************************************************/
/******* esta clase ha sido diseñada con ayuda de herramientas RAD    *********/
/******* que genera automáticamente código                       **************/

/******************************************************************************/

public class pantalla extends JPanel implements MouseMotionListener , MouseListener,notificacionCambiosMiCanvas,KeyListener {
    static Font defaultFont=new Font("Helvetica",Font.BOLD,12);
    private int panelSurAltura=50;
    private miChangeListener jspinnerListener=new miChangeListener(this);
    private int initWidth=1015;
    private int initHeigth=695;
    XYLayout xYLayout1 = new XYLayout();
    boolean isStandalone = false;
    miCanvas mcanvas = new miCanvas(this,initWidth, 550);
    Vector<arbol_binario> listaArbol = new Vector<arbol_binario>();
    miJSpinner xIzda = new miJSpinner(-14.0,-Double.MAX_VALUE,Double.MAX_VALUE,0.1,"xIzda",jspinnerListener,Color.RED);
    miJSpinner xDcha = new miJSpinner(14.0,-Double.MAX_VALUE,Double.MAX_VALUE,0.1,"xDcha",jspinnerListener,Color.RED);
    miJSpinner yInf = new miJSpinner(-10.0,-Double.MAX_VALUE,Double.MAX_VALUE,0.1,"yInf",jspinnerListener,Color.BLUE);
    miJSpinner ySup = new miJSpinner(10.0,-Double.MAX_VALUE,Double.MAX_VALUE,0.1,"ySup",jspinnerListener,Color.BLUE);
    miJSpinner ppi = new miJSpinner(2,2,200,1,"ppi",jspinnerListener,Color.MAGENTA);
    miJSpinner numDec=new miJSpinner(5,0,14,1,"numDec",jspinnerListener,new Color(0xff,0x66,00));
    miJSpinner todos=new miJSpinner(0,-Double.MAX_VALUE,Double.MAX_VALUE,0.001,"todos",jspinnerListener);

    JComboBox listaFunciones = new JComboBox();
    
    Vector<Color> listaColores = new Vector<Color>();
    
    JTextField funcion = new JTextField();
    
    //JColorChooser colorChooser1 = new JColorChooser();
    
    miJTextFieldColorChooser colorFuncion = new miJTextFieldColorChooser("Elegir Color de la Función");
    
    miJLabel labelRangoX = new miJLabel(this,"Doble click: cambia color eje x","Rango x:","labelRangoX",Color.RED);
    miJLabel labelRangoY = new miJLabel(this,"Doble click: cambia color eje y","Rango y:","labelRangoY",Color.BLUE);
    miJLabel labelIteraciones = new miJLabel(this,"","Iter./Pixel:","labelIteraciones",Color.MAGENTA);
    miJLabel labelDibujar = new miJLabel(this,"","Dibujar:");
    miJLabel labelAñadirFuncion = new miJLabel(this,"","Añadir Funcion:");
    miJLabel labelDec=new miJLabel(this,"","Dec:","labelDec",new Color(0xff,0x66,00));
    
    miJButton botonRedibujar = new miJButton("Redibujar:","Dibujar la Lista de Funciones");
    //miJButton botonAnadir = new miJButton("<","Añadir a Lista de Funciones");
    miJButton botonQuitar = new miJButton(">","Quintar Funcion de la lista");
    miJRadio radioDrag = new miJRadio("Drag", true,"Drag");
            //"X","Cambiar Color Eje X","colorEjeX",10);
    miJRadio radioZoom = new miJRadio("Zoom",false,"Zoom");
    //javax.swing.ButtonGroup grupoEstadoRaton=new javax.swing.ButtonGroup();
    //grupoEstadoRaton.add(radioDrag);
    //grupoEstadoRaton.add(radioZoom);
            //"Y","Cambiar Color Eje Y","colorEjeY",10);
    //miJButton botonElegirColor = new miJButton("Color","Elegir Color de la Función");
    
    JCheckBox ponerPtosCorte = new JCheckBox("Cortes",false);
    JCheckBox ponerNombresFunciones = new JCheckBox("Nombres",false);
    JCheckBox ponerGrid=new JCheckBox("Grid",true);
    JCheckBox mantenerEscala=new JCheckBox("Escala",true);
    JCheckBox mantenerCentrado=new JCheckBox("Centrar",true);
    JPanel panelSur=new JPanel();

   public void keyTyped(KeyEvent e) {
      mcanvas.keyTyped(e);
   }

   public void keyPressed(KeyEvent e) {
      mcanvas.keyPressed(e);
   }

   public void keyReleased(KeyEvent e) {
      mcanvas.keyReleased(e);
   }

    enum estadoRaton {Drag,Zomm};
    private estadoRaton pantallaEstadoRaton=estadoRaton.Drag;
    private boolean cambiandoRangos=false;
    public IndexedFocusTraversalPolicy indexPolicy;
    //Component initialization
    public void establecerControlRangos() {
       boolean centrar=this.mantenerCentrado.isSelected(), 
               escala=this.mantenerEscala.isSelected();
         /*if (!centrar && !escala) {
            radioDrag.setEnabled(true);
            radioDrag.setSelected(false);
            radioZoom.setSelected(true);
            xIzda.setEnabled(false);xDcha.setEnabled(false);
            yInf.setEnabled(false);ySup.setEnabled(false);
         } else {
            radioDrag.setEnabled(true);
            radioDrag.setSelected(true);
            radioZoom.setSelected(false);
            xIzda.setEnabled(true);xDcha.setEnabled(true);
            yInf.setEnabled(true);ySup.setEnabled(true);
         }*/

    }
    public void jbInit() throws Exception {
        xYLayout1.setWidth(initWidth);
        xYLayout1.setHeight(initHeigth);
        this.setSize(initWidth, initHeigth);
        mcanvas.setBackground(new Color(250,250,224));
        mcanvas.añadirNotificadosCambiosRangos(this);
        numDec.setVisible(false);
        listaFunciones.setFont(defaultFont);
        labelDec.setVisible(false);
        ponerPtosCorte.setFont(defaultFont);
        ponerPtosCorte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               boolean actual=!mcanvas.getPonerPuntosDeCorte();
               mcanvas.setPonerPuntosDeCorte(actual);
               numDec.setVisible(actual);numDec.grabFocus();
               labelDec.setVisible(actual);
               repintar();
            }
        });ponerPtosCorte.setMnemonic(ponerPtosCorte.getText().charAt(1));
        ponerNombresFunciones.setFont(defaultFont);
        ponerNombresFunciones.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               //mcanvas.setPonerPuntosDeCorte(! mcanvas.getPonerPuntosDeCorte());
               mcanvas.ponerNombresFunciones = !mcanvas.ponerNombresFunciones;
               repintar();
            }
        });ponerNombresFunciones.setMnemonic(ponerNombresFunciones.getText().charAt(0));
        ponerGrid.setToolTipText("Doble Click: cambiar color grid.");
        ponerGrid.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               /*MouseEvent mevt=new MouseEvent(ponerGrid, MouseEvent.MOUSE_CLICKED,
                       System.currentTimeMillis(),0,1, 1,1, false); 
               MouseListener[] ml=((JCheckBox)e.getSource()).getMouseListeners();
               ml[ml.length-1].mouseClicked(mevt);*/
               JCheckBox jc=(JCheckBox)e.getSource();
               mcanvas.setGrid(jc.isSelected());
               repintar();
            }
        });
        ponerGrid.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
               JCheckBox jc=(JCheckBox)e.getSource();
               if (e.getClickCount()==2) {
                   Color r = JColorChooser.showDialog(new JFrame(), "Elije un color", jc.getBackground());
                   if (r != null) {
                        jc.setBackground(r);
                        mcanvas.setColorGrid(jc.getBackground());
                        repintar();
                   }
               }
               
           }
        });ponerGrid.setMnemonic(ponerGrid.getText().charAt(0));
        this.mantenerCentrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               boolean estadoActual=mantenerCentrado.isSelected();
               mcanvas.setCentrar(estadoActual);
               establecerControlRangos();
               /*if (estadoActual) {
                  radioDrag.setEnabled(false);
                  radioDrag.setSelected(false);
                  radioZoom.setSelected(true);
                  xIzda.setEnabled(false);xDcha.setEnabled(false);
                  yInf.setEnabled(false);ySup.setEnabled(false);
               } else {
                  radioDrag.setEnabled(true);
                  radioDrag.setSelected(true);
                  radioZoom.setSelected(false);
                  xIzda.setEnabled(true);xDcha.setEnabled(true);
                  yInf.setEnabled(true);ySup.setEnabled(true);
               }*/
               repintar();
            }
        }); mantenerCentrado.setMnemonic(mantenerCentrado.getText().charAt(0));
        this.mantenerEscala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               boolean estadoActual=mantenerEscala.isSelected();
               mcanvas.setMantenerEscala(estadoActual);
               establecerControlRangos();
               //if (estadoActual) {
               //   xIzda.setEnabled(false);xDcha.setEnabled(false);
               //} else {
               //   xIzda.setEnabled(true);xDcha.setEnabled(true);
               //}
               repintar();
            }
         });mantenerEscala.setMnemonic(mantenerEscala.getText().charAt(0));
 
        botonQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if (listaFunciones.getItemCount() > 0) {
                     if (listaFunciones.getSelectedItem() != null) {
                        int i = listaFunciones.getSelectedIndex();
                        funcion.setText((String) listaFunciones.getSelectedItem());
                        listaArbol.removeElementAt(i);
                        colorFuncion.setBackground((Color) listaColores.elementAt(i));
                        listaColores.removeElementAt(i);
                        listaFunciones.removeItemAt(listaFunciones.getSelectedIndex());
                        repintar();
                     }
               }
            }
        });
        /*botonAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                anadirFuncion();
                repintar();
            }
        });*/
        botonRedibujar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repintar();
            }
        });botonRedibujar.setMnemonic(botonRedibujar.getText().charAt(0));
        MouseListener colorEjesActionListener=new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
              Color r = JColorChooser.showDialog(new JFrame(), "Elije un color", colorFuncion.getBackground());
              if (r!=null) {
                  miJTextFieldColorChooser jt=((miJTextFieldColorChooser)e.getSource());
                  jt.setBackground(r);
                  if ("colorEjeX".equalsIgnoreCase(jt.getName())) {
                     mcanvas.setColorEjeX(r);
                  } else if ("colorEjeY".equalsIgnoreCase(jt.getName())) {
                     mcanvas.setColorEjeY(r);
                  }
                  repintar();
              }
           }
        };
        //this.botonColorX.cambiaMouseListener(colorEjesActionListener);
        //this.botonColorY.cambiaMouseListener(colorEjesActionListener);
        
        funcion.setFont(defaultFont);
        funcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                anadirFuncion();
                repintar();
            }
        });
        funcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
               if (e.getClickCount()==2 && !funcion.getText().equalsIgnoreCase("")) {
                  anadirFuncion();
                  repintar();
               }
            }
        });
        this.setLayout(new BorderLayout());
        //this.add(mcanvas, new XYConstraints(0, 0, initWidth, 550));
        this.add(mcanvas,BorderLayout.NORTH);
        XYLayout panelSurLayout=new XYLayout();
        //BorderLayout panelSurLayout=new BorderLayout();
        //Indices de tabulación
        
        //Fin Indices de tabulación
        int desp=549;
        panelSurLayout.setHeight(initWidth); panelSurLayout.setHeight(panelSurAltura);
        panelSur.setLayout(panelSurLayout);
        panelSur.add(labelRangoX, new XYConstraints(10, 553-desp, 62, 17));
        panelSur.add(xIzda, new XYConstraints(5, 574-desp, 70, 26));
        panelSur.add(xDcha, new XYConstraints(5 + 65+4, 574-desp, 70, 26));
        panelSur.add(labelRangoY, new XYConstraints(5 + 2 * 65-48, 553-desp, -1, 17));
        panelSur.add(yInf, new XYConstraints(5 + 3 * 65-50, 574-desp, 70, 26));
        panelSur.add(ySup, new XYConstraints(5 + 3 * 65-50, 574-desp-25, 70, 26));
        panelSur.add(todos, new XYConstraints(5 + 3 * 65+24, 574-desp-25, 20, 52));
        panelSur.add(labelIteraciones, new XYConstraints(248, 553-desp, -1, 17));
        panelSur.add(ppi, new XYConstraints(5 + 4 * 65-18, 574-desp, 70, 26));
        panelSur.add(listaFunciones, new XYConstraints(415, 574-desp, 200, 27));
        panelSur.add(funcion, new XYConstraints(648, 574-desp, 365, 27));
        //panelSur.add(botonElegirColor, new XYConstraints(764, 550-desp, 70, 22));
        panelSur.add(colorFuncion, new XYConstraints(804, 548-desp, 20, 20));
        panelSur.add(this.ponerGrid,new XYConstraints(824,548-desp,50,20));
        panelSur.add(this.mantenerCentrado, new XYConstraints(874,548-desp,70,20));
        panelSur.add(this.mantenerEscala, new XYConstraints(946,548-desp,70,20));
        //this.add(label4, new XYConstraints(475, 555, 120, 17));
        panelSur.add(labelAñadirFuncion, new XYConstraints(690, 557-desp, 156, 15));
        panelSur.add(botonRedibujar, new XYConstraints(317, 574-desp, 102, 27));
        //panelSur.add(botonAnadir, new XYConstraints(610, 556-desp, 40, 22));
        panelSur.add(botonQuitar, new XYConstraints(611, 578-desp, 40, 22));
        panelSur.add(ponerNombresFunciones, new XYConstraints(320, 552-desp, 90, 17));
        panelSur.add(ponerPtosCorte, new XYConstraints(408, 552-desp, 74, 17));
        panelSur.add(labelDec, new XYConstraints(483,552-desp,35,17));
        panelSur.add(numDec,new XYConstraints(521,550-desp,50,24));
        panelSur.add(radioDrag,new XYConstraints(572,548-desp,60,24));
        panelSur.add(radioZoom,new XYConstraints(628,548-desp,60,24));
        panelSur.setSize(initWidth, 100);
        panelSur.setPreferredSize(new Dimension(initWidth,panelSurAltura));
        panelSur.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        JPanel contPanelSur=new JPanel();
        contPanelSur.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        contPanelSur.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        contPanelSur.add(panelSur);
        this.add(contPanelSur,BorderLayout.SOUTH);
        this.mcanvas.addMouseMotionListener(this);
        this.mcanvas.addMouseListener(this);
        indexPolicy=new IndexedFocusTraversalPolicy();
        indexPolicy.addIndexedComponent(xIzda);indexPolicy.addIndexedComponent(xDcha);
        indexPolicy.addIndexedComponent(ySup);indexPolicy.addIndexedComponent(yInf);
        indexPolicy.addIndexedComponent(todos);indexPolicy.addIndexedComponent(ppi);
        indexPolicy.addIndexedComponent(botonRedibujar);indexPolicy.addIndexedComponent(listaFunciones);
        indexPolicy.addIndexedComponent(ponerNombresFunciones);indexPolicy.addIndexedComponent(ponerPtosCorte);
        indexPolicy.addIndexedComponent(numDec);indexPolicy.addIndexedComponent(this.radioDrag);
        indexPolicy.addIndexedComponent(this.radioZoom);indexPolicy.addIndexedComponent(this.botonQuitar);
        //indexPolicy.addIndexedComponent(this.botonAnadir);
        indexPolicy.addIndexedComponent(funcion);
        indexPolicy.addIndexedComponent(this.ponerGrid);
        this.setFocusTraversalPolicy(indexPolicy);
    }
    public pantalla() {
        try {
            jbInit();
            //this.setBackground(Color.PINK);
            mcanvas.establece_rangos(this.xIzda.getDoubleValue(),
                    this.xDcha.getDoubleValue(), this.yInf.getDoubleValue(),
                    this.ySup.getDoubleValue(), this.ppi.getDoubleValue());
            mcanvas.repaint();
            final pantalla self=this;
            this.addComponentListener(new java.awt.event.ComponentAdapter()
            {
                public void componentResized(ComponentEvent e)
                {
                    
                    //System.out.println("pantalla componentresizedd. W: "+self.getWidth()+" H: "+self.getHeight());
                    //mcanvas.setSize(this.get, WIDTH);
                    if (self.getParent()!=null) {
                        self.mcanvas.setSize(self.getWidth(), self.getHeight()-panelSur.getHeight()-14);
                        //self.mcanvas.repaint();
                    }
                }
            });
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Main method
    static public void main(String[] args) {
        JFrame frame = new JFrame();
        pantalla miPantalla = new pantalla();
        frame.setTitle("Representación de Funciones");
        frame.getContentPane().add(miPantalla, BorderLayout.CENTER);
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
        frame.setVisible(true);
    }
    /*
    @Override
    public void setSize(int w, int h) {
        //if (w>=this.initWidth || h>=this.initHeigth) {
        Container padre=this.getParent();
        if (padre!=null) {
            if (w<padre.getWidth()) {
               w=padre.getWidth();
            }
            if (h<padre.getHeight()) {
               h=padre.getHeight();
            }
        }
        super.setSize(w,h);
        System.out.println("cambiado pantalla W: "+this.getWidth()+" H: "+this.getHeight());
            //this.mcanvas.setSize(w, h-250);
        //}
    }*/
    public void cambiaColor(java.awt.AWTEvent e)  {
       Object source=e.getSource();
       if (e instanceof MouseEvent) {
          if (source instanceof miJLabel) {
             miJLabel sourceLabel=(miJLabel) source;
             if ("labelRangoX".equalsIgnoreCase(sourceLabel.getName())) {
                mcanvas.setColorEjeX(sourceLabel.getSelectedColor());
             } else if ("labelRangoY".equalsIgnoreCase(sourceLabel.getName())) {
                mcanvas.setColorEjeY(sourceLabel.getSelectedColor());
             }
          }
       }
       repintar();
    }
    /*** Redibuja todas las funciones                                    **********/
    /*** Parámetros: ninguno                                             **********/
    /*** devuelve: nada                                                   **********/
    synchronized void repintar() {
       if (!cambiandoRangos) {
         if (xIzda.getDoubleValue() < xDcha.getDoubleValue()
                  && yInf.getDoubleValue() < ySup.getDoubleValue()) {
               mcanvas.establece_rangos(xIzda.getDoubleValue(), xDcha.getDoubleValue(),
                     yInf.getDoubleValue(), ySup.getDoubleValue(),
                     ppi.getIntValue());
               if (listaFunciones.getItemCount() > 0) {
                  mcanvas.setListaFunciones(listaFunciones);
                  mcanvas.setListaArboles(listaArbol);
                  mcanvas.setListaColores(listaColores);
               }
               mcanvas.repaint();
         } else {
               if (xIzda.getDoubleValue() > xDcha.getDoubleValue()) {
                  xIzda.setDoubleValue(xDcha.getDoubleValue() - 0.1);
               }
               if (yInf.getDoubleValue() > ySup.getDoubleValue()) {
                  yInf.setDoubleValue(ySup.getDoubleValue() - 0.1);
               }

         }
       }
    }
    /*** Añade una función a la lista de funciones a representar.        **********/
    /***     Comprueba que la función no tiene ningún error de sintaxis  **********/
    /***     al intentar generar su árbol sintáctico. Si no hubo error al**********/
    /***     generarlo es que no había errores de sintaxis.              **********/
    /*** Parámetros: ninguno                                             **********/
    /*** devuelve: nada                                                   **********/
    void anadirFuncion() {
        String s = funcion.getText();
        if (s.length() > 0) {
            try {
                arbol_binario a = mcanvas.eval.arbol_expr(s);
                listaFunciones.addItem(s);
                listaColores.addElement(colorFuncion.getBackground());
                listaFunciones.setSelectedIndex(listaFunciones.getItemCount() - 1);
                listaArbol.addElement(a);
                funcion.setText("");
            } catch (SyntaxErrorException er) {
                mieval.dialogo.cuadro_mensaje("La función expresada tiene algún error"
                        + " de sintaxis",
                        "Mensaje de Error");
            }
        }

    }
    private int mousePosX=0, mousePosY=0;
    public void mouseDragged(MouseEvent e) {
      //if (mousePosX==-1) {
      //   mousePosX=e.getX();
      //   mousePosY=e.getY();
      //} else {
         if (this.mousePosX!=-1) {
            
            if (this.radioDrag.isSelected()) {
               double x1=mcanvas.inversax(mousePosX), y1=mcanvas.inversay(mousePosY), 
                     x2=mcanvas.inversax(e.getX()),y2=mcanvas.inversay(e.getY());
               double xx1=Math.min(x1,x2),
                      xx2=Math.max(x1,x2),
                      yy1=Math.min(y1,y2),
                      yy2=Math.max(y1,y2);
               //g.setColor(Color.RED);
               //g.drawLine(mousePosX, mousePosY, e.getX(), e.getY());
               //this.linea(x1,y1,x2,y2);
               this.xDcha.setValue(this.xDcha.getDoubleValue()+(x1-x2));
               this.xIzda.setValue(this.xIzda.getDoubleValue()+(x1-x2));
               this.ySup.setValue(this.ySup.getDoubleValue()+(y1-y2));
               this.yInf.setValue(this.yInf.getDoubleValue()+(y1-y2));
               this.repintar();
               mousePosX=e.getX();mousePosY=e.getY();
               //this.linea(inversax(mousePosX), inversay(mousePosY), inversax(e.getX()), inversay(e.getY()));
            } else if (this.radioZoom.isSelected()) {
               //this.mcanvas.linea(xx1,yy1,xx2,yy1);
               //this.mcanvas.linea(xx1,yy1,xx1,yy2);
               //this.mcanvas.linea(xx2,yy2,xx2,yy1);
               //this.mcanvas.linea(xx2,yy2,xx1,yy2);
               int xx1=Math.min(this.mousePosX,e.getX()), xx2=Math.max(this.mousePosX,e.getX()),
                   yy1=Math.min(this.mousePosY,e.getY()),yy2=Math.max(this.mousePosY,e.getY());
               this.mcanvas.seleccion=new Rectangle(xx1,yy1,xx2-xx1,yy2-yy1);
               this.repintar();
            }
         }
      //}
      
   }

   public void mouseMoved(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseClicked(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
      
   }

   public void mousePressed(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
      this.mousePosX=e.getX();
      this.mousePosY=e.getY();
   }

   public void mouseReleased(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
      if (this.radioZoom.isSelected() && this.mousePosX!=-1 && this.mousePosX!=e.getX()) {
            double x1=mcanvas.inversax(mousePosX), y1=mcanvas.inversay(mousePosY), 
                  x2=mcanvas.inversax(e.getX()),y2=mcanvas.inversay(e.getY());

            //g.setColor(Color.RED);
            //g.drawLine(mousePosX, mousePosY, e.getX(), e.getY());
            //this.linea(x1,y1,x2,y2);
            this.xDcha.setValue(Math.max(x1,x2));
            this.xIzda.setValue(Math.min(x1,x2));
            this.ySup.setValue(Math.max(y1,y2));
            this.yInf.setValue(Math.min(y1,y2));
            this.repintar();
            //this.linea(inversax(mousePosX), inversay(mousePosY), inversax(e.getX()), inversay(e.getY()));
      }
      this.mousePosX=-1;
      this.mousePosY=-1;
      mcanvas.seleccion=null;
   }

   public void mouseEntered(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseExited(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }
   public void rangosNuevos(double rangoxi, double rangoxd, double rangoyi, double rangoys) {
      cambiandoRangos=true;
      this.xIzda.setDoubleValue(rangoxi);this.xDcha.setDoubleValue(rangoxd);
      this.yInf.setDoubleValue(rangoyi);this.ySup.setDoubleValue(rangoys);
      cambiandoRangos=false;
   }
}
/****************
 * Componentes personalizados
 * @author usuario
 */
class miChangeListener implements ChangeListener {
   private pantalla p=null;
   private double ant=0;
   public miChangeListener(pantalla p) {
      this.p=p;
   }

   public void stateChanged(ChangeEvent e) {
      miJSpinner origen=((miJSpinner)e.getSource());
      if ("numDec".equalsIgnoreCase(origen.getName())) {
         p.mcanvas.setNumDecimales(origen.getIntValue());
      } else if("todos".equalsIgnoreCase(origen.getName())) {
         if (origen.getDoubleValue()>ant) {
            p.yInf.dec();
            p.ySup.inc();
            p.xIzda.dec();
            p.xDcha.inc();
         } else {
            if (p.yInf.getDoubleValue()<p.ySup.getDoubleValue()+0.1) {
               p.yInf.inc();
               p.ySup.dec();
            }
            if (p.xIzda.getDoubleValue()<p.xDcha.getDoubleValue()+0.1) {
               p.xIzda.inc();
               p.xDcha.dec();
            }
         }
         ant=origen.getDoubleValue();
      }
      p.repintar();
   }
}
class miJSpinner extends javax.swing.JSpinner {
   public miJSpinner(double valor,double min, double max,double inc) {
      super(new javax.swing.SpinnerNumberModel(new Double(valor), new Double(min), 
              new Double(max), new Double(inc)));
      this.setAlignmentY(javax.swing.JSpinner.RIGHT_ALIGNMENT);
      this.setOpaque(true);
      //this.setBorder(javax.swing.BorderFactory.createBevelBorder(0));
      this.setBorder(javax.swing.BorderFactory.createEmptyBorder());
      this.setBackground(Color.GRAY);
      this.setForeground(Color.GRAY);
      this.setFont(pantalla.defaultFont);
   }
   public miJSpinner(double v,double mi,double ma, double inc, String name) {
      this(v,mi,ma,inc);
      this.setName(name);
   }
   public miJSpinner(double v,double mi,double ma, double inc, String name, ChangeListener c) {
      this(v,mi,ma,inc,name);
      this.addChangeListener(c);
   }
   public miJSpinner(double v,double mi,double ma, double inc, String name, ChangeListener cl,Color color) {
      this(v,mi,ma,inc,name,cl);
      this.setBackground(color);
      this.setForeground(color);
      //this.setFont(java.awt.Font());
   }
   
   public miJSpinner() {
      this(1.0,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,0.1);
   }
   public void setIntValue(int x) {
      this.setValue(new Double(x));
   }
   public int getIntValue() {
      return ((Double) this.getValue()).intValue();
   }
   public void setDoubleValue(double x) {
      this.setValue(new Double(x));
   }
   public double getDoubleValue() {
      return ((Double)this.getValue()).doubleValue();
   }
   public void inc() {
      this.setValue(this.getModel().getNextValue());
   }
   public void dec() {
      this.setValue(this.getModel().getPreviousValue());
   }
}
class miJLabel extends JLabel implements MouseListener {
   private Color selectedColor=this.getBackground();
   pantalla p=null;
   
   public miJLabel(pantalla p,String toolTip) {
      super();
      this.p=p;
      this.setFont(pantalla.defaultFont);
      if (toolTip!=null && toolTip.compareTo("")!=0) {
         this.setToolTipText(toolTip);
         this.addMouseListener(this);
      }
   }
   public miJLabel(pantalla p,String toolTip,String texto) {
      this(p,toolTip);
      this.setText(texto);
   }
   public miJLabel(pantalla p, String toolTip,String texto,String name) {
      this(p,toolTip,texto);
      this.setName(name);
   }
   public miJLabel(pantalla p,String toolTip,String texto,String name,Color c) {
      this(p,toolTip,texto,name);
      this.setForeground(c);
   }
   public Color getSelectedColor() {
      return this.selectedColor;
   }
   public void mouseClicked(MouseEvent e) {
      if (e.getClickCount()==2) {
         miJLabel jl=(miJLabel) e.getSource();
         this.selectedColor= JColorChooser.showDialog(new JFrame(), "Elije un color", this.getBackground());
         p.cambiaColor(e);
      }
   }

   public void mousePressed(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseReleased(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseEntered(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }

   public void mouseExited(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
   }
   
}
class miJButton extends JButton {
   public miJButton() {
      super();
      this.setFont(pantalla.defaultFont);
      this.setMargin(new Insets(0, 0, 0, 0));
   }
   public miJButton(String texto,String toolTip) {
      this();
      this.setText(texto);
      this.setToolTipText(toolTip);
   }
   public miJButton(String texto,String toolTip,String name) {
      this(texto,toolTip);
      this.setName(name);
   }
   public miJButton(String texto,String toolTip,String name, int sizeFont) {
      this(texto,toolTip,name);
      this.setFont(new Font("Helvetica",Font.BOLD,sizeFont));
   }
}

class miJTextFieldColorChooser extends JTextField implements MouseListener {
    private static Font fuente=pantalla.defaultFont;
    private MouseListener mouseListener=this;
    
    public miJTextFieldColorChooser() {
       this.setFont(pantalla.defaultFont);
       this.setBackground(Color.black);
       this.addMouseListener(this);
    }
    public miJTextFieldColorChooser(String s) {
       this();
       this.setToolTipText(s);
    }
    public miJTextFieldColorChooser(Font f) {
       this();
       this.setFont(f);
       this.setBackground(Color.black);
    }
    public miJTextFieldColorChooser(Font f,Color c) {
       this(f);
       this.setBackground(c);
    }
    public miJTextFieldColorChooser(String texto,String toolTip,String name,int size) {
       this();
       this.setText(texto);
       this.setToolTipText(toolTip);
       this.setName(name);
       this.setFont(fuente.deriveFont((float)size));
    }
    public void mouseClicked(MouseEvent e) {
         Color r = JColorChooser.showDialog(new JFrame(), "Elije un color", this.getBackground());
         if (r != null) {
               this.setBackground(r);
         }
    }
    public void cambiaMouseListener(MouseListener m) {
       this.removeMouseListener(this.mouseListener);
       this.mouseListener=m;
       this.addMouseListener(m);
    }

    public void mousePressed(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
    }
}

class miJRadio extends JRadioButton implements ActionListener {
   static ArrayList<miJRadio> grupo=new ArrayList<miJRadio>();
   static ArrayList<String> teclasReservadas=new ArrayList<String>();
   public miJRadio() {
      grupo.add(this);
      this.addActionListener(this);
   }
   public miJRadio(String texto) {
      this();
      this.setText(texto);
      this.setMnemonic(texto.charAt(0));
   }
   public miJRadio(String texto,boolean selected) {
      this(texto);
      this.setSelected(selected);
   }
   public miJRadio(String texto,boolean selected,String toolTip) {
      this(texto,selected);
      this.setToolTipText(toolTip);
   }

   public void actionPerformed(ActionEvent e) {
      //throw new UnsupportedOperationException("Not supported yet.");
      for(int i=0;i<grupo.size();i++) {
         grupo.get(i).setSelected(false);
      }
      this.setSelected(true);
   }
   
   
}
class IndexedFocusTraversalPolicy extends FocusTraversalPolicy {

   private ArrayList<Component> components = new ArrayList<Component>();

   public void addIndexedComponent(Component component) {
        components.add(component);
   }

   @Override
   public Component getComponentAfter(Container aContainer, 
               Component aComponent) {
        int atIndex = components.indexOf(aComponent);
        int nextIndex = (atIndex + 1) % components.size();
        return components.get(nextIndex);
   }

   @Override
   public Component getComponentBefore(Container aContainer,
         Component aComponent) {
        int atIndex = components.indexOf(aComponent);
        int nextIndex = (atIndex + components.size() - 1) %
                components.size();
        return components.get(nextIndex);
   }

   @Override
   public Component getFirstComponent(Container aContainer) {
        return components.get(0);
   }

   @Override
   public Component getLastComponent(Container aContainer) {
      return components.get(components.size()-1);
   }

   @Override
   public Component getDefaultComponent(Container aContainer) {
      return components.get(0);
   }
}
