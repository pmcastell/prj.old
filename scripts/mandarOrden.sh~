#!/bin/bash
#gal
#U2FsdGVkX18yYWmptO1TIzrbZf2KSUqo3RRi

uso() {
   echo uso: $0 '<usuario-correo-gmail> <orden>' #<password-aes-256-ctr--base64>
   exit 1
}

if [ $# -lt 2 ]; then uso; fi

USER=$1
CMD=$2
#PASS=$3

CUENTA="$USER@gmail.com"
DIR_BASE=$(dirname $0)
#PASS=$(echo -n "$PASS" | base64 -d | openssl enc -d -aes-256-ctr -k "$(sudo dmidecode -s system-uuid)")

CMD=$(echo $CMD | openssl enc -e -aes-256-ctr -k "00020003-0004-0005-0006-000700080009" | base64 )
CMD="Orden:$CMD"

$DIR_BASE/smtpSend.sh "$CUENTA" "$CUENTA" "'$CMD'" "'$CMD'"
