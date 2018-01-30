#!/bin/bash

DEBUG=false

uso() {
   #/scripts/smtpSend.sh iespinfo@gmx.es infosmr2@gmail.com "el libro" "te lo mando" /m/tmp/MARI/Libros/Ingles/The\ catcher\ in\ the\ rye\ -\ Jerome\ David\ Salinger.epub
   echo uso: $0 '<remitente> <dest> <subject> <texto> [<ficheros-a-adjuntar>]' >&2
   exit 1
}   
infor() {
   echo "$@" >&2
}
envia() {
   echo $1;
   if $DEBUG; then infor $1 ; fi
}

espera() {
   COD="$@"
   RECIBIDO=false
   while read COD2; do
      if $DEBUG; then infor "COD: |$COD| - COD2: |$COD2|" ; fi
      COD2="${COD2:0:3}";
      #if [ "$COD" = "$COD2" ]; then break; fi
      for c in $COD; do if [ "$c" == "$COD2" ]; then RECIBIDO=true; break; fi; done
      [ "$RECIBIDO" = "true" ] && break
   done < $FIFO
   if $DEBUG; then infor Salgo de espera COD: "|$COD|" "-" COD2: "|$COD2|" ; fi
}   

   
if [ $# -lt 4 ]; then uso; fi

DIR_BASE=$(dirname $0)

REMITENTE="$1"
RECEPTOR="$2"
SUBJECT="$3"
TEXTO="$4"

DOMINIO=$(echo $REMITENTE | awk -F'@' '{print $2;}')
if [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ] || [ "$DOMINIO" = "gmx.es" ]; then USUARIO=$REMITENTE; 
else USUARIO=$(echo $REMITENTE | awk -F'@' '{print $1;}'); fi

SMTP_SERVER="$($DIR_BASE/smtpServer.sh $DOMINIO)"
[ "$(echo $SMTP_SERVER | grep 587)" != "" ] && START_TLS="-starttls smtp"
PASSWORD=$($DIR_BASE/getMyPass.sh $USUARIO)
if $DEBUG; then  infor "PASS: $PASSWORD, USUARIO: $USUARIO, DIR_BASE: $DIR_BASE"; fi
if [ "$($DIR_BASE/esAlfa.sh $PASSWORD)" = "false" ]; then echo no se pudo obtener la clave de envío; exit 2; fi
FIFO="/tmp/fifo-$(date +'%Y%m%d%H%M%S')"
mkfifo $FIFO


#AUTHPLAIN=$($DIR_BASE/authPlain.sh $USUARIO $PASSWORD)

if $DEBUG; then infor DIR_BASE: $DIR_BASE REMITENTE: $REMITENTE RECEPTOR: $RECEPTOR SUBJECT: $SUBJECT ; 
                infor TEXTO: $TEXTO USUARIO: $USUARIO PASSWORD: $PASSWORD AUTHPLAIN: $AUTHPLAIN START_TLS: $START_TLS; fi

if [ "$(wget --timeout=10 --tries=1 -O - http://portquiz.net:$(echo $SMTP_SERVER | awk -F':' '{print $2;}') 2> /dev/null)" = "" ]; then
   PROXY_CMD="torsocks "
   for((i=1;i<=5;i++));do 
      if [ "$(netstat -onatup 2>/dev/null | grep 9050 | grep -v grep)" = "" ];then 
         sudo service tor start &> /dev/null; else break; fi; done; if $DEBUG; then infor usamos tor ; fi
else 
   PROXY_CMD=""; if $DEBUG; then infor sin proxy ; fi
fi

if $DEBUG; then infor EMPEZANDO; fi
(
espera 250 220
envia "ehlo $USUARIO"; espera 250
#echo "auth plain $AUTHPLAIN"; $ESPERA_CMD
envia "auth login"; espera 334
envia "$(echo -n $USUARIO | base64)";  espera 334
infor "Antes de enviar la contraseña: $PASSWORD"
envia "$(echo -n $PASSWORD | base64)"; espera 235
infor "Después de enviar: $PASSWORD"
envia "MAIL FROM: <$REMITENTE>"; espera 250
envia "RCPT TO: <$RECEPTOR>"; espera 250
envia "data"; espera 354

#$DIR_BASE/cuerpoMail.sh $REMITENTE $RECEPTOR "$SUBJECT" "$TEXTO"  "$5"
$DIR_BASE/cuerpoMail.sh "$@"


envia "."; espera 250
envia "quit"; espera 221 ) | $PROXY_CMD openssl s_client $START_TLS -connect $SMTP_SERVER -crlf -ign_eof  &> $FIFO
rm $FIFO
infor "Mensaje enviado con éxito?"

exit 0

if [ "$REMITENTE" = "iesninfor@gmail.com" ]; then
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSWFJteFZVMjA1VjAxV2JETlhhMk0xVmpGYWMySkVUbGhoTVVwVVZtcEdTMlJIVmtWUmJVWlhWbXhzTTFadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVm1oRFZWWmFkR1ZHV214U2JHdzFWa2QwVjFWdFNrZFhiR2hhWVRKb1JGWldXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV2xOVWJVcEdZMFZ3VjJKVVJYZFdha1pYWkVaT2MxZHNhR2xTTW1oWlYxZDRiMkl5Vm5OVmJGWlRZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNSGRsUjBsNFUydGtXR0pIVWxsWmJGWmhZMnhXYzFWclpGZGlSbkJaV2xWb2ExWXdNVVZTYTFwV1lrWktTRlpxU2tabFZsWlpXa1p3YkdFelFrbFdWM0JIVkRKU1YxVnVVbXBTYXpWWVZXcE9iMkl4V25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqRmtkVnBGTlZOaVJtOTNWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaGhZVWRGZUdOR2FGaGlSbkJvVmtSS1QyUkdUbkphUmxKcFZqSm9lbGRYZUc5aU1XUlhWMWhvWVZKR1NuQlVWM1J6VFRGU1ZtRkhPV2hpUlhBd1ZsZDRjMWR0U2toaFJsSlhUVVp3VkZacVJtdGtSa3AwWlVaa2FWSnNhM2hXYTFwaFZURlZlRmR1U2s1WFJYQnhWVzB4YjFZeFVsaE9WazVPVFZad2VGVXlkREJXTVZweVkwWndXR0V4Y0hKWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RUYmtwb1VqSm9WRmxZY0Zka01WcHhVVzEwYVUxWFVsaFdNalZMVjBkS1NGVnRSbGRpVkVVd1ZqQmFZVmRIVWtoa1JtUnBWbGhDTmxaVVNURlVNVnAwVW01S1ZHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
else
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZiVFZyVmxVeFYyTkljRmhoTVhCUVdWZDRTMk14WkhGUmJGWlhZa2hDVVZkV1pEUlRNazE0V2toR1VtSkdXbGhaYTJoRFZWWmFjVkZ0UmxSTmF6RTFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV25kV01ERldZMFZ3VjJKVVJYZFdha1pYWkVaT2NtSkdTbWhsYlhoWFZtMTBWMU14VWtkV1dHaFlZbGhTV0ZSV2FFTlNiRnBZWlVaT1ZXSlZXVEpWYkZKRFZqQXhkVlZ1V2xaaGExcFlXa1ZhVDJOc2NFZGhSMnhUVFcxb2IxWXhaREJaVmxsM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxU2tkamJVVjZZVVphYUdFeGNHOVdha0poVkRKT2RGSnJaRmhpVjNodlZGVm9RMWRzV25KWGJHUmFWakZHTkZaSGRHdFdiVXBIVjJ4U1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaHJZVWRGZWxGcmJGZFdNMEpJVmtSS1UxWXhWblZWYlhCVFlrVndWVlp0ZUc5Uk1XUnpWMWhvV0dKRk5WUlVWbVEwVjFaV2RHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVIZFNWa1p5VDFkc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
SSH_PASS="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZNakExVmpBeFYySkVUbGhoTWsweFZtcEtTMUl5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVjNOM1pVWmFkR05GZEZSTlZXdzFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZrUjA1R1drWndWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMXBGV2xOVWJGcFlaSHBHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxUVhoa1ZsWjFWMnhrYUdFelFrbFdWM0JIVkRKU1YxWnVUbGhpVjNoVVdWUk9RMlJzV25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBxVWxkU1lWUlZXbmRsYkZweFVtMUdUMkpGV2xwWlZWcHJWVEZLV1ZGcmJGZGlXRUpJVmtSR2ExWXlUa1phUjJoVFRXNW9WVmRXVWs5Uk1XUnpWMWhvWVZKR1NsZFVWbHB6VGtaVmVXUkhkR2hXYXpWSFZqSjRVMWR0U2toaFJsSlhUVlp3V0ZreFdrdGpiVkpIVld4a2FWSnRPVE5XTW5oWFdWWlJlRmRzYUZSaVJuQlpWbXRXZDFZeGJISlhhM1JUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
fi  
