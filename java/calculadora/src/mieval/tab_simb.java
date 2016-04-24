/******************************************************************************/
/******************         tab_simb.java       *******************************/
/******************************************************************************/
package mieval; 
import java.lang.String;
import java.lang.System;
import mieval.entrada;
import mieval.tipo.*;
import java.util.Vector;
/******************************************************************************/
/******************************************************************************/
/*** Esta es utilizada por la clase tab_simb (tabla de s�mbolos) como   *******/
/*** 'registros' enlazados de la tabla de s�mbolos (lista enlazada)        ****/
/******************************************************************************/
/******************************************************************************/
class mientrada {
    entrada simb;
    mientrada siguiente;
    public mientrada(entrada e,mientrada s)
    {
        this.simb=e;
        this.siguiente=s;
    }
    public mientrada(mientrada e)
    {
        this(e.simb,e.siguiente);
    }
}


public class tab_simb {

   public boolean encontrado;
   private mientrada simbolos;
   private mientrada actual, anterior;
   private boolean  IgnoreCase;
   private int numSimbolos=0;

/******************************************************************************/
/*** Constructor                                                     **********/
/******************************************************************************/
   public tab_simb()
   {
       IgnoreCase=true;
   }

/*** Busca una entrada en la tabla de s�mbolos                       **********/
/*** Par�metros: e-> entrada a buscar                                **********/
/*** Devuelve: una referencia a la entrada si se encuentra, null en  **********/
/***     caso contrario                                              **********/
   public entrada busca(entrada e)
   {
      return busca(e.objeto.nombre);
   }

/*** Busca una entrada en la tabla de s�mbolos                       **********/
/*** Par�metros: v-> variable contenida en la entrada a buscar       **********/
/*** Devuelve: una referencia a la entrada si se encuentra, null en  **********/
/***     caso contrario                                              **********/
   public entrada busca(variable v)
   {
      return busca(v.nombre);
   }

/*** Busca una entrada en la tabla de s�mbolos                       **********/
/*** Par�metros: s-> nombre de la variable contenida en entrada a buscar*******/
/*** Devuelve: una referencia a la entrada si se encuentra, null en  **********/
/***     caso contrario                                              **********/
   public entrada busca(String s)
   {
      int r=-1;
      actual=simbolos;
      anterior=null;

      if (IgnoreCase) {
          s=s.toLowerCase();
      }
      while (actual!=null) {
         if (IgnoreCase) {
            if ((r=s.compareTo(actual.simb.objeto.nombre.toLowerCase()))<=0) {
                break;
            }
         } else if ((r=s.compareTo(actual.simb.objeto.nombre))<=0) {
            break;
         }
         anterior=actual;
         actual=actual.siguiente;
      }
      if (r==0) {
         encontrado=true;
         return(new entrada(actual.simb)); //devolvemos referencia al objeto
      } else {
         encontrado=false;
         return(null);
      }
   }

/*** Insxerta una nueva entrada en la tabla de s�mbolos              **********/
/*** Par�metros: comp_lex-> entero que representa al token           **********/
/***             valor-> variable que contiene los atributos del token ********/
/*** Devuelve: una referencia a la entrada si se encuentra, null en  **********/
/***     caso contrario                                              **********/
   public entrada inserta(int comp_lex,variable valor)
   {
      if (valor.nombre==null) {
         System.out.println("S�mbolo de longitud cero no permitido");
      }
      if (IgnoreCase) {
          valor.nombre=valor.nombre.toLowerCase();
      }
      entrada r=busca(valor.nombre);
      if (r==null) { //no existe la variable actual
          if (anterior==null) { //va al principio
              simbolos=new mientrada((r=new entrada(comp_lex,valor)),simbolos);
          } else { // va enmedio
              anterior.siguiente=new mientrada((r=new entrada(comp_lex,valor)),actual);
          }
          numSimbolos++;
      } else {
          actual.simb.objeto=valor; //si ya existe se le asigna el nuevo valor
          r=actual.simb;
      }
      return(r); //devolvemos una copia del objeto
   }

/*** Insxerta una nueva entrada correspondiente a una variable de tipo ********/
/***     doble en la tabla de s�mbolos                               **********/
/*** Par�metros: s-> nombre de la variable                           **********/
/***             comp_lex-> token asociado (p.ej. ID para una variable ********/
/***             valor-> valor doble que se asignar� a la variable     ********/
/*** Devuelve: una referencia a la entrada si se encuentra, null en  **********/
/***     caso contrario                                              **********/
   public entrada inserta(String s, int comp_lex, double valor)
   {
      return inserta(comp_lex,new variable(s,valor));
   }

/*** Insxerta una nueva entrada en la tabla de s�mbolos              **********/
/*** Par�metros: e-> entrada a insertar                              **********/
/*** Devuelve: una referencia a la entrada si se encuentra, null en  **********/
/***     caso contrario                                              **********/
   public entrada inserta(entrada e)
   {
      return inserta(e.comp_lex,e.objeto);
   }
/*** Elimina una entrada de la tabla de s�mbolos                     **********/
/*** Par�metros: s-> nombre de la variable cuya entrada hay que borrar*********/
/*** Devuelve: si la operaci�n con �xito                             **********/
   public boolean borrar(String s)
   {
      entrada r=busca(s);
      if (r!=null) {
         if (anterior==null) { /* es el primero de la lista*/
            simbolos=simbolos.siguiente;
         } else {
            anterior.siguiente=actual.siguiente;
         }
         numSimbolos--;
         return true;
      }
      return false;
   }

/*** Elimina una entrada de la tabla de s�mbolos                     **********/
/*** Par�metros: e-> entrada a eliminar                              **********/
/*** Devuelve: si la operaci�n con �xito                             **********/
   public boolean borrar(entrada e)
   {
      return borrar(e.objeto.nombre);
   }

/*** Elimina una entrada de la tabla de s�mbolos                     **********/
/*** Par�metros: v-> variable contenida en la entrada a eliminar     **********/
/*** Devuelve: si la operaci�n con �xito                             **********/
   public boolean borrar(variable v)
   {
      return borrar(v.nombre);
   }

/*** Devuelve el n�mero total de entradas de la tabla de s�mbolos    **********/
/*** Par�metros: ninguno                                             **********/
   public int getNumSimbolos()
   {
      return  numSimbolos;
   }

/*** Devuelve el elemento i-�simo de la tabla de s�mbolos            **********/
/*** Par�metros: i-> posici�n del elemento a devolver                **********/
/*** Devuelve: una referencia a la variable contenida en la entrada  **********/
/***     i-�sima de la tabla o null si i 'est� fuera de rango'       **********/
   public variable getSimbolo(int i)
   {
      int j=0;
      mientrada e=null;

      if (i<numSimbolos && i>=0) {
         e=simbolos;
         while (j<i) {
            e=e.siguiente;
            j++;
         }
      }
      if (e!=null) {
         return e.simb.objeto;
      } else {
         return null;
      }
   }

/*** Establece que en la comparaci�n de cadenas no se distingua entre *********/
/***     may�sculas y min�sculas, por defecto no se distingue.       **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
   public void IgnoreCase()
   {
       IgnoreCase=true;
   }

/*** Establece que en la comparaci�n de cadenas  se distingua entre   *********/
/***     may�sculas y min�sculas, por defecto no se distingue.       **********/
/*** Par�metros: ninguno                                             **********/
/*** Devuelve: nada                                                  **********/
   public void noIgnoreCase()
   {
       IgnoreCase=false;
   }

/*** Asigna un nuevo valor a una variable                            **********/
/*** Par�metros: id-> nombre de la variable a asignar                **********/
/***             v-> nuevo valor de la variable                      **********/
/*** Devuelve: nada                                                  **********/
   public void asigna(String id, variable v)
   {
      inserta(ctes_eval.ID, new variable(id,(objeto) v.valor.copia()));
   }

/*** Asigna un nuevo valor num�rico a una variable                   **********/
/*** Par�metros: id-> nombre de la variable a asignar                **********/
/***             valor-> nuevo valor de la variable                  **********/
/*** Devuelve: nada                                                  **********/
   public void asigna(String id,double valor)
   {
      entrada aux=busca(id);
      if (encontrado && aux.comp_lex==ctes_eval.ID) {
         aux.objeto.asigna(valor);
      } else {
         inserta(new entrada(ctes_eval.ID, new variable(id,valor)));
      }
   }

}


