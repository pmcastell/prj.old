#!/bin/bash
#sudo find /net/server-sync/home/students/ | grep -i Desktop/INFORMATICA | awk -F'/' '{print $6;}' | uniq
CWD=$(pwd);
LISTA_FICH=$(tempfile);
ALUMNOS=$(tempfile);
DIR_DEST="/net/server-sync/home/teachers/franav/Desktop/TRABAJOS_ALUMNOS2";
ORIGEN=/net/server-sync/home/students;
cd $ORIGEN
sudo find . | grep -iE "Desktop/INFORMATICA$"  >> $ALUMNOS

while read ALU; do
   if [ "$ALU" != "" ]; then
      #sudo find $ALU | grep -v '\.moving_profiles' | grep -iE "(${EXTENSIONES})" >> $LISTA_FICH
      #while read f ; do sudo cp -upv --parents "$f" "$COPIA_TRABAJOS/"; done < $LISTA_FICH 
      sudo find $ALU | awk -F '/' '{ for(i=2;i<NF;i++) { printf $i"/"; }; print $i;}'>> $LISTA_FICH
   fi
done  < $ALUMNOS
#sudo chown -R franav:teachers $COPIA_TRABAJOS
mkdir -p $DIR_DEST &>/dev/null
ULT=$(ls $DIR_DEST/ | grep trabajos | sort | tail -1); DEST="$DIR_DEST/trabajos-$(hostname)-$(date +"%Y-%m-%d--%H-%M").7z"

if [ "$ULT" = "" ]; then sudo nice -19 7za a -r -w$DIR_DEST/ -ms=on -mmt=off -mx=9 -t7z -m0=lzma2 -mfb=64 -md=32m $DEST @$LISTA_FICH ;
                    else sudo nice -19 7za u -r -w$DIR_DEST/ -ms=on -mmt=off -mx=9 -t7z -m0=lzma2 -mfb=64 -md=32m -u- -uq0!$DEST $DIR_DEST/$ULT @$LISTA_FICH;
                         echo detectado ant; 
                    fi   
                    
sudo chown franav:teachers $DEST
sudo rm $LISTA_FICH
sudo rm $ALUMNOS
cd $CWD
exit 0
#sudo rm -rf $COPIA_TRABAJOS






#CONTRASENIA=$(echo -n WW01V2FtRldPV2hhUnpGd1ltYzlQUW89Cg== | base64 -d | base64 -d | base64 -d)
###7za a -w $DIR_DEST -r $DIR_DEST/trabajos$(date +"%Y-%m-%d").7z $COPIA_TRABAJOS/
#nice -19 7za a -w$DIR_DEST/ -mhe -p$CONTRASENIA -r -ms=off -mx=9 $DEST @LISTA_FICH (por desgracia esto no funciona)
#nice -19 7za u -w$DIR_DEST/ -mhe -p$CONTRASENIA/tmp/trabajos/net/server-sync/home/students/edgbaa/Desktop/brasero/ejercicio -r -ms=off -mx=9 -u- -uq0!$DEST $ULT $COPIA_TRABAJOS/
#EXTENSIONES="\.doc.$|\.dot.$|\.xl..$|\.pp..$|\.po..$|\.pp..$|\.sl..$|\.thmx|\.od.$|\.svg$|\.php$"; 



   
