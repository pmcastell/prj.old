#include <stdio.h>
#include <string.h>

int calcularDescuento(int,float,int);


int main2(int argc, char **argv) {
	int ant;
	float vol;
	char esp;
	char prueba[]="abcdefghijkl";

	permutar(prueba,0,strlen(prueba));
	printf("\nIntroduce Ant.:");
	scanf("%d",&ant);
	fflush(stdin);
	printf("\nIntroduce volumen de compras:");
	scanf("%f",&vol);
	fflush(stdin);
	printf("\nClientes especial");
	scanf(" %c",&esp);
	printf("\nEl descuento es %d\n",calcularDescuento(ant,vol,esp=='S'));
}
