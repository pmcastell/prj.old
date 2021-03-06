FICH_PROX=/home/usuario/multi-dvd/proxyList2.txt

if [ ! -f $FICH_PROX ];
then
   echo No se ha encontrado el fichero $FICH_PROX
   exit 1
fi   
NUM_LIN=$(cat $FICH_PROX | wc -l)
I=1
while [ $I -le $NUM_LIN ];
do
   LINEA=$(head -$I $FICH_PROX | tail -1)
   SERV=$(echo $LINEA | awk '{print $1;}')
   PORT=$(echo $LINEA | awk '{print $2;}')
   echo ----------------------------------------------------------------------------------------------
   echo utilizando proxy: http://$SERV:$PORT
   echo Intento núm: $I
   echo ----------------------------------------------------------------------------------------------
   echo $SERV | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}'
   if [ "$(echo $SERV | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}')" == "" ];
   then
      SERV=$(digHost $SERV)
   fi
   if [ "$SERV" != "" ];
   then
      export ftp_proxy="http://$SERV:$PORT"
      eecho wget -c --timeout=10 --tries=1 ftp://ftp.nai.com/pub/datfiles/english/avvdat-6651.tar
      echo ----------------------------------------------------------------------------------------------
      if [ $? -eq 0 ];
      then
         echo exit 0
      fi
   fi
   I=$(expr $I + 1)
done
