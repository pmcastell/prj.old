#include <stdio.h>

int main(int arg, char**argv) {
   int i,j,filas,espacios;
   filas=0;
   printf("\nIntroduce altura: ");
   scanf("%d",&filas);
   fflush(stdin);
   espacios=filas/2;
   for(i=1;i<=filas/2+1;i++) {
      for(j=1;j<=espacios;j++) {
         printf(" ");
      }
      printf("*");
      for(j=1;j<=2*(i-1)-1;j++) {
         printf(" ");
      }
      if (i>1) {
         printf("*");
      }
      printf("\n");
      espacios--;
   }
   espacios+=2;
   i-=2;
   for(;i>=1;i--) {
      for(j=1;j<=espacios;j++) {
         printf(" ");
      }
      printf("*");
      for(j=1;j<=2*(i-1)-1;j++) {
         printf(" ");
      }
      if (i>1) {
         printf("*");
      }
      printf("\n");
      espacios++;
   }
      
}
      
         
