/******************************************************************************/
/******************         variable.java       *******************************/
/******************************************************************************/
package mieval.tipo;
import java.lang.Object;
import mieval.tipo.objeto;
import mieval.tipo.miDouble;
import mieval.tipo.miString;
import mieval.tipo.miBooleano;
import mieval.tipo.miArray;
import mieval.SyntaxErrorException;
public class variable extends Object { //extends object no hace falta ponerlo
    public String nombre;
    public char tipo;
    public objeto valor;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
    public variable()
    {
    }
    public variable(String nombre, char tipo, objeto valor)
    {
        this();
        this.nombre=nombre;
        this.tipo=tipo;
        this.valor=valor;
    }
    public variable(String nombre, char tipo, String s)
    {
        this(nombre, tipo, new miString(s));
    }
    char tipo(objeto ob)
    {
       if (ob instanceof miDouble) {
          return 'D';
       } else if (ob instanceof miString) {
          return 'S';
       } else if (ob instanceof miArray) {
          return 'M';
       } else if (ob instanceof miBooleano) {
          return 'B';
       }
       return ' ';
    }

    public variable(objeto o)
    {
        this();
        this.nombre=null;
        this.tipo=tipo(o);
        this.valor=o.copia();
    }
    public variable(String nombre, objeto o)
    {
       this(o);
       this.nombre=nombre;
    }
    public variable(miDouble d)
    {
       this((objeto) d);
    }
    public variable(miString s)
    {
       this((objeto) s);
    }
    public variable(miBooleano b)
    {
       this((objeto) b);
    }
    public variable(miArray a)
    {
       this((objeto) a);
    }


    public variable(String nombre, char tipo)
    {
        this();
        this.nombre=nombre;
        this.tipo=tipo;
    }
    public variable(String nombre)
    {
        this();
        this.nombre=nombre;
        this.tipo=' '; //tipo indeterminado
    }
    public variable(variable v)
    {
        this(null,v.tipo,v.valor); // no puede haber 2 variables con igual nombre
    }
    public variable(double d)
    {
       this();
       this.nombre=null;
       this.tipo='D';
       this.valor=new miDouble(d);
    }
    public variable(String nombre, double d)
    {
       this(d);
       this.nombre=nombre;
    }
    public variable(boolean b)
    {
       this();
       this.nombre=null;
       this.tipo='B';
       this.valor=new miBooleano(b);
    }
    public variable(char c)
    {
       this();
       this.nombre=null;
       this.tipo='S';
       this.valor=new miString(c);
    }

    public variable(array a)
    {
       this();
       this.nombre=null;
       this.tipo='M';
       this.valor=new miArray(a);
    }

    public variable(char tipo, String s)
    {
       this.nombre=null;
       this.tipo=tipo;
       this.valor=new miString(s);
    }

/*** Devuelve el tipo de esta variable                               **********/
/*** Parámetros: ninguno                                             **********/
    public char type()
    {
       return this.tipo;
    }

/*** Covierte a cadena esta variable                                 **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: la cadena representación de esta variable             **********/
    public String toString()
    {
       if (valor!=null) {
          return valor.toString();
       } else {
          return "";
       }
    }

/*** Valor de cadena de esta variable                                **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor de cadena de esta variable                   **********/
    public String StringValue()
    {
       if (valor!=null) {
          return valor.StringValue();
       } else {
          return "";
       }
    }

/*** Devuelve la longitud de esta variable .Depende del tipo         **********/
/*** Parámetros: ninguno                                             **********/
    public int len()
    {
       if (valor!=null) {
          return valor.len();
       } else {
          return 0;
       }
    }

/*** Devuelve la conversión a doble del valor asociado a esta variable*********/
/*** Parámetros: ninguno                                             **********/
    public double doubleValue()
    {
        return valor.doubleValue();
    }

/*** Devuelve la conversión a entero del valor asociado a esta variable********/
/*** Parámetros: ninguno                                             **********/
    public int intValue()
    {
        return valor.intValue();
    }
/*** Devuelve la conversión a booleano del valor asociado a esta variable******/
/*** Parámetros: ninguno                                             **********/
    public boolean bolValue()
    {
        return valor.bolValue();
    }

/*** Determina si esta variable es menor que otra                    **********/
/*** Parámetros: v-> variable con la que compararla                  **********/
/*** Devuelve: si es menor o no                                      **********/
    public boolean menor(variable v)
    {
       return this.valor.menor(v.valor);
    }

/*** Determina si esta variable es igual a otra                      **********/
/*** Parámetros: v-> variable con la que compararla                  **********/
/*** Devuelve: si se da la igualdad o no                             **********/
    public boolean igual(variable v)
    {
       if (this.tipo!=v.tipo) {
          return false;
       }
       return this.valor.igual(v.valor);
    }

/*** Determina si esta variable es distinta a otra                   **********/
/*** Parámetros: v-> variable con la que compararla                  **********/
/*** Devuelve: si se da la desigualdad o no                          **********/
    public boolean distinto(variable v)
    {
        return (!igual(v));
    }

/*** Determina si esta variable es mayor que otra                    **********/
/*** Parámetros: v-> variable con la que compararla                  **********/
/*** Devuelve: si esta variable es mayor que v o no                  **********/
    public boolean mayor(variable v)
    {
        return (!menor(v) && !igual(v));
    }

/*** Determina si esta variable es mayor o igual que otra            **********/
/*** Parámetros: v-> variable con la que compararla                  **********/
/*** Devuelve: si es mayor o igual que v                             **********/
    public boolean mayor_o_igual(variable v)
    {
        return (!menor(v));
    }
/*** Determina si esta variable es menor o igual que  otra           **********/
/*** Parámetros: v-> variable con la que compararla                  **********/
/*** Devuelve: si esmenor o igual que v                              **********/
    public boolean menor_o_igual(variable v)
    {
        return (menor(v) || igual(v));
    }

/*** Devuelve una copia de esta variable. No se copia el nombre de   **********/
/***     de la variable porque no debe haber 2 variables con el mismo *********/
/***     nombre                                                      **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: una copia sin nombre de esta variable                 **********/
    public variable copia()
    {
       if (valor!=null) {
          return new variable(null,this.tipo,valor.copia());
       } else {
          return new variable(null, this.tipo);
       }
    }

/*** Asigna un nuevo valor a esta variable                           **********/
/*** Parámetros: v-> variable que se asigna a esta variable          **********/
/*** Devuelve: nada                                                  **********/
    public void asigna(variable v)
    {
       this.valor=v.valor.copia();
       this.tipo=v.tipo;
    }

/*** Asigna un nuevo valor numérico a esta variable                  **********/
/*** Parámetros: d-> valor numérico que se asigna                    **********/
/*** Devuelve: nada                                                  **********/
    public void asigna(double d)
    {
       this.tipo='D';
       if (this.valor instanceof miDouble) {
          ((miDouble)this.valor).asigna(d);
       } else {
          this.valor=new miDouble(d);
       }
    }

/*** Devuelve el carácter i-ésimo de esta variable caso que sea tipo **********/
/***     cadena, sino lanza una excepción de error de sintaxis       **********/
/*** Parámetros: i-> posición del carácter en cuestión               **********/
/*** Devuelve: el carácter solicitado si está dentro de los límites  **********/
/***     y la variable es una cadena, en otro caso se lanza ecepc.   **********/
    public char charAt(int i) throws SyntaxErrorException
    {
       if (this.tipo=='S') {
          if (0<=i && i<this.len()) {
             return ((miString) this.valor).S.charAt(i);
          } else {
             throw new SyntaxErrorException("índices fuera de rango");
          }
       } else {
          throw new SyntaxErrorException("op [] no aplicable a este tipo de var.");
       }
    }

/*** Devuelve una subcadena de esta variable caso que sea tipo       **********/
/***     cadena, sino lanza una excepción de error de sintaxis       **********/
/*** Parámetros: i-> posición del primer carácter                    **********/
/***             j-> posición del último carácter                    **********/
/*** Devuelve: la subcadena comprendida entre las posiciones i y j   **********/
/***     de esta variable caso que sea tipo cadena, en otro caso se  **********/
/***     lanza una excepción de error de sintaxis                    **********/
    public String substring(int i, int j) throws SyntaxErrorException
    {
       if (this.tipo=='S') {
          if (0<=i && i<=j && j<len()) {
             return ((miString) this.valor).S.substring(i,j+1);
          } else {
             throw new SyntaxErrorException("índices fuera de rango");
          }
       } else {
          throw new SyntaxErrorException("índices fuera de rango");
       }
    }
/*** Incrementa el valor de esta variable caso que sea tipo numérico **********/
/***     sino lanza una excepción de error de sintaxis               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    public void inc() throws SyntaxErrorException
    {
       if (this.tipo=='D') {
          this.asigna(this.doubleValue()+1);
       } else {
          throw new SyntaxErrorException("Tipo incompatible.");
       }
    }

/*** Decrementa el valor de esta variable caso que sea tipo numérico **********/
/***     sino lanza una excepción de error de sintaxis               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    public void dec() throws SyntaxErrorException
    {
       if (this.tipo=='D') {
          this.asigna(this.doubleValue()-1);
       } else {
          throw new SyntaxErrorException("tipo incompatible.");
       }
    }

/*** Devuelve el número de dimensiones de  esta variable caso que sea**********/
/***    tipo matriz sino lanza una excepción de error de             **********/
/***    sintaxis                                                     **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el numero de dimensiones citado                       **********/
    public int getNumDimensiones() throws SyntaxErrorException
    {
       if (this.tipo=='M') {
          return ((miArray) valor).getNumDimensiones();
       }
       return -1;
    }

/*** Devuelve l i-ésima dimensiónde esta variable caso que sea tipo  **********/
/*** matriz, sino lanza una excepción de error de sintaxis           **********/
/*** Parámetros: i->número de orden de la dimensión a devolver       **********/
/*** Devuelve: el valor de la citada dimensión                       **********/
    public int getDimension(int i) throws SyntaxErrorException
    {
       if (this.tipo=='M') {
          return ((miArray) valor).getDimension(i);
       }
       return -1;
    }

}
