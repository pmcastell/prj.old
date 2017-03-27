/******************************************************************************/
/******************         evaluador.java      *******************************/
/******************************************************************************/
package mieval;
import java.lang.Double;
import java.lang.String;
import java.lang.Character;
import java.util.Vector;
import mieval.ctes_eval;
import mieval.entrada;
import mieval.tab_simb;
import mieval.arbol_binario;
import mieval.tipo.*;
import mieval.SyntaxErrorException;
import mieval.ReturnException;
import java.lang.ArithmeticException;
import mieval.notificado;
import java.io.*;
import mieval.listaFicheros;
import mieval.leerVar;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;


public class evaluador {
    private static tab_simb palabrasReservadas; // contiene las palabras Reservadas
    private tab_simb tabla_simbolos; // variables del programa
    private static listaFicheros ficheros=new listaFicheros(); // ficheros abiertos
                                                      //para su ejecuc��n
    private variable acumulador; // guarda el �ltimo valor calculado

    private String programa_a_ejecutar; // contiene la instrucci�n(es) a ejecut.
    private int chr_actual=0;  // car�cter que se est� procesando actualmente
    private int numLineas=1; // l�nea en ejecuci�n
    private int comienzoLineaAnterior=0; // l�mites �ltima l�nea interpretada
    private int comienzoLineaActual=0;
    private int ultimoCaracter=0; // �ltimo car�cter le�do de la entrada
    private int ultimoToken=0; // �posici�n �ltimo car�cter del �ltimo token

    private entrada lexema_actual; // �ltimo token reconocido y sus atributos
    private entrada ultimo_lexema; // pen�ltimo token reconocido y sus atrib.

    private String expresion_a_derivar; // utilizados para calcular la dervida
    private String expresion;
    private String derivada;
    private String dx; // variable con respecto a la que se deriva

    private variable valorReturn; // para guardar el valor de la inst.RETURN


    //los siguientes se utilizan para salvar el entorno de ejecuci�n
    private Vector programa_a_ejecutar_aux= new Vector();
    private Vector lexema_actual_aux=new Vector();
    private Vector chr_actual_aux=new Vector();
    private Vector numLineas_aux=new Vector();
    private Vector comienzoLineaAnterior_aux=new Vector();
    private Vector comienzoLineaActual_aux=new Vector();

    private Vector notificados=new Vector(); // lista de objetos a quien se
                             // notifica que se hacreado una nueva variable
    private boolean notificacion=false; // indica si se notifica a lista de
                             // notificados o no


    private Vector listaParamReal=new Vector(); // para las llamadas a funciones
    private Vector listaParamSalida=new Vector();//para variables por referencia

    private Vector postIncrementos=new Vector();// listas de variables a
    private Vector postDecrementos=new Vector();// post{inc-dec]rementar

/*******************************************************************************
*** Constructor de la clase: inicializa las palabras reservadas ****************
*** y la tabla de s�mbolos                                      ****************
*******************************************************************************/

    public evaluador()
    {
       tabla_simbolos=new tab_simb(); // tabla_simbolos inicialmente vac�a
       if (palabrasReservadas==null) {
          palabrasReservadas=new tab_simb();
          palabrasReservadas.inserta(ctes_eval.DIV,new variable("div"));
          palabrasReservadas.inserta(ctes_eval.MOD,new variable("mod"));
          palabrasReservadas.inserta(ctes_eval.ACOS,new variable("acos"));
          palabrasReservadas.inserta(ctes_eval.ASEN,new variable("asen"));
          palabrasReservadas.inserta(ctes_eval.ATAN,new variable("atan"));
          palabrasReservadas.inserta(ctes_eval.ATAN2,new variable("atan2"));
          palabrasReservadas.inserta(ctes_eval.CEIL,new variable("ceil"));
          palabrasReservadas.inserta(ctes_eval.COS,new variable("cos"));
          palabrasReservadas.inserta(ctes_eval.COSH,new variable("cosh"));
          palabrasReservadas.inserta(ctes_eval.EXP,new variable("exp"));
          palabrasReservadas.inserta(ctes_eval.ABS,new variable("abs"));
          palabrasReservadas.inserta(ctes_eval.FLOOR,new variable("floor"));
          palabrasReservadas.inserta(ctes_eval.LN,new variable("ln"));
          palabrasReservadas.inserta(ctes_eval.LOG,new variable("log"));
          palabrasReservadas.inserta(ctes_eval.SEN,new variable("sen"));
          palabrasReservadas.inserta(ctes_eval.SENH,new variable("senh"));
          palabrasReservadas.inserta(ctes_eval.SQRT,new variable("sqrt"));
          palabrasReservadas.inserta(ctes_eval.TAN,new variable("tan"));
          palabrasReservadas.inserta(ctes_eval.TANH,new variable("tanh"));
          palabrasReservadas.inserta(ctes_eval.PI,new variable("pi",'D',
                                     new miDouble(Math.PI))); // n�mero pi
          palabrasReservadas.inserta(ctes_eval.E,new variable("e",'D',
                                     new miDouble(Math.E)));//n�mero 'e'
          palabrasReservadas.inserta(ctes_eval.DIM,new variable("dim"));
          palabrasReservadas.inserta(ctes_eval.REDIM, new variable("redim"));
          palabrasReservadas.inserta(ctes_eval.TRUE, new variable("true"));
          palabrasReservadas.inserta(ctes_eval.FALSE, new variable("false"));
          palabrasReservadas.inserta(ctes_eval.IIF, new variable("iif"));
          palabrasReservadas.inserta(ctes_eval.FOR,new variable("for"));
          palabrasReservadas.inserta(ctes_eval.IF, new variable("if"));
          palabrasReservadas.inserta(ctes_eval.ELSE, new variable("else"));
          palabrasReservadas.inserta(ctes_eval.ELSEIF, new variable("elseif"));
          palabrasReservadas.inserta(ctes_eval.WHILE, new variable("while"));
          palabrasReservadas.inserta(ctes_eval.DERIVADA, new variable("derivada"));
          palabrasReservadas.inserta(ctes_eval.DERIV, new variable("deriv"));
          palabrasReservadas.inserta(ctes_eval.INTEGRAL, new variable("integral"));
          palabrasReservadas.inserta(ctes_eval.SIMPLIFICA, new variable("simp"));
          palabrasReservadas.inserta(ctes_eval.EVALUA, new variable("eval"));
          acumulador=(palabrasReservadas.inserta(ctes_eval.ACUMULADOR,
          new variable("?",'D',new miDouble(0.0)))).objeto;//�ltimo valor calculado
          palabrasReservadas.inserta(ctes_eval.PTR, new variable("ptr"));
          palabrasReservadas.inserta(ctes_eval.EXEC, new variable("exec"));
          palabrasReservadas.inserta(ctes_eval.PARAMETERS,
                                     new variable("parameters"));
          palabrasReservadas.inserta(ctes_eval.SUBSTR, new variable("substr"));
          palabrasReservadas.inserta(ctes_eval.TOUPPER, new variable("toupper"));
          palabrasReservadas.inserta(ctes_eval.TOLOWER, new variable("tolower"));
          palabrasReservadas.inserta(ctes_eval.TYPE, new variable("type"));
          palabrasReservadas.inserta(ctes_eval.LEN, new variable("len"));
          palabrasReservadas.inserta(ctes_eval.INT, new variable("int"));
          palabrasReservadas.inserta(ctes_eval.AND, new variable("and"));
          palabrasReservadas.inserta(ctes_eval.OR, new variable("or"));
          palabrasReservadas.inserta(ctes_eval.NOT, new variable("not"));
          palabrasReservadas.inserta(ctes_eval.DIMENS, new variable("dimens"));
          palabrasReservadas.inserta(ctes_eval.NDIM, new variable("ndim"));
          palabrasReservadas.inserta(ctes_eval.RETURN, new variable("return"));
          palabrasReservadas.inserta(ctes_eval.LEER, new variable("leer"));
       }
    }

/******************************************************************************/
/******************************************************************************/
/******************************** Analizador L�xico: **************************/
/******************************************************************************/
/******************************************************************************/

/*** Analizador l�xico. Devuelve los tokens reconocidos en la entrada**********/
/*** Par�metros: ninguno                                              *********/
/*** Devuelve: nada                                                  **********/
    void analex() throws SyntaxErrorException
    {
       int t, tAux;
       String cadena_aux=new String();

       ultimoToken=chr_actual; // guardamos el final del �ltimo token reconocido
       while (true) {
          t=siguienteCaracter(); // avanzamos un car�cter
          if (t==ctes_eval.EOF) {
             lexema_actual.comp_lex=ctes_eval.EOF;
             break;
          } else if (t=='?') {
             lexema_actual.comp_lex=ctes_eval.ACUMULADOR;
             break;
          } else if(t=='$') {
             tAux=siguienteCaracter();
             if (tAux=='#') {
                tAux=siguienteCaracter();
                if (Character.isDigit((char) tAux)) {
                    lexema_actual=new entrada(ctes_eval.CTE,
                         new variable("",'D',new miDouble(numero(tAux))));
                    break;
                }
                devuelveCaracter();
             }
             devuelveCaracter();
             lexema_actual=new entrada(t,new variable(""));
             break;
          } else if (t== ' ' || t=='\t') {
                 // eliminamos espacios y tabuladores de la entrada
          } else if (t=='/') {
             tAux=siguienteCaracter();
             if (tAux=='/') {
                while(true) {
                   t=siguienteCaracter();
                   if (t==ctes_eval.EOF) {
                      lexema_actual.comp_lex=t;
                      return;
                   }
                   if (t=='\n') {
                      devuelveCaracter();
                      break;
                   }
                }
             } else {
                devuelveCaracter();
                lexema_actual=new entrada(t,new variable(""));
                break;
             }
          } else if (t=='\n') {
             numLineas++;
             comienzoLineaAnterior=comienzoLineaActual;
             comienzoLineaActual=chr_actual;
          } else if (Character.isDigit((char) t)) { // numero
             lexema_actual=new entrada(ctes_eval.NUM,
                                       new variable(new miDouble(numero(t))));
             break;
          } else if (Character.isLetterOrDigit((char) t)) { //identificador
             cadena_aux=identificador(t);
             lexema_actual=palabrasReservadas.busca(cadena_aux);
             if (lexema_actual==null) {
                lexema_actual=tabla_simbolos.busca(cadena_aux);
             }
             if (lexema_actual==null) {
                lexema_actual=new entrada(ctes_eval.ID,
                                          new variable(cadena_aux));
             }
             break;
          } else if (t=='!') { // not
             tAux=siguienteCaracter();
             if (tAux=='=') { // distinto
                lexema_actual=new entrada(ctes_eval.DISTINTO, new variable(""));
             } else {
                devuelveCaracter();
                lexema_actual=new entrada(ctes_eval.NOT, new variable(""));
             }
             break;
          } else if (t=='&') { // and
             t=siguienteCaracter();
             if (t!='&') {
                devuelveCaracter();
             }
             lexema_actual=new entrada(ctes_eval.AND, new variable(""));
             break;
          } else if (t=='|') { // or
             t=siguienteCaracter();
             if (t!='|') {
                devuelveCaracter();
             }
             lexema_actual=new entrada(ctes_eval.OR, new variable(""));
             break;
          } else if (t=='=') { //asignaci�n
             t=siguienteCaracter();
             if (t=='=') {
                lexema_actual=new entrada(ctes_eval.IGUAL, new variable(""));
             } else {
                devuelveCaracter();
                lexema_actual=new entrada(ctes_eval.ASSIGN,new variable(""));
             }
             break;
          } else if (t=='+') {
             tAux=siguienteCaracter();
             if (tAux=='+') {
                lexema_actual=new entrada(ctes_eval.INC, new variable(""));
             } else {
                devuelveCaracter();
                lexema_actual=new entrada(t, new variable(""));
             }
             break;
          } else if (t=='-') {
             tAux=siguienteCaracter();
             if (tAux=='-') {
                lexema_actual=new entrada(ctes_eval.DEC, new variable(""));
             } else {
                devuelveCaracter();
                lexema_actual=new entrada(t, new variable(""));
             }
             break;
          } else if (t=='%') {
             lexema_actual=new entrada(ctes_eval.MOD, null);
             break;
          } else if (t=='"' || t==39) { //literal de cadena
             int comienzo=t;
             while (t!=ctes_eval.EOF) {
                t=siguienteCaracter();
                if (t==comienzo) {
                    break;
                } else {
                    cadena_aux+=(char) t;
                }
             }
             if (t==comienzo) {
                 lexema_actual=new entrada(ctes_eval.LITERAL_CADENA,
                                        new variable(new miString(cadena_aux)));
             } else {
                 throw new
                 SyntaxErrorException("fin cadena esperado: ' "+(char) 34+" '");
             }
             break;
          } else if (t=='<') {
             t=siguienteCaracter();
             if (t=='=') {
                 lexema_actual=new entrada(ctes_eval.MENOR_IGUAL,
                                           new variable(""));
             } else if (t=='>') {
                 lexema_actual=new entrada(ctes_eval.DISTINTO,
                                           new variable(""));
             } else {
                 devuelveCaracter();
                 lexema_actual=new entrada('<',new variable(""));
             }
             break;
          } else if (t=='>') {
             t=siguienteCaracter();
             if (t=='=') {
                 lexema_actual=new entrada(ctes_eval.MAYOR_IGUAL,
                                           new variable(""));
             } else {
                 devuelveCaracter();
                 lexema_actual=new entrada('>',new variable(""));
             }
             break;
          } else {
             lexema_actual=new entrada(t,new variable("")); //en otro caso se
             break;    // devuelve el c�digo ASCII como componente l�xico
          }
       }
    }

/*** Inicializa valores para an�lisis l�xico                         **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void inicializa_entorno()
    {
        chr_actual=0;
        numLineas=1;
        comienzoLineaAnterior=0;
        comienzoLineaActual=0;
        lexema_actual=new entrada();
    }

/*** Retira un car�cter de la entrada                                **********/
/*** Par�metros: t -> car�cter rec��n le�do                          **********/
/*** Devuelve: el car�cter retirado de la entrada                    **********/
    int siguienteCaracter()
    {
       if (chr_actual>=programa_a_ejecutar.length()) {
          return ultimoCaracter=ctes_eval.EOF;
       }
       return ultimoCaracter=(int) programa_a_ejecutar.charAt(chr_actual++);
    }

/*** Devuelve el �ltimo car�cter le�do a la entrada                  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void devuelveCaracter()
    {
       if (ultimoCaracter!=ctes_eval.EOF) {
          chr_actual--;
       }
    }

/*** Retira un n�mero de la entrada                                  **********/
/*** Par�metros: t-> car�cter reci�n le�do de la entrada             **********/
/*** Devuelve: la cadena que representa a dicho n�mero               **********/
    String numero(int t) throws SyntaxErrorException
    {
        String cadena_aux=new String();
        int punto=0;

        while (true) {
            cadena_aux+=(char) t;
            if (chr_actual>=programa_a_ejecutar.length()) {
               break;
            }
            t=(int) programa_a_ejecutar.charAt(chr_actual);
            if (t=='.') {
               punto++;
               if (punto>1) {
                  throw new SyntaxErrorException("dos puntos en un n�mero.");
               }
            } else if (!Character.isDigit((char) t)) {
               break;
            }
            chr_actual++;
         }
         return cadena_aux;
    }

/*** Retira un identificador de la entrada                           **********/
/*** Par�metros: t-> car�cter reci�n le�do de la entrada             **********/
/*** Devuelve: la cadena que representa a dicho identificador        **********/
    String identificador(int t)
    {
      String cadena_aux=new String();
      while (true) {
         cadena_aux+=(char)t;
         if (chr_actual>=programa_a_ejecutar.length()) {
            break;
         }
         t= (int) programa_a_ejecutar.charAt(chr_actual);
         if (!Character.isLetterOrDigit((char) t)) {
            break;
         }
         chr_actual++;
      }
      return cadena_aux;
    }

/*** Guarda en ultimo_lexema el �ltimo token retirado de la entrada  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void siguienteToken() throws SyntaxErrorException
    {
       ultimo_lexema=lexema_actual;
       analex();
    }

/*** Comprueba que el lexema actual casa con el token t y avanza al  **********/
/***    siguiente token                                              **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void  match(int t) throws SyntaxErrorException
    {
       if (lexema_actual.comp_lex==t) {
           siguienteToken();
       } else if (t==ctes_eval.FIN || t==ctes_eval.EOF) {
          if (ctes_eval.esTokenValido(lexema_actual.comp_lex)) {
              throw new
              SyntaxErrorException("se esperaba operador o final de instrucc.");
          } else {
              throw new
              SyntaxErrorException("token no identificado: "+
                                    (char) lexema_actual.comp_lex);
          }
       } else {
           throw new
           SyntaxErrorException("se esperaba '"+ctes_eval.nombreToken(t)+"'");
       }
    }

/*** Salta un bloque de la entrada limitada por tokenInicio tokenFin **********/
/*** Par�metros: tokenInicio-> marca el comienzo desde donde se salta**********/
/***             tokenFin-> delimita junto con el anterior el bloque **********/
/*** Devuelve: nada                                                  **********/
    void avanzaBloque(int tokenInicio, int tokenFin) throws SyntaxErrorException
    {
       int numBloques=1;

       match(tokenInicio);
       while (true) {
          if (lexema_actual.comp_lex==tokenInicio) {
             numBloques++;
          } else if (lexema_actual.comp_lex==tokenFin) {
             numBloques--;
          }
          if (numBloques==0) {
             break;
          }
          if (lexema_actual.comp_lex==ctes_eval.EOF) {
             throw new SyntaxErrorException("Unbalanced "+(char) tokenInicio);
          }
          siguienteToken();
       }
       match(tokenFin);
    }

/*** Salta un bloque de la entrada limitado por llaves '{...}'       **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void avanzaBloqueLlaves() throws SyntaxErrorException
    {
       avanzaBloque('{','}');
    }

/*** Salta un bloque de la entrada limitado por par�ntesis           **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void avanzaBloqueParent() throws SyntaxErrorException
    {
       avanzaBloque('(',')');
    }

/*** Devuelve la posici�n del pr�ximo car�cter a leer de la entrada  **********/
/***    por el analizador l�xico                                     **********/
/*** Par�metros: ninguno                                             **********/
    public int getChrActual()
    {
       return chr_actual;
    }

/*** Devuelve el �ltimo token de una expresi�n                       **********/
/*** Par�metros: s-> cadena que representa a la expresi�n            **********/
/*** Devuelve: el �ltimo token de dicha expresi�n                    **********/
    int ultimoToken(String s) throws SyntaxErrorException
    {
       int ult=-1;

       salva_entorno();
       programa_a_ejecutar=s;
       try {
          while (true) {
             siguienteToken();
             ult=lexema_actual.comp_lex;
             if (lexema_actual.comp_lex==ctes_eval.EOF) {
                break;
             }
          }
       } catch (SyntaxErrorException ex) {
          throw new SyntaxErrorException(ex.getMessage());
       } finally {
          restaura_entorno();
       }
       return ult;

    }


/******************************************************************************/
/******************************************************************************/
/**************** Analizador Sint�ctico e it�rprete ***************************/
/******************************************************************************/
/******************************************************************************/

/*** Ejecuta la lista de instrucciones indicadas en expre            **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    public variable programa(String expre)
    {
        programa_a_ejecutar=expre;
        inicializa_entorno();
        variable r=new variable(), res=new variable();

        try {
            siguienteToken();
            while (lexema_actual.comp_lex!=ctes_eval.EOF) {
               try {
                  r=sentencia();
               } catch (ReturnException ex) {
                  r=valorReturn;
               }
               if (r!=null) {
                  res=r; // devolvemos �ltima sentencia!=null, si la hay
               }
            }
            return res; // devolvemos el �ltimo valor calculado
        } catch (SyntaxErrorException ex) {
            if (ex.getMessage().indexOf("exec")>=0) {
               return new variable("",'E', ex.getMessage());
            } else {
               return new variable("",'E',"Error: "+ultimaLinea()
                                                   +"<-"+ex.getMessage());
            }
        }
    }


/*** An�lisis sint�ctico, comprobaci�n de tipos, tratamiento de      **********/
/***    errores y ejecuci�n de una sentencia                         **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �ltimo valor calculado en la ejecuci�n de dicha    **********/
/***    sentencia                                                    **********/
    public variable sentencia() throws SyntaxErrorException, ReturnException
    {
        int chr_aux;
        entrada lexem_aux;
        variable r=new variable();

        switch(lexema_actual.comp_lex) {
            case ctes_eval.PARAMETERS:
               lista_parametros();
               match(ctes_eval.FIN);
               r=null;
               break;
            case ctes_eval.DIM:
               r=declaracion_matriz();
               match(ctes_eval.FIN);
               break;
            case ctes_eval.REDIM:
               break;
            case ctes_eval.FOR:
               r=miFor();
               break;
            case ctes_eval.IF:
               r=miIf();
               break;
            case ctes_eval.WHILE:
               r=miWhile();
               break;
            case ctes_eval.RETURN:
               match(ctes_eval.RETURN);
               if (lexema_actual.comp_lex==ctes_eval.RETURN) {
                  throw new SyntaxErrorException("Return Return");
               }
               valorReturn=sentencia();
               lexema_actual.comp_lex=ctes_eval.EOF;
               throw new ReturnException();
            case ctes_eval.FIN:
               match(ctes_eval.FIN); // sentencia vac�a
               r=null;
               break;
            default:
               r=expr_cond();
               match(ctes_eval.FIN);
        }
        acumulador=r; // guardamos en el acumulador �ltimo valor calculado
        postIncrementa();
        postDecrementa();
        return r;
    }

/*** Copia o hace referenciar los par�metros reales en los formales  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void lista_parametros() throws SyntaxErrorException
    {
       int numParam=0;
       variable pReal, pFormal;
       Object [] lSal=new Object[2];

       if (chr_actual!=10 || numLineas!=1) {
          throw new SyntaxErrorException("sentencia PARAMETERS solo "+
                       "permisible al principio de la 1� l�nea de programa");
       }
       match(ctes_eval.PARAMETERS);
       if (listaParamReal!=null) {
          while (lexema_actual.comp_lex!=ctes_eval.FIN) {
             if (numParam>=listaParamReal.size()) {
                throw new
                SyntaxErrorException("faltan par�metros en la llamada.");
             }
             pReal=(variable) listaParamReal.elementAt(numParam);
             pFormal=new variable();
             if (lexema_actual.comp_lex=='*') {//variable se pasa por referencia
                match('*');
                if (pReal.nombre==null) {//si par�met.no es referencia a variab.
                   throw new SyntaxErrorException("se esperaba una referencia"+
                                                  " a variable");
                }
                lSal=new Object[2];
                lSal[0]=pReal; lSal[1]=pReal.nombre;
                listaParamSalida.addElement(lSal);
                pFormal=pReal;
             } else {
                pFormal=pReal.copia();
             }
             pFormal.nombre=lexema_actual.objeto.nombre;
             tabla_simbolos.inserta(ctes_eval.ID, pFormal);
             notifiNuevaVariable(pFormal);
             numParam++;
             match(ctes_eval.ID);
             if (lexema_actual.comp_lex==',') {
                match(',');
             }
          }
       } else {
          while (lexema_actual.comp_lex!=ctes_eval.FIN) {
             siguienteToken(); // si no se pasan par�metros reales saltamos fin
          }
       }
    }

/*** Sentencia dim-> crea una matriz del tama�o indicado en la       **********/
/***     sentencia                                                   **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: la matriz creada                                      **********
    variable declaracion_matriz() throws SyntaxErrorException
    {
        variable r=new variable();

        match(ctes_eval.DIM);
        if (lexema_actual.comp_lex==ctes_eval.ID) {
            entrada lexem_aux=new entrada(lexema_actual);
            match(ctes_eval.ID);
            match('[');
            r=lexem_aux.objeto=new variable(lexem_aux.objeto.nombre,'M',
               new miArray(new array(lista_expresiones_aritmeticas(),null)));
            match(']');
            tabla_simbolos.inserta(lexem_aux);
            notifiNuevaVariable(lexem_aux);
        }
        return r;
    }*/
    variable declaracion_matriz() throws SyntaxErrorException
    {
        variable r=new variable(), res;
        Vector dimensiones=new Vector(), aux=new Vector();

        match(ctes_eval.DIM);
        if (lexema_actual.comp_lex==ctes_eval.ID) {
            entrada lexem_aux=new entrada(lexema_actual);
            match(ctes_eval.ID);
            while (lexema_actual.comp_lex=='[') {
               match('[');
               dimensiones.add(this.expresion_aritmetica());
               match(']');
            }
            aux.add(dimensiones.elementAt(dimensiones.size()-1));
            r=new variable(lexem_aux.objeto.nombre,'M',new miArray(new array(aux,null)));
            for (int i=dimensiones.size()-2; i>=0; i--) {
               aux.removeAllElements();
               aux.add(dimensiones.elementAt(i));
               res=new variable("",'M', new miArray(new array(aux,null)));
               for(int j=((variable) dimensiones.elementAt(i)).intValue()-1; j>=0; j--) {
                  ((miArray) res.valor).asigna(j,r);
               }
               r=res;
            }
            r.nombre=lexem_aux.objeto.nombre;
            lexem_aux.objeto=r;
            tabla_simbolos.inserta(lexem_aux);
            notifiNuevaVariable(lexem_aux);
        }
        return r;
    }

/*** Sentencia for-> bucle controlado por condici�n                  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �ltimo valor calculador durante la ejecuci�n del   **********/
/***     bucle                                                       **********/
    variable miFor() throws SyntaxErrorException, ReturnException
    {
       int chr_aux, chr_primerParent, chr_coletilla, numLineas_primerParent;
       int numLineas_aux, numLineas_coletilla;
       entrada lexem_aux, primerParentLexem, lexema_coletilla;
       variable r=null, bol=new variable();
       Vector v;

       match(ctes_eval.FOR);
       chr_primerParent=chr_actual;
       numLineas_primerParent=numLineas;
       primerParentLexem=lexema_actual;
       match('(');
       if (lexema_actual.comp_lex!=';') {
          v=lista_expresiones_condicionales();
          r=((variable) v.elementAt(v.size()-1)).copia();
          postIncrementa(); postDecrementa();
       }
       chr_aux=chr_actual;
       lexem_aux=lexema_actual;
       numLineas_aux=numLineas;
       while (true) {
          match(';');
          v=lista_expresiones_condicionales();
          postIncrementa(); postDecrementa();
          bol=(variable) v.elementAt(v.size()-1);
          if (bol.bolValue()) {
             chr_coletilla=chr_actual; // guardamos posici�n
             lexema_coletilla=lexema_actual; // parte final for
             numLineas_coletilla=numLineas;
             match(';');
             chr_actual=chr_primerParent;
             lexema_actual=primerParentLexem;
             numLineas=numLineas_primerParent;
             avanzaBloqueParent(); // avanzamos hasta el bloque de sentencias
             r=bloqueSentencias(); // ejecutamos {bloque}
             chr_actual=chr_coletilla; //volvemos a: ; lista_expr._conds.) {...}
             lexema_actual=lexema_coletilla;
             numLineas=numLineas_coletilla;
             match(';');
             if (lexema_actual.comp_lex!=')') {
                v=lista_expresiones_condicionales();//evaluamos 3� list_exp.cond
                r=((variable) v.elementAt(v.size()-1)).copia();
                postIncrementa(); postDecrementa();
             }
             match(')');
          } else {
             chr_actual=chr_primerParent;
             lexema_actual=primerParentLexem;
             numLineas=numLineas_primerParent;
             avanzaBloqueParent();
             avanzaBloqueLlaves();
             if (r!=null) {
                return r;
             } else {
                return bol;
             }
          }
          chr_actual=chr_aux; //volvemos a la condici�n
          lexema_actual=lexem_aux;
          numLineas=numLineas_aux;
       }
    }

/*** Sentencia if-> ejecuci�n condicional de bloque de instrucciones **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �ltimo valor calculador durante la ejecuci�n de la **********/
/***     sentencia                                                   **********/
    variable miIf() throws SyntaxErrorException, ReturnException
    {
       variable r=null, bol=new variable();

       while (lexema_actual.comp_lex==ctes_eval.IF ||
              lexema_actual.comp_lex==ctes_eval.ELSEIF) {

          match(lexema_actual.comp_lex);
          match('(');
          bol=expr_cond();
          compruebaTipo('B',bol);
          match(')');
          if (bol.bolValue()) {
             r=bloqueSentencias();
             while (lexema_actual.comp_lex==ctes_eval.ELSE ||
                    lexema_actual.comp_lex==ctes_eval.ELSEIF) {
                match(lexema_actual.comp_lex);
                while (lexema_actual.comp_lex!='{') {
                   siguienteToken();
                }
                avanzaBloqueLlaves();
             }
          } else {
             avanzaBloqueLlaves();
             if (lexema_actual.comp_lex==ctes_eval.ELSE) {
                match(ctes_eval.ELSE);
                r=bloqueSentencias();
             }
          }
       }
       if (r!=null) {
          return r;
       } else {
          return bol;
       }
    }

/*** Sentencia while-> ejecuci�n bloque de instrucciones mientras se **********/
/***     cumpla la condici�n                                         **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �ltimo valor calculador durante la ejecuci�n del   **********/
/***     bucle                                                       **********/
    variable miWhile() throws SyntaxErrorException, ReturnException
    {
       variable r= new variable(), bol=new variable();
       int chr_actual_aux, numLineas_aux;
       entrada lexema_actual_aux;

       match(ctes_eval.WHILE);
       chr_actual_aux=chr_actual;
       numLineas_aux=numLineas;
       lexema_actual_aux=lexema_actual;
       while (true) {
          match('(');
          bol=expr_cond();
          compruebaTipo('B', bol);
          match(')');
          if (bol.bolValue()) {
             r=bloqueSentencias();
          } else {
             avanzaBloqueLlaves();
             if (r!=null) {
                return r;
             } else {
                return bol;
             }
          }
          chr_actual=chr_actual_aux;
          numLineas=numLineas_aux;
          lexema_actual=lexema_actual_aux;
       }
    }

/*** Ejecuta un bloque de sentencias encerrado entre llaaves         **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: �ltimo valor calculado en dicho bloque                **********/
    variable bloqueSentencias() throws SyntaxErrorException, ReturnException
    {
       variable r=new variable();

       match('{');
       while (lexema_actual.comp_lex!='}') {
          r=sentencia();
       }
       match('}');

       return r;
    }


/*** Realiza los postincrementos contenios en una instrucci�n tras   **********/
/***     su ejecuci�n                                                **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void postIncrementa()
    {
       variable v;

       if (postIncrementos.size()>0) {
          try {
             for(int i=0; i<postIncrementos.size(); i++) {
                v=(variable) postIncrementos.elementAt(i);
                v.inc();
                notifiNuevaVariable(v);
             }
          } catch (SyntaxErrorException e) {
          }
          postIncrementos=new Vector();
       }
    }

/*** Realiza los postdecrementos contenios en una instrucci�n tras   **********/
/***     su ejecuci�n                                                **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void postDecrementa()
    {
       variable v;

       if (postDecrementos.size()>0) {
          try {
             for(int i=0; i<postDecrementos.size(); i++) {
                v=(variable) postDecrementos.elementAt(i);
                v.dec();
                notifiNuevaVariable(v);
             }
          } catch (SyntaxErrorException e) {
          }
          postDecrementos=new Vector();
       }
    }

/*** Eval�a una lista de expresiones separadas por comas             **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: una copia de dicha lista                              **********/
    Vector lista_expresiones_condicionales_copia() throws SyntaxErrorException
    {
       Vector r=new Vector();

        r.addElement(expr_cond().copia());
        while (lexema_actual.comp_lex==',') {
            match(',');
            r.addElement(expr_cond().copia());
        }
        return r;
    }

/*** Eval�a una lista de expresiones separadas por comas             **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: dicha lista                                           **********/
    Vector lista_expresiones_condicionales() throws SyntaxErrorException
    {
        Vector r=new Vector();

        r.addElement(expr_cond());
        while (lexema_actual.comp_lex==',') {
            match(',');
            r.addElement(expr_cond());
        }
        return r;
    }

/*** Eval�a una lista de expresiones aritm�ticas                     **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: dicha lista                                           **********/
    Vector lista_expresiones_aritmeticas() throws SyntaxErrorException
    {
        Vector r=new Vector();

        r.addElement(expresion_aritmetica().copia());
        while (lexema_actual.comp_lex==',') {
           match(',');
           r.addElement(expresion_aritmetica().copia());
        }
        return r;
    }

/*** Eval�a una expresi�n                                            **********/
/*** Par�metros: cadena representando dicha expresi�n                **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n           **********/
    variable expr_cond(String e) throws SyntaxErrorException
    {

        salva_entorno();
        inicializa_entorno();
        programa_a_ejecutar=e;
        siguienteToken();
        try {
            return expr_cond();
        } catch (SyntaxErrorException ex) {
            throw new SyntaxErrorException("Error al evaluar: "+
                                            programa_a_ejecutar+
                                            "<- "+ex.getMessage());
        } finally {
            restaura_entorno();
        }
    }

/*** Eval�a una expresi�n en la cadena de entrada                    **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable expr_cond() throws SyntaxErrorException
    {
        variable r=new variable(), r1=new variable();
        r=expr_and();
        while (lexema_actual.comp_lex==ctes_eval.OR) {//tiene menor prec.que and
            match(ctes_eval.OR);
            r1=expr_and();
            compruebaTipo('B', r); compruebaTipo('B', r1);
            r=new variable(new miBooleano(r.bolValue() | r1.bolValue()));
        }
        return r;
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n           **********/
    variable expr_and() throws SyntaxErrorException
    {
        variable r=new variable(),r1=new variable();;
        r=expr_not();
        while (lexema_actual.comp_lex==ctes_eval.AND) {
            match(ctes_eval.AND);
            r1=expr_not();
            compruebaTipo('B', r); compruebaTipo('B', r1);
            r=new variable(new miBooleano(r.bolValue() && r1.bolValue()));
        }
        return r;
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable expr_not() throws SyntaxErrorException
    {
       boolean expresion_not=false;
       variable r;

       while (lexema_actual.comp_lex==ctes_eval.NOT) {
          expresion_not= ! expresion_not;
          match(ctes_eval.NOT);
       }
       r=expr_cond_simple();
       if (expresion_not) {
          compruebaTipo('B',r);
          r=new  variable(new miBooleano(!r.bolValue()));
       }
       return r;
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable expr_cond_simple() throws SyntaxErrorException
    {
        variable r=new variable(), r1=new variable(), r2=new variable();

        r1=expr();
        switch (lexema_actual.comp_lex) {
            case '<':
                match('<');
                r2=expr();
                r=new variable(new miBooleano(r1.menor(r2)));
                break;
            case ctes_eval.MENOR_IGUAL:
                match(ctes_eval.MENOR_IGUAL);
                r2=expr();
                r=new variable(new miBooleano(r1.menor_o_igual(r2)));
                break;
            case '>':
                match('>');
                r2=expr();
                r=new variable(new miBooleano(r1.mayor(r2)));
                break;
            case ctes_eval.MAYOR_IGUAL:
                match(ctes_eval.MAYOR_IGUAL);
                r2=expr();
                r=new variable(new miBooleano(r1.mayor_o_igual(r2)));
                break;
            case ctes_eval.IGUAL:
                match(ctes_eval.IGUAL);
                r2=expr();
                r=new variable(new miBooleano(r1.igual(r2)));
                break;
            case ctes_eval.DISTINTO:
                match(ctes_eval.DISTINTO);
                r2=expr();
                r=new variable(new miBooleano(r1.distinto(r2)));
                break;
            default:
                return r1;
        }
        if (r1.tipo!=r2.tipo) {
            throw new SyntaxErrorException("Error en expr condicional: "+
                                            "tipos no coinciden");
        }
        return r;
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable expr() throws SyntaxErrorException
    {
       variable r=null;

       if (lexema_actual.comp_lex=='{') { // una matriz es una expresi�n
          return new variable("",'M',new miArray(matriz()));
       }
       if (lexema_actual.comp_lex==ctes_eval.ID) {
          r=asignacion(); // una asignaci�n es una expresi�n
          if (r!=null) {
             return r;
          }
       }
       r=expresion_aritmetica();

       return r;
    }

/*** Asignaci�n y Evaluaci�n de expresiones en la cadena de entrada  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: una variable conteniendo el valor asignado            **********/
    variable asignacion() throws SyntaxErrorException
    {
       variable r=null;

       if (lexema_actual.comp_lex==ctes_eval.ID) {
          int chr_aux=chr_actual;
          entrada lexem_aux=lexema_actual;
          match(ctes_eval.ID);
          if (lexema_actual.comp_lex==ctes_eval.ASSIGN) {
             match(ctes_eval.ASSIGN);
             lexem_aux.objeto.asigna(expr_cond());
             tabla_simbolos.inserta(lexem_aux);
             notifiNuevaVariable(lexem_aux);
             return lexem_aux.objeto;
          } else if (lexema_actual.comp_lex=='[') {
             if (lexem_aux.objeto.tipo=='M') {
                variable rprima=refMatriz(lexem_aux);
                if (rprima.tipo=='S' && lexema_actual.comp_lex=='[') {
                   r=asignacionCaracterCadena(rprima);
                   if (r!=null) {
                      notifiNuevaVariable(lexem_aux);
                      return r;
                   }
                }
                if (lexema_actual.comp_lex== ctes_eval.ASSIGN) {
                   match(ctes_eval.ASSIGN);
                   r=expr_cond();
                   rprima.asigna(r);
                   notifiNuevaVariable(lexem_aux);
                   rprima.nombre=lexem_aux.objeto.nombre;
                   return r;
                }
             } else if (lexem_aux.objeto.tipo=='S') {
                r=asignacionCaracterCadena(lexem_aux.objeto);
                if (r!=null) {
                   notifiNuevaVariable(lexem_aux);
                   return r;
                }
             } else if (lexem_aux.objeto.tipo!=' ') {
                throw new SyntaxErrorException("Op [] s�lo aplicable a String"+
                                               " o matriz");
             } else {
                throw new SyntaxErrorException("variable no existente "+
                                               lexem_aux.objeto.nombre);
             }
          }
          chr_actual=chr_aux;    // sino hubo �xito
          lexema_actual=lexem_aux; // backtracking volvemos hacia atr�s
       }
       return r;
    }

/*** Asignaci�n de un car�cter dentro de una cadena                  **********/
/*** Par�metros: r-> la cadena en la que se asigna un car�cter       **********/
/*** Devuelve: una variable conteniendo el car�cter asignado         **********/
    variable asignacionCaracterCadena(variable r) throws SyntaxErrorException
    {
       variable res;
       int i;

       compruebaTipo('S',r);
       match('[');
       res=expr_cond();
       match(']');
       i=res.intValue();
       if (!(0<=i && i<r.len())) {
          throw new SyntaxErrorException("indice fuera de rango '"+i+"'");
       }
       if (lexema_actual.comp_lex==ctes_eval.ASSIGN) {
          match(ctes_eval.ASSIGN);
          res=expr_cond();
          compruebaTipo('S', res);
          if (res.len()!=1) {
             throw new SyntaxErrorException("parte dcha.debe ser char<->cadena"
                                            +" long.1");
          }
          String s=r.valor.toString();
          if (i>0) {
             String s2=s.substring(0,i);
             char r2=res.valor.toString().charAt(0);
             String s3=s.substring(i+1,s.length());
             r.valor=new miString(s.substring(0,i)
                        +res.valor.toString().charAt(0)+s.substring(i+1,
                        s.length()));
          } else {
             r.valor=new miString(res.valor.toString().charAt(0)+
                                  s.substring(i+1,s.length()));
          }
          return new variable('S',new String(""+
                                             res.valor.toString().charAt(0)));
       }
       return null;
    }

/*** Retira una expresi�n tipo matriz de la entrada                  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: una lista con los elementos de dicha matriz           **********/
    Vector matriz() throws SyntaxErrorException
    {
        Vector r;

        match('{');
        r=lista_expresiones_condicionales_copia();
        match('}');

        return r;
    }

/*** Eval�a una expresi�n pasada como par�metro                      **********/
/*** Par�metros: e-> la expresi�n a evaluar                          **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable expresion_aritmetica(String e) throws SyntaxErrorException
    {

        salva_entorno();
        inicializa_entorno();

        programa_a_ejecutar=e;
        siguienteToken();
        try {
            return expresion_aritmetica();
        } catch (SyntaxErrorException ex) {
            throw new SyntaxErrorException("Error al evaluar: "+
                                     programa_a_ejecutar+"<- "+ex.getMessage());
        } finally {
            restaura_entorno();
        }
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable expresion_aritmetica() throws SyntaxErrorException
    {
        variable r;
        entrada lexem_aux;

        switch(lexema_actual.comp_lex) {
            case '-':
                match('-');
                r=new variable(-term().doubleValue());
                compruebaTipo('D', r);
                break;
            case '+':
                match('+');
                r=new variable(term().doubleValue());
                compruebaTipo('D', r);
                break;
            default:
                r=term();
        }
        while(lexema_actual.comp_lex=='+' || lexema_actual.comp_lex=='-') {
            lexem_aux=new entrada(lexema_actual);
            match(lexema_actual.comp_lex);
            if (lexem_aux.comp_lex=='+') {
                r=suma(r,term());
            } else {
                r=resta(r,term());
            }
        }
        return r;
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable term() throws SyntaxErrorException
    {
       variable res, r=new variable();
       int l;

       res=factor();
       while (true) {
          l=lexema_actual.comp_lex;
          if (l=='*' || l=='/'  || l==ctes_eval.DIV || l==ctes_eval.MOD ||
              l==ctes_eval.FMOD || l=='\\' || l=='%') {
              match(l);
              r=factor();
              compruebaTipo('D', res); compruebaTipo('D', r);
              // s�lo tiene sentido multiplicar, div..., n�meros
          }
          switch (l) {
             case '*':
                res=new variable(res.doubleValue()*r.doubleValue());
                break;
             case '/':
                res=new variable(res.doubleValue()/r.doubleValue());
                break;
             case ctes_eval.DIV: case '\\':
                res=new variable(res.doubleValue()/r.doubleValue());
                res=new variable((int) res.doubleValue());
                break;
             case ctes_eval.MOD: case ctes_eval.FMOD: case '%':
               if (miDouble.esEntero(res.doubleValue()) &&
                   miDouble.esEntero(r.doubleValue())) {
                   res=new variable(res.intValue() % r.doubleValue());
                } else {
                   res=new variable(Math.IEEEremainder(res.doubleValue(),
                                    r.doubleValue()));
                }
                break;
             default:
                return(res);
          }
       }
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable factor() throws SyntaxErrorException
    {
       variable res=new variable(), r=new variable();

       res=potencia();
       while (lexema_actual.comp_lex=='^') {
         match(lexema_actual.comp_lex);
         r=potencia();
         compruebaTipo('D', res); compruebaTipo('D', r);
         res=new variable(Math.pow(res.doubleValue(), r.doubleValue()));
       }
       return res;
    }

/*** Devuelve la tangente hiperb�lica de un valor                    **********/
/*** Par�metros: x-> valor sobre el que calcular la tangente hiperb�lica*******/
    public static double tanh(double x)
    {
       return (Math.pow(Math.E, x)-Math.pow(Math.E,-x))/
               (Math.pow(Math.E,x)+Math.pow(Math.E,-x));
    }

/*** Devuelve el seno hiperb�lico de un valor                        **********/
/*** Par�metros: x-> valor sobre el que calcular el seno hiperb�lico **********/
    public static double senh(double x)
    {
       return (Math.pow(Math.E,x)-Math.pow(Math.E,-x))/2;
    }

/*** Devuelve el coseno hiperb�lico de un valor                      **********/
/*** Par�metros: x-> valor sobre el que calcular el coseno hiperb�lico ********/
    public static double cosh(double x)
    {
       return (Math.pow(Math.E,x)+Math.pow(Math.E,-x))/2;
    }

/*** Evaluaci�n de expresiones en la cadena de entrada               **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable potencia() throws SyntaxErrorException
    {
       variable res=new variable(), par1, par2, par3;
       entrada lexem_aux;
       int l;

       switch(lexema_actual.comp_lex) {
          case '(':
             match('(');
             res=expr_cond();
             match(')');
             break;
          case ctes_eval.TRUE:
             match(ctes_eval.TRUE);
             res=new variable(true);
             break;
          case ctes_eval.FALSE:
             match(ctes_eval.FALSE);
             res=new variable(false);
             break;
          case ctes_eval.IIF:
             res=funcion_iif();
             break;
          case ctes_eval.NUM:
             res=lexema_actual.objeto;
             match(ctes_eval.NUM);
             break;
          case ctes_eval.LITERAL_CADENA:
             res=lexema_actual.objeto;
             match(ctes_eval.LITERAL_CADENA);
             break;
          case ctes_eval.ID:
             lexem_aux=lexema_actual;
             res=valID();
             l=lexema_actual.comp_lex;
             if (l==ctes_eval.INC) {
                compruebaTipo('D', res);
                postIncrementos.addElement(res);
                match(ctes_eval.INC);
                notifiNuevaVariable(lexem_aux);
             } else if (l==ctes_eval.DEC) {
                compruebaTipo('D',res);
                postDecrementos.addElement(res);
                match(ctes_eval.DEC);
                notifiNuevaVariable(lexem_aux);
             }
             break;
          case ctes_eval.INC: case ctes_eval.DEC:
             l=lexema_actual.comp_lex;
             match(lexema_actual.comp_lex);
             lexem_aux=lexema_actual;
             res=valID();
             compruebaTipo('D',res);
             if (l==ctes_eval.INC) {
                res.inc();
             } else {
                res.dec();
             }
             notifiNuevaVariable(lexem_aux);
             break;
          //funciones matem�ticas de un s�lo argumento
          case ctes_eval.COS: case ctes_eval.COSH: case ctes_eval.ACOS:
          case ctes_eval.SEN: case ctes_eval.SENH: case ctes_eval.ASEN:
          case ctes_eval.TAN: case ctes_eval.TANH: case ctes_eval.ATAN:
          case ctes_eval.CEIL: case ctes_eval.EXP: case ctes_eval.ABS:
          case ctes_eval.FLOOR: case ctes_eval.LN: case ctes_eval.SQRT:
          case ctes_eval.INT:
             l=lexema_actual.comp_lex;
             match(l);
             res=potencia();
             compruebaTipo('D', res);
             switch (l) {
                case ctes_eval.COS:
                   res=new variable(Math.cos(res.doubleValue()));
                   break;
                case ctes_eval.COSH:
                   res=new variable(cosh(res.doubleValue()));
                   break;
                case ctes_eval.ACOS:
                   res=new variable(Math.acos(res.doubleValue()));
                   break;
                case ctes_eval.SEN:
                   res=new variable(Math.sin(res.doubleValue()));
                   break;
                case ctes_eval.SENH:
                   res=new variable(senh(res.doubleValue()));
                   break;
                case ctes_eval.ASEN:
                   res=new variable(Math.asin(res.doubleValue()));
                   break;
                case ctes_eval.TAN:
                   res=new variable(Math.tan(res.doubleValue()));
                   break;
                case ctes_eval.TANH:
                   res=new variable(tanh(res.doubleValue()));
                   break;
                case ctes_eval.ATAN:
                   res=new variable(Math.atan(res.doubleValue()));
                   break;
                case ctes_eval.CEIL:
                   res=new variable(Math.ceil(res.doubleValue()));
                   break;
                case ctes_eval.EXP:
                   res=new variable(Math.exp(res.doubleValue()));
                   break;
                case ctes_eval.ABS:
                   res=new variable(Math.abs(res.doubleValue()));
                   break;
                case ctes_eval.FLOOR:
                   res=new variable(Math.floor(res.doubleValue()));
                   break;
                case ctes_eval.LN:
                   res=new variable(Math.log(res.doubleValue()));
                   break;
                case ctes_eval.SQRT:
                   res=new variable(Math.sqrt(res.doubleValue()));
                   break;
                case ctes_eval.INT:
                   res=new variable(res.intValue());
                   break;
             }
             break;
          // funciones matem�ticas de 2 par�metros
          case ctes_eval.LOG:  case ctes_eval.ATAN2:
             l=lexema_actual.comp_lex;
             match(l);
             match('(');
             par1=expr_cond();
             compruebaTipo('D', par1);
             match(',');
             par2=expr_cond();
             compruebaTipo('D', par2);
             match(')');
             switch(l) {
                case ctes_eval.LOG:
                   res=new variable(Math.log(par2.doubleValue())/
                                    Math.log(par1.doubleValue()));
                   break;
                case ctes_eval.ATAN2:
                   res=new variable(Math.atan2(par1.doubleValue(),
                                    par2.doubleValue()));
                   break;
             }
             break;
          case ctes_eval.PI: case ctes_eval.E:
             String nombre=lexema_actual.objeto.nombre;
             res=lexema_actual.objeto.copia();
             match(lexema_actual.comp_lex);
             if (lexema_actual.comp_lex==ctes_eval.ASSIGN) { // error claro
                throw new SyntaxErrorException(nombre+" es una constante");
             }
             break;
          case ctes_eval.ACUMULADOR:
             match(ctes_eval.ACUMULADOR);
             res=acumulador;
             break;
          // funciones de un par�metro cadena y dos num�ricos
          case ctes_eval.INTEGRAL: case ctes_eval.SUBSTR:
             l=lexema_actual.comp_lex;
             match(l);
             match('(');
             par1=expr_cond();
             compruebaTipo('S', par1);
             match(',');
             par2=expr_cond();
             compruebaTipo('D', par2);
             match(',');
             par3=expr_cond();
             compruebaTipo('D', par3);
             match(')');
             switch (l) {
                case ctes_eval.INTEGRAL:
                   res=new variable(integral(par1.toString(),par2.doubleValue(),
                                             par3.doubleValue()));
                   break;
                case ctes_eval.SUBSTR:
                   res=new variable('S',par1.substring(par2.intValue(),
                                    par3.intValue()));
                   break;
             }
             break;
          case ctes_eval.DERIVADA: case ctes_eval.DERIV:
             res=derivada();
             break;
          // funciones de un par�metros tipo cadena
          case ctes_eval.SIMPLIFICA: case ctes_eval.EVALUA:
          case ctes_eval.TOUPPER: case ctes_eval.TOLOWER:
             l=lexema_actual.comp_lex;
             match(l);
             match('(');
             par1=expr_cond();
             compruebaTipo('S', par1);
             match(')');
             switch(l) {
                case ctes_eval.SIMPLIFICA:
                   res=new variable("",'S',
                           new miString(simplifica_expresion(par1.toString())));
                   break;
                case ctes_eval.EVALUA:
                   {
                      String original=programa_a_ejecutar;
                      salva_entorno();
                      if (ultimoToken(par1.toString())==ctes_eval.FIN) {
                         res=programa(par1.toString());
                      } else {
                         res=programa(par1.toString()+(char) ctes_eval.FIN);
                      }
                      if (res.tipo=='E') {
                         res=new variable('E', original+"->"+res.toString());
                      }
                      restaura_entorno();
                   }
                   break;
                case ctes_eval.TOUPPER:
                   res=new variable('S', par1.toString().toUpperCase());
                   break;
                case ctes_eval.TOLOWER:
                   res=new variable('S', par1.toString().toLowerCase());
                   break;
             }
             break;
          case ctes_eval.PTR:
             match(ctes_eval.PTR);
             match('(');
             par1=expr_cond();
             compruebaTipo('S', par1);
             match(',');
             par2=expr_cond();
             compruebaTipo('S', par2);
             match(',');
             par3=expr_cond();
             compruebaTipo('S', par3);
             res=new variable(new miString(regla_aritmetica(par1.toString(),
                                                            par2.toString(),
                                                            par3.toString())));
             match(')');
             break;
          case ctes_eval.EXEC:
             match(ctes_eval.EXEC);
             match('(');
             par1=expr_cond();
             compruebaTipo('S', par1);
             String s=par1.toString();
             if (lexema_actual.comp_lex==')') {
                match(')');
                res=exec(s,null);
             } else {
                match(',');
                Vector v=lista_expresiones_condicionales();
                match(')');
                res=exec(s,v);
             }
             break;
          // funciones de un par�metro de cualquier tipo
          case ctes_eval.TYPE: case ctes_eval.LEN:
             l=lexema_actual.comp_lex;
             match(l);
             match('(');
             par1=expr_cond();
             match(')');
             switch(l) {
                case ctes_eval.TYPE:
                   res=new variable('S',""+par1.type());
                   break;
                case ctes_eval.LEN:
                   res=new variable(par1.len());
                   break;
             }
             break;
          case ctes_eval.LEER:
             {
                boolean nuevaVar=false;
                match(ctes_eval.LEER);
                match('(');
                par1=expr_cond();
                compruebaTipo('S', par1);
                match(',');
                if (lexema_actual.objeto.tipo!=' ') {
                   par2=valID();
                } else {
                   par2=new variable(lexema_actual.objeto.nombre);
                   match(ctes_eval.ID);
                   nuevaVar=true;
                }
                if (lexema_actual.comp_lex==',') {
                   match(',');
                   par3=expr_cond();
                   compruebaTipo('S', par3);
                   String p3=par3.toString();
                   if (p3.equalsIgnoreCase("S") || p3.equalsIgnoreCase("M")
                      || p3.equalsIgnoreCase("D") || p3.equalsIgnoreCase("B")) {
                      par2.tipo=p3.charAt(0);
                   } else {
                      throw new SyntaxErrorException("tercer par�metro debe ser"
                                                     +" 'S', 'M', 'D' o 'B'");
                   }
                }
                if (par2.tipo==' ') {
                   throw new SyntaxErrorException("Debe especificar un tipo si"
                                                  +" la variable no �xiste");
                }
                match(')');
                leerVariable(par1.toString(), par2);
                if (nuevaVar) {
                   tabla_simbolos.inserta(ctes_eval.ID,par2);
                }
                notifiNuevaVariable(par2);
             }
             break;

          //funciones con 1� par�metro tipo matriz
          case ctes_eval.DIMENS: case ctes_eval.NDIM:
             l=lexema_actual.comp_lex;
             match(l);
             match('(');
             par1=expr_cond();
             compruebaTipo('M', par1);
             if (l==ctes_eval.DIMENS) {
                match(',');
                par2=expr_cond();
                compruebaTipo('D', par2);
                int d=par2.intValue();
                if (d>=par1.getNumDimensiones()) {
                   throw new SyntaxErrorException("Error dimensi�n fuera de"
                                                  +" rango");
                }
                res=new variable(par1.getDimension(par2.intValue()));
             } else { // if (l==ctes_eval.NDIM)
                res=new variable(par1.getNumDimensiones());
             }
             match(')');
             break;
          default:
             if (ctes_eval.esTokenValido(lexema_actual.comp_lex)) {
                if (ctes_eval.esOpAritm(ultimo_lexema.comp_lex)) {
                   if (ultimo_lexema.comp_lex=='+' ||
                       ultimo_lexema.comp_lex=='-') {
                      throw new SyntaxErrorException("se esperaba una"+
                                                     " expresi�n.");
                   } else {
                      throw new SyntaxErrorException("se esperaba una"
                                               +" expresi�n de tipo num�rico.");
                   }
                }
                throw new SyntaxErrorException("token no esperado.");
             } else {
                throw new SyntaxErrorException("token no identificado.");
             }
       }
       return res;
    }

/*** Calcula la suma de dos variables seg�n sean sus tipos           **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable suma(variable s1, variable s2)
    {
        variable r;
        if (s1.tipo=='D' && s2.tipo=='D') {
            r=new variable(s1.doubleValue()+s2.doubleValue());
        } else {
            r= new variable('S',s1.toString() + s2.toString());
        }
        return r;
    }

/*** Calcula la resta de dos variables seg�n sean sus tipos          **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable resta(variable r1, variable r2)
    {
        variable r;
        int i;

        if (r1.tipo=='D' && r2.tipo=='D') {
            r=new variable(r1.doubleValue()-r2.doubleValue());
        } else {
            String rr1=new String(r1.toString()),rr2=new String(r2.toString());
            if ((i=rr1.indexOf(rr2))>=0) { // indexOf devuelve �ndice donde
                               //comienza rr2, negativo si rr1 no contiene a rr2
                r=new variable('S',rr1.substring(0,i)+rr1.substring(i+
                                                    rr2.length(),rr1.length()));
            } else {
                r=new variable('S',rr1);
            }
        }
        return r;
    }


/*** Retira y eval�a la funci�n condicional de la cadena de entrada  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la funci�n              **********/
    variable funcion_iif() throws SyntaxErrorException
    {
        variable r,r1, r2;

        match(ctes_eval.IIF);
        match('(');
        r=expr_cond();
        compruebaTipo('B', r);
        match(';');
        r1=expr_cond();
        match(';');
        r2=expr_cond();
        match(')');
        if  (r.bolValue()) {
            return r1;
        } else {
            return r2;
        }
    }

/*** Calcula la integral definida de una funci�n matem�tica por el   **********/
/***     m�todo de interpolaci�n cuadr�tica de Simpson
/*** Par�metros: f-> cadena representando la funci�n matem�tica a    **********/
/***                 integrar                                        **********/
/***             a-> l�mite inferior de integraci�n                  **********/
/***             b-> l�mite superior de integraci�n                  **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    public double integral(String f, double a, double b)
                                                     throws SyntaxErrorException
    {
        variable r=null;

        if (a>b) {
            throw new SyntaxErrorException("Error en integral: �2� par�metro"
                                           +" debe ser menor que 3� par�metro");
        }
        int num_partes=(int) (Math.abs(((int) b+1)-((int) a-1))*10);
        /*dividimos intervalo en num_partes iguales*/
        if (num_partes<30) {
            num_partes=30;
            /* como m�nimo dividimos el intervalo en 30 partes iguales*/
        }
        double h=(b-a)/num_partes;
        entrada e=tabla_simbolos.busca("x");
        if (e!=null) {
           r=e.objeto.copia();
        }
        tabla_simbolos.asigna("x",a);
        miDouble Xref=refMiDouble("x");
        arbol_binario arbol=arbol_expr(f);
        double suma=expresion_aritmetica(arbol);
        Xref.asigna(b);
        suma+=expresion_aritmetica(arbol);
        double h2=2*h;
        double x=a+h;
        for(int i=1; i<=num_partes/2; i++) {
            Xref.asigna(x);
            suma+=expresion_aritmetica(arbol)*4;
            x+=h2;
        }
        x=a+h2;
        for(int i=1; i<(num_partes/2); i++) {
            Xref.asigna(x);
            suma+=expresion_aritmetica(arbol)*2;
            x+=h2;
        }
        if (r!=null) { //si exist�a en el evaluador la variable x la restauramos
           e.objeto.asigna(r);
        } else { /* no exist�a la variable la borramos */
            tabla_simbolos.borrar("x");
        }
        return miDouble.redondeo((suma*h/3),10);
    }

/*** Ejecuta una funci�n o programa contenido en un fichero          **********/
/*** Par�metros: fichProg-> nombre del fichero conteniendo el c�digo **********/
/***             parametros-> lista de los par�metros reales         **********/
/*** Devuelve: el valor calculado en �ltimo lugar en la ejecuci�n    **********/
    variable exec(String fichProg,Vector parametros) throws SyntaxErrorException
    {
        String programa=""; Object pSal[];
        variable r=new variable(), v;
        tab_simb tabla_simbolos_aux=tabla_simbolos;
        tabla_simbolos=new tab_simb(); // iniciamos variables locales
        Vector listaParamReal_aux=listaParamReal;
        listaParamReal=parametros;
        Vector listaParamSalida_aux=listaParamSalida;
        Vector postIncrementos_aux=postIncrementos;
        Vector postDecrementos_aux=postDecrementos;

        listaParamSalida=new Vector();
        postIncrementos=new Vector();
        postDecrementos=new Vector();
        notifiNuevoEntorno();
        salva_entorno();
        if (!ficheros.abrir(fichProg)) {
           r=new variable("",'E',"Error al Abrir el Fichero '"+fichProg+"'");
        } else {
           programa=ficheros.getContenido(fichProg);
           r=programa(programa);
             if (r.tipo=='E') {
              r=new variable("",'E',"exec "+fichProg+": linea "+numLineas+", "
                                                                 +r.toString());
           }
           for(int i=0; i<listaParamSalida.size(); i++) {
              pSal=(Object []) listaParamSalida.elementAt(i);
              ((variable) pSal[0]).nombre= (String) pSal[1];
              // volvemos a nombrar a las variables por sus nombres originales
           }
        }
        tabla_simbolos=tabla_simbolos_aux;
        notifiFinEntorno();
        if (r.tipo!='E') {
           String s;
          //mostramos nuevos valores par�metros de salida en listaVariables
           for(int i=0;i<listaParamSalida.size(); i++) {
             s=((variable)((Object [])listaParamSalida.elementAt(i))[0]).nombre;
             v=(tabla_simbolos.busca(s)).objeto;
             notifiNuevaVariable(v);
           }
        }
        restaura_entorno();
        postIncrementos=postIncrementos_aux;
        postDecrementos=postDecrementos_aux;
        listaParamReal=listaParamReal_aux;
        if (r.tipo=='E') {
           throw new SyntaxErrorException(r.toString());
        }
        return r;
    }

/*** Comprueba que una variable es de un tipo determinado            **********/
/*** Par�metros: tipo-> tipo a comprobar                             **********/
/***             v-> variable a chequear                             **********/
/*** Devuelve: nada                                                  **********/
    void compruebaTipo(char tipo, variable v) throws SyntaxErrorException
    {
       if (v.tipo!=tipo) {
          switch (tipo) {
             case 'S': case 's':
                throw new SyntaxErrorException("Error se esperaba una"
                                                  +" expresi�n de tipo cadena");
             case 'D': case 'd':
                throw new SyntaxErrorException("Error se esperaba una"
                                                +" expresi�n de tipo num�rico");
             case 'B': case 'b':
                throw new SyntaxErrorException("Error se esperaba una"
                                                +" expresi�n de tipo booleano");
             case 'M': case 'm':
                throw new SyntaxErrorException("Error se esperaba una"
                                                  +" expresi�n de tipo matriz");
             default:
                throw new SyntaxErrorException("Error tipos no coincidentes.");
          }
       }
    }

/*** Evalua una expresi�n en la que interviene un identificador      **********/
/*** Par�metros: fichProg-> nombre del fichero conteniendo el c�digo **********/
/***             parametros-> lista de los par�metros reales         **********/
/*** Devuelve: el valor resultado de evaluar la expresi�n            **********/
    variable valID() throws SyntaxErrorException
    {
       entrada lexem_aux;
       lexem_aux=lexema_actual;
       match(ctes_eval.ID);
       if (lexema_actual.comp_lex=='(') { // llamada a funci�n o programa
          String s=lexem_aux.objeto.nombre+".fjcn";
          match('(');
          if (lexema_actual.comp_lex==')') {
             match(')');
             return exec(s,null);
          } else {
             Vector v=lista_expresiones_condicionales();
             match(')');
             return exec(s,v);
          }
       }
       if (lexem_aux.objeto.tipo==' ') {
           throw new SyntaxErrorException("variable Inexistente.");
       }
       if (lexema_actual.comp_lex=='[') { // acceso a matriz
          variable m=refMatriz(lexem_aux);
          if (m.tipo=='S' && lexema_actual.comp_lex=='[') {
             match('[');
             variable r=expr_cond();
             match(']');
             compruebaTipo('D',r);
             return new variable(m.charAt(r.intValue()));
          }
          return m;
      }
      return lexem_aux.objeto;
    }

/*** Evalua una expresi�n de referencia a un elemento de una matriz  **********/
/*** Par�metros: v-> entrada de la matriz en la tabla de s�mbolos    **********/
/*** Devuelve: el elemento referenciado                              **********/
    variable refMatriz(entrada v) throws SyntaxErrorException
    {
       entrada e=tabla_simbolos.busca(v);
       variable r;
       Vector indice;
       String nombre="";

       if (e==null) {
          throw new SyntaxErrorException("variable inexistente0 '"+
                                                           v.objeto.nombre+"'");
       }
       r=e.objeto;
       nombre=r.nombre;
       while (lexema_actual.comp_lex=='[' && r.tipo=='M') {
          match('[');
          indice=lista_expresiones_aritmeticas();
          match(']');
          r=((miArray) r.valor).elemento(indice);
       }
       if (lexema_actual.comp_lex=='[' && (r.tipo!='S')) {
          throw new SyntaxErrorException("op [] s�lo aplicable a matriz"+
                                                                   " o string");
       }
       r.nombre=nombre;
       return r;
    }

/*** Devuelve la �ltima linea le�da de la entrada                    **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: la �ltima l�nea le�da de la entrada                   **********/
    String ultimaLinea()
    {
       int principio=0, fin=0;

       if (chr_actual>comienzoLineaActual) {
          principio=comienzoLineaActual;
          fin=chr_actual;
          if (chr_actual<programa_a_ejecutar.length()) {
             fin=chr_actual++;
          } else {
             chr_actual=programa_a_ejecutar.length();
          }
       } else {//    if (comienzoLineaAnterior<comienzoLineaActual-1) {
          principio=comienzoLineaAnterior;
          fin=comienzoLineaActual-2;
       }
       return programa_a_ejecutar.substring(principio, fin);
    }

/*** Asigna un valor num�rico a una variable                         **********/
/*** Par�metros: id-> nombre de la variable                          **********/
/***             valor-> valor a asignar                             **********/
/*** Devuelve: nada                                                  **********/
    public void asigna(String id, double valor)
    {
       tabla_simbolos.asigna(id,valor);
    }

/*** Devuelve una referencia al valor num�rico de una variable       **********/
/*** Par�metros: id-> nombre de la variable                          **********/
/*** Devuelve: referencia al valor num�rico de la variable           **********/
    public miDouble refMiDouble(String id) throws SyntaxErrorException
    {
       variable v=tabla_simbolos.busca(id).objeto;
       if (v.tipo=='D') {
          return (miDouble) v.valor;
       } else {
          throw new SyntaxErrorException("variable no es de tipo n�mero");
       }
    }

/******************************************************************************/
/********** C�ulo Simb�lico: **************************************************/
/********** Evaluaci�n de la derivada de una Expresi�n en la entrada **********/
/******************************************************************************/
/******************************************************************************/

/*** Calcula la derivada de una funci�n matem�tica en cadena la      **********/
/***     cadena de entrada                                           **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: la derivada de dicha  evaluar la expresi�n            **********/
    public variable derivada() throws SyntaxErrorException
    {
       int l=lexema_actual.comp_lex;
       int nSima=1;
       variable res;

       match(l);
       match('(');
       res=expr_cond();
       String _err=new String();
       if (res.tipo!='S' && res.tipo!='D') {
          throw new SyntaxErrorException("se esperaba cadena o n�mero");
       }
       if (res.tipo=='D') {
          nSima=res.intValue();
          match(',');
          res=expr_cond();
          match(')');
          compruebaTipo('S', res);
          dx="X";
          derivada=res.toString();
       } else { // res.tipo=='S'
          dx=res.toString();
          if (lexema_actual.comp_lex==')') {
             match(')');
             derivada=dx;
             dx="X";
          } else {
             match(',');
             dx=res.toString();
             res=expr_cond();
             match(')');
             compruebaTipo('S', res);
             derivada=res.toString();
          }
       }
       for(int i=1; i<=nSima; i++) {
          derivada(dx, simplifica_expresion(derivada));
       }
       if (l==ctes_eval.DERIVADA) {
          Vector _r=new Vector();
          _r.addElement(new variable("",'S',expresion));
          _r.addElement(new variable("",'S',new miString(derivada)));
          _r.addElement(new variable("",'S',
                                 new miString(simplifica_expresion(derivada))));
          res=new variable(new array(_r));
       } else {
          res=new variable("",'S',new miString(simplifica_expresion(derivada)));
       }
       return res;
    }

/*** Calcula la derivada de una funci�n matem�tica respecto de una   **********/
/***     variable matem�tica                                         **********/
/*** Par�metros: dx-> la variable matem�tica respecto a la que se    **********/
/***                  deriva                                         **********/
/***             expresion-> la funci�n matem�tica a derivar         **********/
/*** Devuelve: la derivada de dicha  evaluar la expresi�n            **********/
    void derivada(String dx, String expresion) throws SyntaxErrorException
    {

        salva_entorno();
        inicializa_entorno();

        this.dx=dx;
        programa_a_ejecutar=expresion;
        try {
           siguienteToken();
           dexpr();
           match(ctes_eval.EOF);
        } catch (SyntaxErrorException ex) {
           throw new SyntaxErrorException(ex.getMessage());
        } finally {
           restaura_entorno();
        }
    }

/*** Eval�a la expresi�n de la entrada calculando su derivada        **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void dexpr() throws SyntaxErrorException
    {
        String e=new String(""),d=new String("");
        entrada lexem_aux;

        switch (lexema_actual.comp_lex) {
            case '-':
                match('-');
                dterm();
                e+="-"+expresion;
                d+="-("+derivada+")";
                break;
            case '+':
                match('+');
            default:
                dterm();
                e+=expresion;
                d+=derivada;
        }
        while(lexema_actual.comp_lex=='+' || lexema_actual.comp_lex=='-') {
            lexem_aux=new entrada(lexema_actual);
            match(lexema_actual.comp_lex);
            dterm();
            if (lexem_aux.comp_lex=='+') {
                e=simplifica_suma(e,expresion);
                d=simplifica_suma(d,derivada);
            } else {
                e=simplifica_resta(e,expresion);
                d=simplifica_resta(d,derivada);
            }
    	}
    	expresion=e; // expresion contiene la expresi�n original
    	derivada=d;  // derivada contiene la derivada de la expresi�n original
    }

/*** Eval�a un t�rmino de la entrada calculando su derivada          **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void dterm() throws SyntaxErrorException
    {
        String e=new String(""),d=new String(""),
               r1=new String(""),r2=new String("");
        int op;

        dfactor();
        e+=expresion;
        d+=derivada;
        while (lexema_actual.comp_lex=='*' || lexema_actual.comp_lex=='/') {
           op=lexema_actual.comp_lex;
           match(lexema_actual.comp_lex);
               dfactor();
           switch (op) {
                  case '*':
                     r1=simplifica_producto("("+d+")",expresion);
                     r2=simplifica_producto(e,"("+derivada+")");
                     d=simplifica_suma(r1,r2);
                     e=e+'*'+expresion;
                 break;
                  case '/':
                     r1=simplifica_producto(d,expresion);
                     r2=simplifica_producto(e,derivada);
                     d=simplifica_division("("+simplifica_resta(r1,r2)+")",
                                    "("+simplifica_potencia(expresion,"2")+")");
                     e=e+'/'+expresion;
                     break;
          }
       }
       expresion=e; // expresi�n contiene el t�rmino
       derivada=d;  // derivada contiene la derivada del t�rmino
    }

/*** Eval�a un factor de la entrada calculando su derivada           **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void dfactor() throws SyntaxErrorException
    {
        String e=new String(""), d=new String("");

        dpotencia();
        e+=expresion;
        d+=derivada;
        while (lexema_actual.comp_lex=='^') {
            match(lexema_actual.comp_lex);
            if (indiceDe(expresion,dx)>=0) {
                dpotencia();
                if (esNumero(expresion)) {
                    d=simplifica_producto(simplifica_potencia(expresion+"*"+e,
                                miDouble.toString(valor_doble(expresion)-1)),d);
                } else {
                    d=simplifica_producto(simplifica_potencia(expresion+"*"+e,
                                "("+expresion+"-1)"),d);
                }
            } else {
                dpotencia();
                d=simplifica_producto(simplifica_ln(e),
                   simplifica_producto(simplifica_potencia(e,expresion),
                                       derivada));
            }
            e+='^'+expresion;
        }
        expresion=e;
        derivada=d;
    }

/*** Eval�a una potencia de la entrada calculando su derivada        **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void dpotencia() throws SyntaxErrorException
    {
        String r;

        switch(lexema_actual.comp_lex) {
          case '(':
             match('(');
             dexpr();
             expresion="("+expresion+")";
             derivada="("+derivada+")";
             match(')');
             break;
          case ctes_eval.PI: case ctes_eval.E:
             expresion=lexema_actual.objeto.nombre;
             derivada="0";
             match(lexema_actual.comp_lex);
             break;
          case ctes_eval.NUM:
             expresion=(lexema_actual.objeto).toString();
             derivada="0";
             match(ctes_eval.NUM);
             break;
          case ctes_eval.ID:
             if (dx.equalsIgnoreCase(lexema_actual.objeto.nombre)) {
                 derivada="1";
             } else {
                 derivada="0";
             }
             expresion=lexema_actual.objeto.nombre;
             match(ctes_eval.ID);
             break;
          case ctes_eval.COS:
             match(ctes_eval.COS);
             dpotencia();
             derivada="("+simplifica_producto("-sen "+expresion,derivada)+")";
             expresion="cos"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.ACOS:
             match(ctes_eval.ACOS);
             dpotencia();
             derivada=simplifica_producto(simplifica_division("-1",
                      simplifica_raiz("("+simplifica_resta("1",
                      simplifica_potencia(expresion,"2")))+")"),
                      derivada);
             expresion="acos"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.SEN:
             match(ctes_eval.SEN);
             dpotencia();
             derivada="("+simplifica_producto("cos "+expresion,derivada)+")";
             expresion="sen"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.ASEN:
             match(ctes_eval.ASEN);
             dpotencia();
             derivada=simplifica_producto(simplifica_division("1",
                      simplifica_raiz("("+simplifica_resta("1",
                      simplifica_potencia(expresion,"2")))+")"),
                      derivada);
             expresion="asen"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.TAN:
             match(ctes_eval.TAN);
             dpotencia();
             derivada=simplifica_producto(simplifica_division("1",
                      simplifica_potencia("cos"+necesitaEspacio(expresion)+
                                          expresion,"2")),derivada);
                 expresion="tan"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.ATAN:
             match(ctes_eval.ATAN);
             dpotencia();
             derivada=simplifica_producto(simplifica_division("1",
                      "("+simplifica_suma("1",simplifica_potencia(expresion,"2")
                     +")")),derivada);
                 expresion="atan"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.EXP:
             match(ctes_eval.EXP);
             dpotencia();
             derivada=simplifica_producto(simplifica_exponencial(expresion),
                                                                      derivada);
             expresion="exp"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.LN:
             match(ctes_eval.LN);
             dpotencia();
             derivada=simplifica_producto(simplifica_division("1",expresion),
                                                                      derivada);
             expresion="ln"+necesitaEspacio(expresion)+expresion;
             break;
          case ctes_eval.LOG:
             match(ctes_eval.LOG);
             match('(');
             dexpr();
             if (!igualAcero(derivada)) {
                throw new SyntaxErrorException("Expresi�n no se puede derivar");
             }
             r=expresion;
             match(',');
             dexpr();
             match(')');
             derivada=simplifica_producto(simplifica_division("1","("+
                      simplifica_producto("ln"+necesitaEspacio(r)+r,
                      "("+expresion+")"))+")","("+derivada+")");
             expresion="log("+r+","+expresion+")";
             break;
         case ctes_eval.SQRT:
             match(ctes_eval.SQRT);
             dpotencia();
             derivada=simplifica_producto(simplifica_division("1",
                        "(2*sqrt"+necesitaEspacio(expresion)+expresion+")"),
                                                                      derivada);
             expresion="sqrt"+necesitaEspacio(expresion)+expresion;
             break;
         case ctes_eval.SENH:
            match(ctes_eval.SENH);
            dpotencia();
            derivada=simplifica_producto("cosh"+necesitaEspacio(expresion)+
                                                            expresion,derivada);
            expresion="senh"+necesitaEspacio(expresion)+expresion;
            break;
         case ctes_eval.COSH:
            match(ctes_eval.COSH);
            dpotencia();
            derivada=simplifica_producto("senh"+necesitaEspacio(expresion)+
                                                            expresion,derivada);
            expresion="cosh"+necesitaEspacio(expresion)+expresion;
            break;
         case ctes_eval.TANH:
            match(ctes_eval.TANH);
            dpotencia();
            derivada=simplifica_producto("("+simplifica_resta("1",
                      simplifica_potencia("tanh"+
                      necesitaEspacio(expresion)+expresion,"2"))+")",derivada);
            expresion="tanh"+necesitaEspacio(expresion)+expresion;
            break;
          default:
             if (ctes_eval.esTokenValido(lexema_actual.comp_lex)) {
                if (ctes_eval.esOpAritm(ultimo_lexema.comp_lex)) {
                   if (ultimo_lexema.comp_lex=='+' ||
                                                  ultimo_lexema.comp_lex=='-') {
                      throw new SyntaxErrorException("se esperaba una"
                                                                +" expresi�n.");
                   } else {
                      throw new SyntaxErrorException("se esperaba una"
                                               +" expresi�n de tipo num�rico.");
                   }
                }
                throw new SyntaxErrorException("token no esperado.");
             } else {
                throw new SyntaxErrorException("token no identificado.");
             }
       }
    }


/******************************************************************************/
/**************** C�lculo simb�lico: ******************************************/
/**************** Simplificaci�n de Expresiones Matem�ticas  ******************/
/******************************************************************************/
/******************************************************************************/

/*** Simplica una expresi�n matem�tica                               **********/
/*** Par�metros: e-> expresi�n a simplificar                         **********/
/*** Devuelve: la expresi�n simplificada                             **********/
    public String simplifica_expresion(String e) throws SyntaxErrorException
    {
        String r=new String();
        arbol_binario a;

        try {
            a=arbol_expr(e);
            simplifica(a);
            r=arbol_a_cadena(a);
        } catch (SyntaxErrorException ex) {
            throw new SyntaxErrorException(ex.getMessage()+
                                                     e.substring(0,chr_actual));
        }
        return r;
    }

/*** Simplica una expresi�n matem�tica representada por un �rbol     **********/
/***     sint�ctico                                                  **********/
/*** Par�metros: e-> expresi�n a simplificar                         **********/
/*** Devuelve: nada                                                  **********/
    void simplifica(arbol_binario a) throws SyntaxErrorException
    {
        if (a!=null) {
            simplifica(a.hijo_izda);
            simplifica(a.hijo_dcha);
            if (reglas_aritmeticas(a)) {
               simplifica(a);
               return;
            }
            if (a.hijo_izda!=null && a.hijo_dcha!=null) {
                arbol_binario p=a, c=null;
                while (p.hijo_izda.hijo_izda!=null &&
                                ctes_eval.prioridad(a.lex.comp_lex)==
                                ctes_eval.prioridad(p.hijo_izda.lex.comp_lex)){
                    c=acumula_dcha_dcha(a,p.hijo_izda);
                    if (reglas_aritmeticas(c)) {
                        a.hijo_dcha.asigna(c);
                        p.hijo_izda=p.hijo_izda.hijo_izda;
                        simplifica(a);
                        return;
                    }
                    p=p.hijo_izda;
                }
                c=acumula_izda_dcha(p,a);
                if (reglas_aritmeticas(c)) {
                    p.hijo_izda.asigna(c);
                    a.sustituyePorHijoIzda();
                    simplifica(a);
                    return;
                }
            }
        }
    }

/*** Comprueba sin a la expresi�n de un �rbol sint�ctico que         **********/
/***     representa a una expresi�n matem�tica se le puede aplicar   **********/
/***     alguna regla de simplificaci�n y en caso afirmativo aplica  **********/
/***     dicha regla al �rbol sint�tico                              **********/
/*** Par�metros: e-> expresi�n a simplificar                         **********/
/*** Devuelve: nada                                                  **********/
    public boolean reglas_aritmeticas(arbol_binario a)
                                                     throws SyntaxErrorException
    {
        if (a!=null) {
            if (a.hijo_dcha!=null && a.lex.comp_lex!=ctes_eval.MENOS_UNARIO &&
                a.hijo_dcha.lex.comp_lex==ctes_eval.NUM) {
                if (a.hijo_izda==null ||
                                      a.hijo_izda.lex.comp_lex==ctes_eval.NUM) {
                    double d=expresion_aritmetica(a);
                    if (d<0) {
                        a.lex.comp_lex=ctes_eval.MENOS_UNARIO;
                        a.hijo_izda=null;
                        a.hijo_dcha=new arbol_binario(ctes_eval.NUM);
                        a.hijo_dcha.lex.objeto=new variable(-d);
                    } else {
                        a.lex.comp_lex=ctes_eval.NUM;
                        a.lex.objeto=new variable(d);
                        a.hijo_izda=a.hijo_dcha=null;
                    }
                    return true;
                }
            }
            switch (a.lex.comp_lex) {
                case ctes_eval.MENOS_UNARIO:
                    if (regla_aritmetica("-$#0","$#0",a)
                     || regla_aritmetica("-(y+z)","-y-z",a)
                     || regla_aritmetica("-(y-z)","z-y",a)
                     || regla_aritmetica("-(-x)","x",a)) {
                        return true;
                    }
                    break;
                case '+':
                    if (a.hijo_izda.lex.comp_lex=='*' &&
                        a.hijo_dcha.lex.comp_lex=='*') {
                        if (regla_aritmetica("2*x+3*x","(2+3)*x",a)) {
                           return true;
                        }
                    } else if (a.hijo_izda.lex.comp_lex=='/' &&
                               a.hijo_dcha.lex.comp_lex=='/' ) {
                        if (regla_aritmetica("2*x/y+3*x/y","(2+3)*x/y",a)
                           || regla_aritmetica("x/y+3*x/y","($#1+3)*x/y",a)
                           || regla_aritmetica("3*x/y+x/y","(3+$#1)*x/y",a)) {
                           return true;
                        }
                    } else if (a.hijo_izda.lex.comp_lex=='*') {
                        if (regla_aritmetica("3*x+x","(3+$#1)*x",a)) {
                           return true;
                        }
                    } else if (a.hijo_dcha.lex.comp_lex=='*') {
                        if (regla_aritmetica("x+3*x","(3+$#1)*x",a)) {
                         return true;
                        }
                    }
                    if (regla_aritmetica("$#0+x","x",a)
                     || regla_aritmetica("(sen x)^2+(cos x)^2", "$#1", a)
                     || regla_aritmetica("(cos x)^2+(sen x)^2", "$#1", a)
                     || regla_aritmetica("x+$#0","x",a)
                     || regla_aritmetica("x+x","$#2*x",a)
                     || regla_aritmetica("x+(y+z)","x+y+z",a)
                     || regla_aritmetica("x+(y-z)","x+y-z",a)
                     || regla_aritmetica("-y+z","z-y",a)
                     || regla_aritmetica("x+(-z)","x-z",a)) {
                     return true;
                    }
                    break;
                case '-':
                    if (a.hijo_izda.lex.comp_lex=='*' &&
                        a.hijo_dcha.lex.comp_lex=='*') {
                        if (regla_aritmetica("2*x-3*x","(2-3)*x",a)) {
                         return true;
                        }
                    } else if (a.hijo_izda.lex.comp_lex=='/' &&
                               a.hijo_dcha.lex.comp_lex=='/') {
                        if( regla_aritmetica("2*x/y-3*x/y","(2-3)*x/y",a)
                        || regla_aritmetica("x/y-3*x/y","($#1-3)*x/y",a)
                        || regla_aritmetica("3*x/y-x/y", "(3-$#1)*x/y",a)) {
                           return true;
                        }
                    } else if (a.hijo_izda.lex.comp_lex=='*') {
                        if (regla_aritmetica("3*x-x","(3-$#1)*x",a)) {
                         return true;
                        }
                    } else if (a.hijo_dcha.lex.comp_lex=='*') {
                        if (regla_aritmetica("x-3*x","($#1-3)*x",a)
                         || regla_aritmetica("-x-3*x","-($#1+3)*x",a)) {
                         return true;
                        }
                    }
                    if (regla_aritmetica("x-$#0","x",a)
                     || regla_aritmetica("$#0-x","-x",a)
                     || regla_aritmetica("x-x","$#0",a)
                     || regla_aritmetica("-x-x","-$#2*x",a)
                     || regla_aritmetica("x-(y+z)","x-y-z",a)
                     || regla_aritmetica("x-(y-z)","x-y+z",a)
                     || regla_aritmetica("x-(-z)","x+z",a)
                     || regla_aritmetica("-x/y-3*x/y","-($#1+3)*x",a)) {
                     return true;
                    }
                    break;
                 case '*':
                     if (a.hijo_izda.lex.comp_lex=='^' &&
                         a.hijo_dcha.lex.comp_lex=='^') {
                         if (regla_aritmetica("x^2*x^3","x^(2+3)",a)) {
                            return true;
                         }
                     } else if (a.hijo_izda.lex.comp_lex=='^') {
                         if (regla_aritmetica("x^2*x","x^(2+$#1)",a)) {
                             return true;
                         }
                     } else if (a.hijo_dcha.lex.comp_lex=='^') {
                         if (regla_aritmetica("x*x^2","x^(2+$#1)",a)) {
                             return true;
                         }
                     }
                     if (regla_aritmetica("$#0*x","$#0",a)
                      || regla_aritmetica("x*$#0","$#0",a)
                      || regla_aritmetica("x*x","x^$#2",a)
                      || regla_aritmetica("$#1*x","x",a)
                      || regla_aritmetica("x*$#1","x",a)
                      || regla_aritmetica("(x+y)*z","x*z+y*z",a)
                      || regla_aritmetica("(x-y)*z","x*z-y*z",a)
                      || regla_aritmetica("z*(x+y)","z*x+z*y",a)
                      || regla_aritmetica("z*(x-y)","z*x-z*y",a)
                      || regla_aritmetica("x*(-z)","-x*z",a)
                      || regla_aritmetica("(-y)*z","-y*z",a)
                      || regla_aritmetica("x*(y/z)","x*y/z",a)
                      || regla_aritmetica("x*2","2*x",a)
                      || regla_aritmetica("x*(y*z)","x*y*z",a)
                      || regla_aritmetica("2/x*y","2*y/x",a)) {
                      return true;
                     }
                     break;
                case '/':
                    if (a.hijo_izda.lex.comp_lex=='^' &&
                        a.hijo_dcha.lex.comp_lex=='^') {
                        if (regla_aritmetica("x^2/x^3","x^(2-3)",a)) {
                            return true;
                        }
                    } else if (a.hijo_izda.lex.comp_lex=='^') {
                        if (regla_aritmetica("x^2/x","x^(2-$#1)",a)) {
                            return true;
                        }
                    } else if (a.hijo_dcha.lex.comp_lex=='^') {
                        if (regla_aritmetica("x/x^2","$#1/x^(2-$#1)",a)
                         || regla_aritmetica("$#1/x^(-y)","x^y",a)) {
                            return true;
                        }
                    }
                    if (regla_aritmetica("$#0/x","$#0",a)
                     || regla_aritmetica("x/$#1","x",a)
                     || regla_aritmetica("x/x","$#1",a)
                     || regla_aritmetica("(x+y)/z","x/z+y/z",a)
                     || regla_aritmetica("(x-y)/z","x/z-y/z",a)
                     || regla_aritmetica("x/(-y)","-x/y",a)
                     || regla_aritmetica("(-x)/y","-x/y",a)
                     || regla_aritmetica("x/(y*z)","x/y/z",a)
                     || regla_aritmetica("x/(y/z)","x*z/y",a)) {
                       return true;
                    }
                    break;
                case '^':
                    if (regla_aritmetica("x^$#0","$#1",a)
                     || regla_aritmetica("$#0^x","$#0",a)
                     || regla_aritmetica("x^$#1","x",a)
                     || regla_aritmetica("$#1^x","$#1",a)
                     || regla_aritmetica("x^2^3","x^(2*3)",a)
                     || regla_aritmetica("x^(-y)","$#1/x^y",a)
                     || regla_aritmetica("(x*y)^z","x^z*y^z",a)
                     || regla_aritmetica("(x/y)^z","x^z/y^z",a)) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

/*** Forma un �rbol a partir de dos ramas derechas de otro �rbol     **********/
/*** Par�metros: s,b-> las 2 ramas derechas mencionadas              **********/
/*** Devuelve: el �rbol sint�ctico asociado a la expresi�n formada   **********/
/***    por dichas ramas                                             **********/
    arbol_binario acumula_dcha_dcha(arbol_binario a, arbol_binario b)
    {
        if (a.lex.comp_lex=='+') {
            if (b.lex.comp_lex=='+') {
                return new arbol_binario('+',a.hijo_dcha.copia(),b.hijo_dcha);
            } else { // pb.hijo_izda.lex.comp_lex=='-'
                     // || pb.hijo_izda.lex.comp_lex==ctes_eval.MENOS_UNARIO
                return new arbol_binario('-',a.hijo_dcha.copia(),b.hijo_dcha);
            }
        } else if (a.lex.comp_lex=='-') {
            if (b.lex.comp_lex=='+') {
                return new arbol_binario('-',a.hijo_dcha.copia(),b.hijo_dcha);
            } else { // pb.hijo_izda.lex.comp_lex=='-'
                     // || pb.hijo_izda.lex.comp_lex==ctes_eval.MENOS_UNARIO
                return new arbol_binario('+',a.hijo_dcha.copia(),b.hijo_dcha);
            }
        } else if (a.lex.comp_lex=='*') {
            if (b.lex.comp_lex=='*') {
                return new arbol_binario('*',a.hijo_dcha.copia(),b.hijo_dcha);
            } else { //pb.hijo_izda.lex.comp_lex=='/'
                return new arbol_binario('/',a.hijo_dcha.copia(),b.hijo_dcha);
            }
        } else if (a.lex.comp_lex=='/') {
            if (b.lex.comp_lex=='/') {
                return new arbol_binario('*',a.hijo_dcha.copia(),b.hijo_dcha);
            } else {
                return new arbol_binario('/',a.hijo_dcha.copia(),b.hijo_dcha);
            }
        }
        return null;
    }

/*** Forma un �rbol a partir de una rama izquierda y otra rama       **********/
/***     derecha de otro �rbol                                       **********/
/*** Par�metros: s,b-> las 2 ramas derechas mencionadas              **********/
/*** Devuelve: el �rbol sint�ctico asociado a la expresi�n formada   **********/
/***    por dichas ramas                                             **********/
    arbol_binario acumula_izda_dcha(arbol_binario a, arbol_binario b)
    {
       return new arbol_binario(b.lex.comp_lex, a.hijo_izda, b.hijo_dcha);
    }

/******************************************************************************/
/******************************************************************************/
/********************* lenguaje de patrones ***********************************/
/******************************************************************************/
/******************************************************************************/


/*** Comprueba que un patr�n encaja en otro y en caso afirmativo se  **********/
/***     le aplica la transformaci�n que indica otro �rbol           **********/
/*** Par�metros: p-> cadena que representa al patr�n con el que se   **********/
/***                 ha de comparar para ver si se aplica la         **********/
/***                 transformaci�n                                  **********/
/***             a-> cadena que representa la transformaci�n que se  **********/
/***                 aplicar� sobre pr en caso de que pr encaje      **********/
/***                 dentro del patr�n representado por p            **********/
/***             pr->patr�n sobre el que se aplicar� la              **********/
/***                 transformaci�n caso que encaje en p             **********/
/*** Devuelve: la cadena que representa al �rbol sint�ctico tras     **********/
/***     la transformaci�n (si la hubo)                              **********/
    public String regla_aritmetica(String p, String a, String pr)
                                                     throws SyntaxErrorException
    {
        arbol_binario apr=new arbol_binario();
        try {
            apr=arbol_expr(pr);
        } catch (SyntaxErrorException e) {
            throw new SyntaxErrorException("Error en la expresi�n a aplicar"+
                                                                   " la regla");
        }
        regla_aritmetica(p,a,apr);
        return arbol_a_cadena(apr);
    }


/*** Comprueba que un patr�n encaja en otro y en caso afirmativo se  **********/
/***     le aplica la transformaci�n que indica otro �rbol           **********/
/*** Par�metros: p-> cadena que representa al patr�n con el que se   **********/
/***                 ha de comparar para ver si se aplica la         **********/
/***                 transformaci�n                                  **********/
/***             a-> cadena que representa la transformaci�n que se  **********/
/***                 aplicar� sobre pr en caso de que pr encaje      **********/
/***                 dentro del patr�n representado por p            **********/
/***             pr->�rbol sint�ctico al que se aplicar� la          **********/
/***                 transformaci�n caso que encaje en p             **********/
/*** Devuelve: true si se aplic� transformaci�n false en caso        **********/
/***     contrario                                                   **********/
    public boolean regla_aritmetica(String p, String a, arbol_binario pr)
                                                     throws SyntaxErrorException
    {
        if (p!=null && a!=null && pr!=null && p.length()>0) {
            Vector vp=new Vector(), vpr=new Vector();
            arbol_binario ap=arbol_expr(p);
            if (comprueba_patrones(ap,pr,vp,vpr)) {
                if (comprueba_igualdades_patrones(vp,vpr)) {
                    arbol_binario aa=arbol_expr(a);
                    sustituye_patrones(aa,vp,vpr);
                    pr.lex=aa.lex;
                    pr.hijo_izda=aa.hijo_izda;
                    pr.hijo_dcha=aa.hijo_dcha;
                    return true;
                }
            }
        }
        return false;
    }

/*** Sustiuye patrones en un �rbol sint�ctico de una expresi�n       **********/
/*** Par�metros: aa-> �rbol sint�ctico de la expresi�n               **********/
/***             vp-> lista de patrones que ser�n sustituidos por los**********/
/***                  que ocupen la misma posici�n sobre la lista    **********/
/***                  vpr                                            **********/
/***             vpr->lista de patrones que sustituir�n a los que    **********/
/***                  ocupen la misma posici�n sobre la lista vp     **********/
/*** Devuelve: nada                                                  **********/
    void sustituye_patrones(arbol_binario aa,Vector vp,Vector vpr)
                                                     throws SyntaxErrorException
    {
        if (aa!=null) {
            if (!ctes_eval.esOpAritm(aa.lex.comp_lex) &&
                                               aa.lex.comp_lex!=ctes_eval.CTE) {
                arbol_binario aux;
                aux=(arbol_binario) vpr.elementAt(busca(vp,aa));
                aa.lex=aux.lex;
                aa.hijo_izda=aux.hijo_izda;
                aa.hijo_dcha=aux.hijo_dcha;
            } else {
                if (aa.lex.comp_lex==ctes_eval.CTE) {
                    aa.lex.comp_lex=ctes_eval.NUM;
                }
                sustituye_patrones(aa.hijo_izda,vp,vpr);
                sustituye_patrones(aa.hijo_dcha,vp,vpr);
            }
        }
    }

/*** Busca un �rbol sint�ctico de una expresi�n en una lista         **********/
/*** Par�metros: vp-> lista de �rboles sint�cticos                   **********/
/***             aa-> �rbol sint�ctico a buscar                      **********/
/*** Devuelve: la posici�n del �rbol dentro de la lista              **********/
    int busca(Vector vp, arbol_binario aa) throws SyntaxErrorException
    {
        int i=0; arbol_binario aux;
        while (i<vp.size()) {
            aux=(arbol_binario) vp.elementAt(i);
            if ((aux.lex.comp_lex==aa.lex.comp_lex) &&
                 ((aux.lex.comp_lex==ctes_eval.ID &&
                   aux.lex.objeto.nombre.equalsIgnoreCase(aa.lex.objeto.nombre))
                   || ((aux.lex.comp_lex==ctes_eval.NUM ||
                                                aux.lex.comp_lex==ctes_eval.CTE)
                        && aux.lex.objeto.doubleValue()==
                                                aa.lex.objeto.doubleValue()))) {
                return i;
            }
            i++;
        }
        throw new SyntaxErrorException("Error token en patr�n no concuerda"
                                                       +" con token en acci�n");
    }

/*** Comprueba que los patrones representados por p1 y p2 son        **********/
/***     compatibles y devuelve en r1 y r2 la lista de �rboles       **********/
/***     representando a los tokens significativos de un patr�n      **********/
/*** Par�metros: p1-> �rbol representando primer patr�n              **********/
/***             p2-> �rbol representando al segundo patr�n          **********/
/*** Devuelve: un booleano que indica si los patrones son            **********/
/***     entre s�                                                    **********/
    boolean comprueba_patrones(arbol_binario p1, arbol_binario p2,
                               Vector r1, Vector r2)
    {
        if (p1==p2 && p2==null) {
            return true;
        } else if (p1==null && p2!=null) {
            return true;
        } else if (p1!=null && p2==null) {
            return false;
        } else { // p1!=null && p2!=null
            if ((p1.lex.comp_lex==ctes_eval.CTE &&
              (p2.lex.comp_lex==ctes_eval.CTE || p2.lex.comp_lex==ctes_eval.NUM)
                && p1.lex.objeto.doubleValue()==p2.lex.objeto.doubleValue())
                || (p1.lex.comp_lex!=ctes_eval.CTE &&
                    p1.lex.comp_lex==p2.lex.comp_lex)) {
                if (!ctes_eval.esOpAritm(p1.lex.comp_lex)) {
                    r1.addElement((Object) new arbol_binario(p1.lex));
                    r2.addElement((Object) new arbol_binario(p2.lex));
                }
            } else if (p1.lex.comp_lex==ctes_eval.ID) {
                r1.addElement((Object) p1.copia());
                r2.addElement((Object) p2.copia());
            } else {
                return false;
            }
            return comprueba_patrones(p1.hijo_izda,p2.hijo_izda,r1,r2)
                   && comprueba_patrones(p1.hijo_dcha,p2.hijo_dcha,r1,r2);
        }
    }

/*** Comprueba que las igualdades de patrones que se dan en la       **********/
/***     primera lista tambi�n se dan entre los patrones que ocupan  **********/
/***     los mismos lugares en la segunda lista                      **********/
/*** Par�metros: vp-> primera lista de patrones                      **********/
/***             vpr-> segunda lista de patrones                     **********/
/*** Devuelve: un booleano que indica si se dan las mismas igualdades**********/
/***     entre los patrones que ocupan la misma posici�n en ambas    **********/
/***     listas                                                      **********/
    boolean comprueba_igualdades_patrones(Vector vp, Vector vpr)
    {
        for(int i=0; i<vp.size()-1; i++) {
            for(int j=i+1; j<vp.size(); j++) {
                if (((arbol_binario) vp.elementAt(i)).igual(
                           (arbol_binario) vp.elementAt(j))) {
                    if (!(((arbol_binario) vpr.elementAt(i)).igual(
                                (arbol_binario) vpr.elementAt(j)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

/*** Convierte una cadena representando una expresi�n en el �rbol    **********/
/***     �rbol sint�ctico asociado a la misma                        **********/
/*** Par�metros: s-> expresi�n cuyo �rbol quiere obtenerse           **********/
/*** Devuelve: el �rbol sint�ctico asociado a dicha expresi�n        **********/
    public arbol_binario arbol_expr(String s) throws SyntaxErrorException
    {
        arbol_binario r=new arbol_binario();

        salva_entorno();
        inicializa_entorno();
        programa_a_ejecutar=s;
        try {
           siguienteToken();
           r=arbol_expr();
        } catch (SyntaxErrorException  ex) {
           throw new SyntaxErrorException(ex.getMessage());
        } finally {
           restaura_entorno();
        }
        return r;
    }

/*** Convierte una expresi�n matem�tica a su �rbol sint�ctico        **********/
/***     retir�ndola de la entrada                                   **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �rbol sint�ctico asociado a dicha expresi�n        **********/
    arbol_binario arbol_expr() throws SyntaxErrorException
    {
        arbol_binario r=new arbol_binario(),p;
        switch(lexema_actual.comp_lex) {
            case '-':
                match('-');
    	        r=new arbol_binario(ctes_eval.MENOS_UNARIO);
    	        r.hijo_dcha=arbol_term();
	            break;
            case '+':
                match('+');
            default:
                r=arbol_term();
        }
        while (lexema_actual.comp_lex=='+' || lexema_actual.comp_lex=='-') {
            p=new arbol_binario(lexema_actual);
            match(lexema_actual.comp_lex);
            p.hijo_izda=r;
            p.hijo_dcha=arbol_term();
            r=p;
        }
        return r;
    }

/*** Convierte un t�rmino de una  expresi�n matem�tica a su �rbol    **********/
/***     sint�ctico retir�ndola de la entrada                        **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �rbol sint�ctico asociado a dicho t�rmino          **********/
    arbol_binario arbol_term() throws SyntaxErrorException
    {
        arbol_binario r=new arbol_binario(),p;

        r=arbol_factor();
        while (lexema_actual.comp_lex=='*' || lexema_actual.comp_lex=='/') {
            p=new arbol_binario(lexema_actual);
            match(lexema_actual.comp_lex);
            p.hijo_izda=r;
            p.hijo_dcha=arbol_factor();
            r=p;
        }
        return r;
    }

/*** Convierte un f�ctor de una  expresi�n matem�tica a su �rbol     **********/
/***     sint�ctico retir�ndola de la entrada                        **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �rbol sint�ctico asociado a dicho factor           **********/
    arbol_binario arbol_factor() throws SyntaxErrorException
    {
        arbol_binario r=new arbol_binario(), p;

        r=arbol_potencia();
        while (lexema_actual.comp_lex=='^') {
            p=new arbol_binario(lexema_actual);
            match(lexema_actual.comp_lex);
            p.hijo_izda=r;
            p.hijo_dcha=arbol_potencia();
            r=p;
        }
        return r;
    }

/*** Convierte una potencia de una  expresi�n matem�tica a su �rbol  **********/
/***     sint�ctico retir�ndola de la entrada                        **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el �rbol sint�ctico asociado a dicha potencia         **********/
    arbol_binario arbol_potencia() throws SyntaxErrorException
    {
        arbol_binario r=new arbol_binario(), p;

        switch(lexema_actual.comp_lex) {
            case '(':
                match('(');
                r=arbol_expr();
                match(')');
                break;
            case ctes_eval.NUM: case ctes_eval.PI: case ctes_eval.E:
                r.lex=new entrada(lexema_actual.comp_lex,lexema_actual.objeto);
                match(lexema_actual.comp_lex);
                break;
            case ctes_eval.ID:
                r.lex=new entrada(ctes_eval.ID, lexema_actual.objeto);
                match(ctes_eval.ID);
                break;
            case ctes_eval.COS: case ctes_eval.ACOS: case ctes_eval.SEN:
            case ctes_eval.ASEN: case ctes_eval.TAN: case ctes_eval.ATAN:
            case ctes_eval.EXP: case ctes_eval.LN:
            case ctes_eval.SQRT: case ctes_eval.SENH: case ctes_eval.COSH:
            case ctes_eval.TANH:
                r=new arbol_binario(lexema_actual);
                match(lexema_actual.comp_lex);
                r.hijo_dcha=arbol_potencia();
                break;
            case ctes_eval.LOG:
                r=new arbol_binario(lexema_actual);
                match(ctes_eval.LOG);
                r.hijo_izda=arbol_expr();
                match(';');
                r.hijo_dcha=arbol_expr();
                match(')');
                break;
            case ctes_eval.DERIVADA: case ctes_eval.DERIV:
                variable res=expr_cond();
                r=arbol_expr(res.toString());
                break;
            case ctes_eval.CTE:
                r.lex=new entrada(ctes_eval.CTE, lexema_actual.objeto);
                match(ctes_eval.CTE);
                break;
            default:
                throw new SyntaxErrorException("Error de sintaxis");
        }
        return(r);
    }

/*** Convierte un �rbol sint�ctico de una expresi�n a la cadena      **********/
/***     que la representa                                           **********/
/*** Par�metros: a->�rbol a convertir                                **********/
/*** Devuelve: la cadena que representa al �rbol sint�ctico          **********/
    String arbol_a_cadena(arbol_binario a)
    {
        String r=new String(),rd=new String();
        if (a!=null) {
            if (a.hijo_izda!=null) {
                if (ctes_eval.prioridad(a.lex.comp_lex)>
                                ctes_eval.prioridad(a.hijo_izda.lex.comp_lex)) {
                    r="("+arbol_a_cadena(a.hijo_izda)+")";
                } else {
                    r=arbol_a_cadena(a.hijo_izda);
                }
            }
            if (a.hijo_dcha!=null) {
                if (ctes_eval.prioridad(a.lex.comp_lex)>=
                                   ctes_eval.prioridad(a.hijo_dcha.lex.comp_lex)
                          || a.hijo_dcha.lex.comp_lex==ctes_eval.MENOS_UNARIO) {
                    rd="("+arbol_a_cadena(a.hijo_dcha)+")";
                } else {
                    String aux=arbol_a_cadena(a.hijo_dcha);
                    if (aux.charAt(0)=='-') {
                        rd="("+aux+")";
                    } else {
                        rd=aux;
                    }
                }
            }
            if (a.lex.comp_lex==ctes_eval.MENOS_UNARIO) {
                r+='-';
            } else if (a.lex.objeto.nombre!=null &&
                                               a.lex.objeto.nombre.length()>0) {
                r+=a.lex.objeto.nombre;
                if (a.lex.comp_lex!=ctes_eval.ID) {
                    r+=necesitaEspacio(rd);
                }
            } else if (a.lex.objeto.valor!=null) {
                r+=a.lex.objeto.toString();
            } else {
                r+=(char) a.lex.comp_lex;
            }
        }
        r+=rd;
        return r;
    }

/*** Eval�a el valor de la expresi�n aritm�tica asociada a un �rbol  **********/
/*** Par�metros: a-> �rbol a evaluar                                 **********/
/*** Devuelve: el valor resultado de evaluar el �rbol sint�ctico     **********/
    public double expresion_aritmetica(arbol_binario a)
                                                     throws SyntaxErrorException
    {
       try {
          return _expresion_aritmetica(a);
       } catch (ArithmeticException ex) {
          if (ex.getMessage().equals("+")) {
             return Double.POSITIVE_INFINITY;
          } else if (ex.getMessage().equals("-")) {
             return Double.NEGATIVE_INFINITY;
          } else {
             return Double.NaN;
          }
       }
    }

/*** Eval�a el valor de la expresi�n aritm�tica asociada a un �rbol  **********/
/*** Par�metros: a-> �rbol a evaluar                                 **********/
/*** Devuelve: el valor resultado de evaluar el �rbol sint�ctico     **********/
    public double _expresion_aritmetica(arbol_binario a)
                                                     throws SyntaxErrorException
    {
        double d=0.0, d2=0.0;

        if (a!=null) {
            switch(a.lex.comp_lex) {
                case '+':
                    if (a.hijo_izda!=null) {
                        d=expresion_aritmetica(a.hijo_izda);
                    }
                    return d+expresion_aritmetica(a.hijo_dcha);
                case '-':
                    return expresion_aritmetica(a.hijo_izda)-
                                              expresion_aritmetica(a.hijo_dcha);
                case ctes_eval.MENOS_UNARIO:
                    return -expresion_aritmetica(a.hijo_dcha);
                case '*':
                    return expresion_aritmetica(a.hijo_izda)*
                                              expresion_aritmetica(a.hijo_dcha);
                case '/':
                    d=expresion_aritmetica(a.hijo_izda);
                    d2=expresion_aritmetica(a.hijo_dcha);
                    if (Math.abs(d2)<=0.000000000000001) {
                       if (d>=0.0) {
                          throw new ArithmeticException("+");
                       } else if (d<=0.0) {
                          throw new ArithmeticException("-");
                       } else {
                          throw new ArithmeticException("=");
                       }
                    }
                    return d/d2;
                case '^':
                    return Math.pow(expresion_aritmetica(a.hijo_izda),
                                             expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.SEN:
                    return Math.sin(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.SENH:
                    return senh(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.ASEN:
                    return Math.asin(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.COS:
                    return Math.cos(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.COSH:
                    return cosh(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.ACOS:
                    return Math.acos(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.TAN:
                    return Math.tan(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.TANH:
                    return tanh(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.ATAN:
                    return Math.atan(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.LN:
                    return Math.log(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.LOG:
                    return Math.log(expresion_aritmetica(a.hijo_dcha))/
                                    Math.log(expresion_aritmetica(a.hijo_izda));
                case ctes_eval.EXP:
                    return Math.exp(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.SQRT:
                    return Math.sqrt(expresion_aritmetica(a.hijo_dcha));
                case ctes_eval.PI:
                    return Math.PI;
                case ctes_eval.E:
                    return Math.E;
                case ctes_eval.NUM: case ctes_eval.ID: case ctes_eval.CTE:
                    return a.lex.objeto.doubleValue();
                default:
                   throw new SyntaxErrorException("al evaluar �rbol");
            }
        }
        return d;
    }

    boolean esCero(arbol_binario a)
    {
        return (a.lex.comp_lex==ctes_eval.NUM && a.lex.objeto.doubleValue()==0);
    }

    boolean esUno(arbol_binario a)
    {
        return (a.lex.comp_lex==ctes_eval.NUM && a.lex.objeto.doubleValue()==1);
    }




/*******************************************************************************
********************************************************************************
************************* Lectura de variables   *******************************
********************************************************************************
*******************************************************************************/

/*** Llamada por la instrucci�n de lectura del lenguaje para leer    **********/
/***     variables respetando que la expresi�n que se introduzca sea **********/
/***     del mismo tipo                                              **********/
/*** Par�metros: mensa-> mensaje a mostrar al usuario                **********/
/***             v-> variable que se leer� respetando su tipo        **********/
/*** Devuelve: nada                                                  **********/
    void leerVariable(String mensa, variable v) throws SyntaxErrorException
    {
       String tipo=nombreTipo(v.tipo);

       if (v==null || tipo==null) {
          throw new SyntaxErrorException("variable inexistente");
       }
       tipo="Lectura de variable tipo "+tipo;
       Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
       leerVar lv=new leerVar(tipo,true);
       lv.setLocation((d.width - lv.getSize().width) / 2,
                                          (d.height - lv.getSize().height) / 2);
       lv.setMensaje(mensa);
       while (true) {
          lv.show();
          try {
             variable r=expr_cond(lv.getValor());
             if (v.tipo=='S') {
                v.valor=new miString(r.toString());
                return;
             } else if (v.tipo==r.tipo) {
                v.valor=r.valor;
                return;
             } else {
                cuadro_mensaje("El valor debe ser de tipo "+nombreTipo(v.tipo),
                               "Mensaje de Error");
             }
          } catch (SyntaxErrorException ex) {
             cuadro_mensaje("Error la expresi�n introducida debe ser de tipo "+
                             nombreTipo(v.tipo),"Mensaje de Error");
          }
       }

    }

/*** Transforma el car�cter que representa el tipo de una variable   **********/
/***     en su nombre                                                **********/
/*** Par�metros: t-> car�cter que representa el tipo                 **********/
/*** Devuelve: el nombre del tipo                                    **********/
    public String nombreTipo(char t)
    {
       switch(t) {
          case 'S':
             return "CADENA";
          case 'D':
             return "N�MERO";
          case 'M':
             return "MATRIZ";
          case 'B':
             return "BOOLEANO";
          default:
             return null;
       }
    }

/*** Dibuja una ventana de mensaje                                   **********/
/*** Par�metros: s-> mensaje a mostrar dentro de la ventana          **********/
/***             tipo-> mensaje a mostrar en el t�tulo de la ventana **********/
/***                 que indicada el tipo de mensaje (error, info...)**********/
/*** Devuelve: la cadena que representa al �rbol sint�ctico          **********/
    public static void cuadro_mensaje(String s, String tipo)
    {
       JFrame frame=new JFrame();
       Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
       dialogo dlg=new dialogo(s,frame,tipo,true);
       dlg.setLocation((d.width - dlg.getSize().width) / 2,
                                         (d.height - dlg.getSize().height) / 2);
       dlg.show();
    }



/******************************************************************************/
/******************************************************************************/
/************     funciones de comunicaci�n con otros objetos   ***************/
/******************************************************************************/
/******************************************************************************/

/*** A�ade un objeto a la lista de elementos que ser�n notificados   **********/
/***     de alg�n evento, como p.ej. la creaci�n de una variable     **********/
/***     como hace la case CalcWindow con su objeto listaVariables   **********/
/***     que a�ade a la lista de notificados de su objeto evaluador  **********/
/***     para que cada vez que se crea o actualiza una variable se   **********/
/***     se actualice en la interfaz gr�fica de usuario de forma que **********/
/***     que este pueda ver los cambios en las variables de forma    **********/
/***     inst�ntane a la ejecuci�n de programas y/o comandos         **********/
/*** Par�metros: n-> referencia al objeto que ser� notificado        **********/
/*** Devuelve: nada                                                  **********/
    public void addNotificado(notificado n) {
       notificados.addElement((Object) n);
    }

/*** Fija el valor de la variable notificacion de forma que seg�n    **********/
/***     el valor que esta tenga se notificar� o no a los objetos    **********/
/***     de la lista de notificados de los eventos notificables      **********/
/*** Par�metros: b-> nuevo valor de notificacion                     **********/
/*** Devuelve: nada                                                  **********/
    public void setNotificacion(boolean b)
    {
       notificacion=b;
    }

/*** 'Evento' de notificaci�n a la lista de notificados de la        **********/
/***     creaci�n de una nueva variable                              **********/
/*** Par�metros: ve-> referencia a la nueva variable creada          **********/
/*** Devuelve: nada                                                  **********/
    void notifiNuevaVariable(variable ve)
    {
       variable vb=tabla_simbolos.busca(ve.nombre).objeto;
       if (vb!=null) {
          if (notificacion) { // esto enlentece mucho la ejecuci�n
             for(int i=0;i<notificados.size();i++) {
                ((notificado) notificados.elementAt(i)).nuevaVariable(vb.nombre,
                                                              vb.StringValue());
             }
          }
       }
    }
/*** Idem anterior sobrecargado                                      **********/
/*** Par�metros: e-> entrada de la tabla de s�mbolos que contiene    **********/
/***     la nueva variable creada                                    **********/
/*** Devuelve: nada                                                  **********/
    void notifiNuevaVariable(entrada e)
    {
       notifiNuevaVariable(e.objeto);
    }


/*** 'Evento' de notificaci�n de que se ha pasado a un nuevo entorno **********/
/***     o �mbito de ejecuci�n, p.ej. cuando se llama a una funci�n  **********/
/***     o programa de usuario, se inicia un nuevo entorno de        **********/
/***     variables locales                                           **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void notifiNuevoEntorno()
    {
       if (notificacion) {
          for(int i=0;i<notificados.size();i++) {
             ((notificado) notificados.elementAt(i)).nuevoEntorno();
          }
       }
    }

/*** 'Evento' de notificaci�n de que se ha salido del �ltimo entorno **********/
/***     o �mbito de ejecuci�n                                       **********/
/*** Par�metros: ninbuno                                             **********/
/*** Devuelve: nada                                                  **********/
    void notifiFinEntorno()
    {
       if (notificacion) {
          for(int i=0;i<notificados.size();i++) {
             ((notificado) notificados.elementAt(i)).finNuevoEntorno();
          }
       }
    }

/******************************************************************************/
/******************************************************************************/
/************     m�todos y funciones auxiliares                ***************/
/******************************************************************************/
/******************************************************************************/

/*** Devuelve el n�mero de s�mbolos que hay en la tabla de s�mbolos  **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: el n�mero total de s�mbolos que hay en la tabla de    **********/
/***     de s�mbolos                                                 **********/
    public int getNumSimbolos()
    {
       return tabla_simbolos.getNumSimbolos();
    }

/*** Devuelve el elemento i-�simo de la tabla de s�mbolos            **********/
/*** Par�metros: i-> posici�n del elemento a devolver                **********/
/*** Devuelve: la variable de la tabla de s�mbolos que ocupa la      **********/
/*** posici�n i                                                      **********/
    public variable getSimbolo(int i)
    {
       return tabla_simbolos.getSimbolo(i);
    }

/*** Devuelve el �ltimo token reconocido por el analizador l�xico    **********/
/***    de la entrada                                                **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    public int getUltimoToken()
    {
       return ultimoToken;
    }

/*** Abre un fichero en la lista de ficheros abiertos                **********/
/*** Par�metros: fichero-> fichero a abrir                           **********/
/*** Devuelve: booleano que indica si hubo o no �xito en apertura    **********/
    public boolean abrirFichero(String fichero)
    {
       return ficheros.abrir(fichero);
    }

/*** Devuelve el contenido de un fichero previamente abierto         **********/
/*** Par�metros: fichero-> nombre del fichero del que se quiere      **********/
/***     obtener el contenido                                        **********/
/*** Devuelve: el contenido del fichero en forma de una cadena       **********/
    public String getContenidoFichero(String fichero)
    {
       return ficheros.getContenido(fichero);
    }

/*** Salva el entorno de ejecuci�n actualmente en curso              **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void salva_entorno()
    {
        if (programa_a_ejecutar!=null) {
           programa_a_ejecutar_aux.addElement(new String(programa_a_ejecutar));
        } else {
           programa_a_ejecutar_aux.addElement(null);
        }
        if (lexema_actual!=null) {
           lexema_actual_aux.addElement(new entrada(lexema_actual));
        } else {
           lexema_actual_aux.addElement(null);
        }
        chr_actual_aux.addElement(new Integer(chr_actual));
        numLineas_aux.addElement(new Integer(numLineas));
        comienzoLineaAnterior_aux.addElement(new Integer(
                                                        comienzoLineaAnterior));
        comienzoLineaActual_aux.addElement(new Integer(comienzoLineaActual));
    }

/*** Restaura �ltimo entorno de ejecuci�n salvado                    **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void restaura_entorno()
    {
        programa_a_ejecutar=(String) programa_a_ejecutar_aux.elementAt(
                                              programa_a_ejecutar_aux.size()-1);
        programa_a_ejecutar_aux.removeElementAt(
                                              programa_a_ejecutar_aux.size()-1);
        lexema_actual=(entrada) lexema_actual_aux.elementAt(
                                                    lexema_actual_aux.size()-1);
        lexema_actual_aux.removeElementAt(lexema_actual_aux.size()-1);
        chr_actual=((Integer)chr_actual_aux.elementAt(
                                           chr_actual_aux.size()-1)).intValue();
        chr_actual_aux.removeElementAt(chr_actual_aux.size()-1);
        numLineas=((Integer) numLineas_aux.elementAt(
                                            numLineas_aux.size()-1)).intValue();
        comienzoLineaActual=((Integer) comienzoLineaActual_aux.elementAt(
                                  comienzoLineaActual_aux.size()-1)).intValue();
        comienzoLineaAnterior=((Integer) comienzoLineaAnterior_aux.elementAt(
                                comienzoLineaAnterior_aux.size()-1)).intValue();
    }


/*** Devuelve la posici�n de una cadena dentro de otra ignorando     **********/
/***     may�sculas                                                  **********/
/*** Par�metros: s1-> cadena dentro de la que se mira                **********/
/***             s2-> cadena que se busca dentro de s1               **********/
/*** Devuelve: un entero >=0 que representa la posici�n a partir de  **********/
/***     la cual aparece s2 en s1 (ignorando may�sculas) o un entero **********/
/***     <=0 en otro caso                                            **********/
    int indiceDe(String s1, String s2)
    {
       s1=new String(s1);
       s2=new String(s2);
       return s1.toUpperCase().indexOf(s2.toUpperCase());
    }

/*** Inserta un espacio entre el nombre de una funci�n y su argumento**********/
/***     en caso de que sea necesario                                **********/
/*** Par�metros: expresion-> la expresi�n en cuesti�n                **********/
/*** Deuelve: la expresi�n con un espacio insertao en caso de que sea**********/
/***     necesario                                                   **********/
    String necesitaEspacio(String expresion)
    {
        if (expresion!=null && expresion.length()>0 &&
                                                     expresion.charAt(0)=='(') {
            return("");
        } else {
            return(" ");
        }
    }

/******************************************************************************/
/******************************************************************************/
/*********** funciones utilizadas en el c�lculo de la derivada: ***************/
/******************************************************************************/
/******************************************************************************/

/*** Quita exceso de par�ntesis circundando a una expresi�n          **********/
/*** Par�metros: s-> expresi�n                                       **********/
/*** Deuelve: la expresi�n sin los par�ntesis externos sobrantes     **********/
    String quita_parentesis(String s)
    {
        if (s!=null && s.length()>0) {
            while (s.charAt(0)=='(' && s.charAt(s.length()-1)==')') {
                s=s.substring(1,s.length()-1);
            }
        }
        return s;
    }

/*** Dice si dos expresiones son iguales quitando los par�ntesis     **********/
/***     innecesarios de la primera                                  **********/
/*** Par�metros: s1,s2-> las expresiones en cuesti�n                 **********/
/*** Deuelve: si ambas cadenas representan la misma expresi�n        **********/
    boolean igualA(String s1, String s2)
    {
        s1=quita_parentesis(s1);
        return(s1.equalsIgnoreCase(s2));
    }

/*** Nos dice si una expresi�n es igual a cero                       **********/
/*** Par�metros: s1-> la expresi�n en cuesti�n                       **********/
/*** Deuelve: si la expresi�n s1 es igual a cero o no                **********/
    boolean igualAcero(String s1)
    {
        return(igualA(s1,"0") || igualA(s1,"-0"));
    }

/*** Nos dice si una cadena representa un n�mero                     **********/
/*** Par�metros: s1-> la cadena en cuesti�n                          **********/
/*** Deuelve: si la cadena representa o no un n�mero                 **********/
    boolean esNumero(String s)
    {
        int punto=0,i=0;
        if (s==null || s.length()<=0) {
            return false;
        }
        s=quita_parentesis(s);
        while(i<s.length()) {
            if (s.charAt(i)=='.') {
                punto++;
                if (punto>1) {
                    break;
                }
                continue;
            }

            if (!Character.isDigit(s.charAt(i))) {
                break;
            }
            i++;
        }
        return(i==s.length() && i>0);
    }

/*** Convierte a n�mero una cadena                                   **********/
/*** Par�metros: s-> la cadena en cuesti�n                           **********/
/*** Deuelve: el valor del n�mero representado por s                 **********/
    double valor_doble(String s)
    {
        s=quita_parentesis(s);
        return(Double.valueOf(s).doubleValue());
    }

/*** Hace simplificaciones triviales sobre la exponencial            **********/
/*** Par�metros: e-> la expresi�n a simplificar                      **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_exponencial(String e)
    {
        if (igualAcero(e)) {
            return("1");
        }
        if (igualA("1",e)) {
            return("e");
        }
        return("exp"+necesitaEspacio(e)+e);
    }

/*** Hace simplificaciones triviales sobre el logaritno neperiano    **********/
/*** Par�metros: n-> la expresi�n a simplificar                      **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_ln(String n)
    {
        if (n.equalsIgnoreCase("e")) {
            return("1");
        }
        return("ln"+necesitaEspacio(n)+n);
    }

/*** Hace simplificaciones triviales sobre la ra�z cuadrada          **********/
/*** Par�metros: r-> la expresi�n a simplificar                      **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_raiz(String r)
    {
        if (igualAcero(r)) {
            return("0");
        }
        if (igualA(r,"1")) {
            return("1");
        }
        return ("sqrt"+necesitaEspacio(r)+r);
    }

/*** Hace simplificaciones triviales sobre la multiplicaci�n         **********/
/*** Par�metros: f1,f2-> los dos factores                            **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_producto(String f1,String f2)
    {
        if (esNumero(f1) && esNumero(f2)) {
            return(miDouble.toString(valor_doble(f1)*valor_doble(f2)));
        }
        if (igualAcero(f1) || igualAcero(f2)) {
            return("0");
        }
        if (igualA(f1,"1")) {
            return(f2);
        }
        if (igualA(f2,"1")) {
            return(f1);
        }
        return(f1+"*"+f2);

    }

/*** Hace simplificaciones triviales sobre la divisi�n               **********/
/*** Par�metros: d1-> dividendo                                      **********/
/***             d2-> divisor                                        **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_division(String d1, String d2) throws SyntaxErrorException
    {
        if (igualAcero(d2)) {
            throw new SyntaxErrorException("Error de divisi�n por 0");
        }
        if (igualAcero(d1)) {
            return("0");
        }
        if (igualAcero(d2)) {
            return(d1);
        }
        if (esNumero(d1) && esNumero(d2)) {
            return(miDouble.toString(valor_doble(d1)/valor_doble(d2)));
        }
        return(d1+"/"+d2);
    }

/*** Hace simplificaciones triviales sobre la suma                   **********/
/*** Par�metros: s1,s2-> los sumandos                                **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_suma(String s1,String s2)
    {
        if (igualAcero(s1)) {
            return(s2);
        }
        if (igualAcero(s2)) {
            return(new String(s1));
        }
        if (esNumero(s1) && esNumero(s2)) {
            return(miDouble.toString(valor_doble(s1)+valor_doble(s2)));
        }
        return(s1+"+"+s2);
    }

/*** Hace simplificaciones triviales sobre la resta                  **********/
/*** Par�metros: r1-> minuendo                                       **********/
/***             r2-> sustraendo                                     **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_resta(String r1,String r2)
    {
        if (igualAcero(r2)) {
            return(new String(r1));
        }
        if (igualAcero(r1)) {
            return("-"+r2);
        }
        if (esNumero(r1) && esNumero(r2)) {
            return(miDouble.toString(valor_doble(r1)+valor_doble(r2)));
        }
        return(r1+"-"+r2);
    }

/*** Hace simplificaciones triviales sobre potencias                 **********/
/*** Par�metros: p1-> base                                           **********/
/***             p2-> exponente                                      **********/
/*** Deuelve: la expresi�n simplificada                              **********/
    String simplifica_potencia(String p1,String p2)
    {
        if (igualA(p2,"1")) {
            return(new String(p1));
        }
        if ((igualAcero(p2)) && (!igualAcero(p1))) {
            return("1");
        }
        if ((igualAcero(p1)) && (!igualAcero(p2))) {
            return("0");
        }
        if (esNumero(p1) && esNumero(p2)) {
            return(miDouble.toString(Math.pow(valor_doble(p1),valor_doble(p2))));
        }
        return(p1+"^"+p2);
    }


} //fin class evaluador

