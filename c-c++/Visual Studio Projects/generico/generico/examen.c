#include <stdio.h>
int f(int,int,int);
int fr(int,int,int);

int main(int argc, char *argv[]) {
	int cont;

	for(cont=1; cont<=10; cont++) {
		printf("f(%d)=%d.\n",cont,f(1,1,cont));
	}
	printf("--------------\n");
	for(cont=1; cont<=10; cont++) {
		printf("f(%d)=%d.\n",cont,fr(1,1,cont));
	}
}

/* función recursiva que calcule la siguiente serie
a1=x
a2=y
an=an-1+an-2
donde x e y son números enteros que se pasan como parámetros a la función
*/
int f(int x, int y, int n) {
	int cont, an_1, an_2, actual;
	
	if (n==1) {
		return x;
	} else if (n==2) {
		return y;
	}
	an_1=x;
	an_2=y;
	for(cont=3; cont<=n; cont++) {
		actual=an_1+an_2;
		an_1=an_2;
		an_2=actual;
	}
	return actual;
}

		

int fr(int x, int y, int n) {
	if (n==1) {
		return x;
	} else if (n==2) {
		return y;
	} else {
		return fr(x,y,n-1)+fr(x,y,n-2);
	}
}
