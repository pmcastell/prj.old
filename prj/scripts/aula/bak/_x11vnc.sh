#!/bin/bash
###echo "$(ps aux | grep 'x11vnc.sh' | grep -v grep | grep -v gedit | wc -l)"
###echo "$(ps aux | grep 'x11vnc.sh' | grep -v grep | grep -v gedit)"
if [ "$(ps aux | grep '/bin/bash /home/franav/x11vnc.sh' | grep -v grep | grep -v gedit | wc -l)" -gt "2" ];
then
   exit 1
fi
while [ 1 ]; 
do 
   if [ "$(ps aux | grep usuario@franc | grep -v grep )" = "" ];
   then
      ssh -p 443 -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -L 5934:localhost:9999 -N usuario@ubu.noip.me &
      sleep 3
   fi
   vncserver -geometry 1440x900 :1 &> /dev/null
   x11vnc -loop -modtweak  -xkb -connect localhost:5934 -display :1; 
   sleep 1; 
done

###!/bin/bash
###ENCONTRADO=$(/bin/ps aux |  /bin/grep x11vnc | /bin/grep localhost | /bin/grep -v grep )
###if [ "$ENCONTRADO" != "" ];
###then
###   exit 1
###fi
####while [ 1 ];
####do
###   vncserver -geometry 1440x900 :1
###   ssh -p 443 -L 5934:localhost:9999 -N usuario@franc.no-ip.org &
###   /usr/bin/x11vnc -connect localhost:5934 -display :1 &> /dev/null &
####   sleep 300
####done


