#!/bin/bash

if [ "$1" = "" ]; then
   echo uso: $0 '<url-a-descargar>'
   exit 1
fi   
[ "$(which axel)" = "" ] && sudo apt -y install axel &> /dev/null
[ "$(which axel)" = "" ] && DOWNLOADER="wget --no-check-certificate -c --timeout=10 -q --show-progress  " || DOWNLOADER="axel -k -n 15  "
ls -l /tmtmt/kdkdkdkd &> /dev/null
while [ $? -gt 0 ]; do 
#   wget --no-check-certificate -c --timeout=10 -q --show-progress "$@"
   $DOWNLOADER "$@"
done
