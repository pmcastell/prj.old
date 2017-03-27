#!/bin/sh
#HW=10 #BASE=00:01:38:ED:76 #BASE=5c:d9:98:e5:68
/usr/sbin/service network-manager stop
INTERFACES=$(/sbin/ifconfig -a | /bin/grep Link | /bin/grep -oE "^[a-zA-Z].*" | /usr/bin/awk '{print $1;}')
for i in $INTERFACES; do
   if [ "$(echo $i | grep vmnet)" != "" ]; then
      /sbin/ifconfig $i down
   fi
   /usr/local/bin/ponerRandomMAC $i
done

   #if [ "$(echo $i | grep -E '(eth|wlan)')" != "" ]; then
      #/sbin/ifconfig $i down
      #/sbin/ifconfig $i hw ether $BASE:$HW up
      #HW=$(($HW + 1))
   #fi
