#include <stdlib.h>
static int a,b,c;
int *matrizUniInt(int);
int **matrizBidiInt(int,int);
int ***matrizTridiInt(int,int,int);
void *matrizUniGen(int,int);
void **matrizBidiGen(int,int,int);
void ***matrizTridiGen(int,int,int,int);

int main(int argc, char** argv) {
	int a[5]={1,2,3,4,5};
	int  * const c=(int *) malloc(5*sizeof(int));
	const int a1=5;
	int const a2=5;
	const int * a3;
	int const * a4;
	int * const a5;
	int ***prueba=matrizTridiInt(2,3,5);
	int prueba2[2][3][5]={
		{
			{1,2,3,4,5},
			{6,7,8,9,10},
			{11,12,13,14,15}
		},
		{
			{1,2,3,4,5},
			{6,7,8,9,10},
			{11,12,13,14,15}
		}
	};


	int i,j,k;
	for(i=0; i<2; i++) {
		for(j=0; j<3; j++) {
			for(k=0; k<5; k++) {
				prueba[i][j][k]=prueba2[i][j][k];
			}
		}
	}
	free(prueba);
	prueba=matrizTridiGen(2,3,5,sizeof(int));
	for(i=0; i<2; i++) {
		for(j=0; j<3; j++) {
			for(k=0; k<5; k++) {
				prueba[i][j][k]=prueba2[i][j][k];
			}
		}
	}
}

int *matrizUniInt(int cols) {
	return (int *) malloc(cols*sizeof(int));
}

int **matrizBidiInt(int filas, int cols) {
	int **resultado, i;

	resultado=(int **) malloc(filas*sizeof(int *));
	if (resultado==NULL) {
		return resultado;
	}
	for(i=0; i<filas; i++) {
		resultado[i]=matrizUniInt(cols);
	}
	return resultado;
}

int ***matrizTridiInt(int planos, int filas, int cols) {
	int ***resultado;
	int i;
	resultado=(int ***) malloc(planos*sizeof(int **));
	if (resultado==NULL) {
		return resultado;
	}
	for(i=0; i<planos; i++) {
		resultado[i]=matrizBidiInt(filas,cols);
	}
	return resultado;
}

void *matrizUniGen(int cols, int tamElem) {
	return malloc(cols*tamElem);
}

void **matrizBidiGen(int filas, int cols, int tamElem) {
	void **resultado;
	int i;

	resultado=malloc(filas*sizeof(void *));
	if (resultado==NULL) {
		return NULL;
	}
	for(i=0; i<filas; i++) {
		resultado[i]=matrizUniGen(cols,tamElem);
	}
	return resultado;
}
void ***matrizTridiGen(int planos, int filas, int cols, int tamElem) {
	void ***resultado;
	int i;

	resultado=malloc(planos*sizeof(void **));
	if (resultado==NULL) {
		return NULL;
	}
	for(i=0; i<planos; i++) {
		resultado[i]=matrizBidiGen(filas,cols,tamElem);
	}
	return resultado;
}

double determinante(double **m,int n) {
	int i,j;

	for(i=0; i<n; i++) {
		for(j=0; j<n; j++) {
		}
	}
}