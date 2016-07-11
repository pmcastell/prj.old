#!/bin/bash
#gal
#U2FsdGVkX18yYWmptO1TIzrbZf2KSUqo3RRi

uso() {
   echo uso: $0 '<cuenta-correo> <orden>' #<password-aes-256-ctr--base64>
   exit 1
}

if [ $# -lt 2 ]; then uso; fi

CUENTA="$1"
CMD=$2
#PASS=$3

DIR_BASE=$(dirname $0)

CMD=$(echo $CMD | $DIR_BASE/encriptar.sh )
CMD="Ejecutar Orden: $CMD"

$DIR_BASE/smtpSend.sh "$CUENTA" "$CUENTA" "'$CMD'" "'$CMD'"

