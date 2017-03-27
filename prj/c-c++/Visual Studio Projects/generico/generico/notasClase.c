/*
Leer las notas de los alumnos de varios cursos distintos. El usuario decidir� el n� de cursos
del que se van a introducir los datos, pas�ndole un argumento al programa desde la l�nea de 
comandos. Cada clase tiene 17 alumnos. Se pide:
   i. % de aprobados de cada curso
  ii. Nota m�xima y m�nima de cada curso
 iii. Nota m�xima y m�nima del total, con los nombres correspondientes.
  iv. Media total

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#pragma warning(disable:4996)

int main(int argc, char** argv) {
	int curso, alumno, numcursos, 
		totalAlumnos=0, aprobadosCurso;
	float nota=0.0, notaMinCurso, notaMaxCurso,
		  notaMaxTotal=0.0, notaMinTotal=10.0,
		  totalNotas=0.0;
	char nombre[60], nombreMinTotal[60], nombreMaxTotal[60];

	if (argc!=2) {
		printf("Uso: %s <numcursos>.",argv[0]);
		exit(-1);
	}
	numcursos=atoi(argv[1]);
	for(curso=1; curso<=numcursos; curso++) {
		printf("Curso: %2d\n",curso);
		printf("---------\n");
		notaMinCurso=10.0;
		notaMaxCurso=0.0;
		aprobadosCurso=0;
		for(alumno=1; alumno<=17; alumno++) {
			printf("\nIntroduce nombre del alumno: ");
			scanf("%s",nombre);
			printf("\nIntroduce nota alumno %d: ",alumno);
			scanf("%f",&nota);
			if (nota>=5.0) {
				aprobadosCurso++;
			}
			if (nota>notaMaxCurso) {
				notaMaxCurso=nota;
			}
			if (nota<notaMinCurso) {
				notaMinCurso=nota;
			}
			if (nota>notaMaxTotal) {
				notaMaxTotal=nota;
				strcpy(nombreMaxTotal,nombre);
			}
			if (nota<notaMinTotal) {
				notaMinTotal=nota;
				strcpy(nombreMinTotal,nombre);
			}
			totalNotas+=nota;
			totalAlumnos++;
		}
		printf("\nEl porcentaje de aprobados del curso %d, es: %2.2f.\n",curso,(float)aprobadosCurso/17*100);
		printf("La nota M�xima ha sido: %2.2f, y la M�nima: %2.2f.\n",notaMaxCurso,notaMinCurso);
	}
	printf("La Nota M�xima de todos los cursos ha sido: %2.2f.\n",notaMaxCurso);
	printf("Alumno Nota M�xima %s.\n",nombreMaxTotal);
	printf("La Nota M�nima de todos los cursos ha sido: %2.2f.\n",notaMinCurso);
	printf("Alumno Nota M�nima %s.\n",nombreMinTotal);
	printf("La Nota Media Global ha sido: %2.2f.\n",totalNotas/totalAlumnos);

	return 0;
}