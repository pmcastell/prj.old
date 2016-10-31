uso() {
   echo uso: $0 '<dir-ip> (10.10.10.x)'
   exit 1
}
if [ "$#" -lt 1 ]; then uso; fi
   
ETH=$(ifconfig | grep -E ^eth | head -2 | tail -1)
echo "auto $ETH:1
iface $ETH:1 inet static
	address $1
	netmask 255.255.255.0" >> /etc/network/interfaces
sudo apt-get install x11vnc ssh
sudo service ssh start	

