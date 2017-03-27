#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include "pilaGenerica.h"


void * _pilaPop(PILA_GENERICA *p, int tam) {
   static void *devolver=NULL;
   if (devolver!=NULL) {
      free(devolver);
   } 
   devolver=malloc(tam);
   if (devolver==NULL) {
      printf("Función pilaPop. Error al pedir memoria.");
      exit(-1);
   }
   memcpy(devolver,(*p)->info,tam); // guardamos el primer elemento de la lista en devolver antes de borrarlo
   _listaBorra(p,*p); // borramos el primer elemento de la lista (la cima de la pila)
   return devolver;
}

