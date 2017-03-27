import java.awt.*;

public class superCanvas extends Canvas {
    double rangoxi, rangoxd,  rangoys, rangoyi,  incremento;
    double num_pixelsx_unidad, num_pixelsy_unidad;
    Color defaultColor=Color.blue;
    Graphics g=getGraphics();
    final static int minimaSeparacion=40;
    int borde=minimaSeparacion;
    int ant=14;
    boolean altMaxMin=false;
    int numDecimales=2;
    double ox=0.0, oy=0.0;
    int angulo=0, velocidad=0;
    double sen_alfa, cos_alfa;
    int radio=5;
    TextField controlInfo;


    public superCanvas(double rxi, double rxd, double ryi, double rys)
    {
        this();
        establece_rangos(rxi,rxd,ryi,rys);
    }
    public superCanvas()
    {
    }
    public superCanvas(int width, int heigth) {
       this();
       super.setSize(width, heigth);
    }
    public void setControlInfo(TextField l) {
       this.controlInfo=l;
    }   

    public void setAngulo(int a) {
       this.angulo=a;
    }
    public void setVelocidad(int v) {
       this.velocidad=v;
    }

    public void paint(Graphics g)
    {
       ejes();
    }

/*** Transforma la coordenada de abscisas (eje X) a coordenada de     *********/
/***     pantalla horizontal, es decir, la distancia en pixeles desde *********/
/***     la parte izquierda de la pantalla.                           *********/
/*** Parámetros: x-> coordenada a transformar                         *********/
/*** Deuelve: la coordenada de pantalla transformada de x             *********/
    int transformadax(double x)
    {
       return (int) (num_pixelsx_unidad*(x-rangoxi))+borde;
    }

/*** Transforma la coordenada de ordenadas (eje Y) a coordenada de    *********/
/***     pantalla vertical, es decir, la distancia en pixeles desde   *********/
/***     la parte superior de la pantalla.                            *********/
/*** Parámetros: x-> coordenada a transformar                         *********/
/*** Deuelve: la coordenada de pantalla transformada de x             *********/
    int transformaday(double y)
    {
       return (int) (num_pixelsy_unidad*(rangoys-y))+borde;
    }

/*** Dibuja una línea recta que pasa por los puntos (x1,y1),(x2,y2)   *********/
/*** Parámetros: x1,y1,x2,y2-> coordenadas de los 2 puntos por los    *********/
/***     que pasa la línea recta.
/*** Deuelve: nada                                                   *********/
    void linea(double x1,double y1,double x2,double y2)
    {

//       if ((rangoyi<=y1) && (y1<=rangoys) && (rangoyi<=y2) && (y2<=rangoys)) {
           g.drawLine(transformadax(x1),transformaday(y1),
                      transformadax(x2),transformaday(y2));
//       }
    }

/*** Dibuja el eje de abscisas (eje X)                                *********/
/*** Parámetros: ninguno                                              *********/
/*** Deuelve: nada                                                    *********/
    void ejeX()
    {
       int inc, i, pRango, fRango;
       if (num_pixelsx_unidad<minimaSeparacion) {
          inc=(int) (minimaSeparacion/num_pixelsx_unidad);
       } else {
          inc=1;
       }
       g.setColor(Color.black);
       linea(rangoxi, 0.0, rangoxd, 0.0); // dibujamos eje X
       pRango=(int) Math.min(-inc, rangoxd);
       fRango=(int) rangoxi;
       for(i=pRango; i>=fRango; i-=inc) {
          marcaXabajo((double) i);
       }
       pRango=(int) Math.max(inc, rangoxi);
       fRango=(int) rangoxd;
       for(i=pRango; i<=fRango; i+=inc) {
          marcaXabajo((double) i);
       }
    }

/*** Dibuja el eje de ordenadas (eje Y)                               *********/
/*** Parámetros: ninguno                                              *********/
/*** Deuelve: nada                                                    *********/
    void ejeY()
    {
       int inc, i, pRango, fRango;
       if (num_pixelsy_unidad<minimaSeparacion) {
          inc=(int) (minimaSeparacion/num_pixelsy_unidad);
       } else {
          inc=1;
       }
       g.setColor(Color.black);
       linea(0.0,rangoyi,0.0,rangoys); // dibujamos eje Y
       pRango=(int) Math.min(-inc, rangoys);
       fRango=(int) rangoyi;
       for(i=-inc; i>=fRango; i-=inc) {
          marcaYizda((double) i);
       }
       pRango=(int) Math.max(inc, rangoyi);
       fRango=(int) rangoys;
       for(i=pRango; i<=fRango; i+=inc) {
          marcaYizda((double) i);
       }
    }
   public static boolean esEntero(double dob)
   {
      return ((int) dob==dob);
   }

   public static double redondeo(double x, int nDecimales)
   {
      if (nDecimales<=0) {
         return (double) ((long) x);
      } else {
         if (x<0.0) {
            return -redondeo(-x,nDecimales);
         } else if (x==0.0) {
            return 0.0;
         } else {
            x=(long) (x*Math.pow(10,nDecimales)+0.5);
            x/=Math.pow(10,nDecimales);
         }
      }
      return x;
   }
/*** Dibuja una marca sobre el eje X                                  *********/
/*** Parámetros: x-> punto sobre el eje X donde se dibuja la marca y  *********/
/***                 valor que se escribirá sobre ella                *********/
/***             p-> altura el puntos sobre el eje a la que se dibujará********/
/***                 el valor de x.                                   *********/
/*** Deuelve: nada                                                    *********/
    void marcaX(double x, int p)
    {
       if (rangoxi<=x && x<=rangoxd) {
          double y=3/num_pixelsy_unidad; String s;
          linea(x, -y, x, y);
          double xr=redondeo(x,numDecimales);
          s=Double.toString(xr);
          if (esEntero(xr)) {
             g.drawString(s, transformadax(x-4/num_pixelsx_unidad),
                              transformaday(p/num_pixelsy_unidad));
          } else {
             g.drawString(s, transformadax(x-(4*numDecimales)/num_pixelsx_unidad),
                              transformaday(p/num_pixelsy_unidad));
          }
       }
    }

/*** Dibuja una marca sobre el eje X y escribe un valor por encima de *********/
/***    dicho eje (a una altura de 5 puntos sobre el mismo)           *********/
/*** Parámetros: x-> punto sobre el eje X donde se dibuja la marca y  *********/
/***                 el propio valor de x                             *********/
/*** Deuelve: nada                                                    *********/
    void marcaXarriba(double x)
    {
       marcaX(x,5);
    }

/*** Dibuja marcas alternando la altura, sobre el eje X               *********/
/*** Parámetros: x-> punto sobre el eje X sobre el que dibujar la     *********/
/***     marca y el propio valor de x
/*** Deuelve: nada                                                    *********/
    void marcaXabajo(double x)
    {
//       if (ant==14) {
//          ant=30;
//       } else {
//          ant=14;
//       }
       marcaX(x,-ant);
    }
/*** Dibuja una marca sobre a la derecha del eje Y                    *********/
/*** Parámetros: y-> punto sobre el eje Y donde se dibuja la marca y  *********/
/***                 valor que se escribirá sobre ella                *********/
/*** Deuelve: nada                                                    *********/
    void marcaYdcha(double y)
    {
       if (rangoyi<=y && y<=rangoys) {
          double x=2/num_pixelsx_unidad; String s;

          linea(-x, y, x, y);
          s=Double.toString(y);
          g.drawString(s, transformadax(3/num_pixelsx_unidad),
                          transformaday(y-4/num_pixelsy_unidad));
       }
    }


/*** Dibuja una marca a la izquierda del eje Y                        *********/
/*** Parámetros: y-> punto sobre el eje Y donde se dibuja la marca y  *********/
/***                 valor que se escribirá sobre ella                *********/
/*** Deuelve: nada                                                    *********/
    void marcaYizda(double y)
    {
       if (rangoyi<=y && y<=rangoys) {
          double x=2/num_pixelsx_unidad; String s;

          linea(-x, y, x, y);
          s=Double.toString(y);
          g.drawString(s, transformadax(-borde/num_pixelsx_unidad),
                          transformaday(y-4/num_pixelsy_unidad));
       }
    }


/*** Dibuja los ejes de coordenadas                                   *********/
/*** Parámetros: ninguno                                              *********/
/*** Deuelve: nada                                                    *********/
    void inicio()
    {
       ejeX();
       ejeY();
    }
    double X(double velocidad, double angulo, double t) {
       return velocidad*Math.cos(angulo*Math.PI/180)*t;
    }
    double X(double t) {
       return velocidad*cos_alfa*t;
    }
    double XMax(int velocidad, int angulo) {
       double t=2*velocidad*Math.sin(angulo*Math.PI/180)/9.80665;// tiempo max. alcance
       return X((double) velocidad, (double) angulo, t);// x(t)
    }

    double Y(double velocidad, double angulo, double t) {
       return (velocidad*Math.sin(angulo*Math.PI/180)-0.5*9.80665*t)*t;
    }
    double Y(double t) {
       return (velocidad*sen_alfa-0.5*9.80665*t)*t;
    }
    double YMax(int velocidad, int angulo) {
       double t=velocidad*Math.sin(angulo*Math.PI/180)/9.80665; //tiempo altura max.
       return Y((double) velocidad, (double) angulo, t); // y(t)
    }

    void ejes(double x1, double y1, double x2, double y2, double xmax, double ymax)
    {
       g.setColor(Color.black);
       linea(x1, y1, 1.1*x2, y1);
       linea(x1, y1, x1, 1.2*y2);
       marcaXarriba(redondeo(x2,numDecimales));//x2 representa alcance máximo
       marcaXarriba(redondeo(xmax,numDecimales));
       marcaYdcha(redondeo(y2,numDecimales));//y2 representa altura máxima
       marcaYizda(redondeo(ymax,numDecimales));
    }
    void parabolaParam()
    {
       double t=0;
       cos_alfa=Math.cos(angulo*Math.PI/180);
       sen_alfa=Math.sin(angulo*Math.PI/180);
       double tImpacto=2*velocidad*sen_alfa/9.80665;
       double incremento=0.01*velocidad;
       double x1,x2,y1,y2;
       g=this.getGraphics();
       g.setColor(Color.black);

       x1=x2=ox;
       y1=y2=oy;
       while (t<tImpacto) {
          x1=x2;
          y1=y2;
          t+=incremento;
          if (t>tImpacto) {
             t=tImpacto;
          }   
          x2=X(t);
          y2=Y(t);
          linea(x1,y1,x2,y2);          
       }
    }

    void dibujaParabola()
    {
       double tg=Math.tan(this.angulo*Math.PI/180);
       double a=9.80665/(2*Math.pow(velocidad,2)*Math.pow(Math.cos(angulo*Math.PI/180),2));
       double x1, y1, x2, y2;
       double incremento=rangoxd/this.num_pixelsx_unidad/velocidad;
       g=this.getGraphics();
       g.setColor(Color.black);


       x1=x2=0;
       y1=y2=0;

       while (y2>=0) {
          linea(x1,y1,x2,y2);
          x1=x2;
          y1=y2;
          x2+=incremento;
          y2=(tg-a*x2)*x2;
       }
    }




    void clear()
    {
       g=this.getGraphics();
       g.clearRect(0,0,this.getSize().width,this.getSize().height);
    }
    void ejes()
    {
       double t=0;
       double x1,x2,y1,y2;
       cos_alfa=Math.cos(angulo*Math.PI/180);
       sen_alfa=Math.sin(angulo*Math.PI/180);
       double Ymax, Xmax, alcanceMax, alturaMax;
       double tImpacto=2*velocidad*sen_alfa/9.80665;
       g=this.getGraphics();

       alcanceMax=XMax(velocidad, 45); // Máximo alcance para una v dada
       alturaMax=YMax(velocidad, 90); // máxima altura del proyectil para v dada

       Xmax=XMax(velocidad, angulo); //máximo alcance para v y alfa dados
       Ymax=YMax(velocidad, angulo); //máxima altura para v y alfa dados
       clear();
       this.establece_rangos(ox, 1.1*alcanceMax+1, oy, 1.2*alturaMax+1);
       ejes(ox, oy, alcanceMax, alturaMax, Xmax, Ymax);
       this.parabolaParam();
//       dibujaParabola();
    }



    void parabolico() {
       double t=0;
       double x1,x2,y1,y2;
       cos_alfa=Math.cos(angulo*Math.PI/180);
       sen_alfa=Math.sin(angulo*Math.PI/180);
       double Ymax, Xmax, alcanceMax, alturaMax;
       double tImpacto=2*velocidad*sen_alfa/9.80665;
       g=this.getGraphics();

       alcanceMax=XMax(velocidad, 45); // Máximo alcance para una v dada
       alturaMax=YMax(velocidad, 90); // máxima altura del proyectil para v dada

       Xmax=XMax(velocidad, angulo); //máximo alcance para v y alfa dados
       Ymax=YMax(velocidad, angulo); //máxima altura para v y alfa dados

       this.establece_rangos(ox, 1.1*alcanceMax+1, oy, 1.2*alturaMax+1);
       ejes(ox, oy, alcanceMax, alturaMax, Xmax, Ymax);

       long time;
       double incremento;
       incremento=0.02*velocidad/10;


       x1=x2=ox;
       y1=y2=oy;
       ejes();
       do {
          g.setColor(this.defaultColor);
          g.fillOval(transformadax(x1)-radio, transformaday(y1)-radio, 2*radio, 2*radio);
          t+=incremento;
          controlInfo.setText(Double.toString(t));
          time=System.currentTimeMillis();
          if (x1<=(radio+5)/this.num_pixelsx_unidad || y1<=(radio+5)/this.num_pixelsy_unidad) {
             ejes(ox, oy, alcanceMax, alturaMax, Xmax, Ymax);
          }
          while (System.currentTimeMillis()<time+incremento);
          x2=X(t);
          y2=Y(t);
          g.setColor(this.getBackground());
          g.fillOval(transformadax(x1)-radio, transformaday(y1)-radio, 2*radio, 2*radio);
          x1=x2;
          y1=y2;
       } while (t<=tImpacto);
       ejes(ox, oy, alcanceMax, alturaMax, Xmax, Ymax);
       controlInfo.setText(Double.toString(tImpacto));       
       g.setColor(this.defaultColor);
       g.fillOval(transformadax(Xmax)-radio, transformaday(0)-radio, 2*radio, 2*radio);
    }





/*** Devuelve las dimensiones de la ventana de ejecución de la aplicación *****/
/*** parámetros: ninguno                                                  *****/
    public Dimension dimensiones()
    {
       int w, h;
       w=this.getSize().width;
       h=this.getSize().height;
       return new Dimension(this.getSize().width-2*borde,
                                                 this.getSize().height-2*borde);
    }

/*** Establece los rangos sobre los ejes de coordenadas               *********/
/*** Parámetros: rxi-> valor inferior rango eje X                     *********/
/***             rxd-> valor superior rango eje X                     *********/
/***             ryi-> valor inferior rango eje Y                     *********/
/***             rys-> valor superior rango eje Y                     *********/
/*** Deuelve: nada                                                    *********/
    public void establece_rangos(double rxi,double rxd,double ryi,double rys) {
       this.rangoxi=rxi;
       this.rangoxd=rxd;
       this.rangoyi=ryi;
       this.rangoys=rys;

       incremento=(double) (rangoxd-rangoxi)/this.dimensiones().width; //(xd-xi)/anchura
       num_pixelsx_unidad=(double) (this.dimensiones().width-1) / (rangoxd-rangoxi);
       num_pixelsy_unidad=(double) (this.dimensiones().height-1)/ (rangoys-rangoyi);
    }


}



