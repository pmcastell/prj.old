#!/bin/bash
uso() {
   echo uso: $0 '[-k <key>] [<fich-a-encriptar> [<fich-salida>]]'
   echo Si no se especifican parámetros se usa la entrada y salida estándar
   exit 1
}
if [ "$1" = "-k" ]; then shift; KEY=$1; shift; else KEY="clave$(date -u +'%Y-%m-%d')"; fi
if [ "$1" = "" ]; then IN=/dev/stdin; else IN=$1; fi
if [ "$2" = "" ]; then OUT=/dev/stdout; else OUT=$2; fi
#ahora openssl utiliza por defecto -md sha256
#cat $IN | base64 -d | openssl enc -d -md md5 -aes-256-ctr -k $KEY  > $OUT
cat $IN | base64 -d | openssl enc -d -aes-256-ctr -k $KEY  > $OUT
