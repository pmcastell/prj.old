#!/bin/bash
uso() {
   echo uso: $0 '<fichero-a-quitarla> <linea-a-quitar>'
   exit 1
}   
( [ "$1" = "" ] || [ "$2" = "" ] ) && uso
FICHERO="$1"; shift
LINEA="$1"; shift
while [ "$1" != "" ]; do
   LINEA="$LINEA $1"
   shift
done
TMP=$(tempfile)
#echo "TMP: $TMP - LINEA: \"$LINEA\" - FICHERO: $FICHERO"
cat $FICHERO | grep -v "$LINEA" > $TMP
mv $TMP  $FICHERO

