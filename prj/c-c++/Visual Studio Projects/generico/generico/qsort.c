#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void imprime(char **cadenas, int n) {
   int i;

   printf("El array es: \n");   
   for(i=0; i<n; i++) {
      printf("array[%d]=%s.\n",i,cadenas[i]);
   }
}
 
int comp(const void *e1, const void *e2) {
   return strcmp(*(char **)e1,*(char**)e2);
}
   
int compEnteros(const void *e1, const void *e2) {
	return *((int *)e1)-*((int *)e2);
}
int main(int argc, char **argv) {
   char *cadenas[]={"Hola mundo",
      		        "El Instituto",
					"La mesilla",
					"La mesita",
                    "La mesa",
                    "la silla",
                    "el coche",
                    "las llaves",
                    "la pared"};
   int enteros[]={5,1,3,2,9,8,6,2,1};

                    
   imprime(cadenas,7);
   qsort(cadenas,7,sizeof(char *),&comp);
   imprime(cadenas,7);
   qsort(enteros,sizeof(enteros)/sizeof(int),sizeof(int),&compEnteros);
}

