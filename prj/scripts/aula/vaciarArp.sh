#!/bin/bash

#for ip in $(arp -n | awk '{print $1;}' | tail -n +2); do 
#   #echo sudo arp -d $ip;
#   sudo arp -d $ip;  
#done
if [ "$(whoami)" != "root" ]; then sudo $0 $@; exit $?; fi
if [ "$1" = "" ]; then IFACES="eth0 eth1"; else IFACES="$@"; fi
for IFACE in $IFACES; do
   sudo ip link set arp off $IFACE 
   sudo ip link set arp on $IFACE
done
sudo arp -n
