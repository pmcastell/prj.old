#!/bin/bash
. /scripts/debug.sh
DEBUG=false
DIR_BASE=$(dirname $0)
if [ "$1" = "" ]; then PASS="mail" ; else PASS="$1"; fi; debug PASS: $PASS
if [ "$2" = "" ]; then FICHERO="indicepass.html"; else FICHERO=$2; fi; debug FICHERO: $FICHERO

wget -O - http://ubuin.hopto.org/$FICHERO 2> /dev/null | /$DIR_BASE/desencriptar.sh |
while read L; do 
   debug L: "$L";
   if [ "$(echo $L| grep -iE ^$PASS)" != "" ]; then echo $(echo $L | awk '{print $2;}'); break; fi
done;   

