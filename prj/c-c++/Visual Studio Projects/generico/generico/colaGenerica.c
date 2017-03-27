#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include "colaGenerica.h"


void * _colaExtraer(COLA_GENERICA *c, int tam) {
   static void *devolver=NULL;
   if (devolver!=NULL) {
      free(devolver);
   } 
   devolver=malloc(tam);
   if (devolver==NULL) {
      printf("Función colaExtraer. Error al pedir memoria.");
      exit(-1);
   }
   memcpy(devolver,(*c)->info,tam); // guardamos el primer elemento de la lista en devolver antes de borrarlo
   _listaBorra(c,*c); // borramos el primer elemento de la lista (la cima de la cola)
   return devolver;
}

