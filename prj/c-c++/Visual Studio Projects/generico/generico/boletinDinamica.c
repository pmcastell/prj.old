/*
4.Dados dos ficheros con la misma estructura de registro (dos campos: un número entero 
y un nombre (máximo de 11 caracteres)), no ordenados. Fusionarlos obteniendo uno nuevo 
ordenado, que contenga todos los existentes.
*/
#include <stdio.h>
#include "listaGenerica.h"

#pragma warning(disable:4996)

typedef struct {
   int cod;
   char nombre[12];
} INFO;

void crearFicherosEntrada();
void fusionar(char *, char*, char*);
void imprimirFicheroSalida();
int infoComp(const void*,const void*); //función comparac.para insertar en lista genérica

int main(int argc, char**argv) {
   crearFicherosEntrada();
   fusionar("fich1.dat","fich2.dat","fich3.dat");
   imprimirFicheroSalida();
}

void crearFicherosEntrada() {
   FILE *pf1=fopen("fich1.dat","wb"), *pf2=fopen("fich2.dat","wb");
   INFO inf1[]={{3,"antonio"},{2,"luis"},{4,"Sebastián"},{8,"alberto"},{1,"marta"}},
      inf2[]={{2,"alfredo"},{3,"antonio"},{5,"enrique"},{1,"adrian"},{9,"luisa"}};
   int i;

   for(i=0; i<sizeof(inf1)/sizeof(INFO); i++) {
      fwrite(&inf1[i],sizeof(INFO),1,pf1);
      fwrite(&inf2[i],sizeof(INFO),1,pf2);
   }
   fclose(pf1);
   fclose(pf2);
}

void imprimirFicheroSalida() {
   FILE *pf=fopen("fich3.dat","rb");
   INFO inf;

   fread(&inf,sizeof(INFO),1,pf);
   while(!feof(pf)) {
      printf("\n{%d,%s}",inf.cod,inf.nombre);
      fread(&inf,sizeof(INFO),1,pf);
   }
   fclose(pf);

}

void fusionar(char *f1, char * f2, char *salida) {
   FILE *pf1, *pf2,*psal;
   LISTA_GENERICA lf1=listaCrear(), lf2=listaCrear();
   POS_GENERICA pos1, pos2;
   INFO info1,info2;


   pf1=fopen(f1,"rb");
   pf2=fopen(f2,"rb");
   psal=fopen(salida,"wb");
   if (pf1==NULL || pf2==NULL || psal==NULL) {
      printf("Error abriendo ficheros.");
      return;
   }
   //cargamos primer fichero usando el T.A.D. LISTA_GENERICA
   fread(&info1,sizeof(INFO),1,pf1);
   while(!feof(pf1)) {
      listaInsertaOrdenAsc(lf1,info1,&infoComp);
      fread(&info1,sizeof(INFO),1,pf1);
   }
   fclose(pf1);
   fread(&info2,sizeof(INFO),1,pf2);
   while(!feof(pf2)) {
      listaInsertaOrdenAsc(lf2,info2,&infoComp);
      fread(&info2,sizeof(INFO),1,pf2);
   }
   fclose(pf2);
   pos1=listaPrimero(lf1);
   pos2=listaPrimero(lf2);
   while(pos1!=listaFin(lf1) && pos2!=listaFin(lf2)) {
      info1=listaElemento(lf1,pos1,INFO);
      info2=listaElemento(lf2,pos2,INFO);
      if (info1.cod<info2.cod) {
         fwrite(&info1,sizeof(INFO),1,psal);
         pos1=listaSig(lf1,pos1);
      } else if (info1.cod==info2.cod) {
         fwrite(&info1,sizeof(INFO),1,psal);
         fwrite(&info2,sizeof(INFO),1,psal);
         pos1=listaSig(lf1,pos1);
         pos2=listaSig(lf2,pos2);
      } else { // info1.cod>info2.cod
         fwrite(&info2,sizeof(INFO),1,psal);
         pos2=listaSig(lf2,pos2);
      }
   }
   while(pos1!=listaFin(lf1)) {
      fwrite(&listaElemento(lf1,pos1,INFO),sizeof(INFO),1,psal);
      pos1=listaSig(lf1,pos1);
   }
   while(pos2!=listaFin(lf2)) {
      fwrite(&listaElemento(lf2,pos2,INFO),sizeof(INFO),1,psal);
      pos2=listaSig(lf2,pos2);
   }
   fclose(psal);
   listaDestruye(lf1);
   listaDestruye(lf2);
}

int infoComp(const void *i1, const void *i2) {
   INFO *pi1, *pi2;

   pi1=(INFO *)i1;
   pi2=(INFO *)i2;
   return pi1->cod-pi2->cod;
}