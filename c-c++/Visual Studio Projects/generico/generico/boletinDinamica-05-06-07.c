

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "listaGenerica.h"

#pragma warning(disable:4996)

void ej5();
void ej6();
void ej7();
char *intToString(const void *);
int fCompInt(const void *i1, const void *i2);
int compCadenas(const void *s1, const void* s2);
int main(int argc, char**argv) {
   ej5();
   ej6();
   ej7();
}

/*
5.Obtener la media de un número indeterminado de números. Listarlos ascendentemente.
*/
void ej5() {
   LISTA_GENERICA l=listaCrear();
   POS_GENERICA pos;
   int n, total=0;

   do {
      printf("\nIntroduce número: ");
      fflush(stdin);
      scanf("%d",&n);
      if (n<0) {
         printf("\nTerminada la entrada.");
      } else {
         listaInsertaOrdenAsc(l,n,fCompInt);
      }
   } while (n>=0);
   printf("\nLa lista es: \n%s.",listaToString(l,&intToString));
   printf("\n-----------------------------------------------");
   pos=listaPrimero(l); 
   while(pos!=listaFin(l)) {
      total+=listaElemento(l,pos,int);
      pos=listaSig(l,pos);
   }
   printf("\nLa Media es: %.2f.",(double)total/listaNumElementos(l));
   listaDestruye(l);
}

char *intToString(const void *i) {
   static char aux[12];
   itoa(*((int *)i),aux,10);
   return aux;
}

int fCompInt(const void *i1, const void *i2) {
   int *ii1, *ii2;
   ii1=(int *)i1; ii2=(int *)i2;
   return *ii1-*ii2;
}
/*
6.Almacenar un nº indeterminado de cadenas. Listarlas alfabéticamente.
*/
void ej6() {
   LISTA_GENERICA l=listaCrear();
   POS_GENERICA pos;
   char cadena[512];
   int entradaDatos=1;

   do {
      printf("\nIntroduce cadena ('****' para terminar: ");
      fflush(stdin);
      gets(cadena);
      if (strcmp(cadena,"****")==0) {
         printf("\nEntrada Terminada");
         entradaDatos=0;
      } else {
         listaInsertaOrden(l,cadena,&compCadenas);
      }
   } while(entradaDatos==1);
   printf("\nImprimiendo las cadenas: ");
   pos=listaPrimero(l);
   while(pos!=listaFin(l)) {
      printf("\n%s",&listaElemento(l,pos,char *));
      pos=listaSig(l,pos);
   }
   listaDestruye(l);
}

char *cadToString(const void *s) {
   return (char *)s;
}

int compCadenas(const void *s1, const void* s2) {
   return strcmp((char *)s1,(char *)s2);
}
/*
7.Invertir una cadena. Utilizar un nodo para cada carácter.
*/
void ej7() {
   LISTA_GENERICA l=listaCrear();
   POS_GENERICA pos;
   char prueba[]="Esto es una prueba", *aux=prueba;

   while(*aux!='\0') {
      listaInsertaFinal(l,*aux);
      aux++;
   }
   aux=prueba;
   pos=listaUltimo(l);
   while(pos!=POS_ERRONEA) {
      *aux=listaElemento(l,pos,char);
      pos=listaAnt(l,pos);
      aux++;
   }
   listaDestruye(l);
}





