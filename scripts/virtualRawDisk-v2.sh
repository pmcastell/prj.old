#!/bin/bash
uso() {
   echo uso: $0 '<fichero|dispositivo> <tamaño-en-GIGAS> <path-grub4dos>]'
      echo Ejemplo: $0 lliurex.img 8250MB /home/lliurex/imagenes/Arranque
   echo Ejemplo: $0 /dev/sdc
   exit 1
}

. /scripts/discos.sh

[ "$1" = "-h" ] || [ "$1" = "--help" ] && uso
[ "$1" != "" ] && FICH_DISCO="$1" || FICH_DISCO="lliurex-15.04-k4-imagen.raw"
[ "$2" != "" ] && TAM="$2" || TAM="8310MB"
[ "$3" != "" ] && DIR_BASE="$3" || DIR_BASE="/home/lliurex/imagenes/Arranque"

if [ "${FICH_DISCO:0:4}" = "/dev" ]; then
   [ "$(listaDiscosSata | grep $(basename $FICH_DISCO))" != "" ] && echo no puedo formatear discos sata internos: $FICH_DISCO && exit 2
   [ "$(listaDiscosUsb |  grep $(basename $FICH_DISCO))" = "" ] && echo Dispositivo no encontrado: $FICH_DISCO && exit 3
   DISP="$FICH_DISCO"
   PART="${DISP}1"
else
   fallocate -l ${TAM} ${FICH_DISCO}
   DISP="$(primerLoopDisp)"
   sudo losetup $DISP $FICH_DISCO
   PART="${DISP}p1"
fi

if [ "${FICH_DISCO:0:4}" = "/dev" ]; then
   while true; do
      echo "¿Desea formatear $FICH_DISCO (S/N)"
      read RESP
      ([ "$RESP" = "S" ] || [ "$RESP" = "N" ]) && break
   done
else
   RESP="S"   
fi
if [ "$RESP" = "S" ]; then
   sudo parted --script $DISP mktable msdos mkpart primary 2048s 100%
   sudo partprobe $DISP
   sudo mkfs.msdos -F 32 ${PART}
fi
sudo $DIR_BASE/grub4dos-0.4.4/bootlace.com --time-out=0 $DISP

MONTAJE_TEMP=/tmp/$(uuid)
mkdir $MONTAJE_TEMP
sudo mount $PART $MONTAJE_TEMP

sudo cp $DIR_BASE/grub4dos-0.4.4/grldr $MONTAJE_TEMP/
sudo cp $DIR_BASE/autorun* $MONTAJE_TEMP/
sudo cp $DIR_BASE/menu.lst $MONTAJE_TEMP/
sudo cp $DIR_BASE/listaFicherosArranqueImagenes.txt $MONTAJE_TEMP/
sudo touch $MONTAJE_TEMP/B127-A93F
sudo mkdir -p $MONTAJE_TEMP/distros
sudo cp -ruv $DIR_BASE/distros/sysrcd $MONTAJE_TEMP/distros/
sudo mkdir -p $MONTAJE_TEMP/fsImages/lliurex15.04
sudo mkdir -p $MONTAJE_TEMP/fsImages/lliurex15.04-kernel4
sudo cp -ruv $DIR_BASE/grub4dos-0.4.4 $MONTAJE_TEMP/
sudo cp -ruv $DIR_BASE/fsImages/imagen-32-k3/* $MONTAJE_TEMP/fsImages/lliurex15.04/
sudo cp -ruv $DIR_BASE/fsImages/imagen-64-k4/* $MONTAJE_TEMP/fsImages/lliurex15.04-kernel4/
sudo umount $MONTAJE_TEMP
sudo rmdir $MONTAJE_TEMP
[ "${DISP:0:9}" = "/dev/loop" ] && sudo losetup -d $DISP 
echo Proceso terminado
