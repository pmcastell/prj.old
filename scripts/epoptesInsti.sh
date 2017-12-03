#!/bin/bash

#5 tabs , alt+c , bajo, dcha, bajo, bajo, bajo, intro
#xdotool key Tab Tab Tab Tab Tab ctrl+a alt+c KP_Down KP_Right KP_Down KP_Down KP_Down KP_Enter
#(epoptes &) && sleep 1 && xdotool key Tab Tab Tab Tab Tab ctrl+a alt+c KP_Down KP_Right KP_Down KP_Down KP_Down KP_Enter
   
epoptes &
sleep 2
#KEYS="Tab Tab Tab Tab Tab" 
xdotool key Tab Tab Tab Tab Tab
KEYS="ctrl+a alt+c KP_Down KP_Right KP_Down KP_Down KP_Down KP_Enter"
for k in $KEYS; do
   sleep 0.25
   eecho xdotool key $k
done   
