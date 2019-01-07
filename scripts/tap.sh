#!/bin/bash

#sudo tunctl -t tap0
#sudo ip tuntap add dev tap0 mode tap
#sudo ip link del link vmnet1 dev tap0
LAST_TAP="$(ip a | grep tap | awk '{print $2;}' | sort | uniq | tail -1 | egrep -o 'tap[0-9]+' | egrep -o '[0-9]+')"
[ "$LAST_TAP" = "" ] && LAST_TAP="-1" || LAST_TAP=$(( $LAST_TAP + 1 ))
TAP="tap${LAST_TAP}"
sudo ip tuntap add dev $TAP mode tap
sudo ip link set dev $TAP up
RED=$(( 10 + $LAST_TAP ))
RED="10.${RED}.${RED}"
sudo ip address add ${RED}.100/26 dev tap0
sudo dnsmasq -i $TAP -z --dhcp-range=${RED}.200,${RED}.240,255.255.255.0,12h --except-interface=lo
sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s ${RED}.0/24
#sudo ip route add 10.10.10.96/27 dev tap0
