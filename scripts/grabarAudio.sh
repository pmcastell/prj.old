uso() {
   echo uso $0 fich-salida.mp3
   exit 1
}
if [ "$1" = "" ];
then
   uso
fi   
if [ "$(echo $1 | grep mp3)" = "" ];
then
   SALIDA=$1.mp3
else
   SALIDA=$1
fi      
ffmpeg -f alsa -i pulse -ac 2 -ab 128K  $SALIDA
