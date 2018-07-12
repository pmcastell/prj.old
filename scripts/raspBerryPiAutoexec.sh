#!/bin/bash
redOpenvpn() {
    fRedActual="/root/redActual.txt"
    [ ! -f $fRedActual ] && echo 3 > $fRedActual
    R=$(( ($(cat $fRedActual) + 1) % 4 ))
    echo $R > $fRedActual
    case "$R" in 
        0) /usr/sbin/openvpn --config /home/usuario/freeVpns/book/euro1/vpnbook-euro1-udp25000.ovpn &
           echo red vpn seleccionada: euro1
           ;;
        1) /usr/sbin/openvpn --config /home/usuario/freeVpns/book/euro2/vpnbook-euro2-udp25000.ovpn &
           echo red vpn seleccionada: euro2
           ;;
        2) /usr/sbin/openvpn --config /home/usuario/freeVpns/book/fr1/vpnbook-fr1-udp25000.ovpn &
           echo red vpn seleccionada: fr1
           ;;
        3) /usr/sbin/openvpn --config /home/usuario/freeVpns/book/de233/vpnbook-de233-udp25000.ovpn &
           echo red vpn seleccionada: de233
           ;;
    esac       
}
main() {
    /bin/echo "i:$(/bin/date)" >> /root/inicio.txt
    export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
    /bin/echo '##########' enviar la salida de los comandos a fichero autoeec.log
    exec > /tmp/autoexec.log
    exec 2>&1
    /bin/echo '##########' configurar los interfaces de red
    IFAP=wlan0
    IFCON=wlan1
    #/usr/bin/macchanger -r $IFAP
    /usr/bin/macchanger -r $IFCON
    /scripts/puntoAcceso.sh $IFAP MiCasa Montoro65 172.16.1 133 235 &
    /sbin/ip a add 192.168.1.27/24 dev $IFCON
    /sbin/ip link set dev $IFCON up
    /sbin/ip route add default via 192.168.1.1
    /sbin/wpa_supplicant -B -i $IFCON -c /root/MOVISTAR_E360 -Dwext &
    /bin/echo '##########' sincronizar hora
    /usr/sbin/ntpdate es.pool.ntp.org &
    /bin/echo '##########' actualizar ddns
    /scripts/duckdns.sh micasa6543 "" 3a115b52-3c62-42ac-93b4-47ed6ea18423 &
    /bin/echo '##########' obtener la clave y configurar red openvpn
    /scripts/bookPass.sh
    #dar un tiempo para que se inicie la openvpn
    redOpenvpn
    #Esperar conexión
    /bin/sleep 5
    /bin/echo '##########' esperar conexiones tunelSsh y añadir ruta tor a través de r1
    /scripts/aula/torRoute.sh $IFCON &
    /bin/echo '##########' firewall
    /scripts/firewall.sh
    /bin/echo '##########' actualizar el sistema
    /scripts/actualiza.sh &
    /sbin/route add -host ceutam24.duckdns.org gw r1
    /bin/echo "f:$(/bin/date )" >> /root/inicio.txt
}
[ $SHLVL -gt 2 ] && main
