/*
Programa principal Jugar.cpp

Analisis: el programa principal se encargara de mostrar un menu dando las
opciones de pintar un rombo en pantalla, jugar a piedra papel o tijera o no
hacer nada.

Entradas: un numero para la opcion del menu de presentacion, un numero entero
			para la longitud de la diagonal del rombo, un caracter para pintar el
         rombo y otro para repetir el proceso o no. Un numero entero para las
         partidas, un numero entero para la eleccion del jugador,y un caracter
         para repetir el proceso o no.

Salidas: un rombo hecho de caracteres y un mensaje con el ganador final, ganador
		de cada partida, el numero de partidas ganadas por cada uno y el numero de
      empates.

Inicio
   Hacer        //Bucle controlado por suceso (la VCB opcion es centinela)
   	Hacer
      	Presentar menu
      	Lectura de la opcion
  		Mientras la opcion no sea valida//Evaluacion VCB
   	Si la opcion es distinta de 0
   		Segun opcion escogida                   //Estructura de seleccion multiple
      		Para el caso 1
         		Juego rombo
         	Para el caso 2
         		Jugar a piedra papel o tijera
      	Fin segun
   	sino
   		Mostrar mensaje de despedida
   	Fin_si
   Mientras la opcion sea distinta de 0
Fin

Modulos detallados

//Juego Rombos de primer nivel

Entradas:un numero entero y un caracter
Salidas: pinta un rombo en pantalla

Inicio modulo Juego Rombos
	Hacer
   	Pedir la longitud de la diagonal del rombo(filas) y validar
      Pedir el caracter para pintar el rombo
      Pintar rombo
		Preguntar si desea volver a pintar un rombo y validar respuesta
   Mientras respuesta sea positiva
Fin modulo Juego Rombos

//Modulo Pintar rombo de segundo nivel
Inicio modulo Pintar rombo
      Calcular la media del numero de filas mas 1
      Inicializar numero de filas a 1                  //Inicializacion de VCB numero de filas contador
      Hacer                                            //Inicio bucle repetir
      	Pintar espacios de mitad superior 				 //Esto se realizara con un bucle para
         Pintar caracteres de mitad superior           //Esto se realizara con un bucle para
         actualizacion de media
         actualizar numero de fila
      Mientras el numero actual de la fila sea menor o = media   //Evaluacion y Fin bucle repetir

      Calcular la media del numero de filas mas 1
      Inicializar numero de filas a 2
      Hacer
      	Pintar espacios de mitad inferior            //Esto se realizara con un bucle para
         Pintar caracteres de mitad inferior          //Esto se realizara con un bucle para
         actualizacion de media
         actualizacion de numero de fila
      Mientras el numero actual de la fila sea menor o = media
Fin modulo Pintar rombo

//Modulo Pintar espacios de mitad superior de tercer nivel
Inicio modulo pintar espacios de mitad superior
	Para (i=1 y mientras sea menor o = que media, incrementar i en 1)  //Inicializacion, evaluacion y actualizacion
   	Escribir un espacio                                               de la VCB i contador
Fin modulo pintar espacios de mitad superior

//Modulo Pintar caracteres de mitad superior de tercer nivel
Inicio modulo pintar caracteres de mitad superior
	Para (j=1 y mientras sea menor o = que numero actual de la fila, incrementar j en 1)
     	Escribir un caracter
Fin modulo pintar caracteres de mitad superior

//Modulo Pintar espacios de mitad inferior de tercer nivel
Inicio modulo pintar espacios de mitad inferior
	Para (i=1 y mientras sea menor o = que numero actual de la fila, incrementar i en 1)
     	Escribir un espacio
Fin modulo pintar espacios de mitad inferior

//Modulo Pintar caracteres de mitad inferior de tercer nivel
Inicio modulo pintar caracteres de mitad inferior
	 Para (j=1 y mientras sea menor que media, incrementar j en 1)
     	Escribir un caracter
Fin modulo pintar caracteres de mitad inferior

// Jugar piedra papel o tijera de primer nivel

Entradas: un entero mayor que cero para el numero de partidas que desea jugar,
			y un numero entre entre 1 y 3 para la eleccion de piedra papel o tijera
Salidas: un mensaje con el ganador final, ganador de cada partida,
entero con el numero de partidas ganadas por cada uno y el numero de empates.

Inicio modulo Jugar piedra papel o tijera
	Hacer
   	Preguntar cuantas partidas quiere jugar y validar
      Preguntar si desea piedra papel o tijera y validar
      Calcular opcion ordenador
      Comparar opciones y mostrar ganador de partida
      Mostrar ganador final, numero de partidas ganadas por cada uno y el
      numero de empates
      Preguntar si desea volver a jugar
   Mientras respuesta sea positiva
Fin modulo Jugar piedra papel o tijera

*/
/*
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
//#include <conio.h>

void main3 (void){

int opcion, longitud, media, numfila, i, j, medioromb;
char caracter, espacio, respuesta;
int npartidas, njugadas, eleccionjug, eleccionord, contadorord=0,contadorjug=0, contadorempate=0;

do
{  printf("\n\t\t\tEL JUEGO DEL MENU\n\n");
	do
   {
		printf("\nELIJA UNA OPCION: \n1.-Pintar Rombo \n2.-Jugar a piedra papel o tijera \n0.-Salir\nRespuesta:   \t");
   	scanf("%d",&opcion);
      fflush(stdin);
   }
   while (opcion<0 || opcion>2);
	//while (opcion!=1 && opcion!=2 && opcion!=0);
	if (opcion!=0){
		switch (opcion){
   		case 1:
         	clrscr();
      		do{
               printf("\n\tPINTAR ROMBO\n");         //Inicio modulo Juego rombo
               do{
               	printf("\n\t De que longitud desea la diagonal del rombo (1 o 3 o 5 o 7 o 9):   ");
                  scanf("%d", &longitud);
                  fflush(stdin);
               }
               while (longitud<1 || longitud>9 || longitud==2 || longitud==4 || longitud==6 || longitud==8);
               printf("\n\t Que caracter desea para pintar el rombo:   ");
               scanf("%c", &caracter);
               fflush(stdin);
               media=(longitud/2)+1;
			      numfila=1;
               espacio=' ';
               medioromb=1;
			      do{
						for (i=1;i<=media;i++){
					  		printf("%c",espacio);
				     	}
				      for (j=1;j<=medioromb;j++){
					   	printf("%c",caracter);
					  	}
				      printf("\n");
				      media--;
      				numfila++;
                  medioromb=medioromb+2;
				   }
				   while (numfila<=(longitud/2+1));
				   numfila=2;
               medioromb=longitud-1;
				   do{
				  		for (i=1;i<=numfila;i++){
					  		printf("%c",espacio);
				     	}
				      for (j=1;j<medioromb;j++){
					   	printf("%c",caracter);
     					scanf("%c", &respuesta);
				     	}
				      printf("\n");
				      numfila++;
                  medioromb=medioromb-2;
				   }
				   while (numfila<=(longitud/2+1));
               do{
               	printf("\n\tDesea volver a pintar rombo(S/N):   ");
               	fflush(stdin);
               	respuesta=toupper(respuesta);
                  clrscr();
            	}
               while (respuesta!='S'&& respuesta!='N');
               if(respuesta=='N'){
               	printf("\n\tHASTA OTRA, nos vemos en el menu\n\n");
               }
            }
			   while(respuesta=='S');
         	break;                                      //Fin modulo Juego rombo
      	case 2:
            clrscr();
         	do{
	            printf("\n\tJUEGO PIEDRA PAPEL O TIJERA\n");   //Inicio modulo Piedra papel o tijera
					do
   				{
						printf("\n\t Cuantas partidas desea jugar?:   ");
						scanf("%d", &npartidas);
		  				fflush(stdin);
	 			  }
              while (npartidas<=0);
              for (njugadas=1;njugadas<=npartidas;njugadas++)
              {
              		do{
   	  			 		printf("\n\tElija: piedra(1) papel(2) o tijera(3)\n\tRespuesta:   ");
			       		scanf("%d",&eleccionjug);
         		 		fflush(stdin);
             		}
      				while (eleccionjug<1 || eleccionjug>3);
			      	eleccionord= random(2)+1;	//Calcular opcion ordenador
	      			printf("\n\t La elecccion del jugador ha sido: %d\n", eleccionjug);
			         printf("\n\t La elecccion del ordenador ha sido: %d\n", eleccionord);
      				//Comparar opciones y mostrar ganador de partida
			      	if (eleccionjug==eleccionord){
      					printf("\n\tEmpate!\n\n");
			        		contadorempate++;
	      			}
			   	   else{
      					if(eleccionjug==1 && eleccionord==3 || eleccionjug==3 && eleccionord==2 || eleccionjug==2 && eleccionord==1){
			         		printf("\n\tUsuario gana!\n\n");
      						contadorjug++;
							}
         				else{
								printf("\n\tOrdenador gana!\n\n");
								contadorord++;
        					}
      				}
         			//Fin Comparar opciones y mostrar ganador de partida
              }
              if(contadorjug==contadorord){
						printf("\n\tNo hay ganador final, Empate!\n\n");
		        }
      		  else{
         			if(contadorjug>contadorord){
	            		printf("\n\tGanador final usuario!\n\n");
		            }
      		      else{
	         		   printf("\n\tGanador final ordenador!\n\n");
		            }
              }
              printf("\tEl usario a ganado: %d\n\n", contadorjug);
              printf("\tEl ordenador a ganado: %d\n\n", contadorord);
		        printf("\tHa habido %d empates\n\n", contadorempate);
      	do{
				printf("\n\t Desea volver a jugar(S/N):   ");
     			scanf("%c", &respuesta);
         	fflush(stdin);
      		respuesta=toupper(respuesta);
            clrscr();
			}
			while (respuesta!='S'&& respuesta!='N');
         if(respuesta=='N'){
           	printf("\n\tHASTA OTRA, nos vemos en el menu\n\n");
         }
      }
		while(respuesta=='S');
      break;															//Fin modulo Piedra papel o tijera
   	}
	}
	else
		printf("\n\tHasta otra!");

}
while (opcion!=0);
getchar();
}
*/
