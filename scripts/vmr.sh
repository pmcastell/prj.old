#ssh franc@iesinclan.dyndns.org vmrun -vp  password -T ws start  /home/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui
#ssh franc@iesinclan.dyndns.org vmrun -vp password  -T ws start  /home/franc/vmware/miUbuntu64/$1.vmx nogui
#ssh franc@iesinclan.dyndns.org 'ecryptfs-mount-private && cd /home/franc && vmrun -vp password  -T ws start  /home/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui'
[ "$1" = "" ] && echo "Debes especificar el nombre de la máquina a poner en marcha" && exit 1
VM="$(find /l/VMS/vmware/$1 | egrep '\.vmx$')"
xhost +localhost
/usr/bin/vmrun -T ws start  "$VM" nogui
