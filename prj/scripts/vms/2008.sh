#/usr/bin/ecryptfs-mount-private < /dev/null &>/dev/null
#cd /home/franc
if [ "$1" = "stop" ];
then
   /usr/bin/vmrun -T ws stop /discoNuevo/franc/vmware/winServ2008/winServ2008.vmx
else 
   /usr/bin/vmrun -T ws start /discoNuevo/franc/vmware/winServ2008/winServ2008.vmx nogui
fi
sleep 5
ps aux | grep -i vmx
