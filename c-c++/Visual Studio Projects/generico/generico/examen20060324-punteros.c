#include <stdio.h>
#include <stdlib.h>
#pragma warning(disable:4996)

//prototipos
void invierte(char *s);
char *hallarSubcadena(char *principal, char *secundaria);
int cuentaRepeticiones(char *principal, char *secundaria); 
int eliminaRepetidos(int *matriz, int tam, int bajo, int alto);
void imprimeMatriz(double *m,int n);
int compara(const void *e1, const void *e2);
void leeImprimeOrdenado(void);

//para probar las funciones
int main(int argc, char** argv) {
   char cad[]="hola mundo, adi�s mundo cruel";
   int n;
   int matriz[]={1,2,3,4,5,6,7,8,9,10};

   invierte(cad);
   n=cuentaRepeticiones(cad,"odnum");
   n=eliminaRepetidos(matriz,10,3,7);
   leeImprimeOrdenado();
}





/*1.Funci�n con el siguiente prototipo: void invierte(char *); que recibe una cadena de
caracteres y la invierte. Por ejemplo si se la llama con una cadena cuyo valor sea �hola� la 
cadena quedar� con �aloh�.
*/
void invierte(char *s) {
   char *fin=s;
   char charAux;

   while(*fin!='\0') {
      fin++;
   } //avanzamos fin hasta el '\0' del final
   fin--; //ahora fin apunta al �ltimo car�cter de s
   while(s<fin) {
      charAux=*s; //intercambiamos el car�cter al que apunta s con 
      *s=*fin;     // el car�cter al que 
      *fin=charAux; // apunta fin
      s++; 
      fin--;
   }
}


/*
2.Funci�n con el siguiente prototipo: int cuentaRepeticiones(char *principal, char *secundaria); 
que nos devuelve el n�mero de veces que la cadena secundaria se encuentra dentro de la principal.
(No debe usar indexaci�n de punteros), por ejemplo, si la llama con (�aquella ma�ana lav� toda su
ropa�,�la�) deber� devolver 2.
*/
char *hallarSubcadena(char *principal, char *secundaria) {
   char *ppal, *psec;

   while(*principal!='\0') {
      if (*principal==*secundaria) {
         ppal=principal+1;
         psec=secundaria+1;
         while(*ppal==*psec && *psec!='\0') {
            ppal++;
            psec++;
         }
         if (*psec=='\0') {
            return principal;
         }
      }
      principal++;
   }
   return NULL;
}

int cuentaRepeticiones(char *principal, char *secundaria) {
   char *paux;
   int rep=0;

   paux=hallarSubcadena(principal,secundaria);
   while(paux!=NULL) {
      rep++;
      paux=hallarSubcadena(paux+1,secundaria);
   }
   return rep;
}

/*
3.Funci�n con el siguiente prototipo: int eliminaRepetidos(int *matriz, int tam, int bajo, int 
alto); que elimina de la matriz recibida como primer par�metro todos los n�meros enteros que 
sean menores que bajo y todos los que sean mayores que alto y devuelve el n�mero de elementos 
eliminados; por ejemplo, si la llamamos con ({1,2,3,4,5,6,7,8,9,10},3,7) la matriz deber�a 
quedar: {3,4,5,6,7} y deber�a devolver 5 (elementos que se han eliminado).
*/
int eliminaRepetidos(int *matriz, int tam, int bajo, int alto) {
   int eliminados=0;
   int *fin=matriz+tam-1, *pmat;

   while(matriz<=fin) {
      if (*matriz<bajo || *matriz>alto) {
         for(pmat=matriz; pmat<fin; pmat++) {
            *pmat=*(pmat+1);
         }
         fin--;
         eliminados++;
      } else {
         matriz++;
      }
   }
   return eliminados;
}


/*
4.Funci�n con el siguiente prototipo: void leeImprimeOrdenado(void); que repetidamente pregunta 
al usuario cuantos n�meros reales (double) va a introducir por teclado, construye una matriz de 
ese tama�o, la ordena (utilizando qsort de <stdlib.h>) y la imprime, hasta que el usuario indique
que desea introducir 0 n�meros. (AQU� SI PUEDE USARSE INDEXACI�N DE PUNTEROS)
*/
void imprimeMatriz(double *m,int n) {
   int i;

   printf("{");
   for(i=0; i<n; i++) {
      printf("%.2lf",m[i]);
      if (i<n-1) {
         printf(",");
      }
   }
   printf("}");
}

int compara(const void *e1, const void *e2) {
   double *d1=(double *)e1, *d2=(double *)e2;
   if (*d1<*d2) {
      return -1;
   } else if (*d1==*d2) {
      return 0;
   } else {
      return 1;
   }
}
void leeImprimeOrdenado(void) {
   int n,i;
   double *matriz;

   do {
      printf("\n�Cu�ntos n�meros desea introducir? ");
      fflush(stdin);
      scanf("%d",&n);
      if (n>0) {
         matriz=(double *) malloc(n*sizeof(double)); //constru�mos matriz para n n�meros
         if (matriz==NULL) {
            printf("\nFunci�n leeImprimeOrdenado. Error de asignaci�n de memoria.\n");
            return;
         }
         for(i=0; i<n; i++) {
            printf("\n m[%d]: ",i);
            fflush(stdin);
            scanf("%lf",&matriz[i]);
         }
         printf("\nMatriz introducida: ");
         imprimeMatriz(matriz,n);
         qsort(matriz,n,sizeof(double),&compara);
         printf("\nLa matriz queda: ");
         imprimeMatriz(matriz,n);
         free(matriz);
      }
   }while(n>0);
}
