#!/bin/bash

TEMP=$(tempfile)
HOSTS() {
echo -e "10.10.10.9      sp
10.10.10.10     win
10.10.10.11     sa1
10.10.10.12     sa2
10.10.10.13     st
10.10.10.14     sp2
10.10.10.15     sb
10.10.10.16     sm
10.10.10.17     spt
10.10.10.18     sh
10.2.1.21       ltsp21
10.2.1.22       ltsp22
10.2.1.23       ltsp23
172.18.161.250  fsserver
192.168.10.6	ipsp" > $TEMP
echo $TEMP
}
uso() {
   cat<<EOF
   uso: $0 
   Añade HOSTS al fichero /etc/hosts si no estaban incluidos
EOF
   exit 1
}
#for h in $(HOSTS); do
#   echo $h;
#done
if [ "$1" = "-h" ]; then uso; fi
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
CAMBIO=0
while read h; do
   [ "$(cat /etc/hosts | grep $(echo $h | awk '{print $1;}'))" = "" ] &&
   [ "$(cat /etc/hosts | grep $(echo $h | awk '{print $2;}'))" = "" ] && CAMBIO=$(( $CAMBIO + 1 )) && echo $h >> /etc/hosts
done < $(HOSTS)
if [ $CAMBIO -gt 0 ]; then echo Se han añadido $CAMBIO hosts a /etc/hosts; else echo No ha habido cambios; fi 

