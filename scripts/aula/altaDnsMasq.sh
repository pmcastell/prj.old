#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 <dir-mac> <num-pc>
   Ejemplo: $0 38:d5:47:e0:ec:14 114 (asigna a es mac la dir ip: 10.2.1.114)
   
EOF
   exit 1
}      
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
ESTABA=$(cat /etc/dnsmasq.conf | grep $2 | grep -v '^#')
if [ "$ESTABA" != "" ]; then
   
