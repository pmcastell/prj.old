#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#pragma warning(disable:4996)

#include "listaGenerica.h"

typedef struct {
   int num;
   char nombre[11];
} INFO;

int comparaInfo(const void * info1, const void *info2) {
   INFO *inf1, *inf2;
   inf1=(INFO *)info1;
   inf2=(INFO *)info2;
   return inf1->num-inf2->num;
}

char *infoToString(const void *info) {
   static char aux[3+9+11];
   INFO *inf=(INFO *)info;
   strcpy(aux,"{");
   itoa(inf->num,&aux[1],10);
   strcat(aux,",");
   strcat(aux,inf->nombre);
   strcat(aux,"}");
   return aux;
}
int intComp(const void  * i1, const void * i2) {
   return *((int *)i1)-*((int*)i2);
}

char *intToString(const void *i) {
   static char aux[10];
   itoa(*((int *)i),aux,10);
   return aux;
}

int funcion(int a, int b) {
   return a+b;
}

int main(int argc, char **argv) {
   INFO inf[]={
      {5,"Pepe"},
      {2,"Antonio"},
      {3,"Juan"},
      {1,"Luisito"},
      {4,"Alfredo"},
      {6,"Fernando"}
   };
   int m[]={2,4,6,1,5,3};
   int i;
   char *auxDebug; //para depurar el programa
   LISTA_GENERICA l=listaCrear(), l2=listaCrear(), l3=listaCrear(), l4=listaCrear();

   i=funcion((1,2,3),5);
   



   for(i=0; i<sizeof(inf)/sizeof(INFO); i++) {
      listaInsertaOrdenAsc(l,inf[i],&comparaInfo);
      auxDebug=listaToString(l,&infoToString);
      listaInsertaOrdenDesc(l3,inf[i],&comparaInfo);
      printf("\n%s",auxDebug);
      listaInsertaOrdenAsc(l2,m[i],&intComp);
      auxDebug=listaToString(l2,&intToString);
      listaInsertaOrdenDesc(l4,m[i],&intComp);
      printf("\n%s",auxDebug);
   }
   for(i=0; i<sizeof(inf)/sizeof(INFO); i++) {
      printf("\nElemento que será borrado: %s.",infoToString(&listaElemento(l,listaPrimero(l),INFO)));
      listaBorra(l,listaPrimero(l));
      listaBorra(l3,listaPrimero(l3));
      auxDebug=listaToString(l,&infoToString);
      printf("\n%s",auxDebug);
      printf("\nElemento que será borrado: %d.",listaElemento(l2,listaPrimero(l2),int));
      listaBorra(l2,listaPrimero(l2));
      listaBorra(l4,listaPrimero(l4));
      auxDebug=listaToString(l2,&intToString);
      printf("\n%s",auxDebug);
   }
   listaDestruye(l);
   listaDestruye(l2);
   l=listaCrear();
   for(i=0; i<sizeof(inf)/sizeof(INFO);i++) {
      listaInserta(l,listaPrimero(l),inf[i]);
      auxDebug=listaToString(l,&infoToString);
      listaBorra(l,listaPrimero(l));
      auxDebug=listaToString(l,&infoToString);
      listaInsertaPrincipio(l,inf[i]);
      listaBorra(l,listaPrimero(l));
      auxDebug=listaToString(l,&infoToString);
      listaInsertaFinal(l,inf[i]);
      printf("\nElemento Insertado: %s.",infoToString(&listaElemento(l,listaUltimo(l),INFO)));
   }
   if (listaVacia(l)) {
      printf("\nLa lista está vacía.");
   }
   printf("\nElemento Primero: %s.",infoToString(&listaElemento(l,listaPrimero(l),INFO)));
   printf("\nElemento Ultimo: %s.",infoToString(&listaElemento(l,listaUltimo(l),INFO)));
   listaDestruye(l);
   if (listaVacia(l)) {
      printf("\nLa lista está vacía.");
   }




}