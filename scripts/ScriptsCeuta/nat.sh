#!/bin/bash

echo 1 > /proc/sys/net/ipv4/ip_forward
iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.10.10.0/24
