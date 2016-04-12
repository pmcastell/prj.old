#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <direct.h> //esto no es ANSI, aquí está _chdir
#include <io.h> //esto no es ANSI, aquí está _findnext, firts
#include <conio.h> // esto tampoco, ¡¡Este windows del demonio!!!
#include "arbolCad.h"
#include "listaGenerica.h"

#pragma warning(disable:4996)

void sintaxis(char *prg) {
    fprintf(stderr,"\nSintaxis: %s <path>",prg);
    fprintf(stderr,"\n<path> ruta de directorios");
    fprintf(stderr,"\nEjemplo %s c:\\windows",prg);
    exit(-1);
}
char *siguienteDir() {
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
/*
void directorios(ARBOLCAD a) {
    char *nextDir;
    ARBOLCAD aAux;

    if (_chdir(a->info)!=0) {
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
            _chdir("..");
            aAux=arbolCadHd(aAux);
        }
    }
}
*/
int main(int argc, char** argv) {
   static LISTA_GENERICA l=listaCrear();
   POS_GENERICA pos=NULL;

   int i=5;
   listaInsertaFinal(l,i);
   listaInserta(l,pos=NULL,i);
}