INTERFAZ=ath0
for I in ra0 ath0 wlan0 wpi0;
do 
   INTERFAZ=$(ifconfig $I &2>&1)
   if [ "$INTERFAZ" != "" ];
   then
      INTERFAZ=$I
      break;
   fi
done
echo $INTERFAZ
FICHERO_SALIDA=/tmp/redes.txt
USUARIO=$(whoami)
if [ "$3" = "" ];
then
   FICHERO_CLAVES=wep
else
   FICHERO_CLAVES=$3
fi      
if [ "$2" = "" ];
then
   LIMITE=3
else
   LIMITE=$2
fi      
echo usuario: $USUARIO
if [ "$USUARIO" != "root" ];
then
   echo Debes ejecutar este script como root
   exit 1
   sudo bash -c $0
fi
if [ -f $FICHERO_SALIDA ];
then 
   rm $FICHERO_SALIDA
fi   
echo $FICHERO_CLAVES
WLANS=$(cat /m/Mios/AIRELAB/wep/$FICHERO_CLAVES.txt | grep " ##" | gawk '{print $1;}')
NUM_REDES=$( echo $WLANS | wc -w)
echo He encontrado $NUM_REDES Redes:
habla He encontrado $NUM_REDES Redes
echo $WLANS
j=1
for RED in $WLANS;
do
   echo Probando red $j/$NUM_REDES: $RED
   nomonitor $INTERFAZ
   i=0
   while [ $i -lt $LIMITE ];
   do
      echo wlan $RED $FICHERO_CLAVES $INTERFAZ
      wlan $RED $FICHERO_CLAVES $INTERFAZ
      if [ $? -eq 0 ];
      then
         i=1000
      fi
      i=$(expr $i + 1)
      echo $i
   done
   if [ $i -gt 999 ];
   then
      if [ "$1" = "-f" ];
      then
         habla esta disponible $RED
         echo esta disponible $RED >> $FICHERO_SALIDA
      else
         exit 0
      fi
   fi
   j=$(expr $j + 1)
done   
if [ "$USUARIO" != "root" ];
then
   exit
fi
if [ "$1" = "-f" ];
then
   echo redes encontradas
   cat $FICHERO_SALIDA
fi   

