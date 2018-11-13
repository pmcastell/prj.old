#!/bin/bash

echo "splashimage=/imgs/mano.xpm

foreground ffffff
#background ffff88
viewport 3 2 77 22
shadow 1
#color black/cyan yellow/cyan #color yellow/magenta yellow/cyan #color white/cyan yellow/black

timeout 30
default 0

title ----------------- Sistemas Disponibles ------------
root

title SysRcD32 Live CD rescue32
find --set-root /penDriveArranque
kernel /distros/sysrcd/rescue32 initrd=/distros/sysrcd/initram.igz setkmap=es subdir=/distros/sysrcd rootpass=root autoruns=no
initrd=/distros/sysrcd/initram.igz


title SysRcD64 Live CD rescue64
find --set-root /penDriveArranque
kernel /distros/sysrcd/rescue64 initrd=/distros/sysrcd/initram.igz setkmap=es subdir=/distros/sysrcd rootpass=root autoruns=no
initrd=/distros/sysrcd/initram.igz

title reboot
reboot

title halt
halt
"
