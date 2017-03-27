#!/bin/bash

sudo firewall
menuRes
cumples &
dropbox start -i
#x11vnc -display :0 -rfbauth /home/usuario/.vnc/passwd -forever &> /dev/null &
x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
vncserver-x11 -ShowStatus &
actualiza &
$SHELL


