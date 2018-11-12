#!/bin/bash

notifica() {
   hablaf -n se ha perdido la conexión 
   hablaf -n intentando resucitar el interfaz
   (sleep 1 && wmctrl -F -a "Conexion Perdida" -b add,above) &
   (zenity --info --title="Conexion Perdida" --text="Se ha perdido la conexión")
}
resucita() {
   while true; do
      eecho sudo pkill --signal SIGHUP wpa_supplicant
      sleep 1
      INTERFAZ=$(iwconfig 2>&1 | grep -v "no wireless" | grep ESSID | grep -v "ESSID:off" | awk '{print $1;}')
      [ "$INTERFAZ" != "" ] && break
   done
   RED=$(ip a show dev $INTERFAZ | grep inet | grep -v inet6 | awk '{print $2;}' | awk -F'/' '{print $1;}')
   [ "$RED" = "" ] && RED="172.16.1"
   sudo ifconfig $INTERFAZ ${RED}.25/24 up
   sudo route add default gw ${RED}.1
   ping -c 4 r1
   sudo pkill --signal SIGHUP openvpn
####   /home/usuario/rutasSslh.sh
}

ERRORES=0
while true; do 
   GATEWAY=$(netstat -rn | grep -iE '(default|^0.0.0.0)' | tail -1 | awk '{ print $2;}' | grep -v 0.0.0.0 )
   [ "$GATEWAY" = "" ] && [ -f /etc/rc.d/routing ] && sudo /etc/rc.d/routing restart
   [ "$GATEWAY" = "" ] && GATEWAY="172.16.1.1"
   DNS1=$(cat /etc/resolv.conf | grep -i nameserver | grep -v '#' | head -1 | awk -F'nameserver' '{print $2;}')
   [ "$DNS1" = "" ] && DNS1="8.8.4.4"
   #INTERFAZ=$(interfaces | grep -i eth | head -1)
   INTERFAZ=$(netstat -rn | grep -iE '(^0.0.0.0|default)' | tail -1 | awk '{ print $NF; }')
   [ "$INTERFAZ" = "" ] && INTERFAZ="wlan20"
   arping -I $INTERFAZ -c 4 $GATEWAY || resucita
   ping -c 4 $GATEWAY || resucita
   sudo route add -host g2 gw $GATEWAY
   ping -c 4 g2 || rescuta
   #DNS1=squid
   #ping -c 4 $DNS1
   #ping -c 4 80.58.0.33
   [ "$(dirIp 15)" = "" ] && [ "$(dirIp2 15)" = "" ] && resucita && notifica
   continue
   if [ $? -gt 0 ]; then
      echo Error haciendo ping a $DNS1
      ERRORES=$(( $ERRORES + 1 )) 
      if [ $ERRORES -gt 0 ]; then
         resucita
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

