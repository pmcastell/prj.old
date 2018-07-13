#!/bin/bash

uso() {
   cat << EOF
   uso: $0 <usuario>
EOF
   exit 1
}      
#desencripta() {
#   TEXTO="$1"
#   CLAVE="$2"
#   echo $TEXTO | base64 -d | openssl enc -d -aes-256-ctr -k "$CLAVE"
#}   
claveRouterInsti() {
   ROUTER="172.18.161.1"
   ping -c 2 -W 1 $ROUTER &>/dev/null
   #echo "$(arp -n | grep -w $ROUTER | awk '{print $3;}')"
   echo 00:a0:26:b3:b9:6d
}

if [ "$1" = "" ]; then uso; fi 

if [ -d /home/franav ]; then
   fCLAVES="/home/franav/claves/claves.txt"
   claveGeneral="$(claveRouterInsti)"
elif [ -d /datos/usuarios/profesores/fcriadon ]; then
   fCLAVES="/datos/usuarios/profesores/fcriadon/claves/claves.txt"; 
   claveGeneral="$(claveRouterInsti)"
elif [ -f "/m/Mios/Personal/Privado/AgendasClaves/claves.txt" ]; then
   fCLAVES="/m/Mios/Personal/Privado/AgendasClaves/claves.txt";
   #claveGeneral=$(cat /m/Mios/Personal/Privado/AgendasClaves/claveInsti.txt)
   claveGeneral="$(claveRouterInsti)"
else
   zenity --info --text="No se encuentra el fichero de claves"
   exit 2
fi
#if [ "$1" = "franav" ]; then desencripta $(cat $fCLAVES | grep franav | awk -F':' '{print $2;}') $claveGeneral; 
#elif [ "$1" = "fcriadon" ]; then desencripta $(cat $fCLAVES | grep fcriadon | awk -F':' '{print $2;}') $claveGeneral; 
#else echo Usuario no encontrado; fi
#if [ ! -f $fCLAVES ]; then zenity --info --text="No se encuentra el fichero de claves"; fi
#echo $(desencripta $(cat $fCLAVES | grep "$1:" | awk -F':' '{print $2}') $claveGeneral)
echo $(cat $fCLAVES | grep "$1:" | awk -F':' '{print $2}')  | /scripts/desencriptar.sh -k $claveGeneral 
