#!/bin/bash
#if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?;  fi
#echo $1 > "/sys/devices/pci0000:00/0000:00:01.0/0000:01:00.0/drm/card0/card0-LVDS-1/radeon_bl0/brightness"
DEV="/sys/devices/pci0000:00/0000:00:01.0/0000:01:00.0/drm/card0/card0-LVDS-1/radeon_bl0/brightness"
[ "$1" = "" ] && cat $DEV && exit 1
BR="$1"
SIGNO="${BR:0:1}"
#BR="${BR:1}"
ACTUAL="$(cat $DEV)"
([ "$SIGNO" = "+" ] || [ "$SIGNO" = "-" ]) && NUEVO=$(( $ACTUAL $SIGNO ${BR:1} )) || NUEVO="$BR"
[ "$NUEVO" -gt 255 ] && NUEVO="255"
[ "$NUEVO" -lt 0 ] && NUEVO="0"
echo $NUEVO | sudo tee $DEV

