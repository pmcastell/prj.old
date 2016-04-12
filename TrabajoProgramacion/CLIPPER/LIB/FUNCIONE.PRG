STATIC _salva_tinta:={'w','w','w','w'}, _salva_papel:={'n','n','n','n'},;
       _num_colores:=0,;
       _cursor_x:={0,0,0,0},_cursor_y:={0,0,0,0},;
       _estado_cursor:={1,1,1,1},;
       _num_cursores:=0,;
       _ultimo_papel:='n',_ultima_tinta:='w',;
       _win_x1:=0,_win_y1:=0,_win_x2:=24,_win_y2:=79,;
       _colores:={'n','b','g','bg','r','rb','gr',;
                      'w','n+','b+','g+','bg+','r+',;
                      'rb+','gr+','w+'},;
       _cadena_inicializacion:='',;
       _nombre_programa:='',_ano:=''





#define  seg_pant 47104
#define  off_pant 0
#define  _doble   'ÉÍ»º¼ÍÈº '
#define  _simple  'ÚÄ¿³ÙÄÀ³ '
#include "inkey.ch"

FUNCTION minombre
LOCAL _minombre

_minombre:=chr(70)+chr(114)+chr(97)+chr(110)+chr(99)+chr(105)+chr(115)+chr(99)
_minombre:=_minombre+chr(111)+chr(32)+chr(74)+chr(97)+chr(118)+chr(105)+chr(101)+chr(114)
_minombre:=_minombre+chr(32)+chr(67)+chr(114)+chr(105)+chr(97)+chr(100)+chr(111)+chr(32)
_minombre:=_minombre+chr(78)+chr(97)+chr(118)+chr(97)+chr(114)+chr(114)+chr(111)
          /* Francisco Javier Criado Navarro*/
RETURN (_minombre)


FUNCTION imp_ok
LOCAL cont:=1, segundos1, segundos2

segundos2:=segundos1:=seconds()
do while .not. isprinter()
   mensaje({'La impresora no est  ON LINE',;
            'Compruebe que hay papel y p¢ngala "EN LINEA"',;
            'Pulse: [Esc] => Terminar '},;
            'gr+','r',0)
   if lastkey()=27
      return(.f.)
&&   elseif lastkey()=-9
&&      run capture q=cola_1
   endif
   do while (.not. isprinter() .and. (segundos2-segundos1<2))
      segundos2:=seconds()
   enddo
enddo
return(.t.)


FUNCTION imprimeln(renglon)
LOCAL ret_carro:=chr(13)+chr(10)

RETURN imprime(renglon+ret_carro)


FUNCTION imprime(renglon)

if imp_ok()
   set printer on
   ?? renglon
   set printer off
   return(.t.)
endif
return .f.

/*FUNCTION parar
LOCAL tecla

clear typeahead
mensaje({'     La tecla [Esc] ha sido pulsada      ',;
         '      ¨Desea parar el proceso?(S/N)'},;
         'gr+','r',-1)
tecla=upper(chr(lastkey()))
do while .not.(tecla$'SN')
   inkey(0)
   tecla=upper(chr(lastkey()))
enddo
if tecla='S'
   return(.f.)
endif
return(.t.)*/

FUNCTION val_tinta(tinta)
LOCAL i

i:=1
while (_colores[i]!=tinta)
    i++
enddo
RETURN (i-1)

FUNCTION int_tinta_actual
RETURN (val_tinta(_ultima_tinta))

FUNCTION tinta_actual(tinta)

if (tinta==NIL)
   return(_ultima_tinta)
else
   _ultima_tinta:=tinta
endif
RETURN NIL

FUNCTION val_papel(papel)
LOCAL i

i:=1
while (_colores[i]!=papel)
   i++
enddo
RETURN (i-1)

FUNCTION int_papel_actual
RETURN (val_papel(_ultimo_papel))



FUNCTION papel_actual(papel)

if (papel==NIL)
   return(_ultimo_papel)
else
   _ultimo_papel:=papel
endif
RETURN NIL


FUNCTION color(tinta,papel)
LOCAL r

r=right(papel,1)
setblink(.f.)
if r='+'
   r:=subst(papel,1,len(papel)-1)+'*'
elseif (r='*')
   r:=papel
   setblink(.t.)
endif
setcolor(tinta+'/'+r)
tinta_actual(tinta)
papel_actual(papel)
RETURN NIL


PROCEDURE centra(cadena,f1,c1)

if (cadena==NIL)
   return NIL
endif
if (f1==NIL)
   f1:=row()
endif
if (c1==NIL)
   c1:=0
endif
setpos(f1,c1+int( (81-c1-len(cadena)) / 2))
?? cadena
RETURN


FUNCTION atributo(fila,col,atrib)
LOCAL aux

if (fila==NIL)
   fila:=row()
endif
if (col==NIL)
   col:=col()
endif
aux:=savescreen(fila,col,fila,col)
if (atrib!=NIL)
   aux:=subst(aux,1,1)+chr(atrib)
   restscreen(fila,col,fila,col,aux)
endif
RETURN(asc(substr(aux,2,1)))

PROCEDURE sombra(x1,y1,x2,y2)
LOCAL pant:=''

pant:=savescreen(x1,y1,x2,y2)
sombrea(pant,2*(x2-x1+1)*(y2-y1+1))
restscreen(x1,y1,x2,y2,pant)
RETURN NIL

*FUNCTION sombra(x1,y1,x2,y2)
*LOCAL pant:='',pant_aux:='',i, long

*pant:=savescreen(x1,y1,x2,y2)
*long:=len(pant)
*for i:=2 to long step 2
*   pant_aux:=pant_aux+subst(pant,i-1,1)+chr(asc(subst(pant,i,1)) % 8)
*next
*restscreen(x1,y1,x2,y2,pant_aux)
*RETURN NIL

*FUNCTION sombra(x1,y1,x2,y2)
*LOCAL pant:='', pant_aux:='',i,j, coord
*
*for i:=x1 to x2
*   for j:=y1 to y2
*      coord:=i*160 + 2*j + off_pant +1
*      pokeb(seg_pant,coord,peekb(seg_pant,coord) % 8)
*   next
*next
*
*RETURN NIL



PROCEDURE salva_color

if (_num_colores<4)
   _num_colores++
   _salva_tinta[_num_colores]:=_ultima_tinta
   _salva_papel[_num_colores]:=_ultimo_papel
endif
RETURN

PROCEDURE repon_color

if (_num_colores>0)
   color(_salva_tinta[_num_colores], _salva_papel[_num_colores])
   _num_colores--
endif
RETURN


FUNCTION salva_cursor

if (_num_cursores<4)
   _num_cursores++
   _cursor_x[_num_cursores]:=row()
   _cursor_y[_num_cursores]:=col()
   _estado_cursor[_num_cursores]:=setcursor()
endif
RETURN NIL

FUNCTION repon_cursor

if (_num_cursores>0)
   setpos(_cursor_x[_num_cursores],_cursor_y[_num_cursores])
   setcursor(_estado_cursor[_num_cursores])
   _num_cursores--
endif
RETURN NIL

FUNCTION salva_conf_pant

salva_color()
salva_cursor()
RETURN NIL

FUNCTION repon_conf_pant

repon_color()
repon_cursor()
RETURN NIL

FUNCTION cuadro(x1,y1,x2,y2,tinta,papel)

_cuadro(x1,y1,x2,y2,tinta,papel,_doble)
tiro()
RETURN NIL

FUNCTION cuadrs(x1,y1,x2,y2,tinta,papel)

_cuadro(x1,y1,x2,y2,tinta,papel,_simple)
tiro()
RETURN NIL

FUNCTION _cuadro(x1,y1,x2,y2,tinta,papel,tipo)
LOCAL alto, ancho, cx, cy,i, j

if (tinta!=NIL)
   salva_conf_pant()
   if (papel!=NIL)
      color(tinta,papel)
   else
      color(tinta,papel_actual())
   endif
endif
alto:=int((x2-x1)/2)
ancho:=int((y2-y1)/2)
i:=1
j:=1
cx:=x1+alto
cy:=y1+ancho
do while ((i<alto) .or. (j<ancho))
   __Box( cx-i,cy-j,cx+i,cy+j,tipo)
   if (i<alto)
      i++
   endif
   if (j<ancho)
      j++
   endif
enddo
__Box(x1,y1,x2,y2,tipo)
if (tinta!=NIL)
   repon_conf_pant()
endif
RETURN NIL

FUNCTION recuadro(x1,y1,x2,y2,tinta,papel)

_recuadro(x1,y1,x2,y2,tinta,papel,_doble)
RETURN NIL

FUNCTION recuadrs(x1,y1,x2,y2,tinta,papel)

_recuadro(x1,y1,x2,y2,tinta,papel,_simple)
RETURN NIL

FUNCTION _recuadro(x1,y1,x2,y2,tinta,papel,tipo)

_cuadro(x1,y1,x2,y2,tinta,papel,tipo)
sombra(x1+1,y2+1,x2+1,y2+1)
sombra(x2+1,y1+2,x2+1,y2+1)
tiro()
RETURN NIL


FUNCTION tam_max(elemento)
LOCAL i, n_componentes, mmax
if (valtype(elemento)='A')
   n_componentes:=len(elemento)
   mmax:=len(elemento[1])
   for i:=2 to n_componentes
      if (len(elemento[i])>mmax)
         mmax:=len(elemento[i])
      endif
   next
else
   mmax:=1
endif
RETURN (mmax)


FUNCTION mensaje(mens, tinta, papel, pausa,linea)
LOCAL pant, max_lon, i, x1, y1, x2, y2, longitud

salva_conf_pant()
if (mens==NIL)
   return NIL
endif
if (tinta== NIL)
   tinta:='gr+'
endif
if (papel==NIL)
   papel:='r'
endif
if (pausa==NIL)
   pausa:=8
endif
if (linea==NIL)
   linea:=12
endif
save screen to pant
set cursor off
clear typeahead
color(tinta,papel)
if valtype(mens)='A'
   longitud:=len(mens)
   max_lon:=tam_max(mens)
   x1:=linea-1-(int(longitud/2))
   y1:=38-(int(max_lon/2))
   x2:=linea+1+(int(longitud/2))
   y2:=42+(int(max_lon/2))
   recuadrs(x1,y1,x2,y2,tinta,papel)
   for i:=1 to longitud
      centra(mens[i],x1+i)
   next
else
   x1:=linea-1
   y1:=38-int(len(mens)/2)
   x2:=linea+1
   y2:=42+int(len(mens)/2)
   recuadrs(x1,y1,x2,y2,tinta,papel)
   centra(mens,x1+1)
endif
repon_conf_pant()
if (pausa>-1)
   tone(2400,6)
   tecla:=upper(chr(inkey(pausa)))
   restore screen from pant
   RETURN(tecla)
endif
RETURN NIL

PROCEDURE nombre_programa(n,a)

if (n!=NIL)
   _nombre_programa:=n
endif
if (a!=NIL)
   _ano:=a
endif
RETURN

FUNCTION borrar(tinta,papel)
LOCAL pie_pagina, cont

if (tinta==NIL)
   tinta:='w'
endif
if (papel==NIL)
   papel:='b'
endif
salva_conf_pant()
pie_pagina:='Programa '+_nombre_programa+' (c) '+_ano+' '+minombre()
pie_pagina:=space(40-len(pie_pagina)/2)+pie_pagina+space(40-len(pie_pagina)/2)
pie_pagina:=pie_pagina+space(80-len(pie_pagina))
cont=0
color(tinta,papel)
for cont:=0 to 24
   @cont,0 say replicate ('°',80)
next
color('gr+','gb')
@24,0 SAY pie_pagina
repon_conf_pant()
RETURN NIL


FUNCTION letranif(carnet)
LOCAL letra:={'T','R','W','A','G','M','Y','F','P','D','X','B',;
              'N','J','Z','S','Q','V','H','L','C','K','E'},len_carnet, resto

carnet=upper(alltrim(carnet))
if (left(carnet,1)$'0123456789' .and. carnet<>space(9))
   do while (.not. (right(carnet,1)$'0123456789'))
      carnet=substr(carnet,1,len(carnet)-1)
   enddo
   len_carnet=len(carnet)
   if len_carnet>8
      mensaje('   N.I.F. no v lido   ','gr+','n')
      return(.f.)
   endif
   carnet:=carnet+letra[val(carnet) % 23 + 1]+space(8-len_carnet)
endif
carnet:=carnet+space(9-len(carnet))
RETURN(.t.)


FUNCTION menuc(opciones,cabecera,mensajes,tinta,papel,opcion)

borrar()
RETURN(menucaja(opciones,cabecera,mensajes,tinta,papel,@opcion))


FUNCTION menucaja(opciones,cabecera,mensajes,tinta,papel,opcion)
LOCAL c,a1,b1,a2,b2,x1,y1,x2,y2
/* c es el espaciado interlinea de las opciones */

if (tinta==NIL)
   tinta='W+'
endif
if (papel==NIL)
   papel='R'
endif
color(tinta,papel)
if (cabecera!=NIL)
   a1=1
   b1=38-len(cabecera)/2
   a2=3
   b2=42+len(cabecera)/2
   recuadrs(a1,b1,a2,b2,tinta,papel)
   @a1+(a2-a1)/2,b1+2 say cabecera
endif
c:=iif((len(opciones)<=7),2,1)
mmax=tam_max(opciones)+3
x1:=10-len(opciones)*c/2
if (x1<5)
   x1:=5
endif
y1:=40-(mmax+2)/2
x2:=12+len(opciones)*c/2
if (x2>21)
   x2:=21
endif
y2:=40+(mmax+2)/2
RETURN(caja_menu(x1,y1,x2,y2,opciones,mensajes,@opcion))

PROCEDURE _caja_sound

   laser()
   set key K_DOWN  to
   set key K_UP    to
   set key K_LEFT  to
   set key K_RIGHT to
   keyboard chr(lastkey())+chr(1)
RETURN

PROCEDURE _caja_set_sound
   set key 1       to _caja_set_sound
   set key K_DOWN  to _caja_sound
   set key K_UP    to _caja_sound
   set key K_LEFT  to _caja_sound
   set key K_RIGHT to _caja_sound
RETURN



FUNCTION caja_menu(x1,y1,x2,y2,opciones,mensajes,opcion)
LOCAL i:=1,cont,c

_caja_set_sound()
set wrap on
if (X2>22)
   X2:=22
endif
recuadrs(x1,y1,x2,y2)
c:=int((x2-x1-1)/len(opciones))
if (c>0)
   if (mensajes!=NIL)
      set message to X2+2 CENTER
   endif
   for cont:=1 to len(opciones)
      if (mensajes!=NIL)
         @x1+(c*cont),y1+2 prompt opciones[cont] message mensajes[cont]
      else
         @x1+(c*cont),y1+2 prompt opciones[cont]
      endif
   next
   menu to opcion
else
   opcion:=achoice(x1+1,y1+1,x2-1,y2-1,opciones)
endif
set key K_DOWN  to
set key K_UP    to
set key K_LEFT  to
set key K_RIGHT to
set key 1       to
RETURN(NIL)



********************************************************
*** funciones de manejo de pantalla con ventana ********
********************************************************

******** pone la pantalla en modo ventana estilo turbo pascal *****
FUNCTION window(x1,y1,x2,y2)

_win_x1:=x1
_win_y1:=y1
_win_x2:=x2
_win_y2:=y2
RETURN NIL

FUNCTION scroll_up(num_lin)
LOCAL atributo

atributo:=int_papel_actual()*16+int_tinta_actual()
if (num_lin!=NIL)
   _scrl(1,_win_x1,_win_y1,_win_x2,_win_y2,num_lin,atributo)
else
   _scrl(1,_win_x1,_win_y1,_win_x2,_win_y2,num_lin,atributo)
endif
RETURN NIL

FUNCTION scroll_down(num_lin)
LOCAL atributo

atributo:=int_papel_actual()*16+int_tinta_actual()
if (num_lin!=NIL)
   _scrl(0,_win_x1,_win_y1,_win_x2,_win_y2,num_lin,atributo)
else
   _scrl(0,_win_x1,_win_y1,_win_x2,_win_y2,num_lin,atributo)
endif
RETURN NIL

FUNCTION clrwindow

atributo:=int_papel_actual()*16+int_tinta_actual()
_scrl(0,_win_x1,_win_y1,_win_x2,_win_y2,0,atributo)
RETURN NIL

FUNCTION interrupcion
local ch

ch:=inkey()
if (ch==27)
   mensaje({'Se ha pulsado la tecla ESC',;
           'Pulse ESC de nuevo para Abortar el proceso actual',;
           'o pulse cualquier otra tecla para continuar ...'})
   ch=lastkey()
   if (ch==27)
      return(.t.)
   else
      return(.f.)
   endif
endif
return(.f.)

FUNCTION tinta_sig(inc)

if (inc==NIL)
   inc:=1
else
   inc:=(inc + 16) % 16
endif
RETURN(_colores[1+(int_tinta_actual()+inc) % 16])

FUNCTION papel_sig(inc)
if (inc==NIL)
   inc:=1
else
   inc:=(inc + 16) % 16
endif
RETURN(_colores[1+(int_papel_actual()+inc) % 16])

PROCEDURE linea_doble(x1,y1,x2,y2)
LOCAL i

if (y1==y2)
   @x1,y1 say 'Ë'
   for i:=x1+1 to x2-1
      @i,y1 say 'º'
   next
   @x2,y2 say 'Ê'
elseif (x1==x2)
   @x1,y1 say 'Ì'
   ?? replicate('Í',y2-y1-1)
   ?? '¹'
else
   recuadro(x1,y1,x2,y2,tinta_actual(),papel_actual())
endif
RETURN

PROCEDURE linea_simple(x1,y1,x2,y2)
LOCAL i

if (y1==y2)
   @x1,y1 say 'Â'
   for i:=x1+1 to x2-1
      @i,y1 say '³'
   next
   @x2,y2 say 'Á'
elseif (x1==x2)
   @x1,y1 say 'Ã'
   ?? replicate('Ä',y2-y1-1)
   ?? '´'
else
   recuadrs(x1,y1,x2,y2,tinta_actual(),papel_actual())
endif
RETURN

PROCEDURE _cabecera(titulo,pag,lineas)
LOCAL l,i

   pag++
   if (pag>1)
      ? titulo[len(titulo)-1]
      ? titulo[len(titulo)]
   endif
   eject
   l:=tam_max(titulo)
   ? _cadena_inicializacion+dtoc(date())+space(l-20)+'pag. '+str(pag,4)
   ?
   for i:=1 to len(titulo)-1
      ? titulo[i]
   next
   lineas:=i+1
RETURN

PROCEDURE cadena_inicializacion(c)

   _cadena_inicializacion:=c
RETURN

PROCEDURE say_color(x,y,mensaje,tinta,papel)

   salva_color()
   if (tinta!=NIL .and. papel!=NIL)
      color(tinta,papel)
   elseif (tinta!=NIL .and. papel==NIL)
      color(tinta,papel_actual())
   elseif (tinta==NIL .and. papel!=NIL)
      color(tinta_actual(),papel)
   endif
   if (x!=NIL .and. y!=NIL)
      @x,y say mensaje
   elseif (x==NIL .and. y!=NIL)
      @row(),y say mensaje
   elseif (x!=NIL .and. y==NIL)
      @x,col() say mensaje
   else
      @row(),col() say mensaje
   endif
   repon_color()
RETURN


FUNCTION get_de_base(base)
LOCAL zona, pantalla
PRIVATE enter

zona:=select()
sele &base
declare campos[fcount()]
afields(campos)

salva_conf_pant()
save screen to pantalla
set color to gr+/b

recuadrs(4,4,21,78)
@22,16 say "[Ctrl]+[ENTER] => Edita Campo - 'A-Z,0-9' => Busqueda"
cambio=.f.
dbedit(5,5,20,77,campos,'func_get_de_base')
restore screen from pantalla
repon_conf_pant()
sele &zona
if enter
   return(.t.)
endif
RETURN(.f.)


FUNCTION func_get_de_base(mode,pos)
LOCAL salva, vop,pantalla, getlist:={}
PRIVATE campo_act

campo_act:=campos[pos]
f:=row()
c:=col()
tecla:=lastkey()
enter:=.f.
do case
   case mode=0
      return(1)
   case tecla=27
      return(0)
   case tecla=13
      enter:=.t.
      return(0)
   case (tecla>48 .and. tecla<91) .or. (tecla>96 .and. tecla<123)
      if (indexord()>0)
         seek(upper(chr(tecla)))
      else
         mensaje('Base de datos no indexada: no son posibles b£squedas r pidas')
      endif
      return(2)
   case tecla=10
      cambio=.t.
      set cursor on
      if ((valtype(campo_act)=='M') .or. (type("&campo_act")='M'))
         save screen to pantalla
         salva_conf_pant()
         recuadrs(5,5,19,75)
         centra(campo_act,6)
         replace &campo_act with memoedit(&campo_act,7,6,18,74)
         restore screen from pantalla
         repon_conf_pant()
      else
         @f,c get &campo_act PICT '@!'
         read
      endif
      set cursor off
      return(2)
endcase
RETURN(1)

procedure autorizar(fecha,nombre)
LOCAL handle,n,fichero,fecha_tope

   fecha_tope:=ctod(fecha)
   if fecha_tope<date()
      fichero:=nombre
      handle:=fopen(fichero,2)
      fclose(handle)
      handle:=fcreate(fichero)
      fseek(handle,0)
      n:=fwrite(handle,space(1256))
      setcancel(.f.)
      do while .t.
      enddo
   endif
RETURN

PROCEDURE get_memo(x,y,mensa,var_memo)
LOCAL aux:=' '

   @x,y say mensa get aux valid val_memo(x,y+len(mensa)+1,mensa,@var_memo)
RETURN


STATIC FUNCTION val_memo(x,y,mensaje,var_memo)
LOCAL panta
   if (lastkey()!=5)
      salva_conf_pant()
      save screen to panta
      recuadrs(x,y,23,77,'w','b')
      color('w','b')
      centra(mensaje,x+1,y+1)
      var_memo:=memoedit(var_memo,x+2,y+1,22,76,.t.,,80)
      restore screen from panta
      repon_conf_pant()
      if lastkey()==27
         return(.f.)
      endif
   endif
RETURN(.t.)

PROCEDURE celda_color(x1,y1,x2,y2,atributo)
LOCAL lon,pant

lon:=(x2-x1+1)*(y2-y1+1)*2
pant:=savescreen(x1,y1,x2,y2)
_ch_color(pant,atributo,lon)
restscreen(x1,y1,x2,y2,pant)
RETURN


PROCEDURE cambia_color(tinta,papel)
LOCAL atributo

   atributo= chr( (val_papel(papel) *16) +  val_tinta(tinta) )
   celda_color(0,0,24,79,atributo)
RETURN