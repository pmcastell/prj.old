#!/bin/bash

uso() {
   echo uso: $0 '<num-pc>'
   exit 1
}
puertoLibre() {    
    FPORT="/tmp/___pantalla_PORT___.txt"
    if [ ! -f  $FPORT ]; then
       for((PORT=5920;PORT<=65535;PORT++)); do
          if [ "$PORT" = "65535" ]; then 
             zenity --info --title="No se encuentra puerto libre" --text="No se ha encontrado un puerto libre"; 
             exit 2; 
          fi
          if [ "$(netstat -lnt | grep ":$PORT")" = "" ]; then break; fi
       done
       echo $PORT > $FPORT
       vncviewer -listen ${PORT} &
    else
       PORT=$(cat $FPORT)
    fi  
    echo $PORT 
}

if [ $# -lt 1 ]; then uso; fi
if [ "$2" != "" ]; then DISP=$2; else DISP=0; fi

PORT=$(puertoLibre)   
echo "PORT $PORT" 
[ "$(ip a | grep 172.124.117)" != "" ] && RED="172.124.117" && IP="${RED}.100" && USER="profesor" && ID_RSA=" -i /home/${USER}/.ssh/id_rsa.puertas "
[ "$(ip a | grep 172.124.116)" != "" ] && RED="172.124.116" && IP="${RED}.100" && USER="profesor" && ID_RSA=" -i /home/${USER}/.ssh/id_rsa.puertas "
[ "$(ip a | grep 10.2.1)" != "" ] && RED="10.2.1" && IP="${RED}.254" && USER="lliurex" 
#ssh lliurex@10.2.1.$1 sudo x11vnc -noshm -24to32 --auth /var/run/lightdm/root/:$DISP --display :$DISP -connect_or_exit 10.2.1.254:${PORT}&>/dev/null &
#echo "$@"
for i in $@; do
   #echo $i
   [ $i -lt 100 ] && PC=$(( $i + 100)) || PC=$i
   #ssh $ID_RSA ${USER}@${IP}.${i} sudo x11vnc -noshm -24to32 --auth /var/run/lightdm/root/:$DISP --display :$DISP -connect_or_exit server:${PORT} &>/dev/null &
   echo ssh $ID_RSA ${USER}@${RED}.${PC} sudo sudo -u ciclomedio x11vnc  -display :0 -noshm -24to32  -connect_or_exit ${IP}:${PORT}
   ssh $ID_RSA ${USER}@${RED}.${PC} sudo sudo -u ciclomedio x11vnc  -display :0 -noshm -24to32  -connect_or_exit ${IP}:${PORT} &>/dev/null &
done   
