#!/bin/bash

MAQUINA="$1"
([ "$MAQUINA" = "ser" ] || [ "MAQUINA" = "SER" ]) && /scripts/kvnet.sh -m 1024 -hda "/home/usuario/VirtualBoxVMs/SER200/SERDisk.vmdk"
([ "$MAQUINA" = "apw" ] || [ "MAQUINA" = "APW" ]) && /scripts/kvnet.sh -m 1024 -hda "/home/usuario/VirtualBoxVMs/APW200/2SMRDisk.vmdk"
([ "$MAQUINA" = "sin" ] || [ "MAQUINA" = "SIN" ]) && /scripts/kvnet.sh -m 1024 -hda "/home/usuario/VirtualBoxVMs/SIN200/2SMR clonar-disk1.vmdk" -hdb "/home/usuario/VirtualBoxVMs/SIN200/RAID1.vmdk" -hdc "/home/usuario/VirtualBoxVMs/SIN200/RAID2.vmdk"
[ "$2" != "" ] && /scripts/kvnet.sh -m $2 -hda $1
