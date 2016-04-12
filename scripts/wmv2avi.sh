uso() {
   $0 '<fichero-entrada>'
   exit 1
}

if [ "$1" = "" ];
then
   uso
fi   
if [ "$2" = "" ];
then
   VIDEOBR=600
else 
   VIDEOBR=$2
fi
if [ "$3" = "" ];
then
   AUDIOBR=128k
else
   AUDIOBR=$3
fi            
#ffmpeg -i $1 -vcodec mpeg4 -vtag xvid -sameq -acodec libmp3lame -f avi $1.xvid.avi
#ffmpeg -i $1 -vcodec mpeg4 -vtag XVID -b ${VIDEOBR}k -ab ${AUDIOBR}k -acodec libmp3lame -f avi $1.xvid.avi
eecho ffmpeg -i $1 -vcodec mpeg4 -vtag XVID -qscale 0 -acodec libmp3lame -ab $AUDIOBR -f avi $1.avi 
if [ $? != 0 ];
then
   echo si falla quejándose de unknown libmpelame o algo parecido: sudo apt-get install ffmpeg libavcodec-extra-52
fi
