{$M 65000,0,60000}
program pracom;

uses crt,tw,dos,ventanas,mimou,deg,sounds;


const  (* regtros. de la UART *)
      TX=$F8;
      RX=$F8;
      DLSB=$F8;
      DMSB=$F9;
      IER=$F9;
      IIR=$FA;
      LCR=$FB;
      MCR=$FC;
      LSR=$FD;
      MSR=$FE;

      FRAMMING_ERROR=1;
      FATAL_ERROR=2;
      OVERRUN_ERROR=3;
      PARITY_ERROR=4;

      COM1=$0C;
      COM2=$0B;

      TALK_R=255;
      FTP_R=254;
      TALK_OK=253;
      FTP_OK=252;
      NO_COM=251;

      TIEMPO_ESPERA=800000;


type emi_rec = (EMISOR, RECEPTOR);

VAR COMINT:byte;
    fin_talk:boolean;
    ADDRRX, ADDRTX, ADDRIIR, ADDRIER, ADDRMCR, ADDRDLSB, ADDRDMSB,
    ADDRLSR, ADDRLCR ,ADDRMSR:word;
    RXX,RXY,TXX,TXY:byte;
    TCOM1,TCOM2:pointer;

    comunicacion,rechazada,habilitada_comunicacion: boolean;
    lnop:lnwin;
    opciones:winop;
    opci:byte;




     (***** INTERRUPCIONES ******************************)

     {$F+}

     procedure dibuja_menu;FORWARD;
     procedure dibuja_scr_talk;FORWARD;
     function error_UART: byte;FORWARD;
     procedure talk(estado: emi_rec);FORWARD;
     procedure envia_caracter;FORWARD;
     procedure trata_recibido;FORWARD;
     procedure trata_error;FORWARD;
     procedure rechazada_comunicacion;FORWARD;
     procedure UART_inicial;FORWARD;


     procedure ring;
     var f:byte;
     begin
        for f:=1 to 8 do begin
           soundrise(2100,2200,1);
           delay(10);
        end;
        delay(400);
        for f:=1 to 8 do begin
           soundrise(2100,2200,1);
           delay(10);
        end;
     end;

     procedure elim_pendientes;
     var b,bb:byte;
     begin
        b:=port[ADDRIIR];
        while b<>$01 do begin
        if b and $06 = $06 then begin (* Hubo algun error *)
           bb:=port[ADDRLSR];
        end;
        if b and $04 = $04 then begin (* Se recibi¢ dato *)
           bb:=port[ADDRRX];
        end;
        if b and $02 = $02 then begin (* Buffer vacio *)
           bb:=port[ADDRIIR];
        end;
        b:=port[ADDRIIR];
     end;
     end;



     procedure voidint;interrupt;
     begin
     end;

     procedure detectora; interrupt;
     VAR codigo,CONTIIR,bb:byte;
     begin
          CONTIIR:=port[ADDRIIR];
          while CONTIIR<>$01 do begin
              case CONTIIR of
                 $4:begin                           (* Hay caracter recibido *)
                       codigo:=port[ADDRRX];
                       elim_pendientes;
                       port[$20]:=$20;
                       if habilitada_comunicacion then begin
                          case codigo of
                             TALK_R: talk(RECEPTOR);
                             {FTP_R: ftp(RECEPTOR);}
                          end;
                       end else begin
                          port[ADDRTX]:=NO_COM;
                       eaàáàá 	¤¤ºv@@@@@@@@@@@@@@@PT@¦Ê@àêÊÈÊ@ÊÜìÒÂä@ÈÂèÞ@TR                 $6:bb:=port[ADDRLSR];               (*   Hubo algun error   *)
                 $0:bb:=port[ADDRMSR];
              end;
              CONTIIR:=port[ADDRIIR];
          end;
          port[$20]:=$20;
     end;

     procedure ack_com;interrupt;
        VAR codigo,b,bb: byte;
     begin
          b:=port[ADDRIIR];
          while b<>$01 do begin
             case b of
                $4:begin
                      codigo:=port[ADDRRX];
                      case codigo of
                         FTP_OK, TALK_OK: comunicacion:=TRUE;
                         NO_COM:rechazada_comunicacion;
                      end;
                    end;
                $2:bb:=port[ADDRIIR];               (* Se puede enviar dato *)
                $6:bb:=port[ADDRLSR];               (*   Hubo algun error   *)
             end;
             b:=port[ADDRIIR];
          end;
          port[$20]:=$20;
     end;

     procedure talkint;interrupt;
     var b:byte;
     begin
          b:=port[ADDRIIR];
          while b<>$01 do begin
             port[$20]:=$20;
             case b of
                $2: envia_caracter;
                $4: trata_recibido;
                $6: trata_error;
             end;
             b:=port[ADDRIIR];
          end;
          port[$20]:=$20;
     end;

     (**** FIN INTERRUPCIONES *******************************)

     procedure rechazada_comunicacion;
     var opciones:winop;
     begin
        setintvec(COMINT,addr(voidint));
        rechazada:=TRUE;
        window(1,1,80,25);
        opciones[0]:='Comunicacion rechazada';
        opciones[1]:='  El PC Remoto esta rechazando';
        opciones[2]:='sus intentos de comunicaci¢n.';
        opciones[3]:='  Intentelo en otro momento.';
        openacwin(22,10,opciones,3,YELLOW,RED,#244+#224);
     end;

     procedure dibuja_menu;
     var dd:deco;
     begin
          hiddenmouse;
          dd[1]:='ÍÎÎÎÎÎÎÏ';
          dd[2]:='Ë  âã  Ì';
          dd[3]:='ËPracomÌ';
          dd[4]:='Ë v1.0 Ì';
          dd[5]:='ÙÚÚÚÚÚÚÛ';
          backdeco(dd,5,lightcyan,cyan);
          lnop[1].titop:='Configuracion';
                lnop[1].numop:=3;
                lnop[1].winop[1]:='Config. UART';
                lnop[1].winop[2]:=LINESEP;
                lnop[1].winop[3]:='DOS Shell';
          lnop[2].titop:='Comunicacion';
                lnop[2].numop:=3;
                lnop[2].winop[1]:='Talk';
                lnop[2].winop[2]:='Ftp';
                lnop[2].winop[3]:='Impr. remota';
          lnop[3].titop:='Miscelanea';
                lnop[3].numop:=6;
                lnop[3].winop[1]:='Color tinta';
                lnop[3].winop[2]:='Color fondo';
                lnop[3].winop[3]:=LINESEP;
                lnop[3].winop[4]:='Cambiar fuente';
                lnop[3].winop[5]:=LINESEP;
                lnop[3].winop[6]:='Sobre Pracom...';
          lnwinmenu(6,3,74,22,lnop,3,white,blue);
          showmouse;
     end;

     procedure dibuja_scr_talk;
     begin
        charbackdeco(' ',lightcyan,cyan);
        twinboxnshadow(2,2,80,12,'PC Remoto',white,bî àá    8#   àáàáàá  		 	  <	àá<	 àáàá  	 <33	$$	 àá  0àá  àá		 	  <	 0		 ! 0		 	àá		 	  <	<	$ 0		 	àá		 	  <	1	<  $!0 0		 	àáelse
           error_UART := FATAL_ERROR;
        end;
     end;

     procedure talk(estado: emi_rec);
        VAR b,c:byte;
            tiempo:longint;
     begin
          RXX:=4;
          RXY:=4;
          TXX:=4;
          TXY:=16;
          tiempo:=0;
          comunicacion:=FALSE;
          rechazada:=FALSE;
          dibuja_scr_talk;
          window(3,15,78,23);
          if estado = receptor then begin
             port[ADDRTX]:=TALK_OK;
             ring;
          end else begin
              setintvec(COMINT,addr(ack_com));
              port[ADDRTX]:= TALK_R;
              while (tiempo<TIEMPO_ESPERA) and (not comunicacion) and (not rechazada) do
                 inc(tiempo);
              comunicacion:=FALSE;
          end;
          window(1,1,80,25);
          if not rechazada then begin
             if (tiempo<TIEMPO_ESPERA) then begin
                dibuja_scr_talk;
                textcolor(red);
                textbackground(white);
                window(3,3,78,11);
                clrscr;
                textcolor(blue);
                textbackground(white);
                window(3,15,78,23);
                clrscr;
                normal;
                setintvec(COMINT,addr(talkint));
                fin_talk:=FALSE;
                while not fin_talk do begin
                   while (not keypressed) and (not fin_talk) do;
                   if not fin_talk then
                      envia_caracter;
                end;
                no_cursor;
             end else begin
                opciones[0]:='Error en respuesta ';
                opciones[1]:='  No se ha obtenido respuesta';
                opciones[2]:='del sistema remoto.';
                opciones[3]:='  Intentelo en otro momento.';
                openacwin(22,10,opciones,3,YELLOW,RED,#226+#227);
             end;
          end;
          (* Restablece condiciones iniciales *)
          window(1,1,80,25);
          setintvec(COMINT,addr(voidint));
          dibuja_menu;
          setintvec(COMINT,addr(detectora));
          no_cursor;
     end;

     procedure envia_caracter;
     var ch:char;
     begin
        if not fin_Talk then
           ch:=readkey;
        textcolor(blue);
        asm cli end;
        if ch=#27 then begin
           fin_talk:=true;
           port[ADDRTX]:=27;
        end else begin
           port[ADDRIER]:=0;
           if ch=#13 then begin
              writeln;
              port[ADDRTX]:=10;
              port[ADDRTX]:=13;
           end else begin
              if ch<'¤' then begin
                 gotoxy(TXX,TXY);
                 write(ch);
                 port[ADDRTX]:=ord(ch);
              end;
           end;
           TXX:=wherex;
           TXY:=wherey;
           port[ADDRIER]:=7;
        end;
     end;

     procedure trata_recibido;
     var ch:char;
     begin
        window(3,3,78,11);
        gotoxy(RXX,RXY);
        textcolor(red);
        ch:=chr(port[ADDRRX]);
        if ch=#27 then
           fin_talk:=true
        else
           if ch<>#13 then
              write(ch)
           else
              writeln;
        if ch=#27 then
           fin_talk!	 àá	 	àá	 	àá   àá  àáàáàá	  		 		 	àá	àáàá		 	  <	 àá1	<  $!0 0		 	àáàáàáàá	  	 <	 àáàá	 ,b,bb:byte;
         pos:word;
         CIIR:Byte;
     begin
        write('Que com:');
        read(tipo);
        if tipo=1 then begin
           pos:=$300;
           COMINT:=COM1;
        end else begin
           pos:=$200;
           COMINT:=COM2;
        end;
        ADDRRX:=RX+pos;
        ADDRTX:=TX+pos;
        ADDRIIR:=IIR+pos;
        ADDRIER:=IER+pos;
        ADDRMCR:=MCR+pos;
        ADDRDLSB:=DLSB+pos;
        ADDRDMSB:=DMSB+pos;
        ADDRLSR:=LSR+pos;
        ADDRMSR1 	 àáàá 33#<9àáàá 	 <33	#8	 àáàá 	 <33	3#9    àá 	 <33	3 9 àáàáàá$   			  àáàá8  $!	 	@@@@@@@@PTTTTTTT@ÆÞÜÌÒÎêäÂÆÓDÜ@ÈÊ@ØÂ@ÈÊ@ØÂ@ª¤¨@TTTTTTR@@@@@@@@àÞäè¶¤¤ºtzH`nv@PT@àÞÜÊÚÞæ@z`@TR@@@@@@@@@@@àÞäè¶¤¤ºtzH`nv@PT@```a 0111  Habilitacion de interrupciones *)
           port[ADDRMCR]:=port[ADDRMCR] or $08;  (*   OUT2=1  *)

        (***** Desabilitamos interrupciones pendientes****)

        Elim_pendientes;

        (************** OCW's 8259 PIC **************)

        port[$20]:=$20;                  (* EOI generico  *)
        port[$20]:=$0B;                  (* leemos el IRR *)

        if tipo=1 then begin
           port[$21]:=port[$21] and $EF;    (* Habilitamos la IRQ4 *)
        end else begin
           port[$21]:=port[$21] and $F7;    (* Habilitamos la IRQ3 *)
        end;
     end;

procedure sel_color_tinta;
var c:byte;
begin
   setwcolor(opencolorwin(10,5,15,'Seleccion color tinta',white,magenta));
end;

procedure sel_color_fondo;
var c:byte;
begin
   setwbkcolor(opencolorwin(10,7,7,'Seleccion color fondo',white,magenta));
end;

procedure cambia_fuente;
begin
   if winfont(openfilewin(50,6,'*.CHS',10)) then;
end;

procedure sobre_esto;
var op:winop;
begin
   op[0]:='Sobre Prah  àá   0	 	  	àá     8    1 àá   $ 	  àá   àá  	  	àá   àá   	  	31	1!8	 <#3 àá   0##  	03! $8 !àáàáàá	  	  	 àá	àáàá   8    ?    ÂÆÒon,cuadro,habilitada_comunicacion);
   dialogue(12,6,1,d);
end;

procedure dos_shell;
var p:paleta;
    t:^tempscr;
    old_prompt,new_promo	àáàá àá  àá àá  	  	  àá  	  	   	<8     	  àá     	  àá	<9<! àá  		àá 	   àá  àá		àá	<		$9<àáÊðÊÆPÎÊèÊÜìPN¦ NRXNNRv@@@ÈÊÎäÂÈÃ(ABAJO,p,50);
   textwinmode;
   move(t^,pantalla,4000);
   degrada(ARRIBA,p,50);
   dispose(t);
end;

{$F-}

begin
   getintvec(COM1,TCOM1);
   getintvec(COM2,TCOM2);

   DIRECTVIDEO:=true;

   swapvectors;
   opci:=0;
   habilitada_comunicacion:=true;
   textwinmode;

   UART_inicial;

   setwcolor(black);
   setwbkcolor(white);
   while opci<>NONE do begin
      window(1,1,80,25);
      clrscr;
      charbackdeco(#181,white,cyan);
      dibuja_menu;
      opci:=readlnwin(6,3,74,22 àá  àá  	 àá  àá 0 $ 	àá   	 àáÀ@@@@@@@@fdtæÊØ¾ÆÜ=É`D L~~~~~~~~~"`p dp D @B~~~~~~~~~"  d$@ @ ~~~~~|@N~~|@N~~|@ `  B~~|  p<@` $"~~| @@bà2à2~~| @@bà&à&@N