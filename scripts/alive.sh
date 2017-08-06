#!/bin/bash

ERRORES=0
while true; do 
   GATEWAY=$(netstat -rn | grep -iE '(default|^0.0.0.0)' | tail -1 | awk '{ print $2;}' | grep -v 0.0.0.0 )
   if [ "$GATEWAY" = "" -a -f /etc/rc.d/routing ];    then
      sudo /etc/rc.d/routing restart
   fi
   DNS1=$(cat /etc/resolv.conf | grep -i nameserver | grep -v '#' | grep -v grep | head -1 | awk -Fnameserver '{print $2;}')
   #INTERFAZ=$(interfaces | grep -i eth | head -1)
   INTERFAZ=$(netstat -rn | grep -E '^0.0.0.0|default' | tail -1 | awk '{ print $NF; }')
   eecho sudo arping -I $INTERFAZ -c 4 $GATEWAY
   ping -c 4 $GATEWAY
   #DNS1=squid
   ping -c 4 $DNS1
   #ping -c 4 80.58.0.33
   if [ $? -gt 0 ];    then
      ERRORES=$(expr $ERRORES + 1) 
      if [ $ERRORES -gt 0 ]; then
         hablaf -n se ha perdido la conexión 
         (sleep 1 && wmctrl -F -a "Conexion Perdida" -b add,above) &
         (zenity --info --title="Conexion Perdida" --text="Se ha perdido la conexión")
         sudo kill -HUP $(pgrep wpa_supplicant); sudo ifconfig $INTERFAZ 192.168.1.27/24 && sudo route add default gw r1; 
         #zenity --info --text="Se ha perdido la conexión" &
       
         #hablaf -n se ha perdido la conexión && sleep 1
         #hablaf -n se ha perdido la conexión && sleep 1
         #hablaf -n se ha perdido la conexión && sleep 1
         PID=$(ps ax | grep openvpn | grep -v grep | grep -v sudo | grep -v sh  | awk '{print $1;}')
         #if [ "$PID" = "" ];
         #then
            for ((i=1;i<=5;i++)); do
               ####sudo vpn stop
               sleep 1
               ####mata rsync
            done
            ###eecho sudo openvpn --config /home/usuario/freeVpns/book/uk1/vpnbook-uk1-tcp443.ovpn &
            ####eecho sudo openvpn --config /home/usuario/freeVpns/book/euro1/vpnbook-euro1-udp25000.ovpn &
            ####sleep 10
            ####eecho sudo openvpn --remote 80.36.87.166 443 udp --config openvpn/franc-nuevo2.ovpn &
         #else
         #  eecho sudo kill -HUP $PID
         #fi
         ERRORES=0
      fi
   fi
   sleep 20
done

