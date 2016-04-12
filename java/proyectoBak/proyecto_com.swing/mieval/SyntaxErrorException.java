/******************************************************************************/
/******************         SyntaxErrorException.java   ***********************/
/******************************************************************************/
package mieval;
import java.lang.Exception;
/******************************************************************************/
/******************************************************************************/
/*** Esta excepción es utilizada en  la clase evaluador para lanzar un  *******/
/*** error de sintaxis en la interpretación de la entrada. De esta forma*******/
/*** el control pasa al módulo principal del analizador sintáctico, el  *******/
/*** cual se encargará de informar del error producido.                 *******/
/******************************************************************************/
/******************************************************************************/
public class SyntaxErrorException extends Exception{
    public SyntaxErrorException()
    {
        super();
    }
    public SyntaxErrorException(String s)
    {
        super(s);
    }
}
