#!/bin/bash
if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi
if [ "$1" != "" ]; then
   while [ "$1" != "" ]; do 
      LINEA=$(cat /etc/dnsmasq.conf | grep $1)
      MAC=$(echo $LINEA | awk -F'=' '{print $2;}' | awk -F',' '{print $1;}')
      IP=$(echo $LINEA | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}')
      sudo arp -s $IP $MAC
      sudo etherwake $MAC; 
      sudo wakeonlan $MAC &> /dev/null ;
      shift
   done
   exit
fi
while read l; do
   #dhcp-host=00:1a:a0:bc:88:f1,pc01,10.2.1.201,infinite
   if [ "$(echo $l | grep dhcp-host | grep -v grep)" != "" ]; then
      MAC=$(echo $l | awk -F'=' '{print $2;}' | awk -F',' '{print $1;}')
      IP=$(echo $l | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}')
      sudo arp -s $IP $MAC &> /dev/nuull;
      sudo etherwake $MAC; 
      sudo wakeonlan $MAC &> /dev/null ;
   fi
done < /etc/dnsmasq.conf
   
