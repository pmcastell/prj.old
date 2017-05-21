#!/bin/bash

uso() {
   cat<<EOF
   Uso: $1
EOF
   exit 1
}
appendSiNoEsta() {
   CAD="$1"
   FICH="$2"
   [ ! -f "$FICH" ] && touch $FICH
   if [ "$(cat "$FICH" | grep -F "$CAD")" = "" ]; then
      echo "$CAD" >> "$FICH"
   fi
}
