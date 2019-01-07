#!/bin/bash

export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/scripts:/scripts/aula
[ "$(pgrep alive | wc -l)" -gt 3 ] && exit 1
. $(dirname $0)/redOpenvpn.sh

main() {
    /bin/echo "i:$(/bin/date)" >> /root/inicio.txt
    /bin/echo '##########' enviar la salida de los comandos a fichero autoeec.log
    exec > /tmp/autoexec.log
    exec 2>&1
    /bin/echo '##########' configurar los interfaces de red
    IFAP=wlan0; IFCON=wlan1;
    IFCON_IP=192.168.1.27/24; DEFAULT_GW=192.168.1.1
    #/usr/bin/macchanger -r $IFAP
    #/usr/bin/macchanger -r $IFCON
    #/sbin/ip a add $IFCON_IP dev $IFCON
    #/sbin/ip link set dev $IFCON up
    #/sbin/ip route add default via $DEFAULT_GW
    #/sbin/wpa_supplicant -B -i $IFCON -c /root/WIFICON.cfg -Dwext &
    ip link set $IFCON a 00:04:4b:47:bf:28
    CONT=1
    while [ "$CONFIG_IP" = "" ] && [ $CONT -le 5 ]; do 
        CONFIG_IP=$(/scripts/wpaWifi.sh $IFCON | egrep '([0-9]{1,3}\.){2}[0-9]{1,3}')
        CONT=$(( $CONT + 1 ))
    done
    CHANNEL=$(echo $CONFIG_IP | awk '{print $2;}')
    CHANNEL="13"
    echo "CONFIG_IP: $CONFIG_IP"
    echo /scripts/puntoAcceso.sh $IFAP MiCasa "$(/bin/echo TW9udG9ybzY1|base64 -d)" 172.16.1 133 235 $CHANNEL &
    /scripts/puntoAcceso.sh $IFAP MiCasa "$(/bin/echo TW9udG9ybzY1|base64 -d)" 172.16.1 133 235 $CHANNEL &
    IFCON_IP="$(echo $CONFIG_IP | awk '{print $1;}')"
    DEFAULT_GW=${IFCON_IP}.1
    IFCON_IP="${IFCON_IP}.27/24"
    /bin/echo '##########' sincronizar hora
    /usr/sbin/ntpdate es.pool.ntp.org &
    /bin/echo '##########' actualizar ddns
    #/scripts/duckdns.sh ceutaavm12 "" 3a115b52-3c62-42ac-93b4-47ed6ea18423 &
    /scripts/duckdns.sh ceuta6543 "" 3a115b52-3c62-42ac-93b4-47ed6ea18423 &
###    /bin/echo '##########' obtener la clave y configurar red openvpn
###    /scripts/bookPass.sh
###    redOpenvpn 2 &
###    #dar un tiempo para que se inicie la openvpn
###    /bin/sleep 5
    /bin/echo '##########' esperar conexiones tunelSsh y añadir ruta tor a través de r1
    /scripts/aula/torRoute.sh $IFCON &
    /bin/echo '##########' firewall
    /root/firewall.sh
    /bin/echo '##########' actualizar el sistema
    /scripts/actualiza.sh &
    #/sbin/route add -host ceutam24.duckdns.org gw r1
    /root/alive.sh $IFCON $IFCON_IP $DEFAULT_GW &
    #echo /root/alive.sh $IFCON $IFCON_IP $DEFAULT_GW >> /root/inicio.txt 
    /bin/echo "f:$(/bin/date )" >> /root/inicio.txt
}
#[ $SHLVL -gt 1 ] && main
main "$@"

