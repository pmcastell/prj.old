#!/bin/bash
uso() {
   cat << EOF
   uso: $0
   Añade alias para conectarse a los servidores
   sa1 -> conexión con usuario franav a aula1srv
   sa2 -> conexión con usuario franav a aula2srv
   sb -> conexión con usuario franav a biblioSrv
   sp -> conexión con usuario franav a salaProfesSrv
EOF
   exit 1
}
DEST="$HOME/.bashrc"
anadirLinea() {
   LINEA="$@"
   if [ "$(cat $DEST | grep "$LINEA")" = "" ]; then
      echo "$LINEA">>$DEST
   else
      echo "No se añade. Ya existía: $LINEA"
   fi
}   
if [ "$1" = "-h" ]; then uso; fi   
kFcriadon=$($(dirname $0)/claves.sh fcriadon)
BASE_DIR=$(dirname $0)
anadirLinea "export BASE_DIR=\"$(dirname $0)\""
anadirLinea "alias sa1='sshpass -p \$($BASE_DIR/claves.sh franav) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y franav@sa1'"
anadirLinea "alias sa2='sshpass -p \$($BASE_DIR/claves.sh franav) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y franav@sa2'"
anadirLinea "alias sb='sshpass -p \$($BASE_DIR/claves.sh franav) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y franav@sb'"
anadirLinea "alias sp='sshpass -p \$($BASE_DIR/claves.sh fcriadon) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y fcriadon@sp'"
anadirLinea "alias ltsp21='sshpass -p \$($BASE_DIR/claves.sh ltspadmin) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y fcriadon@ltsp21'"
anadirLinea "alias ltsp22='sshpass -p \$($BASE_DIR/claves.sh ltspadmin) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y fcriadon@ltsp22'"
anadirLinea "alias ltsp23='sshpass -p \$($BASE_DIR/claves.sh ltspadmin) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y fcriadon@ltsp23'"
