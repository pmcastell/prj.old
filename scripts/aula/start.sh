#!/bin/bash

uso() {
   echo uso: $0 '<SSH|OVP>' '<si|no>'
   echo SSH pone en marcha tunel SSH
   echo OVP pone en marcha tunel OVPN
   echo si habilita el tunel
   echo no deshabilita el tunel
   exit 1
}
ponerMD5() {
   FICHERO=$1
   NUM_LINEAS=$(cat $FICHERO |wc -l)
   MD51=$(md5Lineas $FICHERO $((NUM_LINEAS - 1)))
   MD52=$(cat $FICHERO| grep MD5SUM= | awk -F'=' '{print $2;}')
   if [ "$MD51" != "$MD52" ]; then
      if [ "$MD52" != "" ]; then
         NUM_LINEAS=$(($NUM_LINEAS - 1))
      fi
      MD5=$(md5Lineas $FICHERO $NUM_LINEAS)
      if [ "$MD52" != "" ]; then
         sed -i "s/MD5SUM=$MD52/MD5SUM=$MD5/g" $FICHERO
      else
         echo "MD5SUM=$MD5" >> $FICHERO
      fi
   fi
}

if [ "$1" = "SSH" ]; then
   SERVICE="ssh"
elif [ "$1" = "OVP" ]; then
   SERVICE="openvpn"
else
   uso
fi   
if [ "$(whoami)" != "root" ]; then sudo $0 $*; exit ; fi
if [ "$2" = "si" ]; then sudo service $SERVICE start; CAD1=no; CAD2=si;
elif [ "$2" = "no" ]; then sudo service $SERVICE stop; CAD1=si; CAD2=no;
else uso; fi

INDICES="/home/usuario/hostinger/indice4.html /home/usuario/hostinger/indice5.html"
INDICES="/home/usuario/hostinger/indice6.html"

for INDICE in $INDICES; do
   #INDICE=/tmp/indice4.html
   REAL_IP=""
   while [ "$(echo $REAL_IP | grep -E '([0-9]{1,3}\.){3}[0-9]{1,3}')" = "" ]; do
      REAL_IP=$(realIp| head -1 | awk '{print $1;}')
   done 
   LINEA=$(cat $INDICE | grep GLOBAL_TUN_IP | head -1)
   LINEAS="GLOBAL_TUN_IP=$REAL_IP"
   sed -i "s/${LINEA}/${LINEAS}/g" $INDICE
   for SERV in iesinclan aulasrv1  ubu ubu15 Free10; do
      sed -i "s/${SERV}_TUN_${1}=${CAD1}/${SERV}_TUN_${1}=${CAD2}/g" $INDICE
   done   
   sed -i "s/GLOBAL_TUN_${1}=${CAD1}/GLOBAL_TUN_${1}=${CAD2}/g" $INDICE
   PWD=$(pwd)
   ponerMD5 $INDICE  
   #srv2aula_TUN_SSH=no
   #srv2aula   
done

cd /home/usuario/hostinger
./actualiza2.sh
TEMP=$(tempfile)
for INDICE in $INDICES; do
   wget -O - http://ubuin.noip.me/$(basename $INDICE) 2>/dev/null | openssl enc -d -aes-256-ctr -k "clave$(date -u +'%Y-%m-%d')" > $TEMP
   if [ "$(md5sum $TEMP | cut -f1 -d ' ')" != "$(md5sum $INDICE|cut -f1 -d ' ')" ]; then eecho habla -n Error en fichero: $INDICE; fi
done   
cd $PWD






