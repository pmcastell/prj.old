#!/bin/bash
cambiarLabelDisco() {
   #sudo e2label /dev/sda1 ubuntu
   [ "$2" = "" ] && echo faltan parámetros. Ejemplo cambiarLabelDisco /dev/sdb1 ubuntu && return
   sudo e2label $1 $2
}

montarDiscoLabel() {
   #udisksctl mount -b /dev/sdb1 /media/usuario/Mint
   [ "$1" = "" ] && echo falta parámetro. Ejemplo: montarDiscoLabel Mint &&  return
   DEV="/dev/$(lsblk -o name,label | grep $1 | grep -o 'sd[a-z][0-9]\+')"
   udisksctl mount -b $DEV /media/$USER/$1
}
