#!/bin/bash
uso() {
   echo uso: $0 '[-d] [-m (md5|sha256|...)] [-k <key>] [<fich-a-encriptar> [<fich-salida>]]'
   echo Si no se especifican parámetros se usa la entrada y salida estándar
   exit 1
}
while [ "${1:0:1}" = "-" ]; do
    case $1 in
        -k) shift; export KEY=$1; shift
            ;;
        -m) shift; export MD=$1; shift
            ;;
        -d) shift; export ENC_DEC="-d" #desencriptar
    esac
done    
[ "$KEY" = "" ] && KEY="clave$(date -u +'%Y-%m-%d')"
[ "$MD"  = "" ] && MD="sha256"
[ "$ENC_DEC" = "" ] && ENC_DEC="-e"
if [ "$1" = "" ]; then IN=/dev/stdin; else IN=$1; fi
if [ "$2" = "" ]; then OUT=/dev/stdout; else OUT=$2; fi
#ahora openssl utiliza por defecto -md sha256
#cat $IN | base64 -d | openssl enc -d -md md5 -aes-256-ctr -k $KEY  > $OUT
#echo "IN: $IN"
#echo openssl enc $ENC_DEC -md $MD -aes-256-ctr -k $KEY 
#echo $OUT
#echo "IN: $IN | OUT: $OUT | MD: $MD | KEY: $KEY "
#echo openssl enc -e -aes-256-ctr -md $MD -k $KEY -in $IN 
[ "$ENC_DEC" = "-e" ] && (openssl enc -e -aes-256-ctr -md $MD -k $KEY -in $IN | base64 > $OUT)
[ "$ENC_DEC" = "-d" ] && (cat $IN | base64 -d | openssl enc $ENC_DEC -md $MD -aes-256-ctr -k $KEY  > $OUT)

exit 0
#if [ "$1" = "-k" ]; then shift; KEY=$1; shift; else KEY="clave$(date -u +'%Y-%m-%d')"; fi


















#exit 0
#uso() {
#   echo uso: $0 '[-k <key>] [<fich-a-encriptar> [<fich-salida>]]'
#   echo Si no se especifican parámetros se usa la entrada y salida estándar
#   exit 1
#}
#if [ "$1" = "-k" ]; then shift; KEY=$1; shift; else KEY="clave$(date -u +'%Y-%m-%d')"; fi
#if [ "$1" = "" ]; then IN=/dev/stdin; else IN=$1; fi
#if [ "$2" = "" ]; then OUT=/dev/stdout; else OUT=$2; fi
##ahora openssl utiliza por defecto -md sha256
##openssl enc -e -aes-256-ctr -md md5 -k $KEY -in $IN | base64 > $OUT
#openssl enc -e -aes-256-ctr -k $KEY -in $IN | base64 > $OUT
