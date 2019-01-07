#!/bin/bash

[ $# -lt 2 ] && echo "uso: $0 '<fichero-hd> <punto-montaje>'" && exit
HD="$1"
DEST="$2"
sudo fdisk -l $HD | grep -A 50 Dispositivo
read -p "Elige partición a montar: " PART

OFFSET=$(sudo fdisk -l $HD | grep -A 50 Dispositivo | grep ${HD}${PART} | awk '{print $2;}')
[ "$OFFSET" = "*" ] && OFFSET=$(sudo fdisk -l $HD | grep -A 50 Dispositivo | grep ${HD}${PART} | awk '{print $3;}') 
[ "$OFFSET" != "" ] && OFFSET=$(( $OFFSET * 512 ))
LOOP=$(losetup -l | awk -F'/dev/loop' '{print $2;}' | awk '{print $1;}' | sort -n | tail -1)
LOOP=$(( $LOOP + 1 ))
LOOP="/dev/loop${LOOP}"
sudo losetup -o $OFFSET $LOOP $HD
sudo fsck -fv $LOOP
sudo mount $LOOP $DEST
for d in dev dev/pts proc sys; do [ -e $DEST/$d ] && sudo mount --bind /${d} $DEST/${d}; done
read -p "Pulsa intro para desmontar"
for d in dev/pts dev proc sys; do [ -e $DEST/$d ] && sudo umount $DEST/${d}; done
sudo umount $DEST
sudo losetup -d $LOOP
exit 0


