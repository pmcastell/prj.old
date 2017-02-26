#!/bin/bash
uso() {
   cat<<EOF
   uso: $0 <origen> <destino>
   Mueve el fichero o directorio de <origen> a <destino>, conservando el "path" de origen
EOF
   exit 1
}
[ "$2" = "" ] && uso
cp -pr --parents $1 $2
if [ "$?" = "0" ]; then
   rm -rf $1
else
   echo algo salió mal no se ha borrado el origen
fi
