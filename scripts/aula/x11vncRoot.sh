#!/bin/bash

if [ "$(ps aux | grep -i x11vnc | grep /var/run/lightdm | grep -v grep)" = "" ]; then
   /usr/bin/x11vnc -capslock -rfbauth /root/.vnc/passwd -autoport 5911 -auth /var/run/lightdm/root/:0 -display :0 -forever -loop  &> /dev/null &
fi   
