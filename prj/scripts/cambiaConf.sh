#PID=$(ps ax | grep -i xinit | grep with-gdm | awk '{print $1;}' | head -1)
#while [ "$PID" != "" ];
#do
#   echo Matando xinit $PID
#   kill -TERM $PID
#   PID=$(ps ax | grep -i xinit | grep with-gdm | awk '{print $1;}' | head -1) 
#done
PID=$(ps ax | grep X | grep -v grep | awk '{print $1;}' | head -1)
while [ "$PID" != "" ];
do
   echo Matando X $PID
   kill -TERM $PID
   PID=$(ps ax | grep X | grep -v grep | awk '{print $1;}' | head -1)
done

OPCION=1
for XORG in $(ls /etc/X11/xorg.conf.*);
do 
   echo $OPCION')' $XORG
   OPCION=$(expr $OPCION + 1)
done   
read OPCION
#rm /etc/X11/xorg.conf &> /dev/null
Xorg -configure
CONT=1
for XORG in $(ls /etc/X11/xorg.conf.*);
do
   if [ $CONT -eq $OPCION ];
   then
      cp $XORG /etc/X11/xorg.conf
   fi
   CONT=$(expr $CONT + 1)
done
   
