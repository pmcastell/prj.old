#!/bin/bash
#dhcp-host=00:19:66:e9:3b:f6,pc01,10.2.1.101,infinite
if [ "$1" != "" ]; then
   while [ "$1" != "" ]; do 
      ssh lliurex@$1 sudo /sbin/poweroff
      shift
   done
   exit
fi
for IP in $(cat /etc/dnsmasq.conf | grep dhcp-host | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}'); do 
   echo Apagando: $IP
	ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo /sbin/shutdown -h now &>/dev/null &
	#sleep 1
done
