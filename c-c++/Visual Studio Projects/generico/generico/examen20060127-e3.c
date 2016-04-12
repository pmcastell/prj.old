#include <stdio.h>
#include <string.h>

//#pragma warning(disable:4996)


/*
3. Realizar la ordenación de una tabla de 7 cadenas. Emplear una función para 
inicializar la tabla (void inicializa(char [][];), otra para la ordenación y otra 
para el listado (void lista(char [][]);).
*/

void inicializa(char [][17]);
void ordena(char tabla[][17],int n);
void lista(char [][17]);

int main(int argc, char *argv[]) {
	char tabla[7][17];

	inicializa(tabla);
	lista(tabla);
	ordena(tabla,7);
	lista(tabla);
	
}

void inicializa(char tabla[][17]) {
	strcpy(tabla[0],"cadena 3");
	strcpy(tabla[1],"cadena 2");
	strcpy(tabla[2],"cadena 5");
	strcpy(tabla[3],"cadena 7");
	strcpy(tabla[4],"cadena 4");
	strcpy(tabla[5],"cadena 1");
	strcpy(tabla[6],"cadena 6");
}

void intercambiar(char *s1, char *s2) {
	char aux[17];

	strcpy(aux,s1);
	strcpy(s1,s2);
	strcpy(s2,aux);
}
	
void ordena(char tabla[][17],int n) {
	int i,j;

	for(i=1; i<n; i++) {
		for(j=i; strcmp(tabla[j],tabla[j-1])<0 && j>0; j--) {
			intercambiar(tabla[j-1],tabla[j]);
		}
	}
}

void lista(char tabla[][17]) {
	int i;

	for(i=0; i<7; i++) {
		printf("%s\n",tabla[i]);
	}
}
