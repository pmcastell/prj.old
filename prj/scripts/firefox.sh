PID=$(ps ax | grep -i /home/usuario/programas/firefox/firefox | grep -v grep | awk '{print $1;}')
if [ "$PID" = "" ];
then
   while [ "$PID" = "" ];
   do
      if [ -f /usr/bin/firefox-3.5 ];
      then      
	/usr/bin/firefox-3.5 &
      fi
      sleep 1
      PID=$(ps ax | grep -i firefox-3.5 | grep -v grep | awk '{ print $1;}' | head -1)
   done     
   PID=$(ps ax | grep -i firefox-3.5 | grep -v grep | awk '{ print $1;}' | head -1)
   I=1

   while [ "$PID" != "" ];
   do
      kill -9 $PID
      if [ $I -gt 1 ];
      then
         sleep 1;
      fi
      I=$( expr $i + 1 )
      PID=$(ps ax | grep -i firefox-3.5 | grep -v grep | awk '{ print $1;}' | head -1)
   done 
fi     
/home/usuario/programas/firefox/firefox $1 $2 $3 $4 $5 $6 $7 $8 $9 &
