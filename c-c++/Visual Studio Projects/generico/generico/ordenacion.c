#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#pragma warning(disable:4996)

void burbuja(int [],int);
void seleccionDirecta(int [],int);
void insercionDirecta(int [],int);
void shell(int [],int);
void quickSort(int[],int,int);

void intercambiar(int*,int*);





int main(int argc, char *argv[]) {
	time_t t1,t2;
	int m[]= {9,6,3,8,1,2,5,1};


	t1=time(NULL);
	//burbuja(m,sizeof(m)/sizeof(int));
	//seleccionDirecta(m,sizeof(m)/sizeof(int));
	//insercionDirecta(m,sizeof(m)/sizeof(int));
	//shell(m,sizeof(m)/sizeof(int));
	quickSort(m,0,sizeof(m)/sizeof(int)-1);
	t2=time(NULL);
	printf("\nEl Algoritmo ha tardado en ejecutarse %ld segundos.",t2-t1);
}




void burbuja(int m[],int n) {
	int i,j;

	for(i=0; i<n-1; i++) {
		for(j=0; j<n-i-1; j++) {
			if (m[j]>m[j+1]) { // cambiar > por < si orden descendente
				intercambiar(&m[j],&m[j+1]);
			}
		}
	}
}

void seleccionDirecta(int m[], int n) {
	int i,j,min;

	for(i=0; i<n-1; i++) {
		min=i;
		for(j=i+1; j<n; j++) {
			if (m[j]<m[min]) {
				min=j;
			}
		}
		if (min!=i) {
			intercambiar(&m[i],&m[min]);
		}
	}
}

void insercionDirecta(int m[], int n) {
	int i,j;

	for(i=1; i<n; i++) {
		for(j=i; m[j]<m[j-1] && j>0; j--) {
			intercambiar(&m[j-1],&m[j]);
		}
	}
}

void shell(int m[],int n) {
	int salto,sw,i;

	for(salto=n/2; salto>=1; salto/=2) {
		do {
			sw=0;
			for(i=0; i<n-salto; i++) {
				if (m[i]>m[i+salto]) {
					intercambiar(&m[i],&m[i+salto]);
					sw=1;
				}
			}
		}while(sw==1);
	}
}


void quickSort(int m[],int p,int u) {
	int i,j;


	if (p<u) {
		i=p+1; j=u;
		while (i<j) {
			for(;i<j && m[i]<=m[p]; i++);
			for(;i<j && m[j]>m[p]; j--);
			intercambiar(&m[i],&m[j]);
		}
		if (m[p]<=m[i]) {
			i--;
		}
		intercambiar(&m[i],&m[p]);
		if (p<i-1) {
			quickSort(m,p,i-1);
		}
		if (i+1<u) {
			quickSort(m,i+1,u);
		}
	}
}



void intercambiar(int *n1, int *n2) {
	int aux;

	aux=*n1;
	*n1=*n2;
	*n2=aux;
}

char *toString(int m[],int n) {
	static char res[4096], aux[20];
	int i;

	res[0]='{';
	res[1]=0;
	for(i=0; i<n; i++) {
		itoa(m[i],aux,10);
		strcat(res,aux);
		if (i<n-1) {
			strcat(res,",");
		}
	}
	strcat(res,"}");

	return res;
}



int GNA(int alto, int bajo) {
	static int primeraVez=1;

	if (primeraVez) {
		srand((unsigned)time(NULL));
		primeraVez=0;
	}
	return (int) ((alto-bajo)*(float)rand()/RAND_MAX+bajo);
}
void llenar(int m[],int n) {
	int i;

	for(i=0; i<n; i++) {
		m[i]=GNA(1,n);
	}
}
void copiar(int dest[],int origen[],int n) {
	int i;

	for(i=0; i<n; i++) {
		dest[i]=origen[i];
	}
}

int ordenada(int m[],int n) {
	int i;

	for(i=1; i<n; i++) {
		if (m[i-1]>m[i]) {
			return 0;
		}
	}
	return 1;
}