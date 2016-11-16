#!/bin/bash
uso() {
   echo $0 '<url-youtube>'
   echo Descarga el audio del vídeo en formato mp3
   exit 1
}
if [ $# -lt 1 ]; then uso; fi
   
youtube-dl -f bestaudio --extract-audio --audio-format mp3 $1
