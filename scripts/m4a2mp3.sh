#!/bin/bash
# Convertidor de m4a a mp3 por Skatox (www.skatox.com)
if [ "$1" = "" ]
then
   echo 'Error: Sintaxis incorrecta'
   echo 'Sintaxis: m4a2mp3 [Archivo_original.m4a] [Nombre_del_archivoconvertido]'
   exit 1 
fi 
if [ "$2" = "" ]
then
   echo ''
   echo 'No se paso por parametro el nombre del archivo mp3, se tomara Convertido.mp3 por defecto'
   $2 = Convertido
fi
mplayer -ao pcm "$1"
lame -h -b 192 "audiodump.wav" "$2.mp3"
rm audiodump.wav
