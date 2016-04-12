#include <stdlib.h>
static int a,b,c;

int main(int argc, char** argv) {
	int a[5]={1,2,3,4,5};
	int  * const c=(int *) malloc(5*sizeof(int));
	int matriz[][3]={{1,2,3},{4,5,6}};
	char *p="hola";
	int fil=2,col=3;
	int (*m2)[3];
	int **m3;
		char *cadenas[4], *palabras[]={"Qué","mala","es"};
	char alumnos[][10]={"Pedro","Antonio"};
	
	int i,j; 

	m2=(int (*)[3]) malloc(fil*col*sizeof(int));
	for(i=0; i<fil; i++) {
		for(j=0; j<col; j++) {
			m2[i][j]=matriz[i][j];
		}
	}
	m3=m2;
	p="hola";
	p="adios";




	for(i=0; i<5; i++) {
		*(c+i)=*(a+i);
	}

}