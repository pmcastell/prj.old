uso() {
   echo Uso: $0 '[[-p] <directorio-origen-a-comprimir>]'
   exit 1
}   
if [ "$1" = "-p" ] && [ "$2" != "" ]; then PASSWD=$(readPass); ORIGEN=$2; elif [ "$1" = "-p" ] && [ "$2" = "" ]; then uso; 
elif [ "$1" = "" ]; then ORIGEN=.; elif [ "$1" = "-h" ]; then uso; else ORIGEN=$1; fi 
DESTINO=./$(pwd | gawk -F'/' '{print $NF;}').7z
if [ "$PASSWD" != "" ]; then PASSWD="-p${PASSWD}"; fi
if [ "$(echo $* | grep -i '\-nv' | grep -v grep)" != "" ];
then
   eecho nice -19 7za a $DESTINO -w./ -mhe $PASSWD -r -ms=off -mx=9 $ORIGEN
else   
   eecho nice -19 7za a $DESTINO -w./ -mhe $PASSWD -r -ms=off -mx=9 -v10m $ORIGEN
fi   
#mv $DESTINO ./

