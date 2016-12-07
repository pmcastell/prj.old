#!/bin/bash

uso() {
   echo uso: $0 '<fichero-usuarios.txt>' '<carpeta-examen>' '<ficheros-examen>'
   exit
}

USUARIOS="$1"
#CARPETA_EXAMEN=EXAMEN_CALC
CARPETA_EXAMEN="$2"
FICHEROS_EXAMEN="$3"
while read USUARIO; do
   if [ "$USUARIO" = "" ]; then continue; fi
   DEST=/net/server-sync/home/students/$USUARIO/Desktop/$CARPETA_EXAMEN
   sudo mkdir -p $DEST
   for FICH in "$FICHEROS_EXAMEN"; do
      sudo cp -v $FICH $DEST/
      ls -l $DEST/
   done
   sudo chown -R $USUARIO $DEST
   echo USUARIO: ACTUAL $USUARIO
done < $USUARIOS

