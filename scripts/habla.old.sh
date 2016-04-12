#echo $1 $2 $3 $4 $5 $6 $7 $8 $9 | festival --tts --language spanish
PARAMETROS=$*
CAMBIAR_VOLUMEN=1
if [ "$(echo \'$PARAMETROS\' | grep \\-n)" != "" ];
then 
   CAMBIAR_VOLUMEN=0
   PARAMETROS=$(echo $PARAMETROS | awk -F'-n' '{print $1$2;}')
   echo $PARAMETROS
fi

if [ "$(echo $PARAMETROS | grep -i "\-v")" != "" ];
then

   VOZ=$(echo $PARAMETROS | awk -F'-v' '{print $2;}' | awk '{print $1;}')
   if [ "$(echo $VOZ | grep -i mbrola )" != "" ];
   then
      VOZ=voice_$VOZ
   fi
   if [ "$(echo $VOZ |  grep -i voice_)" = "" ];
   then
      VOZ=voice_$VOZ'_diphone'
   fi
   PARAMETROS=$(echo $PARAMETROS | cut -d' ' -f 3-)
read pepe
else
   VOZ=voice_JuntaDeAndalucia_es_pa_diphone
fi
if [ $CAMBIAR_VOLUMEN -eq 1 ];
then
   #Ponemos el volumen maestro al 80%
   #amixer -c 0 sset Master,0 80% &> /dev/null
   echo hola &> /dev/null
fi   
LINEA=$PARAMETROS
if [ -f $1 ];
then
   echo \($VOZ\) '(tts "'$1'" nil)' | festival --pipe &> /dev/null
else    
   echo \($VOZ\) '(SayText "'$LINEA'")' | festival --pipe &> /dev/null
fi   
if [ $CAMBIAR_VOLUMEN -eq 1 ];
then
   #amixer -c 0 sset Master,0 100% &> /dev/null
   echo hola &> /dev/null
fi

