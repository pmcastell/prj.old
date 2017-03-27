#!/bin/bash
OS=$(uname)
MASCARA=255.255.255.0
#APS="192.168.1.9 192.168.1.17 192.168.1.18 192.168.76.2"
APS="192.168.76.6"
if [ -f /etc/init.d/NetworkManager ];
then
   NETWORK_MANAGER=/etc/init.d/NetworkManager
fi 
if [ -f /etc/init.d/network-manager ];
then
   NETWORK_MANAGER=/etc/init.d/network-manager
fi
sudo $NETWORK_MANAGER start
sleep 3
sudo $NETWORK_MANAGER stop
if [ "$1" != "" ];
then
   IP="$1 netmask $MASCARA"
   ROUTE=$(echo $IP | gawk -F '.' '{print $1"."$2"."$3;}')
   ROUTE=$ROUTE'.1'
else
   IP="192.168.1.6 netmask $MASCARA"
   ROUTE=192.168.1.1
fi      
INTERFAZ=$(sudo confHwIfaces $IP | tail -1)
ERR=0
while [ $ERR -lt 1 ];
do
   sudo route del default &> /dev/null
   ERR=$?
done
if [ "$OS" = "FreeBSD" ];
then 
   sudo route add default $ROUTE
else   
   sudo route add default gw $ROUTE   
fi
#if [ "$(echo $IP | grep 192.168.1)" == "" ];
#then
   ALIAS=0
   for AP in $APS;
   do
      #while [ "$(netstat -rn | grep $AP)" = "" ];
      #do
      #   if [ "$OS" = "FreeBSD" ];
      #   then
      #      eecho sudo route add -host $AP $(ifconfig bge0 | grep inet | gawk '{ print $2;}')
      #   else
      #      eecho sudo route add -host $AP dev $INTERFAZ
      #   fi
      #done
      ALIAS=$(expr $ALIAS + 1)
      eecho ifconfig $INTERFAZ:$ALIAS $AP
   done      
#fi   
if [ "$( netstat -rn | grep -i default | grep -i 192.168.1.1)" != "" ];
then
   while [ "$(netstat -rn | grep 192.168.1.1)" = "" ];
   do
      if [ "$OS" = "FreeBSD" ];
      then
         eecho sudo route add -host 192.168.1.1 $(ifconfig bge0 | grep inet | gawk '{ print $2;}')
      else
         eecho sudo route add -host 192.168.1.1 dev $INTERFAZ
      fi
   done
fi   
cat /etc/hosts.default > /etc/hosts
sudo bash -c  "(echo $(echo $IP|awk '{ print $1;}') $(hostname) >> /etc/hosts)"

sudo /m/Mios/prj/scripts/squid.sh 172.16.1.9
sudo bash -c  "(echo nameserver 8.8.8.8 > /etc/resolv.conf)"
sudo bash -c  "(echo nameserver 80.58.0.33 >> /etc/resolv.conf)"
sudo arp -d -a &
sudo arp -d -a &
if [ "$OS" = "Linux" ];
then
   sudo firewall
   if [ "$(echo $* | grep \"-n\")" = "" ];
   then
      actualiza
   fi
fi   
if [ "$(echo $* | grep \"-n\")" = "" ];
then
   sleep 4
   /m/Mios/prj/scripts/dynDns.sh
   /m/Mios/prj/scripts/dnsexit.sh ubuntu.linkpc.net
fi   

