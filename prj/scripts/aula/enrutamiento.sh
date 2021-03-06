#!/bin/bash
estado() {
   echo "Enrutamiento: $(cat /proc/sys/net/ipv4/ip_forward)"
   echo "Nat: $(sudo iptables -t nat -nL)" 
}
uso() {
   echo Uso: $0 '<on-off>'
   echo 'Activa/Desactiva enrutamiento en el servidor'
   estado
   exit 1
}
if [ "$1" = "" ]; then uso; fi
if [ "$(whoami)" != "root" ]; then sudo $0 $@; exit $?; fi
if [ "$1" = "on" ]; then
   sudo bash -c 'echo 1 > /proc/sys/net/ipv4/ip_forward'
   if [ "$(sudo iptables -t nat -nL | grep MASQUERADE | grep 10.2.1.0)" = "" ]; then
      sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.2.1.0/24
   fi
elif [ "$1" = "off" ]; then
   sudo bash -c 'echo 0 > /proc/sys/net/ipv4/ip_forward' 
fi
echo "Estado Actual:"
echo "--------------"
estado  
