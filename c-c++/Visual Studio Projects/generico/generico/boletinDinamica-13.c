#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "pilaGenerica.h"

#pragma warning(disable:4996)
/*
13.Escribe una función que reciba como parámetro el puntero a un fichero de texto que contiene un programa fuente
escrito en C, previamente abierto y con la referencia situada al principio, y devuelva a través de otro parámetro
una variable que indique si la colocación de llaves, paréntesis y corchetes abiertos y cerrados en dicho programa 
es correcta. El prototipo de la función será: 
void detecta_errores(FILE *pf, LOGICO *correcto); 	
Si hay un error la variable correcto tendrá el valor FALSO, en caso contrario VERDADERO.
*/
void detecta_errores(FILE *pf, int *correcto);
int main(int argc, char **argv) {
   char fich[]="boletinDinamica-13.c";
   FILE *pf;
   int correcto=0;

   if (argc==2) {
      strcpy(fich,argv[1]);
   }
   pf=fopen(fich,"rt");
   if (pf==NULL) {
      printf("\nError abriendo fichero: %s.",fich);
      exit(-1);
   }
   detecta_errores(pf,&correcto);
   if (correcto) {
      printf("\nEl fichero %s NO TIENE errores.",fich);
   } else {
      printf("\nEl fichero %s TIENE errores.",fich);
   }
}

void detecta_errores(FILE *pf, int *correcto) {
   PILA_GENERICA p=pilaCrear();
   char c, cAux; 

   *correcto=0;
   while((c=fgetc(pf))!=EOF) {
      if (c=='{' || c=='[' || c=='(') {
         pilaPush(p,c);
      } else if (c==')' || c==']' || c=='}') {
         if (!pilaVacia(p)) {
            cAux=pilaCima(p,char);
            if ( (cAux=='{' && c=='}') || (cAux=='[' && c==']') || (cAux=='(' && c==')') ) {
               pilaPop(p,char);
            } else {
               return;
            }
         } else {
            return;
         }
      } 
   }
   if (pilaVacia(p)) {
      *correcto=1;
   }
}


