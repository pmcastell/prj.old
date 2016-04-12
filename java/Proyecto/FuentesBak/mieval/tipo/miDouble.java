/******************************************************************************/
/******************         miDouble.java       *******************************/
/******************************************************************************/
package mieval.tipo;
import mieval.SyntaxErrorException;
import java.lang.Math;
public class miDouble extends objeto {
   Double D;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
   public miDouble(objeto o)
   {
      D=new Double(o.doubleValue());
   }
   public miDouble(double d)  {
      D=new Double(d);
   }
   public miDouble(int d) {
      D=new Double(d);
   }
   public miDouble(long d) {
      D=new Double(d);
   }
   public miDouble(float d) {
      D=new Double(d);
   }
   public miDouble(String d)
   {
      try {
         D=new Double(d);
      } catch (NumberFormatException e) {
      }
   }

/*** Copia el valor doble de un objeto en este                       **********/
/*** Parámetros: o-> objeto del cual se copiará su valor doble       **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(objeto o)
   {
      D=new Double(o.doubleValue());
   }

/*** Asigna un valor doble a este objeto                             **********/
/*** Parámetros: d-> objeto del cual se copiará su valor doble       **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(miDouble d)
   {
      D=new Double(d.doubleValue());
   }

/*** Copia el valor doble de un objeto en este                       **********/
/*** Parámetros: o-> valor doble                                     **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(double d)
   {
      D=new Double(d);
   }

/*** Devuelve una copia de este objeto                               **********/
/*** Parámetros: nada                                                **********/
   public objeto copia()
   {
      return new miDouble(this.doubleValue());
   }

/*** Nos dice si la parte decimal de un doble es cero                **********/
/*** Parámetros: dob-> valor doble                                   **********/
/*** Deuelve: si el valor doble es entero                            **********/
   public static boolean esEntero(double dob)
   {
      return ((int) dob==dob);
   }

/*** Nos dice si la parte decimal del valor doble de este objeto     **********/
/***     es cero                                                     **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: si este doble es entero                                **********/
   public boolean esEntero()
   {
      return esEntero(this.doubleValue());
   }

/*** Nos dice si este doble es entero y es par                       **********/
/*** Parámetros: dob-> valor doble                                   **********/
/*** Deuelve: si es entero y par                                     **********/
   public static boolean esPar(double dob)
   {
      return esEntero(dob) && dob % 2== 0;
   }

/*** Nos dice si el valor doble de este objeto es entero y par       **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: si es entero y par                                     **********/
   public boolean esPar()
   {
      return esEntero() && D.intValue() % 2== 0;
   }

/*** Convierte a cadena un valor doble                               **********/
/*** Parámetros: d-> valor doble                                     **********/
/*** Deuelve: una cadena representando al valor doble                **********/
   public static String toString(double d)
   {
      if (esEntero(d)) {
         return Integer.toString((int) d);
      } else {
         if (Double.isNaN(d)) {
            return("Indeterminado");
         } else if (Double.isInfinite(d)) {
            if (d==Double.NEGATIVE_INFINITY) {
               return("-Infinito");
            } else {
               return("Infinito");
            }
         } else {
            return Double.toString(d);
         }
      }
   }

/*** Convierte a cadena al valor doble de este objeto                **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: cadena representando el valor doble de este objeto     **********/
   public String toString()
   {
      return toString(D.doubleValue());
   }

/*** Convierte a bolean al valor doble de este objeto                **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: True si igual 0.0, False caso contrario                **********/
   public boolean bolValue()
   {
      if (D.doubleValue()==0.0) {
         return true;
      } else {
         return false;
      }
   }

/*** Convierte a entero al valor doble de este objeto                **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: Valor truncado del doble                               **********/
   public int intValue()
   {
      return D.intValue();
   }

/*** Deuelve el valor doble de este objeto                           **********/
/*** Parámetros: ninguno                                             **********/
   public double doubleValue()
   {
      return D.doubleValue();
   }

/*** Compara el valor doble de este objeto con el de otro            **********/
/*** Parámetros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si es menor o no                                       **********/
   public boolean menor(objeto o)
   {
      return D.doubleValue()<o.doubleValue();
   }

/*** Compara el valor doble de este objeto con el de otro            **********/
/*** Parámetros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si es igual o no                                       **********/
   public boolean igual(objeto o)
   {
      return D.doubleValue()==o.doubleValue();
   }

/*** Devuelve un valor asociado al signo del valor doble del parámetro*********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: 1, -1 ó 0                                              **********/
   public static int signo(double x)
   {
      if (x<0.0) {
         return -1;
      } else if (x>0.0) {
         return 1;
      } else {
         return 0;
      }
   }
/*** Devuelve el valor entero asociado al valor doble de este objeto **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: 1, -1 ó 0                                              **********/
   public int signo()
   {
      return signo(this.doubleValue());
   }

/*** Redondea a nDecimales el valor x                                **********/
/*** Parámetros: x-> valor doble a redondear                         **********/
/***             nDecimales-> número de decimales a redondear        **********/
/*** Deuelve: el número redondeado                                   **********/
   public static double redondeo(double x, int nDecimales) throws
                                                            SyntaxErrorException
   {
      if (nDecimales<0) {
         throw new SyntaxErrorException("no se puede redondear a "+nDecimales+
                                        " decimales.");
      }
      if (nDecimales==0) {
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

/*** Trunca a nDecimales el valor x                                  **********/
/*** Parámetros: x-> valor doble a truncar                           **********/
/***             nDecimales-> número de decimales a truncar          **********/
/*** Deuelve: el número truncado                                     **********/
   public static double trunc(double x, int nDecimales)
                                                     throws SyntaxErrorException
   {
      if (nDecimales<0) {
         throw new SyntaxErrorException("no se puede redondear a "+nDecimales+
                                        " decimales.");
      }
      if (nDecimales==0) {
         return (double) ((long) x);
      } else {
         if (x<0.0) {
            return -trunc(-x, nDecimales);
         } else if (x==0.0) {
            return 0.0;
         } else {
            x=(long) (x*Math.pow(10, nDecimales));
            x/=Math.pow(10, nDecimales);
         }
      }
      return x;
   }


}