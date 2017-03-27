if [ "$3" = "" ];
then
   INTERFACE=wlan0
else
   INTERFACE=$3
fi   
#nomonitor $INTERFACE
#sleep 1
#nomonitor $INTERFACE
MARCA='##'
if [ "$2" != "" ];
then
   FICHERO=/m/Mios/AIRELAB/wep/$2.txt
else
   FICHERO=/m/Mios/AIRELAB/wep/wep.txt
fi
if [ "$1" = "" -o "$1" = "WebSTARR" ];
then
   ESSID=WebSTAR
   CHANNEL=1
   IP=192.168.0.6
   ROUTER=192.168.0.1
   echo ifconfig $INTERFACE ssid $ESSID channel $CHANNEL wepmode off
   #ifconfig $INTERFACE down
   ifconfig $INTERFACE ssid $ESSID channel $CHANNEL wepmode off 
   #country spain -bgscan
   ifconfig $INTERFACE up
else
   ESSID=$1
   CHANNEL=$(cat $FICHERO | grep $MARCA | grep  $1 | head -n 1 | awk '{print $3;}')
   if [ "$CHANNEL" = "" ];
   then
      echo no he encontrado el canal de la red $1
      exit 1
   fi
   KEY=$(cat $FICHERO | grep $MARCA | grep -i $1 | head -n 1 | awk '{print $6;}')
   if [ "$KEY" = "" ];
   then
      echo no he encontrado la clave de la red $1
      exit 2
   fi
   IP=$(cat $FICHERO | grep $MARCA | grep  $1 | head -n 1 | grep -o 192.168.* | awk '{print $1;}')
   if [ "$IP" = "" ];
   then
      IP=192.168.1.6
      ROUTER=192.168.1.1
   else
      ROUTER=$(echo $IP | awk -F'.' '{ print $1"."$2"."$3".1";}')
   fi
   echo
   #ifconfig $INTERFACE ssid $ESSID channel $CHANNEL wepkey 0x$KEY wepmode on deftxkey 1
   
   ifconfig $INTERFACE down
   echo ifconfig $INTERFACE ssid $ESSID channel $CHANNEL wepkey 0x$KEY wepmode on deftxkey 1 up
   #ifconfig $INTERFACE country spain -bgscan
   #ifconfig $INTERFACE up
   ifconfig $INTERFACE ssid $ESSID channel $CHANNEL wepkey 0x$KEY wepmode on deftxkey 1 up
   #authmode open
   #ifconfig $INTERFACE up
fi      
sleep 1
echo ifconfig $INTERFACE $IP
ifconfig $INTERFACE $IP netmask 255.255.255.0
AUX=$(netstat -rn | grep -i default)
while [ "$AUX" != "" ];
do
   echo route delete default
   route delete default
   AUX=$(netstat -rn | grep -i default)
done   
echo route add default $ROUTER
route add default $ROUTER
echo arp -d $ROUTER
arp -d $ROUTER
#ifconfig $INTERFACE down
#sleep 1
#ifconfig $INTERFACE up
PING_COMMAND="ping -c 4 -v -W 1 -n -r $ROUTER"
echo $PING_COMMAND
ifconfig $INTERFACE
$PING_COMMAND

