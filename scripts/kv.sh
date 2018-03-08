#!/bin/bash

sudo kvm -machine q35 -soundhw hda -vga vmware -k es -boot menu=on -localtime -net nic,vlan=0,macaddr=00:1d:60:d8:69:b7 -net tap,vlan=0 "$@" &
