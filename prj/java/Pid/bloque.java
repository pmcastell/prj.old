
import java.util.Vector;

public class bloque
{
   private static int tipoAdyacencia=4; // variable clase determina tipo adyac. 
   public char elemento;
   public boolean marcado=false;
   public int x1, x2, y1, y2;
   public bloque() {
   }
   public bloque(char elemento, int x1, int x2, int y1, int y2)
   {
      this.elemento=elemento;
      this.x1=x1;
      this.x2=x2;
      this.y1=y1;
      this.y2=y2;
   }
   public bloque(char elemento, int x1, int x2, int y1, int y2, boolean marcado)
   {
      this(elemento,x1,x2,y1,y2);
      this.marcado=marcado;
   }
   public int tamano() {
      return x2-x1+1;
   }
   public static void setAdyacencia(int a) {
      if (a==4 || a==8) {
         tipoAdyacencia=a;
      } else {
         tipoAdyacencia=4;
         dialogo.cuadro_mensaje("La Adyacencia sólo puede ser 4 u 8. Se deja valor por defecto: 4","Error");
      }
   }      

   public static boolean es4_Adyacente(bloque b1, bloque b2)
   {
      if ((Math.abs(b1.y1-b2.y2)==1 || Math.abs(b1.y2-b2.y1)==1) &&
          ((b1.x1<=b2.x1 && b2.x2<=b1.x2) || (b2.x1<=b1.x1 && b1.x2<=b2.x2))) {
         return true;
      } else
      if ((Math.abs(b1.x1-b2.x2)==1 || Math.abs(b1.x2-b2.x1)==1) &&
          ((b1.y1<=b2.y1 && b2.y2<=b1.y2) || (b2.y1<=b1.y1 && b1.y2<=b2.y2))) {
         return true;
      }
      return false;
   }
   static boolean es8_Adyacente(bloque b1, bloque b2) {
      if ((Math.abs(b1.x1-b2.x2)==1 || Math.abs(b1.x2-b2.x1)==1) &&
          (Math.abs(b1.y1-b2.y2)==1 || Math.abs(b1.y2-b2.y1)==1)) {
         return true;
      } else {
         return es4_Adyacente(b1,b2);
      }
   }


   public boolean esAdyacente(bloque b2) {
      if (this.tipoAdyacencia==4) {
         return es4_Adyacente(this,b2);
      } else { // if (this.tipoAdyacencia==8) { //podríamos añadir tipos adyacenc.
         return es8_Adyacente(this,b2);
      }
   }

   public String toString() {
      return("["+elemento+":"+x1+","+x2+","+y1+","+y2+"]");
   }
   public boolean estaEnBloque(int x, int y) {
      if (x1<= x && x<=x2 && y1<=y && y<=y2) {
         return true;
      }
      return false;
   }      


}
