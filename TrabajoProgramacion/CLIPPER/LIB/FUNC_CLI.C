#include <dos.h>
#include <extend.h>

/*CLIPPER SOMBRA()
{
   int x1, y1, x2, y2, i, j;
   unsigned char far *pantalla;

   x1= _parni(1);
   y1= _parni(2);
   x2= _parni(3);
   y2= _parni(4);
   /*pantalla=MK_FP(((biosequip() & 0x30) == 0x30)*0x800+0xb000,0);*/
   pantalla=MK_FP(0xb800,0);
   for (i=x1;i<=x2;i++) {
      for (j=y1;j<=y2;j++) {
	 pantalla[i*160+2*j+1]%=8;
      }
   }
   _ret();
}*/

/*CLIPPER _CH_COLOR()
{
   unsigned char far *pantalla;
   unsigned int i,tinta,papel;
   unsigned char *atributo;

   tinta= _parni(1);
   papel= _parni(2);
   *atributo= (papel << 4) +  tinta;
   pantalla=MK_FP(0xb800,0);
   for (i=1;i<4000;i+=2) {
      pantalla[i]= atributo[0];
   }
   _ret();
}*/

CLIPPER IMPR_PANT()
{
   geninterrupt(0x5);
   _ret();
}

CLIPPER INTR()
{
   geninterrupt(_parni(1));
   _ret();
}

/* funcion scrroll llamada _scrl(dir,x1,y1,x2,y2,Num_lin,Atrib_relleno)*/
CLIPPER _SCRL()
{
   int dir,x1,y1,x2,y2,par6,par7;

   dir= _parni(1); /* direccion del scroll */
   x1= _parni(2);
   y1= _parni(3);
   x2= _parni(4);
   y2= _parni(5);
   par6=_parni(6);
   par7=_parni(7);
   if (dir) {
      _AH=0x06;  /* servicio de INT 10h (arriba)*/
   } else {
      _AH=0x07; /* servicio de INT 10h (abajo) */
   }
   _AL= par6; /* n£mero de l¡neas a "scrollear"*/
   _BH= par7; /* atributo color relleno */
   _CH= x1; /* coordenada esquina sup.izda. fila */
   _CL= y1; /*  "          "        "   "   col. */
   _DH= x2; /*  "          "      inf.dcha. fila */
   _DL= y2; /*  "          "       "    "   col. */
   geninterrupt(0x10);
   _ret();
}

CLIPPER REEMPLAZAC()
/* llamada reemplazac(cadena,caracter,indice)*/
{

    _parc(1)[_parni(3)+1]=*_parc(2);
    _ret();
}



CLIPPER SONIDO()
{
   sound(_parni(1));
   _ret();
}
CLIPPER RETARDO()
{
   delay(_parni(1));
   _ret();
}
CLIPPER SUPERSONIC()
{
   unsigned int i, min, max;

   min=26000;
   max=29500;
   for(i=min;i<=max;i++) {
      sound(i-(max << 4));
      delay(1);
      sound( i << 2);
      sound(max-i);
      delay(1);
   }
   nosound();
}
CLIPPER TIRO()
{
   unsigned int i;

   for(i=4000;i>=20;i-=4) {
      sound(i);
   }
   nosound();
}
CLIPPER AMETRALLAD()
{
   int j;

   for(j=1;j<=20;j++) {
      TIRO();
   }
}
CLIPPER LASER()
{
   unsigned int j,min,base;
   unsigned long max,i;

   base=4400;
   min=2200;
   max=3600;
   /*incremento=1; incrementos decimales necesitaria floating point*/
   i=min;
   while ( i<max) {
      sound(base+(int)(i) % base);
      sound(base-(int)(i) % base);
      i++;
   }
   nosound();
}

CLIPPER SOMBREA()
{
   int i,j;
   char *c;

   j=_parni(2);
   c=_parc(1);
   for (i=1;i<j;i+=2)
      c[i]%=8;
   _ret();
}

CLIPPER _CH_COLOR()
/* llamada cambia_color(pant,atributo,lon)*/
{
    int i,lon;
    char *pant,*atributo;

    pant=_parc(1);
    atributo=_parc(2);
    lon=_parni(3);
    for (i=1;i<=lon;i+=2,pant++) {
       *++pant=*atributo;
    }
    _ret();
}
