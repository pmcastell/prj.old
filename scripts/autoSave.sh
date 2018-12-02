#!/bin/bash

WORIG=$(xdotool getactivewindow)
BROWSERS=('Chrome' 'Firefox')
sleep 0.5
xdotool key Ctrl+s
for((i=0; i<${#BROWSERS[@]};i++)); do
    BROW=${BROWSERS[$i]}
    w=$(wmctrl -l | egrep -i $BROW  | awk '{print $1;}')
    [ "$w" != "" ] && xdotool windowactivate $w && sleep 0.5 && xdotool key Ctrl+F5
done
xdotool windowactivate $WORIG    
