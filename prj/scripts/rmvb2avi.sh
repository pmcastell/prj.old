uso() {
   echo uso: $0 '<fich-rmbv> [<vide-bit-rate> [<audio-bit-rate]]'
   exit -1
}
if [ "$1" = "" ];
then
   uso
fi      
if [ "$2" = "" ];
then
   VRATE=1200
else 
   VRATE=$2
fi
if [ "$3" = "" ];
then
   ARATE=128
else
   ARATE=$3
fi            
mencoder $1 -oac mp3lame -lameopts preset=128 -ovc lavc -lavcopts vcodec=mpeg4:vbitrate=1200 -ofps 25 -of avi -o $1.xvid.avi -ffourcc XVID


