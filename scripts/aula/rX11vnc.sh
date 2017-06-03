#!/bin/bash

uso() {
   echo Uso: $0 '<servidor(9,10,11,12,15)> [<display>]'
   exit 1
}
if [ "$1" = "" ]; then uso; fi
if [ "$2" = "" ]; then DISP="0"; else DISP="$2"; fi
SERVER="10.10.10.$1"

if [ "$1" = "9" ]; then USER="fcriadon"; else USER="franav"; fi
sshpass -p $(/scripts/aula/claves.sh $USER)  ssh $USER@$SERVER "sudo killall x11vnc; sleep 1 && echo reiniciando && sudo /root/x11vncRoot.sh  $DISP; sleep 1; ps aux | grep -i x11vnc"
#vncviewer -Locale es_ES $SERVER:11 &

