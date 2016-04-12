#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <memory.h>

#pragma warning(disable:4996)
#include "listaGenerica.h"


//Imprime mensajes de error y termina el programa
void error(char *mensaje) {
   printf("\n%s.\n",mensaje);
   exit(-1);
}

//Crear una lista vacía
/*
Cambiada por la macro #define listaCrear() NULL
LISTA_GENERICA listaCrear() {
   return NULL; // creamos una lista vacía
}
*/
//Destruye todos los NODOS de la lista l
void _listaDestruye(LISTA_GENERICA *l) {
   NODO *pAux;

   pAux=*l;
   while(pAux!=NULL) {
      *l=(*l)->sig;
      free(pAux->info);
      free(pAux);
      pAux=*l;
   }
}
//Devuelve verdadero si la lista esta vacío, falso en caso contrario
/*
Cambiada por la macro #define listaVacia(l) (l==NULL)
int listaVacia(LISTA_GENERICA l) {
   return l==NULL;
}
*/

//Inserta un nuevo dato 'DELANTE' de la posición p de la lista
void _listaInserta(LISTA_GENERICA *l, POS_GENERICA p, void * dato, int tam) {
   NODO *nuevo=(NODO *) malloc(sizeof(NODO)),
      *posAux;
   if (nuevo==NULL) {
      error("Error listaInserta. No hay memoria para crear nodo.");
   }
   nuevo->info=malloc(tam);
   if (nuevo->info==NULL) {
      error("Error listaInserta. No hay memoria para crear nodo.");
   }
   memcpy(nuevo->info,dato,tam); // nuevo->info=dato;
   if (p==*l) {
      nuevo->sig=*l;
      *l=nuevo;
   } else {
      posAux=*l;
      while(posAux!=NULL && posAux->sig!=p) {
         posAux=posAux->sig;
      }
      if (posAux==NULL) {
         error("Error listaInserta. La posición indicada no existe en la lista.");
      } else {
         nuevo->sig=p;
         posAux->sig=nuevo;
      }
   }
}
//Inserta dato al principio de la lista l
/*
Cambiada por macro #define listaInsertaPrincipio(l,d) _listaInserta(&(l),*(l),&(d),sizeof(d))
void _listaInsertaPrincipio(LISTA_GENERICA *l, void * dato,int tam) {
   _listaInserta(l,*l,dato,tam);
}
*/
//Inserta dato al final de la lista l
/*Cambiada por la macro 
#define listaInsertaFinal(l,d) _listaInsertaFinal(&(l),NULL,&(d),sizeof(d))

void _listaInsertaFinal(LISTA_GENERICA *l, void * dato, int tam) {
   _listaInserta(l,NULL,dato,tam);
}
*/


//inserta dato en la posición ordenada ascendente (por defecto es asc.) que le corresponde
void _listaInsertaOrden(LISTA_GENERICA *l, void * dato, int tam, int (*fComparacion)(const void*,const void*)) {
   NODO *posAux=*l;
   while(posAux!=NULL && (*fComparacion)(dato,posAux->info)>=0) {
      posAux=posAux->sig;
   }
   _listaInserta(l,posAux,dato,tam);
}
//inserta dato en la posición ordenada descendente que le corresponde
void _listaInsertaOrdenDesc(LISTA_GENERICA *l, void * dato, int tam, int (*fComparacion)(const void*,const void*)) {
      NODO *posAux=*l;
   while(posAux!=NULL && (*fComparacion)(dato,posAux->info)<=0) {
      posAux=posAux->sig;
   }
   _listaInserta(l,posAux,dato,tam);
}
//Borrar el dato en la posición p de la lista l
void _listaBorra(LISTA_GENERICA *l, POS_GENERICA p) {
   NODO *posAux;

   if (p==*l) {
      *l=(*l)->sig;
      free(p->info);
      free(p);
   } else {
      posAux=*l;
      while(posAux->sig!=p) {
         posAux=posAux->sig;
      }
      if (posAux==NULL) {
         error("Error listaBorra. La posición indicada no existe en la lista.");
      } else {
         posAux->sig=p->sig;
         free(p->info);
         free(p);
      }
   }
}
//Devuelve el dato de la posición p de la lista l
/*Cambiada por la macro #define listaElemento(l,p) ((p)->info)
void * _listaElemento(LISTA_GENERICA l, POS_GENERICA p) {
   return p->info;
}
*/

//Devuelve la posición del primer elemento de la lista l
/*
Cambiada por la macro #define listaPrimero(l) (l)
POS_GENERICA listaPrimero(LISTA_GENERICA l) {
   return l;
}
*/

//Devuelve la posición del último elemento de la lista l
POS_GENERICA listaUltimo(LISTA_GENERICA l) {
   POS_GENERICA pAux=l;
   if (l==NULL) {
      return NULL; // indicamos que no hay último ya que la lista está vacía
   }
   while(pAux->sig!=NULL) {
      pAux=pAux->sig;
   }
   return pAux;
}
//Devuelve la posición anterior a p si existe si no devuelve NULL
POS_GENERICA listaAnt(LISTA_GENERICA l, POS_GENERICA p) {
   POS_GENERICA pAux=l;

   while(pAux!=NULL && pAux->sig!=p) {
      pAux=pAux->sig;
   }
   return pAux;
}

//Devuelve el número de elementos actualmente en la lista
int listaNumElementos(LISTA_GENERICA l) {
   int num=0;
   while(l!=NULL) {
      l=l->sig;
      num++;
   }
   return num;
}
//Devuelve la posición de dato dentro de la lista o POS_ERRONEA si no se encuentra
POS_GENERICA _listaBuscaElemento(LISTA_GENERICA l, void *dato, int tam, int (*fComparacion)(const void*,const void *)) {
   POS_GENERICA posAux=l;
   while(posAux!=NULL) {
      if ((*fComparacion)(dato,posAux->info)==0) {
         return posAux;
      }
      posAux=posAux->sig;
   }
   return POS_ERRONEA;
}
//Devuelve una cadena con cada elemento de la lista en forma de matriz p.ej. {1,2,3,4,...}
char * listaToString(LISTA_GENERICA l,char * (*fInfoToString)(const void *)) {
   static char aux[4096]; // cadena lo suficientemente grande como para albergar a la lista

   strcpy(aux,"{"); // inicializamos la cadena aux con una llave abierta
   while(l!=NULL) {
      strcat(aux,(*fInfoToString)(l->info));
      if (l->sig!=NULL) {
         strcat(aux,",");
      }
      l=l->sig;
   }
   strcat(aux,"}"); // cerramos la matriz
   return aux;
}

