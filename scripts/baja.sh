#!/bin/bash

CARPETA_ORIGEN=/home/usuario/multi-dvd
cd $CARPETA_ORIGEN
#FICHERO=$(wget   http://devbuilds.kaspersky-labs.com/devbuilds/AVPTool/ -O - | grep -oiE 'setup.*exe' | head -1)
#FICHERO="http://devbuilds.kaspersky-labs.com/devbuilds/AVPTool/$FICHERO"
###buclef wget -c --timeout=10 $FICHERO -O kvrt.exe
URL="http://devbuilds.kaspersky-labs.com/devbuilds/KVRT/latest/full"
FICHERO=""
while [ "$FICHERO" = "" ];
do
   #FICHERO=$(wget -q --timeout=10 http://devbuilds.kaspersky-labs.com/devbuilds/AVPTool/avptool11/ -O - | grep exe | tail -1 | awk -F'>' '{print $2;}' | awk -F'<' '{print $1;}')
   FICHERO=$(wget -q --timeout=10   ${URL}/ -O - | grep exe | tail -1 | awk -F'>' '{print $2;}' | awk -F'<' '{print $1;}')
   sleep 1
done   
descarga "${URL}/${FICHERO}" 
#-O kvrt.exe
###descarga http://dl.antivir.de/down/vdf/ivdf_fusebundle_nt_en.zip
###descarga http://dl1.avgate.net/down/windows/hbedv.key
./mcafee.sh
#buclef wget -c --timeout=10 $( wget -q ftp://dnl-eu10.kaspersky-labs.com/devbuilds/AVPTool/ -O -  | tail -4 | head -1 | awk '{ print $7;}' | awk -F\" '{print $2;}' ) -O kvrt.exe

#echo debes descargar manualmente kvrt.exe de http://www.kaspersky.com/sp/anti-virus_trial/
buclef wget -c --timeout=10 http://www.drivesnapshot.de/download/snapshot.exe -O snapshot.exe
###if [ "$1" != "-w" ];
###then
###   echo Continuo\(S/N\)?
###   read RESP
###   if [ "$RESP" = "n" -o "$RESP" = "N" ];
###   then
###      exit -1
###   fi
###fi   
#if [ -d /tmp/AV7PE ];
#then
#   rm -rf /tmp/AV7PE
#fi   
#mkdir /tmp/AV7PE
#cp -f ./winXpLinux/PROGRAMS/av7pe/AV7PE.7z /tmp/AV7PE/
CWD=$(pwd)
#cd /tmp/AV7PE
#wine AV7PE.exe x -y
#7za x -y AV7PE.7z
unzip -o $CWD/ivdf_fusebundle_nt_en.zip -d ./winXpLinux/PROGRAMS/Antivir/
#rm -f AV7PE.exe
#rm -f AV7PE.7z
#wine $CWD/winXpLinux-cd/PROGRAMS/av7pe/7za.exe  a -sfx -r -mx=9 AV7PE.exe AV7PE
mv -f hbedv.key ./winXpLinux/PROGRAMS/Antivir/
#7za a -r -mx=9 AV7PE.7z AV7PE

#wine $CWD/winXpLinux-cd/PROGRAMS/av7pe/7za.exe  u -sfx -r -ms=off -mx=9 -u- -uq0!AV7PE.exe.tmp AV7PE.exe AV7PE
#rm -f AV7PE.exe
#mv -f AV7PE.exe.tmp AV7PE.exe
#chmod +w AV7PE.7z

#cp -f AV7PE.7z $CWD/winXpLinux/PROGRAMS/av7pe/
#cp -f AV7PE.7z /m/PROGRAMS/av7pe/
#cp -f AV7PE.7z $CWD/winXpLinux-cd/PROGRAMS/av7pe/

#cp -rv ./winXpLinux/PROGRAMS/Antivir/* /m/PROGRAMS/Antivir/
#cp -rv ./winXpLinux/PROGRAMS/Antivir/* $CWD/winXpLinux-cd/PROGRAMS/Antivir/
#cd $CWD

###cp -f kvrt.exe ./winXpLinux/PROGRAMS/kvrt/
#cp -f kvrt.exe ./winXpLinux-cd/PROGRAMS/kvrt/
cp -f kvrt.exe /m/PROGRAMS/kvrt/
DIR_ACTUAL=$(pwd)
cd $CARPETA_ORIGEN/winXpLinux/PROGRAMS/mcafee/
tar -xvf $(ls $CARPETA_ORIGEN/avvdat*.tar)  
cd $DIR_ACTUAL
#unzip -o $(ls dat*.zip)  -d ./winXpLinux-cd/PROGRAMS/mcafee/
#unzip -o $(ls dat*.zip)  -d /m/PROGRAMS/mcafee/

cp -f snapshot.exe ./winXpLinux/PROGRAMS/snapshot/
#cp -f snapshot.exe ./winXpLinux-cd/PROGRAMS/snapshot/
#cp -f snapshot.exe /m/PROGRAMS/snapshot/


##rm -f AV7PE.7z
rm ./kvrt.exe ./dat*.zip ./ivdf_fusebundle_nt_en.zip ./snapshot.exe ./avv*.tar &> /dev/null
exit 0
