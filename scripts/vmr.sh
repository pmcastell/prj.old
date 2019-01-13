#!/bin/bash

#ssh franc@iesinclan.dyndns.org vmrun -vp  password -T ws start  /home/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui
#ssh franc@iesinclan.dyndns.org vmrun -vp password  -T ws start  /home/franc/vmware/miUbuntu64/$1.vmx nogui
#ssh franc@iesinclan.dyndns.org 'ecryptfs-mount-private && cd /home/franc && vmrun -vp password  -T ws start  /home/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui'
. /scripts/vbrun.sh
[ "$1" = "" ] && echo "Debes especificar el nombre de la máquina a poner en marcha" && exit 1
VM="$(find /l/VMS/vmware/$1 | egrep '\.vmx$')"
[ "$1" = "win10Pr" ] && (ps aux | grep vmrun | grep -q win10Pr) && zenity --info --text "La máquina ya está en uso con vbox" && exit 1
[ "$1" = "win10Pr" ] && sudo sed -i 's/10.10.10.110 win10Pr/10.10.10.10 win10Pr/g' /etc/hosts
###xhost +localhost
/usr/bin/vmrun -T ws start  "$VM" nogui &
vncConnect $1
