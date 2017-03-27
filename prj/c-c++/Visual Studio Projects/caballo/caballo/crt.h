#ifndef __CRT__
    void tecladoEspaniol();
    char __convierteCaracter(char c);
    char * __convierteCadena(char *s);
    void __printf(char *format, ... );
    //#define printf __printf
	void gotoxy(int x, int y);
    void saltoLinea();
    void mueveCursorHorizontal(int i);
    void mueveCursorVertical(int i);
	void clrscr(char c);
	void clrscr();
    #define __CRT__
#endif
