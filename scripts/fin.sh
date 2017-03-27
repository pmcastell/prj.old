#!/bin/bash

desmontaEncfs() {
   DESMONTAR=$1; INTENTOS=1;
   while [ "$(mount | grep encfs | grep $1)" != "" -a $INTENTOS -le 5 ]; do
   #while [ -f $FICHERO -a $INTENTOS -le 5 ]; do
      sudo eecho fusermount -u $DESMONTAR
      INTENTOS=$(expr $INTENTOS + 1)
   done
}
   
mata xflux
mata alive
sudo vpn stop
desmontaEncfs "/m/Mios"
desmontaEncfs "/l/virtualbox"
if [ "$(ps aux | grep 'VBoxHeadless --comment Free' | grep -v grep)" != "" ]; then
   /m/Mios/prj/scripts/freeB.sh stop
fi   
echo cerrando conexiones vpn-ssh
#/home/usuario/aula/start.sh SSH no
FICHERO=$(ls -l /l/Mios-7z/ | grep "$(date | awk '{ print $2" "$3;}')")
if [ "$FICHERO" = "" ]; then
    echo No has hecho copia /l/Mios-7z
    echo ¿Quieres hacerla?
    read resp
    if [ "$resp" = "s" -o "$resp" = "S" ]; then
       mios
    fi
fi    

if [ "$1" = "" ]; then
   gnome-session-quit
elif [ "$1" = "reboot" ]; then
   sudo reboot
else
   sudo poweroff
fi         
