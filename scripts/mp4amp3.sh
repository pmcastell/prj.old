if [ $1 = "" ];
then
   echo uso $0 '<fichero-a-convertir> [<ratio-de-conversion>]'
fi
if [ $2 = "" ];
then
   RATIO=192000
else
   RATIO=$2
fi         
ffmpeg -i $1 -f mp3 -ab $RATIO -vn $1.mp3
