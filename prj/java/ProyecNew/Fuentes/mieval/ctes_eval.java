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
   public  static final int MENOS_UNARIO  =272;

   public  static final int LOG           =273;
   public  static final int DIV           =274;
   public  static final int MOD           =275;
   public  static final int ATAN2         =276;
   private static final int ULTIMO_OP_ARIT=276;

   public  static final int ASSIGN        =277;
   public  static final int DERIVADA      =278;
   public  static final int NOT           =279;
   public  static final int OR            =280;
   public  static final int AND           =281;
   public  static final int IIF           =282;
   public  static final int MENOR_IGUAL   =283;
   public  static final int MAYOR_IGUAL   =284;
   public  static final int DISTINTO      =285;
   public  static final int IGUAL         =286;
   public  static final int SIMPLIFICA    =287;
   public  static final int EVALUA        =288;


   public  static final int NUM           =289;
   public  static final int PI            =290;
   public  static final int E             =291;
   public  static final int ID            =292;
   public  static final int LITERAL_CADENA=293;

   public  static final int EOF           =294;
   public  static final int FIN           =(int) ';';
   public  static final int DIM           =295;
   public  static final int REDIM         =296;

   public  static final int TRUE          =297;
   public  static final int FALSE         =298;
   public  static final int INTEGRAL      =300;


   public  static final int FOR           =301;
   public  static final int IF            =302;
   public  static final int ELSE          =303;
   public  static final int ELSEIF        =304;
   public  static final int WHILE         =305;
   public  static final int ACUMULADOR    =306; //registro último valor calculado
   public  static final int CTE           =307;
   public  static final int PTR           =308;
   public  static final int EXEC          =309;
   public  static final int PARAMETERS    =310;
   public  static final int INC           =311;
   public  static final int DEC           =312;
   public  static final int SUBSTR        =313;
   public  static final int TOUPPER       =314;
   public  static final int TOLOWER       =315;
   public  static final int TYPE          =316;
   public  static final int DERIV         =317;
   public  static final int LEN           =318;
   public  static final int INT           =319;
   public  static final int DIMENS        =320;
   public  static final int NDIM          =321;
   public  static final int RETURN        =322;
   public  static final int LEER          =323;
   private static final int ULTIMO        =323; // con esto controlamos que un token sea válido
                                       // si está entre PRIMERO Y ULTIMO
   public static String nombres[]={"acos","asen","atan","ceil","cos",
      "cosh","exp","abs","floor","fmod","ln","sen","senh","sqrt",
      "tan","tanh","-","log","div","mod","atan2","=","derivada","!","||",
      "&&","iif","<=",">=","!=","==","simp","eval","numero","pi",
      "e","IDENTIFICADOR","LITERAL_CADENA","EOF","","dim","redim","true","false",
      "integral","for","if","else","elseif","while","?","CTE","ptr","exec",
      "parameters","inc","dec","substr","toupper","tolower","type","deriv","len","int",
      "dimens","ndim", "return","leer"
   };
   public static boolean esOpAritm(int c)
   {
        return (PRIMER_OP_ARIT<=c && c<=ULTIMO_OP_ARIT) || c=='+'
                || c=='-' || c=='*' || c=='/' || c=='^';
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


