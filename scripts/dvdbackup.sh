#!/bin/bash
uso() {
   echo $0 '<dir-destino>'
   exit 1
}

if [ "$1" = "" ];
then
   uso
fi     
dvdbackup -i /dev/sr0 -M -o $1
