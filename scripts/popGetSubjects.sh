#!/bin/bash

DEBUG=false

uso() {
   echo uso: $0 '<cuenta-correo>' >&2
   echo 'Descarga los asuntos de los mensajes almacenados en <cuenta>'
   exit 1
}   
infor() {
   echo "$@" >&2
}
debug() {
   if $DEBUG; then echo "$@" >&2; fi
}
envia() {
   echo $1; 
   debug $1
}

espera() {
   COD=$1
   debug espera
   while read COD2; do
      COD2="${COD2:0:3}";
      debug COD: "|$COD|" "-" COD2: "|$COD2|"
      if [ "$COD" = "$COD2" ]; then break; fi
   done < $FIFO
   debug COD: "|$COD|" "-" COD2: "|$COD2|"
}   
obtenMensajes() {
   espera "+OK";
   envia "user $USUARIO"; espera "+OK";
   envia "pass $PASSWORD"; espera "+OK";
   envia "stat"; 
   while read RESP; do
      [ "${RESP:0:3}" = "+OK" ] && break; debug "RESP: $RESP"
   done < $FIFO
   NUM_MSG=$(echo $RESP | awk '{print $2;}'); debug "NUM_MSG: $NUM_MSG"
   
   for((i=1;i<=$NUM_MSG;i++)); do
      envia "top $i 0"; debug top $i 0;
      while read L; do
         debug "L: $L"
         [ "$L" = "$(printf '.\r')" ] && break;
         [ "$(echo $L | grep -iE ^Subject)" != "" ] && echo $L | awk -F'Subject:' '{print $2;}' >> $MENSAS;
      done < $FIFO
   done
   envia "quit"; espera "+OK";
   exit;
}   
if [ $# -lt 1 ]; then uso; fi

DIR_BASE=$(dirname $0)

CUENTA="$1"

DOMINIO=$(echo $CUENTA | awk -F'@' '{print $2;}'); debug "DOMINIO: $DOMINIO";
if [ "$DOMINIO" = "hotmail.com" ] || [ "$DOMINIO" = "live.com" ]; then USUARIO=$CUENTA; 
else USUARIO=$(echo $CUENTA | awk -F'@' '{print $1;}'); fi; debug "USUARIO: $USUARIO"

POP3_SERVER="$($DIR_BASE/mailServer.sh $DOMINIO pop3)"
PASSWORD=$($DIR_BASE/getMyPass.sh); debug "PASSWORD: $PASSWORD"
if [ "$($DIR_BASE/esAlfa.sh $PASSWORD)" = "false" ]; then infor No se pudo obtener la clave de envío; exit 2; fi
FIFO="/tmp/fifo-$(date +'%Y%m%d%H%M%S')"
mkfifo $FIFO; debug $FIFO

#AUTHPLAIN=$($DIR_BASE/authPlain.sh $USUARIO $PASSWORD)

debug DIR_BASE: $DIR_BASE CUENTA: $CUENTA USUARIO: $USUARIO PASSWORD: $PASSWORD

if [ "$(wget --timeout=10 --tries=1 -O - http://portquiz.net:$(echo $POP3_SERVER | awk -F':' '{print $2;}') 2> /dev/null)" = "" ]; then
   PROXY_CMD="torsocks "
   for((i=1;i<=5;i++));do 
      if [ "$(netstat -onatup 2>/dev/null | grep 9050 | grep -v grep)" = "" ];then 
         sudo service tor start &> /dev/null; else break; fi; done; debug usamos tor
else 
   PROXY_CMD=""; debug "sin proxy"
fi
MENSAS=$(tempfile)
obtenMensajes | $PROXY_CMD openssl s_client -connect $POP3_SERVER -crlf -ign_eof  &> $FIFO

rm $FIFO
cat $MENSAS
infor "Mensajes guardados en: $MENSAS"

exit 0

if [ "$CUENTA" = "iesninfor@gmail.com" ]; then
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSWFJteFZVMjA1VjAxV2JETlhhMk0xVmpGYWMySkVUbGhoTVVwVVZtcEdTMlJIVmtWUmJVWlhWbXhzTTFadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVm1oRFZWWmFkR1ZHV214U2JHdzFWa2QwVjFWdFNrZFhiR2hhWVRKb1JGWldXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV2xOVWJVcEdZMFZ3VjJKVVJYZFdha1pYWkVaT2MxZHNhR2xTTW1oWlYxZDRiMkl5Vm5OVmJGWlRZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNSGRsUjBsNFUydGtXR0pIVWxsWmJGWmhZMnhXYzFWclpGZGlSbkJaV2xWb2ExWXdNVVZTYTFwV1lrWktTRlpxU2tabFZsWlpXa1p3YkdFelFrbFdWM0JIVkRKU1YxVnVVbXBTYXpWWVZXcE9iMkl4V25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqRmtkVnBGTlZOaVJtOTNWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaGhZVWRGZUdOR2FGaGlSbkJvVmtSS1QyUkdUbkphUmxKcFZqSm9lbGRYZUc5aU1XUlhWMWhvWVZKR1NuQlVWM1J6VFRGU1ZtRkhPV2hpUlhBd1ZsZDRjMWR0U2toaFJsSlhUVVp3VkZacVJtdGtSa3AwWlVaa2FWSnNhM2hXYTFwaFZURlZlRmR1U2s1WFJYQnhWVzB4YjFZeFVsaE9WazVPVFZad2VGVXlkREJXTVZweVkwWndXR0V4Y0hKWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RUYmtwb1VqSm9WRmxZY0Zka01WcHhVVzEwYVUxWFVsaFdNalZMVjBkS1NGVnRSbGRpVkVVd1ZqQmFZVmRIVWtoa1JtUnBWbGhDTmxaVVNURlVNVnAwVW01S1ZHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
else
PASSWORD="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZiVFZyVmxVeFYyTkljRmhoTVhCUVdWZDRTMk14WkhGUmJGWlhZa2hDVVZkV1pEUlRNazE0V2toR1VtSkdXbGhaYTJoRFZWWmFjVkZ0UmxSTmF6RTFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV25kV01ERldZMFZ3VjJKVVJYZFdha1pYWkVaT2NtSkdTbWhsYlhoWFZtMTBWMU14VWtkV1dHaFlZbGhTV0ZSV2FFTlNiRnBZWlVaT1ZXSlZXVEpWYkZKRFZqQXhkVlZ1V2xaaGExcFlXa1ZhVDJOc2NFZGhSMnhUVFcxb2IxWXhaREJaVmxsM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxU2tkamJVVjZZVVphYUdFeGNHOVdha0poVkRKT2RGSnJaRmhpVjNodlZGVm9RMWRzV25KWGJHUmFWakZHTkZaSGRHdFdiVXBIVjJ4U1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFVWaG9hMVpzV2pGV01uaHJZVWRGZWxGcmJGZFdNMEpJVmtSS1UxWXhWblZWYlhCVFlrVndWVlp0ZUc5Uk1XUnpWMWhvV0dKRk5WUlVWbVEwVjFaV2RHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVIZFNWa1p5VDFkc1UwMHlhRmxXYlhCTFRrWlJlRmRzYUZSaE1sSnhWVzB4TkdGR1ZYZGhSVTVUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
SSH_PASS="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZNakExVmpBeFYySkVUbGhoTWsweFZtcEtTMUl5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVjNOM1pVWmFkR05GZEZSTlZXdzFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZrUjA1R1drWndWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMXBGV2xOVWJGcFlaSHBHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxUVhoa1ZsWjFWMnhrYUdFelFrbFdWM0JIVkRKU1YxWnVUbGhpVjNoVVdWUk9RMlJzV25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBxVWxkU1lWUlZXbmRsYkZweFVtMUdUMkpGV2xwWlZWcHJWVEZLV1ZGcmJGZGlXRUpJVmtSR2ExWXlUa1phUjJoVFRXNW9WVmRXVWs5Uk1XUnpWMWhvWVZKR1NsZFVWbHB6VGtaVmVXUkhkR2hXYXpWSFZqSjRVMWR0U2toaFJsSlhUVlp3V0ZreFdrdGpiVkpIVld4a2FWSnRPVE5XTW5oWFdWWlJlRmRzYUZSaVJuQlpWbXRXZDFZeGJISlhhM1JUVW14d2VGVXlkR0ZpUmxwelUyeHdXbFpXY0doWlZXUkdaVWRPUjJKR2FHaE5WbkJ2Vm10U1MxUXlVa2RVYmtwaFVteEtjRlpxU205bGJHUllaVWM1YVUxWFVsaFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1JtaFRUVVpaTVZac1pEUmpNV1IwVTJ0a1dHSlhhR0ZVVmxwM1ZrWmFjVkp1WkZOTlZrcDVWR3hhVDJGV1NuUlBWRTVYVFc1b1dGbFVRWGhTTVdSellVWlNhRTFzU25oV1YzUlhXVlpaZUZkdVJsVmlWR3h5V1d0YWQyVkdWblJrUkVKb1lYcEdlVlJzVm05WGJGcFhZMGhLV2xaWFVrZGFWM2hIWTIxS1IxcEdaRk5XV0VKMlZtcEdZV0V4VlhoWFdHaFZZbXhhVmxscldrdGpSbFp4VW10MFYxWnNjRWhXVjNSTFlUQXhSVkpzVGxaU2JFWXpWVVpGT1ZCUlBUMD0="
fi

#while read RESP; do 
#   debug "NUM_MSG: $NUM_MSG LONG RESP: ${#RESP} RESP: $RESP"
#   if [ "$RESP" = "$(printf '.\r')" ]; then 
#      debug salgo
#      break; 
#   else
#      NUM_MSG=$(( $NUM_MSG + 1 ));
#   fi
#done < $FIFO

