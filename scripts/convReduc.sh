uso() {
   echo uso: $0 '<fichero-video> [<dir-salida> [<video-rate>] [<audio-bit-rate>]]]'
   exit 1
}   
if [ "$1" = "" ];
then 
   uso
fi   
if [ "$2" != "" ];
then
   DIR_SALIDA=$2
else
   DIR_SALIDA='.'   
fi   
if [ "$3" != "" ];
then
   VBITR=$3
else
   VBITR=1024
fi
if [ "$4" != "" ];
then
   ABITR=$4
else
   ABITR=96
fi       
if [ "$5" = "" ];
then
   ESCALA=640,360
else
   ESCALA=$5
fi           
NOMBRE_FICH=$(basename $1)
STREAMS_AUDIO=$(mplayer $NOMBRE_FICH -endpos 0  2>&1 | grep -E -o '\-aid [0-9]' | awk '{ print $2;}')
echo nice -n 19 mencoder $1 -ovc xvid -xvidencopts bitrate=$VBITR -oac mp3lame -lameopts cbr:br=$ABITR:vol=1.5  -vf scale -zoom -xy $ESCALA -o $DIR_SALIDA/$NOMBRE_FICH.xvid.avi -aid $(echo $STREAMS_AUDIO | head -1)
nice -n 19 mencoder $1 -sid 10 -ovc xvid -xvidencopts bitrate=$VBITR -oac mp3lame -lameopts cbr:br=$ABITR:vol=1.5  -vf scale -zoom -xy $ESCALA -o $DIR_SALIDA/$NOMBRE_FICH.xvid.avi -aid $(echo $STREAMS_AUDIO | head -1)
#-af pan=2:3:3:1.5:1.5:3:3:3:3:3:3:1.5:1.5
STREAMS_AUDIO=$(mplayer $NOMBRE_FICH -endpos 0  2>&1 | grep aviheader | grep -i aid | rev | awk '{ print $1;}')
if [ $(echo $STREAMS_AUDIO | wc -l) -gt 1 ];
then
   nice -n 19 mencoder $1 -ovc frameno -of rawaudio -oac mp3lame -lameopts cbr:br=$ABITR:vol=1.5 -aid $(echo $STREAMS_AUDIO | tail -1) -o $DIR_SALIDA/$1.2.mp3
   nice avimerge  -i $DIR_SALIDA/$1.xvid.avi -o $DIR_SALIDA/$1.dual.avi -p $DIR_SALIDA/$1.2.mp3 
fi
#nice -n 19 mkvextract tracks $1 4:life/$1.esp.srt 5:life/$1.eng.srt

hablaf he terminao de convertir $1
hablaf he terminao de convertir $1
hablaf he terminao de convertir $1

