/*
1.Escribir una función para borrar un registro de tipo REG (int cod, char nom[7]), de un 
fichero llamado codigo.dat. La función recibe un código, que debe ser localizado y eliminado
en caso de encontrarse; en caso contrario: ERROR. 
Prototipo de la función: LOGICO borra(int,REG[],int);
Tener en cuenta:
* El número máximo de registros del fichero será 21.
* El campo cod es entero y positivo.
*La función devolverá el número del registro borrado, o -1 en caso de error.
*/

#include <stdio.h>

#pragma warning(disable:4996)


typedef struct {
	int cod;
	char nom[7];
} REG;

void crearFicheroCodigos();
int borra(int,REG[],int);
long contar_registros(FILE *,int);

int main(int argc, char **argv) {
	REG tabla[21];
	int i;
	int codigosParaBorrar[]={1,2,3,7,8,25,99,33,47,99,99};


	crearFicheroCodigos();
	for(i=0; i<sizeof(codigosParaBorrar)/sizeof(int); i++) {
		if (borra(codigosParaBorrar[i],tabla,21)<0) {
			printf("Error borrando código: %d inexistente.\n",codigosParaBorrar[i]);
		}
	}
}


int borra(int codigo,REG tabla[],int tam) {
	FILE *pf=fopen("codigo.dat","rb");
	int i=0,j, borrado=-1;

	//comprobamos si código correcto
	if (codigo<0) {
		return -1;
	}
	//cargamos registros en tabla
	fread(&tabla[i],sizeof(REG),1,pf);
	while(!feof(pf) && (i<tam-1 || tabla[i].cod==codigo)) {
		if (tabla[i].cod==codigo && borrado<0) {
			borrado=i;
		} else {
			i++;
		}
		fread(&tabla[i],sizeof(REG),1,pf);
	}
	if (feof(pf)) { // si es fin de fichero hemos leído basura en el último elemento de la tabla
		i--; // i queda apuntando al último elemento válido leído en la tabla
	}
	fclose(pf);
	//volcamos tabla en fichero
	pf=fopen("codigo.dat","wb");
	for(j=0;j<=i; j++) {
		fwrite(&tabla[j],sizeof(REG),1,pf);
	}
	fclose(pf);
	//devolvemos el código del registro borrado
	return borrado;
}


long contar_registros(FILE *pf, int num_bytes) {
	long pos=ftell(pf),res;
	fseek(pf,0,SEEK_END);
	res=ftell(pf)/num_bytes;
	fseek(pf,pos,SEEK_SET);
	return res;
}

void crearFicheroCodigos() {
	REG tabla[]={
		{1,"Uno"},
		{3,"Tres"},
		{8,"Ocho"},
		{25,"Vcinco"},
		{33,"Tres"},
		{47,"Csiete"},
		{99,"Nnueve"}
	};
	FILE *pf=fopen("codigo.dat","wb");
	int i;

	for(i=0; i<sizeof(tabla)/sizeof(REG); i++) {
		fwrite(&tabla[i],sizeof(REG),1,pf);
	}
	fclose(pf);
}