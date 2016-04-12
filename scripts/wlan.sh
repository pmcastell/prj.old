uso() {
   echo uso: $0 '<red> <fichero> <interface>'
   exit 1
}
RUTA=/m/Mios/Personal/AIRELAB/wep
if [ "$1" = "" ];
then
   uso
fi   
MARCA='##'
if [ "$2" != "" ];
then
   FICHERO=$RUTA/$2.txt
else
   FICHERO=$RUTA/wep.txt
fi
if [ "$3" = "" ];
then
   INTERFACE=ath0
else
   INTERFACE=$3
fi   
if [ "$4" = "" ];
then
   IP=$(cat $FICHERO | grep $MARCA | grep  $1 | head -n 1 | grep -o 192.168.* | awk '{print $1;}')
   if [ "$IP" = "" ];
   then
      IP=192.168.1.6
      ROUTER=192.168.1.1
   else
      ROUTER=$(echo $IP | awk -F'.' '{ print $1"."$2"."$3".1";}')
   fi
else   
   IP=$4
   ROUTER=$(echo $4 | gawk -F'.' '{ print $1"."$2"."$3;}')".1"
fi   
if [ "$1" = "" -o "$1" = "WebSTARR" ];
then
   ESSID=WebSTAR
   CHANNEL=7
   IP=192.168.0.6
   ROUTER=192.168.0.1
   echo iwconfig $INTERFACE mode Managed essid $ESSID channel $CHANNEL
   iwconfig $INTERFACE mode Managed essid $ESSID channel $CHANNEL
else
   ESSID=$1
   CHANNEL=$(cat $FICHERO | grep  $1 | head -n 1 | awk '{print $3;}')
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
   else
      echo clave de la red: $KEY
   fi
   eecho iwconfig $INTERFACE mode Managed essid $ESSID channel $CHANNEL key $KEY
   #iwconfig $INTERFACE mode Managed essid $ESSID channel $CHANNEL key $KEY
fi      
sleep 1
OS=$(uname)
if [ "$OS" = "FreeBSD" ];
then
   eecho sudo ifconfig $INTERFACE down
   eecho sudo ifconfig $INTERFACE link 00:01:38:ed:76:99
   eecho sudo ifconfig $INTERFACE up
else
   eecho sudo ifconfig $INTERFACE down
   eecho sudo ifconfig $INTERFACE hw ether 00:01:38:ed:76:99
   eecho sudo ifconfig $INTERFACE up
fi
eecho ifconfig $INTERFACE $IP
AUX=$(netstat -rn | grep "^0\.0\.0\.0")
while [ "$AUX" != "" ];
do
   echo route delete default
   route delete default
   AUX=$( netstat -rn | grep "^0\.0\.0\.0")
done   
echo route add default gw $ROUTER
route add default gw $ROUTER
echo arp -d -a
arp -d -a
echo nameserver 80.58.0.33 > /etc/resolv.conf
echo ping -c 4 $ROUTER
ping -c 4 -W 1 $ROUTER

