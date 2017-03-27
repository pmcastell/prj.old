#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "listaGenerica.h"

#pragma warning(disable:4996)
/*
10.Realizar una función que cree un fichero (rdos.dat), a partir del procesamiento de los 
datos contenidos en otros ficheros. Partiremos de los siguientes ficheros:

asignatura.dat: cod(código asignatura) entero y mayor que cero; nom(nombre asignatura) 
máximo de 30 caracteres. Existen 10 asignaturas distintas.

Curso.dat: cod(código curso) entero y mayor que cero; nom (nombre curso, por ejemplo 4ESOA) 
de 5 caracteres. 

Alumnos.dat: dni, nombre, apellidos, dirección, teléfono y codc (código del curso).

Notas.dat: dni y diez campos código de asignatura con sus diez correspondientes 
calificaciones (representadas con un carácter: ‘S’ 9-10 puntos, ‘N’ 7-8 puntos, ‘B’ 6 puntos,
‘C’ 5 puntos).

Rdo.dat: dni y nombre alumno de aquellos con todas las materias aprobadas; y nombre del 
curso de cada alumno.

Durante el proceso de escritura de rdo.dat, se proporcionará el listado  siguiente:
NOMBRE ALUMNO  ASIG1-NOTA1   ASIG2-NOTA2  . . . .  ASIG10-NOTA10 
*/
typedef struct {
   unsigned short cod;
   char nom[30];
} REG_ASIGNATURAS;

typedef struct {
   unsigned short cod;
   char nom[6];
} REG_CURSOS;

typedef struct {
   char dni[10];
   char nombre[15],
        apellidos[30],
        dir[40],
        tlf[10];
   unsigned short codc;
} REG_ALUMNOS;
#define NUM_NOTAS 10
#define NUM_ASIGNATURAS 10
typedef struct {
   char dni[10];
   unsigned short asignaturas[NUM_ASIGNATURAS];
   char notas[NUM_NOTAS];
} REG_NOTAS;

typedef struct {
   char dni[10];
   char nombre[15],
        apellidos[30],
        curso[6];
} REG_RDOS;

FILE * abrirFichero(char *nomFich, char *modo);
void crearFicheros();
void proceso();
int todoAprobado(REG_NOTAS nota);
void listaResultados();

int main(int argc, char **argv) {
   crearFicheros();
   proceso();
   listaResultados();
}

void listaResultados() {
   FILE *pf=abrirFichero("rdos.dat","rb");
   REG_RDOS rdo;
   printf("------------------------------------------------------------------------------------------------");
   fread(&rdo,sizeof(REG_RDOS),1,pf);
   while(!feof(pf)) {
      printf("\n%9s %-15s %-30s %-30s ",rdo.dni,rdo.nombre,rdo.apellidos,rdo.curso);
      fread(&rdo,sizeof(REG_RDOS),1,pf);
   }
   fclose(pf);
}

FILE * abrirFichero(char *nomFich, char *modo) {
   FILE *pf=fopen(nomFich,modo);
   if (pf==NULL) {
      printf("\nError abriendo fichero: %s.",nomFich);
      exit(-1);
   }
   return pf;
}
void cargarFicheroLista(FILE *pf, LISTA_GENERICA *l, int tam, int (*pfuncComp)(const void*,const void*)) {
   char *buf=(char *)malloc(tam);
   if (buf==NULL) {
      printf("\nError pidiendo memoria. No se pudo cargar fichero en lista.");
      exit(-1);
   }
   fread(buf,tam,1,pf);
   while(!feof(pf)) {
      _listaInsertaOrden(l,buf,tam,pfuncComp);
      fread(buf,tam,1,pf);
   }
   fclose(pf);
   free(buf);
}

int compAsig(const void *a1, const void *a2) {
   REG_ASIGNATURAS *aa1, *aa2;
   aa1=(REG_ASIGNATURAS *)a1;
   aa2=(REG_ASIGNATURAS *)a2;
   return aa1->cod-aa2->cod;
}

int compCurso(const void *c1, const void *c2) {
   REG_CURSOS *cc1, *cc2;
   cc1=(REG_CURSOS *)c1;
   cc2=(REG_CURSOS *)c2;
   return cc1->cod-cc2->cod;
}

int compAlum(const void *a1, const void *a2) {
   REG_ALUMNOS *aa1, *aa2;
   aa1=(REG_ALUMNOS *)a1;
   aa2=(REG_ALUMNOS *)a2;
   return strcmp(aa1->dni,aa2->dni);
}


int compNot(const void *n1, const void *n2) {
   REG_NOTAS *nn1, *nn2;
   nn1=(REG_NOTAS *)n1;
   nn2=(REG_NOTAS *)n2;
   return strcmp(nn1->dni,nn2->dni);
}


void proceso() {
   FILE *fAsig,*fCur, *fAlum, *fNot, *fRdo;
   LISTA_GENERICA lAsig=listaCrear(), lCur=listaCrear(), lAlum=listaCrear(), lNot=listaCrear();
   POS_GENERICA posAlum,posNota;
   REG_ALUMNOS alum;
   REG_NOTAS nota;
   REG_CURSOS curso;
   REG_ASIGNATURAS asig;
   REG_RDOS rdo;
   int comp,i;

   fAsig=abrirFichero("asignatura.dat","rb");
   fCur =abrirFichero("curso.dat","rb");
   fAlum=abrirFichero("alumnos.dat","rb");
   fNot =abrirFichero("notas.dat","rb");
   fRdo =abrirFichero("rdos.dat","wb");

   cargarFicheroLista(fAsig,&lAsig,sizeof(REG_ASIGNATURAS),&compAsig);
   cargarFicheroLista(fCur,&lCur,sizeof(REG_CURSOS),&compCurso);
   cargarFicheroLista(fAlum,&lAlum,sizeof(REG_ALUMNOS),&compAlum);
   cargarFicheroLista(fNot,&lNot,sizeof(REG_NOTAS),&compNot);
   posAlum=listaPrimero(lAlum);
   posNota=listaPrimero(lNot);
   while(posAlum!=listaFin(lAlum) && posNota!=listaFin(lNot)) {
      alum=listaElemento(lAlum,posAlum,REG_ALUMNOS);
      nota=listaElemento(lNot,posNota,REG_NOTAS);
      comp=strcmp(alum.dni,nota.dni);
      if (comp==0) {
         if (todoAprobado(nota)) {
            strcpy(rdo.dni,alum.dni);
            curso.cod=alum.codc;
            strcpy(rdo.curso,listaElemento(lCur,listaBuscaElemento(lCur,curso,compCurso),REG_CURSOS).nom);
            strcpy(rdo.nombre,alum.nombre);
            strcpy(rdo.apellidos,alum.apellidos);
            printf("\n%-15s %-30s ",rdo.nombre,rdo.apellidos);
            for(i=0; i<10; i++) {
               asig.cod=nota.asignaturas[i];
               printf("%30s-%c ",listaElemento(lAsig,listaBuscaElemento(lAsig,asig,compAsig),REG_ASIGNATURAS).nom,
                  nota.notas[i]);
            }
            fwrite(&rdo,sizeof(REG_RDOS),1,fRdo);
         } 
         posAlum=listaSig(lAlum,posAlum);
         posNota=listaSig(lNot,posNota);
      } else if (comp<0) {
         printf("\nError Alumno si notas: %s %s.",alum.nombre,alum.apellidos);
         posAlum=listaSig(lAlum,posAlum);
      } else {
         printf("\nError hay notas de un alumno no encontrado, dni: %s.",nota.dni);
         posNota=listaSig(lNot,posNota);
      }
   }
   fclose(fAsig);fclose(fCur);fclose(fAlum);fclose(fNot);fclose(fRdo);
}

int todoAprobado(REG_NOTAS regNota) {
   int i;
   char nota;

   for(i=0; i<NUM_NOTAS; i++) {
      nota=regNota.notas[i];
      if (nota!='S' && nota!='N' && nota!='B' && nota!='C') {
         return 0;
      }
   }
   return 1;
}

void crearFicheros() {
   REG_ASIGNATURAS asig[]={
      {1,"PLE"},
      {2,"ADA"},
      {3,"SIMMR"},
      {4,"FOL"},
      {5,"IMS"},
      {6,"OBD"},
      {7,"RET"},
      {8,"FISICA"},
      {9,"MATES"},
      {10,"IUA"}
   };
   REG_CURSOS curso[]={
      {1,"1-ESO"},
      {2,"2-ESO"},
      {3,"3-ESO"},
      {4,"4-ESO"},
      {5,"1-BAC"},
      {6,"2-BAC"},
      {7,"1-TES"},
      {8,"2-TES"},
      {9,"1-DAI"},
      {10,"2-DAI"}
   };
   REG_ALUMNOS alumno[]={
      {"12345684G","Gerardo","Suárez Col","C/ Guisante, 7. Sevilla","954123462",7},
      {"12345682E","Emilio","Quintero Alvarez","C/ Enebro, 5. Sevilla","954123460",5},
      {"12345685H","Hermenegildo","Torres Paz","C/ Huerta, 8. Sevilla","954123463",8},
      {"12345679B","Bernardo","Navarro Ortíz","C/ Betunia, 2. Sevilla","954123457",2},
      {"12345681D","Daniel","Pérez López","C/ Dátil, 4. Sevilla","954123459",4},
      {"12345680C","Carlos","Ortensio Florindo","C/ Clavel, 3. Sevilla","954123458",3},
      {"12345683F","Fancisco","Ruiz Bermúdez","C/ Flor, 6. Sevilla","954123461",6},
      {"12345678A","Antonio","Marcos Pérez","C/ Amapola, 1. Sevilla","954123456",1}
   };
   REG_NOTAS nota[]={
      {"12345682E",{1,2,3,4,5,6,7,8,9,10},{'I','S','S','S','S','S','S','S','S','S'}},
      {"12345679B",{1,2,3,4,5,6,7,8,9,10},{'I','S','S','S','S','S','S','S','S','S'}},
      {"12345683F",{1,2,3,4,5,6,7,8,9,10},{'S','S','S','S','S','S','S','S','S','I'}},
      {"12345680C",{1,2,3,4,5,6,7,8,9,10},{'N','C','N','C','N','C','N','B','B','B'}},
      {"12345681D",{1,2,3,4,5,6,7,8,9,10},{'S','S','S','S','S','S','S','S','S','S'}},
      {"12345678A",{1,2,3,4,5,6,7,8,9,10},{'C','C','B','B','B','B','N','N','N','S'}},
      {"12345684G",{1,2,3,4,5,6,7,8,9,10},{'S','S','S','S','S','S','S','S','S','S'}},
      {"12345685H",{1,2,3,4,5,6,7,8,9,10},{'S','B','C','B','C','N','N','N','C','C'}}
   };
   int i;
   FILE *pf=fopen("asignatura.dat","wb");
   if (pf==NULL) {
      printf("\nError creando asignatura.dat");
      return;
   }
   for(i=0; i<sizeof(asig)/sizeof(REG_ASIGNATURAS); i++) {
      fwrite(&asig[i],sizeof(REG_ASIGNATURAS),1,pf);
   }
   fclose(pf);
   pf=fopen("curso.dat","wb");
   if (pf==NULL) {
      printf("\nError creando curso.dat");
      return;
   }
   for(i=0; i<sizeof(curso)/sizeof(REG_CURSOS);i++) {
      fwrite(&curso[i],sizeof(REG_CURSOS),1,pf);
   }
   fclose(pf);
   pf=fopen("alumnos.dat","wb");
   if (pf==NULL) {
      printf("\nError creando alumnos.dat");
      return;
   }
   for(i=0; i<sizeof(alumno)/sizeof(REG_ALUMNOS); i++) {
      fwrite(&alumno[i],sizeof(REG_ALUMNOS),1,pf);
   }
   fclose(pf);
   pf=fopen("notas.dat","wb");
   if (pf==NULL) {
      printf("\nError creando notas.dat");
      return;
   }
   for(i=0; i<sizeof(nota)/sizeof(REG_NOTAS);i++) {
      fwrite(&nota[i],sizeof(REG_NOTAS),1,pf);
   }
   fclose(pf);
}

char *asigToString(const void *a) {
   static char aux[50];
   REG_ASIGNATURAS *aa;
   aa=(REG_ASIGNATURAS *)a;
   sprintf(aux,"{%3u,%s}",aa->cod,aa->nom);
   return aux;
}
char *cursoToString(const void *c) {
   static char aux[20];
   REG_CURSOS *cc;
   cc=(REG_CURSOS *)c;
   sprintf(aux,"{%3u,%s}",cc->cod,cc->nom);
   return aux;
}
char *alumToString(const void *a) {
   static char aux[200];
   REG_ALUMNOS *aa;

   aa=(REG_ALUMNOS *)a;
   sprintf(aux,"{%s,%s,%s,%s,%s}",aa->dni,aa->nombre,aa->apellidos,aa->dir,aa->tlf,aa->codc);
   return aux;
}
char *notaToString(const void *n) {
   static char aux[200];
   REG_NOTAS * nn;
   int i;

   nn=(REG_NOTAS *)n;
   sprintf(aux,"{%s,{",nn->dni);
   for(i=0; i<NUM_ASIGNATURAS; i++) {
      itoa(nn->asignaturas[i],&aux[strlen(aux)],10);
      strcat(aux,",");
   }
   strcat(aux,"},{");
   for(i=0; i<NUM_NOTAS; i++) {
      aux[strlen(aux)]=nn->notas[i];
      aux[strlen(aux)]=0;
      if (i<9) {
         strcat(aux,",");
      }
   }
   strcat(aux,"}}");
   return aux;
}
