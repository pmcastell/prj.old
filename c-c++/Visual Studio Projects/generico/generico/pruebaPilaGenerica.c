#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#pragma warning(disable:4996)


#include "pilaGenerica.h"

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

char * charToString(const void * s) {
   return (char *)s;
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
   }, infAux;
   int m[]={2,4,6,1,5,3};
   int i,intAux;
   PILA_GENERICA p=pilaCrear(), p2=pilaCrear();

   for(i=0; i<sizeof(inf)/sizeof(INFO); i++) {
      pilaPush(p,inf[i]);
      //auxDebug=pilaToString(p,&infoToString);
      pilaPush(p2,m[i]);
      //auxDebug=pilaToString(p2,&infoToString);
   }
   while(!pilaVacia(p)) {
      infAux=pilaPop(p,INFO);
      intAux=pilaPop(p2,int);
   }


}