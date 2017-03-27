cp /etc/resolv.conf.insti /etc/resolv.conf
ifconfig msk0 10.1.1.193 netmask 255.255.255.0
route add default 10.1.1.1
