#include <stdio.h>
#include "info.h"
#include "lista.h"

int main(int argc, char **argv) {
   INFO inf[]={
      {5,"Pepe"},
      {2,"Antonio"},
      {3,"Juan"},
      {1,"Luisito"},
      {4,"Alfredo"},
      {6,"Fernando"}
   };
   int i;
   char *auxDebug; //para depurar el programa
   LISTA l=listaCrear();

   for(i=0; i<sizeof(inf)/sizeof(INFO); i++) {
      listaInsertaOrdenado(&l,inf[i]);
      auxDebug=listaToString(l);
      printf("\n%s",auxDebug);
   }
   for(i=0; i<sizeof(inf)/sizeof(INFO); i++) {
      listaBorra(&l,listaPrimero(l));
      auxDebug=listaToString(l);
      printf("\n%s",auxDebug);
   }


}