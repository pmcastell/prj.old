#!/bin/bash

uso() {
   echo $0 '[<segundos>]'
   exit 1
}
arreglaEspacios() {
   FILE="$1";
   l=$(expr length "$FILE");
   RES="";
   for((i=0;i<$l;i++)); do
      C=${FILE:${i}:1};
      if [ "$C" = " " ]; then
         RES="$RES\ ";
      else
         RES="${RES}${C}";
      fi
   done
   echo $RES;
}
   
   
if [ "$1" = "" ]; then TIEMPO=60; else TIEMPO=$1; fi

SECS_ACTUAL=$(date +%s)
SECS=$(expr $SECS_ACTUAL - $TIEMPO)
echo $SECS_ACTUAL $SECS
find . | while read FILE ; do
   #echo "FICHERO $FILE"
   stat --printf="%n %Y\n" "$FILE" | awk '$NF > '$SECS' {print $0}'
done   
