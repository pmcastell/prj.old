procedure editar
LOCAL opcion,temp, read_exit,pantalla
PARAMETERS base, indice

commit
set date british
set scoreboard off
set talk off
set echo off
set wrap on
read_exit:=readexit()
do while .t.
   color('W','B')
   indexado=.f.
   borrar('W','B')
   if (base==NIL)
      n:=adir('*.dbf')
      DECLARE dire[n]
      ADIR('*.dbf',dire)
      *clear
      *for x=1 to n
      *  @x,40-len(dire[x])/2  prompt dire[x]
      *next
      borrar()
      if ((temp:=(n/2))>10)
         temp:=10
      endif
      recuadrs(12-temp,29,14+temp,49)
      opcion:=achoice(13-temp,32,13+temp,48,dire)
      if (opcion==0)
         CLEAR
         RETURN
      endif
      base=dire[opcion]
   endif
   base:=upper(base)
   if (right(base,4)<>'.DBF')
      base=base+'.DBF'
   endif
   CLEAR
   if (indice==NIL)
      resp=' '
      indice=space(8)
      do while .not. resp$'SN'
         @10,12 SAY '¨Est  el fichero indexado?' get resp pict '!'
         indexado=.T.
         read
      enddo
      indice1='        '
      ext='NTX'
      if resp='S'
         if file("&indice1.ntx")
            use &base index &indice1 EXCLUSIVE
            if neterr()
               mensaje('ERROR: Fichero '+base+' en uso por otro usuario')
               CLEAR
               RETURN
            endif
         else
            @13,12 say 'Nombre del fichero de ¡ndice: 'get indice1
            @row(),col() say '.'
            @row(),col() get ext
            read
            if lastkey()=27
               clear
               quit
            endif
            ext:=alltrim(ext)
            nomb_indice=alltrim(indice1)+iif(ext<>space(3),'.'+ext,ext)
            use &base index &nomb_indice EXCLUSIVE
            if neterr()
               mensaje('ERROR: Fichero '+base+' en uso por otro usuario')
               CLEAR
               RETURN
            endif
         endif
      else
         use &base
         if neterr()
            mensaje('ERROR: Fichero '+base+' en uso por otro usario')
         endif
      endif
   endif
   if (base!=NIL .and. indice!=NIL)
      if indice<>''
         indice:=upper(indice)
         if (right(indice,4)<>'.NTX')
            indice:=indice+'.NTX'
         endif
         if (file(base) .and. file(indice))
            use &base index &indice
         else
            mensaje('Error: al abrir base: '+base+' indice: '+indice)
            return
         endif
      else
         use &base
         if neterr()
            mensaje('ERROR: Fichero '+base+' en uso por otro usario')
         endif
      endif
   endif
   clear
   cuadrs(0,0,24,79)
   linea_simple(20,0,20,79)
   cuadrs(20,68,22,79)
   @20,68 say 'Â'
   @22,79 say '´'
   for l=1 to 2
      @20+l,1 say replicate(' ',67)
   next
   @23,1 say replicate(' ',78)
   @21,11 say '[Intro] => Escribir -                 - [Esc] => Salir'
   @21,11 say '[Intro] => Escribir - [Supr] => Borrar - [Esc] => Salir'
   @22,1 say '[Ctrl]+P => Empaquetar - Ir Final para A¤adir - [Ctrl]+O => Ordenar'
   @23,11 say '[Ctrl]+L => Localizar'
   declare campos[fcount()]
   afields(campos)
   readexit(.t.)
   dbedit(1,1,19,78,campos,'funcion')
   readexit(read_exit)
   base:=NIL
   indice:=NIL
enddo
CLEAR
return


FUNCTION funcion
LOCAL ultima_tecla
PARAMETERS mode,pos
PRIVATE campo_act, salva, vop

set cursor on
f=row()
c=col()
@21,69 say 'Reg: '+str(recno(),5)
if deleted()
   @23,69 say 'Borrado'
else
   @23,69 say '       '
endif
campo_act=campos[pos]
ultima_tecla:=lastkey()
do case
   case mode=0
      return(1)
   case mode=2
      tone(2000,7)
      salva=savescreen(10,30,12,47)
      vop=' '
      set confirm off
      @23,32 say 'A¤adir (S/N) = >' get vop picture '!' valid(vop$'SN')
      read
      @23,1 say replicate('Û',68)
      set confirm on
      @23,11 say '[Ctrl]+L => Buscar'
      restscreen(10,30,12,47,salva)
      if vop='S'
         append blank
      endif
      return(1)
   case mode=3
      tone(100,7)
      @23,15 say 'Fichero vac¡o Pulse cualquier tecla para salir'
      inkey(0)
      return(0)
   case ultima_tecla=27
      return(0)
   case (ultima_tecla=13 .or. (ultima_tecla>32 .and. ultima_tecla<256))
      /*if valtype(campo_act)='N'
         c=c+1   antes no funcionaba bien, ahora si
      endif*/
      do while (.not. rlock())
         mensaje('Intentando bloqueo del Registro',,,0.2)
      enddo
      keyboard(chr(lastkey()))
      if ((valtype(campo_act)=='M') .or. (type("&campo_act")='M'))
         salva_conf_pant()
         save screen to pantalla
         recuadrs(3,3,21,77,'w','b')
         centra(campo_act,4)
         replace &campo_act with memoedit(&campo_act,5,4,20,76,.t.)
         restore screen from pantalla
         repon_conf_pant()
      else
         @f,c get &campo_act
      end
      read
      return(1)
   case ultima_tecla=7
     tone(1000,2)
     if .not. deleted()
        delete record recno()
     else
        recall record recno()
     endif
     return(2)
   case ultima_tecla=16
     pack
     keyboard chr(30)
     return(2)
   case ultima_tecla=15
        sort on &campo_act to temp.dbf
        close database
        delete file &base
        rename temp.dbf to &base
        use &base
        return(2)
   case ultima_tecla=12
        clear gets
        parametro=&campo_act
        if type('&campo_act')='N'
           parametro=0
        else
           parametro=space(len(parametro))
        endif
        @23,1 say replicate('Û',68)
        set intensity off
        @23,5 say '&campo_act. a localizar:' get parametro picture '@!'
        read
        if type('&campo_act')<>'N'
           parametro=alltrim(parametro)
           l=len(alltrim(parametro))
           if ((indexado) .and. (indexkey(1)=campo_act))
              seek(parametro)
           else
              locate for subst(&campo_act,1,l)=parametro
           endif
        else
           if ((indexado) .and. (indexkey(1)=campo_act))
              seek(parametro)
           else
              locate for &campo_act=parametro
           endif
        endif
        set intensity on
        if eof()
           @23,1 say replicate('Û',68)
           @23,2 say '&parametro. no encontrado'
           inkey(4)
        endif
        @23,1 say replicate('Û',68)
        @23,11 say '[Ctrl]+L => Buscar'
        return(1)
endcase
RETURN(2)

/*FUNCTION funcion2
parameters mode,pos
private campo_act, salva, vop
f=row()
c=col()
@21,69 say 'Reg: '+str(recno(),5)
if deleted()
   @23,69 say 'Borrado'
else
   @23,69 say '       '
endif
campo_act=campos[pos]
ultima_tecla:=lastkey()
do case
case mode=0
   return(1)
case mode=3
   tone(100,7)
   @23,15 say 'Fichero vac¡o Pulse cualquier tecla para salir'
   inkey(0)
   return(0)
case ultima_tecla=27
   return(0)
case ultima_tecla=13
   if type('&campo_act')='N'
      c=c+1
      @f,c get &campo_act PICT '@!'
      read
   endif
   return(1)
endcase
RETURN(2)*/


PROCEDURE mayusculas(c)
LOCAL i

if (c!=NIL)
   for i:=1 to len(c)
      reemplazac(c,upper(subst(c,i,1)),i)
   next
endif
RETURN
