/*
 1.- Escribir todas las funciones que aparecen a continuación con notación puntero.

Longitud: halla la longitud de la cadena.
Leer-palabra: lee una cadena sin blancos.
Leer-cadena: lee una cadena incluso blancos.
Imprimir-cadena: imprime una cadena.
Hallar-Subcad: Devuelve un puntero a una subcadena dentro de otra cadena
Suprimir-subcad: Suprime una subcadena dentro una cadena
Insertar: inserta una cadena en otra 
Concatenar: añade una cadena a otra.
Buscar: busca una cadena en otra.
Buscar-car: busca un carácter en una cadena.
Sustituir: Sustituye todas las ocurrencias de una cadena dentro de otra, por otra cadena.
Sustituir-car: sustituye un carácter de una cadena por otro.
*/
#include <stdio.h>

unsigned int longitud(char *);
void leerPalabra(char *);
void leerCadena(char *);
void imprimirCadena(char *);
char *hallarSubcadena(char *,char *);
char *suprimirSubcadena(char *,char *);
char *insertarCadena(char *,char *,int);
char *concatenar(char *,char *);
char *buscaChar(char *, char);
char *sustituir(char *, char *, char *);
char *sustituirChar(char *, char, char);
int main(int argc, char** argv) {
	char cad[80]="hola pepe";
	int l;
	char *paux;

	l=longitud(cad);
	fflush(stdin);
	leerPalabra(cad);
	fflush(stdin);
	leerCadena(cad);
   strcpy(cad,"hola pepe");
	paux=hallarSubcadena("hola pepe","epe");
	paux=hallarSubcadena("hola pepe","epo");
	paux=hallarSubcadena("hola pepe","pep");
	paux=hallarSubcadena("hola pepe","");
	paux=suprimirSubcadena(cad,"pep");
	paux=suprimirSubcadena(cad,"ola");
	paux=suprimirSubcadena(cad,"epe");
   paux=insertarCadena(cad,", ¿cómo estás?",strlen(cad));
   paux=suprimirSubcadena(cad,", ¿cómo estás?");
   paux=insertarCadena(cad," vecino",4);
   paux=suprimirSubcadena(cad," vecino");
   paux=suprimirSubcadena(cad,"hola");
   paux=insertarCadena(cad,"hello ",0);
   paux=concatenar(cad,", ¿Cómo estás?");
   paux=buscaChar(cad,'C');
   paux=concatenar(cad,", ¿Cómo estás?");
   paux=concatenar(cad,". Espero que bien.");
   paux=concatenar(cad,", ¿Cómo estás?");
   paux=sustituir(cad,", ¿Cómo estás?","");
   paux=sustituirChar(cad,'e','U');
}


//Longitud: halla la longitud de la cadena.
unsigned int longitud(char *s) {
   char *p=s;
   while(*p++);
   return (unsigned) (p-s-1);
}

//Leer-palabra: lee una cadena sin blancos.
void leerPalabra(char *s) {
   char c;

   while((c=getchar())!=' ' && c!='\n') {
      *s++=c;
   }
   *s='\0';
}

//Leer-cadena: lee una cadena incluso blancos.
void leerCadena(char *s) {
   char c;

   while((c=getchar())!='\n') {
      *s++=c;
   }
   *s=0;
}

//Imprimir-cadena: imprime una cadena.
void imprimirCadena(char *s) {
   while(*s) {
      printf("%c",*s++);
   }
}

//Hallar-Subcad: Devuelve un puntero a una subcadena dentro de otra cadena
char * hallarSubcadena(char *ppal, char *sec) {
   char *p_ppal, *p_sec;
   while(*ppal) {
      if (*ppal==*sec) {
         p_ppal=ppal+1;
         p_sec=sec+1;
         while(*p_ppal && *p_sec && *p_ppal==*p_sec) {
            p_ppal++;
            p_sec++;
         }
         if (*p_sec=='\0') {
            return ppal;
         }
      }
      ppal++;
   }
   return NULL;
}

//Suprimir-subcad: Suprime una subcadena dentro una cadena
char * suprimirSubcadena(char *ppal, char *sec) {
   char *res,*pos;
   int l=longitud(sec);
   res=pos=hallarSubcadena(ppal,sec);
   if (pos==NULL) {
      return NULL;
   }
   while(*(pos+l)!='\0') {
      *pos=*(pos+l);
      pos++;
   }
   *pos='\0';
   return res;
}

//Insertar: inserta una cadena en otra 
char *insertarCadena(char *ppal, char *sec, int pos) {
   int lppal=longitud(ppal),
       lsec=longitud(sec);
   char *p_ppal;
   if (0<=pos && pos<=lppal) { // si la posición es legal
      for(p_ppal=ppal+lppal; p_ppal>=ppal+pos; p_ppal--) { //hacemos hueco
         *(p_ppal+lsec)=*p_ppal;
      }
      p_ppal=ppal+pos;
      while(*sec) {
         *p_ppal++=*sec++;
      } // copiamos la cadena secundaria en la posición
      return (ppal+pos); //devolvemos la posición donde se ha insertado
   }
   return NULL;
}
//Concatenar: añade una cadena a otra.
char *concatenar(char *ppal, char *sec) {
   return insertarCadena(ppal,sec,longitud(ppal));
}
//Buscar-car: busca un carácter en una cadena.
char *buscaChar(char *cad, char c) {
   char busca[2];
   busca[0]=c; busca[1]='\0'; //construimos una cadena de un carácter y
   return hallarSubcadena(cad,busca); //utilizamos la función ya construída
}
//Sustituir: Sustituye todas las ocurrencias de una cadena dentro de otra, por otra cadena.
char *sustituir(char *ppal, char *sustituida, char *sustituyente) {
   char *pos, *res=NULL;
   while((pos=suprimirSubcadena(ppal,sustituida))!=NULL) {
      insertarCadena(ppal,sustituyente,(int)(pos-ppal));
      res=pos;
   }
   return res; //devolvemos la última poisición donde se ha sustituido
}
//Sustituir-car: sustituye un carácter de una cadena por otro.
char *sustituirChar(char *ppal, char sustituido, char sustituto) {
   char cadSustituida[2];
   char cadSustituta[2];
   cadSustituida[0]=sustituido; cadSustituida[1]='\0';
   cadSustituta[0]=sustituto; cadSustituta[1]='\0';
   return sustituir(ppal,cadSustituida,cadSustituta);
}
