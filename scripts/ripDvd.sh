#!/usr/local/bin/bash
uso() {
   echo $0 '<dir-dvd> <fichero-avi> [capitulo inicial [capitulo final]] '
   exit -1
}
   

if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi   
CARPETA_ORIGEN=$1
SALIDA=$2
echo Hora Comienzo: > tiempo.txt
date >> tiempo.txt
echo Hora Comienzo: 
date 
WORKDIR=./
cd $WORKDIR
NUMTITLES=$(mplayer -dvd-device $CARPETA_ORIGEN dvd:// -identify -endpos 0 -vo null -vc null -ao null -nosound 2> /dev/null | grep ID_DVD_TITLES= | cut -d "=" -f 2  )
eecho hablaf Total Titulos Encontrados $NUMTITLES
eecho nice -19 mencoder -aspect 16:9 -vf cropdetect,scale -zoom -xy 640 dvd:// -dvd-device $CARPETA_ORIGEN -oac copy -ovc xvid -xvidencopts bitrate=600 -o prueba.avi -ss 00:05:00 -endpos 60 > /tmp/prueba.txt
CROP=$(cat /tmp/prueba.txt | grep -i crop | grep -i area | tail -n 1 | gawk -F= '{ print $2; }' |  gawk -F')' '{print $1;}')
if [ "$3" = "" ];
then  
   i=1
else
   i=$3
fi      
if [ "$4" = "" ];
then
   FIN=$NUMTITLES
else
   FIN=$4
fi      

while [ $i -le $NUMTITLES ];
do
   if [ $i -lt 10 ];
   then
      TITLE=0$i
   else
      TITLE=$i
   fi
   eecho nice -19 mencoder -aspect 16:9 -vf crop=$CROP,scale -zoom -xy 640 dvd://$TITLE -dvd-device $CARPETA_ORIGEN -o $SALIDA-$TITLE.avi -oac mp3lame -lameopts cbr:br=128:vol=1 -ovc xvid -xvidencopts bitrate=1300 -ni -alang en #-aid 0 #-alang en #-aid 0
   ESPANIOL=$(mplayer -dvd-device $CARPETA_ORIGEN dvd:// -identify -endpos 0 -vo null -vc null -ao null -nosound 2> /dev/null | grep -i aid | grep -i es)
   if [ "$ESPANIOL" != "" ];
   then
      eecho nice -19 mencoder -ovc frameno -of rawaudio -oac mp3lame -lameopts cbr:br=128:vol=1.5 -alang es  -o $SALIDA-$TITLE.esp.mp3  dvd://$TITLE -dvd-device $CARPETA_ORIGEN
      eecho nice -19 avimerge -i $SALIDA-$TITLE.avi -p $SALIDA-$TITLE.esp.mp3 -o $SALIDA-$TITLE.dual.avi
   fi
   i=$(expr $i + 1)
done
echo Hora Fin: >> tiempo.txt
date >> tiempo.txt
echo Hora Fin: 
date 
rm prueba.*

