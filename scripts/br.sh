#!/bin/bash
[ "$1" = "" ] && echo debes suministrar un porcentaje de brillo && exit 1

if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?;  fi
echo $1 > "/sys/devices/pci0000:00/0000:00:01.0/0000:01:00.0/drm/card0/card0-LVDS-1/radeon_bl0/brightness"
