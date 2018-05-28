#!/bin/bash
uso() {
   echo uso: $0 '<dir-destino> <fich-destino> <carpeta-fichero-a-comprimir>'
   exit 1
}
[ $# -lt 3 ] && uso
[ "$(whoami)" != "root" ] && sudo $0 $@ && exit $?
DIR_DEST="/m/Mios/Instituto/mysqlMoodle"
DIR_DEST="$1"
FICH_DEST="${2}.7z"
FICH_DEST_TMP="${2}Tmp.7z"
ORIGEN="$3"
PASS=$(/scripts/getMyPass.sh encfs)
echo nice -19 7za u -w${DIR_DEST} -mhe -p${PASS} -ms=off -mx=9 -mmt=8 -u- -uq0!${DIR_DEST}/${FICH_DEST_TMP} ${DIR_DEST}/${FICH_DEST} ${ORIGEN}
nice -19 7za u -w${DIR_DEST} -mhe -p${PASS} -ms=off -mx=9 -mmt=8 -u- -uq0!${DIR_DEST}/${FICH_DEST_TMP} ${DIR_DEST}/${FICH_DEST} ${ORIGEN}
rm ${DIR_DEST}/${FICH_DEST}
mv ${DIR_DEST}/${FICH_DEST_TMP} ${DIR_DEST}/${FICH_DEST}
chown usuario:usuario ${DIR_DEST}/${FICH_DEST}
