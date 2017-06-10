#!/bin/bash

. /scripts/uso.sh
. /scripts/monitors.sh

([ "$1" = "" ] || [ "$1" = "-h" ]) && uso "Uso: $0 <on|off>\nOn: hace que el monitor se ponga en espejo con el proyector\noff: desactiva el proyector"
MONITOR1=$(listaMonitores | grep VGA)
[ "$MONITOR1" = "" ] && echo no encuentro Monitor VGA
MONITOR2=$(listaMonitores | grep -v $MONITOR1 | tail -1)
if [ "$1" = "on" ]; then
   MAX_RES_COMUN=$(maxima_resolucion)
   /usr/bin/xrandr --output $MONITOR1 --mode $MAX_RES_COMUN --output $MONITOR2 --mode $MAX_RES_COMUN --same-as $MONITOR1
else
   /usr/bin/xrandr --output $MONITOR1 --off --output $MONITOR2 -s 0 
fi   

 
