#!/bin/bash
echo "dirname: $(dirname $1), basename: $(basename $1)"
cd $(dirname $1)
#/snap/bin/brackets $(basename $1)
/snap/bin/brackets &
sleep 5
while [ "$WIN_ID" = "" ]; do
    WIN_ID="$(wmctrl -l | awk '$NF=="Brackets"' | awk '{print $1;}')"
    sleep 1
done    
eecho wmctrl -i -a $WIN_ID
wmctrl -i -a $WIN_ID
xdotool key ctrl+O
#&& xdotool key Home && xdotool key shift+End && xdotool type $(basename $0) && xdotool key KP_Enter
