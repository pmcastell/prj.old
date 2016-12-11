#!/bin/bash

uso() {
   cat<<EOF
   Uso: $0 
   Conecta con el servidor cuyo nombre coincida con el enlace con el que se le llama
EOF
   exit 1
}   
if [ "$1" = "-h" ]; then uso; fi
if [ -d /home/franav
