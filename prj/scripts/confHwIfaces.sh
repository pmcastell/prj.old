############
#!/bin/bash
############
if [ "$1" != "" ];
then
   IP=$1
else
   IP="192.168.1.6 netmask 255.255.255.0"
fi  
HW=10
BASE=00:01:38:ED:76
BASE=5c:d9:98:e5:68
for i in $(interfaces);
do
   VM=$(echo $i | grep -iE '(vmnet)')
   if [ "$VM" != "" -a "$VM" != "vmnet8" ];
   then 
      sudo ifconfig $VM down
   fi
   VM=$(echo $i | grep -iEo '(eth[0-9]+|bge[0-9]+)|wlan[0-9]+|ath[0-9]+')
   if [ "$VM" != "" ];
   then
      ESTADO=$(ifconfig| grep $i | grep -v grep)
      #echo estado: $ESTADO - interfaz: $i
      sudo ifconfig $i down
      if [ "$OS" = "FreeBSD" ];
      then
         eecho sudo ifconfig $i link $BASE:$HW
      else
         eecho sudo ifconfig $i hw ether $BASE:$HW
      fi
      if [ "$ESTADO" != "" ];
      then
        sudo ifconfig $i up
      fi
      ###eecho sudo ifconfig $VM $IP
      INTERFAZ=$VM
   fi
   HW=$(($HW + 1))
done
#echo $INTERFAZ

