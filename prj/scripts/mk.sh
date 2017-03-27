#!/bin/bash

ORIGEN=/home/usuario/multi-dvd/winXpLinux
OS=$(uname)
if [ "$OS" = "FreeBSD" ];
then
   DESTCD=/home/usuario/Escritorio/
   DESTDVD=/home/usuario/Escritorio/   
   #if [ ! -d /l/ImagenesCdDvd ];
   #then
   #   sudo ntfs-3g /dev/ntfs/L-ZAntf-101 /mnt/l
   #fi
else   
   DESTDVD=/l/ImagenesCdDvd
fi   
if [ "$1" != "cd" -a "$1" != "dvd" ];
then
   DESTDVD=$1
fi
if [ "$2" != "" ];
then
   PRIORIDAD=$2
   #DESTDVD=$2
else
   PRIORIDAD=0
fi   
CUR_DIR=$(pwd)
cd $ORIGEN
#LISTA="grldr menu.lst ntldr NTDETECT.COM win51 win51ip win51ip.sp3 I386 PROGRAMS autorun.inf boot BOOTFONT.BIN casper FreeBSD FreeBSD.ico grub4dos-0.4.4 images imgs sysrcd ubuntu-10.04-desktop-i386.iso"
LISTA=$(ls $ORIGEN)
#Primero los ficheros
for f in $LISTA;
do
   if [ -f $f ];
   then
      OBJETIVOS="$OBJETIVOS $f"
   fi
done
#luego I386 y PROGRAMS
OBJETIVOS="$OBJETIVOS I386 boot SOURCES PROGRAMS"
#por último los directorios
for d in $LISTA;
do 
   if [ -d $d ];
   then
      if [ "$d" != "I386" -a "$d" != "PROGRAMS" -a "$d" != "SOURCES" -a "$d" != "boot" ];
      then
         OBJETIVOS="$OBJETIVOS $d"
      fi
   fi
done
for i in $OBJETIVOS;
do
   GRAFTPOINTS="$GRAFTPOINTS $i=$i"
done

#if [ "$1" = "" -o "$1" = "dvd" ];
#then 
   rm $DESTDVD/winXpLinux.iso
   if [ -f /m/PROGRAMS/mcafee/runtime.dat ];
   then
      rm /m/PROGRAMS/mcafee/runtime.dat
   fi
   eecho nice -$PRIORIDAD mkisofs -graft-points -f -iso-level 4 -R -b grldr -no-emul-boot -boot-load-size 4 -boot-info-table -o $DESTDVD/winXpLinux.iso $GRAFTPOINTS
   kvm -boot d -cdrom $DESTDVD/winXpLinux.iso -m 512 &
#fi   
cd $CUR_DIR

