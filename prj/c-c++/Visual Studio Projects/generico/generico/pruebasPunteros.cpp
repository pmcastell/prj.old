#include <stdlib.h>
static int a,b,c;

int main(int argc, char** argv) {
	int a[5]={1,2,3,4,5};
	int  * const c=(int *) malloc(5*sizeof(int));
	int matriz[][3]={{1,2,3},{4,5,6}};
	char *p="hola";
	int (*m2)[];
	int fil=2,col=3;
	m2=(int (*)[]) malloc(fil*col*sizeof(int));
	for(int i=0; i<fil; i++) {
		for(int j=0; j<col; j++) {
			m2[i][j]=matriz[i][j];
		}
	}
	p="hola";
	p="adios";


	char *cadenas[4], *palabras[]={"Qué","mala","es"};
	char alumnos[][10]={"Pedro","Antonio"};

	for(int i=0; i<5; i++) {
		*(c+i)=*(a+i);
	}

}