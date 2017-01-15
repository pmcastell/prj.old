#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 [ <interfaz-wireless> <ssid> <clave-wpa> <dir-red-sin-última-parte> ]
   Ejemplo: $0 wlan20 MiRedWifi miClave 172.16.1
   Crearía un punto de acceso usando la interfaz de red wlan20, con nombre de red MiRedWifi,
           con clave de acceso a la red wpa miClave y con direcciones IP en la red 72.16.1.0/24 
   Se activa enrutamiento y nat a través del equipo
   
EOF
   exit 1
}      
fichConf() {
cat<<EOF
#uth_algs=1
beacon_int=50
channel=3
country_code=ES
disassoc_low_ack=1
driver=nl80211
hw_mode=g
ht_capab=[HT40+][HT40-][SHORT-GI-40][RX-STBC1]
ieee80211d=1
ieee80211n=1
interface=$WLAN
require_ht=0
rsn_pairwise=CCMP
ssid=$SSID
wmm_enabled=1
wpa=2
wpa_key_mgmt=WPA-PSK
wpa_passphrase=$CLAVE
EOF
}
if [ "$1" = "-h" ]; then uso; fi
TEMP=$(tempfile)
if [ $# -lt 4 ]; then
   WIFIS=$(iwconfig 2>&1| grep -v "no wireless" | grep ^[^\ ] | awk '{print $1;}' | tr "\n" "|")
   zenity --forms --title="Parámetros punto Acceso Wireles" --text="Necesito la siguiente info:" --add-list "Interfaz Wifi:" --list-values "$WIFIS"  --add-entry="Nombre Red (ssid):" --add-entry="Clave" --add-entry "Dir.Red:" | sed -e "s/,//g" | sed -e "s/|/\n/g" > $TEMP
   read -d "\n" WLAN SSID CLAVE RED < $TEMP
else 
   WLAN=$1; SSID=$2; CLAVE=$3; RED=$4; RANGO1=$5; RANGO2=$6
fi
if [ "$RANGO1" = "" ]; then RANGO1=35; fi
if [ "$RANGO2" = "" ]; then RANGO2=240; fi
fichConf > $TEMP
sudo /usr/sbin/hostapd $TEMP &
#&> /dev/null &

#if [ "$1" != "" ]; then RED="$1"; else RED="172.16.2"; fi
#if [ "$2" != "" ]; then IFACE="$2"; else IFACE="wlan20"; fi
sudo ifconfig $WLAN $RED.1/24 up
sudo cat<<EOF | sudo tee /etc/dnsmasq.d/puntoAcceso.conf
interface=$WLAN
dhcp-range=$WLAN,$RED.$RANGO1,$RED.$RANGO2,255.255.255.0,12h
EOF
sudo service dnsmasq restart
###sudo dnsmasq --interface="$WLAN" --bind-interfaces --dhcp-range=$RED.72,$RED.100,255.255.255.0
###echo sudo firewall -accept $RED.0/24 &> /dev/null
sudo bash -c 'echo 1 > /proc/sys/net/ipv4/ip_forward'
sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s $RED.0/24
echo rm $TEMP

