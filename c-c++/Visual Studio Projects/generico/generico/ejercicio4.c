#include <stdio.h>

int main(int argc, char **argv) {
   char car;
   int cont;

   printf("Introduce el car�cter: ");
   fflush(stdout)
   scanf("%c",&car);
   

   for(cont=1; cont<=5; cont++) {
      car++;
      printf("ASCII: %d. Car�cter: %c\n",car,car);
   }
   return 0;
}   
