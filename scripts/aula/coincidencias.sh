user() {
   echo $* | awk -F'students/' '{print $2;}' | awk -F'/' '{print $1;}'
}   
md5() {
   echo $* | awk '{print $1;}'
}
crearListaTrabajos() {
   EXTENSIONES="\.ods$|\.xls$|\.xlsx$"
   EXTENSIONES="\.ods$|\.xls$|\.xlsx$|\.svg$"
   EXTENSIONES="\.odp$|\.pdf$"
   EXTENSIONES=".svg$"
   sudo find /net/server-sync/home/students/ | grep -iE "(${EXTENSIONES})" > $TEMP
}
crearListaMd5Unica() {
   TEMP2=$(tempfile)
   TEMP3=$(tempfile)
   if [ -f $ENTRADA ]; then rm $ENTRADA; fi
   while read FICH; do 
      sudo md5sum "$FICH" >> $ENTRADA
   done < $TEMP
   cat $ENTRADA | sort  > $TEMP2
   cat $TEMP2 | uniq > $TEMP3
   rm $ENTRADA
   rm $TEMP2
   mv $TEMP3 $ENTRADA
}   
DEBUG=0  
   
TEMP=/tmp/listaFicheros.txt   
ENTRADA=/tmp/listaFicherosmd5.sort.txt
[ "$DEBUG" = 1 ] && ENTRADA=/tmp/pruebas.txt
SALIDA=/tmp/coincidencias.txt

crearListaTrabajos
crearListaMd5Unica
   
if [ -f $SALIDA ]; then rm $SALIDA; fi
NLIN=1;
(read LINEA1 
while [ "$LINEA1" != "" ]; do
   [ "$DEBUG" = 0 ] && printf "\rLinea Actual Procesada: $NLIN"
   MD51=$(md5 $LINEA1)
   USU1=$(user $LINEA1)
   read LINEA2; NLIN=$(($NLIN+1));
   MD52=$(md5 $LINEA2)
   USU2=$(user $LINEA2)
   [ "$DEBUG" = 1 ] && echo echo LINEA1: $LINEA1
   [ "$DEBUG" = 1 ] && echo echo LINEA2: $LINEA2
   [ "$DEBUG" = 1 ] && read </dev/tty
   if [ "$MD51" != "$MD52" ]; then LINEA1=$LINEA2; continue;
   else
      #echo $LINEA >> $SALIDA
      while [ "$MD51" = "$MD52" ]; do
         if [ "$USU1" != "$USU2" ]; then #"$LINEA1" != "$LINEA2" -a 
            if [ "$LINEAP" != true ]; then echo $LINEA1>> $SALIDA; LINEAP=true; fi
            echo $LINEA2 >> $SALIDA; 
            USU1=$USU2
         fi
         read LINEA2; NLIN=$(($NLIN+1));
         [ "$DEBUG" = 1 ] && echo echo LINEA2: $LINEA2
         [ "$DEBUG" = 1 ] && read < /dev/tty
         MD52=$(md5 $LINEA2)
         USU2=$(user $LINEA2)
      done
      LINEA1=$LINEA2
      LINEAP=false
   fi
   echo ---------------------------------------------------------------------------------------------- >> $SALIDA
done) < $ENTRADA

#sudo rm $TEMP

#               echo $LINEA
#               echo $LINEA2
#               echo $USU1: $USU2
#               echo $MD51: $MD52
#               exit

