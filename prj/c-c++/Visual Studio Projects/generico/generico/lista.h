#ifndef __LISTA__
#define __LISTA__

#include "info.h"

typedef struct nodo  {
   INFO info;
   struct nodo * sig;
} NODO ;
typedef NODO * LISTA;
typedef NODO * POS;

LISTA listaCrear();
void listaDestruye(LISTA *l);
void listaInserta(LISTA *l, POS p, INFO dato);
void listaInsertaPrincipio(LISTA *l, INFO dato);
void listaInsertaFinal(LISTA *l, INFO dato);
void listaInsertaOrdenado(LISTA *l, INFO dato);
void listaBorra(LISTA *l, POS p);
INFO listaElemento(LISTA l, POS p);
int listaVacia(LISTA l);
POS listaPrimero(LISTA l);
POS listaUltimo(LISTA l);
char * listaToString(LISTA l);

#endif
