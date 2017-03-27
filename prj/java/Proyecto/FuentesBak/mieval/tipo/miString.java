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
/*** Par�metros: o-> objeto del cual se copiar� su valor de cadena   **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(objeto o)
   {
      S=o.toString();
   }

/*** Copia el valor de cadena de un objeto cadena en este            **********/
/*** Par�metros: d-> objeto que se copiar�                           **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(miString d)
   {
      S=d.toString();
   }

/*** Devuelve una copia de este objeto                               **********/
/*** Par�metros: ninguno                                             **********/
   public objeto copia()
   {
      return new miString(S);
   }

/*** Devuelve el valor de cadena de este objeto                      **********/
/*** Par�metros: ninguno                                             **********/
   public String toString()
   {
      return new String(S);
   }

/*** Devuelve el valor booleano de este objeto cadena                **********/
/*** Par�metros: ninguno                                             **********/
   public boolean bolValue()
   {
      if (S.equalsIgnoreCase("FALSE")) {
         return true;
      } else { // cualquier cadena distinta de false es true
         return false;
      }
   }

/*** Devuelve una representaci�n entrecomillada de este objeto cadena**********/
/*** Par�metros: ninguno                                             **********/
   public String StringValue()
   {
      return (char) 34 + S + (char) 34;
   }

/*** Devuelve el valor entero (si lo hay) representado por esta cadena ********/
/*** Par�metros: ninguno                                             **********/
   public int intValue()
   {
         return Integer.parseInt(S);
   }

/*** Devuelve el valor doble  (si lo hay) representado por esta cadena ********/
/*** Par�metros: ninguno                                             **********/
   public double doubleValue()
   {
      return new Double(S).doubleValue();
   }

/*** Ccompara este objeto cadena con el valor de cadena de otro objeto ********/
/*** Par�metros: o-> objeto con el que se compara                    **********/
/*** Devuelve: si es menor seg�n el orden lexicogr�fico              **********/
   public boolean menor(objeto o)
   {
      return S.compareTo(o.toString())<0;
   }

/*** Ccompara este objeto cadena con el valor de cadena de otro objeto ********/
/*** Par�metros: o-> objeto con el que se compara                    **********/
/*** Devuelve: si es igual                                           **********/
   public boolean igual(objeto o)
   {
      return S.equals(o.toString());
   }

/*** Elemento i-�simo                                                **********/
/*** Par�metros: i-> posici�n del car�cter al que se quiere acceder  **********/
/*** Devuelve: el car�cter i-�simo de la cadena asociada a este objeto*********/
   public char charAt(int i)
   {
      return S.charAt(i);
   }

/*** Devuelve una subcadena                                          **********/
/*** Par�metros: i-> �ndice de comienzo                              **********/
/***             j-> �ndice de fin                                   **********/
/*** Devuelve: la subcadena comprendida entre i y j de este objeto   **********/
   public String substring(int i,int j)
   {
      return S.substring(i,j);
   }


}
