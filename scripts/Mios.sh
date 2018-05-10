#!/bin/bash
DIR_DEST=/l/Mios-7z
ULT=$(ls -p $DIR_DEST/ | grep -v / | sort | tail -1)
ULT=$DIR_DEST/$ULT
DEST=$(ls $DIR_DEST/ | sort | awk -F\. '{print $2;}' | tail -1)
DEST=$(expr $DEST + 1)
CONTRASENIA=$(/scripts/getMyPass.sh encfs)
if [ $DEST -lt 10 ]; then
   DEST=000$DEST
elif [ $DEST -lt 100 ]; then
   DEST=00$DEST
elif [ $DEST -lt 1000 ]; then
   DEST=0$DEST
fi
      
DEST=$DIR_DEST/Mios.$DEST.7z; ORIGEN=/m/Mios
echo Origen:  $ORIGEN; echo Destino: $DEST; echo Último: $ULT
#CMD_OPTS=" -w$DIR_DEST/ -mhe -p$CONTRASENIA -r -ms=off -mx=9 "
CMD_OPTS=" -w$DIR_DEST/ -mhe -p$CONTRASENIA -ms=off -mx=9 -mmt=8 "
if [ "$ULT" = "$DIR_DEST/" -o "$1" = "-n" ]; then
   echo ¿Crear uno nuevo? \(S/N\)
   read RESP
   if [ "$RESP" = "S" ]; then
      nice -19 7za a $CMD_OPTS $DEST $ORIGEN
   else
      echo ok! No hacemos nada
   fi
   exit 1
fi
if [ ! -f $ULT ]; then
   echo Error no encuentro $ULT
   exit 2
fi
nice -19 7za u $CMD_OPTS -u- -uq0!$DEST $ULT $ORIGEN


#del %FICH_DEST%
#move %FICH_DEST%.tmp %FICH_DEST%

   
      


#SET UNI_DEST=k:
#SET FICH_DEST=%UNI_DEST%\Mios.7z
#SET ORIGEN=k:\Mios\*
#time /t 
#if exist %FICH_DEST%.tmp del %FICH_DEST%.tmp
#if exist %FICH_DEST% (
#   start /low /b /wait 7za u -w%UNI_DEST%\ -mhe -p$CONTRASENIA -ssw -r -ms=off -mx=9 -u- -uq0!%FICH_DEST%.tmp %FICH_DEST% %ORIGEN%
#   del %FICH_DEST%
#   move %FICH_DEST%.tmp %FICH_DEST%
#) else (
#   start /low /b /wait 7za a -w%UNI_DEST%\ -mhe -p$CONTRASENIA -ssw -r -ms=off -mx=9 %FICH_DEST% %ORIGEN%
#)
#time /t


