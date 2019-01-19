#!/bin/bash
if [ "$2" != "" ]; then 
   if [ "$(echo $2 | grep '\-')" != "" ]; then
      ITEMS="--playlist-items $(seq -s "," $(echo $2 | tr "-" " "))"
   else
      ITEMS="--playlist-items $2";
   fi
else ITEMS=""; fi
#ITEMS=$(echo $ITEMS | sed "s/-/\\\-/g")
#youtube-dl --write-srt --sub-lang es,en --sub-format srt -f bestvideo+bestaudio https://www.youtube.com/watch?v=Zh3Yz3PiXZw&feature=youtu.be
#--write-auto-sub
#youtube-dl --write-srt --sub-lang es,en --sub-format srt -f bestvideo+bestaudio --auto-number -f bestvideo+bestaudio https://www.youtube.com/playlist?list=PLq1noKggzASu92gX_ARJRk-W9aX_4OL7d
#youtube-dl --playlist-start 54 --write-srt --sub-lang es,en --sub-format srt -f bestvideo+bestaudio --auto-number --autonumber-start 54 -f bestvideo+bestaudio https://www.youtube.com/playlist?list=PLq1noKggzASu92gX_ARJRk-W9aX_4OL7d
echo youtube-dl -f bestaudio $ITEMS --extract-audio --audio-format mp3 --output "%(autonumber)s%(title)s.%(ext)s" \'$1\'
youtube-dl -f bestaudio $ITEMS --extract-audio --audio-format mp3 --output '%(autonumber)s%(title)s.%(ext)s' $1
