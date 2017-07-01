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
encuentraDir() {
   if [ -d /home/usuario/$1 ]; then
      echo /home/usuario/$1
   elif [ -d /home/franav/$1 ]; then
      echo /home/franav/$1;
   fi
}

if [ "$1" = "SSH" ]; then
   SERVICE="ssh"
elif [ "$1" = "OVP" ]; then
   SERVICE="openvpn"
else
   uso
fi   
if [ "$(whoami)" != "root" ]; then sudo $0 $*; exit $?; fi
if [ "$2" = "si" ]; then sudo service $SERVICE start; CAD1=no; CAD2=si;
elif [ "$2" = "no" ]; then sudo service $SERVICE stop; CAD1=si; CAD2=no;
else uso; fi
DIR_BASE="/scripts"
DIR_BASE_HOSTINGER=$(encuentraDir "hostinger")
if [ "$DIR_BASE_HOSTINGER" = "" ]; then echo No se han podido encontrar ficheros de índice; habla -n No se han podido encontrar ficheros de índice; exit 2; fi
INDICES="$DIR_BASE_HOSTINGER/indice4.html $DIR_BASE_HOSTINGER/indice5.html"
INDICES="$DIR_BASE_HOSTINGER/indice6.html $DIR_BASE_HOSTINGER/indice5.html"
#PWD=$(pwd)
for INDICE in $INDICES; do
   #INDICE=/tmp/indice4.html
   REAL_IP=""
   while [ "$(echo $REAL_IP | grep -E '([0-9]{1,3}\.){3}[0-9]{1,3}')" = "" ]; do
      REAL_IP=$(realIp| head -1 | awk '{print $1;}')
   done 
   LINEA=$(cat $INDICE | grep GLOBAL_TUN_IP | head -1)
   LINEAS="GLOBAL_TUN_IP=$REAL_IP"
   sed -i "s/${LINEA}/${LINEAS}/g" $INDICE
   for SERV in iesinclan aula1srv aula2srv ubu ubu15 Free10; do
      sed -i "s/${SERV}_TUN_${1}=${CAD1}/${SERV}_TUN_${1}=${CAD2}/g" $INDICE
   done   
   sed -i "s/GLOBAL_TUN_${1}=${CAD1}/GLOBAL_TUN_${1}=${CAD2}/g" $INDICE
   ponerMD5 $INDICE  
   #srv2aula_TUN_SSH=no
   #srv2aula   
done
#echo $PASSWD | openssl enc -e -aes-256-ctr -k "clave$(date -u +'%Y-%m-%d')" | base64 > $DIR_BASE_HOSTINGER/indicep.html
#echo $PASSWD > $DIR_BASE_HOSTINGER/indicep.html
/m/Mios/Personal/Privado/AgendasClaves/genClavesIndicePass.sh
#cd $DIR_BASE_HOSTINGER
$DIR_BASE_HOSTINGER/actualiza2.sh "$INDICES $DIR_BASE_HOSTINGER/indicepass.html"
TEMP=$(tempfile)
for INDICE in $INDICES; do
   wget -O - https://ganimedes.000webhostapp.com/$(basename $INDICE) 2>/dev/null | $DIR_BASE/desencriptar.sh > $TEMP
   if [ "$(md5sum $TEMP | cut -f1 -d ' ')" != "$(md5sum $INDICE|cut -f1 -d ' ')" ]; then 
      habla -n "Error en fichero: $INDICE. Voy a Borrarlo"; ERROR=true;
      lftp  ftp://u964077031.ganimedes:$(/scripts/getMyPass.sh hostinger)@ftp.ganimedes.esy.es -e "set ssl:verify-certificate no; rm indice6.html;bye" 
      sudo $0 $@
      exit $?
   fi
done   
#cd $PWD
if [ $ERROR ]; then exit 2; else exit 0; fi

