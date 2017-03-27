sudo ifconfig $(interfaces | grep eth) 192.168.1.254
sudo route add default gw 192.168.1.168 
sudo cat /etc/hosts | grep -v 192.168.0.215 > /tmp/hosts
sudo mv /tmp/hosts /etc/hosts
sudo echo 192.168.1.168 routerTic >> /etc/hosts



