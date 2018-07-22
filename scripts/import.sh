#!/bin/bash

uso() {
   echo uso: $0 '<fichero> [<función-a-importar>]'
   exit 1
}

[ "$1" = "" ] && uso
   
