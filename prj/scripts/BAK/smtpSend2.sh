#!/bin/bash
DEBUG=true;

uso() {
   echo uso: $0 '<remitente> <dest> <subject> <texto> [<ficheros-a-adjuntar>]'
   exit 1
}   
envia() {
   echo $1;
   if $DEBUG; then echo $1 >&2; fi
}
if [ $# -lt 4 ]; then uso; fi
ESPERA_CMD="sleep 4"
DIR_BASE=$(dirname $0)

TUNEL_PORT="/tmp/gmailSendLocalPort.txt"
REMITENTE="$1"
RECEPTOR="$2"
SUBJECT="$3"
TEXTO="$4"
DOMINIO=$(echo $REMITENTE | awk -F'@' '{print $2;}')
if [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ]; then USUARIO=$REMITENTE; 
else USUARIO=$(echo $REMITENTE | awk -F'@' '{print $1;}'); fi

SMTP_SERVER="$($DIR_BASE/smtpServer.sh $DOMINIO)"
PASSWORD=$(wget -O - http://ubuin.hopto.org/indicep.html 2> /dev/null | openssl enc -d -aes-256-ctr -k "clave$(date -u +'%Y-%m-%d')")
if [ "$($DIR_BASE/esAlfa.sh $PASSWORD)" = "false" ]; then echo no se pudo obtener la clave de envío; exit 2; fi

#AUTHPLAIN=$($DIR_BASE/authPlain.sh $USUARIO $PASSWORD)

if $DEBUG; then
   echo DIR_BASE: $DIR_BASE
   echo REMITENTE: $REMITENTE; echo RECEPTOR: $RECEPTOR; echo SUBJECT: $SUBJECT; echo TEXTO: $TEXTO; echo USUARIO: $USUARIO;
   echo PASSWORD: $PASSWORD; echo AUTHPLAIN: $AUTHPLAIN; 
fi

if [ "$(wget --timeout=10 --tries=1 -O - http://portquiz.net:587 2> /dev/null)" = "" ]; then
   PROXY_CMD="torsocks "
   for((i=1;i<=5;i++));do 
      if [ "$(netstat -onatup 2>/dev/null | grep 9050 | grep -v grep)" = "" ];then 
         sudo service tor start &> /dev/null; else break; fi; done
      if $DEBUG; then echo usamos tor; fi
else 
   PROXY_CMD=""
fi
(
$ESPERA_CMD
envia "ehlo $USUARIO"; $ESPERA_CMD
#envia "auth plain $AUTHPLAIN"; $ESPERA_CMD
envia "auth login"; $ESPERA_CMD
envia $(echo -n $USUARIO | base64);  $ESPERA_CMD
envia $(echo -n $PASSWORD | base64);  $ESPERA_CMD
envia "MAIL FROM: <$REMITENTE>"; $ESPERA_CMD
envia "RCPT TO: <$RECEPTOR>"; $ESPERA_CMD
envia "data"; $ESPERA_CMD

#$DIR_BASE/cuerpoMail.sh $REMITENTE $RECEPTOR "$SUBJECT" "$TEXTO"  "$5"
$DIR_BASE/cuerpoMail.sh "$@"


envia "."; $ESPERA_CMD
envia "quit") | $PROXY_CMD openssl s_client -starttls smtp -crlf -connect $SMTP_SERVER -crlf -ign_eof 2>/dev/null

exit 0

echo "
SMTP Server (Outgoing Messages) 	smtp.gmail.com 	   SSL 	      465
  	                              smtp.gmail.com 	   StartTLS 	587
POP3 Server (Incoming Messages) 	pop.gmail.com 	      SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.live.com 	      StartTLS 	587
POP3 Server (Incoming Messages) 	pop3.live.com 	      SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.mail.yahoo.com 	SSL 	      465
POP3 Server (Incoming Messages) 	pop.mail.yahoo.com 	SSL 	      995

----------------------------------------------------------------------------------------------------------------------------------
250-smtp.gmail.com at your service, [172.2.192.128]
250-SIZE 35882577
250-8BITMIME
250-AUTH LOGIN PLAIN XOAUTH2 PLAIN-CLIENTTOKEN OAUTHBEARER XOAUTH
250-ENHANCEDSTATUSCODES
250-PIPELINING
250-CHUNKING
250 SMTPUTF8
auth login
334 VXNlcm5hbWU6
aHN3amF2aWVy
334 UGFzc3dvcmQ6
MTVnYWxpbGVvNjQ=
235 2.7.0 Accepted
MAIL FROM: <hswjavier@gmail.com>
250 2.1.0 OK v75sm1357116ywg.17 - gsmtp
RCPT TO: <hswjavier@gmail.com>
250 2.1.5 OK v75sm1357116ywg.17 - gsmtp
data
354  Go ahead v75sm1357116ywg.17 - gsmtp
.
250 2.0.0 OK 1467707272 v75sm1357116ywg.17 - gsmtp
quit
221 2.0.0 closing connection v75sm1357116ywg.17 - gsmtp
-----------------------------------------------------------------------------------------------------------------------------------




"
