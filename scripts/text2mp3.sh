uso() {
   echo uso $0 '<texto>' '<fichero-mp3-salida>'
   exit 1
}
if [ "$1" = "" -o "$2" = "" ]; then uso; fi  
TEMP=$(tempfile)
if [ -f $1 ]; then
   cat $1 | iconv -f utf-8 -t iso-8859-1 | text2wave -o $TEMP.wav
else   
   echo "$1" | text2wave -o $TEMP.wav
fi   
lame -f $TEMP.wav $2.mp3
rm $TEMP $TEMP.wav
