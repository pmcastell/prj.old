#!/bin/bash
. /scripts/debug.sh
. /scripts/uso.sh

DEBUG=false
DIR_BASE=$(dirname $0)
#if [ "$1" = "" ]; then USU="mail" ; else USU="$1"; fi; debug USU: $USU
[ "$1" = "" ] && uso "Uso: $0 <usuario>" || USU="$1"
[ -f /m/Mios/Personal/Privado/AgendasClaves/genClavesIndicePass.sh ] && cat /m/Mios/Personal/Privado/AgendasClaves/genClavesIndicePass.sh | grep -iE "^$USU " | awk '{print $2;}' && exit

if [ "$2" = "" ]; then FICHERO="indicepass.html"; else FICHERO=$2; fi; debug FICHERO: $FICHERO
wget -O - http://ubuin.hopto.org/$FICHERO 2> /dev/null | /$DIR_BASE/desencriptar.sh |
while read L; do 
   debug L: "$L";
   if [ "$(echo $L| grep -iE ^$USU | awk '{print $1;}')" = "$USU" ]; then echo $(echo $L | awk '{print $2;}'); break; fi
done;   

