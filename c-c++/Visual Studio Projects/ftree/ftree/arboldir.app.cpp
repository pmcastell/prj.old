#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <direct.h> //esto no es ANSI, aquí está _chdir
#include <io.h> //esto no es ANSI, aquí está _findnext, firts
#include <conio.h> // esto tampoco, ¡¡Este windows del demonio!!!
//#include <unistd.h>
#include "arbolCad.h"
#include "listaGenerica.h"

#pragma warning(disable:4996)

void sintaxis(char *prg) {
    fprintf(stderr,"\nSintaxis: %s <path>",prg);
    fprintf(stderr,"\n<path> ruta de directorios");
    fprintf(stderr,"\nEjemplo %s c:\\windows",prg);
    exit(-1);
}

char * siguienteDir(void) {
    static intptr_t primero=-1;
    struct _finddata_t ft;

    do {
        if (primero<0) { // primera vez que buscamos en este directorio
            primero=_findfirst("*.*",&ft);
            if (primero<0) { // no hay nad dentro
                return NULL;
            }
        } else {
            if (_findnext(primero,&ft)!=0) { // no hay más ficheros en este directorio
                primero=-1;
                return NULL;
            }
        }
    }while((ft.attrib & _A_SUBDIR)!=_A_SUBDIR ||
        strcmp(".",ft.name)==0 || strcmp("..",ft.name)==0);
    return strcpy((char *) malloc(strlen(ft.name)+1),ft.name);
}


void directorios(ARBOLCAD a) {
    char *nextDir;
    ARBOLCAD aAux;

    if (chdir(a->info)!=0) {
        fprintf(stderr,"\nError. No se ha podido cambiar a %s",a->info);
        return;
    }
    nextDir=siguienteDir();
    if (nextDir!=NULL) {
        arbolCadPonInfoHi(nextDir,a);
        aAux=arbolCadHi(a);
        do {
            nextDir=siguienteDir();
            if (nextDir==NULL) {
                break;
            }
            arbolCadPonInfoHd(nextDir,aAux);
            aAux=arbolCadHd(aAux);
        }while(true);
        aAux=arbolCadHi(a);
        while(aAux!=NULL) {
            directorios(aAux);
            chdir("..");
            aAux=arbolCadHd(aAux);
        }
    }
}
void printRayas(LISTA_GENERICA l) {
    POS_GENERICA p, psig;
    ARBOLCAD aux;

    if (l!=NULL) {
        p=listaPrimero(l);
        p=listaSig(l,p);
        if (p!=NULL) {
            printf("\n");
            while(p!=listaFin(l)) {
                psig=listaSig(l,p);
                aux=listaElemento(l,p,ARBOLCAD);
                if (arbolCadHd(aux)!=NULL) {
                    if (psig==NULL) {
                        //printf("+---");
                       printf("%c%c%c%c",195,196,196,196);
                    } else {
                        //printf("|   ");
                        printf("%c   ",179);
                    }
                } else {
                    if (psig==NULL) {
                        //printf("|---");╣║╗╝¢¥┐à└┴
                        printf("%c%c%c%c",192,196,196,196);
                    } else {
                        printf("    ");
                    }
                }
                p=listaSig(l,p);
            }
        }
    }
}

void miprintf(char *s) {
//181:Á 144:É 214:Í 224:Ó 233:Ú 160:á 130:é 161:í  162:ó  163:ú  164:ñ 165:Ñ
   while(*s) {
      if (*s>0 && *s<=127) {
         printf("%c",*s);
      } else if (*s=='á') {
         printf("%c",160);
      } else if (*s=='é') {
         printf("%c",130);
      } else if (*s=='í') {
         printf("%c",161); 
      } else if (*s=='ó') {
         printf("%c",162);
      } else if (*s=='ú') {
         printf("%c",163);
      } else if (*s=='Á') {
         printf("%c",181);
      } else if (*s=='É') {
         printf("%c",144);
      } else if (*s=='Í') {
         printf("%c",214);
      } else if (*s=='Ó') {
         printf("%c",224);
      } else if (*s=='Ú') {
         printf("%c",233); 
      } else if (*s=='ñ') {
         printf("%c",164);
      } else if (*s=='Ñ') {
         printf("%c",165);
      } else {
         printf("%c",*s);
      }
      s++;
   }
}

void printArbol(ARBOLCAD a) {
    static LISTA_GENERICA listaPilaDir=listaCrear();

    if (a!=NULL) {
        listaInsertaFinal(listaPilaDir,a);
        printRayas(listaPilaDir);
        //printf("%-s",arbolCadContenido(a));
        miprintf(arbolCadContenido(a));
        if (arbolCadHi(a)!=NULL) {
            printArbol(arbolCadHi(a));
        }
        listaBorra(listaPilaDir,listaUltimo(listaPilaDir));
        if (arbolCadHd(a)!=NULL) {
            printArbol(arbolCadHd(a));
        }

    }
}

void main(int argc, char **argv) {
    ARBOLCAD a;

    char *path;


    if (argc!=2) {
        sintaxis(*argv);
    }
    //setlocale(LC_ALL,"spanish_spain");
    path=argv[1];
    arbolCadInicia(a);
    arbolCadPonInfo(path,a);
    directorios(a);
    printf("\n");
    printArbol(a);

}