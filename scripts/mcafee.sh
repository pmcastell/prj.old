TEMP=$(tempfile)
TAM_FICHERO=0
WGET_COMMAND="wget --proxy-user=usuario --proxy-password=usuario "
URL="ftp://ftp.nai.com/pub/datfiles/english"
###por la junta
INTERFAZ=$(route -n | grep -iE '^0.0.0.0|default' | awk '{print $NF;}' | tail -1 )
ROUTER=$(route -n | grep -iE '^0.0.0.0|default' | awk '{ print $2;}'| tail -1)
IP=$(ip a show dev wlan0 | grep inet | egrep -o '([0-9]{1,3}\.){3}[0-9]{1,3}')
RED=$(echo $IP | awk -F'.' '{print $1}')
echo INTERFAZ: $INTERFAZ
echo IP: $IP
echo ROUTER: $ROUTER
echo RED: $RED
if [ "$RED" = "192" -a "$ROUTER" = "192.168.0.1" ]; then
    if [ "$ftp_proxy" != "" ];     then
       unset ftp_proxy 
    fi
    ####sudo route add -host ftp.nai.com dev tun0
    sudo route add -host ftp.nai.com gw 192.168.8.1
fi    

###por la junta
while [ $TAM_FICHERO -le 1000000 ]; do 
   ###if [ -f $TEMP ]; then eecho rm $TEMP; fi
   ###echo $WGET_COMMAND  -S -O - $URL/ '2>&1 | grep -i tar | tee' $TEMP
   #$WGET_COMMAND  -S -O - $URL/ 2>&1 | grep -i tar | tee $TEMP
   ###TEMP=$($WGET_COMMAND  -S -O - $URL/ 2>&1 | grep -i tar | tee $TEMP)
   
   ###NOMBRE=$(cat $TEMP | grep -Eo "avvdat.*tar" | tail -1)
   ###TAM_FICHERO=$(echo $TEMP | grep -Eo "([0-9]{7,9}|[0-9]{2,3}(\.[0-9]{3}){2})" | gawk -F'.' '{print $1$2$3; }' | head -1) 
   echo Obteniendo tam. de fichero: $NOMBRE
   INFO=$(curl ftp://ftp.nai.com:21/pub/datfiles/english/ 2> /dev/null | grep tar | tail -1)
   NOMBRE=$(echo $INFO | awk '{ print $NF;}')
   TAM_FICHERO=$(echo $INFO | awk '{ print $5;}')
   sleep 1;
done   

###por la junta
if [ "$RED" = "192" -a "$ROUTER" = "192.168.0.1" ]; then
    #export ftp_proxy="http://proxy.spidernet.it:8080"
    #export ftp_proxy="http://78.47.214.156:8080" ## proxy alemán libre
    #export ftp_proxy="http://squid:3128"
    #echo ftp_proxy: $ftp_proxy
    echo hola > /dev/null;
fi    
###por la junta
TAM_NUEVO=0
echo Tam.: $TAM_FICHERO
echo nombre: $NOMBRE
echo URL: $URL
echo ..........................................................
echo http_proxy: $http_proxy
echo ftp_proxy : $ftp_proxy

echo while [ $TAM_FICHERO -gt $TAM_NUEVO ]; 
while [ $TAM_FICHERO -gt $TAM_NUEVO ]; do 
   if [ "$ROUTER" = "jdjdj192.168.0.1" ];    then
      echo curl "$URL/$NOMBRE" -o $NOMBRE --speed-time 10 --speed-limit 1000 --limit-rate 60000 --max-time 300 --keepalive-time 5 --continue - --max-filesize $TAM_FICHERO
      ###curl "$URL/$NOMBRE" -o $NOMBRE --speed-time 10 --speed-limit 1000 --limit-rate 60000 --max-time 300 --keepalive-time 15 --continue - --max-filesize $TAM_FICHERO
      curl "$URL/$NOMBRE" -o $NOMBRE --speed-time 10 --speed-limit 1000 --max-time 300 --keepalive-time 15 --continue - --max-filesize $TAM_FICHERO
   else
      #echo curl "$URL/$NOMBRE" -o $NOMBRE --speed-time 10 --speed-limit 1000 --max-time 300 --keepalive-time 5 --continue - --max-filesize $TAM_FICHERO
      #curl "$URL/$NOMBRE" -o $NOMBRE --speed-time 10 --speed-limit 1000 --max-time 300 --keepalive-time 5 --continue - --max-filesize $TAM_FICHERO
      echo "Descargando: $URL/$NOMBRE"
      descarga "$URL/$NOMBRE"
   fi
   if [ -f $NOMBRE ];    then
      TAM_NUEVO=$(ls -l $NOMBRE | gawk '{ print $5; }');
   fi
   echo Descargado: $TAM_NUEVO;
   sleep 1
done
#rm $TEMP





   #$WGET_COMMAND -N -c --no-passive-ftp --timeout=10 --bytes=$TAM_FICHERO "$URL"

   #K:\Mios\utils>curl ftp://80.239.144.185/pub/datfiles/english/dat-5189.zip -o dat-5189.zip --continue - --max-filesize 16984062 --connect-timeout 10
   
#USER_AGENT="Mozilla/5.0 (Windows; U; Windows NT 5.1; es-ES; rv:1.8.1.11) Gecko/20071128 SeaMonkey/1.1.7"
#USER_AGENT="Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1;"
#USER_AGENT=""
#WGET_COMMAND="wget  --user-agent=\"$USER_AGENT\""

#URL="ftp://80.239.144.185/pub/datfiles/english"
#URL="ftp://213.200.104.33/pub/datfiles/english"
#URL="ftp://213.200.104.28/pub/datfiles/english"
