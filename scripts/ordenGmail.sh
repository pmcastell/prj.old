#!/bin/bash

#"U2FsdGVkX18Qd1c9SgvifpcNzWDJxHJoBIw="
#"00020003-0004-0005-0006-000700080009"

#gal
#U2FsdGVkX18vw3YDFJZy14i1mXHJ5Eq3ERbr
  

uso() {
   echo uso: $0 '<email> <password-email-aes-256-ctr--base64>'
   exit 1
}
if [ $# -lt 2 ]; then uso; fi

CUENTA="$1"
PASS="$2"
USER="$(echo $CUENTA | awk -F'@' '{print $1;}')"
DIR_BASE=$(dirname $0)

PASS=$(echo -n "$PASS" | base64 -d | openssl enc -d -aes-256-ctr -k "$(sudo dmidecode -s system-uuid)")
CMD=$(wget -O - https://$USER:$PASS@mail.google.com/mail/feed/atom 2> /dev/null | xmllint --format - | grep  '<title>' | grep 'Orden:')
CMD=$(echo $CMD | awk -F 'Orden:' '{print $2;}'| awk -F"'</title>" '{print $1;}')
CMD=$(echo $CMD | base64 -d | openssl enc -d -aes-256-ctr -k "$(sudo dmidecode -s system-uuid)")
TEMP=$(tempfile)
$CMD &> $TEMP
$DIR_BASE/smtpSend.sh "$CUENTA" "$CUENTA" "'Orden ejecutada $(date)'" "'Orden ejecutada: $CMD'" $TEMP
rm $TEMP

