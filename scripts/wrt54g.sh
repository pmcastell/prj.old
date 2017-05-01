/sbin/ifconfig eth1 192.168.1.27 netmask 255.255.255.0
/sbin/route add default gw 192.168.1.1
/bin/sleep 10
/usr/sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 172.16.254.0/24
/usr/sbin/iptables -A PREROUTING -t nat -p tcp -i eth1 --dport 443 -j DNAT --to-destination 172.16.254.7:443
/sbin/sleep 10
/sbin/ifconfig wl0.1 172.16.254.252 netmask 255.255.255.0
/usr/sbin/brctl addif br0 wl0.1
