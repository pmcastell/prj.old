#!/bin/bash
. /scripts/uso.sh
[ "$1" = "" ] && uso Uso: "$0 <lista-de-opciones>"

OPC=0
while [ "$1" != "" ]; do
   opciones[$OPC]=$1
   shift
   OPC=$(expr $OPC + 1)
done
#ELEGIDA=$(($OPC + 1))
while [ "$ELEGIDA" = "" ] || [ "$(echo $ELEGIDA | egrep -x '[0-9]+')" = "" ] || [ $ELEGIDA -lt 1 ] || [ $ELEGIDA -gt $OPC ]; do
    for((i=0;i<$OPC;i++)); do
       echo $(expr $i + 1)')' ${opciones[$i]} > /dev/stderr
    done
    echo elige una opción entre 1')' y $OPC')' > /dev/stderr
    read ELEGIDA
done
echo $ELEGIDA
   
