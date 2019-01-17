#!/bin/bash
 
#for ip in $(arp -n | awk '{print $1;}' | tail -n +2); do 
#   #echo sudo arp -d $ip;
#   sudo arp -d $ip;  
#done
[ "$1" != "" ] && IFACE="$1"  || IFACE="$(ip a | grep UP | grep -v vboxnet | grep -v vmnet | grep -v lo | awk '{print $2;}' | awk -F ':' '{print $1;}')"
if [ "$(whoami)" != "root" ]; then sudo $0 $@; exit $?; fi
sudo ip link set arp off $IFACE 
sudo ip link set arp on $IFACE
sleep 1
sudo arp -n

