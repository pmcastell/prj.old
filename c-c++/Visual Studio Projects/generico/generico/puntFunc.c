#include <stdio.h>
#include <stdlib.h>

void f1();
void f2();
void f3();
void f4();

void menu(char *[], void (*[])(), int);

typedef void (*pfVoidVoid)();

int main(int argc, char **argv) {
   char *opciones[]={"Opcion 1",
                     "Opcion 2",
                     "Opcion 3",
                     "Opcion 4"};
   void (*acciones[4])()={&f1,&f2,&f3,&f4};
   pfVoidVoid acciones2[4]={&f1,&f2,&f3,&f4};
   (*acciones[2])();

   menu(opciones,acciones,4);
   
}

void menu(char *opciones[], void (*acciones[])(), int tam) {
   int i,opc;
   do {
      do {
         for(i=0; i<tam; i++) {
            printf("%d -> %s.\n",i+1,opciones[i]);
         }
         printf("%d -> Salir.\n",i+1);
         printf("Elija una opción comprendida entre 1 y %d: ",i+1);
         fflush(stdout);
         fflush(stdin);
         scanf("%d",&opc);
      } while (opc<1 || opc>tam+1);
      (*acciones[opc-1])();
   } while (opc!=tam);   
}

void f1() {
  printf("\n\nEjecutando f1.\n");
}

void f2() {
  printf("\n\nEjecutando f2.\n");
}

void f3() {
  printf("\n\nEjecutando f3.\n");
}

void f4() {
  printf("\n\nEjecutando f4.\n");
}
