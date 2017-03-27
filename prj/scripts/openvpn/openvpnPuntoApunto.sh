openvpn --genkey --secret key
### CLIENTE
sudo openvpn --remote ubu.noip.me 443 --proto tcp-client tcp --dev tun4 --ifconfig 10.7.0.7 10.7.0.6 --port 443 --secret key 
### SERVIDOR
sudo openvpn --remote 195.235.183.105 --proto tcp-server --port 443 --dev tun4 --ifconfig 10.7.0.6 10.7.0.7 --verb 5 --secret key
