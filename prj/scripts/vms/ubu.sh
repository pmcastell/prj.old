#/usr/bin/ecryptfs-mount-private < /dev/null &>/dev/null
#cd /home/franc
DIR_MAQUINA=/home/franc/miUbuntu64
CHA="$(cat /home/franc/cha.txt)"
for((i=1;i<=10;i++));
do
   CHA=$(echo -n $CHA | base64 -d)
done
if [ "$1" = "stop" ];
then
   /usr/bin/vmrun -vp $CHA -T ws suspend $DIR_MAQUINA/miUbuntu64.vmx
else 
   /usr/bin/vmrun -vp $CHA -T ws start $DIR_MAQUINA/miUbuntu64.vmx nogui
fi
sleep 5
ps aux | grep -i vmx
