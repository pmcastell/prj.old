uso() {
   echo uso $0 '<texto>' '<fichero-mp3-salida>'
   exit 1
}
if [ "$1" = "" -o "$2" = "" ]; then uso; fi  
TEMP=$(tempfile)
echo "$1" | text2wave -o $TEMP.wav
lame -f $TEMP.wav $2.mp3
rm $TEMP $TEMP.wav
