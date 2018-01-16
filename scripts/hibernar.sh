#!/bin/bash
function uso() {
   echo uso: $(basename $0) '[<-h|-m|-s> <horas|minutos|segundos>]'
   echo 'hiberna el sistema y programa encendido para dentro de horas|minutos|segundos'
   exit 1
}   
if [ "$1" != "" ]; then
   if [ "$1" = "--help" ]; then uso;
   elif [ "$1" = "-h" ]; then SEGUNDOS=$(( $2 * 3600 ))
   elif [ "$1" = "-m" ]; then SEGUNDOS=$(( $2 * 60 ))
   else SEGUNDOS=$2; 
   fi
   sudo rtcwake -m no -t $(expr $(date -d "now" +%s) + $SEGUNDOS ) 
fi   
sudo swapoff -a
sudo swapon -a
sudo pm-hibernate
