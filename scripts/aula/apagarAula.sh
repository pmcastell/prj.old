#!/bin/bash
#dhcp-host=00:19:66:e9:3b:f6,pc01,10.2.1.101,infinite
DEBUG=true
__apagarPc() {
   if [ "$1" = "-p" ]; then COMMAND="/sbin/poweroff"; else COMMAND="/sbin/shutdown -h now"; fi
   IP=$2
   echo Apagando: $IP
   if $DEBUG; then echo "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo "$COMMAND" &>/dev/null &"; fi
   ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo "$COMMAND" &>/dev/null &
}

__apagarAula() {
   for IP in $(cat /etc/dnsmasq.conf | grep dhcp-host | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}'); do 
	   __apagarPc $1 $IP
	   #sleep 1
   done
}
if [ "$1" = "" ]; then CMD="-s"; else CMD="$1"; shift; fi
if ( [ "$CMD" = "-p" ] || [ "$CMD" = "-s" ] ) && [ "$2" = "" ]; then __apagarAula "$CMD"; exit 0; fi
#elif [ "$CMD" = "-s" ]; then CMD="$1"; shift; fi
while [ "$1" != "" ]; do 
   if $DEBUG; then echo __apagarPc $CMD $1; fi
   __apagarPc $CMD $1
   shift
done
exit 0
