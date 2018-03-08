#!/bin/sh

while true; do  
   vncviewer $1
   [ "$(ping -c 2 $1 | grep '0% packet loss')" = "" ] && break
done   
