#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <locale.h>

#pragma warning(disable:4996)

typedef struct {
     unsigned short int 
         dia,
         mes,
         anio;
} FECHA;

typedef struct {
   char nombre[50];
   FECHA cumple;
   char tipo;
} CUMPLES;
   

    //devuelve la fecha actual del sistema operativo
FECHA fechaActual() {
    FECHA f;
    struct tm *ahora;
    time_t hora;

    time( &hora );  
    ahora = localtime( &hora ); 
    f.dia=ahora->tm_mday;
    f.mes=ahora->tm_mon+1;
    f.anio=1900+ahora->tm_year;
    return f;
}
   char nombre[50];
   FECHA cumple;
   char tipo;

void listarCumples(CUMPLES *tabla,int n) {
   CUMPLES c;
   int i;

   printf("\n%-30s %-10s %s","NOMBRE","FECHA","TIPO");
   printf("\n%-30s %-10s %s","------------------------------","----------","----");
   for(i=0; i<n; i++) {
      c=tabla[i];
      printf("\n%-30s %02d/%02d/%04d %c",c.nombre,c.cumple.dia,c.cumple.mes,c.cumple.anio,c.tipo);
   }
   printf("\n%-30s %-10s %s","------------------------------","----------","----");
}
CUMPLES * cargarCumples(char *fichero) {
   FILE *f=fopen(fichero,"rt");
   char aux[1024];
   int numRegs=0;

   fgets(aux,1024,f);
   while(!feof(f)) {
      numRegs++;
      fgets(aux,1024,f);
   }
   fclose(f);
   CUMPLES *r=new CUMPLES[numRegs];
   f=fopen(fichero,"rt");
   while(!feof(f)) {
   }
   return r;

}

void pausa() {
    printf("\nPulsa una tecla para terminar...");
    getchar();
}

int main(int argc, char**argv) {
   int diasMeses[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
   CUMPLES cump[]={
      {"PAPA",{12,11,2006},'C'},
      {"MAMA",{14,1,2007},'C'},
      {"PAQUI",{1,11,1958},'C'},
      {"ANTONIO",{22,3,1960},'C'},
      {"JUAN",{6,6,1963},'C'},
      {"FRANC",{6,8,1965},'C'},
      {"MARI",{21,10,1969},'C'},
      {"JORGE",{30,9,1975},'C'},

      {"RAFA",{21,9,1987},'C'},
      {"ALBERTO",{9,8,2006},'C'},
      {"DAVID",{13,5,2006},'C'},
      {"LAURA",{20,4,1996},'C'},
      {"ANTOÑIN",{26,4,1994},'C'},

      {"ESTHER",{20,2,2007},'C'},
      {"CRISTINA",{7,3,2007},'C'},
      
      {"PAPA, ANTONIO, CHICO RICO",{13,6,2006},'S'},
      {"MAMA, MARI",{12,9,2006},'S'},
      {"PAQUI",{4,10,2006},'S'},
      {"JUAN",{24,6,2006},'S'},
      {"FRANC",{3,12,2006},'S'},
      {"JORGE",{23,4,2006},'S'},

      {"RAFA",{24,10,2006},'S'},

      {"DIA DEL PADRE",{19,3,2007},'S'},
      {"DIA DE LA MADRE",{6,5,2006},'S'},


      {"ESTHER",{1,7,2006},'S'},
      {"CRISTINA",{24,7,2006},'S'},

      {"JUAN JOSÉ",{5,7,1958},'C'},
      {"MARÍA",{8,9,2006},'S'},
      {"FÁTIMA",{17,12,2006},'C'},
      {"JUAN JOSÉ",{19,3,2007},'S'},

      {"RENOVACIÓN CERTIFICADO FNMT",{25,4,2006},'C'}
      
   };
   FECHA actual=fechaActual();
   int hayCumples=0, dias,i,j;


   setlocale(LC_ALL,"spanish_spain.1252"); //poner teclado español

   if (argc>1) {
      for(i=1; i<argc; i++) {
         if (argv[i][0]=='-' || argv[i][0]=='/') {
            for(j=1;argv[i][j];j++) {
               switch(argv[i][j]) {
                  case 'l': case 'L':
                     printf("\n\n");
                     listarCumples(cump,sizeof(cump)/sizeof(CUMPLES));
                     printf("\n\n");
                     break;
                  case 'h': case 'H': case '?':
                     printf("\n\nUso %s [opcion].",argv[0]);
                     printf("\nOpcion: -l -> listar cumples");
                     printf("\n        -h -> esta ayuda");
                     printf("\nSi se omite opción se listarán los cumples o santos a menos de 5 días vista.\n\n");
                     break;
               }
            }
         }
      }
      exit(0);
   }

   for(int i=0; i<sizeof(cump)/sizeof(CUMPLES); i++) {
      if (cump[i].cumple.dia>5 && actual.mes==cump[i].cumple.mes) {
         dias=cump[i].cumple.dia-actual.dia;
      } else if (actual.mes+1==cump[i].cumple.mes) {
         dias=diasMeses[actual.mes]-actual.dia+cump[i].cumple.dia;
      } else {
         dias=-1;
      }


      if (dias<=5 && dias >=0) {
         hayCumples=1;
         if (dias==0) {
            printf("\n* Hoy es ");
         } else {
            printf("\n* Faltan %d días para ",dias);
         }
         if (cump[i].tipo=='C') {
            printf("el Cumpleaños de: %s->%02d/%02d/%04d.\n",cump[i].nombre,cump[i].cumple.dia,cump[i].cumple.mes,cump[i].cumple.anio);
         } else {
            printf("el Santo de: %s.\n",cump[i].nombre);
         }
      }
   }
   if (hayCumples) {
      pausa();
   }
   return 0;
}


