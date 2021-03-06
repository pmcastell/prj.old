#!/bin/sh

die() {
   echo "$@"
   exit 1
}

check1=$(which drmdecrypt)
[ "$check1" = "" ] && die "drmdecrypt is missing: https://github.com/IvoNet/drmdecrypt and follow instructions"   
check2=$(which HandBrakeCLI)
[ "$check2" = "" ] && die "HandBrakeCLI is missing: http://handbrake.fr/downloads2.php"
check3=$1
[ "$check3" = "" ] && die "Uso: $0 <fichero-srf-sin-extension> [<segundos-de-comienzo>]"
startat=$2
[ "$startat" = "" ] && startat=0
drmdecrypt "$1.srf" 
HandBrakeCLI -e x264 -q 20.0 -a 1,1 -E faac,copy:ac3 -B 160,160 -6 dpl2,auto -R Auto,Auto -D 0.0,0.0 \
           --audio-copy-mask aac,ac3,dtshd,dts,mp3 --audio-fallback ffac3 -f mp4 -Y 1920 -X 1080 -A --decomb --loose-anamorphic \
           --modulus 2 -m --x264-preset ultrafast --h264-profile high --h264-level 4.1 --start-at duration:$startat -i "$1.ts" -o "$1.mkv"
