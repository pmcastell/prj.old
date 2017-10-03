#!/bin/bash

sudo tunctl -t tap0
sudo ip link set dev tap0 up
sudo ip address add 10.10.10.100/24 dev tap0
sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.10.10.0/24
