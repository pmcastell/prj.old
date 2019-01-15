#!/bin/bash

while [ "$1" != "" ]; do
    ip a show dev $1
    while true; do
        IP=$(ip a show wlan0 | grep inet | grep -v inet6 | head -1 | awk '{print $2;}')
        [ "$IP" = "" ] && break
        eecho sudo ip a del $IP dev $1
    done
    ip a show dev $1
    shift
done    
