if [ "$1" = "" ]; 
then 
   echo Uso: $0 comando-a-ejecutar parametros;
   exit 1;

fi
#until [ -z "$1" ];
#do
#   #echo until: $LINEA
#   LINEA="$LINEA $1"
#   shift
#done
LINEA="$*"
if [ "$(param -c $*)" != "" ];
then
   LIMITE=$(param -c $*)
   shift
   shift
   LINEA=$*
#   LINEA1=$(echo $LINEA | awk -F'-c' '{print $1;}')
#   LINEA2=$(echo $LINEA | awk -F'-c' '{print $2;}')
#   LINEA2=$(echo $LINEA2 | awk -F"$LIMITE " '{print $2;}')
#   LINEA=$LINEA1$LINEA2
else
   LIMITE=999999999999999999
fi
i=1    
while [ $i -le $LIMITE ]; 
do 
   #echo bash -c "$LINEA"
   bash -c "$LINEA"
   if [ $? -gt 0 ];
   then
      echo .;
   fi
   #echo bucle terminado | festival --tts;
   habla -n bucle terminado &> /dev/null
   sleep 1;
   i=$(expr $i + 1)
done


