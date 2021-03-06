#!/bin/bash

. /scripts/uso.sh
. /scripts/monitors.sh

   
([ "$1" = "" ] || [ "$1" = "-h" ] || ([ "$1" != "on" ] && [ "$1" != "off" ]) ) && uso "Uso: $0 <on|off>\nOn: hace que el monitor se ponga en espejo con el proyector\noff: desactiva el proyector"
[ "$(listaMonitores | wc -l)" -lt 2 ] && echo "Este Equipo sólo tiene un monitor. No se puede 'espejar' nada" && exit 3
MONITOR1=$(listaMonitores | egrep '(VGA|DisplayPort)')
[ "$MONITOR1" = "" ] && MONITOR1=$(listaMonitores|tail -1)
[ "$MONITOR1" = "" ] && echo no encuentro Monitor VGA && exit 2
MONITOR2=$(listaMonitores | grep -v $MONITOR1 | tail -1)
[ "$MONITOR2" = "" ] && echo no encuentro segundo monitor && exit 3

if [ "$1" = "on" ]; then
   MAX_RES_COMUN=$(maxima_resolucion)
   /usr/bin/xrandr --output $MONITOR1 --mode $MAX_RES_COMUN --output $MONITOR2 --mode $MAX_RES_COMUN --same-as $MONITOR1
else
   MAX_RES1=$(listaMonitoresRes | grep $MONITOR1 | awk '{print $2;}')
   MAX_RES2=$(listaMonitoresRes | grep $MONITOR2 | awk '{print $2;}')
   if [ "$2" = "on" ]; then
      /usr/bin/xrandr --output $MONITOR1 --mode $MAX_RES1 --output $MONITOR2 --mode $MAX_RES2 --left-of $MONITOR1      
   else 
      /usr/bin/xrandr --output $MONITOR1 --off --output $MONITOR2 --mode $MAX_RES2
   fi
fi   

 
