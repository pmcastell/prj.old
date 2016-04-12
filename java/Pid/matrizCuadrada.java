
import java.util.Vector;
import java.io.*;
import java.io.Reader;
import java.lang.IndexOutOfBoundsException;

public class matrizCuadrada
{
   private char [][] m;
   private char etiq='A';
   private int dim=0;
   public matrizCuadrada() {

   }
   public matrizCuadrada(int dim) {
      m=new char[dim][dim];
      this.dim=dim;
   }
   public matrizCuadrada (String s) {
     this(s.indexOf('\n'));
     int x,y=dim-1, sLen=s.length();
     int i=0;
     while (i<sLen) {
        x=0;
        while(i<sLen && s.charAt(i)!='\n') {
           setElement(x,y,s.charAt(i));
           x++; i++;
        }
        y--; i++;
     }
   }
   public static matrizCuadrada fileToMatrizCuadrada(BufferedReader r) {
      String s="";
      String l;
      matrizCuadrada res=new matrizCuadrada();
      try {
         l=r.readLine();
         while (l!=null) {
            s+=l;
            l=r.readLine();
            if (l!=null) {
               s+='\n';
            }   
         }
         r.close();
         res=new matrizCuadrada(s);
      } catch (Exception ex) {
         dialogo.cuadro_mensaje("Ocurrió una excepción: "+ex,"Error");
      }
      return res;
   }


   public int dimension() {
      return dim;
   }
   public void setElement(int x, int y, char valor) {
//         m[dim-y-1][x]=valor;
         m[x][y]=valor;
   }
   public char getElement(int x, int y)
   {
          return m[x][y];
//          return m[dim-y-1][x];
   }

   public String toString() {
      String r="";
      for(int y=dim-1; y>=0; y--) {
         for(int x=0; x<dim; x++) {
            r+=getElement(x,y);
         }
         if (y!=0) {
            r+="\n";
         }
      }
      return r;
   }

/*   int buscaElemento(int x,int y, conjunto c)
   {
     lista l;
     bloque b;

     for(int i=0; i<c.size(); i++) {
        l=(lista) c.elementAt(i);
        for(int j=0; j<l.size(); j++) {
           b=(bloque) l.elementAt(j);
           if (b.estaEnBloque(x,y)) {
              return i;
           }
        }
     }
     return 0;
   }*/
   public void etiquetaConexas(conjunto c) {
      lista l;
      bloque b;

      for(int i=0; i<c.size(); i++) {
         l=(lista) c.elementAt(i);
         for(int j=0; j<l.size(); j++) {
            b=(bloque) l.elementAt(j);
            for(int x=b.x1; x<=b.x2; x++) {
               for(int y=b.y1; y<=b.y2; y++) {
                  this.setElement(x,y,etiq);
               }
            }
         }
         this.etiq++;
      }
   }


/*   public String etiquetaConexas(conjunto  c) {
     String r="";
     int e;

     for(int y=matriz.dimension()-1; y>=0; y--) {
        for(int x=0; x<matriz.dimension(); x++) {
           e=matriz.getElement(x,y);
           if (e==1) {
              r+=((char) ('A'+buscaElemento(x,y,c)));
           } else {
              r+='0';
           }
        }
        r+='\n';
     }
     return r;
  }*/


}
