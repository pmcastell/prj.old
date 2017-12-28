#!/bin/bash

RED="172.16.25"
RINI="${RED}.151"
RFIN="${RED}.152"
MASK="255.255.255.0"
sudo ip a add ${RED}.1/24 dev eth27
sudo dnsmasq --interface=eth27 --bind-interfaces --dhcp-range=${RINI},${RFIN},${MASK}
while true; do
   ping -c 1 ${RINI}
   [ $? -eq 0 ] && break
done
ssh pi@${RINI}
sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s ${RED}.0/24
