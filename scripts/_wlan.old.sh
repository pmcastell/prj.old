INTERFACE=ath0
IP=192.168.1.7
ROUTER=192.168.1.1
if [ "$1" = "" -o "$1" = "WebSTAR" ];
then
   ESSID=WebSTAR
   CHANNEL=7
   IP=192.168.0.7
   ROUTER=192.168.0.1
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL
fi      
if [ "$1" = "WLAN_9A" ];
then
   ESSID=WLAN_9A 
   CHANNEL=4
   KEY=5A303030324346433133463941
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi     
if [ "$1" = "WLAN_FF" ];
then
   ESSID=WLAN_FF
   CHANNEL=9
   KEY=5A303030324346423033394646
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi  
if [ "$1" = "WLAN_55" ];
then
   ESSID=WLAN_55
   CHANNEL=7
   KEY=5A303030324346433837453535
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi  
if [ "$1" = "WLAN_95" ];
then
   ESSID=WLAN_95
   CHANNEL=9
   KEY=5A303030324346364244303935
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi  
if [ "$1" = "WLAN_3F" ];
then
   ESSID=WLAN_3F
   CHANNEL=6
   KEY=58303030313338433742343346
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi  
if [ "$1" = "WLAN_37" ];
then
   ESSID=WLAN_37
   CHANNEL=4
   KEY=5A303030324346364343353337
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi  
if [ "$1" = "WLAN_BC" ];
then
   ESSID=WLAN_BC
   CHANNEL=6
   KEY=58303030313338364542344243
   echo iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
   iwconfig $INTERFACE essid $ESSID channel $CHANNEL key $KEY
fi  
     


sleep 1
echo ifconfig $INTERFACE $IP
ifconfig $INTERFACE $IP
echo route add default gw $ROUTER
route add default gw $ROUTER



                                                                        

# 00:01:38:DD:DA:7E   10        2        0    0   6  54. WEP  WEP         WLAN_8E             
 
 
