#!/bin/bash

puertoLibre() {
   read LOWERPORT UPPERPORT < /proc/sys/net/ipv4/ip_local_port_range
   while :
   do
           PORT="`shuf -i $LOWERPORT-$UPPERPORT -n 1`"
           ss -lpn | grep -q ":$PORT " || break
   done
   echo $PORT
}
if [ "$(ps aux | grep "xvnc4viewer -listen" | grep -v grep)" = "" ];
then
   PUERTO=$(puertoLibre)
   xvnc4viewer -listen $PUERTO &
else
   PUERTO=$(ps aux | grep "xvnc4viewer -listen" | grep -v grep | awk '{ print $NF;}' | head -1)   
fi   
sleep 1
bash -c "ssh lliurex@10.2.1.$1 pantalla $PUERTO > /dev/null" &

