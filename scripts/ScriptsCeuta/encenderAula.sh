#!/bin/bash
source $(dirname $0)/funcionesAula.sh
main() {
    [ "$1" = "" ] && uso
    [ "$1" = "-l" ] && echo MACS CICLO1 && echo ------------------- && macsCiclo1 && echo ------------------- && echo MACS CICLO2 && echo ------------------- && macsCiclo2 && echo ------------------- && exit
    ( echo $1 | grep -Eq '([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}' ) && echo sudo etherwake -i $(interfaz) $1 && exit
    ( echo $1 | grep -Eq 'pc1[0-9]{2}') && (PC="$1";MAC=$(macsCiclo1 | grep "172.124.116.${PC:2}" | awk '{print $2;}'); echo sudo etherwake -i $(interfaz) $MAC) && exit
    ( echo $1 | grep -Eq 'pc2[0-9]{2}') && (PC="$1";MAC=$(macsCiclo2 | grep "172.124.117.1${PC:3}" | awk '{print $2;}'); echo sudo etherwake -i $(interfaz) $MAC) && exit
    [ "$1" != "ciclo1" ] && [ "$1" != "ciclo2" ] && uso
    IFACE=$(interfaz)
    ([ "$1" = "ciclo1" ] && macsCiclo1 || macsCiclo2) | while read L; do 
       echo sudo etherwake -i $IFACE $(echo $L | awk '{print $2;}')
       sudo etherwake -i $IFACE $(echo $L | awk '{print $2;}')
    done   
}
uso() {
   echo uso: $0 '<ciclo1|ciclo2|MAC|pc1xx|pc2xx>'
   exit 1
}   

interfaz() {
   ip a | grep 172.124 | awk '{print $NF;}'
}
main "$@"

