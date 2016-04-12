#!/bin/bash

# Set this to your username
USERNAME="usuario"
AUTH=/var/lib/lightdm/.Xauthority
#XAUTH=$(/usr/bin/ls /var/run/xauth)

# Are we root, or just some schmuck?
#if [ $(whoami) = "root" ]
#then
#   # Now, do we have a password file created?
#   if [ -e /home/$USERNAME/.vnc/passwd ]
#   then
   #XAUTH=$(sudo ls /var/run/gdm/ | grep $USERNAME)
   #if [ "$XAUTH" = "" ];
   #then
   #   XAUTH=$(sudo ls /var/run/gdm/*)
   #fi
   #sudo x11vnc -display :0 -auth $AUTH -rfbport 5905 -rfbauth /home/$USERNAME/.vnc/passwd -forever
#   else
#      echo -e "You have not set a password yet, run: x11vnc -storepasswd as $USERNAME."
#      exit 1
#   fi
#else
#   echo "You need to be root to do this (or use sudo -b ./startx11vnc)."
#   exit 1
#fi
#AUTH=/var/lib/lightdm/.Xauthority
#AUTH=/home/$USERNAME/.Xauthority
#while [ 1 ]; do  x11vnc -display :0 -auth $AUTH -rfbport 5905 -rfbauth /home/$USERNAME/.vnc/passwd -forever; #sleep 1;done
x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
exit 0 

