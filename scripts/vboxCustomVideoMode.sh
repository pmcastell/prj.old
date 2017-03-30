#!/bin/bash
uso() {
   cat<<EOF
   uso $0 maquina resolución
   Ejemplo $0 win7 1440x900x32
EOF
   exit 1
}
[ "$2" = "" ] && uso
vboxmanage setextradata "$1" "CustomVideoMode1" "$2"
