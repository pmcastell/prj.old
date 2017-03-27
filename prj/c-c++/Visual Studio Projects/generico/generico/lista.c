#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#pragma warning(disable:4996)
#include "lista.h"


//Imprime mensajes de error y termina el programa
void error(char *mensaje) {
   printf("\n%s.\n",mensaje);
   exit(-1);
}

//Crear una lista vac�a
LISTA listaCrear() {
   return NULL; // creamos una lista vac�a
}
//Devuelve verdadero si la lista esta vac�o, falso en caso contrario
int listaVacia(LISTA l) {
   return l==NULL;
}
//Inserta un nuevo dato 'DELANTE' de la posici�n p de la lista
void listaInserta(LISTA *l, POS p, INFO dato) {
   NODO *nuevo=(NODO *) malloc(sizeof(NODO)),
      *posAux;
   nuevo->info=dato;
   if (p==*l) {
      nuevo->sig=*l;
      *l=nuevo;
   } else {
      posAux=*l;
      while(posAux->sig!=p) {
         posAux=posAux->sig;
      }
      if (posAux==NULL) {
         error("Error listaInserta. La posici�n indicada no existe en la lista.");
      } else {
         nuevo->sig=p;
         posAux->sig=nuevo;
      }
   }
}
//Inserta dato al principio de la lista l
void listaInsertaPrincipio(LISTA *l, INFO dato) {
   listaInserta(l,*l,dato);
}
//Inserta dato al final de la lista l
void listaInsertaFinal(LISTA *l, INFO dato) {
   listaInserta(l,listaUltimo(*l),dato);
}
//Inserta dato en la posici�n ordenada que le corresponda
void listaInsertaOrdenado(LISTA *l, INFO dato) {
   NODO *nuevo=(NODO *) malloc(sizeof(NODO)),
      * pAux;

   nuevo->info=dato;
   nuevo->sig=NULL;
   if (*l==NULL || infoCompara(dato,(*l)->info)<0) {
      nuevo->sig=*l;
      *l=nuevo;
   } else {
      pAux=*l;
      while(pAux->sig!=NULL && infoCompara(pAux->sig->info,dato)<0) {
         pAux=pAux->sig;
      }
      nuevo->sig=pAux->sig;
      pAux->sig=nuevo;
   }
}
//Borrar el dato en la posici�n p de la lista l
void listaBorra(LISTA *l, POS p) {
   NODO *posAux;

   if (p==*l) {
      *l=(*l)->sig;
      free(p);
   } else {
      posAux=*l;
      while(posAux->sig!=p) {
         posAux=posAux->sig;
      }
      if (posAux==NULL) {
         error("Error listaBorra. La posici�n indicada no existe en la lista.");
      } else {
         posAux->sig=p->sig;
         free(p);
      }
   }
}
//Devuelve el dato de la posici�n p de la lista l
INFO listaElemento(LISTA l, POS p) {
   return p->info;
}
//Devuelve la posici�n del primer elemento de la lista l
POS listaPrimero(LISTA l) {
   return l;
}
//Devuelve la posici�n del �ltimo elemento de la lista l
POS listaUltimo(LISTA l) {
   POS pAux=l;
   if (l==NULL) {
      return NULL; // indicamos que no hay �ltimo ya que la lista est� vac�a
   }
   while(pAux->sig!=NULL) {
      pAux=pAux->sig;
   }
   return pAux;
}
//Destruye todos los NODOS de la lista l
void listaDestruye(LISTA *l) {
   NODO *pAux;

   pAux=*l;
   while(pAux!=NULL) {
      *l=(*l)->sig;
      free(pAux);
      pAux=*l;
   }
}
//Devuelve una cadena con cada elemento de la lista en forma de matriz p.ej. {1,2,3,4,...}
char *listaToString(LISTA l) {
   static char aux[4096]; // cadena lo suficientemente grande como para albergar a la lista

   strcpy(aux,"{"); // inicializamos la cadena aux con una llave abierta
   while(l!=NULL) {
      strcat(aux,infoToString(l->info));
      if (l->sig!=NULL) {
         strcat(aux,",");
      }
      l=l->sig;
   }
   strcat(aux,"}"); // cerramos la matriz
   return aux;
}

