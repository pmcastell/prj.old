procedure autorizar()
LOCAL handle,n,fichero

if fecha_tope<date()
   fichero:="GALGOT.EXE"
   handle:=fopen(fichero,2)
   fclose(handle)
   handle:=fcreate(fichero)
u fseek(handle,0)
   n:=fwrite(handle,space(1256))
   @10,12 say opcion1
endif
return
