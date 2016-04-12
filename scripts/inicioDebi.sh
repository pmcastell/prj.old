if [ "$1" = "start" ];
then
   PID=$(ps aux | grep -i x11vnc | grep 5906 | grep -v grep)
   cont=0
   while [ "$PID" != "" -a $cont -lt 5];
   do
      kill -TERM $PID
      cont=$(expr $cont + 1)
      PID=$(ps aux | grep -i x11vnc | grep 5906 | grep -v grep)
   done
   while [ "$PID" != "" -a $cont -lt 5];
   do
      kill -9 $PID
      cont=$(expr $cont + 1)
      PID=$(ps aux | grep -i x11vnc | grep 5906 | grep -v grep)
   done
   /usr/bin/x11vnc -rfbport 5906 -shared -reopen -forever -loop -rfbauth /home/usuario/.x11vnc/passwd &
   fi
else
   /m/Mios/prj/scripts/mata.sh x11vnc
fi
#/usr/bin/gnome-terminal &

