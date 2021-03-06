#!/bin/bash
LISTA=""
DEST=""
while [ "$1" != "" ]; do
   LISTA="$LISTA $1"
   DEST="$2"
   if [ "$3" = "" ]; then
      break;
   fi
   shift
done;
for PC in $(cat /etc/dnsmasq.conf | grep -i dhcp-host | awk -F',' '{print $3;}'); do
   echo copiando $LISTA en: $PC:$DEST
   for f in $LISTA; do
      echo scp -r "$f" lliurex@$PC:$DEST
      scp -r "$f" lliurex@$PC:$DEST
   done
done   

