if [ "$1" != "" ];
then
   echo usando $1
fi
   
SALIDA=/home/usuario/Escritorio/vaughan/vaughan-$(date '+%d-%m-%Y-%H-%M').mp3
PID=$(ps aux | grep -i ssh | grep 1935 | grep -v grep | awk '{print $2;}' | head -1)
while [ "$PID" != "" ];
do
        eecho kill -TERM $PID
        PID=$(ps aux | grep -i ssh | grep 1935 | grep cdn | grep -v grep | awk '{print $2;}' | head -1)
done
#rtsp://cdn.s1.eu.nice264.com:1935/niceLiveServer/vaughana_vaughan_radio_aac.streamext

PROTO="rtsp"
HOST=cdn.s1.eu.nice264.com:1935
DOC=niceLiveServer/vaughana_vaughan_radio_aac.streamext

PROTO="rtmp"
HOST=cdn.s6.eu.nice264.com:1935
DOC=niceLiveServer/vaughana_vaughan_radio_aac.stream



if [ "$1" == "ssh" ];
then
   #URI=rtmp://localhost:1935/niceLiveServer/vaughana_vaughan_radio_aac.stream
   URI=$PROTO://localhost:1935/$DOC
   ssh -L 1935:$HOST -N franc@squid &
   sleep 10
else   
   #URI=rtmp://$HOST/niceLiveServer/vaughana_vaughan_radio_aac.stream
   URI="$PROTO://$HOST/$DOC"
fi   


eecho vlc  $URI --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=std{access=file,mux=raw,dst=$SALIDA}}" &
sleep 30
eecho vlc $SALIDA &