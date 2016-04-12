#ifndef __LISTA_GENERICA__
#define __LISTA_GENERICA__


typedef struct nodo  {
   void * info;
   struct nodo * sig;
} NODO ;

typedef NODO * LISTA_GENERICA;
typedef NODO * POS_GENERICA;
#define POS_ERRONEA NULL


/* 
Interfaz 
--------
(algunas funciones en realidad no lo son, sino que son macros del preprocesador)
----------------------------------------------------------------------------------------
l->LISTA_GENERICA, p->POS_GENERICA, d->INFO, fCompInfo->puntero a func. comparación info
----------------------------------------------------------------------------------------
LISTA_GENERICA listaCrear(); -> Devuelve una lista nueva (vacía)
void listaDestruye(l); -> Destruye todos los nodos de una lista y la deja vacía
void listaInserta(l,p,d); -> Inserta un nuevo nodo con la info d en la posición p
void listaInsertaPrincipio(l,d); -> Inserta un nuevo nodo con la info d en la primera pos.
void listaInsertaFinal(l,d); -> Idem anterior pero al final
void listaInsertaOrden(l,d,fCompInfo); -> Inserta un nodo en orden ascendente según la función fCompInfo
void listaInsertaOrdenAsc(l,d,fCompInfo); -> Idem anterior
void listaInsertaOrdenDesc(l,d,fCompInfo); -> Inserta un nodo en orden descendente
void listaBorra(l,p); -> Borra el nodo de la posición p
tipo listaElemento(l,p,tipo); -> devuelve el campo info del nodo situado en la posición p
int listaVacia(LISTA_GENERICA l); -> devuelve cierto si la lista está vacía
POS_GENERICA listaPrimero(LISTA_GENERICA l); -> devuelve la posición del primer elemento
POS_GENERICA listaUltimo(LISTA_GENERICA l); -> Idem último elemento
POS_GENERICA listaFin(LISTA_GENERICA l); -> Devuelve la posición ficticia de fin de lista
POS_GENERICA listaSig(l,p); -> Devuelve la posición del nodo siguiente al de la posición p
POS_GENERICA listaAnt(LISTA_GENERICA l, POS_GENERICA p);-> Devuelve la posición anterior al nodo de la pos.p
int listaNumElementos(LISTA_GENERICA l); -> Devuelve el número de elementos de la lista
POS_GENERICA listaBuscaElemento(l,d,fCompInfo)-> Devuelve la posición del dato d dentro de l o POS_ERRONEA en caso contrario
char * listaToString(LISTA_GENERICA l,char * (*fInfoToString)(const void *));->convierte a cadena la lista
*/


#define listaCrear() NULL

void _listaDestruye(LISTA_GENERICA *l);
#define listaDestruye(l) _listaDestruye(&(l))

void _listaInserta(LISTA_GENERICA *l, POS_GENERICA p, void * dato, int tam);
#define listaInserta(l,p,d) _listaInserta(&(l),p,&(d),sizeof(d))

#define listaInsertaPrincipio(l,d) _listaInserta(&(l),l,&(d),sizeof(d))

#define listaInsertaFinal(l,d) _listaInserta(&(l),NULL,&(d),sizeof(d))

void _listaInsertaOrden(LISTA_GENERICA *l, void * dato, int tam, int (*fComparacion)(const void*,const void*));
#define listaInsertaOrden(l,d,f) _listaInsertaOrden(&(l),&(d),sizeof(d),f)
#define listaInsertaOrdenAsc listaInsertaOrden

void _listaInsertaOrdenDesc(LISTA_GENERICA *l, void * dato, int tam, int (*fComparacion)(const void*,const void*));
#define listaInsertaOrdenDesc(l,d,f) _listaInsertaOrdenDesc(&(l),&(d),sizeof(d),f)

void _listaBorra(LISTA_GENERICA *l, POS_GENERICA p);
#define listaBorra(l,p) _listaBorra(&(l),p)

#define listaElemento(l,p,tipo)  (*((tipo *) (p)->info))


#define listaVacia(l) (l==NULL)

#define listaPrimero(l) (l)

POS_GENERICA listaUltimo(LISTA_GENERICA l);

#define listaFin(l) NULL

#define listaSig(l,p) ((p)->sig)

POS_GENERICA listaAnt(LISTA_GENERICA l, POS_GENERICA p);

int listaNumElementos(LISTA_GENERICA l);

POS_GENERICA _listaBuscaElemento(LISTA_GENERICA l, void *dato, int tam, int (*fComparacion)(const void*,const void *));
#define listaBuscaElemento(l,d,f) _listaBuscaElemento(l,&(d),sizeof(d),f)

char * listaToString(LISTA_GENERICA l,char * (*fInfoToString)(const void *));

#endif


