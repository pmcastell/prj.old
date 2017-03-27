/******************************************************************************/
/******************         miString.java       *******************************/
/******************************************************************************/
package mieval.tipo;

public class miString extends objeto {
   String S;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
   public miString(objeto o)
   {
      S=o.toString();
   }
   public miString(double d)  {
      S=Double.toString(d);
   }
   public miString(int d) {
      S=Integer.toString(d);
   }
   public miString(long d) {
      S=Long.toString(d);
   }
   public miString(float d) {
      S=Float.toString(d);
   }
   public miString(String s) {
      S=new String(s);
   }
   public miString(char c)
   {
      S=new String(""+c);
   }

/*** Copia el valor de cadena de otro objeto en este                 **********/
/*** Parámetros: o-> objeto del cual se copiará su valor de cadena   **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(objeto o)
   {
      S=o.toString();
   }

/*** Copia el valor de cadena de un objeto cadena en este            **********/
/*** Parámetros: d-> objeto que se copiará                           **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(miString d)
   {
      S=d.toString();
   }

/*** Devuelve una copia de este objeto                               **********/
/*** Parámetros: ninguno                                             **********/
   public objeto copia()
   {
      return new miString(S);
   }

/*** Devuelve el valor de cadena de este objeto                      **********/
/*** Parámetros: ninguno                                             **********/
   public String toString()
   {
      return new String(S);
   }

/*** Devuelve el valor booleano de este objeto cadena                **********/
/*** Parámetros: ninguno                                             **********/
   public boolean bolValue()
   {
      if (S.equalsIgnoreCase("FALSE")) {
         return true;
      } else { // cualquier cadena distinta de false es true
         return false;
      }
   }

/*** Devuelve una representación entrecomillada de este objeto cadena**********/
/*** Parámetros: ninguno                                             **********/
   public String StringValue()
   {
      return (char) 34 + S + (char) 34;
   }

/*** Devuelve el valor entero (si lo hay) representado por esta cadena ********/
/*** Parámetros: ninguno                                             **********/
   public int intValue()
   {
         return Integer.parseInt(S);
   }

/*** Devuelve el valor doble  (si lo hay) representado por esta cadena ********/
/*** Parámetros: ninguno                                             **********/
   public double doubleValue()
   {
      return new Double(S).doubleValue();
   }

/*** Ccompara este objeto cadena con el valor de cadena de otro objeto ********/
/*** Parámetros: o-> objeto con el que se compara                    **********/
/*** Devuelve: si es menor según el orden lexicográfico              **********/
   public boolean menor(objeto o)
   {
      return S.compareTo(o.toString())<0;
   }

/*** Ccompara este objeto cadena con el valor de cadena de otro objeto ********/
/*** Parámetros: o-> objeto con el que se compara                    **********/
/*** Devuelve: si es igual                                           **********/
   public boolean igual(objeto o)
   {
      return S.equals(o.toString());
   }

/*** Elemento i-ésimo                                                **********/
/*** Parámetros: i-> posición del carácter al que se quiere acceder  **********/
/*** Devuelve: el carácter i-ésimo de la cadena asociada a este objeto*********/
   public char charAt(int i)
   {
      return S.charAt(i);
   }

/*** Devuelve una subcadena                                          **********/
/*** Parámetros: i-> índice de comienzo                              **********/
/***             j-> índice de fin                                   **********/
/*** Devuelve: la subcadena comprendida entre i y j de este objeto   **********/
   public String substring(int i,int j)
   {
      return S.substring(i,j);
   }


}
