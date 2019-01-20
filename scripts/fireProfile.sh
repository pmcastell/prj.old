#!/bin/bash

[ "$1" != "" ] && PASSWD="$1" || PASSWD=$(echo QW5jcmltbzFNYW5hanUyCg== | base64 -d)
[ "$2" != "" ] && PROFILE="$2" || PROFILE="/home/usuario/.mozilla/firefox/o0kw6e4o.default"
DECRYPT_PRG=$(which firefox_decrypt.py)
[ "$DECRYPT_PRG" = "" ] && DECRYPT_PRG="$(dirname $0)/firefox_decrypt.py)"
/usr/bin/sshpass -p $PASSWD $DECRYPT_PRG $PROFILE
