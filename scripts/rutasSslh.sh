#!/bin/bash
GATEWAY="$(route -n | grep -vE tun[0-9]+$ | grep ^0\.0\.0\.0 | awk '{print $2;}')"
HOSTS="$(sudo netstat -onatp | grep sslh | awk '{print $5;}' | awk -F':' '{print $1;}' | grep -vE '(0.0.0.0|127.0.0.1)')"

for IP in $HOSTS; do sudo route add -host $IP gw $GATEWAY; done
