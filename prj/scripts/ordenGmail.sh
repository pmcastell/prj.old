#!/bin/bash
DEBUG=false
uso() {
   echo uso: $0 '<email>'
   exit 1
}
debug() {
   if $DEBUG; then echo $1 >&2; fi
}   
obtenerOrden2() {
   PASS=$($DIR_BASE/getMyPass.sh);  debug "PASS: $PASS"
   if [ "$($DIR_BASE/esAlfa.sh "$PASS")" = "false" ]; then exit; fi
   #CMD=$(wget -O - https://$USUARIO:$PASS@mail.google.com/mail/feed/atom 2> /dev/null | xmllint --format - | grep  '<title>' | grep 'Ejecutar Orden: '|head -1)
   CMD=$(wget -O - https://$USUARIO:$PASS@mail.google.com/mail/feed/atom 2> /dev/null); debug "CMD: $CMD";
   if [ "$CMD" = "" ]; then exit; fi
   CMD=$(echo $CMD |  xmllint --format - | grep  '<title>' | grep 'Ejecutar Orden: '|head -1); debug "CMD: $CMD";
   if [ "$CMD" = "" ]; then exit; fi
   CMD=$(echo $CMD | awk -F 'Ejecutar Orden: ' '{print $2;}'| awk -F"</title>" '{print $1;}'); debug "CMD: $CMD";
   CMD=$(echo $CMD | awk -F"'" '{print $1;}'); debug "CMD: $CMD";
   CMD=$(echo -n $CMD | $DIR_BASE/desencriptar.sh); debug "CMD: $CMD";
   echo $CMD
}
obtenerOrden() {
   CMD=$($DIR_BASE/popGetSubjects.sh $CUENTA | grep "Ejecutar Orden: " | tail -1)
   if [ "$CMD" = "" ]; then exit; fi
   CMD=$(echo $CMD | awk -F 'Ejecutar Orden: ' '{print $2;}'); debug "CMD: $CMD";
   CMD=$(echo $CMD | awk -F"'" '{print $1;}'); debug "CMD: $CMD";
   CMD=$(echo -n $CMD | $DIR_BASE/desencriptar.sh); debug "CMD: $CMD";
   echo $CMD
}   
   
if [ $# -lt 1 ]; then uso; fi

CUENTA="$1"
USUARIO="$(echo $CUENTA | awk -F'@' '{print $1;}')"
DIR_BASE=$(dirname $0)

CMD=$(obtenerOrden)
debug "Ejecutar Orden: $CMD";
if [ "$CMD" = "" ]; then exit; fi
TEMP=$(tempfile)
$CMD &> $TEMP

$DIR_BASE/smtpSend.sh "$CUENTA" "$CUENTA" "'Orden ejecutada $(date) $CMD'" "'$(cat $TEMP)'"
rm $TEMP

exit 0

echo "
#"U2FsdGVkX18Qd1c9SgvifpcNzWDJxHJoBIw="
#"00020003-0004-0005-0006-000700080009"

#gal
#U2FsdGVkX18vw3YDFJZy14i1mXHJ5Eq3ERbr

"
