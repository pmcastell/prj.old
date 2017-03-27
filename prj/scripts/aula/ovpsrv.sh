uso() {
   echo uso: $0 '<número-dispositivo-tun>'
   exit 1
}   
if [ "$1" = "" ]; then uso; fi
sudo openvpn --proto tcp-server --port 443 --dev tun$1 --ifconfig 10.$1.$1.1 10.$1.$1.$1 --verb 5 --secret /root/key
