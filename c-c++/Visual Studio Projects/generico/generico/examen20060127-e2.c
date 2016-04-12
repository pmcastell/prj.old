#include <stdio.h>
#include <string.h>


/*
2. Realizar una funci�n que a�ada una subcadena en otra cadena, un n�me3ro de veces
especificado: void ins_cad(char *cadena, char *subcad,int veces).
*/

void ins_cad(char *,char *,int);

int main(int argc, char *argv[]) {
	char cad[200]="Esto es una prueba", subcad[]=", cadena repetida";

	printf("Cadena: %s. Subcadena: %s.\n",cad,subcad);
	ins_cad(cad,subcad,5);
	printf("Cadena: %s.",cad);

	
}
// asumimos que cad tiene suficiente espacio para albergar al contenido que ya
// ten�a m�s veces*longitud(subcad) caracteres extra
void ins_cad(char *cad, char *subcad, int veces) {
	int i,j,l;

	l=(int) strlen(cad);
	for(i=1; i<=veces; i++) {
		j=0;
		while(subcad[j]!='\0') {
			cad[l++]=subcad[j++];
		}
	}
	cad[l]='\0';
}