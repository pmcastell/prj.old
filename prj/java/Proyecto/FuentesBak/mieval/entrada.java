/******************************************************************************/
/******************         entrada.java        *******************************/
/******************************************************************************/
package mieval;
import mieval.tipo.*;
/******************************************************************************/
/********************** entradas de la tabla de s�mbolos **********************/
/******************************************************************************/
public class entrada {
   public int comp_lex;  
   public variable objeto;
  
   
   public entrada() {
   } //constructor vac�o siempre es bueno tener uno, por si acaso

   public entrada(int comp_lex, variable objeto)
   {
      this.comp_lex=comp_lex;
      this.objeto=objeto;
   }
   public entrada(entrada e)
   {
      this(e.comp_lex,e.objeto);
   }   
}
