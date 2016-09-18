#!/bin/bash
#th=-40
uso() {
   echo uso: $0 '<nivel-de-silencio-en-dB> <fichero-a-trocear>'
   exit 1
}
[ "$1" = "" ] && uso
[ "$2" = "" ] && uso
mp3splt -s -p th=$1 $2
