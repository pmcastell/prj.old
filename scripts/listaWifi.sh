IFACE="wlan20"
TEMP=/tmp/$(date +"%Y%m%d%H%M%s").tmp

FILTRO="(Quality|ESSID)"
FILTRO="(Address|Quality|ESSID|Channel|WPA|WEP)"
FILTRO2="Frequency"

MARCA_INICIO="Quality"
MARCA_INICIO="Cell"
sudo ifconfig $IFACE up
sudo iwlist $IFACE scan | grep -E "$FILTRO" | grep -vE "$FILTRO2"  2>/dev/null > $TEMP
sleep 1
#TEMP=/tmp/2016021022201455139208.tmp
TEMP2=/tmp/$(date +"%Y%m%d%H%M%s").tmp
(read LINEA
while [ "$LINEA" != "" ]; do
   if [ "$(echo $LINEA | grep $MARCA_INICIO)" != "" ]; then
      echo -n $(echo $LINEA| awk '{print $NF;}')
      while read LINEA; do
         if [ "$(echo $LINEA | grep $MARCA_INICIO)" != "" ]; then 
           break;
         else 
           echo -n " | $LINEA"
         fi
      done
      echo ""
   else
      read LINEA
   fi
done ) < $TEMP
