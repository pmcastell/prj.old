#!/bin/bash
uso() {
   echo uso: $0 '<remitente> <dest> <subject> <texto> [<ficheros-a-adjuntar>]'
   exit 1
}   

REMITENTE="$1"
RECEPTOR="$2"
SUBJECT="$3"
TEXTO="$4"
USUARIO=$(echo $REMITENTE | awk -F'@' '{print $1;}')
PASSWORD=basura68
AUTHPLAIN=$(/m/Mios/prj/scripts/authPlain.sh $USUARIO $PASSWORD)
(echo "ehlo $USUARIO";sleep 2
echo "auth plain $AUTHPLAIN";sleep 2
echo "MAIL FROM: <$REMITENTE>";sleep 2
echo "RCPT TO: <$RECEPTOR>";sleep 2
echo "data";sleep 2

#/m/Mios/prj/scripts/cuerpoMail.sh $REMITENTE $RECEPTOR "$SUBJECT" "$TEXTO"  "$5"
/m/Mios/prj/scripts/cuerpoMail.sh "$@"

###echo "From: <$REMITENTE>
###To: <$RECEPTOR>
###Subject: $SUBJEC

###HOLITAS
echo ".";sleep 2
echo "quit") | openssl s_client -starttls smtp -crlf -connect smtp.gmail.com:587 -crlf -ign_eof

