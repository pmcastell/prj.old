/******************************************************************************/
/******************         SyntaxErrorException.java   ***********************/
/******************************************************************************/
package mieval; 
import java.lang.Exception;
/******************************************************************************/
/******************************************************************************/
/*** Esta excepci�n es utilizada en  la clase evaluador para lanzar un  *******/
/*** error de sintaxis en la interpretaci�n de la entrada. De esta forma*******/
/*** el control pasa al m�dulo principal del analizador sint�ctico, el  *******/
/*** cual se encargar� de informar del error producido.                 *******/
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
