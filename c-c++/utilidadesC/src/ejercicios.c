/*
 ============================================================================
 Name        : ejercicios.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

/*
Programa Principal_Evaluar notas
El programa convierte la nota numérica del alumno en SUSPENSO, APROBADO, BIEN,
NOTABLE O SOBRESALIENTE comprobando si se encuentra en un rango determinado
entre 0 y 10.

Entradas:   Numero real con la nota, respuesta para realizar el proceso 'S' o 'N' y validar todas las entradas
Salidas: Cadena de caracteres con el resultado

Suponemos que el usuario introducirá solo caracteres numéricos.

Inicio
Preguntar si desea evaluar nota								// Inicializacion VCB centinela
Mientras la respuesta sea correcta							//Evaluacion VCB
	Pedir nota
	Evaluacion nota
	Mostrar resultado
	Volver a preguntar si desea evaluar nota otra vez	// Actualización VCB centinela
Fin_Mientras
Fin

*/

#include <stdio.h>
#include <ctype.h>

#include <stdio.h>

int main2(int argc, char**argv) {
	int i;

	for(i=1;i<=10;i++) {
		printf("El cuadrado de %d es %d.\n",i,i*i);
		//getchar();
	}
	i=1;
	while(i<=10) {
		printf("El cuadrado de %d es %d\n",i,i*i);
		if (i==5)
			printf("%d\n",i);
		i++;
	}

	printf("\nIntroduce día: ");
	scanf("%d",&i);
	switch(i) {
		case 1:
			printf("Lunes");
			break;
		case 2:
			printf("Martes");
			break;
		case 3: case 4: case 5: case 6: case 7:
			printf("Es después del martes");
			break;
		default:
			printf("No era un día correcto");
	}
	return 0;
}



/*
int main2(int argc, char** argv)
{
   char respuesta;
   printf("\tQuiere evaluar la nota?(S/N)  ");      //Inicializacion VCB (centinela)
   scanf("%c", &respuesta);
   fflush(stdin);
   fflush(stdin);
   respuesta=toupper(respuesta);
// me falta realizar la validacion con un mientras de todo
	while ((respuesta!='S')&&(respuesta!='N'))
   {
   	printf("\n\tDebe responder 'S' o 'N'\n");
      printf("\n\tQuiere evaluar la nota? (S/N)   ");
      scanf("%c", &respuesta);
   	fflush(stdin);
   	fflush(stdin);
  		respuesta=toupper(respuesta);
   }
   //Evaluar nota
   float nota;
   while (respuesta=='S')
   {
      //Comprobar numero
      printf("\n\tIntroduce la nota:   ");
      scanf("%f", &nota);
      fflush(stdin);
      fflush(stdin);
      while (nota<0 || nota>10)                            // Subtarea iterativa VBC(centinela)
      {
         printf("\n\tError: la nota introducida no es valida.");
         printf("\n\tIntroduce de nuevo la nota:   ");
         scanf("%f", &nota);
         fflush(stdin);
         fflush(stdin);
      }
      //Resultado
      if (nota<5)
      {
         printf("\n\tAlumno: SUSPENSO.\n\n");
      }
      else
      {
         if(nota>=5 && nota<6)
         {
            printf("\n\tAlumno: APROBADO.\n\n");
         }
         else
         {
            if(nota>=6 && nota<7)
            {
               printf("\n\tAlumno: BIEN.\n\n");
            }
            else
            {
               if (nota>=7 && nota<8)
               {
                  printf("\n\tAlumno: NOTABLE.\n\n");
               }
               else
               {
                  if (nota<=10)
                  {
                  	printf("\n\tAlumno: SOBRESALIENTE.\n\n");
                  }
               }
            }
         }
      }
      printf("\tQuiere volver a evaluar la nota?(S/N)  ");      //Actualizacion VCB
      scanf("%c", &respuesta);
      fflush(stdin);
      fflush(stdin);
      respuesta=toupper(respuesta);
     	while ((respuesta!='S')&&(respuesta!='N'))
  		{
   		printf("\n\tDebe responder 'S' o 'N'\n");
      	printf("\n\tQuiere evaluar la nota? (S/N)   ");
     		scanf("%c", &respuesta);
   		fflush(stdin);
   		fflush(stdin);
  			respuesta=toupper(respuesta);
   	}
   }//Fin_Mientras
   printf("\n\t\t HASTA OTRA! ^_^");
   getchar();
   return 0;
}
*/
