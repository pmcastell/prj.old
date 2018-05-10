#!/bin/bash
[ "$1" = "" ] && echo Debes especificar el puerto && exit 1
sudo x11vnc -noshm -24to32 -connect_or_exit server:46601 -auth /var/run/lightdm/root/:0 -display :0
