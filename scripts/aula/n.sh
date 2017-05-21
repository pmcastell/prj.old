#!/bin/bash
echo "basename: $(basename $0), dirname: $(dirname $0)"
exit
echo . $(dirname)/uso.sh

[ "$1" = "" ] && uso $0 "<on|off>"\n"   Activa (on)/Desactiva (off) las notificaciones de escritorio"
[ "$1" = "on" ] && sudo mv /usr/share/dbus-1/services/org.freedesktop.Notifications.service{,.disabled} || \
   sudo mv /usr/share/dbus-1/services/org.freedesktop.Notifications.service{.disabled,}
