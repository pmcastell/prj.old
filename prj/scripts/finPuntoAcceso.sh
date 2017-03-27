#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0
   Para el punto de Acceso del aula
EOF
   exit 1
}
PID="1"
while [ "$PID" != "" ]; do
   PID=$(ps ax | grep "/usr/sbin/hostapd" | grep -v grep | grep -v sudo | awk '{print $1;}')
   sudo kill -TERM $PID
done
PID="1"
while [ "$PID" != "" ]; do
   PID=$(ps ax | grep dnsmasq | grep interface | grep dhcp-range | grep -v grep | awk '{print $1;}')
   sudo kill -TERM $PID
done
