#!/bin/bash
if [ "$1" = "" ];
then
   echo 'Uso: $0 fichero-imagen-iso [dir-montaje]'
   echo Por defecto se monta en /virtualcd
   exit 1
fi
if [ "$2" = "" ];
then
   DIR_MONTAJE=/virtualcd
else
   DIR_MONTAJE=$2
fi      
mdconfig -a -t vnode -f $1 -u 1
mount -t cd9660 /dev/md1 $DIR_MONTAJE

