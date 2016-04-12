#include "crt.h"
#ifdef printf
    #undef printf
#endif
#include <stdio.h>
#include <windows.h> // para las funciones de cursor de windows
#include <stdarg.h>
#include <string.h>

#include "stdafx.h"

//Páginas de códigos disponibles en esta máquina
//[HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Nls\CodePage]
//Esto a continuación hace que se vean las mismas páginas de códigos
//en windows que en las consolas tipo MS-DOS
// La página usada para el teclado español es la ISO-8859-2 (Latin2)
//código de página para windows 1252
//esto sólo funciona en windows nt/xp :(
void tecladoEspaniol() {
    SetConsoleCP(1252); 
    SetConsoleOutputCP(1252);
}


typedef enum {_SIN_TIPO_,_CHAR_,_INT_,_DOUBLE_,_POINTER_,_STRING_} tipos;



char __convierteCaracter(char c) {
   int msdos[]={128,129,130,131,132,133,134,135,136,137,138,139,
		        140,141,142,143,144,145,146,147,148,149,150,151,
				152,153,154,155,156,157,158,159,160,161,162,163,
				164,165,166,167,168,169,170,171,172,173,174,175,
				176,177,178,179,180,181,182,183,184,185,186,187,
				188,189,190,191,192,193,194,195,196,197,198,199,
				200,201,202,203,204,205,206,207,208,209,210,211,
				212,213,214,215,216,217,218,219,220,221,222,223,
				224,225,226,227,228,229,230,231,232,233,234,235,
				236,237,238,239,240,241,242,243,244,245,246,247,
				248,249,250,251,252,253,254,255};
   int winds[]={'Ç','ü','é','â','ä','à','å','ç','ê','ë','è','ï',
		        'î','ì','Ä','Å','É','æ','Æ','ô','ö','ò','û','ù',
				'ÿ','Ö','Ü','¢','£','¥',158,159,'á','í','ó','ú',
				'ñ','Ñ','ª','º','¿',169,'¬','½','¼','¡','«','»',
				176,177,178,179,180,'Á',182,183,184,185,186,187,
				188,189,190,191,192,193,194,195,196,197,198,199,
				200,201,202,203,204,205,206,207,208,209,210,211,
				212,213,'Í',215,216,217,218,219,220,221,222,223,
				'Ó','ß',226,227,228,229,'µ',231,232,'Ú',234,235,
				236,237,238,239,240,'±',242,243,244,245,'÷',247,
				'°',249,'·',251,252,'²',254,255};

   if (c & 128) {
	   int i;
	   for(i=0; i<=127; i++) {
		   if (winds[i]==c) {
			   return (char)msdos[i];
		   }
	   }
   }
   return c;
}

char * __convierteCadena(char *s) {
	char *res=s;
	while (*s=__convierteCaracter(*s)) {
		s++;
	}
	return res;
}

void __printf(char *format, ... ) {
   va_list marker;
   char formatoAux[30]="%";
   tipos tipo;
   char caracter; int entero; double doble; void *puntero; char *cadena;
   char *cadAux;
   int i=0;

   va_start(marker,format); 
   while (*format) {
	   if (*format=='%') {
		   format++;
		   if (*format=='%') {
			   printf("%%");
			   format++;
			   continue;
		   }
		   i=1; tipo=_SIN_TIPO_;
		   while(*format!=' ' && *format!='%' && *format && tipo==_SIN_TIPO_) {
			   formatoAux[i]=*format;
			   switch (*format) {
			   case 'c': case 'C': 
				   tipo=_CHAR_;
				   caracter= va_arg(marker, char);
				   break;
			   case 'd': case 'i': case 'o': case 'u': case 'x': case 'X':
				   tipo=_INT_;
				   entero = va_arg(marker, int);
				   break;
			   case 'e': case 'E': case 'f': case 'g': case 'G':
				   tipo=_DOUBLE_;
				   doble = va_arg( marker, double);
				   break;
			   case 'n': case 'p':
				   tipo=_POINTER_;
				   puntero = va_arg( marker, void *);
				   break;
			   case 's': case 'S':
				   tipo=_STRING_;
				   cadena = va_arg( marker, char *);
				   break;
			   }
			   i++;
			   format++;
		   }
		   formatoAux[i]='\0';
		   if (tipo<_CHAR_) {
			   tipo=_INT_;
			   entero=va_arg(marker, int);
		   }
		   switch(tipo) {
		   case _CHAR_:
			   printf(formatoAux,__convierteCaracter(caracter));
			   break;
		   case _INT_:
			   printf(formatoAux,entero);
			   break;
		   case _DOUBLE_:
			   printf(formatoAux,doble);
			   break;
		   case _POINTER_:
			   printf(formatoAux,puntero);
			   break;
		   case _STRING_:
			   int len=0;
			   while(cadena[len++]); //averiguar longitud de cadena
			   cadAux=new char[len];
			   len=0;
			   while(cadAux[len]=__convierteCaracter(cadena[len])) {
				   len ++;// copiar cadena
			   }
			   printf(formatoAux,cadAux);
			   delete cadAux;
			   break;
		   }
	   } else {
		   putchar(__convierteCaracter(*format));
		   format++;
	   }
   }
}
#define printf __printf

void gotoxy(int x, int y) {
	static HANDLE hOutput = GetStdHandle(STD_OUTPUT_HANDLE); // asignar handler de pantalla
	COORD coor; // X,Y coordenadas de pantalla
	coor.X = x; 
	coor.Y = y; 
	SetConsoleCursorPosition(hOutput,coor); // poner el cursor en la posición (x,y)
}

void saltoLinea() {
    static HANDLE hOutput = GetStdHandle(STD_OUTPUT_HANDLE); // asignar handler de pantalla
    COORD coor;

    CONSOLE_SCREEN_BUFFER_INFO sbi;
    GetConsoleScreenBufferInfo(hOutput,&sbi);
    coor.X=sbi.dwCursorPosition.X;
    coor.Y=sbi.dwCursorPosition.Y+1;
    SetConsoleCursorPosition(hOutput,coor);
}

void mueveCursorHorizontal(int i) {
    static HANDLE hOutput = GetStdHandle(STD_OUTPUT_HANDLE); // asignar handler de pantalla
    COORD coor;

    CONSOLE_SCREEN_BUFFER_INFO sbi;
    GetConsoleScreenBufferInfo(hOutput,&sbi);
    coor.X=sbi.dwCursorPosition.X+i;
    coor.Y=sbi.dwCursorPosition.Y;
    SetConsoleCursorPosition(hOutput,coor);
}

void mueveCursorVertical(int i) {
    static HANDLE hOutput = GetStdHandle(STD_OUTPUT_HANDLE); // asignar handler de pantalla
    COORD coor;

    CONSOLE_SCREEN_BUFFER_INFO sbi;
    GetConsoleScreenBufferInfo(hOutput,&sbi);
    coor.X=sbi.dwCursorPosition.X;
    coor.Y=sbi.dwCursorPosition.Y+i;
    SetConsoleCursorPosition(hOutput,coor);
}




void clrscr(char c)
{
	static HANDLE stdout_handle = GetStdHandle(STD_OUTPUT_HANDLE);
	CONSOLE_SCREEN_BUFFER_INFO csbi;
	COORD topleft = {0, 0};
	DWORD write_count;

	fflush(stdin);
	SetConsoleCursorPosition(stdout_handle, topleft);
	GetConsoleScreenBufferInfo(stdout_handle, &csbi);
	DWORD length = csbi.dwSize.X*csbi.dwSize.Y;
	FillConsoleOutputCharacter(stdout_handle, TEXT(c), length, topleft,
		&write_count);
	FillConsoleOutputAttribute(stdout_handle, csbi.wAttributes, length,
		topleft, &write_count);
}

void clrscr() {
	clrscr(' ');
}


