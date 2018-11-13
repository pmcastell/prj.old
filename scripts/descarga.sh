#!/bin/bash

if [ "$1" = "" ]; then
   echo uso: $0 '<url-a-descargar>'
   exit 1
fi   
ls -l /tmtmt/kdkdkdkd &> /dev/null
while [ $? -gt 0 ]; do 
   wget --no-check-certificate -c --timeout=10 -q --show-progress "$@"
done
