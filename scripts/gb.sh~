#!/bin/bash

# trap ctrl-c and call ctrl_c()
trap ctrl_c INT

function ctrl_c() {
   echo "CTRL+C pulsado: saliendo"
   sudo vpn stop
   exit 1
}
I=1
for u in $(ls /home/usuario/freeVpns/gb-*); do 
   sudo vpn stop 
   echo ------------------------------------------------------------------------------------------------------------
   echo $I: sudo openvpn --config $u
   sudo openvpn --config $u &  
   echo ------------------------------------------------------------------------------------------------------------
   while true; do
      read -rsn1 KEY
      [ "$KEY" = "k" ] && break
   done
   echo ------------------------------------------------------------------------------------------------------------
   I=$(($I+1))
done
