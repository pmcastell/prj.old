#!/bin/bash

if [ "$1" = "" ]; then
   echo uso: $0 '<url-a-descargar> [<limit-rate>]'
   exit 1
fi   
if [ "$2" != "" ]; then
   LIMIT='--limit-rate='$2'K'
   echo $LIMIT
fi   
ls -l /tmtmt/kdkdkdkd &> /dev/null
while [ $? -gt 0 ]; do 
   wget -c --timeout=10 $LIMIT $1
done
