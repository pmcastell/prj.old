#ffmpeg -i entrada.flac -acodec libmp3lame -ab 192k -ac 2 -ar 44100 salida.mp3
ffmpeg -i $1 -ab 192k -ac 2 -ar 48000 $1.mp3
