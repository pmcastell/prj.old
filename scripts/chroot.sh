#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 <punto-montaje>
EOF
   exit 1
}
[ "$1" = "" ] && uso
#[ "$(whoami)" != "root" ] && sudo $0 "$@" && exit $?
. $(dirname $0)/discos.sh
[ -d "$1" ] && MONTAJE="$1" || MONTAJE="/media/$USER/$1"
[ ! -d "$MONTAJE" ] && uso

[ ! -d "$MONTAJE" ] && montarDiscoLabel $1
if [ "$2" = "" ] || [ "$2" = "on" ]; then
   sudo mount --bind /dev $MONTAJE/dev
   sudo mount --bind /dev/pts $MONTAJE/dev/pts
   sudo mount --bind /sys $MONTAJE/sys
   sudo mount --bind /proc $MONTAJE/proc
   sudo chroot $MONTAJE
elif [ "$2" = "off" ]; then
   sudo umount $MONTAJE/proc
   sudo umount $MONTAJE/sys
   sudo umount $MONTAJE/dev/pts
   sudo umount $MONTAJE/dev
   sudo umount $MONTAJE
else 
   uso
fi
