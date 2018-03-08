#!/bin/bash
#if [ "$(whoami)" != "root" ]; then gksudo $0: exit ; fi
USUPASS=$(zenity --password --title="Instroduce contraseña de usuario")
if [ ! -f /l/miL/virtualbox-enc/win7/win7.vbox ]; then
   PASS=$(/scripts/getMypass.sh encfs)
   echo $USUPASS | sudo -S bash -c "echo $PASS | encfs -S --public /l/.virtualbox.enc /l/virtualbox-enc"
fi   
sudo -H -u usuario vboxmanage startvm win7 --type headless --vrde off &
ping -c 4 win7 &> /dev/null
echo $USUPASS | sudo -S  iptables -F
ping -c 4 win7 &> /dev/null
echo $USUPASS | sudo -S  firewall
sleep 20
sudo -H -u usuario vncviewer win7 &

