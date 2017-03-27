if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi   
sudo route add -host 69.94.131.10 gw r1
#sudo route add -net 195.57.19.0/24 gw r1
#sudo route add -net 195.235.183.0/24 gw r1
#sudo route add -host 83.36.146.40 gw r1a
TEMP="/tmp/rutasNuevas.txt"
if [ -f $TEMP ]; then
   while read RUTA; do sudo route del -host $RUTA; done < $TEMP
   rm $TEMP
fi
   
for IP in $(sudo netstat -onatup | grep sslh | grep -v grep | awk '{print $5;}'| grep -v 0.0.0.0 | grep -v 127.0.0.1 | awk -F':' '{print $1;}'); do 
   sudo route add -host $IP gw r1
   echo $IP >> $TEMP
done

