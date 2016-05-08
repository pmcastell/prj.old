###3
#!/bin/bash
uso() {
   echo uso: $0 '[-m <cantidad-ram>] (-boot d -cdrom <fichero-cdrom> | -hda <fichero-imagen-disco>)'
   exit 1
}
function _remmina {
TEMP=$(tempfile)
echo "[remmina]
disableclipboard=0
ssh_auth=0
clientname=
quality=0
ssh_charset=
ssh_privatekey=
console=0
resolution=1366x768
group=
password=
name=local:$1
ssh_loopback=0
shareprinter=0
ssh_username=
ssh_server=
security=
protocol=VNC
execpath=
sound=off
exec=
ssh_enabled=0
username=
sharefolder=
domain=
server=localhost:$1
colordepth=32
window_maximize=0
window_height=768
viewmode=1
window_width=1366" > $TEMP.remmina
remmina -c $TEMP.remmina &
rm $TEMP
}
if [ $# -lt 1 ]; then uso; fi   
if [ "$(ifconfig | grep -E ^kvmbr0)" = "" ]; then
   RED="10.15.15"
   MASK="255.255.255.0"
   sudo brctl addbr kvmbr0
   sudo ifconfig kvmbr0 $RED.1/24
   sudo ifconfig kvmbr0 up
   sudo eecho dnsmasq --interface=kvmbr0 --bind-interfaces --dhcp-range=$RED.15,$RED.200,$MASK
   sudo iptables -t nat -D POSTROUTING -j MASQUERADE -s $RED.0/$MASK &>/dev/null
   sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s $RED.0/$MASK
fi
VNCPORT=55
VNCUSADOS=$(sudo netstat -lnt | awk '{print $4;}' | grep 59 | awk -F':' '{print $2;}')
while true; do
   if [ "$(echo $VNCUSADOS | grep $VNCPORT)" = "" ]; then break; fi
   VNCPORT=$(( $VNCPORT + 1 ))
done 
###1
sudo kvm -machine q35 -soundhw hda -vga qxl -k es -boot menu=on -vnc ":$VNCPORT" -localtime -net nic,vlan=0,macaddr=00:1d:60:d8:69:b7 -net tap,vlan=0 -m 1024 "$*" &
sleep 2
###2
_remmina $VNCPORT
TAPS=$(ifconfig -a | grep -E ^tap | awk '{print $1;}') 
for t in $TAPS; do
   #brctl addif kvmbr0 tap0; #brctl addif kvmbr0 tap1
   sudo brctl addif kvmbr0 $t
done   

exit 0




###1
###TAPS=$(ifconfig -a | grep -E ^tap | awk '{print $1;}') 
###NUM_TAPS=$(ifconfig -a | grep -E ^tap | awk '{print $1;}' | wc -l)
###if [ "$TAPS" = "" ]; then NUEVO_TAP="tap0"; else NUEVO_TAP="tap$NUM_TAPS"; fi
#http://backreference.org/2010/03/26/tuntap-interface-tutorial/
###sudo tunctl -u usuario -t $NUEVO_TAP
###sudo brctl addif kvmbr0 $NUEVO_TAP
###sudo ifconfig $NUEVO_TAP up
###kvm -machine q35 -vga vmware -k es -boot menu=on -vnc :$VNCPORT -localtime -net nic,vlan=0,macaddr=$(randomMAC) -net tap,vlan=0,ifname=$NUEVO_TAP -m 1024 $* &
#sudo kvm -machine q35 -soundhw hda -vga std -k es -boot menu=on -vnc ":$VNCPORT" -localtime -net nic,vlan=0,macaddr=$(randomMAC) -net tap,vlan=0 -m 1024 $* &

###2
###sudo tunctl -d $NUEVO_TAP
#vinagre localhost:$VNCPORT &>/dev/null &

###3
#sudo kvm -machine q35 -vga vmware -k es -boot menu=on -vnc :15 -localtime -net nic,vlan=0 -net tap,vlan=0,ifname=tap15 -m 1024 -hda /dev/sdb 
#sudo kvm -machine q35 -g widthxheight[xdepth] -vga vmware -k es -boot menu=on -vnc :15 -localtime -net nic,vlan=0 -net tap,vlan=0,ifname=tap15 -m 1024 -hda /dev/sdb
#sudo kvm -machine q35 -vga vmware -vnc :15 -k es -boot menu=on  -localtime -net nic -net vde,ifname=tap0 -m 1024 -hda /dev/sdb

