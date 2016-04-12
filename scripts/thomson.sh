OS=$(uname)
if [ "$1" = "" ];
then
   IFACE=bge0
else 
   IFACE=$1
fi
if [ "$2" = "" ];
then
   IP=192.168.0.6
else
   IP=$2
fi
if [ "$3" = "" ];
then
   MASK=255.255.0.0
else
   MASK=$3
fi   
sudo ifconfig $IFACE $IP netmask $MASK up
RES=0
while [ $RES -lt 1 ];
do 
   sudo route del default
   RES=$?
done
if [ "$OS" = "FreeBSD" ];
then
   sudo route add default 192.168.0.1
else
   sudo route add default gw 192.168.0.1
fi

