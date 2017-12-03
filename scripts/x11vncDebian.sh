#!/bin/bash

while true; do
    AUTH=$(ps aux | grep Xorg | grep vt | grep /run/user | grep -v grep | tail -1 | egrep -o '/run/user.* ' | awk '{ print $1; }')
    DISPLAY=$(ps aux | grep Xorg | grep vt | grep /run/user | grep -v grep | tail -1 | egrep -o 'vt[0-9]+' | egrep -o '[0-9]+')
    DISPLAY=$(($DISPLAY-1))
    x11vnc -auth ${AUTH} -display :${DISPLAY}
done    
