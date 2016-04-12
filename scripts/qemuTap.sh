#!/bin/bash
#http://blog.alantan.com/2007/01/qemu-tap-bridge-network-configuration.html
if [ "$1" != "" ];
then
   IFACE=$1
else
   #IFACE=eth4
   IFACE=$(interfaces | grep -i eth | head -1)
fi
BRCTL=$(which brctl)
# 1st, release all DHCP address and remove all IP address associated with the original $IFACE
#/sbin/dhcpcd -k
/sbin/ip addr flush $IFACE
# then take the interface down so we can rename it
/sbin/ip link set $IFACE down
# now rename the original $IFACE to r$IFACE (Real $IFACE)
#/sbin/nameif -r r$IFACE $IFACE
/sbin/ip link set $IFACE name r$IFACE
# OK, bring the same interface (with new name though) back up
/sbin/ip link set r$IFACE up
# 2nd let's create a bridge called $IFACE so other programs think they are talking to the same old interface (actually they will talk to the bridge which is a clone of the original $IFACE - with name MAC addr)
eecho $BRCTL addbr $IFACE
# then add both origianl $IFACE and tap1 device to the bridge
#tunctl -t tap0 #primero hay que crear tap0
#$BRCTL addif $IFACE tap0
eecho $BRCTL addif $IFACE r$IFACE
echo "showing bridge mac addresses"
$BRCTL showmacs $IFACE
# 3rd, we need to bring the newly created bridge UP
/sbin/ip link set $IFACE up
# 4th, renew the DHCP address if possible
#/sbin/dhcpcd -n
if [ "$1" = "" ];
then
   /sbin/ifconfig $IFACE 192.168.1.6
   /sbin/route add default gw 192.168.1.1
else
   /sbin/ifconfig $IFACE $1.6
   /sbin/route add default gw $1.1   
fi   
/sbin/ip addr show
sudo kvm -boot d -cdrom /l/ImagenesCdDvd/winXpLinux.iso -m 512 -localtime -net nic -net tap

