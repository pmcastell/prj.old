#ifndef __PILA_GENERICA__
#define __PILA_GENERICA__

#include "listaGenerica.h"

typedef LISTA_GENERICA PILA_GENERICA;


/* Interfaz ""Virtual"":
(todas las operaciones salvo pilaPop son macro redifiniendo operaciones del 
T.A.D. LISTA_GENERICA)
---------------------------------------------------------------------
p->PILA_GENERICA, d->INFO, fCompInfo->puntero a func. comparación INFO
----------------------------------------------------------------------
PILA_GENERICA pilaCrear(); -> crear una pila vacía
void pilaDestruye(p); -> Destruy ela pila
void pilaPush(p,d); -> Inserta un elemento en la cima de la pila
tipo pilaPop(p,tipo); -> Devuelve y desapila el elemento de la cima de la pila
tipo pilaCima(p,tipo);  -> Devuelve el elemento de la cima de la pila
int pilaVacia(p); -> Devuelve Cierto si pila vacía
char * pilaToString(p,char * (*fInfoToString)(const void *));->converite pila a cadena
*/






#define pilaCrear listaCrear
#define pilaDestruye listaDestruye
#define pilaPush listaInsertaPrincipio
void * _pilaPop(PILA_GENERICA *p,int tam);
#define pilaPop(p,tipo) (*((tipo *)_pilaPop(&(p),sizeof(tipo))))
#define pilaCima(p,tipo) listaElemento(p,listaPrimero(p),tipo)
#define pilaVacia listaVacia
#define pilaToString listaToString

#endif


