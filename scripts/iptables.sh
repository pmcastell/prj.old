sudo iptables -t nat -nL
sudo iptables -F
sudo iptables -t nat -D POSTROUTING 3
sudo iptables -t nat -j MASQUERADE -A POSTROUTING -s 10.32.32.0/24
