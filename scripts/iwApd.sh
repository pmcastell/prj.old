#!/bin/bash
uso() {
   echo Uso: $0 '<interface> [<dir-ip>]'
   exit
}   

crearIface() {
   local IFACE="$1" NAME="$2" TYPE="$3"
   eecho sudo iw dev $NAME interface del &> /dev/null
   eecho sudo iw dev $IFACE interface add $NAME type $TYPE
   sleep 2
   while [ "$(ifconfig -a | grep $NAME)" = "" ]; do 
      MAC=$(ip -o link | grep "${IFACE}:" | awk '{print $(NF-2);}')
      MAC_ORIG=$(ethtool -P $IFACE | awk '{print $NF;}')
      IFACE2=$(ip -o link | grep -E "($MAC|$MAC_ORIG)" | grep -v $IFACE | awk -F':' '{print $2;}')
      eecho sudo ip link set $IFACE2 name $NAME
      sleep 1
   done 
}   
reinicia() {
    IFACES="$@"
    for I in $IFACES; do
       sudo iw dev $I del
    done
    sudo /scripts/finPuntoAcceso.sh
    sudo killall wpa_supplicant
}    
[ "$1" = "-r" ] && reinicia wlan200 wlan201 && exit 0
[ "$1" = "" ] && uso
IFACE="$1"
[ "$2" = "" ] && CHANNEL=6 || CHANNEL="$2"
[ "$3" = "" ] && SSID="MiCasa" || SSID="$3"
[ "$4" = "" ] && DNSMASQ_RED="172.16.1" || DNSMASQ_RED="$4"
###[ "$2" = "" ] && IP="192.168.1.25/24" || IP="$2"
###sudo ip link set dev $IFACE down
###crearIface $IFACE ${IFACE}0 station
###sudo macchanger -r ${IFACE}0
crearIface $IFACE "${IFACE}1" __ap
sudo macchanger -r ${IFACE}1
###sudo ip a add $IP dev ${IFACE}0
###sudo ip link set dev ${IFACE}0 up
###sudo wpa_supplicant -B -i ${IFACE}0 -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_E360 -Dwext &
###sudo ip route add default via "$(echo $IP | awk -F'/' '{print $1;}' | cut -d'.' -f1-3)".1
sudo /scripts/finPuntoAcceso.sh
sudo /scripts/puntoAcceso.sh ${IFACE}1 ${SSID} Montoro65 ${DNSMASQ_RED} 35 128 $CHANNEL



