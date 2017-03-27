#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#pragma warning(disable:4996)

int cuentaCaracter(char *cad, char c);
void imprimeInversa(char *cad);
void eliminarRepetidos(int [], int *);
char *arrayToString(int m[], int n);

int main(int argc, char **argv) {
   char cad[80]="hola pepe";
   int cont;
   int m[]={4,5,3,4,5,3,9,8,8,4,5};
   int n=sizeof(m)/sizeof(int);

   cont=cuentaCaracter(cad,'e');
   imprimeInversa(cad);
   eliminarRepetidos(m,&n);
   printf("%s",arrayToString(m,n));



}




/*
2.- Escribe una función  int cuenta_carácter(char * cad, char car); que cuente y devuelva 
el número de apariciones de un carácter car en una cadena cad.
*/
int cuentaCaracter(char *cad, char c) {
   int cuenta=0;
   while(*cad) {
      if (*cad==c) {
         cuenta++;
      }
      cad++;
   }
   return cuenta;
}
/*
3.- Escribe un procedimiento que tenga como parámetro una cadena de caracteres y la 
imprima en orden inverso. 
*/
void imprimeInversa(char *cad) {
   char *aux=cad;
   while(*aux) {
      aux++;
   }
   aux--; //aux queda apuntando al '\0'
   while(aux>=cad) {
      printf("%c",*aux);
      aux--;
   }
}

/*
4.- Realiza una función para eliminar números repetidos de un array de enteros. El array 
y la longitud se pasan como parámetros. La longitud debe quedar actualizada.
*/
void eliminarRepetidos(int m[], int *numElem) {
   int *pivote,  *actual, *aux; char *debug;
   pivote=m;
   actual=m+1;
   for(pivote=m; pivote<m+*numElem-1; pivote++) {
      for(actual=pivote+1; actual<m+*numElem;) {
         if (*pivote==*actual) {
            for(aux=actual; aux<m+*numElem-1; aux++) {
               debug=arrayToString(m,*numElem);
               *aux=*(aux+1); //desplazamos los elementos posterios una posic. a la izq.
               debug=arrayToString(m,*numElem);
            }
            (*numElem)--;
            debug=arrayToString(m,*numElem);
         } else {
            actual++;
         }
      }
   }
}

char *arrayToString(int m[], int n) {
   static char res[2048], aux[10]; //reservamos espacio de sobra
   int i;

   strcpy(res,"{");
   for(i=0; i<n; i++) {
      itoa(m[i],aux,10);
      strcat(res,aux);
      if (i<n-1) {
         strcat(res,",");
      }
   }
   strcat(res,"}");
   return res;
}