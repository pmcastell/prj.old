/******************************************************************************/
/******************         notificado.java     *******************************/
/******************************************************************************/
package mieval;
/******************************************************************************/
/*** este interfaz es implementado por aquellos objetos que deben ser *********/
/*** notificados de algún evento. Por ej. cuando se crea una nueva variable ***/
/*** la clase evaluador tiene una lista de 'notificados' a los que notifica ***/
/*** que una nueva variable se ha creado y el valor que ha tomado ésta ********/
/*** mediante el uso del método nuevaVariable cuyo prototipo se muestra *******/
/*** a continuación                                                     *******/
/******************************************************************************/
public interface notificado {
   public void nuevaVariable(String nombre, String valor);
   public void nuevoEntorno();
   public void finNuevoEntorno();
}