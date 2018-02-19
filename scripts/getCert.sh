#!/bin/bash
DEST="$1"
[ "$1" = "" ] && uso
[ "$2" = "" ] && PORT="443" || PORT="$2"
[ "$(echo $1 | grep ':')" != "" ] && DEST=$(echo $1 | awk -F':' '{print $1;}')  && PORT=$(echo $1 | awk -F':' '{print $2;}')
echo | openssl s_client -connect  $DEST:$PORT 2>/dev/null | awk 'BEGIN {found=false} /CERTIFICATE/{if (found) print; found=!found} {if (found) print }'
