#!/bin/bash

uso() {
   echo Uso: $0 '<num-pc> <usuario>'
   echo "Ejemplo $0 111 alumne (cierra la sesión de alumne en la máquina pc111)"
   exit 1
}
if [ $# -lt 2 ]; then uso; fi
ssh lliurex@pc$1 sudo pkill -u $2
