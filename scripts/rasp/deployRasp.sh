#!/bin/bash


uso() {
   echo Uso: $0 '<ceuta | micasa | ALL>'
   exit 1
}

[ "$1" = "" ] && uso
[ "$1" = "ALL" ] && DEST="ceuta6543 micasa6543" || DEST="${1}6543"
SCRIPTS="puntoAcceso.sh wpaWifi.sh duckdns.sh bookPass.sh aula/torRoute.sh actualiza.sh"

for d in $DEST; do
   for script in $SCRIPTS; do 
       eecho scp -P 443 /scripts/$script root@${d}.duckdns.org:/scripts/$script
   done
   eecho scp -P 443 /scripts/rasp/*.sh root@${d}.duckdns.org:/root/
done   
