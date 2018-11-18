if [ "$1" != "" ]; then
   echo usando $1
fi
   
SALIDA=/home/usuario/Escritorio/vaughan/vaughan-$(date '+%d-%m-%Y-%H-%M').mp3

#rtsp://cdn.s1.eu.nice264.com:1935/niceLiveServer/vaughana_vaughan_radio_aac.streamext

PROTO="rtsp"
HOST=cdn.s1.eu.nice264.com:1935
DOC=niceLiveServer/vaughana_vaughan_radio_aac.streamext

PROTO="rtmp"
HOST=cdn.s6.eu.nice264.com:1935
DOC=niceLiveServer/vaughana_vaughan_radio_aac.stream


PROTO="http"
HOST="nodo05.cloud01.str:8057"
HOST="vaughanradioweb.streaming-pro.com"
PORT="8057"
DOC="streamdemo_mp3.nsv"
DOC=""


while true; do
    PID=$(ps aux | grep -i ssh | grep $HOST | grep -v grep | awk '{print $2;}' | head -1)
    [ "$PID" = "" ] && break
    eecho kill -TERM $PID
done
[ "$(curl -m 10 portquiz.net:${PORT} 2>/dev/null | grep -i successful)" = "" ] && zenity --info --text="El puerto ${PORT} de ${HOST} está cerrado." && exit 1
if [ "$1" == "ssh" ]; then
   #URI=rtmp://localhost:1935/niceLiveServer/vaughana_vaughan_radio_aac.stream
   URI=$PROTO://localhost:1935/$DOC
   ssh -L 1935:${HOST}:${PORT} -N franc@squid &
   sleep 10
else   
   #URI=rtmp://$HOST/niceLiveServer/vaughana_vaughan_radio_aac.stream
   URI="$PROTO://${HOST}:${PORT}/${DOC}"
fi   


eecho vlc  $URI --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=std{access=file,mux=raw,dst=$SALIDA}}" &
sleep 30
eecho vlc $SALIDA &
