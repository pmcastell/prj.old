<?php

// Adding in PXE Menu OpenSysClone Restore Image in HDD local from server, unicast mode

$MenuEntryList=array();
$MenuEntry=new stdClass();
$MenuEntry->id="opensysclone";
$MenuEntry->label="OSC: Restaurar Imagen lliurex15.05-kernel4";
$MenuEntry->menuString='LABEL Restaurar Imagen lliurex15.05-kernel4
MENU LABEL OpenSysClone: Restaurar Imagen lliurex15.05-k4-ENuevos
KERNEL opensysclone-system/vmlinuz
APPEND initrd=opensysclone-system/initrd.img boot=live union=aufs config noswap noprompt vga=788 ip=frommedia ethdevice-timeout=60 fetch=tftp://10.2.1.254/opensysclone-system/filesystem.squashfs ocs_prerun1="dhclient -v eth0" ocs_prerun2="mount -t nfs 10.2.1.254:/net/OpenSysClone/imagenes /home/partimag" ocs_prerun3="busybox tftp -g -r /opensysclone-system/testservermount.sh -l /tmp/testservermount.sh 10.2.1.254" ocs_prerun4="bash /tmp/testservermount.sh" ocs_prerun5="sleep 5" ocs_prerun6="busybox tftp -g -r /opensysclone-system/hostnamesaver.sh -l /tmp/ejecuta.sh 10.2.1.254" ocs_prerun7="bash /tmp/ejecuta.sh" ocs_live_run="ocs-sr -g auto -e1 auto -e2 -k1 -r -j2 -p command restoredisk lliurex15.05-kernel4-EquiposNuevos sda" ocs_postrun1="busybox tftp -g -r /opensysclone-system/hostnamerestore.sh -l /tmp/ejecuta.sh 10.2.1.254" ocs_postrun2="bash /tmp/ejecuta.sh ; poweroff" keyboard-layouts=NONE ocs_live_batch="no" locales="es_ES.UTF-8" nolocales';
array_push($MenuEntryList, $MenuEntry);

// "Return" MenuEntryListObject
$MenuEntryListObject=$MenuEntryList;

?>
