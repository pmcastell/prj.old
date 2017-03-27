uso() {
   echo $0 fichero-salida-grabacion
   exit 1
}
if [ "$1" = "-h" ];
then
   uso
fi      
if [ "$1" = "" ];
then
   SALIDA=/tmp/sesion.avi
else
   SALIDA=$1
fi      
#ffmpeg -f alsa -ac 2 -i pulse -f x11grab -r 24 -s 1280x800 -i :0.0 -acodec pcm_s16le -vcodec libx264 -vpre lossless_ultrafast -threads 0 $SALIDA
#ffmpeg -f alsa -ac 2 -i pulse -f x11grab -r 24 -s $(xrandr | grep '*' | awk '{print $1;}') -i :0.0 -acodec pcm_s16le -vcodec libx264 -vpre lossless_ultrafast -threads 0 $SALIDA
#avconv -f alsa -ac 2 -i pulse -f x11grab -r 24 -s $(xrandr | grep '*' | awk '{print $1;}') -i :0.0+0,0 -acodec pcm_s16le -vcodec libx264 -threads 0  $SALIDA 
avconv -f x11grab -s $(xrandr | grep '*' | awk '{print $1;}') -r 30 -i :0.0+0,0  -ac 2  $SALIDA
