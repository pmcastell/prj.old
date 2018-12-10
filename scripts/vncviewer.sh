#!/bin/sh

while true; do  
   /usr/bin/vncviewer $1
   [ "$(ping -c 2 $1 | grep '0% packet loss')" = "" ] && break
done   
