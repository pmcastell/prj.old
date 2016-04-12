#include <stdio.h>
#include <stdlib.h>

void tablas(int,int);
void linea(int);

int main(int argc, char *argv[]) {
	if (argc!=3) {
		printf("Uso: %s numfilas numcolumnas.",argv[0]);
		return -1;
	}
	tablas(atoi(argv[1]),atoi(argv[2]));
	return 0;
}



void tablas(int filas, int columnas) {
	int f,c;

	linea(columnas+1);
	printf("|    ");
	for(c=1;c<=columnas;c++) {
		printf("|%4d",c);
	}
	printf("|\n");
	linea(columnas+1);
	for(f=1;f<=filas;f++) {
		printf("|%4d",f);
		for(c=1;c<=columnas;c++) {
			printf("|%4d",f*c);
		}
		printf("|\n");
		linea(columnas+1);
	}
}

void linea(int columnas) {
	int c;

	for(c=1;c<=columnas;c++) {
		printf("+----");
	}
	printf("+\n");
}