#!/bin/bash

if [ "$(whoami)" != "root" ]; then sudo $0 $@; exit $?; fi
DIR=$(dirname $0) 
if [ "$1" != "" ]; then
   while [ "$1" != "" ]; do 
      LINEA=$(cat /etc/dnsmasq.conf | grep -vE '^#' | grep $1  | head -1)
      if [ "$LINEA" = "" ]; then echo no se encontró mac de $1; shift; continue; fi
      MAC=$(echo $LINEA | awk -F'=' '{print $2;}' | awk -F',' '{print $1;}')
      IP=$(echo $LINEA | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}')
      #sudo arp -s $IP $MAC
      sudo etherwake -b $MAC; 
      sudo wakeonlan $MAC &> /dev/null ;
      shift
   done
   exit
fi
while read l; do
   #dhcp-host=00:1a:a0:bc:88:f1,pc01,10.2.1.201,infinite
   if [ "${l:0:1}" = "#" ]; then continue; fi
   if [ "$(echo $l | grep dhcp-host | grep -v grep)" != "" ]; then
      MAC=$(echo $l | awk -F'=' '{print $2;}' | awk -F',' '{print $1;}')
      IP=$(echo $l | awk -F'=' '{print $2;}' | awk -F',' '{print $3;}')
      #sudo arp -s $IP $MAC #&> /dev/null;
      sudo etherwake -i eth0 -b $MAC &
      sudo wakeonlan $MAC &> /dev/null &
   fi
done  < /etc/dnsmasq.conf 
   
