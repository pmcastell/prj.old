#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "listaGenerica.h"

#pragma warning(disable:4996)

#define MAX 10

typedef struct {
   int column;
   double valor;
} INFO;

#define COLS 3
#define FILAS 5

double suma_col(LISTA_GENERICA tabla[],int nfil,int columna);
void convierteMatriz(double m[][COLS],LISTA_GENERICA tabla[]);

char *infoToString(const void *inf) {
   INFO *info=(INFO *)inf;
   static char aux[24];
   sprintf(aux,"{%d,%.2lf}",info->column,info->valor);
   return aux;
}
   
   

int main(int argc, char**argv) {
   LISTA_GENERICA tabla[MAX];
   double m[FILAS][COLS]={
      {1,2,3},
      {0,1,0},
      {8,0,2},
      {0,4,2},
      {5,0,1}
   };
   double suma;

   convierteMatriz(m,tabla,FILAS);
   suma=suma_col(tabla,3,0);
   suma=suma_col(tabla,3,2);
   suma=suma_col(tabla,3,2);
}
/*
8.Una matriz de números reales se encuentra almacenada mediante una tabla de listas, cada
una de las cuales contiene los elementos no nulos de una fila de la matriz. Para cada 
elemento se almacena su columna y su valor. Los elementos están colocados en orden 
ascendente del número de columna. Escribir la función double suma_col(NODO *tabla[],
int nfil, int columna); que devuelva la suma de los elementos de la columna de la matriz 
que se pasa como parámetro de entrada. El parámetro nfil represente el número de filas de 
la matriz. Las definiciones necesarias:	
 #define MAX 10
					typedef struct nodo{
						int column;
						double valor;
						struct nodo *sig;
					}NODO;
					NODO *tabla[MAX];
*/
double suma_col(LISTA_GENERICA tabla[],int nfil,int columna) {
   int i;
   LISTA_GENERICA posAux;
   double suma=0;

   for(i=0; i<nfil; i++) {
      posAux=listaPrimero(tabla[i]);
      while(posAux!=listaFin(tabla[i]) && listaElemento(tabla[i],posAux,INFO).column<columna) {
         posAux=listaSig(tabla[i],posAux);
      }
      if (posAux!=listaFin(tabla[i]) && listaElemento(tabla[i],posAux,INFO).column==columna) {
         suma+=listaElemento(tabla[i],posAux,INFO).valor;
      }
   }
   return suma;
}

void convierteMatriz(double m[][COLS],LISTA_GENERICA tabla[]) {
   int i,j;
   INFO inf;

   for(i=0; i<FILAS; i++) {
      tabla[i]=listaCrear();
      for(j=0; j<COLS; j++) {
         if (m[i][j]!=0) {
            inf.column=j;
            inf.valor=m[i][j];
            listaInsertaFinal(tabla[i],inf);
         }
      }
   }
   for(j=i; j<MAX; j++) {
      tabla[j]=listaCrear(); // inicializamos el resto de elementos de la tabla a lista vacía
   }

}
