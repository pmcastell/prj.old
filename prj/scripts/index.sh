SAVEIFS=$IFS
IFS=$(echo -en "\n\b")
#for f in $*
#do
#  echo "$f"
#done
if [ "$1" != "" ];
then
   DIR=$1
else
   DIR=.
fi      
if [ "$2" != "" ];
then
   FICH_SALIDA=$2
else
   FICH_SALIDA=index.html
fi      

if [ -f $DIR/$FICH_SALIDA ];
then 
   echo Fichero $DIR/$FICH_SALIDA ya existe
   echo '¿Sobreescribir?(S/N)'
   read RESP
   if [ "$RESP" != "s" -a "$RESP" != "S" ];
   then
      echo No se ha indexado
      exit 1
   fi
fi
NUM_CELDAS_FILA=3
echo '<html>' > $DIR/$FICH_SALIDA
echo '   <head>' >> $DIR/$FICH_SALIDA
echo '   </head>' >> $DIR/$FICH_SALIDA
echo '   <body>' >> $DIR/$FICH_SALIDA
echo '      <table border="1" cellpadding="5" cellspacing="5" style="margin-left:auto; margin-right:auto; background-color:lightblue;">' >> $DIR/$FICH_SALIDA
echo '         <tr><th colspan="'$NUM_CELDAS_FILA'">Listado de Ficheros</td></tr>' >> $DIR/$FICH_SALIDA

i=1
for f in $(find $DIR);
do
   if [ $i -eq 1 ];
   then
      echo '         <tr>' >> $DIR/$FICH_SALIDA
   fi
   #echo '<td><a href="'$(basename $f)'">'$(basename $f)'</a></td>' >> $DIR/$FICH_SALIDA
   echo '<td><a href="'$f'">'$f'</a></td>' >> $DIR/$FICH_SALIDA
   
   if [ $i -eq $NUM_CELDAS_FILA ];
   then
      echo '         </tr>' >> $DIR/$FICH_SALIDA
      i=0
   fi
   i=$(expr $i + 1)
done   
echo '      </table>' >> $DIR/$FICH_SALIDA
echo '   </body>' >> $DIR/$FICH_SALIDA
echo '</html>' >> $DIR/$FICH_SALIDA
IFS=$SAVEIFS

