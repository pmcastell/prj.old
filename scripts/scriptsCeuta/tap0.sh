#!/bin/bash

sudo ip tuntap add mode tap tap0
sudo ip link set dev tap0 up
sudo ip address add 10.10.10.100/24 dev tap0
sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.10.10.0/24
echo 1 | sudo tee /proc/sys/net/ipv4/ip_forward &> /dev/null

