if [ "$1" = "" ]; 
then 
   echo Uso: $0 comando-a-ejecutar parametros;
   exit 1;

fi
until [ -z "$1" ];
do
   LINEA="$LINEA $1"
   shift
done   
while [ 1 ]; 
do 
   echo bash -c "$LINEA"
   bash -c "$LINEA"
   if [ $? -gt 0 ];
   then
      #echo bucle terminado | festival --tts;
      habla -n bucle terminado
   else
      exit 0;
   fi
   sleep 5;
done

