#!/bin/bash

uso() {
   Uso: $0 '<paquete-deb-a-instalar> [<equipo-donde-instalarlo>]'
   Ejemplo: $0 wireshar.deb
   exit 1
}

if [ "$1" = "" ]; then uso; else PAQUETE=$1; fi
if [ "$2" = "" ]; then 
   EQUIPOS=$(cat /etc/dnsmasq.conf | awk -F',' '{print $3;}' | grep 10.2.1)
else 
   EQUIPOS=$(cat /etc/dnsmasq.conf | grep $2 | awk -F',' '{print $3;}' | grep 10.2.1)
fi
for IP in $EQUIPOS; do
   #while true; do scp $1 lliurex@$IP:/tmp/; if [ $? -eq 0 ]; then break; fi; done
   #gnome-terminal -e "bash -c while true; do scp $1 lliurex@$IP:/tmp/; if [ $? -eq 0 ]; then break; fi; done; ssh -t lliurex@$IP sudo dpkg -i /tmp/$1;bash" &
   gnome-terminal -e "bash -c \"scp $PAQUETE lliurex@$IP:/tmp/;ssh -t lliurex@$IP sudo dpkg -i /tmp/$(basename $PAQUETE)\;/bin/bash\"" 
done   
   
