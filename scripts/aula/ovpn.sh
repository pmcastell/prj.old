#!/bin/bash
TUN=15
if [ "$(/usr/bin/wget http://ubuin.noip.me/tunelInclanOVPN.txt -O - 2> /dev/null)" = "si" ]; then
   /usr/sbin/openvpn --connect-retry-max 3 --remote ubu.noip.me 443 --proto tcp-client --dev tun$TUN --ifconfig 10.7.0.7 10.7.0.6 --port 443 --secret /root/key
else
   INTENTOS=0
   while [ $INTENTOS -le 10 ]; do
      if [ $INTENTOS -le 5 ]; then SENIAL="-TERM"; else SENIAL="-9"; fi
      PID=$(/bin/ps  ax | /bin/grep -i tun$TUN | /bin/grep -v /bin/grep | /usr/bin/awk '{print $1;}')
      if [ "$PID" != "" ]; then
         /bin/kill $SENIAL $PID
      else
         break
      fi
      INTENTOS=$(($INTENTOS+1))
   done
fi
