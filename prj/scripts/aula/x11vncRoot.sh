#!/bin/bash
if [ "$1" = "" ]; then DISP="0"; else DISP="$1"; fi
PORT=$(( 5911 + $DISP ))
if [ "$(ps aux | grep -i x11vnc | grep /var/run/lightdm | grep -v grep)" = "" ]; then
   /usr/bin/x11vnc -capslock -rfbauth /root/.vnc/passwd -autoport $PORT -auth /var/run/lightdm/root/:$DISP -display :$DISP -forever -loop  &> /dev/null &
fi   
