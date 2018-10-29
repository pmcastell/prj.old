#!/bin/bash
uso() {
   echo uso: $0 '<SSID> [<CHANNEL> [<IFACE>]]'
   exit 1
}   
[ "$1" = "" ] && uso
WIFI_AP="$1"
[ "$2" = "" ] && CHANNEL=11 || CHANNEL="$2"
[ "$3" = "" ] && IFACE=wlan0 || IFACE="$3"
DIR_SCRIPTS=$(dirname $0)
#=hcdTuox
sudo $DIR_SCRIPTS/finPuntoAcceso.sh
sudo systemctl stop network-manager NetworkManager
sudo killall dnsmasq &> /dev/null
sudo killall wpa_supplicant &> /dev/null
sudo killall dhclient &> /dev/null
sudo ip link set dev ${IFACE} down
for WIFACE in $(iwconfig 2>&1 | egrep '^wlan[0-9]+\_' | awk '{print $1;}'); do
    sudo ip link set dev ${WIFACE} down
    sudo iw dev ${WIFACE} interface del &> /dev/null
done
sudo iwconfig ${IFACE} mode managed
sudo systemctl start network-manager
sudo rfkill unblock wlan
sudo nmcli radio wifi off
sudo airmon-ng check kill
sudo systemctl stop network-manager
sudo iw dev ${IFACE} interface add ${IFACE}_1 type __ap
sudo ip link set dev ${IFACE}_1 down
sudo macchanger -r ${IFACE}_1
sudo ip link set dev ${IFACE} down
[ "$WIFI_AP" = "WLAN24P" ] && sudo macchanger -m 94:7b:e7:f4:39:17 ${IFACE}
sudo ip link set dev ${IFACE} up
sudo ip link set dev ${IFACE}_1 up
sudo /scripts/puntoAcceso.sh ${IFACE}_1 UbuPort Montoro65 172.18.1 35 128 $CHANNEL

FICH_CONF=$(find $DIR_SCRIPTS/wicd/ | grep -i $WIFI_AP)
sudo wpa_supplicant -B -i ${IFACE} -c $FICH_CONF -D wext &
[ "$(echo $WIFI_AP | grep -i micasa)" != "" ] && ( sudo ip a add 172.16.1.25/24 dev $IFACE; sudo ip route add default via 172.16.1.1) || sudo dhclient ${IFACE} &

#sudo ifconfig ${IFACE} down
#sudo ifconfig ${IFACE} hw ether 94:7b:e7:f4:39:17
#sudo ifconfig ${IFACE} up


