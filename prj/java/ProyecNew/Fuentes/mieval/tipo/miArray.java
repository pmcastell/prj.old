/******************************************************************************/
/******************         miArray.java        *******************************/
/******************************************************************************/
package mieval.tipo;
import mieval.tipo.array;
import java.util.Vector;
import mieval.SyntaxErrorException;
public class miArray extends objeto {
   array A;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
   public miArray()
   {}

   public miArray(array a)
   {
      this.A=a;
   }
   public miArray(Vector v)
   {
      this(new array(v));
   }

/*** Devuelve una copia del array (componente básico de la clase)    **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: la copia del array                                     **********/
   public array getArrayCopia()
   {
      return new array(A);
   }

/*** Devuelve el elemento i-ésimo de la matriz                       **********/
/*** Parámetros: indice-> lista de índices (caso de matriz multidim.)**********/
/*** Deuelve: una referencia al elemento                             **********/
   public variable elemento(Vector indice) throws SyntaxErrorException
   {
      return A.elemento(indice);
   }

/*** Copia otra matriz en esta matriz                                **********/
/*** Parámetros: a-> matriz a copiar                                 **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(objeto a)
   {
      if (a instanceof miArray) {
         this.A= ((miArray) a).getArrayCopia();
      }
      this.A=null;
   }

/*** Idem anterior (sobrecargada)                                    **********/
/*** Parámetros: a-> matriz a copiar                                 **********/
/*** Deuelve: nada                                                   **********/
   public void asigna(miArray a)
   {
      this.A=a.getArrayCopia();
   }
  public void asigna(int i, variable valor) throws SyntaxErrorException
  {
     int []v=new int[1];
     v[0]=i;
     this.A.asigna(v,valor);
  }

   public void asigna(Vector v, variable valor) throws SyntaxErrorException
   {
      this.A.asigna(v,valor);
   }


/*** Realiza una copia de esta matriz                                **********/
/*** Parámetros: nada                                                **********/
/*** Deuelve: la copia de esta matrz                                 **********/
   public objeto copia()
   {
      return new miArray(new array(A));
   }

/*** Convierte a cadena esta matriz                                  **********/
/*** Parámetros: nada                                                **********/
/*** Deuelve: una cadena que representa a esta matriz                **********/
   public String toString()
   {
      return A.toString();
   }

/*** Compara es matriz con cualquier otro tipo de variable. La       **********/
/***     comparación se hace en orden a la longitd de cada objeto
/*** Parámetros: o-> objeto que representa a otra variable con la que**********/
/***     comparar                                                    **********/
/*** Deuelve: si esta matriz es 'menor' que la otra variable         **********/
   public boolean menor(objeto o)
   {
      return A.len()<o.len();
   }

/*** Compara esta matriz para ver si es igual a otra variable        **********/
/*** Parámetros: o->variable con la que comparar                     **********/
/*** Deuelve: si se da la igualdad o no                              **********/
   public boolean igual(objeto o)
   {
      return toString().equals(o.toString());
   }

/*** Devuelve la longitud (nº total de elementos) de esta matriz     **********/
/*** Parámetros: nada                                                **********/
   public int len()
   {
      return A.len();
   }

/*** Devuelve el número de dimensiones de esta matriz                **********/
/*** Parámetros: nada                                                **********/
   public int getNumDimensiones()
   {
      return A.getNumDimensiones();
   }

/*** Devuelve la longitud de una determinada dimensión               **********/
/*** Parámetros: i-> número de orden de la dimensión                 **********/
   public int getDimension(int i)
   {
      return A.getDimension(i);
   }
}
