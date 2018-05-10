#!/bin/bash

desmontaEncfs() {
   DESMONTAR=$1; INTENTOS=1;
   while [ "$(mount | grep encfs | grep $1)" != "" -a $INTENTOS -le 5 ]; do
   #while [ -f $FICHERO -a $INTENTOS -le 5 ]; do
      echo ejecutando: sudo fusermount -u $DESMONTAR
      sudo fusermount -u $DESMONTAR
      INTENTOS=$(expr $INTENTOS + 1)
   done
}
   
mata xflux
mata alive
sudo vpn stop

sudo fusermount -u /home/usuario/googleDrive &> /dev/null
if [ "$(ps aux | grep 'VBoxHeadless --comment Free' | grep -v grep)" != "" ]; then
   /m/Mios/prj/scripts/freeB.sh stop
fi   
#echo cerrando conexiones vpn-ssh
#/home/usuario/aula/start.sh SSH no
desmontaEncfs "/l/virtualbox-enc"
FICHERO=$(ls -l /l/Mios-7z/ | grep "$(date | awk '{ print $2" "$3;}')")
if [ "$FICHERO" = "" ]; then
    read -p 'No has hecho copia /l/Mios-7z.... ¿Quieres hacerla?'  resp
    ([ "$resp" = "s" ] || [ "$resp" = "S" ] ) && mios
fi  
while [ "$(pgrep dropbox)" != "" ]; do echo Intentando terminar dropbox.; date;  dropbox stop; sleep 1; done
CONT=1
while true; do
   desmontaEncfs "/m/Mios"
   [ $CONT -gt 5 ] && break
   [ "$(mount | grep encfs | grep "/m/Mios")" = "" ] && break
   echo "CONT: $CONT"
   echo "lsof: $(lsof /m/Mios 2>/dev/null)"
   echo "sudo lsof: $(sudo lsof /m/Mios 2>/dev/null)"
   echo "fuser: $(fuser /m/Mios 2>/dev/null)"
   echo "sudo fuser: $(sudo fuser /m/Mios 2> /dev/null)"
   CONT=$(($CONT + 1))
done
sudo sync
read -p '¿Cerramos?' var
if [ "$1" = "" ]; then
   gnome-session-quit
elif [ "$1" = "reboot" ]; then
   sudo reboot
else
   sudo poweroff
fi         
