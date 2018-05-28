uso() {
   echo uso: $0 opciones
   echo opciones:
   echo '\-e s-comienzo s-duración' extraer un trozo de películla a partir del segundo s-segundo y tamaño s-duración segundos
   echo '\-mvs modificar volumen sonido'
}
   

#compilar ffmpeg:
aptitude install libfaac0 libfaac-dev libx11-dev libxvidcore-dev libx265-dev libvorbis-dev libtheora-dev libopencore-amrwb-dev libopencore-amrnb-dev libmp3lame-dev libvpx-dev
./configure --enable-gpl --enable-version3 --enable-nonfree --enable-postproc --enable-libfaac --enable-libmp3lame --enable-libopencore-amrnb --enable-libopencore-amrwb --enable-libtheora --enable-libvorbis --enable-libvpx --enable-libx265 --enable-libxvid --enable-x11grab

#Extraer un trozo de película (29 segundos) copy eindica dejar el mismo codec, vcodec se refiere al codec de video y acodec al de audio
ffmpeg -i input.avi -ss 00:54:24 -t 29 -vcodec copy -acodec copy output.avi
#Modificar volumen por trozos (en el ejemplo apagar audio entre los segundos 5 y 10, y 15 y 20
ffmpeg -i input.mp4 -af "volume=enable='between\(t,5,10\)':volume=0, volume=enable='between\(t,15,20\)':volume=0" ... output.mp4
#Cambiar formato contenedor de mmp4 a mkv y codec de audio
ffmpeg -i input.mp4 -vcodec copy -acodec mp3 output.mkv
#Escalar video 
ffmpeg -i input.mp4 -vf scale=1080x585 output.avi
#para que se vean en la tele: (cambiamos formato de mp4 a avi, qscale:v (v se refiere a vídeo) mientras más grande sea el número menos #calidad tendrá el vídeo, entre 4 y 6 tiene buena calidad
ffmpeg -i input.mp4 -qscale:v 4 output.avi
PELI="VoiceFromTheStone" ;ffmpeg -i $PELI.mp4 -b:v 2500k -b:a 128k $PELI.avi
#cambiar una pista de audio por otra (copy indica no volver a recodificar ninguno de los codecs, ni vídeo ni audio)
ffmpeg -i input.mp4 -i audioInput.mp3 -map 0:v -map 1:a -codec copy -shortest output.mp4
#normalizar audio
ffmpeg -i video.avi -af "volumedetect" -f null /dev/null
salida del comando anterior:
[Parsed_volumedetect_0 @ 0x7f8ba1c121a0] mean_volume: -16.0 dB
[Parsed_volumedetect_0 @ 0x7f8ba1c121a0] max_volume: -5.0 dB
[Parsed_volumedetect_0 @ 0x7f8ba1c121a0] histogram_0db: 87861
#ahora normalizamos al max_volume
ffmpeg -i video.avi -af "volume=5dB" -c:v copy -c:a libmp3lame -q:a 2 output.avi
#Cambiar mp4 a avi, recodificar vídeo (-qscale:v 4), escalar vídeo (-fv scale=1024x554), recodificar audio a mp3 (-c:a ...)
ffmpeg -i input.mp4 -qscale:v 4 -vf scale=1024x554 -c:a libmp3lame -q:a 2 output-2.avi

#Cambiar mp4 a webm (compatible html5), codificar en vp8 (webm permite vp8 (más antiguo) y vp9 (más nuevo):
ffmpeg -i input.mp4 -c:v libvpx -b:v 1M -c:a libvorbis output.webm
#aumentar volumen en ficheros mp3
mkdir ./nuevo;for i in $(ls *.mp3); do ffmpeg -i $i -q:a 2 -af volume=2.5 nuevo/$i;done
#Extraer audio de un apelícula y aumentar el volumen
ffmpeg -i peli.mkv -b:a 64k -af volume=3.0 -vcodec none -acodec mp3 peliAudio.mp3
#recodificar película, recodificar vídeo, recodificar audio como mp3  32kbits y aumentar volumen al doble y cortar los primeros 30 segundos
ffmpeg -i 106.2-106.3.mp4 -b:v 240k -r 7.563059 -acodec mp3 -b:a 32k -af volume=2.0 -ss 00:00:30 106.2-106.3-2.mp4
ffmpeg -i 106.1.mp4       -b:v 240k -r 7.563059 -acodec mp3 -b:a 32k -af volume=2.0 -ss 00:00:30 106.1-2.mp4
#crear un vídeo a partir de una imagen y un fichero de audio
#https://stackoverflow.com/questions/13824045/join-images-and-audio-to-result-video
ffmpeg -i /tmp/universo.jpg -i TheLuckyNumber.mp3 -r 5 -acodec copy TheLuckyNumber.mp4
