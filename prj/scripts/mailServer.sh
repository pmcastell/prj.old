#!/bin/bash
uso() {
   echo uso: $0 '<dominio>'
   echo devuelve el servidor y puerto de smtp de ese dominio
   exit 1
}   
DOMINIO=$1
if [ "$2" = "" ] || [ "$2" = "smtp" ]; then
   if   [ "$DOMINIO" = "gmail.com" ]; then MAIL_SERVER="smtp.gmail.com:587"; 
   elif [ "$DOMINIO" = "yahoo.com" ] || [ "$DOMINIO" = "yahoo.es" ]; then MAIL_SERVER="smtp.mail.yahoo.com:587"; 
   elif [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ]; then MAIL_SERVER="smtp.live.com:587";
   elif [ "$DOMINIO" = "gva.es" ]; then MAIL_SERVER="smtp.gva.es:587"; fi
elif [ "$2" = "pop3" ]; then
   if   [ "$DOMINIO" = "gmail.com" ]; then MAIL_SERVER="pop.gmail.com:995"; 
   elif [ "$DOMINIO" = "yahoo.com" ] || [ "$DOMINIO" = "yahoo.es" ]; then MAIL_SERVER="pop.mail.yahoo.com:995"; 
   elif [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ]; then MAIL_SERVER="pop3.live.com:995";
   elif [ "$DOMINIO" = "gva.es" ]; then MAIL_SERVER="pop3.gva.es:995"; fi
fi
   
echo $MAIL_SERVER
exit 0

echo "
SMTP Server (Outgoing Messages) 	smtp.gmail.com 	   SSL 	      465
  	                              smtp.gmail.com 	   StartTLS 	587
POP3 Server (Incoming Messages) 	pop.gmail.com 	      SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.live.com 	      StartTLS 	587
POP3 Server (Incoming Messages) 	pop3.live.com 	      SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.mail.yahoo.com 	SSL 	      465
POP3 Server (Incoming Messages) 	pop.mail.yahoo.com 	SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.gva.es 	      StartTLS 	587
POP3 Server (Incoming Messages) 	pop3.gva.es 	      SSL 	      995

"
