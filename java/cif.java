import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class cif { 
   public static boolean esCif(String cif){
    
      //_log.debug("CIF "+cif);
    
      Pattern cifPattern =
         Pattern.compile("([ABCDEFGHKLMNPQSabcdefghklmnpqs])(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)(\\d)([abcdefghijABCDEFGHIJ0123456789])");
    
      Matcher m = cifPattern.matcher(cif);
      if(m.matches()){
         //_log.debug("CIF cumple el patrón:");
    
         //Sumamos las posiciones pares de los números centrales (en realidad posiciones 3,5,7 generales)
         int sumaPar = Integer.parseInt(m.group(3))+Integer.parseInt(m.group(5))+Integer.parseInt(m.group(7));
    
         //_log.debug("Suma par: "+sumaPar);
    
         //Multiplicamos por 2 las posiciones impares de los números centrales (en realidad posiciones 2,4,6,8 generales)
         //Y sumamos ambos digitos: el primer digito sale al dividir por 10 (es un entero y quedará 0 o 1)
         //El segundo dígito sale de modulo 10
         int sumaDigito2 = ((Integer.parseInt(m.group(2))*2)% 10)+((Integer.parseInt(m.group(2))*2)/ 10);
         int sumaDigito4 = ((Integer.parseInt(m.group(4))*2)% 10)+((Integer.parseInt(m.group(4))*2)/ 10);
         int sumaDigito6 = ((Integer.parseInt(m.group(6))*2)% 10)+((Integer.parseInt(m.group(6))*2)/ 10);
         int sumaDigito8 = ((Integer.parseInt(m.group(8))*2)% 10)+((Integer.parseInt(m.group(8))*2)/ 10);
    
         int sumaImpar = sumaDigito2 +sumaDigito4 +sumaDigito6 +sumaDigito8 ;
         //_log.debug("Suma impar: "+sumaImpar);
         int suma = sumaPar +sumaImpar;
         int control = 10 - (suma%10);
         //La cadena comienza en el caracter 0, J es 0, no 10
         if (control==10)
            control=0;
         String letras = "JABCDEFGHI";
         //_log.debug("Control: "+control+" ó "+letras.substring(control,control+1));
    
         //El dígito de control es una letra
         if (m.group(1).equalsIgnoreCase("K") || m.group(1).equalsIgnoreCase("P") ||
            m.group(1).equalsIgnoreCase("Q") || m.group(1).equalsIgnoreCase("S")){
    
            //_log.debug("Tiene que ser una letra");
            if (m.group(9).equalsIgnoreCase(letras.substring(control,control+1)))
               return true;
            else
               return false;
         }
         //El dígito de control es un número
         else if (m.group(1).equalsIgnoreCase("A") || m.group(1).equalsIgnoreCase("B") ||
            m.group(1).equalsIgnoreCase("E") || m.group(1).equalsIgnoreCase("H")){
    
            //_log.debug("Tiene que ser un numero");
            if (m.group(9).equalsIgnoreCase(""+control))
               return true;
            else
               return false;
         }
         //El dígito de control puede ser un número o una letra
         else{
            if (m.group(9).equalsIgnoreCase(letras.substring(control,control+1))||
               m.group(9).equalsIgnoreCase(""+control))
               return true;
            else
               return false;
         }
      }
      else
         return false;
    
   }
   public static void main(String args[]) {
      if (args.length<1) {
         System.out.println("Error: debes suministrar un C.I.F.");
      } else if (esCif(args[0])) {
         System.out.println("C.I.F. "+args[0]+" es correcto.");
      } else {
         System.out.println("C.I.F. "+args[0]+" es Incorrecto.");
      }
      System.out.println("Pulsa una tecla para terminar.....");
      try {
         System.in.read();
      } catch (Exception ex) {
      }
   }
}
      