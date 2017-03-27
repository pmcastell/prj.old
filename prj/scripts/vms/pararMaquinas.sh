#!/bin/bash
/usr/local/bin/ubu.sh stop &> /dev/null
for i in $(/bin/ls /discoNuevo/franc/vmware);
do
   if [ -d /discoNuevo/franc/vmware/$i ];
   then
      if [ "$(/bin/ps aux | /bin/grep -i $i | /bin/grep -v /bin/grep | /bin/grep -i vmx)" != "" ];
      then
#          /bin/echo vmw.sh $i stop
          /usr/local/bin/vmw.sh $i stop &> /dev/null
#          /bin/echo Parando: $i
      fi
   fi
done       
/bin/echo Apango sistema .... $(date) >> /discoNuevo/franc/logSys.txt

