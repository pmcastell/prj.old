#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void rotarDerecha(char *v,int p,int f) {
	char vf; int j;
	vf=v[f];
	for(j=f;j>p;j--) {
		v[j]=v[j-1];
	}
	v[p]=vf;
}
void rotarIzquierda(char *v,int p,int f) {
	char vp; int j;
	vp=v[p];
	for(j=p;j<f;j++) {
		v[j]=v[j+1];
	}
	v[f]=vp;
}
void permutar(char *v,int i, int n) {
	int j;
	if (i==n) {
		printf("%s\n",v);
	} else {
		permutar(v,i+1,n);
		for(j=i+1;j<n;j++) {
			rotarDerecha(v,i,j);
			permutar(v,i+1,n);
			rotarIzquierda(v,i,j); /*lo dejamos como estaba*/
		}
	}
}
/*
 * void intercambia(char *a, char *b) {
	char temp=*a;
	*a=*b;
	*b=temp;
}
void permutaciones(char *v,int n) {
	int i,l;
	char buf[25];

	if (n==1) {
		printf("%s\n",v);
	} else {
		l=strlen(v);
		strcpy(buf,v);
		permutaciones(v,n-1);
		for(i=0;i<n-1;i++) {
			intercambia(&v[i],&v[n-1]);
			permutaciones(v,n-1);
			//intercambia(&v[n-1],&v[i]);
		}
		strcpy(v,buf);

	}
}
*/
int main3(int argc, char**argv) {
	if (argc<2) {
		printf("\nUso %s <cadena-de-caracteres-a-permutar\n",argv[0]);
		exit(-1);
	}
	permutar(argv[1],0,strlen(argv[1]));
	return 0;
}
