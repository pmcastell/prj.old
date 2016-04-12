if [ "$1" != "" ];
then
   INTERFAZ=$1
else
   INTERFAZ=$(netstat -rn | grep -iE '(default|0\.0\.0\.0)' | awk '{ print $6;}')
fi
ifconfig $INTERFAZ && netstat -rn
