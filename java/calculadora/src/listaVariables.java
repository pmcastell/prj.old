/******************************************************************************/
/******************         listaVariables.java    ****************************/
/******************************************************************************/
import mieval.notificado;
import mieval.entrada;
import mieval.tipo.variable;

//import javax.swing.*;
import java.lang.String;
import jclass.bwt.*;
import jclass.bwt.JCMultiColumnList;
import jclass.util.JCVector;
import java.awt.Color;
import java.util.Vector;

/******************************************************************************/
/*****  esta clase ha sido semi-generada con la ayuda de herramientas *********/
/*****  RAD                                                           *********/
/******************************************************************************/
public class listaVariables extends JCMultiColumnList implements notificado{
  private boolean encontrado=false;
  private String etiqueta1, etiqueta2;
  private Vector internalList=new Vector();


/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
  public listaVariables()
  {
     try {
        etiqueta1="";
        etiqueta2="";
        jbInit();
     } catch (Exception e) {
        e.printStackTrace();
     }
  }

  public listaVariables(String e1, String e2) {
    try {
      etiqueta1=e1;
      etiqueta2=e2;
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

/*** Devuelve una cadena formada for n espacios                      **********/
/*** Par�metros: n-> n�mero de espacios                              **********/
  String spaces(int n)
  {
     String s="";
     for(int i=1;i<=n;i++) {
        s+=" ";
     }
     return s;
  }

/*** Este m�todo es uno de los m�todos del interfaz notificado que   **********/
/***     implementa esta clase y es llamado por otro objeto          **********/
/***     (el evaluador) para notificarle que                         **********/
/***     se ha creado una nueva variable y que debe actualizar la    **********/
/***     ventana que muestra la lista de las variables al usuario    **********/
/*** Par�metros: nombre-> nombre de la variable creada               **********/
/***             valor-> cadena representado el valor de la variable **********/
/*** Deuelve: nada                                                   **********/
  public void nuevaVariable(String nombre, String valor) {
     int  i;
     java.util.Vector s=new java.util.Vector();

     s.addElement(nombre);
     s.addElement(valor);
     i=busca(nombre);
     if (encontrado) {
        replaceItem(s,i);
        paintRow(i);
        encontrado=false;
     } else {
       addItem(s,i);
       makeVisible(i);
       paintRow(i);
     }
  }

/*** Este m�todo es uno de los m�todos del interfaz notificado que   **********/
/***     implementa esta clase y es llamado por otro objeto          **********/
/***     (el evaluador) para notificarle que                         **********/
/***     se ha creado un nuevo entorno de ejecuci�n y que por tanto  **********/
/***     se debe crear una nueva lista de variables vac�a para mostrar*********/
/***     al usuario                                                  **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: nada                                                   **********/
  public void nuevoEntorno()
  {
      internalList.addElement(this.getItems());
      this.setItems(new JCVector());
  }

/*** Este m�todo es uno de los m�todos del interfaz notificado que   **********/
/***     implementa esta clase y es llamado por otro objeto          **********/
/***     (el evaluador) para notificarle que                         **********/
/***     se ha terminado el �ltimo entorno de ejecuci�n y que se     **********/
/***     restaura el anterior por lo que debe restaurarse la anterior**********/
/***     lista de variables y volver a mostrarla al usuario          **********/
/*** Par�metros: nombre-> ninguno                                    **********/
/*** Deuelve: nada                                                   **********/
  public void finNuevoEntorno()
  {
     this.setItems((JCVector) internalList.elementAt(internalList.size()-1));
     internalList.removeElementAt(internalList.size()-1);

  }

/*** Busca una variable representada por s y devuelve la posici�n que**********/
/***     ocupa en la lista de variables                              **********/
/*** Par�metros: s-> nombre de la variable a buscar                  **********/
/*** Deuelve: un entero que representa la posici�n de la variable en **********/
/***     la lista caso de que se haya encontrado, sino la posici�n   **********/
/***     que deber�a ocupar dicha variable en orden alfab�tico       **********/
  public int busca(String s) {
     int i=0,j=countItems()-1,k=0;
     java.util.Vector item;
     String is;

     while (i<=j) {
        k=(i+j) / 2;
        is=(String) ((java.util.Vector)getItem(k)).elementAt(0);
        if (s.compareTo(is)<0) {
           j=k-1;
        } else if (s.compareTo(is)>0) {
           i=k+1;
        } else {
           encontrado=true;
           return k;
        }
     }
     if (i>j) {
        return i;
     } else {
        return k;
     }
  }



/*** Este m�todo ha sido generado por la herramienta RAD             **********/
/***     e inicializa los componentes gr�ficos                       **********/
  public void jbInit() throws Exception{
    this.setColumnLabels(jclass.util.JCUtilConverter.toStringList(etiqueta1+","
                                                                   +etiqueta2));
    setBackground(Color.white);
    setScrollbarDisplay(0);
  }
}
