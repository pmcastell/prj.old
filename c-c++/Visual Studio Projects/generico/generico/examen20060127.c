#include <stdio.h>
#include <string.h>




/*
1. Realizar un programa que lea una cadena de caracteres (máximo 11).
Mediante el uso de una función llamada invertir: imprimirla en onrden inversa,
sin usar otra variable intermedia. Mediante el uso de otra función llamada caract:
imprimirla carácter a carácter, de la siguiente forma (ejemplo: para):
					El carácter 1 es: p
					El carácter 2 es: a
					El carácter 3 es: r
					El carácter 4 es: a
void invertir(char*);
void caract(char []);
*/

void invertir(char *);
void caract(char []);
int main(int argc, char *argv[]) {
	char cad[12];

	printf("Introduce una cadena (máx. 11 caracteres): ");
	gets(cad);
	printf("La cadena invertida es: \n");
	invertir(cad);
	printf("\nLos carácteres de la cadena original, son: \n");
	caract(cad);

	
}
void invertir(char *cad) {
	int l=strlen(cad),i;

	for(i=l-1; i>=0; i--) {
		printf("%c",cad[i]);
	}
}
void caract(char cad[]) {
	int i=0;

	while(cad[i]!='\0') {
		printf("El carácter %d es: %c\n",i+1,cad[i]);
		i++;
	}
}


