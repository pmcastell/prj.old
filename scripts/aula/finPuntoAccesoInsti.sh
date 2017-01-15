#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0
   Para el punto de Acceso del aula
EOF
   exit 1
}
     
PID=$(ps ax | grep "/usr/sbin/hostapd" | grep -v grep | grep -v sudo | awk '{print $1;}')
sudo kill -TERM $PID
sudo rm $(ls /etc/dnsmasq.d/puntoAcceso.conf)
sudo service dnsmasq stop
