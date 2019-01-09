#!/bin/bash

uso() {
   echo uso $0 '<texto>' '<fichero-wav-o-mp3-salida>'
   exit 1
}
info() {
   echo "TEXTO: $TEXTO"; echo "SALIDA: $SALIDA"; echo "EXT: $EXT"
   echo "VOZ: $VOZ"; echo "TEXT2WAVE: $TEXT2WAVE"; echo "TEMP: $TEMP"
}   
([ "$1" = "" ] || [ "$2" = "" ]) && uso 
TEXTO="$1"; SALIDA="$2"; EXT=$(echo $SALIDA | awk -F'.' '{print $2;}')
[ "$EXT" != "wav" ] && [ "$EXT" != "mp3" ] && uso
[ "$3" = "" ] && VOZ="(voice_JuntaDeAndalucia_es_sf_diphone)" || VOZ="(voice_${3})"
TEMP=$(tempfile)
TEXT2WAVE="text2wave -eval $VOZ -o $TEMP.wav  "
info
if [ -f "$TEXTO" ]; then
   cat "$TEXTO" | iconv -f utf-8 -t iso-8859-1 | ${TEXT2WAVE} 
else   
   echo "$TEXTO" | ${TEXT2WAVE} 
fi   
if [ "$EXT" = "wav" ]; then
   eecho mv $TEMP.wav $SALIDA
elif [ "$EXT" = "mp3" ]; then   
   eecho lame -f $TEMP.wav $SALIDA &> /dev/null
   eecho rm $TEMP.wav
fi   
mplayer $SALIDA &> /dev/null
