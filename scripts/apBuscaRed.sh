OS=$(uname)
INTERFAZ=$(interfaces | grep -i eth)
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
   LIMITE=1
else
   LIMITE=$2
fi      

echo usuario: $USUARIO
if [ "$USUARIO" != "root" ];
then
   echo Debes ejecutar este script como root

   echo sudo bash -c $0 $1 $2 $3 $4 $5 $6 $7 $8 $9
   sudo $0 $1 $2 $3 $4 $5 $6 $7 $8 $9

fi

if [ -f $FICHERO_SALIDA ];
then 
   rm $FICHERO_SALIDA
fi   

WLANS=$(cat /m/Mios/AIRELAB/wep/$FICHERO_CLAVES.txt | grep "##" | gawk '{print $1;}')
echo fichero de claves: $FICHERO_CLAVES $WLANS
NUM_REDES=$( echo $WLANS | wc -w)
echo He encontrado $NUM_REDES Redes:
habla He encontrado $NUM_REDES Redes
echo $WLANS
j=1
for RED in $WLANS;
do
   echo Probando red $j/$NUM_REDES: $RED
   #nomonitor $INTERFAZ
   i=0
   while [ $i -lt $LIMITE ];
   do
      #echo wlan $RED $FICHERO_CLAVES $INTERFAZ
      #wlan $RED $FICHERO_CLAVES $INTERFAZ
      echo sudo ap $RED -n
      sudo ap $RED -n
      
      if [ "$OS" = "FreeBSD" ];
      then
         echo ping -t 6 80.58.0.33
         ping -t 6 80.58.0.33
      else
         echo ping -c 2 -w 0.5 80.58.0.33
         ping -c 2 -w 0.5 80.58.0.33
      fi
      if [ $? -eq 0 ];
      then
         i=1000
      fi
      i=$(expr $i + 1)
      echo Red $j: $RED.
   done
   if [ $i -gt 999 ];
   then
      if [ "$1" = "-f" ];
      then
         habla esta disponible $RED
         echo esta disponible $RED >> $FICHERO_SALIDA
      else
         echo s1 vale $1
         exit 0
      fi
   fi
   j=$(expr $j + 1)
   apActual
   echo '====================================================================='
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

