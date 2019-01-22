sudo ip link set wlan0 name wlan13
ip a add local 10.16.16.2/24 remote 10.16.16.16/24 dev tun16
sudo ip address add 10.10.10.100/24 dev tap0
sudo ip address add 10.18.18.100/24 dev tap0
sudo ip link set dev tap0 up
sudo ip link set tap0 a 00:00:42:47:bf:28
sudo ip tuntap add mode tap tap55
