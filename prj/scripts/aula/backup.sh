#!/bin/bash
DIR_DEST=/BACKUPS
DEST=$DIR_DEST/students.$(date +'%Y-%m-%d').tar.bz2
ORIGEN=/net/server-sync/home/students
echo Origen:  $ORIGEN
echo Destino: $DEST


#nice -19 7za u -w$DIR_DEST/ -mhe  -r -ms=off -mx=9 -u- -uq0!$DEST $ULT $ORIGEN

nice -19 tar -cvjf $ULT $ORIGEN

SIZE=$(du -sk $ORIGEN | cut -f 1)
tar cvf - $ORIGEN | pv -p -s ${SIZE}k | bzip2 -c > $DEST

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



#if [ "$ULT" = "$DIR_DEST/" -o "$1" = "-n" ];
#then
#   echo ¿Crear uno nuevo? \(S/N\)
#   read RESP
#   if [ "$RESP" = "S" ];
#   then
#      echo nice -19 7za a -w$DIR_DEST/ -mhe  -r -ms=off -mx=9 $DEST $ORIGEN
#      nice -19 7za a -w$DIR_DEST/ -mhe  -r -ms=off -mx=9 $DEST $ORIGEN
#   else
#      echo ok! No hacemos nada
#   fi
#   exit 1
#fi
#if [ ! -f $ULT ];
#then
#   echo Error no encuentro $ULT
#   exit 2
#fi



#SIZE=$(du -sk folder-with-big-files | cut -f 1)
#tar cvf - folder-with-big-files | pv -p -s ${SIZE}k | bzip2 -c > big-files.tar.bz2
#tar cvf - /
