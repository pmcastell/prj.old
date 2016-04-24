/******************************************************************************/
/******************         objeto.java         *******************************/
/******************************************************************************/
package mieval.tipo; 
import mieval.SyntaxErrorException;

/******************************************************************************/
/*** esta clase abstracta define un comportamiento com�n que adoptar�n ********/
/*** todas las clases que de ella desciendan. �stas sobreescribir�n los *******/
/*** m�todos que cambien seg�n el tipo de variable que representen. Las *******/
/*** 4 clases que descienden de �sta son miArray para representar      ********/
/*** matrices, miString para cadenas, miDouble para n�meros (todos ellos*******/
/*** ser�n representados en doble precisi�n y miBooleano para booleanos *******/
/******************************************************************************/
public abstract class objeto {
   public abstract objeto copia();
   public abstract void asigna(objeto o);
   public abstract boolean menor(objeto o);
   public abstract boolean igual(objeto o);
   public abstract objeto suma(objeto o) throws SyntaxErrorException;
   public abstract objeto resta(objeto o) throws SyntaxErrorException;
   public abstract objeto producto(objeto o) throws SyntaxErrorException;
   public abstract objeto division(objeto o) throws SyntaxErrorException;
   public abstract objeto modulo(objeto o) throws SyntaxErrorException;
   public abstract objeto potencia(objeto o) throws SyntaxErrorException;
   public abstract void inc() throws SyntaxErrorException;
   public abstract void dec() throws SyntaxErrorException;

/*** Implementa distinto en funci�n de igual que implementar� cada   **********/
/***     descendiente                                                **********/
/*** Par�metros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si este objeto es distinto a o                         **********/
   public boolean distinto(objeto o)
   {
      return !igual(o);
   }

/*** Implementa mayor en funci�n de menor y de igual que implementar� cada*****/
/***     descendiente                                                **********/
/*** Par�metros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si este objeto es mayor que o                          **********/
   public boolean mayor(objeto o)
   {
      return !(menor(o) || igual(o));
   }

/*** Implementa mayor o igual en funci�n de menor que implementar� cada********/
/***     descendiente                                                **********/
/*** Par�metros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si este objeto es mayor o igual que o                  **********/
   public boolean mayor_o_igual(objeto o)
   {
      return !menor(o);
   }

/*** Implementa menor o igual en funci�n de menor y de igual que     **********/
/***     implementar� cada descendiente                              **********/
/*** Par�metros: o-> objeto con el que comparar                      **********/
/*** Deuelve: si este objeto es mayor o igual que o                  **********/
   public boolean menor_o_igual(objeto o)
   {
      return menor(o) || igual(o);
   }

/*** Devuelve el valor de cadena de este objeto                      **********/
/*** Par�metros: ninguno                                             **********/
   public String StringValue()
   {
      return this.toString();
   }

/*** Devuelve la longitud de este objeto convertido a cadena         **********/
/*** Par�metros: ninguno                                             **********/
   public int len()
   {
      return this.toString().length();
   }

/*** Devuelve el valor doble de este objeto cada descendiente sobreescribir�***/
/*** Par�metros: ninguno                                             **********/
   public double doubleValue() {
      return 0.0; // hay variables para las que no tiene sentido
   }

/*** Devuelve el valor boolean de este objeto cada descendiente sobreescribir�*/
/*** Par�metros: ninguno                                             **********/
   public boolean bolValue() {
      return true; // hay variables para las que no tiene sentido
   }

/*** Devuelve el valor entero de este objeto cada descendiente sobreescribir�**/
/*** Par�metros: ninguno                                             **********/
   public int intValue() {
      return 0; // hay variables para las que no tiene sentido
   }
 
}
