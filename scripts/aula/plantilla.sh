#!/bin/bash
uso() {
   cat<<EOF
   uso S0 
EOF
   exit 1
}

if [ "$1" = "-h" ]; then uso; fi      
