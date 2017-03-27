/******************************************************************************/
/******************         ctes_eval.java      *******************************/
/******************************************************************************/
package mieval; 
public final class ctes_eval {
    //constantes representando los tokens
   private static final int PRIMERO       =256;
   private static final int PRIMER_OP_ARIT=256;
   public  static final int ACOS          =256;
   public  static final int ASEN          =257;
   public  static final int ATAN          =258;
   public  static final int CEIL          =259;
   public  static final int COS           =260;
   public  static final int COSH          =261;
   public  static final int EXP           =262;
   public  static final int ABS           =263;
   public  static final int FLOOR         =264;
   public  static final int FMOD          =265;
   public  static final int LN            =266;
   public  static final int SEN           =267;
   public  static final int SENH          =268;
   public  static final int SQRT          =269;
   public  static final int TAN           =270;
   public  static final int TANH          =271;
   public  static final int ROUND	      =272;
   public  static final int TRUNC	      =273;
   public  static final int DATEDIF       =274;
   public  static final int FACTORIAL     =275;
   public  static final int MENOS_UNARIO  =276;

   public  static final int LOG           =277;
   public  static final int DIV           =278;
   public  static final int MOD           =279;
   public  static final int ATAN2         =280;
   private static final int ULTIMO_OP_ARIT=281;

   public  static final int ASSIGN        =291;
   public  static final int MAS_IGUAL     =292;
   public  static final int MENOS_IGUAL   =293;
   public  static final int POR_IGUAL     =294;
   public  static final int DIVIDIDO_IGUAL=295;
   public  static final int POTENCIA_IGUAL=296;
   public  static final int MOD_IGUAL     =297;
                
   public  static final int DERIVADA      =298;
   public  static final int NOT           =299;
   public  static final int OR            =300;
   public  static final int AND           =301;
   public  static final int IIF           =302;
   public  static final int MENOR_IGUAL   =303;
   public  static final int MAYOR_IGUAL   =304;
   public  static final int DISTINTO      =305;
   public  static final int IGUAL         =306;
   public  static final int SIMPLIFICA    =307;
   public  static final int EVALUA        =308;


   public  static final int NUM           =309;
   public  static final int PI            =310;
   public  static final int E             =311;
   public  static final int ID            =312;
   public  static final int LITERAL_CADENA=313;
   public  static final int LITERAL_FECHA =314;

   public  static final int EOF           =315;
   public  static final int FIN           =(int) ';';
   public  static final int DIM           =316;
   public  static final int REDIM         =317;

   public  static final int TRUE          =318;
   public  static final int FALSE         =319;
   public  static final int INTEGRAL      =320;


   public  static final int FOR           =321;
   public  static final int IF            =322;
   public  static final int ELSE          =323;
   public  static final int ELSEIF        =324;
   public  static final int WHILE         =325;
   public  static final int ACUMULADOR    =326; //registro ultimo valor calculado
   public  static final int CTE           =327;
   public  static final int PTR           =328;
   public  static final int EXEC          =329;
   public  static final int PARAMETERS    =330;
   public  static final int INC           =331;
   public  static final int DEC           =332;
   public  static final int SUBSTR        =333;
   public  static final int TOUPPER       =334;
   public  static final int TOLOWER       =335;
   public  static final int TYPE          =336;
   public  static final int DERIV         =337;
   public  static final int LEN           =338;
   public  static final int INT           =339;
   public  static final int DIMENS        =340;
   public  static final int NDIM          =341;
   public  static final int QUITAR_FILA   =342;
   public  static final int QUITAR_COL    =343;
   public  static final int RANGO         =344;
   public  static final int MENOR         =345;
   public  static final int DETERMINANTE  =346;
   public  static final int ADJUNTA       =347;
   public  static final int INVERSA       =348;
   public  static final int RETURN        =349;
   public  static final int LEER          =350;
   public  static final int LEERMATRIZ    =351;
   public  static final int PRINT         =352;
   public  static final int PRINTCOLOR    =353;
   public  static final int CLEAR         =354;
   public  static final int ANIO_BISIESTO =355;
   public  static final int FECHA_VALIDA  =356;
   public  static final int FECHA_ACTUAL  =357;
   public  static final int FECHA_NUM     =358;
   public  static final int HORA_ACTUAL   =359;
   private static final int ULTIMO        =359; // con esto controlamos que un token sea v�lido
                                       // si est� entre PRIMERO Y ULTIMO
   public static String nombres[]={"acos","asen","atan","ceil","cos",
      "cosh","exp","abs","floor","fmod","ln","sen","senh","sqrt",
      "tan","tanh","-","log","div","mod","atan2","=","derivada","!","||",
      "&&","iif","<=",">=","!=","==","simp","eval","numero","pi",
      "e","IDENTIFICADOR","LITERAL_CADENA","EOF","","dim","redim","true","false",
      "integral","for","if","else","elseif","while","?","CTE","ptr","exec",
      "parameters","inc","dec","substr","toupper","tolower","type","deriv","len","int",
      "dimens","ndim", "quitarFila","quitarColumna","rango","menor","det","adjunta","inversa",
      "return","leer"
   };
   public static boolean esOpAsignacion(int c) {
      return c>=ASSIGN && c<=MOD_IGUAL;
   }

	public static boolean esOpAritmElemental(int c) {
	   return c=='+' || c=='-' || c=='*' || c=='/' || c=='^' || c==MOD;
	}
   public static boolean esOpAritm(int c)
   {
        return (PRIMER_OP_ARIT<=c && c<=ULTIMO_OP_ARIT) || esOpAritmElemental(c);
   }

   public static boolean esMonoToken(int c)
   {
       if (c=='+' || c=='-' || c=='*' || c=='/' || c=='^' || c=='<' ||
                  c=='>' || c=='=' || c=='(' || c==')' || c=='{' || c=='}' ||
                  c=='[' || c==']' || c=='?' || c==',' ||
                  c==(char) ctes_eval.FIN) {
           return true;
       } else {
           return false;
       }
   }

   public static boolean esTokenValido(int c)
   {
       if (PRIMERO<=c && c<=ULTIMO) {
           return true;
       } else {
           return esMonoToken(c);
       }
   }
   public static String nombreToken(int c)
   {
       if (PRIMERO<=c && c<=ULTIMO) {
           return new String(nombres[c-256]);
       } else if (esMonoToken(c)) {
           return (""+(char) c);
       } else {
           return("Token no conocido");
       }
   }

   public static int prioridad(int c)
   {
        switch(c) {
            case '+': case '-': case MENOS_UNARIO:
               return(0);
            case '*': case '/':
               return(1);
            case '^':
               return(2);
            case ctes_eval.COS: case ctes_eval.ACOS: case ctes_eval.SEN:
            case ctes_eval.ASEN: case ctes_eval.TAN: case ctes_eval.ATAN:
            case ctes_eval.EXP: case ctes_eval.LN:
            case ctes_eval.LOG: case ctes_eval.SQRT: case ctes_eval.ATAN2:
            case ctes_eval.SENH: case ctes_eval.COSH: case ctes_eval.TANH:
               return(3);
            default:
               return(4);
        }
   }
   boolean esRelacional(int r)
   {
       return(r=='<' || r=='>' || r=='=' || r==ctes_eval.MENOR_IGUAL ||
              r==ctes_eval.MAYOR_IGUAL || r==ctes_eval.DISTINTO ||
              r==ctes_eval.AND || r==ctes_eval.OR);
   }




}


