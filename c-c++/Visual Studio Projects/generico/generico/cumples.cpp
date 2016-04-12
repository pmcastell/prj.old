#include <stdio.h>
#include <time.h>
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

int main(int argc, char**argv) {
   CUMPLES cump[]={
      {"PAPA",{12,11,1922},'C'},
      {"MAMA",{14,1,1937},'C'},
      {"PAQUI",{1,11,1958},'C'},
      {"ANTONIO",{22,3,1960},'C'},
      {"JUAN",{6,6,1963},'C'},
      {"FRANC",{6,8,1965},'C'},
      {"MARI",{21,10,1969},'C'},
      {"JORGE",{30,9,1975},'C'},

      {"RAFA",{21,9,1987},'C'},
      {"ALBERTO",{9,8,1989},'C'},
      {"DAVID",{13,5,1990},'C'},
      {"ANTOÑIN",{26,4,1994},'C'},
      {"LAURA",{20,4,1996},'C'},
      

      {"ESTHER",{20,2,1965},'C'},
      {"CRISTINA",{7,3,1985},'C'},
      
      {"PAPA, ANTONIO, CHICO RICO",{13,6,2006},'S'},
      {"MAMA, MARI",{12,9,2006},'S'},
      {"PAQUI",{4,10,2006},'S'},
      {"JUAN",{24,6,2006},'S'},
      {"FRANC",{3,12,2006},'S'},
      {"JORGE",{23,4,2006},'S'},

      {"RAFA",{24,10,2006},'S'},

      {"DIA DEL PADRE",{19,3,2007},'S'},
      {"DIA DE LA MADRE",{1,5,2006},'S'},

      {"ESTHER",{1,7,2006},'S'},
      {"CRISTINA",{24,7,2006},'S'},

      {"JUAN JOSÉ",{5,7,1958},'C'},
      {"MARÍA",{8,9,2006},'S'},
      {"FÁTIMA",{17,12,2006},'C'},
      {"JUAN JOSÉ",{19,3,2007},'S'},

      {"RENOVACIÓN CERTIFICADO FNMT",{25,4,2006},'C'},
      
   };
   FECHA actual=fechaActual();
   int hayCumples=0;
   for(int i=0; i<sizeof(cump)/sizeof(CUMPLES); i++) {
      if (cump[i].cumple.dia==actual.dia && cump[i].cumple.mes==actual.mes) {
         hayCumples=1;
         if (cump[i].tipo=='C') {
            printf("\nHoy es el Cumpleaños de: %s->%02d/%02d/%04d. Cumple %d años.",cump[i].nombre,cump[i].cumple.dia,cump[i].cumple.mes,cump[i].cumple.anio,actual.anio-cump[i].cumple.anio);
         } else {
            printf("\nHoy es el Santo de: %s.",cump[i].nombre);
         }
      }
   }
   if (hayCumples) {
      printf("\nPulsa una tecla para terminar...");
      getchar();
   }
}

