sudo route add -net 195.57.19.0/24 gw r1
sudo route add -net 195.235.183.0/24 gw r1
sudo mkdir /run/openvpn
#sudo openvpn --proto tcp-server --port 443 --tls-server --mode server --port-share 127.0.0.1 22 --dev tun4 --ifconfig 10.7.0.6 10.7.0.7 --verb 5 --dh /etc/openvpn/dh2048.pem --ca /etc/openvpn/ubu.noip.me-ca.crt  --cert /etc/openvpn/ubu.noip.me.crt  --key /etc/openvpn/ubu.noip.me.key 
sudo /usr/sbin/openvpn --writepid /run/openvpn/openvpn-443.pid --daemon ovpn-openvpn-443 --status /run/openvpn/openvpn-443.status 10 --cd /etc/openvpn --config /etc/openvpn/openvpn-443.conf --script-security 2
