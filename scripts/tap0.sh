#!/bin/bash

#sudo tunctl -t tap0
#sudo ip tuntap add dev tap0 mode tap
#sudo ip link del link vmnet1 dev tap0
if [ "$(ip a | grep vmnet1)" != "" ]; then
    sudo ip link add link vmnet1 name tap0 address 0a:0b:0c:0d:0e:0f type macvlan mode bridge
else 
    sudo ip tuntap add dev tap0 mode tap
fi    
sudo ip link set dev tap0 up
sudo ip address add 10.10.10.100/26 dev tap0
dnsmasq -i tap0 -z --dhcp-range=10.10.10.200,10.10.10.240,255.255.255.0,12h --except-interface=lo
#sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.10.10.0/24
#sudo ip route add 10.10.10.96/27 dev tap0
