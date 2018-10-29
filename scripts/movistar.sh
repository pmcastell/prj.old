#!/bin/bash

sudo systemctl stop network-manager
sudo killall wpa_supplicant
sudo wpa_supplicant -B -i wlan1 -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_1F78 -Dwext &
CONT="0"
while [ "$(iwconfig 2>&1 | grep 'ESSID:\"MOVISTAR_1F78\"')" == "" ]; do
   sleep 1
   CONT=$(( $CONT + 1 ))
   [ $CONT -gt 5 ] && echo no se conecta la tarjeta && break
done
sudo ip a add 192.168.1.27/24 dev wlan1
sudo ip route add default via 192.168.1.1
