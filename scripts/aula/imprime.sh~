#!/bin/bash

uso() {
   Uso: $0 '<lista-de-trabajos-a-imprimir>'
   exit 1
}

if [ $# -lt 1 ]; then uso; fi

TRABAJOS="$@"
for t in $TRABAJOS; do
   lpr -o fit-to-page -o media=A4 -P RicohAficioMPC3001 $t
done   
