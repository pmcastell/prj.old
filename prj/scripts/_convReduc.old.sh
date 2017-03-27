if [ "$2" != "" ];
then
   DIR_SALIDA=$2
   NOMBRE_FICH=$(parteFichero $1)
   nice -n 19 mencoder $1 -ovc xvid -xvidencopts bitrate=1600 -oac mp3lame -lameopts cbr:br=96:vol=1.5  -vf scale -zoom -xy 720,404 -o $DIR_SALIDA/$(basename $1).xvid.avi -aid 0 
else
   nice -n 19 mencoder $1 -ovc xvid -xvidencopts bitrate=1600 -oac mp3lame -lameopts cbr:br=96:vol=1.5  -vf scale -zoom -xy 720,404 -o $1.xvid.avi -aid 0 
fi   

#-af pan=2:3:3:1.5:1.5:3:3:3:3:3:3:1.5:1.5
#nice -n 19 mencoder $1 -ovc frameno -of rawaudio -oac mp3lame -lameopts cbr:br=96:vol=1.5 -aid 1 -o life/$1.2.mp3
#nice -n 19 mkvextract tracks $1 4:life/$1.esp.srt 5:life/$1.eng.srt
#nice avimerge  -i life/$1.xvid.avi -o life/def/$1.dual.avi -p life/$1.2.mp3 
hablaf he terminao de convertir $1
hablaf he terminao de convertir $1
hablaf he terminao de convertir $1

