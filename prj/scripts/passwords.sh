#!/bin/bash

uso() {
   cat << EOF
   uso: $0 <usuario>
EOF
   exit 1
}      
desencripta() {
   TEXTO="$1"
   CLAVE="$2"
   echo $TEXTO | base64 -d | openssl enc -d -aes-256-ctr -k "$CLAVE"
}   

if [ "$1" = "" ]; then uso; fi 

fCLAVES="/m/Mios/Personal/Privado/AgendasClaves/passwords.txt"
if [ ! -f $fCLAVES ]; then zenity --info --text="No se encuentra el fichero de claves"; exit 2; fi
claveGeneral="$(ethtool -P wlan20 | awk '{print $NF;}')"
#if [ "$1" = "franav" ]; then desencripta $(cat $fCLAVES | grep franav | awk -F':' '{print $2;}') $claveGeneral; 
#elif [ "$1" = "fcriadon" ]; then desencripta $(cat $fCLAVES | grep fcriadon | awk -F':' '{print $2;}') $claveGeneral; 
#else echo Usuario no encontrado; fi
claveEncriptada="$(cat $fCLAVES | grep $1 | awk -F':' '{print $2}')"
if [ "$claveEncriptada" = "" ]; then zenity --info --text="No se encuentra ningún usuario $1"; exit 3; fi
echo $(desencripta $claveEncriptada $claveGeneral)
