uso() {
   echo $0 '<fichero-salida> <URL-stream-a-grabar>'
   exit -1
}
if [ "$1" = "" -0 "$2" = "" ];
then
   uso
fi      
#mplayer -dumpfile vaughan.mp3 -dumpstream http://vaughanradio.streaming-pro.com:8012
mplayer -dumpfile $1 -dumpstream $2
