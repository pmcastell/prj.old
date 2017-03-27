#ifndef __COLA_GENERICA__
#define __COLA_GENERICA__

#include "listaGenerica.h"

typedef LISTA_GENERICA COLA_GENERICA;

/* Interfaz ""Virtual"":
(Todas las operaciones salvo colaExtraer se han implementado mediante reutilización
de las operaciones del T.A.D. LISTA_GENERICA definiendo para ello macros del preprocesador)
------------------------------------------------------------------------------------------
c->COLA_GENERICA, d->info, fCompInfo->puntero a func. comparación info
---------------------------------------------------------------------------
COLA_GENERICA colaCrear();-> crear una cola vacía
void colaDestruye(p);-> Destruye todos los nodos de la cola
void colaInsertar(p,d);->Inserta la información en un nodo al final de la cola
tipo colaExtraer(p,tipo);->Devuelve y elimina el primer elemento de la cola
tipo colaFrente(p,tipo); -> Devuelve el primer elemento de la cola
int colaVacia(p); -> Verdadero si cola vacía
char * colaToString(p,char * (*fInfoToString)(const void *));->convierte cola a cadena
*/

#define colaCrear listaCrear
#define colaDestruye listaDestruye
#define colaInsertar listaInsertaFinal
void * _colaExtraer(COLA_GENERICA *c,int tam);
#define colaExtraer(c,tipo) *((tipo *)_colaExtraer(&(c),sizeof(tipo)))
#define colaFrente(c,tipo) listaElemento(c,listaPrimero(c),tipo)
#define colaVacia listaVacia
#define colaToString listaToString
#endif



