#SALIDA=/tmp/vaughan-$(date '+%d-%m-%Y-%H-%M').mp3

#eecho vlc  http://vaughanradio.streaming-pro.com:8012 --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=display,dst=std{access=file,mux=raw,dst=$SALIDA}}"
#eecho vlc  http://vaughanradio.streaming-pro.com:8012 --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=std{access=file,mux=raw,dst=
#$SALIDA}}" &

#eecho vlc  rtmp://cdn.s2.eu.nice264.com:1935/niceLiveServer/vaughana_vaughan_radio.streamext --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=std{access=file,mux=raw,dst=$SALIDA}}" &
#sshpass -p basura68 ssh -p 443 -L 1935:cdn.s2.eu.nice264.com:1935 -N usuario@shell.cjb.net &
#eecho vlc  rtmp://cdn.s2.eu.nice264.com:1935/niceLiveServer/vaughana_vaughan_radio.streamext --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=std{access=file,mux=raw,dst=$SALIDA}}" &


SALIDA=/home/usuario/Escritorio/vaughan/vaughan-$(date '+%d-%m-%Y-%H-%M').mp3
PID=$(ps aux | grep -i 'ssh -p 443 -L 1935:cdn.s2.eu.nice264.com' | grep -v grep | awk '{print $2;}' | head -1)
###while [ "$PID" != "" ];
###do
###        eecho kill -TERM $PID
###        PID=$(ps aux | grep -i 'ssh -p 443 -L 1935:cdn.s2.eu.nice264.com' | grep -v grep | awk '{print $2;}' | head -1)
###done
###sshpass -p basura68 ssh -p 443 -L 1935:cdn.s2.eu.nice264.com:1935 -N usuario@shell.cjb.net &

HOST=cdn.s6.eu.nice264.com:1935
URI=rtmp://$HOST/niceLiveServer/vaughana_vaughan_radio_aac.stream
#ssh -L 1935:$HOST -N franc@squid &

#sleep 10

eecho vlc  $URI --sout "#transcode{acodec=mp3,ab=64,channels=2}:duplicate{dst=std{access=file,mux=raw,dst=$SALIDA}}" &
sleep 30
eecho vlc $SALIDA
