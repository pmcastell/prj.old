#!/bin/bash
uso() {
   echo uso: $0 '<cadena>'
   echo Indica si todos los caracteres de la cadena son alfanuméricos
   exit 1
}

if [ "$1" = "" ]; then uso; fi
STR="$1";L=${#STR}; I=0; 
while [ $I -lt $L ]; do 
   if [ "${STR:$I:1}" != " " ] && [ "$(echo ${STR:$I:1} | grep -E '[A-Za-z0-9_]' )" = "" ]; then 
      echo false; exit;
   fi; 
   I=$(( $I + 1 )); 
done
echo true;
