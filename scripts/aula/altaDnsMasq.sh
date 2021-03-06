#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 <dir-mac> <num-pc>
   Ejemplo: $0 38:d5:47:e0:ec:14 114 (asigna a es mac la dir ip: 10.2.1.114)
   
EOF
   exit 1
} 
ultimaLineaDhcpHost() {
   FICH="$1"
   NUMLINEA=1
   (while read L; do
      #echo $L
      [ "${L:0:9}" = "dhcp-host" ] && break
      NUMLINEA=$(($NUMLINEA+1))
      #echo $NUMLINEA
   done
   while read L; do
      #echo $L
      [ "${L:0:9}" != "dhcp-host" ] && break
      NUMLINEA=$(($NUMLINEA+1))
      #echo $NUMLINEA
   done
   echo $NUMLINEA) < $FICH
}   
([ "$1" = "" ] || [ "$2" = "" ]) && uso
MAC="$1"
NUM="$2"
[ "$3" = "" ] && CONF="/etc/dnsmasq.conf" || CONF="$3"
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
ESTABA=$(cat $CONF | grep -vE '^#' | grep $MAC )
SUS="dhcp-host=$MAC,pc${NUM},10.2.1.${NUM},12h"
if [ "$ESTABA" != "" ]; then
   ###sed -i "'/^${LINEA1}$/d'" $CONF_SQUID
   ###dhcp-host=00:19:66:e9:3b:7b,pc122,10.2.1.122,12h
   if [ "$ESTABA" != "$SUS" ]; then
      sed -i "s/^${ESTABA}$/${SUS}/g" $CONF
      CESTABA='#'"$ESTABA"
      if [ "$(cat $CONF | grep ^$CESTABA)" = "" ]; then
         echo "$CESTABA" >> $CONF
      fi
   fi
else
   #LINE=$(cat $CONF | grep -n ^dhcp-host | awk -F':' '{print $1;}' | tail -1)
   LINE=$(($(ultimaLineaDhcpHost $CONF)+1))
   sed -i "${LINE}i${SUS}" $CONF
fi   
sed -i "/.*${MAC}.*/d" /var/lib/misc/dnsmasq.leases
ssh lliurex@10.2.1.${NUM} sudo rm '/var/lib/dhcp/*.leases'
service dnsmasq restart   
   
   
