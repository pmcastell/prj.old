#include <stdio.h>

int main(int argc, char **argv) {
   char car;
   int cont;

   printf("Introduce el carácter: ");
   fflush(stdout)
   scanf("%c",&car);
   

   for(cont=1; cont<=5; cont++) {
      car++;
      printf("ASCII: %d. Carácter: %c\n",car,car);
   }
   return 0;
}   
