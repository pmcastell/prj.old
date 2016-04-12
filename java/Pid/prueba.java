
import java.util.Vector;
import java.io.*;
import java.io.Reader;

public class prueba
{
   public static void main(String arg[])
   {
      BufferedReader r;
      try {
         r=new BufferedReader(new InputStreamReader(new FileInputStream("prueba.txt")));
         String l=r.readLine();
         int len=l.length(), fila=0, columna=0;
         matrizCuadrada matriz=new matrizCuadrada(len);
         while (l!=null) {
            for(columna=0; columna<len; columna++) {
               matriz.setElement(fila,columna,l.charAt(columna));
            }
            fila++;
            l=r.readLine();
         }
         System.out.println(matriz);
         quadArbol a=new quadArbol(matriz);
         String s=a.toString();
         System.out.println("El arbol es : "+s);
         conjunto c=a.buscaConexas('1');
         s=a.toString();
         System.out.println("El arbol es : "+s);
         r.close();
      } catch (Exception ex) {
         System.out.println("Ocurrió la siguiente excepción: "+ex);
         ex.printStackTrace();
      }


   }
}
