//#include <stdio.h>
#include <math.h>


int main() {
   char car;
   int cont;
   double a,b=5.90,c=3.2;

   printf("\nIntroduce el carácter: ");
   scanf("%c",&car);

   for(cont=1; cont<=5; cont++) {
      car++;
      printf("ASCII: %d. Carácter: %c.\n",car,car);
   }
   printf("a: %lf, b: %lf, c: %lf\n",a,b,c);
   //a=pow(b,c);
   printf("a: %lf",a);
   //a=sqrt(a);
   printf("a: %lf",a);
}

