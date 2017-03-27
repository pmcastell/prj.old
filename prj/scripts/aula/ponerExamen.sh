#!/bin/bash

uso() {
   echo uso: $0 '<fichero-usuarios.txt>' '<carpeta-examen>' '<ficheros-examen>'
   exit
}
if [ $# -lt 3 ]; then uso; fi
USUARIOS="$1"
CARPETA_EXAMEN="$2"
FICHEROS_EXAMEN="$3"
DIR_BASE="/net/server-sync/home/students"
while read USUARIO; do
   if [ "$USUARIO" = "" ]; then continue; fi
   echo "-------------------------- USUARIO: $USUARIO -------------------------"
   DEST=$DIR_BASE/$USUARIO/Desktop/$CARPETA_EXAMEN
   [ ! -d "$DIR_BASE/$USUARIO/Desktop" ] && echo "Error: no existe $DIR_BASE/$USUARIO/Desktop"
   sudo mkdir -p $DEST
   for FICH in "$FICHEROS_EXAMEN"; do
      echo copia
      sudo cp -v $FICH $DEST/
      #echo ls -l $DEST/
   done
   sudo chown -R $USUARIO $DEST
   #echo USUARIO: ACTUAL $USUARIO
   echo "-------------------------- USUARIO: $USUARIO -------------------------"
done < $USUARIOS

