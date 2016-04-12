/******************************************************************************/
/******************         miBooleano.java     *******************************/
/******************************************************************************/
package mieval.tipo;

import mieval.tipo.booleano;
public class miBooleano extends objeto{
   booleano B;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
   public miBooleano(objeto o)
   {
      B=new booleano(o.bolValue());
   }

   public miBooleano(boolean b)
   {
      B=new booleano(b);
   }

   public miBooleano(double d)  {
      B=new booleano(d!=0);
   }
   public miBooleano(int d) {
      B=new booleano(d!=0);
   }
   public miBooleano(long d) {
      B=new booleano(d!=0);
   }
   public miBooleano(float d) {
      B=new booleano(d!=0);
   }

   public miBooleano(String d)
   {
      if (d.equalsIgnoreCase("FALSE")) {
         B=new booleano(false);
      } else {
         B=new booleano(true);
      }
   }

/*** Copia el valor booleano de un objeto en este                    **********/
/*** Par�metros: o-> objeto del cual se copiar� su valor booleano    **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(objeto o)
   {
      B=new booleano(o.bolValue());
   }

/*** Copia un booleano en este                                       **********/
/*** Par�metros: d-> booleano a copiar                               **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(miBooleano d)
   {
      B=new booleano(d.bolValue());
   }

/*** Devuelve una copia de este booleano                             **********/
/*** Par�metros: nada                                                **********/
   public objeto copia()
   {
      return new miBooleano(this.bolValue());
   }

/*** Convierte a cadena este booleano                                **********/
/*** Par�metros: nada                                                **********/
/*** Deuelve: la cadena representando a este booleano                **********/
   public String toString()
   {
      return B.toString();
   }

/*** Convierte a boolean este booleano                               **********/
/*** Par�metros: nada                                                **********/
/*** Deuelve: el valor boolean de este booleano                      **********/
   public boolean bolValue()
   {
      return (B.bolValue());
   }

/*** Convierte a entero este booleano                                **********/
/*** Par�metros: nada                                                **********/
/*** Deuelve: 1 si el valor boolean de este es true, 0 caso contrario**********/
   public int intValue()
   {
      if (B.bolValue()) {
         return 1;
      } else {
         return 0;
      }
   }

/*** Convierte a doble  este booleano                                **********/
/*** Par�metros: nada                                                **********/
/*** Deuelve: 1.0 si el valor boolean de este es true, 0.0 caso      **********/
/***     contrario                                                   **********/
   public double doubleValue()
   {
      return (double) intValue();
   }

/*** Compara  con otra variable seg�n el valor booleano (false<true) **********/
/*** Par�metros: nada                                                **********/
/*** Deuelve: si este booleano es menor que otra variable            **********/
/***     contrario                                                   **********/
   public boolean menor(objeto o)
   {
      return B.doubleValue()<o.doubleValue();
   }

/*** Compara  con otra variable seg�n el valor booleano              **********/
/*** Par�metros: nada                                                **********/
/*** Deuelve: si este booleano es igual que otra variable            **********/
   public boolean igual(objeto o)
   {
      return B.bolValue()==o.bolValue();
   }

}
