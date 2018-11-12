#!/bin/bash

export PATH="/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"

. $(dirname $0)/redOpenvpn.sh

resucita() {
    #/sbin/wpa_supplicant -B -i $INTERFAZ -c /root/WIFICON.cfg -Dwext &
    INTERFAZ="$1"
    [ "$INTERFAZ" = "" ] && INTERFAZ=$(netstat -rn | grep -iE '(^0.0.0.0|default)' | tail -1 | awk '{ print $NF; }')
    [ "$INTERFAZ" = "" ] && INTERFAZ="wlan1"
    [ "$2" = "" ] && IP="192.168.1.27/24" || IP="$2"
    [ "$3" = "" ] && GW="192.168.1.1" || GW="$3"
    /sbin/ip a add $IP dev $INTERFAZ
    /sbin/ip link set dev $INTERFAZ up
    /sbin/ip route add default via $GW
    while true; do
        RES="$(iwconfig $INTERFAZ 2>&1 | grep -v "no wireless" | grep ESSID | grep -v "ESSID:off" | awk '{print $1;}')"
        [ "$RES" != "" ] && break
        sudo pkill --signal SIGHUP wpa_supplicant
        [ "$(ps aux | grep wpa_supplicant | grep $INTERFAZ | grep -v grep)" = "" ] && (/sbin/wpa_supplicant -B -i $INTERFAZ -c /root/WIFICON.cfg -Dwext &)
        sleep 1
    done
}    
G2="8.8.4.4"
INTERFAZ="$1"
IP="$2"
GATEWAY="$3"
while true; do 
    [ "$GATEWAY" = "" ] && GATEWAY=$(netstat -rn | grep -iE '(default|^0.0.0.0)' | tail -1 | awk '{ print $2;}' | grep -v 0.0.0.0 )
    [ "$GATEWAY" = "" ] && GATEWAY=192.168.1.1
    #DNS1=$(cat /etc/resolv.conf | grep -i nameserver | grep -v '#' | head -1 | awk -F'nameserver' '{print $2;}')
    [ "$INTERFAZ" = "" ] && INTERFAZ=$(netstat -rn | grep -iE '(^0.0.0.0|default)' | tail -1 | awk '{ print $NF; }')
    arping -c 4 -I $INTERFAZ $GATEWAY
    [ $? -gt 0 ] && resucita $INTERFAZ $IP $GATEWAY
    sleep 10
    ip route add $G2 via $GATEWAY
    ping -c 4 $G2
    [ $? -gt 0 ] && resucita $INTERFAZ $IP $GATEWAY
    sleep 10
    [ "$(pgrep openvpn)" = "" ] && (redOpenvpn &)
    [ "$(dirIp 4)" = "" ] && [ "$(dirIp2 4)" = "" ] && (redOpenvpn &)
    [ "$(dirIp)" = "$(realIp)" ] && (redOpenvpn &)
    DIR_ACT="$(dig ceuta6543.duckdns.org | grep -v '^;' | grep A | awk '{print $NF;}')"
    [ "$DIR_ACT" != "$(realIp)" ] && /scripts/duckdns.sh ceuta6543 "" 3a115b52-3c62-42ac-93b4-47ed6ea18423 &
    sleep 10
done    
