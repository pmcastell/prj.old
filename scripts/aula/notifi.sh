#!/bin/bash
#Script to pause notifications
# click to stop, click to continue
uso() {
   cat<<EOF
   Uso: $0 <on|off>
   Habilita/Deshabilita las notificaciones de escritorio
EOF
   exit 1
}
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
STATUS=$(ps aux | grep notify-osd | grep -c Tl)

#if [ "$STATUS" == "0" ]; then
if [ "$1" == "off" ]; then
  exec killall -s STOP notify-osd 
elif [ "$1" == "on" ]; then
  exec killall -s CONT notify-osd &
  #notify-send -u critical "Notifications are On"
else
  uso
fi
exit 0
