#!/bin/bash

#(epoptes &) ; sleep 1.5 && xdotool type franav && xdotool key Tab && xdotool type $(/home/franav/aula/claves.sh franav) && xdotool key KP_Enter
epoptes &
while true; do
   sleep 0.5
   if [ "$(wmctrl -l | grep -i epoptes)" != "" ]; then break; fi
done
#wmctrl -a epoptes
#xdotool type franav 
#wmctrl -a epoptes
#xdotool key Tab
wmctrl -a epoptes
xdotool type $(/home/franav/aula/claves.sh franav) && xdotool key KP_Enter

