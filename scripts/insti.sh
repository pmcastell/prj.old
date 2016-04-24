sudo route add -net 195.57.19.0/24 gw r1
sudo route add -net 195.235.183.0/24 gw r1
sudo openvpn --proto tcp-server --port 443 --dev tun4 --ifconfig 10.7.0.6 10.7.0.7 --verb 5 --secret /m/Mios/prj/scripts/openvpn/key 2> /dev/null
sleep 10
sudo route add -net 10.10.10.0/24 gw 10.7.0.7
ping -c 4 10.10.10.11
