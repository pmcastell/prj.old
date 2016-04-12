#
#
SO=$(uname)
EXCEPT=$*
#if [ "$(echo $* | grep -iE '\-[0-9]{1,3}')" != "" ] ;
#then
#   EXCEPT=9
#   INTERFACE=$(netstat -rn | grep -E '(default|^0\.0\.0\.0)' | grep -oE '[^ ]*$')
#elif [ "$1" = "" ];
#if [ "$(echo $* | grep -iE '\-[0-9]{1,3}')" != "" -o "$1" = "" ];
#then
#   #INTERFACE=$(route | grep default | awk '{print $8;}' | head -1)
#   #INTERFACE=$(netstat -rn | grep default | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}')
#   #INTERFACE=$(netstat -rn | grep default | grep -oE '.*[0-9]')
#fi
if [ "$(echo $* | grep -- -i)" != "" ];
then
   INTERFACE=$(echo $* | awk -F'-i' '{ print $2;}')
else
   INTERFACE=$(netstat -rn | grep -E 'default' | grep -oE '[^ ]*$')
   if [ "$INTERFACE" = "" ];
   then
   	INTERFACE=$(netstat -rn | grep -E '^0\.0\.0\.0' | grep -oE '[^ ]*$' | tail -1 )
   fi
fi  
if [ "$(echo $* | grep "\-p")" != "" ];
then
   PRINCIPIO=$(echo $* | awk -F'-p' '{print $2;}' | awk '{ print $1;}')
else
   PRINCIPIO=2
fi      
if [ "$(echo $* | grep "\-f")" != "" ];
then
   FIN=$(echo $* | awk -F'-f' '{print $2;}' | awk '{ print $1;}')
else
   FIN=255
fi      
echo $INTERFACE
if [ "$INTERFACE" = "" ];
then
   eecho habla -n No encuentro ruta por la que salir
fi
DIR_IP=$(ifconfig $INTERFACE | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}'| grep -v 255 | tail -1)
RED=$(echo $DIR_IP | awk -F\. '{print $1"."$2"."$3;}')
echo $RED
#echo DIR_IP: $DIR_IP red: $RED
printf "\n"
i=$PRINCIPIO
#for((i=2; i<255; i++));
CONECTADOS=0
while [ $i -lt $FIN ];
do
   if [ "$(echo $EXCEPT | grep  '\-'$i)" = "" ];
   then
      #printf "\r   "
      printf "\r$i"
      #echo $i
      #echo arping -I $INTERFACE -q -c 1 -w 0.1 $RED.$i #&> /dev/null
      if [ "$SO" = "FreeBSD" ];
      then
         sudo arping -i $INTERFACE -q -c 1 -w 3000000  $RED.$i &> /dev/null
      else
         #echo 'arping -I '$INTERFACE' -q -c -w 0.5 '$RED'.'$i' \&\> //dev//null'
         arping -I $INTERFACE -q -c 1 -w 0.3 $RED.$i &> /dev/null
      fi
      if [ $? -lt 1 ];
      then
         echo Esta conectado $RED.$i
         hablaf -n Esta conectado $RED.$i &> /dev/null
         date
         CONECTADOS=1
      fi
      #resto=$(expr $i % 21)
      #if [ $resto -eq 0 ];
      #then
      #   habla -n $i
      #fi
      #sleep 1
   fi
   i=$(expr $i + 1)
done
if [ $CONECTADOS -eq 0 ];
then
   habla -n No habia nadie conectado a la red $RED
fi   
