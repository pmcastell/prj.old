#!/bin/bash

for i in $(ls /discoNuevo/franc/vmware);
do
   if [ -d /discoNuevo/franc/vmware/$i ];
   then
      if [ "$(ps aux | grep -i $i)" != "" ];
      then
          echo vmw.sh $i stop
          vmw.sh $i stop
      fi
   fi
done       

