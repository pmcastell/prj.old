import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import mieval.SyntaxErrorException;
import mieval.arbol_binario;
import mieval.evaluador;
import mieval.tipo.miDouble;

/******************************************************************************/
/*** Esta clase implementa un Canvas *********/
/***     (lienzo) para dibujar en él. Además dispone de métodos de   **********/
/***     dibujo de funciones y de un objeto evaluador para evaluar   **********/
/***     expresiones y de esta forma ir calculando los valores de la **********/
/***     x y su imagen para poder dibujar los puntos de las funciones *********/
/******************************************************************************/
public class miCanvas extends Canvas implements java.awt.event.MouseListener,java.awt.event.KeyListener {

    private double rangoxi, rangoxd, rangoys, rangoyi, incremento,pppi;
    private double num_pixelsx_unidad, num_pixelsy_unidad;
    private JComboBox listaFunciones;
    private Vector listaArboles;
    private Vector listaColores;
    private Color defaultColor = Color.blue;
    public static evaluador eval;
    private Graphics g;
    private final static int minimaSeparacion = 40;
    private int borde = 20;//minimaSeparacion;
    private int numDecimales = 5;
    private boolean ponerPuntosDeCorte = false;
    boolean ponerNombresFunciones = false;
    private int ant = 14;
    private boolean altMaxMin = false;
    private Color colorEjeX=Color.BLACK, colorEjeY=Color.BLACK,colorGrid=Color.lightGray,colorSeleccion=Color.MAGENTA;
    private boolean ponerGrid=true;
    //private int initWidth=0;
    //private int initHeigth=0;
    public Container padre;
    public Rectangle seleccion=null;
    private boolean mantenerEscala=true, centrar=true;
    private ArrayList<notificacionCambiosMiCanvas> notificadosCambiosRangos=new ArrayList<notificacionCambiosMiCanvas>();
    

    /******************************************************************************/
    /********************* Constructores       ************************************/
    /******************************************************************************/
    public miCanvas(Container c,double rxi, double rxd, double ryi, double rys, double ppi) {
        this(c);
        establece_rangos(rxi, rxd, ryi, rys, ppi);
    }


    public miCanvas(Container c) {
        this.padre=c;
        if (eval == null) {
            eval = new evaluador();
            eval.asigna("x", 0);
        }
        listaFunciones = null;
        listaArboles = null;
        listaColores = null;
        this.addMouseListener(this);
        this.addKeyListener(this);
    }

    public miCanvas(Container c,int width, int heigth) {
        this(c);
        this.setSize(width, heigth);
        //this.initWidth=width;
        //this.initHeigth=heigth;
    }
    public void setGrid(boolean b) {
       this.ponerGrid=b;
    }
    public boolean getGrid() {
       return this.ponerGrid;
    }
    public void setColorGrid(Color c) {
       this.colorGrid=c;
    }
    public Color getColorGrid() {
       return this.colorGrid;
    }
    public void setColorEjeX(Color c) {
       this.colorEjeX=c;
    }
    public void setColorEjeY(Color c) {
       this.colorEjeY=c;
    }
    public void setNumDecimales(int n) {
       if (n>=0 && n<=15) {
          this.numDecimales=n;
       }
    }
    public Dimension mayorDimension() {
       Container c=this.padre;
       int w=this.getWidth();
       int h=this.getHeight();
       while (c!=null) {
          if (c.getWidth()>w) {
             w=c.getWidth();
          }
          if (c.getHeight()>h){
             h=c.getHeight();
          }
          c=c.getParent();
       }
       return new Dimension(w,h);
    }
    /*
    public Dimension mayorDimensions() {
       int w=this.getWidth(), h=this.getHeight();
       if (this.padre!=null) {
          if (padre.getWidth()>w) {
             w=padre.getWidth();
          }
          if (padre.getHeight()>h) {
             h=padre.getHeight();
          }
          pantalla p=(pantalla)this.padre;
          if (p.padre!=null) {
             if (p.padre.getWidth()>w) {
                w=p.padre.getWidth();
             }
             if (p.padre.getHeight()>h) {
                h=p.padre.getHeight();
             }
          }
          miJTabbedPane mjp=(miJTabbedPane)p.padre;
          if (mjp!=null) {
             if (mjp.padre!=null) {
                if (mjp.padre.getWidth()>w) {
                   w=mjp.padre.getWidth();
                }
                if (mjp.padre.getHeight()>h) {
                   h=mjp.padre.getHeight();
                }
             }
          }
       }
       return new Dimension(w,h);

    }
    */
    public void setSize(int w,int h) {
       //Dimension tam=this.mayorDimension();
        //if (w<initWidth) {
          //  w=initWidth;
        //}
        //if (h<initHeigth) {
          //  h=initHeigth;
        //}
        //Exception e=new Exception();
        //if (w<tam.getWidth()) {
           //w=(int)tam.getWidth();
        //}
        //if (h<tam.getHeight()) {
           //h=(int)tam.getHeight();
        //}
        super.setSize(w,h);
        //this.setPreferredSize(new Dimension(w,h));
        establece_rangos();
        //System.out.println("Cambiandito mcanvas "+this+" W: "+this.getWidth()+ " H: "+this.getHeight());
        //System.out.println("Cambiandito mcanvas "+this+" W: "+w+ " H: "+h);
        //e.printStackTrace();
        try {
            this.dibuja();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*** Determina si se dibujan o no los puntos de corte de las funciones ********/
    /***     con el eje X.                                                 ********/
    /*** Parámetros: p-> true se dibujan, false no se dibujan              ********/
    /*** devuelve: nada                                                     ********/
    public void setPonerPuntosDeCorte(boolean p) {
        ponerPuntosDeCorte = p;
    }

    /*** Devuelve si se dibujan o no los puntos de corte de las funciones *********/
    /***     con el eje X.                                                *********/
    /*** Parámetros: ninguno                                              ********
     * @return 
     */
    public boolean getPonerPuntosDeCorte() {
        return ponerPuntosDeCorte;
    }

    /*** Establece la lista de funciones que serán dibujadas                *******/
    /*** Parámetros: l-> lista de funciones a dibujar                       *******/
    /*** devuelve: nada                                                      *******/
    public void setListaFunciones(JComboBox l) {
        this.listaFunciones = l;
    }

    /*** Establece la lista de árboles sintácticos que representan a las  *********/
    /***     funciones a dibujar.                                         *********/
    /*** Parámetros: a-> lista de árboles                                 *********/
    /*** devuelve: nada                                                    *********/
    public void setListaArboles(Vector a) {
        this.listaArboles = a;
    }

    /*** Establece la lista de colores con que se representarán las       *********/
    /***     funciones a dibujar.                                         *********/
    /*** Parámetros: c-> lista de colores                                 *********/
    /*** devuelve: nada                                                    *********/
    public void setListaColores(Vector c) {
        this.listaColores = c;
    }

    /*** Este método es llamado por el sistema cada vez que hay que       *********/
    /***     redibujar la ventana de la aplicación. De esta forma si por  *********/
    /***     ejemplo abrimos otra ventana con otra aplicación que tapa a  *********/
    /***     esta y luego la cerramos, la parte de esta que quedaba tapada*********/
    /***     por la anterior quedaría en blanco. De esta forma se redibuja*********/
    /***     toda la ventana de la aplicación y no se produce dicho efecto*********/
    public void paint(Graphics g) {

        this.g = g;
        try {
            inicio();
        } catch (SyntaxErrorException e) {
            mieval.dialogo.cuadro_mensaje("Error representando ejes de coordenadas",
                    "Mensaje de Error");
            return;
        }
        if (listaFunciones != null) {
            try {
                dibuja();
            } catch (SyntaxErrorException e) {
                mieval.dialogo.cuadro_mensaje("Error representando funciones",
                        "Mensaje de Error");
            }
        }
        if (this.seleccion!=null) {
           g.setColor(this.colorSeleccion);
           g.drawRect((int)seleccion.getX(), (int)seleccion.getY(), (int)seleccion.getWidth(), (int)seleccion.getHeight());
        }
    }

    /*** Transforma la coordenada de abscisas (eje X) a coordenada de     *********/
    /***     pantalla horizontal, es decir, la distancia en pixeles desde *********/
    /***     la parte izquierda de la pantalla.                           *********/
    /*** Parámetros: x-> coordenada a transformar                         *********/
    /*** devuelve: la coordenada de pantalla transformada de x             *********/
    int transformadax(double x) {
        return (int) (num_pixelsx_unidad * (x - rangoxi)) + borde;
    }
    double inversax(int x) {
       return (x-borde)/num_pixelsx_unidad+rangoxi;
    }

    /*** Transforma la coordenada de ordenadas (eje Y) a coordenada de    *********/
    /***     pantalla vertical, es decir, la distancia en pixeles desde   *********/
    /***     la parte superior de la pantalla.                            *********/
    /*** Parámetros: x-> coordenada a transformar                         *********/
    /*** devuelve: la coordenada de pantalla transformada de x             *********/
    int transformaday(double y) {
        return (int) (num_pixelsy_unidad * (rangoys - y)) + borde;
    }
    double inversay(int y) {
       //return -((y-borde)/num_pixelsy_unidad-rangoys);
       return rangoys-(y-borde)/num_pixelsy_unidad;
    }
    /*** Dibuja una línea recta que pasa por los puntos (x1,y1),(x2,y2)   *********/
    /*** Parámetros: x1,y1,x2,y2-> coordenadas de los 2 puntos por los    *********/
    /***     que pasa la línea recta.
    /*** devuelve: nada                                                   *********/
    void linea(double x1, double y1, double x2, double y2) {

        if ((rangoyi <= y1) && (y1 <= rangoys) && (rangoyi <= y2) && (y2 <= rangoys)) {
            g.drawLine(transformadax(x1), transformaday(y1),
                    transformadax(x2), transformaday(y2));
        }
    }

    /*** Dibuja el eje de abscisas (eje X)                                *********/
    /*** Parámetros: ninguno                                              *********/
    /*** devuelve: nada                                                    *********/
    void ejeX() throws SyntaxErrorException {
        int inc, i, pRango, fRango;
        if (num_pixelsx_unidad < minimaSeparacion) {
            inc = (int) (minimaSeparacion / num_pixelsx_unidad);
        } else {
            inc = 1;
        }
        g.setColor(this.colorEjeX);
        linea(rangoxi, 0.0, rangoxd, 0.0); // dibujamos eje X
        pRango = (int) Math.min(-inc, rangoxd);
        fRango = (int) rangoxi;
        for (i = pRango; i >= fRango; i -= inc) {
           if (this.ponerGrid) {
               g.setColor(this.colorGrid);
               linea(i,this.rangoys,i,this.rangoyi);
            }
            g.setColor(colorEjeX);
            marcaXarriba((double) i);
            
        }
        pRango = (int) Math.max(inc, rangoxi);
        fRango = (int) rangoxd;
        for (i = pRango; i <= fRango; i += inc) {
           if (this.ponerGrid) {
               g.setColor(this.colorGrid);
               linea(i,this.rangoys,i,this.rangoyi);
            }
            g.setColor(colorEjeX);
            marcaXarriba((double) i);
            
        }
    }

    /*** Dibuja el eje de ordenadas (eje Y)                               *********/
    /*** Parámetros: ninguno                                              *********/
    /*** devuelve: nada                                                    *********/
    void ejeY() {
        int inc, i, pRango, fRango;
        if (num_pixelsy_unidad < minimaSeparacion) {
            inc = (int) (minimaSeparacion / num_pixelsy_unidad);
        } else {
            inc = 1;
        }
        g.setColor(this.colorEjeY);
        linea(0.0, rangoyi, 0.0, rangoys); // dibujamos eje Y
        pRango = (int) Math.min(-inc, rangoys);
        fRango = (int) rangoyi;
        for (i = -inc; i >= fRango; i -= inc) {
           if (this.ponerGrid) {
               g.setColor(this.colorGrid);
               linea(this.rangoxi,(double)i,this.rangoxd,(double)i);
            }
            g.setColor(colorEjeY);
            marcaY((double) i);
        }
        pRango = (int) Math.max(inc, rangoyi);
        fRango = (int) rangoys;
        for (i = pRango; i <= fRango; i += inc) {
           if (this.ponerGrid) {
               g.setColor(this.colorGrid);
               linea(this.rangoxi,(double)i,this.rangoxd,(double)i);
            }
            g.setColor(colorEjeY);
            marcaY((double) i);
        }
    }

    /*** Dibuja una marca sobre el eje X                                  *********/
    /*** Parámetros: x-> punto sobre el eje X donde se dibuja la marca y  *********/
    /***                 valor que se escribirá sobre ella                *********/
    /***             p-> altura el puntos sobre el eje a la que se dibujará********/
    /***                 el valor de x.                                   *********/
    /*** devuelve: nada                                                    *********/
    void marcaX(double x, int p) throws SyntaxErrorException {
        if (rangoxi <= x && x <= rangoxd) {
            double y = 3 / num_pixelsy_unidad;
            String s;
            linea(x, -y, x, y);
            //double xr = miDouble.redondeo(x, numDecimales);
            double xr=miDouble.trunc(x,numDecimales);
            s = miDouble.toString(xr);
            if (miDouble.esEntero(xr)) {
                g.drawString(s, transformadax(x - 4 / num_pixelsx_unidad),
                        transformaday(p / num_pixelsy_unidad));
            } else {
                g.drawString(s, transformadax(x - (4 * numDecimales) / num_pixelsx_unidad),
                        transformaday(p / num_pixelsy_unidad));
            }
        }
    }

    /*** Dibuja una marca sobre el eje X y escribe un valor por encima de *********/
    /***    dicho eje (a una altura de 5 puntos sobre el mismo)           *********/
    /*** Parámetros: x-> punto sobre el eje X donde se dibuja la marca y  *********/
    /***                 el propio valor de x                             *********/
    /*** devuelve: nada                                                    *********/
    void marcaXarriba(double x) throws SyntaxErrorException {
        marcaX(x, 5);
    }

    /*** Dibuja marcas alternando la altura, sobre el eje X               *********/
    /*** Parámetros: x-> punto sobre el eje X sobre el que dibujar la     *********/
    /***     marca y el propio valor de x
    /*** devuelve: nada                                                    *********/
    void marcaXabajo(double x) throws SyntaxErrorException {
        if (ant == 14) {
            ant = 30;
        } else {
            ant = 14;
        }
        marcaX(x, -ant);
    }

    /*** Dibuja una marca sobre el eje Y                                  *********/
    /*** Parámetros: y-> punto sobre el eje Y donde se dibuja la marca y  *********/
    /***                 valor que se escribirá sobre ella                *********/
    /*** devuelve: nada                                                    *********/
    void marcaY(double y) {
        if (rangoyi <= y && y <= rangoys) {
            double x = 2 / num_pixelsx_unidad;
            String s;

            linea(-x, y, x, y);
            s = miDouble.toString(y);
            g.drawString(s, transformadax(7 / num_pixelsx_unidad),
                    transformaday(y - 4 / num_pixelsy_unidad));
        }
    }

    /*** Dibuja los ejes de coordenadas                                   *********/
    /*** Parámetros: ninguno                                              *********/
    /*** devuelve: nada                                                    *********/
    void inicio() throws SyntaxErrorException {
        ejeX();
        ejeY();
    }

   public void mouseClicked(MouseEvent e) {
      if (e.getClickCount()==2) {
         Color r = JColorChooser.showDialog(new JFrame(), "Elije un color", this.getBackground());
         if (r!=null) {
            this.setBackground(r);
         }
      }
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void keyTyped(KeyEvent e) {

   }

   public void keyPressed(KeyEvent e) {
      if (e.isAltDown()) {
         double inc=incremento*pppi;
         if (e.getKeyCode()==KeyEvent.VK_DOWN) {
            rangoxd+=inc;rangoxi-=inc;
            rangoyi-=inc;rangoys+=inc;
            establece_rangos();repaint();
         } else if (e.getKeyCode()==KeyEvent.VK_UP) {
            rangoxd-=inc;rangoxi+=inc;
            rangoyi+=inc;rangoys-=inc;
            establece_rangos();repaint();
         }
      } else if (e.isControlDown() && !this.centrar) {
         double inc=incremento*pppi;
         if (e.getKeyCode()==KeyEvent.VK_DOWN) {
            rangoys-=inc;rangoyi-=inc;
            establece_rangos();repaint();
         } else if (e.getKeyCode()==KeyEvent.VK_UP) {
            rangoys+=inc;rangoyi+=inc;
            establece_rangos();repaint();
         } else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
            rangoxi-=inc;rangoxd-=inc;
            establece_rangos();repaint();
         } else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
            rangoxi+=inc;rangoxd+=inc;
            establece_rangos();repaint();
         }
      }
   }

   public void keyReleased(KeyEvent e) {
   }


  

  

    /******************************************************************************/
    /*** Clase privada utilizada para almacenar valores significativos    *********/
    /***     para el dibujo de cada función. En JAVA no existen estructuras********/
    /***     como en C++, por lo que utilizamos un objeto que sólo tiene  *********/
    /***     datos (como si fuese una estructura, sin método alguno)      *********/
    /******************************************************************************/
    class valoresf {

        public double y1,
                y2,
                xmax,
                ymax,
                xmin,
                ymin;
    }

    /*** Dibuja todas las funciones contenidas en la lista de funciones   *********/
    /*** Parámetros: ninguno                                              *********/
    /*** devuelve: nada                                                    *********/
    synchronized void dibuja() throws SyntaxErrorException {
        int lenListaFunc = (listaFunciones==null?0:listaFunciones.getItemCount());
        if (lenListaFunc<=0) {
            return;
        }
        //Vector listaValores = new Vector();
        java.util.ArrayList<valoresf> listaValores=new java.util.ArrayList<valoresf>();
        int i;
        miDouble X;
        valoresf lv = new valoresf();
        arbol_binario arbol;
        double x1, x2;

        for (i = 0; i < lenListaFunc; i++) {
            listaValores.add(new valoresf());
        } // para calcular las funciones
        eval.asigna("x", rangoxi);
        X = eval.refMiDouble("x");
        x2 = x1 = rangoxi;
        for (i = 0; i < lenListaFunc; i++) {
            lv = listaValores.get(i);
            lv.ymax = rangoyi;
            lv.ymin = rangoys;
            lv.y1 = eval.expresion_aritmetica((arbol_binario) listaArboles.elementAt(i));
            if (lv.y1 == 0.0) {
                marcaXabajo(x1);
            }
            lv.xmax = lv.xmin = rangoxi;
        }
        while (x2 <= rangoxd) {
            x2 += incremento;
            X.asigna(x2);
            for (i = 0; i < lenListaFunc; i++) {
                lv = listaValores.get(i);
                arbol = (arbol_binario) listaArboles.elementAt(i);
                lv.y2 = eval.expresion_aritmetica(arbol);
                if (lv.y2 > lv.ymax && lv.y2 <= rangoys) { //&& !Double.isInfinite(lv.y2)) {
                    lv.xmax = x2;
                    lv.ymax = lv.y2;
                }
                if (lv.y2 < lv.ymin && lv.y2 >= rangoyi) {
                    lv.xmin = x2;
                    lv.ymin = lv.y2;
                }
                g.setColor((Color) listaColores.elementAt(i));
                linea(x1, lv.y1, x2, lv.y2);
                if (lv.y2 == 0.0) {
                    if (ponerPuntosDeCorte) {
                        marcaXabajo(x2);
                    }
                }
                if (miDouble.signo(lv.y1) != miDouble.signo(lv.y2)) {
                    if (ponerPuntosDeCorte) {
                        puntoCorte(x1, x2, lv.y1, lv.y2, arbol, X);
                        X.asigna(x2);
                    }
                } else if (Math.abs(lv.y2) <= 0.00000000000001) {
                    if (ponerPuntosDeCorte) {
                        this.marcaXabajo(lv.y2);
                    }
                }
                lv.y1 = lv.y2;
            }
            x1 = x2;
        }
        for (i = 0; i < lenListaFunc; i++) {
            altMaxMin = !altMaxMin;
            lv = listaValores.get(i);
            ponNombreFuncion(lv, i, altMaxMin);
        }
    }

    /*** Dibuja el nombre de una función por encima o por debajo de la    *********/
    /***     misma, según el valor de altMaxMin                           *********/
    /*** Parámetros: lv-> registro de valores de la función               *********/
    /***             i-> índice de la función en la lista de funciones    *********/
    /***             altMaxMin-> true, por encima, false, por debajo      *********/
    /*** devuelve: nada                                                    *********/
    void ponNombreFuncion(valoresf lv, int i, boolean altMaxMin) {
        if (this.ponerNombresFunciones) {
            double x, y;
            String f = (String) listaFunciones.getItemAt(i);
            int tamano = g.getFontMetrics(g.getFont()).stringWidth(f);
            g.setFont(new Font("Verdana",1,14));
            if (altMaxMin) {
                x = lv.xmax;
                y = lv.ymax + 3 / num_pixelsy_unidad;
            } else {
                x = lv.xmin;
                y = lv.ymin - 14 / num_pixelsy_unidad;
            }
            if (transformadax(x) + tamano > transformadax(rangoxd)) {
                x = rangoxd - (tamano + borde) / num_pixelsx_unidad;
            }
            g.setColor((Color) listaColores.elementAt(i));
            g.drawString(f, transformadax(x), transformaday(y));
        }
    }

    /*** Calcula por aproximación por el método de la cuerda y bisección combinados
     *** un punto de  *********/
    /***     corte comprendido entre a y b                                *********/
    /*** Parámetros: a-> valor inferior del intervalo                     *********/
    /***             b-> valor superior del intervalo                     *********/
    /***             fa-> imagen por la función de a                      *********/
    /***             fb-> imagen por la función de b                      *********/
    /***             arbol-> arbol sintáctico de la función               *********/
    /***             X-> referencia al valor de la variable "x" dentro del*********/
    /***                 evaluador, se utiliza para aumentar la eficiencia*********/
    /***                 en velocidad, igual que el parámetro anterior    *********/
    /*** devuelve: nada                                                    *********/
    void puntoCorte(double a, double b, double fa, double fb,
            arbol_binario arbol, miDouble X) throws SyntaxErrorException { // método de la cuerda
        double xn, fxn = 10.0, antXn = rangoxi - 5; // damos a antXn un valor fuera de rango

        if (Double.isInfinite(fa) || Double.isInfinite(fb)
                || Double.isNaN(fa) || Double.isNaN(fb)) {
            return;
        }
        int i=0; boolean cuerda=true; double minVal=0.00000000000001;
        while (true && i++<=100000) {
            if (cuerda) {
               xn = (a * fb - b * fa) / (fb - fa);
               cuerda=false;
            } else {
               xn = a+(b-a)/2;
               cuerda=true;
            }
            if (Math.abs(xn - antXn) <= minVal) { //Math.pow(10, -numDecimales)) {
                if (Math.abs(fxn) <= minVal){ //0.001) {
                    marcaXabajo(xn);
                }
                return;
            }
            antXn = xn;
            X.asigna(xn);
            fxn = eval.expresion_aritmetica(arbol);
            if (Double.isInfinite(fxn) || Double.isNaN(fxn)) {
                return;
            }
            if (miDouble.signo(fxn) == miDouble.signo(fb)) {
                b = xn;
                fb = fxn;
            } else { // if (miDouble.signo(xn)==miDouble.signo(fa))
                a = xn;
                fa = fxn;
            }
        }
    }

    /*** Devuelve las dimensiones de la ventana de ejecución de la aplicación *****/
    /*** parámetros: ninguno                                                  *****/
    public Dimension dimensiones() {
        return new Dimension(this.getSize().width - 2 * borde,
                this.getSize().height - 2 * borde);
    }

    /*** Establece los rangos sobre los ejes de coordenadas sobre los     *********/
    /***     cuales se representarán las funciones.                       *********/
    /*** Parámetros: rxi-> valor inferior rango eje X                     *********/
    /***             rxd-> valor superior rango eje X                     *********/
    /***             ryi-> valor inferior rango eje Y                     *********/
    /***             rys-> valor superior rango eje Y                     *********/
    /***             ppi-> número de iteraciones por pixel, es decir,     *********/
    /***                   número de veces que se calcula la función entre*********/
    /***                   pixel y pixel. Hace que la representación sea  *********/
    /***                   más exacta                                     *********/
    /*** devuelve: nada                                                    *********/
    public void establece_rangos2() {
        incremento = (double) (rangoxd - rangoxi) / this.dimensiones().width; //(xd-xi)/anchura
        num_pixelsx_unidad = (double) (this.dimensiones().width - 1) / (rangoxd - rangoxi);
        double rangoy=((double)this.dimensiones().height-1)/(num_pixelsx_unidad);
        
        if (this.mantenerEscala) {
           //rangoyi=-((double)this.dimensiones().height-1)/(num_pixelsx_unidad*2);
           rangoys=rangoyi+rangoy;
        }
        num_pixelsy_unidad = (double) (this.dimensiones().height - 1) / (rangoys - rangoyi);
        incremento /= this.pppi; //calculamos la función pppi veces por cada pixel
    }
    public synchronized void establece_rangos() {
        num_pixelsy_unidad = (double) (this.dimensiones().height - 1) / (rangoys - rangoyi);
        double rangox=rangoxd-rangoxi;
        if (this.mantenerEscala) {
           rangox=((double)this.dimensiones().width-1)/(num_pixelsy_unidad);
           //rangoyi=-((double)this.dimensiones().height-1)/(num_pixelsx_unidad*2);
           rangoxd=rangoxi+rangox;
        }
        incremento = (double) (rangoxd - rangoxi) / this.dimensiones().width; //(xd-xi)/anchura
        num_pixelsx_unidad = (double) (this.dimensiones().width - 1) / (rangoxd - rangoxi);
        incremento /= this.pppi; //calculamos la función pppi veces por cada pixel
        if (centrar) {
           rangoxd=rangox/2;
           rangoxi=-rangoxd;
           rangoys=(rangoys-rangoyi)/2;
           rangoyi=-rangoys;
        }
        for(int i=0;i<this.notificadosCambiosRangos.size();i++) {
           this.notificadosCambiosRangos.get(i).rangosNuevos(rangoxi, rangoxd, rangoyi, rangoys);
        }
    }
    public void establece_rangos(double rxi, double rxd, double ryi, double rys,
            double pppi) {
        this.rangoxi = rxi;
        this.rangoxd = rxd;
        this.rangoyi = ryi;
        this.rangoys = rys;
        this.pppi=pppi;
        establece_rangos();
    }
    public void añadirNotificadosCambiosRangos(notificacionCambiosMiCanvas notificado) {
       this.notificadosCambiosRangos.add(notificado);
    }
    public void quitarNotificadosCambiosRangos(notificacionCambiosMiCanvas notificado) {
       for(int i=0;i<this.notificadosCambiosRangos.size();i++) {
          if (this.notificadosCambiosRangos.get(i)==notificado) {
             this.notificadosCambiosRangos.remove(i);
          }
       }
    }
    public boolean getCentrar() {
       return this.centrar;
    }
    public void setCentrar(boolean c) {
       this.centrar=c;
    }
    public boolean getMantenerEscala() {
       return this.mantenerEscala;
    }
    public void setMantenerEscala(boolean c) {
       this.mantenerEscala=c;
    }
}

//------------------------------------ Final del fichero miCanvas.java

