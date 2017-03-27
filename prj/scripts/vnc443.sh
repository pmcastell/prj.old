while [ 1 ];
do
   ps ax | grep -i x11vnc
   if [ $? -ge 1 ];
   then
      x11vnc &
   fi
   x11vnc -connect formacion.dyndns.org:443 &
   sleep 300
done
   
