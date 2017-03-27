#include <stdio.h>
typedef struct {
	unsigned int cod;
	char nom[30];
} ASIGNATURA;

typedef struct {
	unsigned int cod;
	char nom[5];
} CURSO;

typedef struct {
	char dni[10];
	char nombre[20],
	     apellidos[30],
		 direccion[40],
		 telefono[10];
	unsigned int cod;
} ALUMNO;


typedef struct {
	char dni[10];
	unsigned int codAsig[10];
	char calificaciones[10];
} NOTA;

typedef struct {
	char dni[10];
	char nombre[20];
	char apellidos[30];
	char curso[5];
} RESULTADO;


#pragma warning(disable:4996)

int main(int argc, char**argv) {

}

void crearAsignaturas() {
	int i;
	FILE *pf=fopen("asignatura.dat","wb");
	ASIGNATURA a[]={{0,"LENGUA"},{1,"MATEMATICAS"},
					{2,"FISICA"},{3,"INFORMATICA"},
					{4,"RELIGION"},{5,"EDUCACION FISICA"},
					{6,"PLE"},{7,"ADA"}};

	for(i=0; i<8; i++) {
		fwrite(&a[i],sizeof(ASIGNATURA),1,pf);
	}
	fclose(pf);
}

void crearCurso() {
	int i;
	FILE *pf=fopen("curso.dat","wb");
	CURSO c[]={{0,"1-ESO"},
			   {1,"2-ESO"},
			   {2,"3-ESO"},
			   {3,"4-ESO"},
			   {5,"1-B-A"},
			   {6,"1-B-B"},
			   {7,"2-B-A"},
			   {8,"2-B-B"}};
	for(i=0; i<9; i++) {
		fwrite(&c[i],sizeof(CURSO),1,pf);
	}
	fclose(pf);
}

void crearAlumno() {
	int i;
	FILE *pf=fopen("alumnos.dat","wb");
	ALUMNO a[]={
		{"12345671","Pepito","Pérez López","C/ Rosario,1","954112231"},
		{"12345672","Juanito","González García","C/ Cuesta,2","954112232"},
		{"12345673","Luisito","Pérez Pérez","C/ Revuelta,3","954112233"},
		{"12345674","Rafaelito","González González","C/ Revolucion,4","954112234"},
		{"12345675","Albertín","Ramírez Ramírez","C/ Alternativa,5","954112235"}
	};
	for(i=0;i<5;i++) {
		fwrite(&a[i],sizeof(ALUMNO),1,pf);
	}
	fclose(pf);
}
