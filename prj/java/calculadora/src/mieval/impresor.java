/******************************************************************************/
/******************         impresor.java     *******************************/
/******************************************************************************/
package mieval; 
/******************************************************************************/
/*** este interfaz es implementado por aquellos objetos que sean capaces ******/
/*** de visualizar la salida de la instrucci�n print en la ejecuci�n de los ***/
/*** programas
/******************************************************************************/
public interface impresor {
   public void imprime(String s);
   public void imprimec(String color, String s);
   public void clear();
}
