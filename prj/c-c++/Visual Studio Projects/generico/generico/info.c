#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#pragma warning(disable:4996)
#include "info.h"


//lee del teclado una estructura de tipo INFO
void infoLee(INFO *info) {
   printf("\nIntroduce el número: ");
   fflush(stdin);
   scanf("%d",info->num);
   printf("\nIntroduce el nombre: ");
   fflush(stdin);
   gets(info->nombre);
}
//Escribe en pantalla una estructura de tipo INFO
void infoEscribir(INFO info) {
   printf("\nNúmero: %d.",info.num);
   printf("\nNombre: %s.",info.nombre);
}
//Devuelve una cadena con los campos de la estructura info entre llaves
char * infoToString(INFO info) {
   static char aux[9+11];

   strcpy(aux,"{");
   itoa(info.num,&aux[1],10);
   strcat(aux,",");
   strcat(aux,info.nombre);
   strcat(aux,"}");
   return aux;
}
//devuelve <0 si info1<info2, 0 si info1==info2, >0 si info1>info2
int infoCompara(INFO info1, INFO info2) {
   return info1.num-info2.num;
}