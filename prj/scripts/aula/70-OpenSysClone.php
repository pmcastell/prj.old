<?php
/*/var/www/ipxeboot -> /usr/share/llxbootmanager/www-boot
ls -l /var/www/ipxeboot/pxemenu.d/
total 24
-rw-r--r-- 1 root root 8147 oct  5 16:24 10-ltsp.php
-rw-r--r-- 1 root root 1472 mar 23 11:03 75-OpenSysClone.php
-rw-r--r-- 1 root root 1514 mar 23 11:03 77-OpenSysClone.php
-rw-r--r-- 1 root root  365 ene 20  2015 80-bootfromharddisk.php
-rw-r--r-- 1 root root 3657 oct  5 16:25 90-netinstall.php
*/
// Adding in PXE Menu OpenSysClone Save Image from HDD local in server
$MenuEntryList=array();
$MenuEntry=new stdClass();
$MenuEntry->id="opensysclone";
$MenuEntry->label="OSC: Crear en el server la imagen imagen_pc1_aula";
$MenuEntry->menuString='LABEL Grabar Imagen imagen_pc1_aula
MENU LABEL OpenSysClone Grabar Imagen imagen_pc1_aula
KERNEL opensysclone-system/vmlinuz
APPEND initrd=opensysclone-system/initrd.img boot=live config noswap nolocales edd=on nomodeset noprompt nosplash ocs_prerun="dhclient -v eth0" ocs_prerun2="mount -t nfs 10.2.1.254:/net/OpenSysClone/imagenes /home/partimag" ocs_prerun3="busybox tftp -g -r /opensysclone-system/testservermount.sh -l /tmp/testservermount.sh 10.2.1.254" ocs_prerun4="bash /tmp/testservermount.sh" ocs_prerun5="sleep 5" ocs_live_run="ocs-sr -fsck-src-part-y -q2  -j2 -z1 -i 2000 -p poweroff savedisk imagen_pc1_aula sda" ocs_live_extra_param="" ocs_live_keymap="NONE" ocs_live_batch="no" ocs_lang="es_ES.UTF-8" vga=788 ip=frommedia ethdevice-timeout=60 fetch=tftp://10.2.1.254/opensysclone-system/filesystem.squashfs';
array_push($MenuEntryList, $MenuEntry);

// "Return" MenuEntryListObject
$MenuEntryListObject=$MenuEntryList;



?>


