/******************************************************************************/
/******************         arbol_binario.java      ***************************/
/******************************************************************************/
package mieval; 
import mieval.entrada;
import mieval.tipo.variable;
public class arbol_binario {
    public entrada lex;
    public arbol_binario hijo_izda;
    public arbol_binario hijo_dcha;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
    public arbol_binario()
    {
    }

    public arbol_binario(arbol_binario a)
    {
        lex=a.lex;
        hijo_izda=a.hijo_izda;
        hijo_dcha=a.hijo_dcha;
    }

    public arbol_binario(entrada e)
    {
        lex=new entrada(e);
    }
    public arbol_binario(int c)
    {
        lex=new entrada(c,new variable("",'P'));
    }
    public arbol_binario(int c, arbol_binario hi, arbol_binario hd)
    {
        this(c);
        this.hijo_izda=hi;
        this.hijo_dcha=hd;
    }

/*** Asigna un �rbol al actual los hijos quedan compartidos          **********/
/*** Par�metros: a-> �rbol a asignar                                 **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(arbol_binario a)
    {
        this.lex=new entrada(a.lex);
        this.hijo_izda=a.hijo_izda;
        this.hijo_dcha=a.hijo_dcha;
    }

/*** Realiza una copia completa del �rbol pasado como par�metro      **********/
/*** Par�metros: a-> �rbol a copiar                                  **********/
/*** Deuelve: un nuevo �rbol totalmente igual que el original        **********/
    public static arbol_binario copia(arbol_binario a)
    {
        arbol_binario r=new arbol_binario();

        if (a!=null) {
            r.lex=new entrada(a.lex);
            if (a.hijo_izda!=null) {
                r.hijo_izda=copia(a.hijo_izda);
            }
            if (a.hijo_dcha!=null) {
                r.hijo_dcha=copia(a.hijo_dcha);
            }
        }
        return r;
    }

/*** Realiza una copia de �ste �rbol                                 **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: una copia totalmente id�ntica del �rbol actual         **********/
    public arbol_binario copia()
    {
        return copia(this);
    }

/*** Nos dice si �ste �rbol es totalmente igual a otro               **********/
/*** Par�metros: b-> �rbol con el que se compara                     **********/
/*** Deuelve: si es o no igual a b                                   **********/
    public boolean igual(arbol_binario b)
    {
        return igual(this,b);
    }

/*** Nos dice si dos �rboles son totalmente id�nticos                **********/
/*** Par�metros: a,b-> �rboles a comparar                            **********/
/*** Deuelve: si son o no iguales                                    **********/
    public static boolean igual(arbol_binario a, arbol_binario b)
    {

        boolean r;
        if (a==b) {
            return true;
        } else if (a==null || b==null) {
            return false;
        } else if ((a.lex.comp_lex==b.lex.comp_lex) &&
                    (a.lex.objeto.nombre==null && b.lex.objeto.nombre==null ||
                       (a.lex.objeto!=null && b.lex.objeto!=null &&
                        comparaCadenas(a.lex.objeto.nombre, b.lex.objeto.nombre)))
                   && ((a.lex.objeto.valor==null && b.lex.objeto.valor== null) ||
                       (a.lex.objeto.valor!=null && b.lex.objeto.valor!=null &&
                         a.lex.objeto.igual(b.lex.objeto)))) {
            r=(igual(a.hijo_izda,b.hijo_izda) && igual(a.hijo_dcha,b.hijo_dcha));
            if (a.lex.comp_lex=='+'|| a.lex.comp_lex=='*') {
                return r || (igual(a.hijo_izda,b.hijo_dcha) && igual(a.hijo_dcha,b.hijo_izda));
            }
            return r;
        } else {
            return false;
        }
    }

/*** Asigna a �ste �rbol su hijo izquierda                           **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: nada                                                   **********/
    public void sustituyePorHijoIzda()
    {
       if (hijo_izda!=null) {
          this.lex=this.hijo_izda.lex;
          this.hijo_dcha=this.hijo_izda.hijo_dcha;
          this.hijo_izda=this.hijo_izda.hijo_izda;
       }
    }

/*** Asigna a �ste �rbol su hijo derecha                             **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: nada                                                   **********/
    public void sustituyePorHijoDcha()
    {
       if (hijo_dcha!=null) {
          this.lex=this.hijo_dcha.lex;
          this.hijo_izda=this.hijo_dcha.hijo_izda;
          this.hijo_dcha=this.hijo_dcha.hijo_dcha;
       }
    }
/*** Asigna un �rbol al actual los hijos quedan compartidos          **********/
/*** Par�metros: a-> �rbol a asignar                                 **********/
/*** Deuelve: nada                                                   **********/
    public static boolean comparaCadenas(String s1, String s2)
    {
       if (s1==null || s2==null) {
          return s1==s2;
       } else {
          return s1.equalsIgnoreCase(s2);
       }
    }


}