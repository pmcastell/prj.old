#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "listaGenerica.h"

#pragma warning(disable:4996)
/*
9.Dado un fichero saltos.dat que contiene las tres mejores marcas del campeonato europeo de salto
de longitud de cada país. Cada registro de saltos.dat contiene el nombre del país y tres 
puntuaciones. Obtener dos listados con los resultados: el listado ordenado según las medias de 
las puntuaciones y el listado ordenado alfabéticamente por país.
*/

typedef struct {
   char pais[15];
   double marcas[3];
} INFO;
char * infoToString(const void *inf) {
   INFO *info;
   static char aux[200];

   info=(INFO *)inf;
   sprintf(aux,"{%-15s,%10.2lf,%10.2lf,%10.2lf}",info->pais,info->marcas[0],info->marcas[1],info->marcas[2]);
   return aux;
}

int fCompPuntuacion(const void* i1, const void *i2) {
   INFO *inf1, *inf2;
   double media1=0, media2=0;
   int i;

   inf1=(INFO *)i1;
   inf2=(INFO *)i2;
   for(i=0; i<3; i++) {
      media1+=inf1->marcas[i];
      media2+=inf2->marcas[i];
   }
   media1/=3;
   media2/=3;
   if (media1<media2) {
      return -1;
   } else if (media1>media2) {
      return 1;
   } else {
      return 0;
   }
}
int fCompPais(const void *i1, const void *i2) {
   INFO *inf1, *inf2;
   inf1=(INFO *)i1;
   inf2=(INFO *)i2;

   return strcmp(inf1->pais,inf2->pais);
}

void crearFicheroSaltos() {
   FILE *pSaltos=fopen("saltos.dat","wb");
   int i;
   
   INFO info[]={
      {"España",   {8.35,7.25,7.95}},
      {"Suiza",    {5.25,6.5,5.75}},
      {"USA",      {7.75,7.25,8.15}},
      {"Alemania", {7.00,7.05,7.07}},
      {"Rusia"   , {6.75,7.09,7.45}},
      {"Francia" , {5.15,5.25,5.35}},
      {"Italia"  , {6.07,6.15,6.25}},
      {"Australia",{7.05,7.15,7.95}}
   };
   for(i=0; i<sizeof(info)/sizeof(INFO); i++) {
      fwrite(&info[i],sizeof(INFO),1,pSaltos);
   }
   fclose(pSaltos);
   
}

int main(int argc, char** argv) {
   LISTA_GENERICA lPunt=listaCrear(),lPais=listaCrear();
   POS_GENERICA pos;
   FILE *fSaltos;
   INFO inf;

   crearFicheroSaltos();
   fSaltos=fopen("saltos.dat","rb");
   if (fSaltos==NULL) {
      printf("\nError abriendo saltos.dat");
      return -1;
   }
   fread(&inf,sizeof(INFO),1,fSaltos);
   while(!feof(fSaltos)) {
      listaInsertaOrdenDesc(lPunt,inf,&fCompPuntuacion);
      listaInsertaOrdenAsc(lPais,inf,&fCompPais);
      fread(&inf,sizeof(INFO),1,fSaltos);
   }
   fclose(fSaltos);
   printf("\nListado por media de puntuaciones");
   printf("\n---------------------------------");
   pos=listaPrimero(lPunt);
   while(pos!=listaFin(lPunt)) {
      inf=listaElemento(lPunt,pos,INFO);
      printf("\n%s. Media: %.2lf",infoToString(&inf),(inf.marcas[0]+inf.marcas[1]+inf.marcas[2])/3);
      pos=listaSig(lPunt,pos);
   }
   printf("\nListado por alfabético por países");
   printf("\n---------------------------------");
   pos=listaPrimero(lPais);
   while(pos!=listaFin(lPais)) {
      printf("\n%s",infoToString(&listaElemento(lPais,pos,INFO)));
      pos=listaSig(lPais,pos);
   }
   listaDestruye(lPunt);
   listaDestruye(lPais);

}
