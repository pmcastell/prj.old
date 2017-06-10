#!/bin/bash
uso() {
   echo $0 '<interfaz>'
   exit 1
}
[ "$1" = "-v" ] && VERBOSE=true && shift
[ "$1" = "" ] && uso
IFACE=$1   
[ "$(ifconfig | grep $IFACE)" = "" ] && ESTADO=down || ESTADO=up
sudo ifconfig $IFACE down;
[ $VERBOSE ] && echo "$IFACE. MAC Original: $(ethtool -P $IFACE | awk '{print $NF;}')"
[ $VERBOSE ] && echo "$IFACE. MAC Actual:   $(ifconfig -a | grep $IFACE | awk '{print $NF;}')" 
while true; do 
   RAND_MAC=$(randomMAC) #echo probando: $RAND_MAC
   [ "$(sudo ifconfig $IFACE hw ether $RAND_MAC 2>&1 | grep 'No se puede asignar la direcci')" = "" ] && break
done ; 
[ $VERBOSE ] && echo "$IFACE. MAC Nueva:    $(ifconfig -a | grep $IFACE | awk '{print $NF;}')"
[ $VERBOSE ] && echo sudo ifconfig $IFACE $ESTADO
sudo ifconfig $IFACE $ESTADO
