#!/bin/bash

uso() {
   cat<<EOF
   Uso: $0 [<pc-ip>|<pc-name>]
   Ejemplo: $0 10.2.1.103 (despierta el epoptes del pc103
   Ejemplo: $0 pc103      (Ídem)
EOF
   exit 1
}
#if [ $# -lt 1 ]; then uso; fi      
#xdotool key --delay 0 --clearmodifiers ctrl+c | 
for ip in $(cat /etc/dnsmasq.conf | grep -i dhcp-host | awk -F ',' '{print $3;}'); do
   #ssh lliurex@$ip sudo /etc/init.d/epoptes-client restart &>/dev/null &
   despiertaEpoptes $ip &
done
