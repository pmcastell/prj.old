#!/bin/bash
#echo $1 $2 $3 $4 $5 $6 $7 $8 $9 | festival --tts --language spanish
#until [ -z "$1" ];
#do
#   LINEA="$LINEA $1"
#   shift
#done
#Ponemos el volumen maestro al 80%
##LINEA="$*"
##if [ -f $1 ];
##then
##   echo '(voice_JuntaDeAndalucia_es_sf_diphone)' '(tts "'$1'" nil)' | festival --pipe &> /dev/null
##else    
##   echo '(voice_JuntaDeAndalucia_es_sf_diphone)' '(SayText "'"$LINEA"'")' | festival --pipe &> /dev/null
##fi   
habla -v voice_JuntaDeAndalucia_es_sf_diphone $*
