/******************************************************************************/
/******************         ReturnException.java   ****************************/
/******************************************************************************/
package mieval; 
import java.lang.Exception;
/******************************************************************************/
/******************************************************************************/
/*** Esta excepci�n es utilizada por la clase evaluador para implentar  *******/
/*** la instrucci�n return. Ante una instrucci�n RETURN se 'lanza' una  *******/
/*** excepci�n ReturnException que es propagada al m�dulo principal del *******/
/*** analizador sint�ctico, el cual se encargar� de devolver el valor   *******/
/*** que corresponda. Por ello esta clase no implementa ning�n m�todo   *******/
/*** nuevo, simplemente hereda de su superclase (Exception). Lo �nico   *******/
/*** importante es que el citado m�dulo principal pueda distinguirla de *******/
/*** la excepci�n generada por ej. por un error sint�ctico. Y de esta   *******/
/*** forma llevar a cabo un tratamiento distingo.                       *******/
/******************************************************************************/
/******************************************************************************/
public class ReturnException extends Exception{

 public ReturnException() {
    super();
 }
} 