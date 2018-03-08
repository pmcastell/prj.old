#!/bin/bash

# trap ctrl-c and call ctrl_c()
function ctrl_c() {
   echo "CTRL+C pulsado: saliendo"
   sudo vpn stop
   exit 1
}
I=1
[ "$1" = "--remove" ] && [ $# -lt 3 ] && echo Error en parámeros && exit 1
[ "$1" = "--remove" ] && [ "$2" != "" ] && R1="$2" && [ "$3" != "" ] && R2="$3" && shift && shift && shift
#echo "R1: $R1 , R2: $R2 , S1: $1 , S2: $2 , S3: $3"
PAIS="gb"
[ "$1" = "us" ] && PAIS="us" && shift
[ "$1" != "" ] && BUSCA="$1" && echo "BUSCA: $BUSCA"
for u in $(ls /home/usuario/freeVpns/${PAIS}-*); do
   #echo BUSCA: $BUSCA && exit 
   [ "$BUSCA" != "" ] && [ "$BUSCA" != "$I" ] && I=$(($I+1)) && echo I: $I && continue 
   BUSCA="" 
   [ "$R2" != "" ] && [ "$I" -le "$R2" ] && [ "$R1" -le "$I" ] && I=$(($I+1)) && echo Borrando: $u && rm $u && continue
   
   #echo "BUSCA: $BUSCA . R1: $R1 . R2: $R2. I: $I" && exit
   sudo vpn stop
   echo ------------------------------------------------------------------------------------------------------------
   echo $I: sudo openvpn --config $u
   sudo openvpn --config $u &  
   echo ------------------------------------------------------------------------------------------------------------
   while true; do
      read -rsn1 KEY
      [ "$KEY" = "k" ] && break
      [ "$KEY" = "R" ] && rm $u && echo Borrado: $u && break
   done
   echo ------------------------------------------------------------------------------------------------------------
   [ "$KEY" != "R" ] && I=$(($I+1))
done
