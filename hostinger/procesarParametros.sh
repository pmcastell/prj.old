#!/bin/bash

TEMP=$(tempfile)
TEMP=/tmp/prueba.txt
wget -O - http://ubuin.noip.me/indice.html 2> /dev/null > $TEMP
while read LINEA; do 
   EXPORT=$(echo $LINEA | awk -F'=' '{print "export "$1"="$2;}'); 
   VAR=$(echo $LINEA | awk -F'=' '{print $1;}');
   VAL=$(echo $LINEA | awk -F'=' '{print $2;}');
   $EXPORT; 
   #echo "VAR: $VAR ,  VAL: $VAL"
   echo $EXPORT
done < $TEMP
rm $TEMP
