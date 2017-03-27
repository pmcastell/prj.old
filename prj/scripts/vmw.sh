#/usr/bin/ecryptfs-mount-private < /dev/null &>/dev/null
#cd /home/franc
if [ "$1" = "" ];
then
   echo uso: $0 '<nombre-maquina-virt> [<stop>]'
   exit 1
fi
MAQUINA=$1
if [ "$(echo $MAQUINA  | grep -i vmx)" != "" ];
then
   MAQUINA=$(echo $MAQUINA  | awk -F'.' '{ print $1;}')
fi   
if [ "$2" = "stop" ];
then
   /usr/bin/vmrun -vp galileo -T ws stop /discoNuevo/franc/vmware/$MAQUINA/$MAQUINA.vmx
else 
   /usr/bin/vmrun -vp galileo -T ws start /discoNuevo/franc/vmware/$MAQUINA/$MAQUINA.vmx nogui
fi
sleep 5
ps aux | grep -i vmx
