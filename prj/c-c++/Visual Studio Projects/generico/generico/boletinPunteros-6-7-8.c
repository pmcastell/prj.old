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
    capital("Espa�a",cap);
    capital("Francia",cap);
    capital("Reino Unido",cap);
    capital("Alemania",cap);
    capital("Grecia",cap);
    capital("Italia",cap);
    capital("Holanda",cap);
    if (esPalindromo("dabale arroz a la zorra el abad")) {
       printf("Es Pal�ndromo");
    } else {
       printf("No es Pal�ndromo");
    }
    if (esPalindromo2("dabale arroz  a la zorra el abad")) {
       printf("Es Pal�ndromo");
    } else {
       printf("No es Pal�ndromo");
    }
    if (esPalindromo2("a b  a   c   a b   a")) {
       printf("Es Pal�ndromo");
    } else {
       printf("No es Pal�ndromo");
    }
    leerOrdenarFichero("D:\\Mis Documentos\\Visual Studio 2005\\Projects\\generico\\debug\\ejercicio8.txt");


}
/*
6.- Dise�ar un funci�n a la que se pase como 
par�metro el nombre de un pa�s europeo y devuelva su capital.
*/
void capital(char *pais, char *capital) {
    static char *paises[]={"Espa�a","Francia","Alemania","Reino Unido",
        "Dinamarca","B�lgica","Italia","Grecia",NULL};
    static char *capitales[]={"Madrid","Par�s","Berl�n","Londres",
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
7.- Realizar una funci�n para comprobar si una cadena que se env�a como par�metro es o no 
un PAL�NDROMO (se lee igual de izquierda a derecha que de derecha a izquierda sin tener en 
cuenta los espacios en blanco). 
*/
//versi�n 1
int esPalindromo(char *s) {
   char *auxPrincipio, *auxFin, *aux;
   auxPrincipio=auxFin=aux=(char *) malloc(strlen(s)+1);
   if (auxFin==NULL) {
      printf("Funci�n: esPalindromo. Error al pedir memoria.\n");
      return -1;
   }
   while(*s!='\0') {
      if (*s!=' ') {
         *auxFin=*s;
         auxFin++;
      }
      s++;
   } //copiamos la cadena s en aux sin espacios, auxFin queda apuntando al '\0' 
   auxFin--; // ahora al �ltimo car�cter
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
//versi�n 2 sin utilizar una cadena auxiliar
int esPalindromo2(char *s) {
   char *pFin=s;
   while(*pFin) {
      pFin++;
   } //pFin queda apuntando al '\0'
   pFin--; //ahora apunta al �ltimo car�cter de la cadena
   while(1) { //mientras verdadero equivale a un bucle infinito
      while(*s==' ' && s<pFin) { //saltar espacios por el principio
         s++;
      }
      while(*pFin==' ' && s<pFin) { //saltar espacios por el final
         pFin--;
      }
      if (s>=pFin || *s!=*pFin) { 
         break; // �no es un bucle infinito cuando se d� esta condici�n se sale del mismo!
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
8.- Ordenar un fichero de texto utilizando un array de punteros a char. El fichero tendr�
como m�ximo 512 l�neas.
NOTA: Utilizar el qsort de stdlib.h. Utilizar una cadena de caracteres auxiliar para ir 
leyendo del fichero. Una vez le�da �sta se puede averiguar su tama�o (strlen) y reservar 
memoria en el elemento actual del array mediante malloc.
*/
int leerOrdenarFichero(char *nombreFichero) {
   int i,j;
   FILE *fich=fopen(nombreFichero,"rt");
   char *tabla[512], 
      aux[4096]; // 4096 es un n�mero de caracteres bastante grande para una l�nea de texto

   leerCadena(aux,4096,fich); //lee una cadena, como m�ximo 4096 caracteres
   i=0;
   while(!feof(fich)) {
      tabla[i]=(char *) malloc(strlen(aux)+1);
      if (tabla[i]==NULL) {
         printf("Funci�n leerOrdenarFichero. Error pidiendo memoria.");
         return -1;
      }
      strcpy(tabla[i],aux);
      i++;
      leerCadena(aux,4096,fich);
   } //leemos el fichero, i tiene el n�mero de elementos de la tabla
   qsort(tabla,i,sizeof(char *),&comp); //lo ordenamos e
   for(j=0;j<i; j++) { //imprimimos el fichero ordenado
      printf("%s\n",tabla[j]);
      free(tabla[j]); // liberamos la memoria utilizada en la tabla
   }
   return 0;
}

void leerCadena(char *s,int tam, FILE *f) {
   fgets(s,tam,f); //fgets deja los caracteres '\n' al final de las l�neas
   if (s[strlen(s)-1]=='\n') { 
      s[strlen(s)-1]='\0'; // con esto quitamos el caracter '\n'
   }
}

int comp(const void *e1, const void *e2) {
   char **se1=(char**) e1, **se2=(char **) e2;
   return strcmp(*se1,*se2);
}

