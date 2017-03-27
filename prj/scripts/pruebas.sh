#!/bin/bash


md5Lineas() {
   FICH=$1
   NUM_LINEAS=$2
   while read L; do echo $L; done < $FICH | head -$NUM_LINEAS | md5sum | awk '{ print $1;}'
}

cat /home/usuario/hostinger/indice5.html > /tmp/pr/indice.html
TEMP=/tmp/pr/indice.html
NUM_LINEAS=$(cat $TEMP | wc -l)   
MD51=$(md5Lineas $TEMP $NUM_LINEAS)
echo $MD51
echo $MD51 >> $TEMP
NUM_LINEAS=$(cat $TEMP | wc -l)   
MD52=$(md5Lineas $TEMP $(( $NUM_LINEAS - 1)))
echo $MD52
tail -1 $TEMP

   
