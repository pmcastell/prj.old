/******************************************************************************/
/******************         notificado.java     *******************************/
/******************************************************************************/
package mieval; 
/******************************************************************************/
/*** este interfaz es implementado por aquellos objetos que deben ser *********/
/*** notificados de alg�n evento. Por ej. cuando se crea una nueva variable ***/
/*** la clase evaluador tiene una lista de 'notificados' a los que notifica ***/
/*** que una nueva variable se ha creado y el valor que ha tomado �sta ********/
/*** mediante el uso del m�todo nuevaVariable cuyo prototipo se muestra *******/
/*** a continuaci�n                                                     *******/
/******************************************************************************/
public interface notificado {
   public void nuevaVariable(String nombre, String valor);
   public void nuevoEntorno();
   public void finNuevoEntorno();
}