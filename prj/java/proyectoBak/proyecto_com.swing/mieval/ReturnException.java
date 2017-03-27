/******************************************************************************/
/******************         ReturnException.java   ****************************/
/******************************************************************************/
package mieval;
import java.lang.Exception;
/******************************************************************************/
/******************************************************************************/
/*** Esta excepción es utilizada por la clase evaluador para implentar  *******/
/*** la instrucción return. Ante una instrucción RETURN se 'lanza' una  *******/
/*** excepción ReturnException que es propagada al módulo principal del *******/
/*** analizador sintáctico, el cual se encargará de devolver el valor   *******/
/*** que corresponda. Por ello esta clase no implementa ningún método   *******/
/*** nuevo, simplemente hereda de su superclase (Exception). Lo único   *******/
/*** importante es que el citado módulo principal pueda distinguirla de *******/
/*** la excepción generada por ej. por un error sintáctico. Y de esta   *******/
/*** forma llevar a cabo un tratamiento distingo.                       *******/
/******************************************************************************/
/******************************************************************************/
public class ReturnException extends Exception{

 public ReturnException() {
    super();
 }
} 