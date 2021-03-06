#!/bin/bash
CAMBIAR_VOLUMEN=1
VOZ=voice_JuntaDeAndalucia_es_pa_diphone
while [ "$1" = "-n" -o "$1" = "-v" -o "$1" = "-l" ]; do
   case $1 in
   "-n")
      CAMBIAR_VOLUMEN=0
      shift;;
   "-v")
      VOZ=$2
      shift;shift;;
   "-l")
      PAUSAR_ENTRE_LINEAS=1
      shift;;
   esac
done
if [ $CAMBIAR_VOLUMEN -eq 1 ]; then
   #Ponemos el volumen maestro al 80%
   amixer -c 0 sset Master,0 80% &> /dev/null
   #echo hola &> /dev/null
fi   
pronuncia() {
   TEXTO=$(echo $1 | iconv -f utf-8 -t iso-8859-1)
   echo \($VOZ\) '(SayText "'$TEXTO'")' | festival --pipe &> /dev/null  
}   

if [ -f "$1" ]; then
   #echo \($VOZ\) '(tts "'$1'" nil)' | festival --pipe &> /dev/null
   while read LINEA; do           
      pronuncia "$LINEA"
      if [ "$PAUSAR_ENTRE_LINEAS" = "1" ]; then
         read -s -n 1 RESP < /dev/tty
         while [ "$RESP" = "R" -o "$RESP" = "r" ]; do
            pronuncia "$LINEA"
            read -s -n 1 RESP < /dev/tty
         done
         if [ "$RESP" = "N" ]; then
            break
         fi
      fi         
   done < "$1"
else    
   pronuncia "$*"
fi         
if [ $CAMBIAR_VOLUMEN -eq 1 ]; then
   amixer -c 0 sset Master,0 100% &> /dev/null
fi
 
