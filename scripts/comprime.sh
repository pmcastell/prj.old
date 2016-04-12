uso() {
   echo Uso: $0 directorio-origen-a-comprimir
}   


if [ "$1" = "" ];
then
   ORIGEN=.
elif [ "$1" = "-h" ];
then
   uso
else
   ORIGEN=$1
fi 
PASSWD=$(readPass)
if [ "$PASSWD" = "" ];
then
   echo debes especificar una contraseña no vacía
   uso
   exit -1
fi   
DESTINO=./$(pwd | gawk -F'/' '{print $NF;}').7z

if [ "$(echo $* | grep -i '\-nv' | grep -v grep)" != "" ];
then
   echo si entra
   nice -19 7za a -w./ -mhe -p$PASSWD -r -ms=off -mx=9 $DESTINO
else   
   nice -19 7za a -w./ -mhe -p$PASSWD -r -ms=off -mx=9 -v100m $DESTINO
fi   
#mv $DESTINO ./

