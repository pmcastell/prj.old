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


ROUTER="172.18.161.1"
ping -c 2 -W 1 $ROUTER &>/dev/null
claveGeneral="$(arp -n | grep -w $ROUTER | awk '{print $3;}')"
if [ -d /home/franav ]; then
   fCLAVES="/home/franav/claves/claves.txt"
elif [ -d /datos/usuarios/profesores/fcriadon ]; then
   fCLAVES="/datos/usuarios/profesores/fcriadon/claves/claves.txt"; 
elif [ -f "/m/Mios/Personal/Privado/AgendasClaves/claves.txt" ]; then
   fCLAVES="/m/Mios/Personal/Privado/AgendasClaves/claves.txt";
   claveGeneral=$(cat /m/Mios/Personal/Privado/AgendasClaves/claveInsti.txt)
else
   zenity --info --text="No se encuentra el fichero de claves"
fi
#if [ "$1" = "franav" ]; then desencripta $(cat $fCLAVES | grep franav | awk -F':' '{print $2;}') $claveGeneral; 
#elif [ "$1" = "fcriadon" ]; then desencripta $(cat $fCLAVES | grep fcriadon | awk -F':' '{print $2;}') $claveGeneral; 
#else echo Usuario no encontrado; fi
if [ ! -f $fCLAVES ]; then zenity --info --text="No se encuentra el fichero de claves"; fi
echo $(desencripta $(cat $fCLAVES | grep $1 | awk -F':' '{print $2}') $claveGeneral)
