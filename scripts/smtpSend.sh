#!/bin/bash

uso() {
   echo uso: $0 '<remitente> <dest> <subject> <texto> [<ficheros-a-adjuntar>]'
   exit 1
}   
if [ $# -lt 4 ]; then uso; fi
ESPERA_CMD="sleep 4"
DIR_BASE=$(dirname $0)

TUNEL_PORT="/tmp/gmailSendLocalPort.txt"
REMITENTE="$1"
RECEPTOR="$2"
SUBJECT="$3"
TEXTO="$4"
USUARIO=$(echo $REMITENTE | awk -F'@' '{print $1;}')
DOMINIO=$(echo $REMITENTE | awk -F'@' '{print $2;}')
if   [ "$DOMINIO" = "gmail.com" ]; then SMTP_SERVER="smtp.gmail.com:587"; 
elif [ "$DOMINIO" = "yahoo.com" ] || [ "$DOMINIO" = "yahoo.es" ]; then SMTP_SERVER="smtp.mail.yahoo.com:587"; 
elif [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ]; then SMTP_SERVER="smtp.live.com:587";
fi
if [ "$REMITENTE" = "iesninfor@gmail.com" ]; then
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSWFJteFZVMjA1VjAxV2JETlhhMk0xVmpGYWMySkVUbGhoTVVwVVZtcEdTMlJIVmtWUmJVWlhWbXhzTTFadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVm1oRFZWWmFkR1ZHV214U2JHdzFWa2QwVjFWdFNrZFhiR2hhWVRKb1JGWldXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV2xOVWJVcEdZMFZ3VjJKVVJYZFdha1pYWkVaT2MxZHNhR2xTTW1oWlYxZDRiMkl5Vm5OVmJGWlRZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNSGRsUjBsNFUydGtXR0pIVWxsWmJGWmhZMnhXYzFWclpGZGlSbkJaV2xWb2ExWXdNVVZTYTFwV1lrWktTRlpxU2tabFZsWlpXa1p3YkdFelFrbFdWM0JIVkRKU1YxVnVVbXBTYXpWWVZXcE9iMkl4V25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqRmtkVnBGTlZOaVJtOTNWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaGhZVWRGZUdOR2FGaGlSbkJvVmtSS1QyUkdUbkphUmxKcFZqSm9lbGRYZUc5aU1XUlhWMWhvWVZKR1NuQlVWM1J6VFRGU1ZtRkhPV2hpUlhBd1ZsZDRjMWR0U2toaFJsSlhUVVp3VkZacVJtdGtSa3AwWlVaa2FWSnNhM2hXYTFwaFZURlZlRmR1U2s1WFJYQnhWVzB4YjFZeFVsaE9WazVPVFZad2VGVXlkREJXTVZweVkwWndXR0V4Y0hKWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RUYmtwb1VqSm9WRmxZY0Zka01WcHhVVzEwYVUxWFVsaFdNalZMVjBkS1NGVnRSbGRpVkVVd1ZqQmFZVmRIVWtoa1JtUnBWbGhDTmxaVVNURlVNVnAwVW01S1ZHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
else
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZiVFZyVmxVeFYyTkljRmhoTVhCUVdWZDRTMk14WkhGUmJGWlhZa2hDVVZkV1pEUlRNazE0V2toR1VtSkdXbGhaYTJoRFZWWmFjVkZ0UmxSTmF6RTFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV25kV01ERldZMFZ3VjJKVVJYZFdha1pYWkVaT2NtSkdTbWhsYlhoWFZtMTBWMU14VWtkV1dHaFlZbGhTV0ZSV2FFTlNiRnBZWlVaT1ZXSlZXVEpWYkZKRFZqQXhkVlZ1V2xaaGExcFlXa1ZhVDJOc2NFZGhSMnhUVFcxb2IxWXhaREJaVmxsM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxU2tkamJVVjZZVVphYUdFeGNHOVdha0poVkRKT2RGSnJaRmhpVjNodlZGVm9RMWRzV25KWGJHUmFWakZHTkZaSGRHdFdiVXBIVjJ4U1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaHJZVWRGZWxGcmJGZFdNMEpJVmtSS1UxWXhWblZWYlhCVFlrVndWVlp0ZUc5Uk1XUnpWMWhvV0dKRk5WUlVWbVEwVjFaV2RHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVIZFNWa1p5VDFkc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
SSH_PASS="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZNakExVmpBeFYySkVUbGhoTWsweFZtcEtTMUl5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVjNOM1pVWmFkR05GZEZSTlZXdzFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZrUjA1R1drWndWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMXBGV2xOVWJGcFlaSHBHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxUVhoa1ZsWjFWMnhrYUdFelFrbFdWM0JIVkRKU1YxWnVUbGhpVjNoVVdWUk9RMlJzV25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBxVWxkU1lWUlZXbmRsYkZweFVtMUdUMkpGV2xwWlZWcHJWVEZLV1ZGcmJGZGlXRUpJVmtSR2ExWXlUa1phUjJoVFRXNW9WVmRXVWs5Uk1XUnpWMWhvWVZKR1NsZFVWbHB6VGtaVmVXUkhkR2hXYXpWSFZqSjRVMWR0U2toaFJsSlhUVlp3V0ZreFdrdGpiVkpIVld4a2FWSnRPVE5XTW5oWFdWWlJlRmRzYUZSaVJuQlpWbXRXZDFZeGJISlhhM1JUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
fi   
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZiVFZyVmxVeFYyTkljRmhoTVhCUVdWZDRTMk14WkhGUmJGWlhZa2hDVVZkV1pEUlRNazE0V2toR1VtSkdXbGhaYTJoRFZWWmFjVkZ0UmxSTmF6RTFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV25kV01ERldZMFZ3VjJKVVJYZFdha1pYWkVaT2NtSkdTbWhsYlhoWFZtMTBWMU14VWtkV1dHaFlZbGhTV0ZSV2FFTlNiRnBZWlVaT1ZXSlZXVEpWYkZKRFZqQXhkVlZ1V2xaaGExcFlXa1ZhVDJOc2NFZGhSMnhUVFcxb2IxWXhaREJaVmxsM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxU2tkamJVVjZZVVphYUdFeGNHOVdha0poVkRKT2RGSnJaRmhpVjNodlZGVm9RMWRzV25KWGJHUmFWakZHTkZaSGRHdFdiVXBIVjJ4U1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaHJZVWRGZWxGcmJGZFdNMEpJVmtSS1UxWXhWblZWYlhCVFlrVndWVlp0ZUc5Uk1XUnpWMWhvV0dKRk5WUlVWbVEwVjFaV2RHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVIZFNWa1p5VDFkc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
PASSWORD=$(echo $PASSWORD | $DIR_BASE/desencrypta.sh)

#AUTHPLAIN=$($DIR_BASE/authPlain.sh $USUARIO $PASSWORD)
DEBUG=false;
if $DEBUG; then
   echo DIR_BASE: $DIR_BASE
   echo REMITENTE: $REMITENTE; echo RECEPTOR: $RECEPTOR; echo SUBJECT: $SUBJECT; echo TEXTO: $TEXTO; echo USUARIO: $USUARIO;
   echo PASSWORD: $PASSWORD; echo AUTHPLAIN: $AUTHPLAIN; 
   exit
fi

if [ "$(wget --timeout=10 --tries=1 -O - http://portquiz.net:587 2> /dev/null)" = "" ]; then
   #if [ -f $TUNEL_PORT ]; then PORT=$(cat $TUNEL_PORT); else 
   #   PORT=$($DIR_BASE/puertoLibre.sh); echo $PORT > $TUNEL_PORT;
   #   ssh -L $PORT:$SMTP_SERVER -N 
   #fi
   #SMTP_SERVER="localhost:$PORT"
   PROXY_CMD="torsocks "
else 
   PROXY_CMD=""
fi
  
(echo "ehlo $USUARIO"; $ESPERA_CMD
#echo "auth plain $AUTHPLAIN"; $ESPERA_CMD
echo "auth login"; $ESPERA_CMD
echo -n $USUARIO | base64;  $ESPERA_CMD
echo -n $PASSWORD | base64;  $ESPERA_CMD
echo "MAIL FROM: <$REMITENTE>"; $ESPERA_CMD
echo "RCPT TO: <$RECEPTOR>"; $ESPERA_CMD
echo "data"; $ESPERA_CMD

#$DIR_BASE/cuerpoMail.sh $REMITENTE $RECEPTOR "$SUBJECT" "$TEXTO"  "$5"
$DIR_BASE/cuerpoMail.sh "$@"


echo "."; $ESPERA_CMD
echo "quit") | $PROXY_CMD openssl s_client -starttls smtp -crlf -connect $SMTP_SERVER -crlf -ign_eof 2>/dev/null

exit 0

echo "
SMTP Server (Outgoing Messages) 	smtp.gmail.com 	   SSL 	      465
  	                              smtp.gmail.com 	   StartTLS 	587
POP3 Server (Incoming Messages) 	pop.gmail.com 	      SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.live.com 	      StartTLS 	587
POP3 Server (Incoming Messages) 	pop3.live.com 	      SSL 	      995

SMTP Server (Outgoing Messages) 	smtp.mail.yahoo.com 	SSL 	      465
POP3 Server (Incoming Messages) 	pop.mail.yahoo.com 	SSL 	      995

"
