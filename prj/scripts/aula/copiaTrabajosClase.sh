#!/bin/bash

uso() { echo uso: $0 '<fichero-lista-usuarios-clase> [<extensiones>]'; exit 1; }
cd $(dirname $0)


if [ "$1" = "" ] && [ ! -d usuarios ]; then uso; fi
CLASE=$(tempfile)
if [ "$1" = "" ]; then
   if [ "$(hostname)" = "aulasrv1" ]; then CURSOS="1ESO-A-B-E.txt 1ESO-C-D.txt 2BACH-BACA-BAHA.txt 4ESO-A-B-PDC1.txt 4ESO-A-B.txt";
   elif [ "$(hostname)" = "srv2aula" ]; then CURSOS="2ESO-F.txt"; fi
   for f in $CURSOS; do cat usuarios/$f >> $CLASE; done
else  cat $1 > $CLASE; fi   
if [ "$2" = "" ]; then 
   EXTENSIONES="\.doc$|\.docx$\.dot$|\.dotx$\.xls$|\.xlsx$|\.ppt$|\.pptx$|\.pdf$\|\.thmx$|\.xlt$|\.xltx$|\.odp$|\.odt$|\.svg$|\.php$"
   EXTENSIONES="${EXTENSIONES}|\.htm.$|\.png$|\.swf"
else EXTENSIONES="$2"; fi
LISTA_FICH=$(tempfile); COPIA_TRABAJOS="/bakNet/CopiasSeg/trabajos"; 
DIR_DEST="/net/server-sync/home/teachers/franav/Desktop/TRABAJOS_ALUMNOS"; #sudo rm -rf $COPIA_TRABAJOS
mkdir -p $COPIA_TRABAJOS&>/dev/null
while read ALU; do
   if [ "$ALU" != "" ]; then
      sudo find /net/server-sync/home/students/$ALU | grep -v '\.moving_profiles' | grep -iE "(${EXTENSIONES})" > $LISTA_FICH
      while read f ; do sudo cp -upv --parents "$f" "$COPIA_TRABAJOS/"; done < $LISTA_FICH 
   fi
done  < $CLASE
#sudo chown -R franav:teachers $COPIA_TRABAJOS
mkdir -p $DIR_DEST &>/dev/null
ULT=$(ls $DIR_DEST/ | grep trabajos | sort | tail -1); DEST="$DIR_DEST/trabajos-$(hostname)-$(date +"%Y-%m-%d--%H-%M").7z"

if [ "$ULT" = "" ]; then sudo nice -19 7za a -w$DIR_DEST/ -mhe -r -mx=9 $DEST $COPIA_TRABAJOS/; else    
                         sudo nice -19 7za u -w$DIR_DEST/ -mhe  -r -mx=9 -u- -uq0!$DEST $DIR_DEST/$ULT $COPIA_TRABAJOS/; echo detectado ant; fi   
sudo chown franav:teachers $DEST
sudo rm $LISTA_FICH
sudo rm $CLASE

exit 0
#sudo rm -rf $COPIA_TRABAJOS






#CONTRASENIA=$(echo -n WW01V2FtRldPV2hhUnpGd1ltYzlQUW89Cg== | base64 -d | base64 -d | base64 -d)
###7za a -w $DIR_DEST -r $DIR_DEST/trabajos$(date +"%Y-%m-%d").7z $COPIA_TRABAJOS/
#nice -19 7za a -w$DIR_DEST/ -mhe -p$CONTRASENIA -r -ms=off -mx=9 $DEST @LISTA_FICH (por desgracia esto no funciona)
#nice -19 7za u -w$DIR_DEST/ -mhe -p$CONTRASENIA/tmp/trabajos/net/server-sync/home/students/edgbaa/Desktop/brasero/ejercicio -r -ms=off -mx=9 -u- -uq0!$DEST $ULT $COPIA_TRABAJOS/
#EXTENSIONES="\.doc.$|\.dot.$|\.xl..$|\.pp..$|\.po..$|\.pp..$|\.sl..$|\.thmx|\.od.$|\.svg$|\.php$"; 



   
