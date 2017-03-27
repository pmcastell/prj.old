sudo apt-get update
sudo apt-get install pcaputils
DUMPCAP=$(which dumpcap)
if [ "$DUMPCAP" != "" ];
then
   echo no se ha instalado pcaputils
   exit 1
fi   
   
sudo setcap 'CAP_NET_RAW+eip CAP_NET_ADMIN+eip' /usr/sbin/dumpcap

