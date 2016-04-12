#!/bin/bash

uso() {
   echo uso: $0 '<SSH|OVP>'
   echo SSH pone en marcha tunel SSH
   echo OVP pone en marcha tunel OVPN
   exit 1
}
if [ "$(whoami)" != "root" ]; then sudo $0 $*; exit 2; fi
if [ "$1" = "SSH" ]; then
   SERVICE="SSH"
elif [ "$1" = "OVP" ]; then
   SERVICE="OVP"
else
   uso
fi 
