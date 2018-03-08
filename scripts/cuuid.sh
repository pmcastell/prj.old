#!/bin/bash
uso() {
   echo uso: $0 '<particion-a-cambiarle-el-uuid> [<nuevo-uuid>]'
   echo 'Cambia el uuid de particion-a-cambiarle-el-uuid por nuevo-uuid o por uno aleatorio si este último no se indica'
   exit 1
}
   
[ "$1" = "" ] && uso
[ "$2" = "" ] && NUEVO="$(uuid)" || NUEVO="$2"
PART="$1"
[ "$(ls $PART)" = "" ] && echo "No se encuentra la partición: $PART" && uso
OLD_UUID=$(sudo cat /tmp/blkid | grep $PART | awk -F'UUID="' '{print $2;}' | awk -F'"' '{print $1;}')
[ "$(which uuidgen)" = "" ] && (. /scripts/funcionesAux.sh ; UUID_CMD="uuid ") || UUID_CMD="uuidgen "
NEW_UUID=$(uuidgen)
eecho sudo tune2fs -O ^uninit_bg $PART
[ $? -gt 0 ] && echo se ha producido un error && exit 2
eecho sudo tune2fs $PART -U $NEW_UUID
[ $? -gt 0 ] && echo se ha producido un error && exit 3
eecho sudo tune2fs -O +uninit_bg $PART
[ $? -gt 0 ] && echo se ha producido un error && exit 4
eecho sudo sed -i "s/$OLD_UUID/$NEW_UUID/g" /etc/fstab

