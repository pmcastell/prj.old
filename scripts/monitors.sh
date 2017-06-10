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


maxResMonitor() {
   [ "$1" = "" ] && echo "Necesito el nombre de un monitor" && return
   MON="$1"
   xrandr -q | 
   (while read L; do
      [ "$(echo $L | grep $MON)" != "" ] && break
   done
   TEMP=$(tempfile)
   while read L; do
      [ "$(echo $L | grep  connected)" != "" ] && break
      RES=$(echo $L | egrep -o [0-9]+x[0-9]+)
      [ "$RES" = "" ] && break
      echo $RES >> $TEMP
   done
   PRIMERO11=$(cat $TEMP | sort -rn | head -1 | awk -F'x' '{print $1;}')
   PRIMERO12=$(cat $TEMP | sort -rn | head -1 | awk -F'x' '{print $2;}')
   SEGUNDO11=$(cat $TEMP | sort -rn | head -2 | tail -1 | awk -F'x' '{print $1;}')
   SEGUNDO12=$(cat $TEMP | sort -rn | head -2 | tail -1 | awk -F'x' '{print $2;}')
   
   if [ "$PRIMERO11" = "$SEGUNDO11" ]; then
      if [ "$PRIMERO12" -gt "$SEGUNDO12" ]; then
         echo "${PRIMERO11}x${PRIMERO12}"
      else
         echo "${SEGUNDO11}x${SEGUNDO12}"
      fi
   else
      cat $TEMP | sort -rn | head -1
   fi
   rm $TEMP)
}
   
listaMonitoresRes() {
   listaMonitores | while read M; do
      #RES=$(xrandr -q | grep -A1 $M | tail -1 | awk '{print $1;}')
      RES=$(maxResMonitor $M)
      echo "$M $RES"
   done
}      
