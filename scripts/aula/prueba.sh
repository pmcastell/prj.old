#!/bin/bash

appendSiNoEsta() {
   CAD="$1"
   FICH="$2"
   if [ "$(cat $FICH | grep -F "$CAD")" = "" ]; then
      echo "$CAD" >> "$FICH"
   fi
}
appendSiNoEsta '15 15   * * * root  /sbin/poweroff' '/tmp/crontab'
appendSiNoEsta '15 21   * * * root  /sbin/poweroff' '/tmp/crontab'

