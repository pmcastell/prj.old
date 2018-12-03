#!/bin/bash

[ "$1" = "" ] && IFACE="vmnet1" || IFACE="$1"
[ "$2" = "" ] && TAP="tap0" || TAP="$2"
[ "$(ip a | grep $IFACE)" = "" ] && echo Error no se ha encontrado $IFACE && exit 1
ip link set $IFACE down
ip link set $IFACE name $TAP
ip link set $TAP up
