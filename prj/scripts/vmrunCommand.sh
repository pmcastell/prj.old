uso() {
   echo uso: $0 comando
   exit 1
}   
if [ "$1" = "" ];
then
   uso()
fi
if [ "$1" = "poweroff"];
then   
   vmrun -vp galileo1564 -gu root -gp 4651galileo -T ws runProgramInGuest  /discoNuevo/franc/vmware/miUbuntu64/miUbuntu64.vmx /sbin/poweroff
elif [ "$1" = "stop" ];
then
   vmrun -vp galileo1564 -T ws stop /discoNuevo/franc/vmware/miUbuntu64/miUbuntu64.vmx soft
fi   
