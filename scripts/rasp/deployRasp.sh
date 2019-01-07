#!/bin/bash


uso() {
    echo Uso: $0 '<ceuta | micasa | ALL>'
    exit 1
}

[ "$1" = "" ] && uso
[ "$1" = "ALL" ] && DEST="ceuta6543.duckdns.org micasa6543.duckdns.org" || DEST="${1}6543.duckdns.org"
[ "$1" = "piw" ] && DEST="piw"
SCRIPTS="puntoAcceso.sh wpaWifi.sh duckdns.sh bookPass.sh aula/torRoute.sh actualiza.sh"

for d in $DEST; do
    eecho scp -P 443 /scripts/rasp/*.sh root@${d}:/root/
    eecho scp -P 443 -r /scripts/wicd root@${d}:/scripts/
    for script in $SCRIPTS; do 
        eecho scp -P 443 /scripts/$script root@${d}:/scripts/$script
    done
done   
