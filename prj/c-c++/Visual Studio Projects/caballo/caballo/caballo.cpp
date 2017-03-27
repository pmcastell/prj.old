#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#using <mscorlib.dll>


#pragma warning(disable:4996)

using namespace System;

#define MAX_N 20
#define MAX_NUM_ELEMENTOS 3*MAX_N*MAX_N
#define TRUE  1
#define FALSE 0
typedef int casilla;



void gotoxy(int x,int y) {
	Console::SetCursorPosition(x,y);
}

void clrscr() {
	Console::Clear();
}


typedef int tipoElementoPila;
typedef struct {
    tipoElementoPila pila[MAX_NUM_ELEMENTOS];
    int actual;
} pilaEntero;

void inicializaPila(pilaEntero *p) {
    p->actual=0;
}

int numElementos(pilaEntero *p) {
    return p->actual;
}

int push(pilaEntero *p, tipoElementoPila elemento) {
    if (p->actual<MAX_NUM_ELEMENTOS) {
        p->pila[p->actual++]=elemento;
        return p->actual-1;
    }
    return -1;
}

tipoElementoPila pop(pilaEntero *p) {
    if (p->actual>0) {
        return p->pila[--(p->actual)];
    }
    return -1; //esto no es muy apropiado
}

#define tablero(x,y) tablero[(x)*n+(y)]
const int dx[]={-1,1,2,2,1,-1,-2,-2},
          dy[]={-2,-2,-1,1,2,2,1,-1};

void imprimeMov(int x, int y, int mov) {
    gotoxy(x*4+2,(y+1)*2);
    printf("%3d",mov);
}
void borraMov(int x, int y) {
    gotoxy(x*4+2,(y+1)*2);
    printf("   ");
}



int GNA(int bajo, int alto) {
	static int primeraVez=TRUE;
	if (primeraVez) {
		srand( (unsigned)time( NULL ) ); 
		primeraVez=FALSE; //esto sólo se ejecuta una vez
	}
	return (int) (bajo+(alto-bajo)*(float)rand()/RAND_MAX);
	//rand() genera un número aleatorio entero entre 0 y RAND_MAX
}
int nuevaJugada(casilla *tablero, int x, int y, int n) {
    int mov, i, k, u,v;
    unsigned int numMovTotal=0;
    pilaEntero p;

    inicializaPila(&p);
    for(mov=2; mov<=n*n; ) {
        for(i=0,k=GNA(0,7); i<8; i++,k=(k+1)%8) {
            u=x+dx[k]; v=y+dy[k];
            if (0<=u && u<n && 0<=v && v<n && !tablero(u,v)) {
                tablero(u,v)=mov; imprimeMov(u,v,mov);
                mov++; numMovTotal++;
                push(&p,x); push(&p,y); push(&p,i);
                x=u; y=v;
                break;
            }
            while(i>=7 && mov>2) {
                borraMov(x,y);
                tablero(x,y)=0;
                i=pop(&p); y=pop(&p); x=pop(&p);
                mov--; numMovTotal++;
            }

        }
        gotoxy(1,24); printf("%d",numMovTotal);
    }
    return mov>n*n;
}
int leerNumero(char *mensaje, int bajo, int alto) {
	int n=0; 

	if (alto<bajo) {
		printf("Error alto es menor que bajo. ");
		return -1; // debemos devolver un valor
	}
	do {
		printf(mensaje);
		fflush(stdin);
		scanf("%d",&n);
	} while(n<bajo || n>alto);
	return n;
}
void inicializa(casilla *tablero, int l) {
    int i;
    for(i=0; i<l*l; i++) {
        tablero[i]=0;
    }
}
void dibujaTablero(int n) {
    int i,j;

    clrscr();
    for(i=1; i<=n+1;i++) {
        gotoxy(1,i*2-1);
        for(j=1;j<=n*4+1;j++) {
            printf("-");
        }
    }
    for(i=0; i<n+1; i++) {
        for(j=1;j<=2*n+1;j++) {
            gotoxy(i*4+1,j);
            printf("|");
        }
    }
}
char confirmar() {
	char resp;

	do {
		printf("\n¿Desea continuar (s/n)? ");
		fflush(stdin);
		scanf("%c",&resp);
	} while (resp!='s' && resp!='S' && resp!='n' && resp!='N');
	if ('a'<=resp && resp<='z') { // si está en minúsculas
		resp=resp+('A'-'a'); // convertir a mayúsculas;
	}
	return resp;
}

int main(int argc, char* argv[]) {
    casilla * tablero;
    int x,y,n;
    char resp=' ';
    do {
        n=leerNumero("Introduce tamaño tablero (3/20): ",3,20);
        tablero=(casilla *) malloc(sizeof(casilla)*n*n);
        inicializa(tablero,n);
        x=leerNumero("Introduce coordenada x: ",1,n);
        y=leerNumero("Introduce coordenada y: ",1,n);
        dibujaTablero(n);
        tablero(x-1,y-1)=1;
        imprimeMov(x-1,y-1,1);
        if (nuevaJugada(tablero,x-1,y-1,n)) {
            gotoxy(19,24);
            printf("Hubo éxito");
        } else {
            gotoxy(19,24);
            printf("Lo siento no hubo éxito");
        }
        free(tablero);
    } while(confirmar()=='S');



    return 0;
}
