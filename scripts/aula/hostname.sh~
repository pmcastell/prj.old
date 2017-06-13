#!/bin/bash
uso() {
   /bin/cat<<EOF
   uso $0 
   Comprueba si el nombre de host coincide con la dirección ip
   P.Ej. si la dir ip es 10.2.1.114 comprueba que el nombre del equipo sea pc114
   y si no lo es lo cambia
EOF
   exit 1
}

if [ "$1" = "-h" ]; then uso; fi  
if [ "$(/usr/bin/whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi    
INTERFAZ=$(/sbin/ifconfig | /bin/grep -E "^eth" | /usr/bin/awk '{print $1;}')
DIR="10\.2\.1\."
for((i=1;i<=60;i++)); do 
   PCNUM=$(/sbin/ifconfig $INTERFAZ | /bin/grep -oE "${DIR}[0-9]{1,3}" | /usr/bin/head -1 | /usr/bin/awk -F'.' '{print $4;}')
   if [ "$PCNUM" != "" ]; then break; fi
   /bin/sleep 1
   #/bin/echo i >> /etc/if.txt
   #/usr/bin/uptime >> /etc/if.txt
   #/sbin/ifconfig >> /etc/if.txt
done   
if [ "$(/bin/hostname)" != "pc${PCNUM}" ]; then
   /bin/hostname pc${PCNUM}
   /bin/echo pc${PCNUM} > /etc/hostname
fi   
if [ "$(/bin/cat /etc/hosts | /bin/grep pc${PCNUM})" = "" ]; then
   /bin/cp /etc/hosts /tmp/hosts.old
   echo "127.0.0.1   localhost pc${PCNUM}" > /etc/hosts
   echo "10.10.10.11 moodle" >> /etc/hosts
   /bin/cat /tmp/hosts.old | grep -v 127.0.0 | grep -v 127.0.1 | grep -v moodle >> /etc/hosts
fi
