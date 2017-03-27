#!/bin/bash

uso() {
   echo uso: $0 '<fichero> <num-lineas>'
   echo devuelve el md5 de las primeras num-lineas de fichero
   exit 1
}
if [ $# -lt 2 ]; then uso; fi
FICH=$1
NUM_LINEAS=$2
#while read L; do echo $L; done < $FICH | head -$NUM_LINEAS | md5sum | awk '{ print $1;}'
for((i=0;i<$NUM_LINEAS;i++)); do read L; echo $L; done <$FICH | md5sum | awk '{print $1;}'
