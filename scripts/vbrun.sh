#!/bin/bash

[ "$1" = "" ] && MAQUINA=$(zenity --entry --title="Dime que máquina arranco" 2>/dev/null) || MAQUINA="$1"
[ "$MAQUINA" = "" ] && zenity --info --text="Necesito saber que máquina arrancar" 2>/dev/null && exit 1
VBOXVM="$(vboxmanage list vms | grep $MAQUINA | awk '{print $1;}' | tr -d '\"')"
[ "$VBOXVM" != "$MAQUINA" ] && zenity --info --text="No encuentro la VM: $MAQUINA" && exit 2
#echo "VBOXVM: $VBOXVM; MAQUINA: $MAQUINA" && echo "hola" && read
vboxmanage startvm $MAQUINA --type headless &
#Esto es por que a veces el firewall hacía cosas "raras" y no llegaba el ping a la tarjeta virtual
for((i=1;i<30;i++)); do
   [ "$(nmap ${MAQUINA} -p 5900 | grep open)" != "" ] && break
   #ping -c 2 ${MAQUINA}
   #[ $? -eq 0 ] && break
   sleep 1
done
PORT="$(nmap -p 5900-5920 ser200 | grep open | egrep -o '^[0-9]{4}')"
vncviewer $MAQUINA:$PORT &

exit 0

#if [ "$(whoami)" != "root" ]; then gksudo $0: exit ; fi
#script antiguo:




##!/bin/bash
##if [ "$(whoami)" != "root" ]; then gksudo $0: exit ; fi
#[ "$1" = "" ] && MAQUINA=$(zenity --entry --title="Dime que máquina arranco") || MAQUINA="$1"
#[ "$MAQUINA" = "" ] && zenity --info --text="Necesito saber que máquina arrancar" 2>/dev/null && exit 1
#USUPASS=$(zenity --password --title="Instroduce contraseña de usuario")
#if [ ! -f /l/virtualbox-enc/win7/win7.vbox ]; then
#   PASS=$(echo $PASS | desencripta)
#   echo $USUPASS | sudo -S bash -c "echo $PASS | encfs -S --public /l/.virtualbox.enc /l/virtualbox-enc"
#fi   
#sudo -H -u usuario vboxmanage startvm $MAQUINA --type headless &
##Esto es por que a veces el firewall hacía cosas "raras" y no llegaba el ping a la tarjeta virtual
#ping -c 2 -W 2 $MAQUINA &> /dev/null
#echo $USUPASS | sudo -S  iptables -F &> /dev/null
#ping -c 2 -W 2 $MAQUINA &> /dev/null
#echo $USUPASS | sudo -S  firewall &> /dev/null
#sleep 5
#sudo -H -u usuario vncviewer $MAQUINA &

#exit 0



#if [ "${MAQUINA}" = "win7" ] && [ ! -f /l/virtualbox-enc/$MAQUINA/${MAQUINA}.vbox ]; then
#   USUPASS=$(zenity --password --title="Instroduce contraseña de usuario" 2>/dev/null)
#   PASS=$(/scripts/getMyPass.sh encfs)
#   echo $USUPASS | sudo -S bash -c "echo $PASS | encfs -S --public /l/.virtualbox.enc /l/virtualbox-enc"
#fi  
