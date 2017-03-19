#!/bin/bash

if [ "$(hostname)" = "aula1srv" ]; then xrandr -s 1152x864 ; fi
if [ "$SHELL" = "" ]; then SHELL="/bin/bash"; fi
[ "$USER" = "franav" ] && fClaves="/home/franav/claves/claves.txt"
[ "$USER" = "fcriadon" ] && fCllaves="/datos/usuarios/profesores/fcriadon"
if [ "$fClaves" != "" ]; then 
   while [ ! -f $fClaves ] || [ "$(cat $fClaves | grep franav)" = "" ]; do
      fusermount -u $(dirname "$fClaves")
      gnome-terminal -e "/usr/bin/encfs /home/franav/.claves /home/franav/claves"
      read
   done   
fi
sleep 1
epoptes &
while true; do
   sleep 0.5
   if [ "$(wmctrl -l | grep -i epoptes)" != "" ]; then break; fi
done
wmctrl -a epoptes
xdotool type franav 
wmctrl -a epoptes
xdotool key Tab
wmctrl -a epoptes
xdotool type $(/home/franav/aula/claves.sh franav) && xdotool key KP_Enter
sleep 1
nemo &
gnome-terminal &
sleep 1
wmctrl -a epoptes

