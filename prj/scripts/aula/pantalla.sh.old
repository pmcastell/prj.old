#!/bin/bash

uso() {
   echo uso: $0 '<num-pc>'
   exit 1
}
if [ $# -lt 1 ]; then uso; fi
if [ "$2" != "" ]; then DISP=$2; else DISP=0; fi
PORT=$1
PORT=${PORT:1:2}
PORT=$(( $PORT + 10 ))
vncviewer -listen 59${PORT} &
ssh lliurex@10.2.1.$1 sudo x11vnc --auth /var/run/lightdm/root/:$DISP --display :$DISP --connect 10.2.1.254:59${PORT}&>/dev/null &
