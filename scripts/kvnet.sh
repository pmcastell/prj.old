#!/bin/bash
if [ "$(ifconfig | grep -E ^kvmbr0)" = "" ]; then
   RED="10.10.10"
   MASK="255.255.255.0"
   sudo brctl addbr kvmbr0
   sudo ifconfig kvmbr0 $RED.100/24
   sudo ifconfig kvmbr0 up
   sudo eecho dnsmasq -i kvmbr0 --bind-interfaces --dhcp-range=$RED.15,$RED.99,$MASK --except-interface=lo --port=0
   sudo iptables -t nat -D POSTROUTING -j MASQUERADE -s $RED.0/$MASK &>/dev/null
   sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s $RED.0/$MASK
fi
if [ "$(ip -o a | grep tap0 | grep 10.10.10.100)" != "" ]; then
   #read -p "tap0 existe, borrarlo?" RESP
   #[ "$RESP" = "s" ] && tunctl -d tap0
   sudo tunctl -d tap0
fi   
   	
TAPS=$(ifconfig -a | grep -E ^tap | awk '{print $1;}')
[ "$(echo $1 | grep -E '([0-9]{1,3}\.){3}')" = "" ] && IP="10.10.10.100/24" ||  (IP="$1" && shitf)
sudo kvm -machine q35 -soundhw hda -vga vmware -k es -boot menu=on -localtime -net nic,vlan=0,macaddr=$(/scripts/randomMAC.sh) -net tap,vlan=0 "$@" &> /tmp/kvm.txt &
sleep 2
TAPS2=$(ifconfig -a | grep -E ^tap | awk '{print $1;}')
echo "TAPS2: $TAPS2"
for t in $TAPS2; do
   echo "t: $t"
   #[ "$(echo "$TAPS" | grep $t)" = "" ] && sudo ip a add $IP dev $t && break
   [ "$(echo "$TAPS" | grep $t)" = "" ] && sudo brctl addif kvmbr0 $t
done   
