/*
CONVOCATORIA EXTRAORDINARIA

Procesar los siguientes ficheros para obtener el listado resumen de calificaciones por 
asignaturas (listado por asignatura, del número de alumnos con la misma calificación) 
de un centro educativo. 
Obtener además un fichero de texto (aprob.txt), con los nombres (en orden alfabético)  
de aquellos alumnos con todas las asignaturas aprobadas (un nombre en cada línea del 
fichero).
a.LISTADO:
	RESUMEN DE CALIFICACIONES POR ASIGNATURAS
	NOMBRE-ASIGNATURA		SUS	APR	NOT	SOB
	XXXXXXXXXXXXXXXXXX	999	999	999	999
b.FICHEROS DISPONIBLES: 
   i.Asignat.dat: código (mayor que cero) y nombre de asignatura.
  ii.Alumnos.dat: nombre, dni, pendiente (S/N) y cinco pares de código de asignatura y 
     su calificación.
 iii.Pendientes.dat: dni y tres pares de código de asignatura y su calificación (0-10).
c.CONDICIONES DE LOS DATOS
   i. Existen como máximo 20 asignaturas distintas.
  ii. Cada alumno cursa un mínimo de cinco asignaturas (fichero alumnos.dat).
 iii. Si el campo pendiente del fichero alumnos.dat es S, significa que dicho alumno 
      cursa asignaturas pendientes. 
  iv. Cada alumno puede tener un máximo de tres asignaturas pendientes, si encontramos 
      un campo código de asignatura a cero, significa que no cursa más asignaturas 
      pendientes.
*/
#include <stdio.h>
#include <string.h>

#pragma warning(disable:4996)
#define NUM_ASIGNATURAS 5
#define MAX_PENDIENTES  3
#define TRUE  1
#define FALSE 0
typedef struct {
   unsigned short int codigo;
   char nombre[20];
} ASIGNATURA;
typedef struct {
   unsigned short int codAsig;
   int nota;
} NOTAS;
typedef struct {
   char nombre[50],
        dni[10],
        pendiente;
   NOTAS calificaciones[NUM_ASIGNATURAS];
} ALUMNO;
typedef struct {
   char dni[10];
   NOTAS calificaciones[MAX_PENDIENTES];
} PENDIENTE;
typedef struct {
   char nombreAsig[40];
   unsigned int sus,
                apr,
                not,
                sob;
} LISTADO;

int crearFichero(char *nombre, void * tabla, int numElem, int tamEstruct) {
   int i;
   FILE *pf=fopen(nombre,"wb");
   if (pf==NULL) {
      return 0;
   }
   for(i=0; i<numElem; i++) {
      fwrite( ((char*)tabla)+i*tamEstruct,tamEstruct,1,pf);
   }
   fclose(pf);
   return 1;
}
void crearFicheroAsignaturas() {
   ASIGNATURA a[]={
      {0,""},
      {1,"MATES"},{2,"FÍSICA"},{3,"PLE"},{4,"I.U.A."},{5,"F.O.L."},
      {6,"S.I.M.R"},{7,"LENGUA"},{8,"A.D.A."},{9,"CASE"},{10,"FILOSOFÍA"},
      {11,"EDUCACIÓN FÍSICA"},{12,"HISTORIA"},{13,"GEOGRAFÍA"},{14,"GEOMETRÍA"},
      {15,"DIBUJO"},{16,"ÉTICA"},{17,"RELIGIÓN"},{18,"CIENCIAS NATURALES"},
      {19,"GRIEGO"},{20,"LATÍN"}
   };
   crearFichero("Asignat.dat",a,sizeof(a)/sizeof(ASIGNATURA),sizeof(ASIGNATURA));
}
void crearFicheroAlumnos() {
   ALUMNO a[]={
      {"Pepito","1",'N', {{1,5}, {2,7}, {3,9}, {4,9}, {5,6}}},
      {"Vicente","2",'S',{{1,6}, {2,8}, {3,10},{4,10},{5,7}}},
      {"Luisito","3",'S',{{1,7}, {2,9}, {3,1}, {4,1}, {5,8}}},
      {"Juanito","4",'S', {{1,8}, {2,10},{3,2}, {4,2}, {5,9}}},
      {"Alfonsito","5",'S', {{1,9}, {2,1} ,{3,3}, {4,3}, {5,10}}},
      {"Vicente","6",'S', {{1,10},{2,2} ,{3,4}, {4,4}, {5,1}}},
      {"Pedro","7",'N', {{1,1}, {2,3} ,{3,5}, {4,5}, {5,2}}},
      {"Alberto","8",'S', {{1,2}, {2,4} ,{3,6}, {4,6}, {5,3}}},
      {"Fernando","9",'N', {{1,3}, {2,5} ,{3,7}, {4,7}, {5,4}}},
      {"Francisco","10",'S',{{1,4}, {2,6} ,{3,8}, {4,8}, {5,5}}}
   };
   crearFichero("Alumnos.dat",a,sizeof(a)/sizeof(ALUMNO),sizeof(ALUMNO));
}
void crearFicheroPendientes() {
   PENDIENTE p[]={
      {"2",{{6,5},{0,0},{0,0}}},
      {"3",{{6,7},{7,8},{0,0}}},
      {"4",{{6,4},{7,3},{8,5}}},
      {"5",{{6,5},{7,5},{0,0}}},
      {"6",{{6,6},{0,0},{0,0}}},
      {"8",{{6,5},{0,0},{0,0}}},
      {"10",{{6,4},{7,4},{8,4}}}
   };
   crearFichero("Pendient.dat",p,sizeof(p)/sizeof(PENDIENTE),sizeof(PENDIENTE));
}
void cargarTablaAsignaturas(char *fich, LISTADO l[],int *tam) {
   ASIGNATURA a;
   FILE *fasig=fopen("Asignat.dat","rb");
   int i;

   *tam=0;
   fread(&a,sizeof(ASIGNATURA),1,fasig);
   while(!feof(fasig)) {
      strcpy(l[a.codigo].nombreAsig,a.nombre);
      (*tam)++;
      fread(&a,sizeof(ASIGNATURA),1,fasig);
   }
   for(i=0; i<*tam; i++) {
      l[i].sus=0;
      l[i].apr=0;
      l[i].not=0;
      l[i].sob=0;
   }
   fclose(fasig);
}
int asignaNotaListado(int cod, int nota, LISTADO l[]) {
   int alumnoSuspenso=FALSE;

   if (nota<5) {
      alumnoSuspenso=TRUE;
      l[cod].sus++;
   } else if (nota<7) {
      l[cod].apr++;
   } else if (nota<9) {
      l[cod].not++;
   } else {
      l[cod].sob++;
   }
   return alumnoSuspenso;
}

void listar(LISTADO l[], int tam) {
   int i;
	
   printf("\nRESUMEN DE CALIFICACIONES POR ASIGNATURAS\n");
   printf("NOMBRE-ASIGNATURA   SUS   APR   NOT   SOB\n");
   printf("-----------------------------------------\n");
   for(i=1; i<tam; i++) {
      printf("%-19s %3d   %3d   %3d   %3d\n",l[i].nombreAsig,l[i].sus,l[i].apr,
         l[i].not,l[i].sob);
   }


}

int main(int argc, char**argv) {
   FILE *falum,  *fpend, *faprob;
   ALUMNO regAlum;
   PENDIENTE regPend;
   LISTADO listado[21];
   int i,alumnoSuspenso,tamListado;

   crearFicheroAsignaturas();
   crearFicheroAlumnos();
   crearFicheroPendientes();
   falum=fopen("Alumnos.dat","rb");
   //fasig=fopen("Asignat.dat","rb");
   fpend=fopen("Pendient.dat","rb");
   faprob=fopen("aprob.txt","wt");
   if (falum==NULL || fpend==NULL || faprob==NULL) {
      fprintf(stderr,"Error al abrir ficheros.");
      return -1;
   }
   cargarTablaAsignaturas("Asignat.dat",listado,&tamListado);
   fread(&regAlum,sizeof(ALUMNO),1,falum);
   while(!feof(falum)) {
      alumnoSuspenso=FALSE;
      for(i=0; i<NUM_ASIGNATURAS; i++) {
         alumnoSuspenso=asignaNotaListado(regAlum.calificaciones[i].codAsig,
                           regAlum.calificaciones[i].nota, listado);
      }
      if (regAlum.pendiente=='S') {
         fseek(fpend,0,SEEK_SET);
         fread(&regPend,sizeof(PENDIENTE),1,fpend);
         while(!feof(fpend) && strcmp(regPend.dni,regAlum.dni)!=0) {
            fread(&regPend,sizeof(PENDIENTE),1,fpend);
         }
         if (feof(fpend)) {
            printf("Error Alumno con el campo pendiente 'S' y sin registros en Pendient.dat");
         } else {
            for(i=0; i<MAX_PENDIENTES; i++) {
               alumnoSuspenso=asignaNotaListado(regPend.calificaciones[i].codAsig,
                  regPend.calificaciones[i].nota, listado);
            }
         }
      }
      if (!alumnoSuspenso) {
         fputs(regAlum.nombre,faprob);
         fputs("\n",faprob);
      }
      fread(&regAlum,sizeof(ALUMNO),1,falum);
   }
   listar(listado,tamListado);
   fclose(falum);
   fclose(fpend);
   fclose(faprob);
}
/*
      typedef struct {
   char nombre[50],
        dni[10],
        pendiente;
   NOTAS calificaciones[5];
} ALUMNO;

*/