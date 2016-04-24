/******************************************************************************/
/******************         miDouble.java       *******************************/
/******************************************************************************/
package mieval.tipo; 
import mieval.SyntaxErrorException;
import java.lang.Math;

public class miDouble extends objeto {
   static java.text.DecimalFormat df=new java.text.DecimalFormat ("0.################");
	static {
	   df.getDecimalFormatSymbols().setDecimalSeparator('.'); // ni put* caso
	}
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
/*** Par�metros: o-> objeto del cual se copiar� su valor doble       **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(objeto o)
   {
      D=new Double(o.doubleValue());
   }

/*** Asigna un valor doble a este objeto                             **********/
/*** Par�metros: d-> objeto del cual se copiar� su valor doble       **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(miDouble d)
   {
      D=new Double(d.doubleValue());
   }

/*** Copia el valor doble de un objeto en este                       **********/
/*** Par�metros: o-> valor doble                                     **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(double d)
   {
      D=new Double(d);
   }

/*** Devuelve una copia de este objeto                               **********/
/*** Par�metros: nada                                                **********/
   public objeto copia()
   {
      return new miDouble(this.doubleValue());
   }

/*** Nos dice si la parte decimal de un doble es cero                **********/
/*** Par�metros: dob-> valor doble                                   **********/
/*** Deuelve: si el valor doble es entero                            **********/
   public static boolean esEntero(double dob)
   {
      return ((int) dob==dob);
   }

/*** Nos dice si la parte decimal del valor doble de este objeto     **********/
/***     es cero                                                     **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: si este doble es entero                                **********/
   public boolean esEntero()
   {
      return esEntero(this.doubleValue());
   }

/*** Nos dice si este doble es entero y es par                       **********/
/*** Par�metros: dob-> valor doble                                   **********/
/*** Deuelve: si es entero y par                                     **********/
   public static boolean esPar(double dob)
   {
      return esEntero(dob) && dob % 2== 0;
   }

/*** Nos dice si el valor doble de este objeto es entero y par       **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: si es entero y par                                     **********/
   public boolean esPar()
   {
      return esEntero() && D.intValue() % 2== 0;
   }

/*** Convierte a cadena un valor doble                               **********/
/*** Par�metros: d-> valor doble                                     **********/
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
            try {
                //d=redondeo(d,13);
            } catch (Exception ex) {
            } finally {
                if (d>10e10 || d<10e-6) {
                   return Double.toString(d);
                } else {
                   return df.format(d).replace(',','.');
                }
            }
         }
      }
   }

/*** Convierte a cadena al valor doble de este objeto                **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: cadena representando el valor doble de este objeto     **********/
   public String toString()
   {
      return toString(D.doubleValue());
   }

/*** Convierte a bolean al valor doble de este objeto                **********/
/*** Par�metros: ninguno                                             **********/
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
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: Valor truncado del doble                               **********/
   public int intValue()
   {
      return D.intValue();
   }

/*** Deuelve el valor doble de este objeto                           **********/
/*** Par�metros: ninguno                                             **********/
   public double doubleValue()
   {
      return D.doubleValue();
   }

/*** Compara el valor doble de este objeto con el de otro            **********/
/*** Par�metros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si es menor o no                                       **********/
   public boolean menor(objeto o)
   {
      return D.doubleValue()<o.doubleValue();
   }

/*** Compara el valor doble de este objeto con el de otro            **********/
/*** Par�metros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si es igual o no                                       **********/
   public boolean igual(objeto o)
   {
      return D.doubleValue()==o.doubleValue();
   }

/*** Devuelve un valor asociado al signo del valor doble del par�metro*********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: 1, -1 � 0                                              **********/
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
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: 1, -1 � 0                                              **********/
   public int signo()
   {
      return signo(this.doubleValue());
   }

/*** Redondea a nDecimales el valor x                                **********/
/*** Par�metros: x-> valor doble a redondear                         **********/
/***             nDecimales-> n�mero de decimales a redondear        **********/
/*** Deuelve: el n�mero redondeado                                   **********/
   public static double potencia(double base,int exponente) {
      double res=base;
      for(int i=1;i<exponente;i++) {
         res*=base;
      }
      return res;
   }
   

   static double potencias[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
   static double potenciasNegativas[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
   static {
      for(int i=1;i<=15;i++) {
         potencias[i]=Math.pow(10, i);
         potenciasNegativas[i]=Math.pow(10,-i);
         //System.out.println(potencias[i]);
      }
   }
   
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
            //x=(long) (x*Math.pow(10,nDecimales)+0.5);
            //return x/Math.pow(10,nDecimales);
            x=(long) (x*potencias[nDecimales]+0.5);
            return x/potencias[nDecimales];
         }
      }
   }
   public double redondeo(int numDecimales) throws SyntaxErrorException {
       return redondeo(this.doubleValue(),numDecimales);
   }

/*** Trunca a nDecimales el valor x                                  **********/
/*** Par�metros: x-> valor doble a truncar                           **********/
/***             nDecimales-> n�mero de decimales a truncar          **********/
/*** Deuelve: el n�mero truncado                                     **********/
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
            x=(long) (x*potencias[nDecimales]);//Math.pow(10, nDecimales));
            x/=potencias[nDecimales];//Math.pow(10, nDecimales);
         }
      }
      return x;
   }
   public objeto suma(objeto o) {
       if (o instanceof miDouble) {
           return new miDouble(this.doubleValue()+o.doubleValue());
       } else {
           return new miString(this.toString()+o.toString());
       }
   }
   public objeto resta(objeto o) {
       if (o instanceof miDouble) {
           return new miDouble(this.doubleValue()-o.doubleValue());
       } else {
           return miString.resta(this,o);
       }
   }
   public objeto producto(objeto o) throws SyntaxErrorException {
       if (o instanceof miDouble) {
           return new miDouble(this.doubleValue()*o.doubleValue());
       } else if (o instanceof miArray) {
           return miArray.producto(this,(miArray)o);
       } else {
           throw new SyntaxErrorException("Error en producto: tipos no coinciden.");
       }
   }
   public objeto division(objeto o)  throws SyntaxErrorException {
      if (o instanceof miDouble) {
          return new miDouble(this.doubleValue()/o.doubleValue());
      } else {
          throw new SyntaxErrorException("Error en divisi�n: tipos no coinciden.");
      }
   }
   public objeto modulo(objeto o) throws SyntaxErrorException {
       if (o instanceof miDouble) {
           return new miDouble(this.doubleValue()%o.doubleValue());
       } else {
           throw new SyntaxErrorException("Error en m�dulo: tipos no coinciden.");
       }
       
   }
   public objeto potencia(objeto o) throws SyntaxErrorException {
      if (o instanceof miDouble) {
         miDouble res=new miDouble(Math.pow(this.doubleValue(),o.doubleValue()));
         return res;
      } else {
         throw new SyntaxErrorException("Error no tiene sentido potencia de booleano exponente decimal.");
      }
   }
   public void inc() throws SyntaxErrorException {
       this.D=new Double(this.D.doubleValue()+1);
   }
   public void dec() throws SyntaxErrorException {
       this.D=new Double(this.D.doubleValue()-1);
   }
}
