#!/bin/bash
[ "$1" = "" ] && PROCESS="flashplayer" || PROCESS="$1"
[ "$2" = "" ] && TIME="0.5" || TIME="$2"
echo PROCESS: "$PROCESS" TIME: "$TIME"
PID=$(ps ax | grep -i $PROCESS | grep -v grep | grep -v $0 | awk '{print $1;'})
while true; do 
    sleep 0.5
    kill -s STOP $PID
    sleep 0.5
    kill -s CONT $PID
done
