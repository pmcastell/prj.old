#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#pragma warning(disable:4996)

#define MAX 10

typedef struct nodo{
   int column;
   double valor;
	struct nodo *sig;
}NODO;



int main(int argc, char**argv) {
   NODO *tabla[MAX];

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
double suma_col(NODO * tabla[],int nfil,int columna) {
   int i;
   NODO *posAux;
   double suma=0;

   for(i=0; i<nfil; i++) {
      posAux=tabla[i];
      while(posAux!=NULL && posAux->column<columna) {
         posAux=posAux->sig;
      }
      if (posAux!=NULL && posAux->column==columna) {
         suma+=posAux->valor;
      }
   }
   return suma;
}