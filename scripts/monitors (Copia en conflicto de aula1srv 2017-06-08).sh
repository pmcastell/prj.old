#!/bin/bash

maxima_resolucion() {
/usr/bin/xrandr -q | egrep -vw "connected" | egrep -o '[0-9]{1,4}x[0-9]{1,4}'  | sort -r -n | ( read R1; while read R2; do
   [ "$R1" = "$R2" ] && echo $R1 && break
   R1=$R2
done)
}

listaMonitores() {
   xrandr -q | grep -w connected | awk '{print $1;}'
}   

listaMonitoresRes() {
   listaMonitores | while read M; do
      RES=$(xrandr -q | grep -A1 $M | tail -1 | awk '{print $1;}')
      echo "$M $RES"
   done
}   

