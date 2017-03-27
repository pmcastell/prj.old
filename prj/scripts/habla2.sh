CAMBIAR_VOLUMEN=1
VOZ=voice_JuntaDeAndalucia_es_pa_diphone
while [ "$1" = "-n" -o "$1" = "-v" ];
do
   if [ "$1" = "-n" ];
   then
      CAMBIAR_VOLUMEN=0
      shift
   fi
   if [ "$1" = "-v" ];
   then
      VOZ=$2
      shift
      shift
   fi
done
if [ $CAMBIAR_VOLUMEN -eq 1 ];
then
   #Ponemos el volumen maestro al 80%
   amixer -c 0 sset Master,0 80% &> /dev/null
   #echo hola &> /dev/null
fi   
if [ -f $1 ];
then
   echo \($VOZ\) '(tts "'$1'" nil)' | festival --pipe &> /dev/null
else    
   echo \($VOZ\) '(SayText "'$*'")' | festival --pipe &> /dev/null
fi         
if [ $CAMBIAR_VOLUMEN -eq 1 ];
then
   #amixer -c 0 sset Master,0 100% &> /dev/null
   echo hola &> /dev/null
fi

