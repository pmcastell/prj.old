#!/bin/bash

uso() {
   cat<<EOF
   uso: $0 <dir-origen> <destino>
   Busca dentro de las carpetas de los alumnos carpetas que se llamen exactamente <dir-origen> y las
   mueve a <destino> conservando el "path"
EOF
   exit 1
}
DIR_BASE="/net/server-sync/home/students"

[ "$2" = "" ] && uso
CWD=$(pwd)
cd $DIR_BASE
TEMP=$(tempfile)
sudo find . -type d -name $1 >> $TEMP
while read f; do
   sudo movepath $f $2
done < $TEMP
#rm $TEMP
cd $CWD
