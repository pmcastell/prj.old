#!/bin/bash
#dhcp-host=00:19:66:e9:3b:f6,pc01,10.2.1.101,infinite
DEBUG=true
apagarPc() {
   if [ "$1" = "-p" ]; then COMMAND="/sbin/poweroff"; else COMMAND="/sbin/shutdown -h now"; fi
   IP=$2
   echo Apagando: $IP
   if $DEBUG; then echo "ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo "$COMMAND" &>/dev/null &"; fi
   ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo "$COMMAND" &>/dev/null &
}

apagarAula() {
   for IP in $(cat /etc/dnsmasq.conf | grep dhcp-host | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}'); do 
	   apagarPc $1 $IP
	   #sleep 1
   done
}
CMD="-p"
if ( [ "$1" = "-p" ] || [ "$1" = "-s" ] ) && [ "$2" = "" ]; then apagarAula "$1"; exit 0;
elif [ "$1" = "-s" ]; then CMD="$1"; shift; fi
while [ "$1" != "" ]; do 
   if $DEBUG; then echo apagarPc $CMD $1; fi
   apagarPc $CMD $1
   shift
done
exit 0
