/******************************************************************************/
/******************         evaluador.java      *******************************/
/******************************************************************************/
package mieval; 
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JFrame;
import mieval.tipo.*;

public class evaluador {
    private static tab_simb palabrasReservadas; // contiene las palabras Reservadas
    private tab_simb tabla_simbolos; // variables del programa
    private static listaFicheros ficheros=new listaFicheros(); // ficheros abiertos
                                                      //para su ejecución
    private variable acumulador; // guarda el último valor calculado

    private String programa_a_ejecutar; // contiene la instrucción(es) a ejecut.
    private int chr_actual=0;  // carácter que se está procesando actualmente
    private int numLineas=1; // línea en ejecución
    private int comienzoLineaAnterior=0; // límites última línea interpretada
    private int comienzoLineaActual=0;
    private int ultimoCaracter=0; // último carácter leído de la entrada
    private int ultimoToken=0; // posición último carácter del último token

    private entrada lexema_actual; // último token reconocido y sus atributos
    private entrada ultimo_lexema; // penúltimo token reconocido y sus atrib.

    private String expresion_a_derivar; // utilizados para calcular la dervida
    private String expresion;
    private String derivada;
    private String dx; // variable con respecto a la que se deriva

    private variable valorReturn; // para guardar el valor de la inst.RETURN


    //los siguientes se utilizan para salvar el entorno de ejecución
    private Vector programa_a_ejecutar_aux= new Vector();
    private Vector lexema_actual_aux=new Vector();
    private Vector chr_actual_aux=new Vector();
    private Vector numLineas_aux=new Vector();
    private Vector comienzoLineaAnterior_aux=new Vector();
    private Vector comienzoLineaActual_aux=new Vector();

    private Vector notificados=new Vector(); // lista de objetos a quien se
                             // notifica que se ha creado una nueva variable
    private boolean notificacion=false; // indica si se notifica a lista de
                             // notificados o no


    private Vector listaParamReal=new Vector(); // para las llamadas a funciones
    //+++private Vector listaParamFormal=new Vector(); // para las llamadas a funciones
    private Vector listaParamReferencias=new Vector(); // para los Parámetros por referencia en las llamadas a las funciones
    private Vector pila=new Vector(); //para guardar las variables locales

    private Vector postIncrementos=new Vector();// listas de variables a
    private Vector postDecrementos=new Vector();// post{inc-dec]rementar
    private impresor imprimeSalida=null;
    
    //historico para guardar los comandos ejecutados
    public historico hist=new historico();
    lectorMatrizInterfaz le;

/*******************************************************************************
*** Constructor de la clase: inicializa las palabras reservadas ****************
*** y la tabla de símbolos                                      ****************
*******************************************************************************/

    public evaluador()
    {
       tabla_simbolos=new tab_simb(); // tabla_simbolos inicialmente vacía
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
          palabrasReservadas.inserta(ctes_eval.ROUND,new variable("round"));
          palabrasReservadas.inserta(ctes_eval.ROUND,new variable("trunc"));
          palabrasReservadas.inserta(ctes_eval.FACTORIAL,new variable("fact"));
          palabrasReservadas.inserta(ctes_eval.PI,new variable("_pi",'D',
                                     new miDouble(Math.PI))); // número pi
          palabrasReservadas.inserta(ctes_eval.PI,new variable("π",'D',
                  					 new miDouble(Math.PI))); // número pi
          palabrasReservadas.inserta(ctes_eval.E,new variable("_e",'D',
                                     new miDouble(Math.E)));//número 'e'
          palabrasReservadas.inserta(ctes_eval.ANIO_BISIESTO,new variable("anioBisiesto"));
          palabrasReservadas.inserta(ctes_eval.FECHA_VALIDA,new variable("fechaValida"));
          palabrasReservadas.inserta(ctes_eval.FECHA_ACTUAL,new variable("fechaActual"));
          palabrasReservadas.inserta(ctes_eval.FECHA_NUM,new variable("fechaNum"));
          palabrasReservadas.inserta(ctes_eval.HORA_ACTUAL,new variable("horaActual"));
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
          new variable("?",'D',new miDouble(0.0)))).objeto;//último valor calculado
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
          palabrasReservadas.inserta(ctes_eval.QUITAR_FILA, new variable("quitarfila"));
          palabrasReservadas.inserta(ctes_eval.QUITAR_COL, new variable("quitarcolumna"));
          palabrasReservadas.inserta(ctes_eval.RANGO,new variable("rango"));
          palabrasReservadas.inserta(ctes_eval.MENOR,new variable("menor"));
          palabrasReservadas.inserta(ctes_eval.DETERMINANTE,new variable("det"));
          palabrasReservadas.inserta(ctes_eval.ADJUNTA, new variable("adjunta"));
          palabrasReservadas.inserta(ctes_eval.INVERSA, new variable("inversa"));
          palabrasReservadas.inserta(ctes_eval.RETURN, new variable("return"));
          palabrasReservadas.inserta(ctes_eval.LEER, new variable("leer"));
          palabrasReservadas.inserta(ctes_eval.LEERMATRIZ,new variable("leerMatriz"));
          palabrasReservadas.inserta(ctes_eval.PRINT, new variable("print"));
          palabrasReservadas.inserta(ctes_eval.PRINTCOLOR, new variable("printc"));
          palabrasReservadas.inserta(ctes_eval.CLEAR,new variable("clear"));
       }
    }
    public evaluador(impresor p,lectorMatrizInterfaz le) {
       this();
       this.imprimeSalida=p;
       this.le=le;
    }
    public tab_simb getTablaSimbolos() {
       return this.tabla_simbolos;
    }

/******************************************************************************/
/******************************************************************************/
/******************************** Analizador Léxico: **************************/
/******************************************************************************/
/******************************************************************************/

/*** Analizador léxico. Devuelve los tokens reconocidos en la entrada**********/
/*** Parámetros: ninguno                                              *********/
/*** Devuelve: nada                                                  **********/
    void analex() throws SyntaxErrorException
    {
       int t, tAux;
       String cadena_aux=new String();

       ultimoToken=chr_actual; // guardamos el final del último token reconocido
       while (true) {
          t=siguienteCaracter(); // avanzamos un carácter
          if (t==ctes_eval.EOF) {
             lexema_actual=new entrada(ctes_eval.EOF,null);
             break;
          } else if (t=='?') {
             lexema_actual=new entrada(ctes_eval.ACUMULADOR,null);
             break;
          } else if (t=='#') {
             tAux=siguienteCaracter();
             if (Character.isDigit((char) tAux)) {
                String d="#"+((char) tAux); 
                for(int i=1;i<=10;i++) {
                  d+=((char)siguienteCaracter());
                }
                miFecha f=new miFecha(d);
                if (f.esValida()) {
                   lexema_actual=new entrada(ctes_eval.LITERAL_FECHA,new variable(f));
                   break;
                } else {
                   for(int i=1;i<=11;i++) {
                      devuelveCaracter();
                   }
                }
             }
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
          } else if (Character.isLetterOrDigit((char) t) || t=='_') { //identificador
             cadena_aux=identificador(t);
             lexema_actual=palabrasReservadas.busca(cadena_aux);
             if (lexema_actual==null) {
                //+++++
                //System.out.println("tabla_simbolos: "+tabla_simbolos+"cadena_aux: "+cadena_aux);
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
                lexema_actual=new entrada('|',new variable(""));
             } else {
                lexema_actual=new entrada(ctes_eval.OR, new variable(""));
             }
             break;
          } else if (t=='=') { //asignación
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
          } else if (t=='"' || t=='\'') { //literal de cadena
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
             break;    // devuelve el código ASCII como componente léxico
          }
       }
       if (ctes_eval.esOpAritmElemental(lexema_actual.comp_lex)) {
          int l=t;
          t=siguienteCaracter();
          if (t=='=') {
             switch(lexema_actual.comp_lex) {
                case '+':
                   l=ctes_eval.MAS_IGUAL;
                   break;
                case '-':
                   l=ctes_eval.MENOS_IGUAL;
                   break;
                case '*':
                   l=ctes_eval.POR_IGUAL;
                   break;
                case '/':
                   l=ctes_eval.DIVIDIDO_IGUAL;
                   break;
                case '^':
                   l=ctes_eval.POTENCIA_IGUAL;
                   break;
                case ctes_eval.MOD:
                   l=ctes_eval.MOD_IGUAL;
                   break;
             }
             lexema_actual.comp_lex=l;
          } else {
             devuelveCaracter();
          }
       }
    }
    void devuelveToken() {
        chr_actual=ultimoToken;
    }

/*** Inicializa valores para análisis léxico                         **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void inicializa_entorno()
    {
        chr_actual=0;
        numLineas=1;
        comienzoLineaAnterior=0;
        comienzoLineaActual=0;
        lexema_actual=new entrada();
    }

/*** Retira un carácter de la entrada                                **********/
/*** Parámetros: t -> carácter recien leído                          **********/
/*** Devuelve: el carácter retirado de la entrada                    **********/
    int siguienteCaracter()
    {
       if (chr_actual>=programa_a_ejecutar.length()) {
          return ultimoCaracter=ctes_eval.EOF;
       }
       return ultimoCaracter=(int) programa_a_ejecutar.charAt(chr_actual++);
    }

/*** Devuelve el último carácter leído a la entrada                  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void devuelveCaracter()
    {
       if (ultimoCaracter!=ctes_eval.EOF) {
          chr_actual--;
       }
    }

/*** Retira un número de la entrada                                  **********/
/*** Parámetros: t-> carácter recien leído de la entrada             **********/
/*** Devuelve: la cadena que representa a dicho número               **********/
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
                  throw new SyntaxErrorException("dos puntos en un número.");
               }
            } else if (!Character.isDigit((char) t)) {
               break;
            }
            chr_actual++;
         }
         return cadena_aux;
    }

/*** Retira un identificador de la entrada                           **********/
/*** Parámetros: t-> carácter recien leído de la entrada             **********/
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

/*** Guarda en ultimo_lexema el último token retirado de la entrada  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void siguienteToken() throws SyntaxErrorException
    {
       ultimo_lexema=lexema_actual;
       analex();
    }

/*** Comprueba que el lexema actual casa con el token t y avanza al  **********/
/***    siguiente token                                              **********/
/*** Parámetros: ninguno                                             **********/
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
		     new SyntaxErrorException("").printStackTrace();
           throw new
           SyntaxErrorException("se esperaba '"+ctes_eval.nombreToken(t)+"'");
       }
    }

/*** Salta un bloque de la entrada limitada por tokenInicio tokenFin **********/
/*** Parámetros: tokenInicio-> marca el comienzo desde donde se salta**********/
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
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void avanzaBloqueLlaves() throws SyntaxErrorException
    {
       avanzaBloque('{','}');
    }

/*** Salta un bloque de la entrada limitado por paréntesis           **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void avanzaBloqueParent() throws SyntaxErrorException
    {
       avanzaBloque('(',')');
    }

/*** Devuelve la posición del próximo carácter a leer de la entrada  **********/
/***    por el analizador léxico                                     **********/
/*** Parámetros: ninguno                                             **********/
    public int getChrActual()
    {
       return chr_actual;
    }

/*** Devuelve el último token de una expresión                       **********/
/*** Parámetros: s-> cadena que representa a la expresión            **********/
/*** Devuelve: el último token de dicha expresión                    **********/
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
/**************** Analizador Sintáctico e intérprete ***************************/
/******************************************************************************/
/******************************************************************************/
void vaciaPila() {
   if (pila.size()>0) {
      this.tabla_simbolos=(tab_simb) pila.elementAt(0);
      for(int i=0; i<pila.size(); i++) {
         this.notifiFinEntorno();
      }
      pila.removeAllElements();
   }
}

/*** Ejecuta la lista de instrucciones indicadas en expre            **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    public void notificarVariablesReferenciadas() {
       variable pFormal,pReal;
       
       for(int i=0; i<listaParamReferencias.size(); i++) {
          pFormal=(variable) listaParamReferencias.elementAt(i);
          if (pFormal!=null) {
             pReal=(variable)listaParamReal.elementAt(i);
             notifiNuevaVariable(pReal.nombre);
          }
       }
    }
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
               hist.añadir(programa_a_ejecutar);
               if (r!=null) {
                  res=r; // devolvemos última sentencia!=null, si la hay
               }
            }
            return res; // devolvemos el último valor calculado
        } catch (SyntaxErrorException ex) {
            vaciaPila();
            if (ex.getMessage().indexOf("exec")>=0) {
               return new variable("",'E', ex.getMessage());
            } else {
               return new variable("",'E',"Error: "+ultimaLinea()
                                                   +"<-"+ex.getMessage());
            }
        }
    }


/*** Análisis sintáctico, comprobación de tipos, tratamiento de      **********/
/***    errores y ejecución de una sentencia                         **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el último valor calculado en la ejecución de dicha    **********/
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
            case ctes_eval.CLEAR:
               if (this.imprimeSalida!=null) {
                  this.imprimeSalida.clear();
               }
               match(ctes_eval.CLEAR);
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
            case ctes_eval.RETURN: //+++++
               match(ctes_eval.RETURN);
               if (lexema_actual.comp_lex==ctes_eval.RETURN) {
                  throw new SyntaxErrorException("Return Return");
               }
               if (listaParamReferencias!=null && listaParamReal!=null ) {
                  if (listaParamReferencias.size()!=listaParamReal.size()) {
                     throw new SyntaxErrorException("Error en número de Parámetros formales o reales.");
                  }
               } else {
                  if (listaParamReferencias!=listaParamReal) {
                     throw new SyntaxErrorException("Error en número de Parámetros formales o reales.");
                  }
               }
               valorReturn=sentencia();
               lexema_actual.comp_lex=ctes_eval.EOF;
               throw new ReturnException();
            case ctes_eval.FIN:
               match(ctes_eval.FIN); // sentencia vacía
               r=null;
               break;
            default:
               r=expr_cond();
               match(ctes_eval.FIN);
        }
        if (r!=null) {
           acumulador=r; // guardamos en el acumulador último valor calculado
        }
        postIncrementa();
        postDecrementa();
        return r;
    }

/*** Copia o hace referenciar los Parámetros reales en los formales  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void lista_parametros() throws SyntaxErrorException
    {
       int numParam=0;
       variable pReal, pFormal;

       if (chr_actual!=10 || numLineas!=1) {
          throw new SyntaxErrorException("sentencia PARAMETERS solo "+
                       "permisible al principio de la 1ª línea de programa");
       }
       match(ctes_eval.PARAMETERS);
       if (listaParamReal!=null) {
          while (lexema_actual.comp_lex!=ctes_eval.FIN) {
             if (numParam>=listaParamReal.size()) {
                throw new
                SyntaxErrorException("faltan Parámetros en la llamada.");
             }
             pReal=(variable) listaParamReal.elementAt(numParam);
             pFormal=new variable();
             if (lexema_actual.comp_lex=='*') {//variable se pasa por referencia
                match('*');
                if (pReal.nombre==null) {//si parámet.no es referencia a variab.
                   throw new SyntaxErrorException("se esperaba una referencia"+
                                                  " a variable");
                } //++++++
                //pFormal=pReal;                
                //+++++listaParamReferencias.addElement(pReal.nombre);
                pFormal.tipo=pReal.tipo; // esto no funcionaba
                pFormal.valor=pReal.valor; // no se transmitían los cambios a la variable real
                listaParamReferencias.addElement(pFormal);
             } else {
                pFormal=pReal.copia();
                //+++++listaParamReferencias.addElement(" ");
                listaParamReferencias.addElement(null);
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
             siguienteToken(); // si no se pasan Parámetros reales saltamos fin
          }
       }
    }

/*** Sentencia dim-> crea una matriz del tamaño indicado en la       **********/
/***     sentencia                                                   **********/
/*** Parámetros: ninguno                                             **********/
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
               dimensiones.addElement(this.expresion_aritmetica());
               match(']');
            }
            aux.addElement(dimensiones.elementAt(dimensiones.size()-1));
            r=new variable(lexem_aux.objeto.nombre,'M',new miArray(new array(aux,null)));
            for (int i=dimensiones.size()-2; i>=0; i--) {
               aux.removeAllElements();
               aux.addElement(dimensiones.elementAt(i));
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

/*** Sentencia for-> bucle controlado por condición                  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el último valor calculador durante la ejecución del   **********/
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
             chr_coletilla=chr_actual; // guardamos posición
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
                v=lista_expresiones_condicionales();//evaluamos 3ª list_exp.cond
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
          chr_actual=chr_aux; //volvemos a la condición
          lexema_actual=lexem_aux;
          numLineas=numLineas_aux;
       }
    }

/*** Sentencia if-> ejecución condicional de bloque de instrucciones **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el último valor calculador durante la ejecución de la **********/
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

/*** Sentencia while-> ejecución bloque de instrucciones mientras se **********/
/***     cumpla la condición                                         **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el último valor calculador durante la ejecución del   **********/
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
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: último valor calculado en dicho bloque                **********/
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


/*** Realiza los postincrementos contenios en una instrucción tras   **********/
/***     su ejecución                                                **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Realiza los postdecrementos contenios en una instrucción tras   **********/
/***     su ejecución                                               **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Evalúa una lista de expresiones separadas por comas             **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Evalúa una lista de expresiones separadas por comas             **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Evalúa una lista de expresiones aritméticas                     **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Evalúa una expresión                                            **********/
/*** Parámetros: cadena representando dicha expresión                **********/
/*** Devuelve: el valor resultado de evaluar la expresión           **********/
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

/*** Evalúa una expresión en la cadena de entrada                    **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión           **********/
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

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
    variable expr() throws SyntaxErrorException
    {
       variable r=null;

       //+++if (lexema_actual.comp_lex=='{') { // una matriz es una expresión
       //   return new variable("",'M',new miArray(matriz()));
       //}
       if (lexema_actual.comp_lex==ctes_eval.ID) {
          r=asignacion(); // una asignaciones una expresión
          if (r!=null) {
             return r;
          }
       }
       r=expresion_aritmetica();

       return r;
    }

/*** Asignación y Evaluaciónde expresiones en la cadena de entrada  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: una variable conteniendo el valor asignado            **********/
    variable asignacion() throws SyntaxErrorException
    {
       variable r=null;

       if (lexema_actual.comp_lex==ctes_eval.ID) {
          int chr_aux=chr_actual;
          entrada lexem_aux=lexema_actual;
          match(ctes_eval.ID);
          if (ctes_eval.esOpAsignacion(lexema_actual.comp_lex)) {
             int l=lexema_actual.comp_lex;
             if (l!=ctes_eval.ASSIGN && lexem_aux.objeto.valor==null) {
                throw new SyntaxErrorException("Variable inexistente: "+lexem_aux.objeto.nombre);
             }
             match(l);
             r=expr_cond();
             //double resultado=r.doubleValue();
             switch(l) {
                case ctes_eval.MAS_IGUAL:
                   //resultado+=lexem_aux.objeto.doubleValue();
                   lexem_aux.objeto.valor=lexem_aux.objeto.valor.suma(r.valor);
                   break;
                case ctes_eval.MENOS_IGUAL:
                   //resultado=lexem_aux.objeto.doubleValue()-resultado;
                   lexem_aux.objeto.valor=lexem_aux.objeto.valor.resta(r.valor);
                   break;
                case ctes_eval.POR_IGUAL:
                   //resultado=lexem_aux.objeto.doubleValue()*resultado;
                   lexem_aux.objeto.valor=lexem_aux.objeto.valor.producto(r.valor);
                   break;
                case ctes_eval.DIVIDIDO_IGUAL:
                   //resultado=lexem_aux.objeto.doubleValue()/resultado;
                   lexem_aux.objeto.valor=lexem_aux.objeto.valor.division(r.valor);
                   break;
                case ctes_eval.POTENCIA_IGUAL:
                   //resultado=Math.pow(lexem_aux.objeto.doubleValue(),resultado);
                   lexem_aux.objeto.valor=lexem_aux.objeto.valor.potencia(r.valor);
                   break;
                case ctes_eval.MOD_IGUAL:
                   //resultado=lexem_aux.objeto.doubleValue() % resultado;
                   lexem_aux.objeto.valor=lexem_aux.objeto.valor.modulo(r.valor);
                   break;
                case ctes_eval.ASSIGN:
                    lexem_aux.objeto.asigna(r);
             }
             tabla_simbolos.inserta(lexem_aux);
             notifiNuevaVariable(lexem_aux);
             return lexem_aux.objeto;
                
                   
                   
          /*if (lexema_actual.comp_lex==ctes_eval.ASSIGN) {
             match(ctes_eval.ASSIGN);
             lexem_aux.objeto.asigna(expr_cond());
             tabla_simbolos.inserta(lexem_aux);
             notifiNuevaVariable(lexem_aux);
             return lexem_aux.objeto;*/
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
                throw new SyntaxErrorException("Op [] sólo aplicable a String"+
                                               " o matriz");
             } else {
                throw new SyntaxErrorException("variable no existente "+
                                               lexem_aux.objeto.nombre);
             }
          }
          chr_actual=chr_aux;    // sino hubo éxito
          lexema_actual=lexem_aux; // backtracking volvemos hacia atrás
       }
       return r;
    }

/*** Asignación de un carácter dentro de una cadena                  **********/
/*** Parámetros: r-> la cadena en la que se asigna un carácter       **********/
/*** Devuelve: una variable conteniendo el carácter asignado         **********/
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

/*** Retira una expresión tipo matriz de la entrada                  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: una lista con los elementos de dicha matriz           **********/
    Vector matriz() throws SyntaxErrorException
    {
        Vector r;

        match('{');
        r=lista_expresiones_condicionales_copia();
        match('}');

        return r;
    }

/*** Evalúa una expresión pasada como parámetro                      **********/
/*** Parámetros: e-> la expresión a evaluar                          **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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
                //r=suma(r,term());
                r=new variable(r.valor.suma(term().valor));
            } else {
                //r=resta(r,term());
                r=new variable(r.valor.resta(term().valor));
            }
        }
        return r;
    }

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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
              //compruebaTipo('D', res); compruebaTipo('D', r);
              // sólo tiene sentido multiplicar, div..., números
          }
          switch (l) {
             case '*':
                //res=new variable(res.doubleValue()*r.doubleValue());
                res=new variable(res.valor.producto(r.valor));
                break;
             case '/':
                //res=new variable(res.doubleValue()/r.doubleValue());
                res=new variable(res.valor.division(r.valor));
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

/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
    variable factor() throws SyntaxErrorException
    {
       variable res=new variable(), r=new variable();

       res=potencia();
       while (lexema_actual.comp_lex=='^') {
         match(lexema_actual.comp_lex);
         r=potencia();
         //compruebaTipo('D', res); compruebaTipo('D', r);
         //res=new variable(Math.pow(res.doubleValue(), r.doubleValue()));
         res=new variable(res.valor.potencia(r.valor));
       }
       return res;
    }

/*** Devuelve la tangente hiperbólica de un valor                    **********/
/*** Parámetros: x-> valor sobre el que calcular la tangente hiperbólica*******/
    public static double tanh(double x)
    {
       return (Math.pow(Math.E, x)-Math.pow(Math.E,-x))/
               (Math.pow(Math.E,x)+Math.pow(Math.E,-x));
    }

/*** Devuelve el seno hiperbólico de un valor                        **********/
/*** Parámetros: x-> valor sobre el que calcular el seno hiperbólico **********/
    public static double senh(double x)
    {
       return (Math.pow(Math.E,x)-Math.pow(Math.E,-x))/2;
    }

/*** Devuelve el coseno hiperbólico de un valor                      **********/
/*** Parámetros: x-> valor sobre el que calcular el coseno hiperbólico ********/
    public static double cosh(double x)
    {
       return (Math.pow(Math.E,x)+Math.pow(Math.E,-x))/2;
    }
/*** Evaluación de un carácter indexado dentro de una cadena                        **********/
/*** Parámetros: ninguno                                                            **********/
/*** Devuelve: un objeto de tipo variable cadena con el valor del carácter indexado **********/
    
    variable caracterIndiceCadena(variable res) throws SyntaxErrorException { 
       match('[');
       int ind=expresion_aritmetica().intValue();
       if (ind<0 || ind>=res.toString().length()) {
          throw new SyntaxErrorException("indice fuera de rango '"+ind+"'");
       }
       res=new variable(res.toString().charAt(ind));
       match(']');
       return res;
    }
/*** Evaluaciónde expresiones en la cadena de entrada               **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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
             if (res.tipo=='S' && lexema_actual.comp_lex=='[') {
                res=caracterIndiceCadena(res);
             } else if (res.tipo=='M' && lexema_actual.comp_lex=='[') {
                res=refMatriz(res);
             }
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
             if (lexema_actual.comp_lex==ctes_eval.ID) {
                 this.devuelveToken();
                 lexema_actual=new entrada('*',new variable(""));
             } else if (lexema_actual.comp_lex==ctes_eval.NOT) {
                 double resultado=res.intValue();
                 for(int i=res.intValue()-1; i>1; i--) {
                     resultado*=i;
                 }
                 res=new variable(resultado);
                 match(ctes_eval.NOT);
             }
             break;
          case ctes_eval.LITERAL_CADENA:
             res=lexema_actual.objeto;
             match(ctes_eval.LITERAL_CADENA);
             if (lexema_actual.comp_lex=='[') {
                res=caracterIndiceCadena(res);
             }
             break;
          case ctes_eval.LITERAL_FECHA:
             res=lexema_actual.objeto;
             match(ctes_eval.LITERAL_FECHA);
             break;
          case ctes_eval.ID:
             lexem_aux=lexema_actual;
             res=valID();
             l=lexema_actual.comp_lex;
             if (l==ctes_eval.INC) {
                compruebaTipos("DF", res);
                postIncrementos.addElement(res);
                match(ctes_eval.INC);
                notifiNuevaVariable(lexem_aux);
             } else if (l==ctes_eval.DEC) {
                compruebaTipos("DF",res);
                postDecrementos.addElement(res);
                match(ctes_eval.DEC);
                notifiNuevaVariable(lexem_aux);
             } else if (l==ctes_eval.NOT) { // el caracter ! del factorial se reconoce por analex como NOT
                double resultado=res.intValue();
                for(int i=res.intValue()-1; i>1; i--) {
                    resultado*=i;
                }
                res=new variable(resultado);
                match(ctes_eval.NOT);
             }
             break;
          case ctes_eval.INC: case ctes_eval.DEC:
             l=lexema_actual.comp_lex;
             match(lexema_actual.comp_lex);
             lexem_aux=lexema_actual;
             res=valID();
             compruebaTipos("DF",res);
             if (l==ctes_eval.INC) {
                res.inc();
             } else {
                res.dec();
             }
             notifiNuevaVariable(lexem_aux);
             break;
          //funciones sin ningún parámetro
          case ctes_eval.FECHA_ACTUAL:
               match(ctes_eval.FECHA_ACTUAL);
               match('(');match(')');
               res=new variable("",'F',miFecha.fechaActual());
               break;
           case ctes_eval.HORA_ACTUAL:
               match(ctes_eval.HORA_ACTUAL);
               match('(');match(')');
               Calendar cal = Calendar.getInstance();
               SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); //"yyyy-MM-dd HH:mm:ss"
               res=new variable("",'S',new miString(sdf.format(cal.getTime())));
               break;
          //funciones matemáticas de un sólo argumento
          case ctes_eval.COS: case ctes_eval.COSH: case ctes_eval.ACOS:
          case ctes_eval.SEN: case ctes_eval.SENH: case ctes_eval.ASEN:
          case ctes_eval.TAN: case ctes_eval.TANH: case ctes_eval.ATAN:
          case ctes_eval.CEIL: case ctes_eval.EXP: case ctes_eval.ABS:
          case ctes_eval.FLOOR: case ctes_eval.LN: case ctes_eval.SQRT:
          case ctes_eval.INT: case ctes_eval.FACTORIAL: case ctes_eval.ANIO_BISIESTO:
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
                case ctes_eval.FACTORIAL:
                   double resultado=1;
                   for(int i=res.intValue(); i>1; i--) {
                       resultado*=i;
                   }
                   res=new variable(resultado);
                   break;
                case ctes_eval.ANIO_BISIESTO:
                   res=new variable("",'B',new miBooleano(miFecha.esBisiesto(res.intValue())));
                   break;
             }
             break;
          //funciones de fecha
          case ctes_eval.FECHA_VALIDA: case ctes_eval.FECHA_NUM:
              l=lexema_actual.comp_lex;
              match(l);
              match(('('));
              par1=potencia();
              match(')');
              miFecha f=new miFecha(par1.toString());
              switch(l) {
                  case ctes_eval.FECHA_VALIDA:
                      res=new variable("",'B',new miBooleano(f.esValida()));
                      break;
                  case ctes_eval.FECHA_NUM:
                      res=new variable("",'D',new miDouble(f.intValue()));
                      break;
              }
              break;
          // funciones matemáticas de 2 Parámetros
          case ctes_eval.LOG:  case ctes_eval.ATAN2:
			 case ctes_eval.ROUND: case ctes_eval.TRUNC:
             l=lexema_actual.comp_lex;
             match(l);
             match('(');
             par1=expr_cond();
             compruebaTipo('S', par1);
             match(',');
             par2=expr_cond();
             compruebaTipo('S', par2);
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
                case ctes_eval.ROUND:
                   res=new variable(miDouble.redondeo(par1.doubleValue(),(int)par2.doubleValue()));
                   break;
                case ctes_eval.TRUNC:
                   res=new variable(miDouble.trunc(par1.doubleValue(),(int)par2.doubleValue()));
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
          // funciones de un parámetro cadena y dos numéricos
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
          // funciones de un Parámetros tipo cadena
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
                res=exec(s,new Vector());
             } else {
                match(',');
                Vector v=lista_expresiones_condicionales();
                match(')');
                res=exec(s,v);
             }
             break;
          // funciones de un parámetro de cualquier tipo
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
                      throw new SyntaxErrorException("tercer parámetro debe ser"
                                                     +" 'S', 'M', 'D' o 'B'");
                   }
                }
                if (par2.tipo==' ') {
                   throw new SyntaxErrorException("Debe especificar un tipo si"
                                                  +" la variable no existe");
                }
                match(')');
                leerVariable(par1.toString(), par2);
                if (nuevaVar) {
                   tabla_simbolos.inserta(ctes_eval.ID,par2);
                }
                notifiNuevaVariable(par2);
             }
             break;          
          case ctes_eval.LEERMATRIZ:
             {
                boolean nuevaVar=false;
                match(ctes_eval.LEERMATRIZ);
                match('(');
                if (le!=null) {
                  if (lexema_actual.comp_lex==')') {
                     match(')');
                     le.leerMatriz(2);
                  } else {
                     par1=this.expresion_aritmetica();
                     if (par1.tipo=='M') {
                        match(')');
                        le.leerMatriz(((miArray)par1.valor));
                     } else {
                        if (lexema_actual.comp_lex==',')  {
                           match(',');
                           par2=this.expresion_aritmetica();
                           match(')');
                           le.leerMatriz(par1.intValue(),par2.intValue());
                        } else {
                           match(')');
                           le.leerMatriz(par1.intValue());
                        }
                     }
                  }
                  res=new variable(le.getMatriz());
                } else {
                   throw new SyntaxErrorException("Error: Esta función no está disponible");
                }
             }
             break;
           case ctes_eval.PRINT: case ctes_eval.PRINTCOLOR:
             {
                String imprimir="";
                String color="";
                l=lexema_actual.comp_lex;
                match(l);
                match('(');
                if (l==ctes_eval.PRINTCOLOR) {
                    color=expr_cond().toString();
                    match(',');
                }
                do {
                   par1=expr_cond();
                   imprimir+=par1.toString();
                   if (lexema_actual.comp_lex==',') {
                      match(',');
                   }
                } while(lexema_actual.comp_lex!=')' && lexema_actual.comp_lex!=ctes_eval.EOF);
                if (this.imprimeSalida==null) {
                   System.out.println(imprimir);
                } else {
                    if (l==ctes_eval.PRINTCOLOR) {
                        this.imprimeSalida.imprimec(color,imprimir);
                    } else {
                        this.imprimeSalida.imprime(imprimir);
                    }
                }
                match(')');
                res=new variable(new miString(imprimir));
             }
             break;
          //funciones con 1ª parámetro tipo matriz
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
                   throw new SyntaxErrorException("Error dimensión fuera de"
                                                  +" rango");
                }
                res=new variable(par1.getDimension(par2.intValue()));
             } else { // if (l==ctes_eval.NDIM)
                res=new variable(par1.getNumDimensiones());
             }
             match(')');
             break;
           case ctes_eval.QUITAR_FILA: case ctes_eval.QUITAR_COL:
           case ctes_eval.DETERMINANTE: case ctes_eval.MENOR:
          case ctes_eval.ADJUNTA: case ctes_eval.INVERSA:   
             {
                 l=lexema_actual.comp_lex;
                 match(l);
                 match('(');
                 par1=expr_cond();
                 compruebaTipo('M', par1);
                 if (l==ctes_eval.QUITAR_COL || l==ctes_eval.QUITAR_FILA || l==ctes_eval.MENOR) {
                    match(',');
                    par2=expr_cond();
                    compruebaTipo('D',par2);
                    if (l==ctes_eval.QUITAR_FILA) {
                       res=new variable( ((miArray)par1.valor).quitarFila(par2.intValue()));
                    } else if (l==ctes_eval.QUITAR_COL) {
                       res=new variable( ((miArray)par1.valor).quitarColumna(par2.intValue()));
                    } else if (l==ctes_eval.MENOR) {
                        match(',');
                        par3=expr_cond();
                        compruebaTipo('D',par3);
                        res=new variable(((miArray)par1.valor).menor(par2.intValue(),par3.intValue()));
                    }
                 } else if (l==ctes_eval.DETERMINANTE) {
                    res=new variable( ((miArray)par1.valor).determinante());
                 } else if (l==ctes_eval.ADJUNTA) {
                    res=new variable( ((miArray)par1.valor).adjunta());
                 } else if (l==ctes_eval.INVERSA) {
                    res=new variable( ((miArray)par1.valor).inversa());
                 }
                 match(')');
             }
             break;
          case '{':
             return new variable("",'M',new miArray(matriz()));
          case '|': 
             match('|');
             res=expr_cond();
             if (res.tipo=='M') {
                res=new variable(((miArray)res.valor).determinante());
             } else if (res.tipo=='D') {
                double d=res.doubleValue();
                res=new variable(new miDouble((d<0 ? -d : d)));
             } else {
                throw new SyntaxErrorException("Error se esperaba matriz o número");
             }
             match('|');
             break;
          default:
             if (ctes_eval.esTokenValido(lexema_actual.comp_lex)) {
                if (ctes_eval.esOpAritm(ultimo_lexema.comp_lex)) {
                   if (ultimo_lexema.comp_lex=='+' ||
                       ultimo_lexema.comp_lex=='-') {
                      throw new SyntaxErrorException("se esperaba una"+
                                                     " expresión.");
                   } else {
                      throw new SyntaxErrorException("se esperaba una"
                                               +" expresión de tipo numérico.");
                   }
                }
                throw new SyntaxErrorException("token no esperado.");
             } else {
                throw new SyntaxErrorException("token no identificado.");
             }
       }
       return res;
    }

/*** Calcula la suma de dos variables según sean sus tipos           **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
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

/*** Calcula la resta de dos variables según sean sus tipos          **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
    variable resta(variable r1, variable r2)
    {
        variable r;
        int i;

        if (r1.tipo=='D' && r2.tipo=='D') {
            r=new variable(r1.doubleValue()-r2.doubleValue());
        } else {
            String rr1=new String(r1.toString()),rr2=new String(r2.toString());
            if ((i=rr1.indexOf(rr2))>=0) { // indexOf devuelve índice donde
                               //comienza rr2, negativo si rr1 no contiene a rr2
                r=new variable('S',rr1.substring(0,i)+rr1.substring(i+
                                                    rr2.length(),rr1.length()));
            } else {
                r=new variable('S',rr1);
            }
        }
        return r;
    }


/*** Retira y evalúa la funcióncondicional de la cadena de entrada  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el valor resultado de evaluar la función             **********/
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

/*** Calcula la integral definida de una función matemática por el   **********/
/***     método de interpolación cuadrática de Simpson
/*** Parámetros: f-> cadena representando la función matemática a    **********/
/***                 integrar                                        **********/
/***             a-> límite inferior de integración                 **********/
/***             b-> límite superior de integración                 **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
    public double integral(String f, double a, double b)
                                                     throws SyntaxErrorException
    {
        variable r=null;

        if (a>b) {
            throw new SyntaxErrorException("Error en integral: 2º parámetro"
                                           +" debe ser menor que 3º parámetro");
        }
        int num_partes=(int) (Math.abs(((int) b+1)-((int) a-1))*10);
        /*dividimos intervalo en num_partes iguales*/
        if (num_partes<30) {
            num_partes=30;
            /* como mínimo dividimos el intervalo en 30 partes iguales*/
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
        if (r!=null) { //si existía en el evaluador la variable x la restauramos
           e.objeto.asigna(r);
        } else { /* no existía la variable la borramos */
            tabla_simbolos.borrar("x");
        }
        return miDouble.redondeo((suma*h/3),10);
    }

/*** Ejecuta una funcióno programa contenido en un fichero          **********/
/*** Parámetros: fichProg-> nombre del fichero conteniendo el código **********/
/***             parametros-> lista de los Parámetros reales         **********/
/*** Devuelve: el valor calculado en último lugar en la ejecución   **********/
    void parametrosSalida() {
       variable pReal,pFormal;
       for(int i=0; i<listaParamReferencias.size(); i++) {
          //nombre=(String) listaParamReferencias.elementAt(i);
          pFormal=(variable) listaParamReferencias.elementAt(i);
          if (pFormal!=null) {
             pReal=((variable)listaParamReal.elementAt(i));
             pReal.valor=pFormal.valor;
          }
       }   
    }   
    variable exec(String fichProg,Vector parametros) throws SyntaxErrorException
    {
        String programa=""; Object pSal[];
        variable r=new variable(), v;
        pila.addElement(tabla_simbolos);
        tabla_simbolos=new tab_simb(); // iniciamos variables locales
        Vector listaParamReal_aux=listaParamReal;
        Vector listaParamReferencias_aux=listaParamReferencias;
        listaParamReal=parametros;
        listaParamReferencias=new Vector();
        Vector postIncrementos_aux=postIncrementos;
        Vector postDecrementos_aux=postDecrementos;
        postIncrementos=new Vector();
        postDecrementos=new Vector();
        notifiNuevoEntorno();
        salva_entorno();
        if (!ficheros.abrir(fichProg)) {
           r=new variable("",'E',"Error al Abrir el Fichero '"+fichProg+"'");
        } else {
           programa=ficheros.getContenido(fichProg);
           r=programa(programa);
           r.nombre=""; //indica q la variable devuelta no está en el entorno al q se devuelve
           if (r.tipo=='E') {
              r=new variable("",'E',"exec "+fichProg+": linea "+numLineas+", "
                                                                 +r.toString());
           }
        }
        if (pila.size()>0) {
           tabla_simbolos=(tab_simb) pila.elementAt(pila.size()-1);
           pila.removeElementAt(pila.size()-1);
           notifiFinEntorno();
        }
        restaura_entorno();
        postIncrementos=postIncrementos_aux;
        postDecrementos=postDecrementos_aux;
        parametrosSalida();
        notificarVariablesReferenciadas();
        listaParamReal=listaParamReal_aux;
        listaParamReferencias=listaParamReferencias_aux;
        if (r.tipo=='E') {
           throw new SyntaxErrorException(r.toString());
        }
        return r;
    }

/*** Comprueba que una variable es de un tipo determinado            **********/
/*** Parámetros: tipo-> tipo a comprobar                             **********/
/***             v-> variable a chequear                             **********/
/*** Devuelve: nada                                                  **********/
    void compruebaTipo(char tipo, variable v) throws SyntaxErrorException
    {
       if (v.tipo!=tipo) {
          switch (tipo) {
             case 'S': case 's':
                throw new SyntaxErrorException("Error se esperaba una"
                                                  +" expresión de tipo cadena");
             case 'D': case 'd':
                throw new SyntaxErrorException("Error se esperaba una"
                                                +" expresión de tipo numérico");
             case 'B': case 'b':
                throw new SyntaxErrorException("Error se esperaba una"
                                                +" expresión de tipo booleano");
             case 'M': case 'm':
                throw new SyntaxErrorException("Error se esperaba una"
                                                  +" expresión de tipo matriz");
             default:
                throw new SyntaxErrorException("Error tipos no coincidentes.");
          }
       }
    }
    void compruebaTipos(String tipos,variable v) throws SyntaxErrorException {
       for(int i=0; i<tipos.length();i++) {
          if (v.tipo==tipos.charAt(i)) {
             return;
          }
       }
       throw new SyntaxErrorException("Error se esperaba una expresión de algunos de los siguientes tipos: "+tipos);
    }
       

/*** Evalua una expresión en la que interviene un identificador      **********/
/*** Parámetros: fichProg-> nombre del fichero conteniendo el código **********/
/***             parametros-> lista de los Parámetros reales         **********/
/*** Devuelve: el valor resultado de evaluar la expresión            **********/
    variable valID() throws SyntaxErrorException
    {
       entrada lexem_aux;
       lexem_aux=lexema_actual;
       match(ctes_eval.ID);
       if (lexema_actual.comp_lex=='(') { // llamada a funcióno programa
          String s=lexem_aux.objeto.nombre+".fjcn";
          match('(');
          if (lexema_actual.comp_lex==')') {
             match(')');
             return exec(s,new Vector());
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
             return caracterIndiceCadena(m);
          }
          return m;
      }
      return lexem_aux.objeto;
    }

/*** Evalua una expresión de referencia a un elemento de una matriz  **********/
/*** Parámetros: v-> entrada de la matriz en la tabla de símbolos    **********/
/*** Devuelve: el elemento referenciado                              **********/
    variable refMatriz(variable r) throws SyntaxErrorException {
       Vector indice;
       while (lexema_actual.comp_lex=='[' && r.tipo=='M') {
          match('[');
          indice=lista_expresiones_aritmeticas();
          match(']');
          r=((miArray) r.valor).elemento(indice);
       }
       if (lexema_actual.comp_lex=='[' && (r.tipo!='S')) {
          throw new SyntaxErrorException("op [] sólo aplicable a matriz"+
                                                                   " o string");
       }
       return r;
    }
    variable refMatriz(entrada v) throws SyntaxErrorException
    {
       entrada e=tabla_simbolos.busca(v);
       variable r;
       String nombre="";

       if (e==null) {
          throw new SyntaxErrorException("variable inexistente0 '"+
                                                           v.objeto.nombre+"'");
       }
       r=e.objeto;
       nombre=r.nombre;
       r=refMatriz(r);
       r.nombre=nombre;
       return r;
    }

/*** Devuelve la última linea leída de la entrada                    **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: la última línea leída de la entrada                   **********/
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

/*** Asigna un valor numérico a una variable                         **********/
/*** Parámetros: id-> nombre de la variable                          **********/
/***             valor-> valor a asignar                             **********/
/*** Devuelve: nada                                                  **********/
    public void asigna(String id, double valor)
    {
       tabla_simbolos.asigna(id,valor);
    }

/*** Devuelve una referencia al valor numérico de una variable       **********/
/*** Parámetros: id-> nombre de la variable                          **********/
/*** Devuelve: referencia al valor numérico de la variable           **********/
    public miDouble refMiDouble(String id) throws SyntaxErrorException
    {
       variable v=tabla_simbolos.busca(id).objeto;
       if (v.tipo=='D') {
          return (miDouble) v.valor;
       } else {
          throw new SyntaxErrorException("variable no es de tipo número");
       }
    }

/******************************************************************************/
/********** Cáulo Simbólico: **************************************************/
/********** Evaluaciónde la derivada de una Expresión en la entrada **********/
/******************************************************************************/
/******************************************************************************/

/*** Calcula la derivada de una función matemática en cadena la      **********/
/***     cadena de entrada                                           **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: la derivada de dicha  evaluar la expresión            **********/
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
          throw new SyntaxErrorException("se esperaba cadena o número");
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

/*** Calcula la derivada de una función matemática respecto de una   **********/
/***     variable matemática                                         **********/
/*** Parámetros: dx-> la variable matemática respecto a la que se    **********/
/***                  deriva                                         **********/
/***             expresion-> la función matemática a derivar         **********/
/*** Devuelve: la derivada de dicha  evaluar la expresión            **********/
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

/*** Evalúa la expresión de la entrada calculando su derivada        **********/
/*** Parámetros: ninguno                                             **********/
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
    	expresion=e; // expresion contiene la expresión original
    	derivada=d;  // derivada contiene la derivada de la expresión original
    }

/*** Evalúa un término de la entrada calculando su derivada          **********/
/*** Parámetros: ninguno                                             **********/
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
       expresion=e; // expresión contiene el término
       derivada=d;  // derivada contiene la derivada del término
    }

/*** Evalúa un factor de la entrada calculando su derivada           **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Evalúa una potencia de la entrada calculando su derivada        **********/
/*** Parámetros: ninguno                                             **********/
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
             if (lexema_actual.comp_lex==ctes_eval.ID) {
                 this.devuelveToken();
                 lexema_actual=new entrada('*',new variable(""));
             }
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
                throw new SyntaxErrorException("Expresión no se puede derivar");
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
                                                                +" expresión.");
                   } else {
                      throw new SyntaxErrorException("se esperaba una"
                                               +" expresión de tipo numérico.");
                   }
                }
                throw new SyntaxErrorException("token no esperado.");
             } else {
                throw new SyntaxErrorException("token no identificado.");
             }
       }
    }


/******************************************************************************/
/**************** Cálculo simbólico: ******************************************/
/**************** Simplificaciónde Expresiones matemáticas  ******************/
/******************************************************************************/
/******************************************************************************/

/*** Simplica una expresión matemática                               **********/
/*** Parámetros: e-> expresión a simplificar                         **********/
/*** Devuelve: la expresión simplificada                             **********/
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

/*** Simplica una expresión matemática representada por un árbol     **********/
/***     sintáctico                                                  **********/
/*** Parámetros: e-> expresión a simplificar                         **********/
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

/*** Comprueba sin a la expresión de un árbol sintáctico que         **********/
/***     representa a una expresión matemática se le puede aplicar   **********/
/***     alguna regla de simplificacióny en caso afirmativo aplica  **********/
/***     dicha regla al árbol sintáctico                              **********/
/*** Parámetros: e-> expresión a simplificar                         **********/
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
                        if (regla_aritmetica("2*x+3*x","(2+3)*x",a)
                           || regla_aritmetica("2*x*y+3*x*y","(2+3)*x*y",a)) {
                           return true;
                        }
                    } else if (a.hijo_izda.lex.comp_lex=='/' &&
                               a.hijo_dcha.lex.comp_lex=='/' ) {
                        if (regla_aritmetica("2*x/y+3*x/y","(2+3)*x/y",a)
                           || regla_aritmetica("x/y+3*x/y","($#1+3)*x/y",a)
                           || regla_aritmetica("3*x/y+x/y","(3+$#1)*x/y",a)
                           || regla_aritmetica("x/z+y/z","(x+y)/z",a)) {
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
                     //|| regla_aritmetica("(x+y)/z","x/z+y/z",a)
                     //|| regla_aritmetica("(x-y)/z","x/z-y/z",a)
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
                     || regla_aritmetica("(x/y)^z","x^z/y^z",a)
                     //|| regla_aritmetica("(x+y)^$#2","x^$#2+$#2*x*y+y^$#2",a)
                     ) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

/*** Forma un árbol a partir de dos ramas derechas de otro árbol     **********/
/*** Parámetros: s,b-> las 2 ramas derechas mencionadas              **********/
/*** Devuelve: el árbol sintáctico asociado a la expresión formada   **********/
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

/*** Forma un árbol a partir de una rama izquierda y otra rama       **********/
/***     derecha de otro árbol                                       **********/
/*** Parámetros: s,b-> las 2 ramas derechas mencionadas              **********/
/*** Devuelve: el árbol sintáctico asociado a la expresión formada   **********/
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


/*** Comprueba que un patrón encaja en otro y en caso afirmativo se  **********/
/***     le aplica la transformaciónque indica otro árbol           **********/
/*** Parámetros: p-> cadena que representa al patrón con el que se   **********/
/***                 ha de comparar para ver si se aplica la         **********/
/***                 transformación                                 **********/
/***             a-> cadena que representa la transformaciónque se  **********/
/***                 aplicará sobre pr en caso de que pr encaje      **********/
/***                 dentro del patrón representado por p            **********/
/***             pr->patrón sobre el que se aplicará la              **********/
/***                 transformacióncaso que encaje en p             **********/
/*** Devuelve: la cadena que representa al árbol sintáctico tras     **********/
/***     la transformación(si la hubo)                              **********/
    public String regla_aritmetica(String p, String a, String pr)
                                                     throws SyntaxErrorException
    {
        arbol_binario apr=new arbol_binario();
        try {
            apr=arbol_expr(pr);
        } catch (SyntaxErrorException e) {
            throw new SyntaxErrorException("Error en la expresión a aplicar"+
                                                                   " la regla");
        }
        regla_aritmetica(p,a,apr);
        return arbol_a_cadena(apr);
    }


/*** Comprueba que un patrón encaja en otro y en caso afirmativo se  **********/
/***     le aplica la transformaciónque indica otro árbol           **********/
/*** Parámetros: p-> cadena que representa al patrón con el que se   **********/
/***                 ha de comparar para ver si se aplica la         **********/
/***                 transformación                                 **********/
/***             a-> cadena que representa la transformaciónque se  **********/
/***                 aplicará sobre pr en caso de que pr encaje      **********/
/***                 dentro del patrón representado por p            **********/
/***             pr->árbol sintáctico al que se aplicará la          **********/
/***                 transformacióncaso que encaje en p             **********/
/*** Devuelve: true si se aplicó transformaciónfalse en caso        **********/
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

/*** Sustiuye patrones en un árbol sintáctico de una expresión       **********/
/*** Parámetros: aa-> árbol sintáctico de la expresión               **********/
/***             vp-> lista de patrones que serán sustituidos por los**********/
/***                  que ocupen la misma posiciónsobre la lista    **********/
/***                  vpr                                            **********/
/***             vpr->lista de patrones que sustituirán a los que    **********/
/***                  ocupen la misma posiciónsobre la lista vp     **********/
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

/*** Busca un árbol sintáctico de una expresión en una lista         **********/
/*** Parámetros: vp-> lista de árboles sintácticos                   **********/
/***             aa-> árbol sintáctico a buscar                      **********/
/*** Devuelve: la posicióndel árbol dentro de la lista              **********/
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
        throw new SyntaxErrorException("Error token en patrón no concuerda"
                                                       +" con token en acción");
    }

/*** Comprueba que los patrones representados por p1 y p2 son        **********/
/***     compatibles y devuelve en r1 y r2 la lista de árboles       **********/
/***     representando a los tokens significativos de un patrón      **********/
/*** Parámetros: p1-> árbol representando primer patrón              **********/
/***             p2-> árbol representando al segundo patrón          **********/
/*** Devuelve: un booleano que indica si los patrones son            **********/
/***     entre sí                                                    **********/
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
/***     primera lista también se dan entre los patrones que ocupan  **********/
/***     los mismos lugares en la segunda lista                      **********/
/*** Parámetros: vp-> primera lista de patrones                      **********/
/***             vpr-> segunda lista de patrones                     **********/
/*** Devuelve: un booleano que indica si se dan las mismas igualdades**********/
/***     entre los patrones que ocupan la misma posiciónen ambas    **********/
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

/*** Convierte una cadena representando una expresión en el árbol    **********/
/***     árbol sintáctico asociado a la misma                        **********/
/*** Parámetros: s-> expresión cuyo árbol quiere obtenerse           **********/
/*** Devuelve: el árbol sintáctico asociado a dicha expresión        **********/
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

/*** Convierte una expresión matemática a su árbol sintáctico        **********/
/***     retirándola de la entrada                                   **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el árbol sintáctico asociado a dicha expresión        **********/
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

/*** Convierte un término de una  expresión matemática a su árbol    **********/
/***     sintáctico retirándola de la entrada                        **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el árbol sintáctico asociado a dicho término          **********/
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

/*** Convierte un fáctor de una  expresión matemática a su árbol     **********/
/***     sintáctico retirándola de la entrada                        **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el árbol sintáctico asociado a dicho factor           **********/
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

/*** Convierte una potencia de una  expresión matemática a su árbol  **********/
/***     sintáctico retirándola de la entrada                        **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el árbol sintáctico asociado a dicha potencia         **********/
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
                if (lexema_actual.comp_lex==ctes_eval.ID) {
                    devuelveToken();
                    lexema_actual=new entrada('*',new variable(""));
                }
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

/*** Convierte un árbol sintáctico de una expresión a la cadena      **********/
/***     que la representa                                           **********/
/*** Parámetros: a->árbol a convertir                                **********/
/*** Devuelve: la cadena que representa al árbol sintáctico          **********/
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

/*** Evalúa el valor de la expresión aritmética asociada a un árbol  **********/
/*** Parámetros: a-> árbol a evaluar                                 **********/
/*** Devuelve: el valor resultado de evaluar el árbol sintáctico     **********/
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

/*** Evalúa el valor de la expresión aritmética asociada a un árbol  **********/
/*** Parámetros: a-> árbol a evaluar                                 **********/
/*** Devuelve: el valor resultado de evaluar el árbol sintáctico     **********/
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
                   throw new SyntaxErrorException("al evaluar árbol");
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

/*** Llamada por la instrucciónde lectura del lenguaje para leer    **********/
/***     variables respetando que la expresión que se introduzca sea **********/
/***     del mismo tipo                                              **********/
/*** Parámetros: mensa-> mensaje a mostrar al usuario                **********/
/***             v-> variable que se leerá respetando su tipo        **********/
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
          lv.setVisible(true);
          try {
             if (v.tipo=='S') {
                v.valor=new miString(lv.getValor());
                return;
             } else {
                variable r=expr_cond(lv.getValor());
                if (v.tipo==r.tipo) {
                   v.valor=r.valor;
                   return;
                } else {
                   dialogo.cuadro_mensaje("El valor debe ser de tipo "+nombreTipo(v.tipo),
                                  "Mensaje de Error");
                }
             }
          } catch (SyntaxErrorException ex) {
             dialogo.cuadro_mensaje("Error la expresión introducida debe ser de tipo "+
                             nombreTipo(v.tipo),"Mensaje de Error");
          }
       }

    }

/*** Transforma el carácter que representa el tipo de una variable   **********/
/***     en su nombre                                                **********/
/*** Parámetros: t-> carácter que representa el tipo                 **********/
/*** Devuelve: el nombre del tipo                                    **********/
    public String nombreTipo(char t)
    {
       switch(t) {
          case 'S':
             return "CADENA";
          case 'D':
             return "número";
          case 'M':
             return "MATRIZ";
          case 'B':
             return "BOOLEANO";
          default:
             return null;
       }
    }

/*** Dibuja una ventana de mensaje                                   **********/
/*** Parámetros: s-> mensaje a mostrar dentro de la ventana          **********/
/***             tipo-> mensaje a mostrar en el título de la ventana **********/
/***                 que indicada el tipo de mensaje (error, info...)**********/
/*** Devuelve: la cadena que representa al árbol sintáctico          **********/
    /*public static void cuadro_mensaje(String s, String tipo)
    {
       JFrame frame=new JFrame();
       Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
       dialogo dlg=new dialogo(s,frame,tipo,true);
       dlg.setLocation((d.width - dlg.getSize().width) / 2,
                                         (d.height - dlg.getSize().height) / 2);
       dlg.setVisible(true);
    }*/



/******************************************************************************/
/******************************************************************************/
/************     funciones de comunicacióncon otros objetos   ***************/
/******************************************************************************/
/******************************************************************************/

/*** Añade un objeto a la lista de elementos que serán notificados   **********/
/***     como hace la case CalcWindow con su objeto listaVariables   **********/
/***     que añade a la lista de notificados de su objeto evaluador  **********/
/***     para que cada vez que se crea o actualiza una variable se   **********/
/***     se actualice en la interfaz gráfica de usuario de forma que **********/
/***     que este pueda ver los cambios en las variables de forma    **********/
/***     instántane a la ejecuciónde programas y/o comandos         **********/
/*** Parámetros: n-> referencia al objeto que será notificado        **********/
/*** Devuelve: nada                                                  **********/
    public void addNotificado(notificado n) {
       notificados.addElement((Object) n);
    }

/*** Fija el valor de la variable notificacion de forma que según    **********/
/***     el valor que esta tenga se notificará o no a los objetos    **********/
/***     de la lista de notificados de los eventos notificables      **********/
/*** Parámetros: b-> nuevo valor de notificacion                     **********/
/*** Devuelve: nada                                                  **********/
    public void setNotificacion(boolean b)
    {
       notificacion=b;
    }

/*** 'Evento' de notificacióna la lista de notificados de la        **********/
/***     creaciónde una nueva variable                              **********/
/*** Parámetros: ve-> referencia a la nueva variable creada          **********/
/*** Devuelve: nada                                                  **********/
    void notifiNuevaVariable(String nombre)
    {
       //+++System.out.println("nombre: "+nombre);
       if (tabla_simbolos==null) {
          System.out.println("TablaSinbolos null");
			 return;
       }
       if (nombre==null) {
          System.out.println("nombre null");
			 return;
       }
             
       //System.out.println("Busco: "+nombre);
		 entrada evb=tabla_simbolos.busca(nombre);
		 if (evb!=null) {
	       variable vb=evb.objeto;
	       if (vb!=null) {
	          if (notificacion) { // esto enlentece mucho la ejecució
	             for(int i=0;i<notificados.size();i++) {
	                ((notificado) notificados.elementAt(i)).nuevaVariable(vb.nombre,
	                                                              vb.StringValue());
	             }
	          }
	       }
		 } else {
		    System.out.println("Variable no encontrada: "+nombre);
		 }
    }
    void notifiNuevaVariable(variable ve) {
       notifiNuevaVariable(ve.nombre);
    }
/*** Idem anterior sobrecargado                                      **********/
/*** Parámetros: e-> entrada de la tabla de símbolos que contiene    **********/
/***     la nueva variable creada                                    **********/
/*** Devuelve: nada                                                  **********/
    void notifiNuevaVariable(entrada e)
    {
       notifiNuevaVariable(e.objeto.nombre);
    }


/*** 'Evento' de notificaciónde que se ha pasado a un nuevo entorno **********/
/***     o ámbito de ejecució, p.ej. cuando se llama a una función **********/
/***     o programa de usuario, se inicia un nuevo entorno de        **********/
/***     variables locales                                           **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    void notifiNuevoEntorno()
    {
       if (notificacion) {
          for(int i=0;i<notificados.size();i++) {
             ((notificado) notificados.elementAt(i)).nuevoEntorno();
          }
       }
    }

/*** 'Evento' de notificaciónde que se ha salido del último entorno **********/
/***     o ámbito de ejecución                                      **********/
/*** Parámetros: ninbuno                                             **********/
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
/************     métodos y funciones auxiliares                ***************/
/******************************************************************************/
/******************************************************************************/

/*** Devuelve el número de símbolos que hay en la tabla de símbolos  **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: el número total de símbolos que hay en la tabla de    **********/
/***     de símbolos                                                 **********/
    public int getNumSimbolos()
    {
       return tabla_simbolos.getNumSimbolos();
    }

/*** Devuelve el elemento i-ésimo de la tabla de símbolos            **********/
/*** Parámetros: i-> posicióndel elemento a devolver                **********/
/*** Devuelve: la variable de la tabla de símbolos que ocupa la      **********/
/*** posicióni                                                      **********/
    public variable getSimbolo(int i)
    {
       return tabla_simbolos.getSimbolo(i);
    }

/*** Devuelve el último token reconocido por el analizador léxico    **********/
/***    de la entrada                                                **********/
/*** Parámetros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
    public int getUltimoToken()
    {
       return ultimoToken;
    }

/*** Abre un fichero en la lista de ficheros abiertos                **********/
/*** Parámetros: fichero-> fichero a abrir                           **********/
/*** Devuelve: booleano que indica si hubo o no éxito en apertura    **********/
    public boolean abrirFichero(String fichero)
    {
       return ficheros.abrir(fichero);
    }

/*** Devuelve el contenido de un fichero previamente abierto         **********/
/*** Parámetros: fichero-> nombre del fichero del que se quiere      **********/
/***     obtener el contenido                                        **********/
/*** Devuelve: el contenido del fichero en forma de una cadena       **********/
    public String getContenidoFichero(String fichero)
    {
       return ficheros.getContenido(fichero);
    }

/*** Salva el entorno de ejecuciónactualmente en curso              **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Restaura último entorno de ejecuciónsalvado                    **********/
/*** Parámetros: ninguno                                             **********/
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


/*** Devuelve la posiciónde una cadena dentro de otra ignorando     **********/
/***     mayúsculas                                                  **********/
/*** Parámetros: s1-> cadena dentro de la que se mira                **********/
/***             s2-> cadena que se busca dentro de s1               **********/
/*** Devuelve: un entero >=0 que representa la posicióna partir de  **********/
/***     la cual aparece s2 en s1 (ignorando mayúsculas) o un entero **********/
/***     <=0 en otro caso                                            **********/
    int indiceDe(String s1, String s2)
    {
       s1=new String(s1);
       s2=new String(s2);
       return s1.toUpperCase().indexOf(s2.toUpperCase());
    }

/*** Inserta un espacio entre el nombre de una funcióny su argumento**********/
/***     en caso de que sea necesario                                **********/
/*** Parámetros: expresion-> la expresión en cuestión                **********/
/*** Deuelve: la expresión con un espacio insertao en caso de que sea**********/
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
/*********** funciones utilizadas en el cálculo de la derivada: ***************/
/******************************************************************************/
/******************************************************************************/

/*** Quita exceso de paréntesis circundando a una expresión          **********/
/*** Parámetros: s-> expresión                                       **********/
/*** Deuelve: la expresión sin los paréntesis externos sobrantes     **********/
    String quita_parentesis(String s)
    {
        if (s!=null && s.length()>0) {
            while (s.charAt(0)=='(' && s.charAt(s.length()-1)==')') {
                s=s.substring(1,s.length()-1);
            }
        }
        return s;
    }

/*** Dice si dos expresiones son iguales quitando los paréntesis     **********/
/***     innecesarios de la primera                                  **********/
/*** Parámetros: s1,s2-> las expresiones en cuestión                 **********/
/*** Deuelve: si ambas cadenas representan la misma expresión        **********/
    boolean igualA(String s1, String s2)
    {
        s1=quita_parentesis(s1);
        return(s1.equalsIgnoreCase(s2));
    }

/*** Nos dice si una expresión es igual a cero                       **********/
/*** Parámetros: s1-> la expresión en cuestión                       **********/
/*** Deuelve: si la expresión s1 es igual a cero o no                **********/
    boolean igualAcero(String s1)
    {
        return(igualA(s1,"0") || igualA(s1,"-0"));
    }

/*** Nos dice si una cadena representa un número                     **********/
/*** Parámetros: s1-> la cadena en cuestión                          **********/
/*** Deuelve: si la cadena representa o no un número                 **********/
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

/*** Convierte a número una cadena                                   **********/
/*** Parámetros: s-> la cadena en cuestión                           **********/
/*** Deuelve: el valor del número representado por s                 **********/
    double valor_doble(String s)
    {
        s=quita_parentesis(s);
        return(Double.valueOf(s).doubleValue());
    }

/*** Hace simplificaciones triviales sobre la exponencial            **********/
/*** Parámetros: e-> la expresión a simplificar                      **********/
/*** Deuelve: la expresión simplificada                              **********/
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
/*** Parámetros: n-> la expresión a simplificar                      **********/
/*** Deuelve: la expresión simplificada                              **********/
    String simplifica_ln(String n)
    {
        if (n.equalsIgnoreCase("e")) {
            return("1");
        }
        return("ln"+necesitaEspacio(n)+n);
    }

/*** Hace simplificaciones triviales sobre la raíz cuadrada          **********/
/*** Parámetros: r-> la expresión a simplificar                      **********/
/*** Deuelve: la expresión simplificada                              **********/
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

/*** Hace simplificaciones triviales sobre la multiplicación        **********/
/*** Parámetros: f1,f2-> los dos factores                            **********/
/*** Deuelve: la expresión simplificada                              **********/
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

/*** Hace simplificaciones triviales sobre la división               **********/
/*** Parámetros: d1-> dividendo                                      **********/
/***             d2-> divisor                                        **********/
/*** Deuelve: la expresión simplificada                              **********/
    String simplifica_division(String d1, String d2) throws SyntaxErrorException
    {
        if (igualAcero(d2)) {
            throw new SyntaxErrorException("Error de división por 0");
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
/*** Parámetros: s1,s2-> los sumandos                                **********/
/*** Deuelve: la expresión simplificada                              **********/
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
/*** Parámetros: r1-> minuendo                                       **********/
/***             r2-> sustraendo                                     **********/
/*** Deuelve: la expresión simplificada                              **********/
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
/*** Parámetros: p1-> base                                           **********/
/***             p2-> exponente                                      **********/
/*** Deuelve: la expresión simplificada                              **********/
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
    public static void main(String args[]) {
        evaluador e=new evaluador();
        //variable v=e.programa("a={{1,2,3},{4,-5,6},{7,8,9}};");
        //v=e.programa("adjunta(a);");
        //variable v=e.programa("deriv(\"3x^2\")");
        //System.out.println(v);
        String expr="";
        //System.out.println("args.length: "+args.length);
        if (args.length==0) {
            java.util.Scanner keyboard = new java.util.Scanner(System.in);
            System.out.println("Evaluador de Expresiones (c) 1999-2011 Francisco Javier Criado Navarro");
            System.out.println("Teclea instrucciones terminadas en ; y pulsa intro");
            System.out.println("Las variables se conservan de una instrucción para otra.");
            System.out.println("Teclea Ctrl+c para terminar...");
            e.programa("a=5;a!;");
            while(true) {
                expr="";
                while (expr.indexOf(";")<0) {
                   expr+=keyboard.nextLine();
                }
                System.out.println(expr+" ==> "+e.programa(expr)+";");
            }
        } else {
            for(int i=0;i<args.length;i++) {
                expr+=args[i];
            }
            System.out.println(e.programa(expr+";"));
        }
    }


} //fin class evaluador

