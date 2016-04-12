#!/bin/bash
PID=$(ps ax | grep -i nm-applet | grep -v grep | awk '{ print $1; }')
kill -TERM $PID
cp /etc/network/interfaces.insti.router /etc/network/interfaces
/etc/init.d/networking restart
cat /proc/sys/net/ipv4/ip_forward
echo 1 > /proc/sys/net/ipv4/ip_forward
iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.1.1.192/255.255.255.224 -o ath1

