#!/bin/bash
. /scripts/funcionesAux.sh

[ "$1" = "" ] && echo necesito un usuario && exit 1
[ "$1" = "-e" ] && EXEC=si && shift
[ "$1" = "" ] && echo necesito un usuario && exit 1
FSUDOERS="/etc/sudoers"
L1="$1 ALL=(ALL:ALL) ALL"
L2="$1 ALL = NOPASSWD: ALL"
[ "$(sudo cat $FSUDOERS | grep '"$L1"')" = "" ] && (echo "$L1" | sudo tee -a $FSUDOERS)
[ "$EXEC" = "si" ] && [ "$(sudo cat $FSUDOERS | grep '"$L2"')" = "" ] && (echo "$L2" | sudo tee -a $FSUDOERS)


