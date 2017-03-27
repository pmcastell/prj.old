uso() {
   echo $0 '<fichero-audio> [<bit-rate(128kbsp)> [<vol> [<fich-salida>]]]'
   exit -1
}   
if [ "$1" = "" ];
then
   uso
fi
if [ "$2" = "" ];
then
   BITRATE=128
else
   BITRATE=$2
fi
if [ "$3" = "" ];
then
   VOL=1
else
   VOL=$3
fi
if [ "$4" = "" ];
then
   #OUT=$(echo “$1”|sed -e ’s/wma$/mp3/’)
   OUT=$1.mp3
else
   OUT=$4
fi                     
#mencoder  $1 -if rawaudio -of rawaudio -ovc frameno -oac mp3lame -lameopts cbr:br=$BITRATE:vol=$VOL -o $OUT
#!/bin/bash
#
# Dump wma to mp3

if [ -d /tmp/$1.wav ];
then
   rm /tmp/$1.wav
fi   
eecho mkfifo /tmp/$1.wav
eecho mplayer -vo null -vc dummy -af resample=44100 -ao pcm:waveheader:file=/tmp/$1.wav $1   &
eecho lame --scale $VOL -h -b $BITRATE /tmp/$1.wav $OUT
eecho rm -f /tmp/$1.wav

