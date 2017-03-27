#/usr/bin/ecryptfs-mount-private < /dev/null &>/dev/null
#cd /home/franc
if [ "$1" = "" ];
then
   echo uso: $0 '<nombre-maquina-virt>'
   exit 1
fi
MAQUINA=$1
MAQUINA_DIR=/home/franc
CHA=$(cat /home/franc/cha.txt)
for((i=1;i<=10;i++));
do 
   CHA=$(echo -n $CHA | base64 -d)
done
if [ "$2" = "stop" ];
then
   /usr/bin/vmrun -vp $CHA -T ws suspend /discoNuevo/franc/vmware/$MAQUINA/$MAQUINA.vmx
else 
   /usr/bin/vmrun -vp $CHA -T ws start /discoNuevo/franc/vmware/$MAQUINA/$MAQUINA.vmx nogui
fi
sleep 5
ps aux | grep -i vmx
