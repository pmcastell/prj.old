#!/bin/bash

DIR_BASE=$(dirname $0)
if [ "$1" = "" ]; then FICHERO=indicep.html; else FICHERO=$1; fi
echo $(wget -O - http://ubuin.hopto.org/$FICHERO 2> /dev/null | /$DIR_BASE/desencriptar.sh)
