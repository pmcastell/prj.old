uso() {
   echo $0 '<fichero-de-video> [<long-horizontal>(640 por defecto) <vid-rate> <audio-rate>])'
   exit 1
}
if [ "$1" = "" ];
then 
   uso
fi 
if [ "$2" = "" ];
then
   LONG=640
else
   LONG=$2
fi
if [ "$3" = "" ];
then
   VIDBITRATE=1024
else
   VIDBITRATE=$3
fi
if [ "$4" = "copy" ];
then
   OAC=copy
elif [ "$4" = "" ];
then
   OAC="mp3lame -lameopts cbr:br=96"
else
   OAC="mp3lam3 -lameopts cbr:br=$4"   
fi         
ASP=$(infoVid $1 2>&1| grep -i aspect | awk '{ print $3;}' | grep -i 1\.7)
if [ "$ASP" = "" ];
then
   ASP="4:3"
else
   ASP="16:9"      
fi   
HOR=$(infoVid $1 2>&1| grep 'VO:'  | awk '{ print $3;}' | awk -F'x' '{ print $1;}')
VER=$(infoVid $1 2>&1| grep 'VO:'  | awk '{ print $3;}' | awk -F'x' '{ print $2;}')
eecho mencoder $1 -zoom -xy $LONG -o $1.xvid.avi -oac $OAC  -ovc xvid -xvidencopts bitrate=2048 -aspect $ASP -vf crop=$HOR:$VER:0:0,scale

