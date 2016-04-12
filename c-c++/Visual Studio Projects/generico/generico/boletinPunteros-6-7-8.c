#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#pragma warning(disable:4996)

void capital(char *, char *);
int esPalindromo(char *);
int leerOrdenarFichero(char *);
int comp(const void*, const void*);
void leerCadena(char *s,int tam, FILE *f);
int main(int argc, char **argv) {
    char cap[25];
    capital("España",cap);
    capital("Francia",cap);
    capital("Reino Unido",cap);
    capital("Alemania",cap);
    capital("Grecia",cap);
    capital("Italia",cap);
    capital("Holanda",cap);
    if (esPalindromo("dabale arroz a la zorra el abad")) {
       printf("Es Palíndromo");
    } else {
       printf("No es Palíndromo");
    }
    if (esPalindromo2("dabale arroz  a la zorra el abad")) {
       printf("Es Palíndromo");
    } else {
       printf("No es Palíndromo");
    }
    if (esPalindromo2("a b  a   c   a b   a")) {
       printf("Es Palíndromo");
    } else {
       printf("No es Palíndromo");
    }
    leerOrdenarFichero("D:\\Mis Documentos\\Visual Studio 2005\\Projects\\generico\\debug\\ejercicio8.txt");


}
/*
6.- Diseñar un función a la que se pase como 
parámetro el nombre de un país europeo y devuelva su capital.
*/
void capital(char *pais, char *capital) {
    static char *paises[]={"España","Francia","Alemania","Reino Unido",
        "Dinamarca","Bélgica","Italia","Grecia",NULL};
    static char *capitales[]={"Madrid","París","Berlín","Londres",
        "Copenhague","Bruselas","Roma","Atenas",NULL};
    char **paisActual=paises, **capitalActual=capitales;

    for(;*paisActual!=NULL && strcmp(*paisActual,pais)!=0; paisActual++,capitalActual++);
    if (*capitalActual!=NULL) {
       strcpy(capital,*capitalActual);
    } else {
       strcpy(capital,"");
    }
}
/*
7.- Realizar una función para comprobar si una cadena que se envía como parámetro es o no 
un PALÍNDROMO (se lee igual de izquierda a derecha que de derecha a izquierda sin tener en 
cuenta los espacios en blanco). 
*/
//versión 1
int esPalindromo(char *s) {
   char *auxPrincipio, *auxFin, *aux;
   auxPrincipio=auxFin=aux=(char *) malloc(strlen(s)+1);
   if (auxFin==NULL) {
      printf("Función: esPalindromo. Error al pedir memoria.\n");
      return -1;
   }
   while(*s!='\0') {
      if (*s!=' ') {
         *auxFin=*s;
         auxFin++;
      }
      s++;
   } //copiamos la cadena s en aux sin espacios, auxFin queda apuntando al '\0' 
   auxFin--; // ahora al último carácter
   while (auxPrincipio<auxFin && *auxPrincipio==*auxFin) {
      auxPrincipio++;
      auxFin--;
   }
   free(aux);
   if (auxPrincipio<auxFin) {
      return 0; //falso
   } else {
      return 1; //verdadero
   }
}
//versión 2 sin utilizar una cadena auxiliar
int esPalindromo2(char *s) {
   char *pFin=s;
   while(*pFin) {
      pFin++;
   } //pFin queda apuntando al '\0'
   pFin--; //ahora apunta al último carácter de la cadena
   while(1) { //mientras verdadero equivale a un bucle infinito
      while(*s==' ' && s<pFin) { //saltar espacios por el principio
         s++;
      }
      while(*pFin==' ' && s<pFin) { //saltar espacios por el final
         pFin--;
      }
      if (s>=pFin || *s!=*pFin) { 
         break; // ¡no es un bucle infinito cuando se dé esta condición se sale del mismo!
      } else {
         s++;
         pFin--;
      }
   } 
   if (s>=pFin) {
      return 1;
   } else {
      return 0;
   }
}

/*
8.- Ordenar un fichero de texto utilizando un array de punteros a char. El fichero tendrá
como máximo 512 líneas.
NOTA: Utilizar el qsort de stdlib.h. Utilizar una cadena de caracteres auxiliar para ir 
leyendo del fichero. Una vez leída ésta se puede averiguar su tamaño (strlen) y reservar 
memoria en el elemento actual del array mediante malloc.
*/
int leerOrdenarFichero(char *nombreFichero) {
   int i,j;
   FILE *fich=fopen(nombreFichero,"rt");
   char *tabla[512], 
      aux[4096]; // 4096 es un número de caracteres bastante grande para una línea de texto

   leerCadena(aux,4096,fich); //lee una cadena, como máximo 4096 caracteres
   i=0;
   while(!feof(fich)) {
      tabla[i]=(char *) malloc(strlen(aux)+1);
      if (tabla[i]==NULL) {
         printf("Función leerOrdenarFichero. Error pidiendo memoria.");
         return -1;
      }
      strcpy(tabla[i],aux);
      i++;
      leerCadena(aux,4096,fich);
   } //leemos el fichero, i tiene el número de elementos de la tabla
   qsort(tabla,i,sizeof(char *),&comp); //lo ordenamos e
   for(j=0;j<i; j++) { //imprimimos el fichero ordenado
      printf("%s\n",tabla[j]);
      free(tabla[j]); // liberamos la memoria utilizada en la tabla
   }
   return 0;
}

void leerCadena(char *s,int tam, FILE *f) {
   fgets(s,tam,f); //fgets deja los caracteres '\n' al final de las líneas
   if (s[strlen(s)-1]=='\n') { 
      s[strlen(s)-1]='\0'; // con esto quitamos el caracter '\n'
   }
}

int comp(const void *e1, const void *e2) {
   char **se1=(char**) e1, **se2=(char **) e2;
   return strcmp(*se1,*se2);
}

