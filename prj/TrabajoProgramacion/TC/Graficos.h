#define _GRAFICOS
//#include "graphics.h"

#define rectangulo(x1,y1,x2,y2) rectangle (x1,479-(y1),x2,479-(y2))

/*prototipos */
void graf_color(int , int);
void abre_graficos(* id);

void graf_color(tinta,papel)
{
  setcolor(tinta);
  setbkcolor(papel);
}

void abre_graficos()
{
 int gdriver = DETECT, gmode, errorcode;

 if (registerfarbgidriver(EGAVGA_driver_far) < 0) exit(1);
 initgraph(&gdriver, &gmode, "");
 errorcode = graphresult();
 if (errorcode != grOk) {
      printf("Error Iniciagraf.%s\n", grapherrormsg(errorcode));
      minombre("Salto del Caballo");
      exit(0);
  }
  graf_color(WHITE,BLUE);
}