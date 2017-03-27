sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s 172.16.144.0/24
sudo bash -c 'echo 1 > /proc/sys/net/ipv4/ip_forward' 
cat /proc/sys/net/ipv4/ip_forward
