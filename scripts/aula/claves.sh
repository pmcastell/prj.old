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

CLAVE="$HOME/.clave"
ROUTER="172.18.161.1"
ping -c 2 $ROUTER &>/dev/null
claveGeneral="$(arp -n | grep -w $ROUTER | awk '{print $3;}')"
if [ "$1" = "franav" ]; then desencripta U2FsdGVkX1+7MwFuQFvPbR3OMiZ7kiYjYGHznA== $claveGeneral; 
elif [ "$1" = "fcriadon" ]; then desencripta U2FsdGVkX19HZCvMnc/F/lAHD2TsbnmJCg== $claveGeneral; 
else echo Usuario no encontrado; fi


