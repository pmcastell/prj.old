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
   public miArray(double[]ad) {
      this(new array(ad));
   }
   public miArray(double[][]ad){
      this(new array(ad));
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
   public variable elemento(int i) throws SyntaxErrorException 
   {
       return A.elemento(i);
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
   public static miArray suma(miArray a, miArray b) throws SyntaxErrorException {
       int i;
       miArray res=(miArray)a.copia();
       if (a.getDimension(0)!=b.getDimension(0)) {
           throw new SyntaxErrorException("Error en suma: matrices de distinta dimensión.");
       }
       for(i=0; i<a.getDimension(0); i++) {
           if (a.elemento(i).tipo=='M' && b.elemento(i).tipo=='M') {
               res.asigna(i, new variable( suma((miArray)a.elemento(i).valor,(miArray) b.elemento(i).valor)));
           } else {
               res.asigna(i,new variable(a.A.elemento(i).valor.suma(b.A.elemento(i).valor)));
           }
       }
       return res;
   }
   
   public miArray suma(miArray o) throws SyntaxErrorException {
       return suma(this,o);
   }
   
   public objeto suma(objeto o) throws SyntaxErrorException {
       if (o instanceof miArray) {
           return suma(this,(miArray)o);
       } else {
           return new miString(this.toString()+o.toString());
       }
   }
   
   public static miArray resta(miArray a, miArray b) throws SyntaxErrorException {
       int i;
       miArray res=(miArray)a.copia();
       if (a.getDimension(0)!=b.getDimension(0)) {
           throw new SyntaxErrorException("Error en suma: matrices de distinta dimensión.");
       }
       for(i=0; i<a.getDimension(0); i++) {
           if (a.elemento(i).tipo=='M' && b.elemento(i).tipo=='M') {
               res.asigna(i, new variable( resta((miArray)a.elemento(i).valor,(miArray) b.elemento(i).valor)));
           } else { 
               res.asigna(i,new variable(a.A.elemento(i).valor.resta(b.A.elemento(i).valor)));
           } 
       }
       return res;
   }
   
   public miArray resta(miArray o) throws SyntaxErrorException {
       return resta(this,o);
   }
   public objeto resta(objeto o) throws SyntaxErrorException {
       if (o instanceof miArray) {
           return resta(this,(miArray) o);
       } else {
           return miString.resta(this,o);
       }
   }
   public static miArray producto(double d, miArray a) throws SyntaxErrorException {
       int i;
       
       miArray res=(miArray) a.copia();
       for(i=0; i<a.getDimension(0); i++) {
           if (a.elemento(i).tipo=='D') {
               res.asigna(i,new variable(new miDouble(a.elemento(i).doubleValue()*d).redondeo(14)   ));
           } else if (a.elemento(i).tipo=='M') {
               res.asigna(i,new variable(producto(d,(miArray)a.elemento(i).valor)));
           } else {
               throw new SyntaxErrorException("Error el producto: tipos no compatibles.");
           }
       }
       return res;
   }
   public static miArray producto(miArray a, double d) throws SyntaxErrorException {
       return producto(d,a);
   }
   public miArray producto(double d) throws SyntaxErrorException {
       return producto(this,d);
   }
   public static miArray producto(miDouble d, miArray a) throws SyntaxErrorException {
       return producto(d.doubleValue(),a);
   }
   public static miArray producto(miArray a,miDouble d) throws SyntaxErrorException {
       return producto(d.doubleValue(),a);
   }
   public static boolean esFactibleProducto(miArray a, miArray b) throws SyntaxErrorException {
       if (a.elemento(0).tipo=='M' && b.elemento(0).tipo=='M') {
           if (((miArray)a.elemento(0).valor).getDimension(0)==b.getDimension(0)) {
               return true;
           }
       }
       return false;
   }
   public static miArray arrayBidi(int m, int n) throws SyntaxErrorException {
       int d[]=new int[1];
       variable v[]=null;
       
       d[0]=m;
       miArray res=new miArray(new array(d,v));
       for(int i=0; i<m; i++) {
           d=new int[1];
           d[0]=n;
           res.asigna(i,new variable("",'M', new miArray(new array(d,v))));
       }
       return res; 
   }
   public boolean esBidimensional() throws SyntaxErrorException {
       return this.elemento(0).tipo=='M' && ((miArray)this.elemento(0).valor).elemento(0).tipo!='M';
   }
   public boolean esCuadrada() throws SyntaxErrorException {
       if (esBidimensional()) {
           return this.A.getDimension(0)==this.elemento(0).getDimension(0);
       }
       return false;
   }
   public miArray quitarColumna(int colQuitar) throws SyntaxErrorException {
       if (esBidimensional()) {
          int numFilas=this.getDimension(0), numCols=((miArray)this.elemento(0).valor).getDimension(0);
          if (colQuitar<0 || colQuitar>=numCols) {
              throw new SyntaxErrorException("Columna supera límite en la matriz.");
          }
          miArray m=arrayBidi(numFilas,numCols-1);
          miArray actualMenor;
          double actualThis;
          for(int fila=0; fila<numFilas; fila++) {
              for(int colMenor=0, colThis=0; colMenor<numCols-1 && colThis<numCols; colMenor++, colThis++) {
                  if (colThis==colQuitar) {
                      colThis++;
                  }
                  actualMenor=(miArray)m.elemento(fila).valor;
                  actualThis=((miArray)this.elemento(fila).valor).elemento(colThis).doubleValue();
                  actualMenor.asigna(colMenor,new variable(actualThis));
              }
              
          }
          return m;
       } else {
           throw new SyntaxErrorException("No se puede quitar columna de la matriz. No es bidimensional.");
       }
   }
   public miArray quitarFila(int filaQuitar) throws SyntaxErrorException {
       if (esBidimensional()) {
           int numFilas=this.getDimension(0), numCols=((miArray)this.elemento(0).valor).getDimension(0);
           if (filaQuitar<0 || filaQuitar>=numFilas) {
               throw new SyntaxErrorException("Error fila supera límite en la matriz.");
           }
           miArray m=arrayBidi(numFilas-1,numCols);
           miArray actualMenor; 
           double actualThis;
          
           for(int filaMenor=0,filaThis=0; filaThis<numFilas; filaMenor++,filaThis++) {
               if (filaThis==filaQuitar) {
                   filaThis++;
               }
               for(int col=0; filaThis<numFilas && col<numCols; col++) {
                   actualMenor=(miArray)m.elemento(filaMenor).valor;
                   //.elemento(col);
                   actualThis=((miArray)this.elemento(filaThis).valor).elemento(col).doubleValue();
                   //actualMenor.asigna(actualThis);
                   actualMenor.asigna(col,new variable(actualThis));
               }
           }
           return m;
        }
        throw new SyntaxErrorException("No se puede quitar fila de la matriz. No es bidimensional.");
   }
   public miArray menor(int i,int j) throws SyntaxErrorException {
       if (esBidimensional()) {
          int numFilas=this.getDimension(0), numCols=((miArray)this.elemento(0).valor).getDimension(0);
          if (i<0 || i>=numFilas) {
               throw new SyntaxErrorException("Error fila supera límite en la matriz.");
          }
          if (j<0 || j>=numCols) {
               throw new SyntaxErrorException("Error columna supera límite en la matriz.");
          }
          miArray m=arrayBidi(numFilas-1,numCols-1);
          variable actualMenor; 
          double actualThis;
          
          for(int filaMenor=0,filaActual=0; filaMenor<numFilas-1 && filaActual<numFilas; filaMenor++,filaActual++) {
              if (filaActual==i) {
                  filaActual++;
              }
              for(int colMenor=0,colActual=0; colMenor<numCols-1 && colActual<numCols; colMenor++,colActual++) {
                  if (colActual==j) {
                      colActual++;
                  }
                  actualMenor=m.elemento(filaMenor);//,colMenor);
                  //((miArray)m.elemento(fila).valor).elemento(col);
                  actualThis=this.elemento(filaActual,colActual).doubleValue();
                          //((miArray)this.elemento(fila).valor).elemento(col).doubleValue();
                  ((miArray)actualMenor.valor).asigna(colMenor,new variable(actualThis));
              }
          }
          return m;
       } else {
           throw new SyntaxErrorException("Menor: la matriz no es bidimensional");
       }

   }
   public static miArray adjunta(miArray m) throws SyntaxErrorException {
      if (m.esBidimensional()) {
         int numFilas=m.getDimension(0), numCols=((miArray)m.elemento(0).valor).getDimension(0);
         miArray res=arrayBidi(numFilas,numCols);
         double det;
         for(int i=0; i<numFilas; i++) {
            for(int j=0; j<numCols; j++) {
               miArray aux=m.menor(i,j);
               det=m.menor(i,j).determinante();
               if ((i + j) % 2 !=0) {
                  det=-det;
               }
               ((miArray)res.elemento(i).valor).asigna(j,new variable(det));
            }
         }
         return res;
      } else {
         throw new SyntaxErrorException("Error la matriz no es bidimensional.");
      }
   }
   public miArray adjunta() throws SyntaxErrorException {
      return adjunta(this);
   }
   public static miArray inversa(miArray m) throws SyntaxErrorException {
      if (m.esCuadrada()) {
         double det=m.determinante();
         if (det==0) {
            throw new SyntaxErrorException("La matriz no tiene inversa. Su determinantes es 0.");
         }
         int dimension=m.getDimension(0);
         double mji, mij;
         //miArray res=arrayBidi(dimension,dimension),res2=m.adjunta();
         miArray res=m.adjunta();
         for(int i=0; i<dimension; i++) {
            for(int j=0; j<=i; j++) {
               mji=res.elemento(j,i).doubleValue();
               mij=res.elemento(i,j).doubleValue();
               ((miArray)res.elemento(i).valor).asigna(j, new variable(mji/det));
               if (i!=j) {
                  ((miArray)res.elemento(j).valor).asigna(i, new variable(mij/det));
               }
            }
         }
         return res;
      } else {
         throw new SyntaxErrorException("Inversa: la matriz no es cuadrada.");
      }
   }
   public miArray inversa() throws SyntaxErrorException {
      return inversa(this);
   }
   private variable elemento(int i, int j) throws SyntaxErrorException {
       return ((miArray)this.elemento(i).valor).elemento(j);
   }
   public static double determinante(miArray m) throws SyntaxErrorException {
       if (m.esCuadrada()) {
           int dimension=m.getDimension(0);
           if (dimension==1) {
               return m.elemento(0,0).doubleValue();
           } else if (dimension==2) {
               return m.elemento(0,0).doubleValue()*m.elemento(1,1).doubleValue()-
                      m.elemento(0,1).doubleValue()*m.elemento(1,0).doubleValue();
           } else {
               double resultado=0;
               int pivote=1;
               for(int i=0; i<dimension; i++) {
                   if (pivote==1) {
                      resultado+=m.elemento(0,i).doubleValue()*determinante(m.menor(0,i));
                   } else { //pivote==-1
                      resultado-=m.elemento(0,i).doubleValue()*determinante(m.menor(0,i));
                   }
                   pivote=-pivote;
               }
               return resultado;
           }
       } else {
           throw new SyntaxErrorException("Determinante: la matriz no es cuadrada.");
       }
   }
   public double determinante() throws SyntaxErrorException {
       return determinante(this);
   }
   
   public static objeto producto(miArray a, miArray b) throws SyntaxErrorException {
       if (a.A.getNumDimensiones()==1 && b.A.getNumDimensiones()==1 && 
               a.A.elemento(0).tipo=='D') {
           if (a.A.getDimension(0)==b.A.getDimension(0)) {
               double res=0;
               variable v1, v2;
               for(int i=0; i<a.A.getDimension(0); i++) {
                   v1=a.elemento(i); v2=b.elemento(i);
                   if (v1.tipo=='D'  && v2.tipo=='D') {
                      res+=v1.doubleValue()*v2.doubleValue();
                   } else {
                       throw new SyntaxErrorException("Error no se pueden multiplicar las matrices.");
                   }
               }
               return new miDouble(res);
           } else {
               throw new SyntaxErrorException("Error las matrices no tienen el mismo número de elementos.");
           }
       }
       if (esFactibleProducto(a,b)) {
           int m=a.getDimension(0), n=b.getDimension(0), p=((miArray) b.elemento(0).valor).getDimension(0);
           double cij;
           variable aik,bkj;
           
           miArray c=arrayBidi(m,p);
           for(int i=0; i<m; i++) {
               for(int j=0; j<p; j++) {
                   cij=0.0;
                   for(int k=0; k<n; k++) {
                       aik=((miArray)a.elemento(i).valor).elemento(k);
                       bkj=((miArray)b.elemento(k).valor).elemento(j);
                       if (aik.tipo!='D' || bkj.tipo!='D') {
                           throw new SyntaxErrorException("Error producto matriz: tipos básicos inconsistentes.");
                       }
                       cij+=aik.doubleValue()*bkj.doubleValue();
                   }
                   ((miArray)c.elemento(i).valor).asigna(j,new variable(new miDouble(cij)));
                           //elemento(j).asigna(cij);
               }
           }
           return c;
       }
       throw new SyntaxErrorException("Error no se pueden multiplicar las matrices.");
   }
   public objeto producto(objeto o) throws SyntaxErrorException {
       if (o instanceof miDouble) {
           return producto(this,o.doubleValue());
       } else if (o instanceof miArray) {
           return miArray.producto(this,(miArray)o);
       } else {
           throw new SyntaxErrorException("Error en producto: tipos no coinciden.");
       }
   }
   public objeto division(objeto o)  throws SyntaxErrorException {
      if (o instanceof miDouble) {
          return producto(this,1/o.doubleValue());
      } else {
          throw new SyntaxErrorException("Error en división: tipos no coinciden.");
      }
   }
   public objeto modulo(objeto o) throws SyntaxErrorException {
       if (o instanceof miDouble) {
           return new miDouble(this.doubleValue()%o.doubleValue());
       } else {
           throw new SyntaxErrorException("Error en módulo: tipos no coinciden.");
       }
   }
   public objeto potencia(objeto o) throws SyntaxErrorException {
       if (o instanceof miDouble && miDouble.esEntero(o.doubleValue())) {
           miArray res=(miArray)this.copia();
           for(int i=2; i<=o.doubleValue(); i++) {
               res=(miArray)res.producto(this);
           }
           return res;
       } else {
           throw new SyntaxErrorException("Error no tiene sentido potencia de matriz con exponente decimal.");
       }
   }
   public void inc() throws SyntaxErrorException {
       throw new SyntaxErrorException("Error no tienen sentido incrementar una matriz.");
   }
   public void dec() throws SyntaxErrorException {
       throw new SyntaxErrorException("Error no tienen sentido decrementar una matriz.");
   }

}
