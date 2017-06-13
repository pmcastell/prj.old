#!/bin/bash

uso() {
   echo Uso: $0 '<num-pc> <usuario>'
   echo "Ejemplo $0 111 alumne (cierra la sesión de alumne en la máquina pc111)"
   exit 1
}
if [ $# -lt 2 ]; then uso; fi
[ "$(echo $1 | egrep -o '^[0-9]+$')" != "" ] && PCNAME="pc${1}" || PCNAME="$1"
ssh lliurex@${PCNAME} sudo pkill -u $2
