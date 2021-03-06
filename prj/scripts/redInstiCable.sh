#!/bin/bash
OS=$(uname)

if [ "$OS" = "Linux" ];
then
   sudo /etc/init.d/NetworkManager stop &> /dev/null
   sudo /etc/init.d/network-manager stop &> /dev/null
fi   
if [ "$1" != "" ];
then
   IP="$1 netmask 255.255.224.0"
   #ROUTE=$(echo $IP | awk -F '.' '{print $1"."$2"."$3;}')
   ROUTE=$(echo $IP | awk -F '.' '{print $1"."$2;}')
   ROUTE=$ROUTE.1.1
else
   IP="172.16.6.6 netmask 255.255.224.0"
   ROUTE=172.16.1.1
fi      
sudo confHwIfaces
ERR=0
while [ $ERR -lt 1 ];
do
   sudo route del default &> /dev/null
   ERR=$?
done
INTERFAZ=$(interfaces | grep -iEo '(eth[0-9]+|bge[0-9]+)' | grep -v grep | head -1)
ifconfig $INTERFAZ $IP up
ifconfig $INTERFAZ:1 192.168.76.6 netmask 255.255.255.0 # para acceder a punto de acceso de secretaria 192.168.76.1
if [ "$OS" = "FreeBSD" ];
then 
   eecho sudo route add default $ROUTE
   eecho sudo route add -net 192.168.0.0 netmask 255.255.255.0 172.16.1.11
else   
   eecho sudo route add default gw $ROUTE   
   eecho sudo route add -net 192.168.0.0 netmask 255.255.255.0 gw 172.16.1.11
   sudo firewall
fi
cat /etc/hosts.default > /etc/hosts
echo 172.16.1.9  iesinclan.dyndns.org >> /etc/hosts
#echo 192.168.0.2	c0 >> /etc/hosts
echo $(echo $IP|awk '{print $1;}') $(hostname) >> /etc/hosts
/m/Mios/prj/scripts/squid.sh 172.16.1.9

sudo bash -c "(echo nameserver 80.58.61.250 > /etc/resolv.conf)"
sudo bash -c "(echo nameserver 80.58.61.254 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 8.8.8.8 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 8.8.4.4 >> /etc/resolv.conf)"
if [ "$(echo $* | grep '\-n')" = "" ];
then
   actualiza
   /m/Mios/prj/scripts/dynDns.sh
fi


#sudo echo domain 41701109.41.andared.cec.junta-andalucia.es > /etc/resolv.conf
#sudo echo search 41701109.41.andared.cec.junta-andalucia.es >> /etc/resolv.conf
#sudo echo nameserver 192.168.0.1 >> /etc/resolv.conf
#sudo echo nameserver 192.168.0.2 >> /etc/resolv.conf

