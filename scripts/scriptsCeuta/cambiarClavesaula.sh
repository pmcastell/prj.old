#!/bin/bash

uso() {
   echo 'Uso: $0 <ciclo1|ciclo2>'
   exit 1
}

source $(dirname $0)/funcionesAula.sh

[ "$1" = "" ] && uso
([ "$1" != "ciclo1" ] && [ "$1" != "ciclo2" ]) && uso
CICLO="$1"
CONT=1
TEMP=$(tempfile)
([ "$CICLO" = "ciclo1" ] && clavesCiclo1 || clavesCiclo2) |
while read L; do
   PC=$(echo $L | awk '{print $1;}')
   CLAVE1=$(echo $L | awk '{print $2;}')
   CLAVE2=$(echo $L | awk '{print $3;}')
   eCLAVE1=$(openssl passwd $CLAVE1)
   eCLAVE2=$(openssl passwd $CLAVE2)
   echo "PC: $PC" 
   ssh -n -q $PC "sudo usermod -p $eCLAVE1 ciclomedio"  &> $TEMP
   [ "$(cat $TEMP | grep -i 'no existe')" != "" ] && echo "PC: $PC no existe el usuario ciclomedio" 
   rm $TEMP
   ssh -n -q $PC "sudo usermod -p $eCLAVE2 ciclosuperior" &> $TEMP
   [ "$(cat $TEMP | grep -i 'no existe')" != "" ] && echo "PC: $PC no existe el usuario ciclosuperior" 
   rm $TEMP
   #echo "CONT: $CONT. L: $L" 
   CONT=$(($CONT + 1))
   ##echo "PC: $PC. CLAVE1: $CLAVE1. CLAVE2: $CLAVE2"
done 
