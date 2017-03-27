#!/bin/bash
#
#RESOLUCIONES="1024x768 1152x864 1280x800 1280x1024 1360x768"
RESOLUCIONES=$(xrandr | grep -iE "^[ ]*[1-9].*" | awk '{print $1;}')
res=0
max=$(echo $RESOLUCIONES | wc -w)
echo while [ $res -lt 1 -o $res -gt $max ];
while [ $res -lt 1 -o $res -gt $max ];
do
    echo Resolución $(xrandr | grep \* | awk '{ print $1;}'):
    opt=1
    for i in $RESOLUCIONES; do
        echo $opt')' $i
        opt=$(expr $opt + 1)
    done    
    read res
done
xrandr -s $(echo $RESOLUCIONES | cut -d' ' -f$res)
kill -HUP $(ps aux | grep -i 'gnome-panel' | grep -v grep | awk '{print $2;}')


