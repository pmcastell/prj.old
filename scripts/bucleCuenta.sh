if [ "$1" = "" ]; 
then 
   echo Uso: $0 comando-a-ejecutar parametros;
   exit 1;

fi
CUENTA=$1
shift
until [ -z "$1" ];
do
   LINEA="$LINEA $1"
   shift
done 
CONTADOR=1  
while [ $CONTADOR -le $CUENTA ]; 
do 
   eecho bash -c "$LINEA"
   bash -c "$LINEA"
   #if [ $? -gt 0 ];
   #then
   #   #echo bucle terminado | festival --tts;
   #   habla -n bucle terminado
   #else
   #   exit 0;
   #fi
   CONTADOR=$(expr $CONTADOR + 1)
   sleep 1;
done
