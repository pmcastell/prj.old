#!/bin/bash

sudo kvm -soundhw hda -vga vmware -k es -boot menu=on -localtime  "$@" &
#machine q35
#-net nic,vlan=0,macaddr=00:1d:60:d8:69:b7 -net tap,vlan=0 "$*" &
