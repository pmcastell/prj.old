#include <stdio.h>

#pragma warning(disable:4996)

typedef struct {
	int cod;
	char nombre[40];
} ALUMNO;

void crearAlumno();
int cargarBorrando(ALUMNO *,int,int);

int main(int argc, char**argv) {
	ALUMNO tabla[6];

	crearAlumno();
	cargarBorrando(tabla,3,4);
}


void crearAlumno() {
	ALUMNO tabla[]={
		{0,"Francisco"},{1,"Pedro"},
		{2,"Fernando"},{3,"Luis"},
		{4,"Rodolfo"},{5,"Antonio"}
	};
	int i;
	FILE *pf=fopen("alumnos.dat","wb");
	for(i=0; i<sizeof(tabla)/sizeof(ALUMNO); i++) {
		fwrite(&tabla[i],sizeof(ALUMNO),1,pf);
	}
	fclose(pf);
}

int cargarBorrando(ALUMNO *tabla, int borrado, int tamMax) {
	FILE *pf;
	int i=0,reg=0;

	if (borrado<0 || borrado >=tamMax) {
		printf("Error: número de registro a borrar incorrecto");
		return -2;
	}
	pf=fopen("alumnos.dat","rb");
	/*fread(&tabla[i],sizeof(ALUMNO),1,pf);
    while(!feof(pf) && (i<tamMax-1 || reg==borrado)) {
		if (reg!=borrado) {
			i++;
		}
		fread(&tabla[i],sizeof(ALUMNO),1,pf);
		reg++;
	}*/
	fread(&tabla[i],sizeof(ALUMNO),1,pf);
	while(!feof(pf) && (i<tamMax-1 ||  i==borrado)) {
		if (i==borrado) {
			borrado--;
		} else {
			i++;
		}
		fread(&tabla[i],sizeof(ALUMNO),1,pf);
	}
	if (feof(pf)) {
		i--;
	}
	fclose(pf);
	return i+1; //tamaño de la tabla



/*	while(!feof(pf)) {// && i<tamMax-1) {
		if (i==borrado) {
			fread(&aux,sizeof(ALUMNO),1,pf);
		} else {
			tabla[i]=aux;
			fread(&aux,sizeof(ALUMNO),1,pf);
			i++;
		}
	}*/
}