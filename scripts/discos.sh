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
listaDiscosUsb() {
   find /dev/disk -ls | grep usb | grep /sd | awk '{print $NF;}' | awk -F'/' '{print $NF;}' | sort | uniq | grep -v [0-9]
}
listaDiscos() {
   ls /dev/sd[a-z] | awk -F '/' '{print $NF;}'
}
listaDiscosSata() {
   LISTADISCOS=$(listaDiscos)
   LISTAUSB=$(listaDiscosUsb)
   for d in $LISTADISCOS; do
      #echo $LISTAUSB
      #echo $d
      [ "$(echo $LISTAUSB | grep $d)" == "" ] && echo $d
      #echo $LISTAUSB | grep $d
   done
}   
primerLoopDisp() {
   for d in $(ls /dev/loop[0-9]); do
      [ "$(mount | grep $d)" = "" ] && [ "$(sudo losetup -a | grep $d)" = "" ] && echo $d && break
   done
}
