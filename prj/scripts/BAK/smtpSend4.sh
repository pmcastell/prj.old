#!/bin/bash

uso() {
   echo uso: $0 '<remitente> <dest> <subject> <texto> [<ficheros-a-adjuntar>]'
   exit 1
}   
envia() {
   echo $1;
   if $DEBUG; then echo $1 >&2; fi
}
hebraLectora() {
   FIFO=$1
   SALIDA=$2
   while  true; do
      read SMTP_RESP
      echo $SMTP_RESP >> $SALIDA
      #if [ "$(echo $SMTP_RESP | awk '{print $1;}')" = "221" ]; then DEBUG=false; fi
   done < $FIFO
   if $DEBUG; then echo TERMINADA HEBRA LECTORA >&2; fi
}   
espera1() {
   sleep 2;
}
espera() {
   COD=$1
   for((i=1;i<=5;i++)); do
      COD_LEIDO=$(tail -1 $TMP_SALIDA_FIFO | awk '{print $1;}')
      if [ "$COD_LEIDO" = "$COD" ];then if $DEBUG; then echo COD: $COD COD_LEIDO: $COD_LEIDO TMPS: $TMP_SALIDA_FIFO >&2;fi; break;  fi
      sleep 1
   done
}
espera2() {
   COD=$1
   RESP1="----------------------------------------------------------"
   RESP2=$SMTP_RESP
   for((i=1;i<5;i++)); do
      while [ "$RESP1" != "$RESP2" ]; do
         RESP1=$RESP2
         RESP2=$SMTP_RESP
      done
      if $DEBUG; then echo RESP1: $RESP1 RESP2: $RESP2 SMTP_RESP: $SMTP_RES  >&2; fi
      if [ "$(echo $RESP1 | awk '{print $1;}')" = "$COD" ]; then break; fi
      sleep 1;
   done
}   

if [ $# -lt 4 ]; then uso; fi
DEBUG=false;
ESPERA_CMD="sleep 4"
DIR_BASE=$(dirname $0)
SMTP_RESP=""
FIFO="/tmp/fifo-$(date +'%Y%m%d%H%M%S')"
mkfifo $FIFO
TMP_SALIDA_FIFO=$(tempfile)
REMITENTE="$1"
RECEPTOR="$2"
SUBJECT="$3"
TEXTO="$4"
USUARIO=$(echo $REMITENTE | awk -F'@' '{print $1;}')
PASSWORD=$(echo U2FsdGVkX19drRs6nj2qnWnhJeNfmvdJrInw | base64 -d | openssl enc -d -aes-256-ctr -k "$(sudo dmidecode -s system-uuid)")
DOMINIO=$(echo $REMITENTE | awk -F'@' '{print $2;}')
if   [ "$DOMINIO" = "gmail.com" ]; then SMTP_SERVER="smtp.gmail.com:587"; 
elif [ "$DOMINIO" = "yahoo.com" ] || [ "$DOMINIO" = "yahoo.es" ]; then SMTP_SERVER="smtp.mail.yahoo.com:587"; 
elif [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ]; then SMTP_SERVER="smtp.live.com:587";
elif [ "$DOMINIO" = "gva.es" ]; then SMTP_SERVER="smtp.gva.es:587"; fi

#pop3s.gva.es:995 o imaps.gva.es:993 Servidor Saliente : smtp.gva.es,
#marcando la casilla "Mi servidor requiere autenticación". 03015105@gva.es

#AUTHPLAIN=$($DIR_BASE/authPlain.sh $USUARIO $PASSWORD)

if $DEBUG; then echo DIR_BASE: $DIR_BASE REMITENTE: $REMITENTE RECEPTOR: $RECEPTOR SUBJECT: $SUBJECT  >&2; 
                echo TEXTO: $TEXTO USUARIO: $USUARIO PASSWORD: $PASSWORD AUTHPLAIN: $AUTHPLAIN FIFO: $FIFO >&2; fi

if [ "$(wget --timeout=10 --tries=1 -O - http://portquiz.net:587 2> /dev/null)" = "" ]; then
   PROXY_CMD="torsocks "
   for((i=1;i<=5;i++));do 
      if [ "$(netstat -onatup 2>/dev/null | grep 9050 | grep -v grep)" = "" ];then 
         sudo service tor start &> /dev/null; else break; fi; done; if $DEBUG; then echo usamos proxy >&2; fi
else 
   PROXY_CMD=""; if $DEBUG; then echo sin proxy >&2; fi
fi
hebraLectora $FIFO $TMP_SALIDA_FIFO &
(
espera 250
envia "ehlo $USUARIO"; espera 250
#echo "auth plain $AUTHPLAIN"; $ESPERA_CMD
envia "auth login"; espera 334
envia "$(echo -n $USUARIO | base64)";  espera 334
envia "$(echo -n $PASSWORD | base64)"; espera 235
envia "MAIL FROM: <$REMITENTE>"; espera 250
envia "RCPT TO: <$RECEPTOR>"; espera 250
envia "data"; espera 354

#$DIR_BASE/cuerpoMail.sh $REMITENTE $RECEPTOR "$SUBJECT" "$TEXTO"  "$5"
$DIR_BASE/cuerpoMail.sh "$@"


envia "."; espera 250
envia "quit"; espera 221 ) | $PROXY_CMD openssl s_client -starttls smtp -connect $SMTP_SERVER -crlf -ign_eof  &> $FIFO
if $DEBUG; then echo TERMINADO PRINCIPAL ENVIANDO: $ENVIANDO >&2; fi
kill $! &> /dev/null
rm $FIFO
#rm $TMP_SALIDA_FIFO
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

if [ "$REMITENTE" = "iesninfor@gmail.com" ]; then
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSWFJteFZVMjA1VjAxV2JETlhhMk0xVmpGYWMySkVUbGhoTVVwVVZtcEdTMlJIVmtWUmJVWlhWbXhzTTFadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVm1oRFZWWmFkR1ZHV214U2JHdzFWa2QwVjFWdFNrZFhiR2hhWVRKb1JGWldXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV2xOVWJVcEdZMFZ3VjJKVVJYZFdha1pYWkVaT2MxZHNhR2xTTW1oWlYxZDRiMkl5Vm5OVmJGWlRZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNSGRsUjBsNFUydGtXR0pIVWxsWmJGWmhZMnhXYzFWclpGZGlSbkJaV2xWb2ExWXdNVVZTYTFwV1lrWktTRlpxU2tabFZsWlpXa1p3YkdFelFrbFdWM0JIVkRKU1YxVnVVbXBTYXpWWVZXcE9iMkl4V25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqRmtkVnBGTlZOaVJtOTNWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaGhZVWRGZUdOR2FGaGlSbkJvVmtSS1QyUkdUbkphUmxKcFZqSm9lbGRYZUc5aU1XUlhWMWhvWVZKR1NuQlVWM1J6VFRGU1ZtRkhPV2hpUlhBd1ZsZDRjMWR0U2toaFJsSlhUVVp3VkZacVJtdGtSa3AwWlVaa2FWSnNhM2hXYTFwaFZURlZlRmR1U2s1WFJYQnhWVzB4YjFZeFVsaE9WazVPVFZad2VGVXlkREJXTVZweVkwWndXR0V4Y0hKWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RUYmtwb1VqSm9WRmxZY0Zka01WcHhVVzEwYVUxWFVsaFdNalZMVjBkS1NGVnRSbGRpVkVVd1ZqQmFZVmRIVWtoa1JtUnBWbGhDTmxaVVNURlVNVnAwVW01S1ZHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
else
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZiVFZyVmxVeFYyTkljRmhoTVhCUVdWZDRTMk14WkhGUmJGWlhZa2hDVVZkV1pEUlRNazE0V2toR1VtSkdXbGhaYTJoRFZWWmFjVkZ0UmxSTmF6RTFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV25kV01ERldZMFZ3VjJKVVJYZFdha1pYWkVaT2NtSkdTbWhsYlhoWFZtMTBWMU14VWtkV1dHaFlZbGhTV0ZSV2FFTlNiRnBZWlVaT1ZXSlZXVEpWYkZKRFZqQXhkVlZ1V2xaaGExcFlXa1ZhVDJOc2NFZGhSMnhUVFcxb2IxWXhaREJaVmxsM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxU2tkamJVVjZZVVphYUdFeGNHOVdha0poVkRKT2RGSnJaRmhpVjNodlZGVm9RMWRzV25KWGJHUmFWakZHTkZaSGRHdFdiVXBIVjJ4U1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaHJZVWRGZWxGcmJGZFdNMEpJVmtSS1UxWXhWblZWYlhCVFlrVndWVlp0ZUc5Uk1XUnpWMWhvV0dKRk5WUlVWbVEwVjFaV2RHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVIZFNWa1p5VDFkc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
SSH_PASS="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZNakExVmpBeFYySkVUbGhoTWsweFZtcEtTMUl5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVjNOM1pVWmFkR05GZEZSTlZXdzFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZrUjA1R1drWndWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMXBGV2xOVWJGcFlaSHBHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxUVhoa1ZsWjFWMnhrYUdFelFrbFdWM0JIVkRKU1YxWnVUbGhpVjNoVVdWUk9RMlJzV25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBxVWxkU1lWUlZXbmRsYkZweFVtMUdUMkpGV2xwWlZWcHJWVEZLV1ZGcmJGZGlXRUpJVmtSR2ExWXlUa1phUjJoVFRXNW9WVmRXVWs5Uk1XUnpWMWhvWVZKR1NsZFVWbHB6VGtaVmVXUkhkR2hXYXpWSFZqSjRVMWR0U2toaFJsSlhUVlp3V0ZreFdrdGpiVkpIVld4a2FWSnRPVE5XTW5oWFdWWlJlRmRzYUZSaVJuQlpWbXRXZDFZeGJISlhhM1JUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
fi  
