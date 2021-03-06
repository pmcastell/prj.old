#!/bin/bash

uso() {
   echo uso: $0 '<path-to-sysrcd....iso>'
   exit 1
}
if [ "$1" = "" ]; then uso; fi

ISO=$(ls $1/sys*.iso)
TEMP_DIR=$(tempfile)
rm $TEMP_DIR
mkdir -p $TEMP_DIR
sudo mount -o loop $ISO $TEMP_DIR
if [ ! -d ./sysrcd ]; then
   echo no existe el directorio sysrcd. Abortando...;
   exit 2;
fi   
rm -rf ./sysrcd/*
#cp $TEMP_DIR/altker32 altker64 initram.igz sysrcd.dat sysrcd.md5
cp $TEMP_DIR/sysrcd.dat ./sysrcd/
cp $TEMP_DIR/sysrcd.md5 ./sysrcd/
cp $TEMP_DIR/isolinux/altker32 ./sysrcd/
cp $TEMP_DIR/isolinux/altker64 ./sysrcd/
cp $TEMP_DIR/isolinux/initram.igz ./sysrcd/
sudo umount $TEMP_DIR
