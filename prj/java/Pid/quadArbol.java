
import java.util.Vector;

public class quadArbol
{
   public char elemento;
   public boolean marcado=false;
   public quadArbol hijo1, hijo2, hijo3, hijo4;
   private int dim=128;



   public quadArbol(char elemento) {
      this.elemento=elemento;
      hijo1=hijo2=hijo3=hijo4=null;
   }
   public quadArbol(quadArbol h1, quadArbol h2, quadArbol h3, quadArbol h4) {
      hijo1=h1;
      hijo2=h2;
      hijo3=h3;
      hijo4=h4;
      elemento='\0'; // nodo interno
   }
   public quadArbol(char e,quadArbol h1,quadArbol h2,quadArbol h3,quadArbol h4) {
      this(h1,h2,h3,h4);
      this.elemento=e;
   }

   public quadArbol(matrizCuadrada matriz) {
      quadArbol a;
      this.dim=matriz.dimension();
      a=construyeArbol(matriz, 0, dim-1, 0, dim-1);
      this.elemento=a.elemento;
      this.hijo1=a.hijo1;
      this.hijo2=a.hijo2;
      this.hijo3=a.hijo3;
      this.hijo4=a.hijo4;
   }

   quadArbol construyeArbol(matrizCuadrada m, int x1, int x2, int y1, int y2)
   {
      if (x2-x1==0) { // es un solo punto
         return new quadArbol(m.getElement(x1,y1));
      } else {
         quadArbol h1, h2, h3, h4;
         h1=construyeArbol(m,x1,x1+(x2-x1-1)/2,y1+(y2-y1+1)/2,y2);
         h2=construyeArbol(m,x1+(x2-x1+1)/2,x2,y1+(y2-y1+1)/2,y2);
         h3=construyeArbol(m,x1,x1+(x2-x1-1)/2,y1,y1+(y2-y1-1)/2);
         h4=construyeArbol(m,x1+(x2-x1+1)/2,x2,y1,y1+(y2-y1-1)/2);
         if (h1.elemento==h2.elemento && h2.elemento==h3.elemento &&
             h3.elemento==h4.elemento && h1.hijo1==null) {
            return new quadArbol(h1.elemento);
         } else {
            return new quadArbol(h1,h2,h3,h4);
         }
      }
   }                

   public lista concatenaListas(lista l1, lista l2)
   {
      lista l=new lista();
      for(int i=0; i<l1.size(); i++) {
         l.addElement(l1.elementAt(i));
      }
      for(int i=0; i<l2.size(); i++) {
         l.addElement(l2.elementAt(i));
      }
      return l;
   }      
   public void procesaBloque(conjunto c, bloque b)
   {
      int i,j;
      lista lb=new lista();
      lb.addElement(b);
      for(i=0; i<c.size(); i++) {
         lista l=(lista) c.elementAt(i);
         for(j=0; j<l.size(); j++) {
            if (b.esAdyacente((bloque) l.elementAt(j))) {
               lb=concatenaListas(lb, l);
               c.removeElementAt(i);
               i--;
               break;
            }
         }
      }
      c.addElement(lb);
   }   


   public conjunto buscaConexas(char color) {
      conjunto c=new conjunto();
      bloque bl;

      desMarca();
      for(int y=0; y<dim; y++) {
         for (int x=0; x<dim;) {
            bl=buscayMarca(x,y);
            if (bl.elemento==color && !bl.marcado) {
               procesaBloque(c,bl);
            }
            x+=bl.tamano();
         }
      }
      return c;
   }

   public boolean estaEnNorOeste(int x, int y, int x1, int x2, int y1, int y2)
   {
      if (x1<=x && x<=x1+(x2-x1-1)/2 && y1+(y2-y1+1)/2<=y && y<=y2) {
         return true;
      }
      return false;
   }

   public boolean estaEnNorEste(int x, int y, int x1, int x2, int y1, int y2)
   {
      if (x1+(x2-x1+1)/2<=x && x<=x2 && y1+(y2-y1+1)/2<=y && y<=y2) {
         return true;
      }
      return false;
   }

   public boolean estaEnSurOeste(int x, int y, int x1, int x2, int y1, int y2)
   {
      if (x1<=x && x<=x1+(x2-x1-1)/2 && y1<=y && y<=y1+(y2-y1-1)/2) {
         return true;
      }
      return false;
   }

   public boolean estaEnSurEste(int x, int y, int x1, int x2, int y1, int y2)
   {
      if (x1+(x2-x1+1)/2<=x && x<=x2 && y1<=y && y<=y1+(y2-y1-1)/2) {
         return true;
      }
      return false;
   }




   public bloque buscayMarca(int x, int y) {
      int x1=0, x2=dim-1, y1=0, y2=dim-1;

      quadArbol a=this;
      while (a.hijo1!=null) {
         if (estaEnNorOeste(x,y,x1,x2,y1,y2)) {
            a=a.hijo1;
            x2=x1+(x2-x1-1)/2;
            y1=y1+(y2-y1+1)/2;
         } else if (estaEnNorEste(x,y,x1,x2,y1,y2)) {
            a=a.hijo2;
            x1=x1+(x2-x1+1)/2;
            y1=y1+(y2-y1+1)/2;
         } else if (estaEnSurOeste(x,y,x1,x2,y1,y2)) {
            a=a.hijo3;
            x2=x1+(x2-x1-1)/2;
            y2=y1+(y2-y1-1)/2;
         } else if (estaEnSurEste(x,y,x1,x2,y1,y2)) {
            a=a.hijo4;
            x1=x1+(x2-x1+1)/2;
            y2=y1+(y2-y1-1)/2;
         }
      }
      bloque b=new bloque(a.elemento,x1,x2,y1,y2,a.marcado);
      a.marcado=true;
      return b;
   }
   public String toString() {
      return _toString(this);
   }
   public static void ponMarca(quadArbol a, boolean m) {
      if (a!=null) {
         a.marcado=m;
         ponMarca(a.hijo1, m);
         ponMarca(a.hijo2, m);
         ponMarca(a.hijo3, m);
         ponMarca(a.hijo4, m);
      }
   }      

   public void ponMarca(boolean m) {
      ponMarca(this,m);
   }
   public void marca() {
      ponMarca(true);
   }
   public void desMarca() {
      ponMarca(false);
   }      

   public String _toString(quadArbol a) {
      if (a.hijo1==null) {
         return "("+Integer.toString(a.elemento)+")";
      } else {
         return("("+_toString(a.hijo1)+","+
                    _toString(a.hijo2)+","+
                    _toString(a.hijo3)+","+
                    _toString(a.hijo4)+")");
      }
   }
}

