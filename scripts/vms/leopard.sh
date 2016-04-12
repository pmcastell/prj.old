#/usr/bin/ecryptfs-mount-private < /dev/null &>/dev/null
#cd /home/franc
if [ "$1" = "stop" ];
then
   /usr/bin/vmrun -T ws stop /discoNuevo/franc/vmware/leopard-10.6.5/MacOs10.6.5_x64.vmx
else 
   /usr/bin/vmrun -T ws start /discoNuevo/franc/vmware/leopard-10.6.5/MacOs10.6.5_x64.vmx nogui
fi
sleep 5
ps aux | grep -i vmx
