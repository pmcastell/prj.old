#!/bin/bash
cambiarIface() {
    IFACES=$(ip a | grep 'state UP' | grep -oE '(wlan[0-9]+|eth[0-9]+)')
    NUM_IFACES=$(ip a | grep 'state UP' | grep -oE '(wlan[0-9]+|eth[0-9]+)' | wc -l)
    if [ $NUM_IFACES -gt 1 ]; then
       for IFACE in $IFACES; do
          IP=$(ifconfig $IFACE | grep inet  | grep -v inet6 | awk -F':' '{print $2;}' | awk '{print $1;}')
          RED=$(echo $IP | awk -F'.' '{print $1"."$2"."$3;}')
          ROUTER=$(route -n | grep $IFACE | grep -E ^0.0.0.0 | awk '{print $2;}')
          if [ "$(ping -c 4 $ROUTER 2>&1 | grep '100% packet loss')" != "" ]; then continue; else break; fi
       done
    else
       IFACE=$IFACES
    fi
    echo cambiando a $IFACE
}    
toggleVoice() {
   if [ "$HABLAR" = "NO" ]; then
      HABLAR=SI
   else
      HABLAR=NO
   fi
   echo HABLAR: $HABLAR
}      
   
   
if [ "$1" = "" ]; then IFACE=wlan20; else IFACE=$1; fi
if [ "$2" = "" ]; then DEVS="22 23 28 32"; else DEVS=$2; fi

trap cambiarIface SIGHUP
trap toggleVoice SIGTRAP

TEMP="/tmp/rutasNuevas.txt"

if [ ! -f $TEMP ]; then touch $TEMP; fi
if [ "$(whoami)" != "root" ]; then sudo $0 $*; fi
while true; do
   for i in $DEVS; do
      if [ "$(ping -c 4 10.$i.$i.$i 2>&1 | grep '100% packet loss')" != "" ]; then
         while true; do
            #IP_TOR="$(tcpdump -c 1 -i $IFACE -n dst port 443 | awk '{print $3}' | awk -F'.' '{print $1"."$2"."$3"."$4;}')"
            IP_TOR="$(tcpdump -c 1 -i $IFACE -n dst port 443 2>/dev/null | egrep -o '([0-9]{1,3}\.){4}[0-9]{1,5} >' | awk '{print $1}' | awk -F'.' '{print $1"."$2"."$3"."$4;}')"
               if [ "$IP_TOR" != "" ] && [ "$(netstat -onatup | grep sslh | grep $IP_TOR)" = "" ]; then break; fi
            sleep 1;
         done
         
         DIR_IP_LOCAL="$(ifconfig $IFACE | grep inet | head -1 | awk '{print $2;}' | awk -F':' '{print $2;}')"
         echo $IP_TOR entrante
         if [ "$(echo $IP_TOR | awk -F'.' '{print $1;}')" = "127" ]; then continue; fi
         if [ "$(echo $IP_TOR | awk -F'.' '{print $1$2;}')" = "192168" ]; then continue; fi
         if [ "$IP_TOR" != "" ] && [ "$(route -n | grep $IP_TOR)" = "" -a "$IP_TOR" != "$DIR_IP_LOCAL" ]; then # -a "$(wget -O - http://$IP_TOR/ 2>/dev/null | grep Tor)" != "" ]; then
            sudo eecho route add -host $IP_TOR gw r1
            if [ "$HABLAR" = "SI" ]; then  habla -n Se ha añadido la ruta $IP_TOR; fi
            sudo netstat -onatup | grep -i sslh
            ping -c 4 $IP_TOR &> /dev/null
            if [ "$(cat $TEMP | grep $IP_TOR)" = "" ]; then
               echo $IP_TOR >> $TEMP
            fi
         fi
      fi
   done
   sleep 20
   TEMP2=$(tempfile)
   while read IP_TOR; do 
      if [ "$(sudo netstat -onatup | grep sslh | grep $IP_TOR )" = "" ]; then
         eecho sudo route del -host $IP_TOR
      else
         echo $IP_TOR >> $TEMP2
      fi
   done < $TEMP 
   rm $TEMP
   mv $TEMP2 $TEMP   
done

