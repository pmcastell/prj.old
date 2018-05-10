#!/bin/bash
uso() {
   echo uso: $0 '<enc|dec> <dir-a-comprimir-encriptar> <password> <fichero-comprimido-encriptado>'
   exit 1
}   
[ $# -lt 3 ] && uso
OP="$1"
DIR="$2"
PASS="$3"
FICH="$4"
CORES="$(lscpu | grep 'CPU(s):' | grep -v NUMA | awk '{print $2;}')"
[ "$OP" = "enc" ] && tar cv "${DIR}" | pigz -5 -p ${CORES} | gpg --batch --symmetric --passphrase ${PASS} > "${FICH}.tar.gz.pgp"

[ "$OP" = "dec" ] && gpg --batch --decrypt --passphrase ${PASS} -o - ${FICH}.tar.gz.pgp | pigz -d | tar xf -
exit 0


#1
	
#gpg --batch --decrypt --passphrase MySecretPassword1 -o ${dir}.tar.gz ${dir}.tar.gz.pgp
