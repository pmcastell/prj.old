#!/bin/bash

DEST="/media/usuario/PackardBell/MIOS/COPIAS/win7"
DEST="/media/usuario/PackardBell/MIOS/virtualbox/win7"
DIR_BASE=$(dirname $0)
[ ! -f $DIR_BASE/getMyPass.sh ] && echo no encuentro getMyPass.sh && exit 4
[ ! -d $DEST ] && echo No encuentro unidad de Destino && exit 1
[ ! -d /l/virtualbox-enc/win7 ] && echo No está Montada la Unidad /l/virtualbox-enc && exit 2
[ -f /media/usuario/PackardBell/MIOS/virtualbox/win7/win7.001 ] && echo ya existe una copia bórrala primero && exit 3
nice -19 7za a -v680m -w$DEST/ -mhe -p$($DIR_BASE/getMyPass.sh encfs) -r -ms=off $DEST/win7.7z /l/virtualbox-enc/win7
