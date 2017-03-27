#!/bin/bash
uso() {
   echo uso: $0 '[<fich-a-encriptar> [<fich-salida>]]'
   echo Si no se especifican parámetros se usa la entrada y salida estándar
   exit 1
}
if [ "$1" = "" ]; then IN=/dev/stdin; else IN=$1; fi
if [ "$2" = "" ]; then OUT=/dev/stdout; else OUT=$2; fi
openssl enc -e -aes-256-ctr -k "clave$(date -u +'%Y-%m-%d')"  -in $IN | base64 > $OUT
