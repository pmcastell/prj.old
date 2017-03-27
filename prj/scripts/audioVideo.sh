#ffmpeg -isync -i input.wav -i input.avi -acodec mp2 -ab 192k -vcodec copy output-final.avi
#mencoder  -ovc copy -oac copy -audiofile "$baseName.mp3" "$baseName-nosound.avi" -o "$baseName-final.avi"
mencoder  -ovc copy -oac copy -audiofile $1 $2 -o $2_audio.avi


