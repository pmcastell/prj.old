#!/bin/bash

resucita() {
   while true; do
      sudo pkill --signal SIGHUP wpa_supplicant
      sleep 1
      INTERFAZ=$(iwconfig 2>&1 | grep -v "no wireless" | grep ESSID | grep -v "ESSID:off" | awk '{print $1;}')
      [ "$INTERFAZ" != "" ] && break
   done
   RED=$(ip a show dev $INTERFAZ | grep inet | grep -v inet6 | awk '{print $2;}' | awk -F'/' '{print $1;}')
   sudo ifconfig $INTERFAZ ${RED}.27/24 up
   sudo route add default gw ${RED}.1
   sleep 1
   ping -c 4 r1
   sudo pkill --signal SIGHUP openvpn
   sleep 1
   /home/usuario/rutasSslh.sh
}

ERRORES=0
while true; do 
   GATEWAY=$(netstat -rn | grep -iE '(default|^0.0.0.0)' | tail -1 | awk '{ print $2;}' | grep -v 0.0.0.0 )
   if [ "$GATEWAY" = "" -a -f /etc/rc.d/routing ];    then
      sudo /etc/rc.d/routing restart
   fi
   DNS1=$(cat /etc/resolv.conf | grep -i nameserver | grep -v '#' | grep -v grep | head -1 | awk -F'nameserver' '{print $2;}')
   #INTERFAZ=$(interfaces | grep -i eth | head -1)
   INTERFAZ=$(netstat -rn | grep -E '^0.0.0.0|default' | tail -1 | awk '{ print $NF; }')
   eecho sudo arping -I $INTERFAZ -c 4 $GATEWAY
   ping -c 4 $GATEWAY
   #DNS1=squid
   ping -c 4 $DNS1
   #ping -c 4 80.58.0.33
   if [ $? -gt 4 ];    then
      ERRORES=$(expr $ERRORES + 1) 
      if [ $ERRORES -gt 0 ]; then
         hablaf -n se ha perdido la conexión 
         hablaf -n intentando resucitar el interfaz
         resucita
         (sleep 1 && wmctrl -F -a "Conexion Perdida" -b add,above) &
         (zenity --info --title="Conexion Perdida" --text="Se ha perdido la conexión")
         PID=$(ps ax | grep openvpn | grep -v grep | grep -v sudo | grep -v sh  | awk '{print $1;}')
         #if [ "$PID" = "" ]; then
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
   sleep 5
done

