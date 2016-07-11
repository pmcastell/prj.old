uso() {
   echo $0 '<fichero-salida> <URL-stream-a-grabar>'
   exit -1
}
if [ "$1" = "" -0 "$2" = "" ];
then
   uso
fi      
#mplayer -dumpfile vaughan.mp3 -dumpstream http://vaughanradio.streaming-pro.com:8012
mplayer -dumpfile vaughan.mp3 -dumpstream rtmp://cdn.s6.eu.nice264.com:1935/niceLiveServer/vaughana_vaughan_radio_aac.stream
mplayer -dumpfile $1 -dumpstream $2
