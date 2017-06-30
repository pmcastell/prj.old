#!/bin/bash

DIR_BASE="$(dirname $0)"
CWD="$DIR_BASE"
if [ ! -f $DIR_BASE/encriptar.sh ] && [ -f /scripts/encriptar.sh ]; then DIR_BASE="/scripts"; fi
if [ "$1" = "" ]; then    LISTA="config.html indice6.html indice5.html"; else LISTA="$1"; fi
for INDICE in $LISTA; do
   $DIR_BASE/encriptar.sh $INDICE /tmp/$INDICE
   for CUENTA in ganimedes scratch; do
      $DIR_BASE/hostinger.sh $CUENTA "/tmp/$INDICE"
   done
   rm /tmp/$INDICE
done   
   
