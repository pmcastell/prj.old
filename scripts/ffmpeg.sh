uso() {
   echo uso: $0 opciones
   echo opciones:
   echo '\-e s-comienzo s-duración' extraer un trozo de películla a partir del segundo s-segundo y tamaño s-duración segundos
   echo '\-mvs modificar volumen sonido'
}
   
echo "   
#compilar ffmpeg:
aptitude install libfaac0 libfaac-dev libx11-dev libxvidcore-dev libx265-dev libvorbis-dev libtheora-dev libopencore-amrwb-dev libopencore-amrnb-dev libmp3lame-dev libvpx-dev
./configure --enable-gpl --enable-version3 --enable-nonfree --enable-postproc --enable-libfaac --enable-libmp3lame --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-libtheora --enable-libvorbis --enable-libvpx --enable-libx265 --enable-libxvid --enable-x11grab

#Extraer un trozo de película
ffmpeg -i m43UnknOwn.avi -ss 00:54:24 -t 29 -vcodec copy -acodec copy tampax.avi
#Modificar volumen por trozos
ffmpeg -i video.mp4 -af \"volume=enable='between\(t,5,10\)':volume=0, volume=enable='between\(t,15,20\)':volume=0\" ...
#Cambiar formato y codec de audio
ffmpeg -i NowAddHoney-2016-720p.mp4 -vcodec copy -acodec mp3 NowAddHoney-2016-720p.mkv
#Escalar video
ffmpeg -i Hail_Caesar-2016.mp4 -vf scale=1080x585 hail.avi

#para que se vean en la tele:
ffmpeg -i TheRewrite2014720pBluRayG2G.fm.mp4 -qscale:v 4 TheRewrite2014720pBluRayG2G.fm.avi
#cabiar una pista de audio por otra
ffmpeg -i video.mp4 -i audio.mp3 -map 0:v -map 1:a -codec copy -shortest out.mp4
#normalizar audio
ffmpeg -i video.avi -af "volumedetect" -f null /dev/null
[Parsed_volumedetect_0 @ 0x7f8ba1c121a0] mean_volume: -16.0 dB
[Parsed_volumedetect_0 @ 0x7f8ba1c121a0] max_volume: -5.0 dB
[Parsed_volumedetect_0 @ 0x7f8ba1c121a0] histogram_0db: 87861
ffmpeg -i video.avi -af "volume=5dB" -c:v copy -c:a libmp3lame -q:a 2 output.avi
"
ffmpeg -i HelloMyNameIsDoris2015.mp4 -qscale:v 4 -vf scale=1024x554 -c:a libmp3lame -q:a 2 HelloMyNameIsDoris2015-2.avi

#Codificar en vp8
ffmpeg -i input.mp4 -c:v libvpx -b:v 1M -c:a libvorbis output.webm
