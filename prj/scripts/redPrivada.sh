#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 
   Para conectar en red (cable cruzado) otro equipo y darle acceso a internet
   a través de ubuntu.
   La red por defecto es 172.16.1.0/24
   Se dan direcciones a la red en el rango 172.16.1.2-172.16.1.100
   Se activa el nat
   
EOF
   exit 1
}      
if [ "$1" = "-h" ]; then uso; fi
if [ "$1" != "" ]; RED="$1"; else RED=172.16.1; fi
if [ "$2" != "" ]; IFACE="$2"; else IFACE="eth27"; fi
sudo ifconfig $IFACE $RED.1/24 up
sudo dnsmasq --interface=$IFACE --bind-interfaces --dhcp-range=$RED.2,$RED.100,255.255.255.0
sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s $RED.0/24
sudo firewall -accept $RED.0/24

