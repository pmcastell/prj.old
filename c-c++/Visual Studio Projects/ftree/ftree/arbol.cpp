#include <stdio.h>
#include <stdlib.h>

//#include "arbol.h"


#define error(mensa) fprintf(stderr,mensa);exit(-1);

void _arbolInicia(ARBOL *pa) {
    *pa=NULL;
}

int arbolVacio(ARBOL a) {
    return a==NULL;
}

void _arbolAnula(ARBOL *pa) {
    while(*pa!=NULL) {
        _arbolAnula(&((*pa)->hi));
        _arbolAnula(&((*pa)->hd));
        free(*pa);
        *pa=NULL;
    }
}

ARBOL arbolHi(ARBOL a) {
    if (a!=NULL) {
        return a->hi;
    }
    return NULL;
}

ARBOL arbolHd(ARBOL a) {
    if (a!=NULL) {
        return a->hd;
    }
    return NULL;
}


tipoBasico arbolContenido(ARBOL a) {
    return a->info;
}

ARBOL arbolLocaliza(tipoBasico x, ARBOL a) {
    ARBOL aAux;

    if (a!=NULL) {
        if (tipoBasicoCompara(x,a->info)==0) {
            return a;
        }
        aAux=arbolLocaliza(x,a->hi);
        if (aAux!=NULL) {
            return aAux;
        }
        aAux=arbolLocaliza(x,a->hd);
        if (aAux!=NULL) {
            return aAux;
        }
    }
    return NULL;
}

void _arbolPonInfo(tipoBasico x, ARBOL *pa) {
    if (*pa==NULL) {
        *pa=(ARBOL) malloc(sizeof(NODO));
        (*pa)->hi=(*pa)->hd=NULL;
    }
    (*pa)->info=x;
}

void arbolPonHi(ARBOL hi, ARBOL a) {
    if (a==NULL) {
        error("arbolPonHi. El árbol está vacío");
    }
    _arbolAnula(&(a->hi));
    a->hi=hi;
}

void arbolPonHd(ARBOL hd, ARBOL a) {
    if (a==NULL) {
        error("arbolPonHd. El árbol está vacío");
    }
    _arbolAnula(&(a->hd));
    a->hd=hd;
}

void arbolPonInfoHi(tipoBasico x, ARBOL a) {
    if (a==NULL) {
        error("arbolPonInfoHi. El árbol está vacío");
    }
    if (a->hi==NULL) {
        a->hi=(NODO *) malloc(sizeof(NODO));
    }
    a->hi->info=x;
    a->hi->hi=a->hi->hd=NULL;
}

void arbolPonInfoHd(tipoBasico x, ARBOL a) {
    if (a==NULL) {
        error("arbolPonInfoHd. El árbol está vacío");
    }
    if (a->hd==NULL) {
        a->hd=(NODO *) malloc(sizeof(NODO));
    }
    a->hd->info=x;
    a->hd->hi=a->hd->hd=NULL;
}