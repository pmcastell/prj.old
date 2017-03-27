/******************************************************************************/
/******************         listaFicheros.java    *****************************/
/******************************************************************************/
package mieval; 
import java.util.Vector;
import java.io.*;

/******************************************************************************/
/******************  esta clase representa cada una de las entradas que********/
/******************  forman parte de la siguiente lista de ficheros    ********/
/******************************************************************************/
class FILE {
   String nombre;
   String contenido;
   File file;
   int numAperturas;
   int numLineas;
   long ultimaModificacion;
   public boolean modificado()
   {
      return file.lastModified()!=ultimaModificacion;
   }
}

/******************************************************************************/
/******************  esta clase mantiene una lista de los ficheros     ********/
/******************  abiertos y sus respectivos contenidos. Antes de   ********/
/******************  devolver el contenido de un fichero comprueba que ********/
/******************  que este no se ha modificado y si es as� vuelve a ********/
/******************  leer del disco su contenido                       ********/
/******************************************************************************/
public class listaFicheros {
   private Vector lista=new Vector();
   private BufferedReader inStream;


/******************************************************************************/
/*** Constructor                                                     **********/
/******************************************************************************/
   public listaFicheros() {
   }

/*** Nos dice si un fichero est� en la lista de ficheros abiertos    **********/
/*** Par�metros: f-> nombre del fichero a buscar                     **********/
/*** Deuelve: un entero>=0 si est� en ls lista que representa su     **********/
/***     posici�n en la misma, un valor<=0 si no est� en la lista    **********/
   public int busca(String f) {
      for(int i=0; i<lista.size(); i++) {
         if (f.equalsIgnoreCase(((FILE) lista.elementAt(i)).nombre)) {
            return i;
         }
      }
      return -1;
   }

/*** Abre un fichero y lee su contenido a memoria                    **********/
/*** Par�metros: fichero-> nombre del fichero                        **********/
/*** Deuelve: si la operaci�n se llev� a cabo con �xito              **********/
   public boolean abrir(String fichero)
   {
      String contenido=new String(), linea;
      FILE f=null;
      int nLin=0,i;

      i=busca(fichero);
      if (i>=0) {
         f=(FILE) lista.elementAt(i);
         f.numAperturas++;
         if (!f.modificado()) {
            return true;
         }
      }
      try {               
//         inStream=new DataInputStream(new FileInputStream(fichero));
         inStream=new BufferedReader(new InputStreamReader(new FileInputStream(fichero)));
      } catch (IOException ex) {
         return false;
      }
      try {
         while((linea=inStream.readLine())!=null) {
            contenido+=linea+'\n';
            nLin++;
         }
         inStream.close();
      } catch (IOException ex) {
         return false;
      }
      if (f==null) {
         f=new FILE();
      }
      f.nombre=fichero.toUpperCase();
      f.file=new File(fichero);
      f.contenido=contenido;
      f.numAperturas=1;
      f.numLineas=nLin;
      f.ultimaModificacion=f.file.lastModified();
      lista.addElement(f);
      return true; /* el fichero se abri� y ley� sin problema*/
   }

/*** Elimina un fichero de la lista de ficheros abiertos y su contenido********/
/*** Par�metros: fichero-> nombre del fichero                        **********/
/*** Deuelve: si la operaci�n se llev� a cabo con �xito              **********/
   public boolean cerrar(String fichero)
   {
      FILE f;

      int i=busca(fichero);
      if (i>=0) {
         f=(FILE) lista.elementAt(i);
         f.numAperturas--;
         if (f.numAperturas<=0) {
            lista.removeElementAt(i);
         }
         return true; /* �peraci�n completada con �xito*/
      }
      return false;
   }

/*** Devuelve la l�nea i-�sima de un fichero                         **********/
/*** Par�metros: fichero-> nombre del fichero                        **********/
/***             i-> n�mero de la l�nea que se quiere obtener        **********/
/*** Deuelve: la l�nea en cuesti�n si existe o null en caso contrario**********/
   public String getLinea(String fichero, int l)
   {
      FILE f;
      int numLinea=0;

      int i=busca(fichero);
      if (i>=0) {
         f=(FILE) lista.elementAt(i);
         if (l>f.numLineas) {
            return null;
         }
         int comienzo,fin;
         comienzo=fin=0;
         while (numLinea<l) {
            if (f.contenido.charAt(fin)=='\n') {
               numLinea++;
               if (numLinea==l) {
                  break;
               } else {
                  comienzo=fin+1; /* empezamos una l�nea nueva */
               }
            }
            fin++;
         }
         if (comienzo<fin) {
            return f.contenido.substring(comienzo,fin);
         } else if (comienzo==fin) {
            return "";
         }
      }
      return null;
   }

/*** Devuelve el contenido �ntegro de un fichero como una cadena     **********/
/*** Par�metros: fichero-> nombre del fichero                        **********/
   public String getContenido(String fichero)
   {
      int i=busca(fichero);
      if (i>=0) {
         return ((FILE) lista.elementAt(i)).contenido;
      }
      return null;
   }

}

