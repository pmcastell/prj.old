#!/bin/bash
OS=$(uname)
INTERFAZ=$(interfaces|grep eth | sort | head -1)
if [ "$OS" = "Linux" ];
then
   sudo /etc/init.d/NetworkManager stop &> /dev/null
   sudo /etc/init.d/network-manager stop &> /dev/null
   sudo /etc/init.d/avahi-daemon stop &> /dev/null
   sudo stop network-manager &> /dev/null
fi   
sudo confHwIfaces
sudo ifconfig $(/m/Mios/prj/scripts/interfaces.sh | grep -i wlan | grep -v grep | head -1) down
while [ "$(ps aux | grep -i dhclient | grep -v grep)" != "" ];
do
   sudo eecho kill -TERM $(ps aux | grep -i dhclient | grep -v grep | awk '{print $2;}')
   sleep 1
done
#sudo eecho dhclient $INTERFAZ &
sudo ifconfig $INTERFAZ 192.168.15.81 netmask 255.255.248.0 up

sudo route add default gw 192.168.8.1
sudo bash -c "(echo nameserver 172.16.1.9 > /etc/resolv.conf)"
sudo bash -c "(echo nameserver 192.168.0.1 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 192.168.0.2 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 8.8.8.8 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 8.8.4.4 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 80.58.61.250 >> /etc/resolv.conf)"
sudo bash -c "(echo nameserver 80.58.61.254 >> /etc/resolv.conf)"
sudo cp /etc/hosts.default /etc/hosts

INTENTOS=1
ping -c 1 192.168.0.1
while [ $? -gt 0 ];
do
    echo esperando a que se configure la red
    eecho ifconfig $INTERFAZ
    eecho sleep 1
    echo intento: $INTENTOS
    INTENTOS=$(expr $INTENTOS + 1)
    ping -c 1 192.168.0.1
done    
sudo vpn stop
/m/Mios/prj/scripts/bookPass.sh
sudo openvpn --config /home/usuario/freeVpns/book/uk1/vpnbook-uk1-tcp443.ovpn &
sleep 5
sudo eecho vpn 
#sudo eecho route add -host 80.36.87.166 gw 192.168.8.1
#if [ "$(echo $* | grep -i '\-defRoute')" != "" ];
#then
#    sudo eecho route del default
#    sudo eecho route add defualt gw 10.1.0.5
#fi 
sudo eecho ifconfig $INTERFAZ:1 192.168.76.6
if [ "$(echo $* | grep \"-n\")" = "" ];
then
   sleep 4
   /m/Mios/prj/scripts/dynDns.sh
   /m/Mios/prj/scripts/dnsexit.sh ubuntu.linkpc.net
fi   
sudo firewall
sudo actualiza

