#!/bin/bash
GDM_USR="117"
while true; do
   AUTH=$( ps aux | grep Xorg | grep Xauthority | tail -1 | awk  '{print $ 16;}')
   VT=$(ps aux | grep Xorg | grep Xauthority | tail -1 | egrep -o 'vt[0-9]+')
   #echo "VT: $VT , VTN: ${VT:2}"
   DISP=$(( ${VT:2} - 1 ))
   [ "$AUTH" != "" ] && [ "$VT" != "" ] && [ "$(ps aux | grep x11vnc | grep -v grep | grep $AUTH)" = "" ] && /usr/bin/x11vnc -auth $AUTH -display :${DISP}  #&& echo /usr/bin/x11vnc -auth $AUTH -display :${DISP}
   sleep 5
done 
#sudo x11vnc -auth /run/user/117/gdm/Xauthority -display :0


##!/bin/bash

#while true; do
#    AUTH=$(ps aux | grep Xorg | grep vt | grep /run/user | grep -v grep | tail -1 | egrep -o '/run/user.* ' | awk '{ print $1; }')
#    DISPLAY=$(ps aux | grep Xorg | grep vt | grep /run/user | grep -v grep | tail -1 | egrep -o 'vt[0-9]+' | egrep -o '[0-9]+')
#    DISPLAY=$(($DISPLAY-1))
#    x11vnc -auth ${AUTH} -display :${DISPLAY}
#done 
