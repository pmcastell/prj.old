#/usr/bin/ecryptfs-mount-private < /dev/null &>/dev/null
#cd /home/franc
if [ "$1" = "stop" ];
then
   /usr/bin/vmrun -vp galileo -T ws stop /discoNuevo/franc/vmware/miUbuntu64/miUbuntu64.vmx
else 
   /usr/bin/vmrun -vp galileo -T ws start /discoNuevo/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui
fi
sleep 5
ps aux | grep -i vmx
