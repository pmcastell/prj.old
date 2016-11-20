#!/bin/bash

#uso() {
#   echo $0 '<-buscado> <donde-buscar>'
#   exit 1
#}
#if [ "$1" = "" -o "$2" = "" ]; then uso;fi    
BUSCADO=$1
shift
while [ "$1" != "$BUSCADO" -a "$1" != "" ]; do
   shift
done
RES=""
if [ "$1" = "$BUSCADO" ]; then
   while true; do 
      shift
      if [ "$1" = "" ]; then break; fi
      ACTUAL=$1
      P=${ACTUAL:0:1}
      if [ "$P" = "-" ]; then break; fi
      RES="$RES ""$1"
   done
fi      
echo $RES

