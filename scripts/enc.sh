if [ "$1" = "" ];
then
   echo uso $0 peli.avi xvid-bitrate mp3lame-bitrate
   exit -1
fi   
if [ "$3" = "" ];
then
   VMP3=64
else
   VMP3=$3
fi
if [ "$2" = "" ];
then
   BXVID=600
else
   BXVID=$2
fi
if [ "$4" = "" ];
then
   VOL=1
else
   VOL=$4
fi
#"K:\Archivos de programa\MPlayer-1.0rc2\mencoder.exe" "%1" -o "%1.xvid.avi" -oac mp3lame -lameopts cbr:br=%VMP3% -ovc xvid -xvidencopts bitrate=%BXVID% %4 %5 %6 %7 %8 %9
eecho mencoder "$1" -o "$(basename $1).xvid.avi"  -oac mp3lame -lameopts cbr:br=$VMP3:vol=$VOL -ovc xvid -xvidencopts bitrate=$BXVID $5 $6 $7 $8 $9

