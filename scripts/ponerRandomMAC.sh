#!/bin/bash
uso() {
   echo $0 '<interfaz>'
   exit 1
}
if [ "$1" = "" ]; then uso; fi
IFACE=$1   
if [ "$(ifconfig | grep $IFACE)" = "" ]; then
   ESTADO=down
else
   ESTADO=up
fi      
sudo ifconfig $IFACE down; 
while true; do 
   RAND_MAC=$(randomMAC) #echo probando: $RAND_MAC
   [ "$(sudo ifconfig $IFACE hw ether $RAND_MAC 2>&1 | grep 'No se puede asignar la direcci')" = "" ] && break
done ; 
eecho sudo ifconfig $IFACE $ESTADO
