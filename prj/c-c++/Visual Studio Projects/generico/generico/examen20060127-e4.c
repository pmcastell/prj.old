#include <stdio.h>
#include <string.h>




#pragma warning(disable:4996)

/*
4. Realizar el siguiente ejercicio mediante el uso de funciones:
	a. Crear el tipo de dato LIBRO: titulo, editorial, fecha de publicacion
	b. Función que almacene los datos de un libro. Prototipo: 
	void almacena (LIBRO *);. Mediante el uso de dicha función almacenar n
	libros en la tabla. Tener en cuenta que el número máximo de libros será
	17.
	c. Elegir una editorial y mediante una función, listar los elementos de 
	la tabla correspondientes. Prototipo: void listar(LIBRO *, char *, int).
	Se le pasa la tabla de libros, el nombre de la editorial a buscar y el número
	de libros insertados en dicha tabla.
*/

typedef struct {
	char titulo[21];
	char editorial[16];
	char fecha[11];
}LIBRO; 

void almacena(LIBRO	*);
void listar(LIBRO *, char *, int);



int main(int argc, char *argv[]) {
	LIBRO libros[17];
	char editorial[16];
	int n,i;

	printf("¿Cuántos libros vas a introducir?");
	fflush(stdin);
	scanf("%d",&n);
	for(i=0; i<n; i++) {
		almacena(&libros[i]);
	}
	printf("Introduce editorial: ");
	fflush(stdin);
	gets(editorial);
	listar(libros,editorial,n);

}


void almacena(LIBRO	*l) {
	printf("Introduce el título del libro: ");
	fflush(stdin);
	gets(l->titulo);
	printf("Introduce Editorial: ");
	fflush(stdin);
	gets(l->editorial);
	printf("Introduce Fecha: ");
	fflush(stdin);
	gets(l->fecha);
}
void imprime(LIBRO l) {
	printf("[%20s,%15s,%11s]",l.titulo,l.editorial,l.fecha);
}
void listar(LIBRO *libros, char *editorial, int tam) {
	int i;

	for(i=0; i<tam; i++) {
		if (strcmp(editorial,libros[i].editorial)==0) {
			imprime(libros[i]);
			printf("\n");
		}
	}
}
