#!/bin/bash

if [ "$(whoami)" != "root" ]; then 
   sudo $0 $*
   exit
fi
   
crearRcLocal() {
cp /etc/rc.local /etc/rc.local.orig
echo '#!/bin/sh -e
#
# rc.local
#
# This script is executed at the end of each multiuser runlevel.
# Make sure that the script will "exit 0" on success or any other
# value on error.
#
# In order to enable or disable this script just change the execution
# bits.
#
# By default this script does nothing.
/bin/echo 1 > /proc/sys/net/ipv4/ip_forward
/sbin/iptables -A POSTROUTING -j MASQUERADE -s 10.2.1.0/24 -t nat

exit 0' > /tmp/rc.local
}

crearDnsmasqConfsrv1() {
echo '#
# This file has been generated by n4d-dnsmasq plugin.
#

# DNS CONFIGURATION
domain-needed
bogus-priv
strict-order
expand-hosts
no-hosts
addn-hosts=/var/lib/dnsmasq/hosts
domain=aula1
local=/aula1/
address=/preseed/mirror/proxy/10.2.1.254
# DHCP CONFIGURATION
interface=eth0
dhcp-authoritative
dhcp-ignore-names
dhcp-option=option:router,0.0.0.0
dhcp-option=option:dns-server,10.2.1.254
dhcp-option=option:domain-name,aula1
dhcp-option=option:domain-search,aula1
dhcp-range=10.2.1.150,10.2.1.250,12h
dhcp-host=00:19:66:e9:3b:f6,pc101,10.2.1.101,infinite
dhcp-host=00:19:66:e9:3b:7a,pc102,10.2.1.102,infinite
dhcp-host=00:19:66:e9:42:17,pc103,10.2.1.103,infinite
dhcp-host=00:19:66:e9:3b:4f,pc104,10.2.1.104,infinite
dhcp-host=00:19:66:e9:3d:88,pc105,10.2.1.105,infinite
dhcp-host=00:19:66:e9:3b:2d,pc106,10.2.1.106,infinite
dhcp-host=00:19:66:e9:3d:30,pc107,10.2.1.107,infinite
dhcp-host=00:19:66:e9:3b:89,pc108,10.2.1.108,infinite
dhcp-host=00:19:66:f0:d1:0c,pc109,10.2.1.109,infinite
dhcp-host=00:19:66:e9:3e:7e,pc110,10.2.1.110,infinite
dhcp-host=00:19:66:e9:3c:78,pc111,10.2.1.111,infinite
dhcp-host=00:19:66:e9:3b:77,pc112,10.2.1.112,infinite
dhcp-host=00:19:66:e9:3b:be,pc113,10.2.1.113,infinite
dhcp-host=00:19:66:e9:41:f9,pc114,10.2.1.114,infinite
dhcp-host=00:19:66:e9:3b:7e,pc115,10.2.1.115,infinite
dhcp-host=00:19:66:e9:3f:07,pc116,10.2.1.116,infinite
dhcp-host=00:19:66:e9:3b:46,pc117,10.2.1.117,infinite
dhcp-host=00:19:66:e9:3e:ed,pc118,10.2.1.118,infinite
dhcp-host=00:19:66:e9:3c:59,pc119,10.2.1.119,infinite
dhcp-host=00:19:66:e9:3b:ac,pc120,10.2.1.120,infinite
dhcp-host=00:19:66:e9:42:07,pc121,10.2.1.121,infinite
dhcp-host=00:19:66:e9:42:12,pc122,10.2.1.122,infinite
dhcp-host=00:19:66:e9:3c:75,pc123,10.2.1.123,infinite
dhcp-host=00:19:66:e9:3b:b4,pc124,10.2.1.124,infinite
dhcp-host=00:19:66:e9:3b:32,pc125,10.2.1.125,infinite
dhcp-host=00:19:66:e9:3c:89,pc126,10.2.1.126,infinite
dhcp-host=00:19:66:e9:3b:e6,pc127,10.2.1.127,infinite
dhcp-host=00:19:66:e9:3b:7b,pc128,10.2.1.128,infinite
dhcp-host=00:19:66:e9:3b:9d,pc129,10.2.1.129,infinite




          
# CONF DIRS
conf-dir=/var/lib/dnsmasq/macs
conf-dir=/var/lib/dnsmasq/config
conf-dir=/etc/dnsmasq.d/' > /etc/dnsmasq.conf
}

crearDnsmasqConfsrv2() {
echo '#
# This file has been generated by n4d-dnsmasq plugin.
#

# DNS CONFIGURATION
domain-needed
bogus-priv
strict-order
expand-hosts
no-hosts
addn-hosts=/var/lib/dnsmasq/hosts
domain=aula1
local=/aula1/
address=/preseed/mirror/proxy/10.2.1.254
# DHCP CONFIGURATION
interface=eth0
dhcp-authoritative
dhcp-ignore-names
dhcp-option=option:router,0.0.0.0
dhcp-option=option:dns-server,10.2.1.254
dhcp-option=option:domain-name,aula1
dhcp-option=option:domain-search,aula1
dhcp-range=10.2.1.100,10.2.1.200,12h
dhcp-host=00:1a:a0:bc:88:f1,pc201,10.2.1.201,infinite
dhcp-host=00:1d:60:d8:69:b5,pc202,10.2.1.202,infinite
dhcp-host=00:1e:8c:07:da:3b,pc203,10.2.1.203,infinite
dhcp-host=00:1d:60:d8:69:c5,pc204,10.2.1.204,infinite
dhcp-host=00:1e:8c:07:da:3a,pc205,10.2.1.205,infinite
dhcp-host=00:1d:60:b4:54:06,pc206,10.2.1.206,infinite
dhcp-host=00:19:66:e9:3b:93,pc207,10.2.1.207,infinite
dhcp-host=00:1d:60:d8:69:b7,pc208,10.2.1.208,infinite
dhcp-host=00:1d:60:d8:69:ae,pc209,10.2.1.209,infinite
dhcp-host=00:1d:60:d8:69:bf,pc210,10.2.1.210,infinite
dhcp-host=00:1e:8c:26:40:e1,pc211,10.2.1.211,infinite
dhcp-host=70:71:bc:ae:dd:35,pc212,10.2.1.212,infinite
dhcp-host=00:1e:8c:26:43:b0,pc213,10.2.1.213,infinite
dhcp-host=00:1a:92:d6:d6:fd,pc214,10.2.1.214,infinite
dhcp-host=00:1d:60:b4:53:d5,pc215,10.2.1.215,infinite
dhcp-host=00:1e:8c:26:40:d9,pc216,10.2.1.216,infinite
dhcp-host=20:cf:30:55:b3:95,pc217,10.2.1.217,infinite
dhcp-host=70:71:bc:ae:e4:35,pc218,10.2.1.218,infinite
dhcp-host=70:71:bc:ae:de:1d,pc219,10.2.1.219,infinite
dhcp-host=70:71:bc:ae:e1:72,pc220,10.2.1.220,infinite


# CONF DIRS
conf-dir=/var/lib/dnsmasq/macs
conf-dir=/var/lib/dnsmasq/config
conf-dir=/etc/dnsmasq.d/' > /etc/dnsmasq.conf
}

if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi  

crearRcLocal
if [ "$(hostname)" = "aulasrv1" ]; then crearDnsmasqConfsrv1 fi
if [ "$(hostname)" = "srv2aula" ]; then crearDnsmasqConfsrv2 fi

sudo apt-get -y install tor connect-proxy privoxy
echo forward-socks4a / 127.0.0.1:9050 . >> /etc/privoxy/config
#redirigir tráfico a través de tor
echo "Host 10.2.1.* pc1* pc2* 10.10.10.*
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	User lliurex	


Host *
	CheckHostIP no
	Compression yes
	Protocol 2
	ProxyCommand connect -4 -S 127.0.0.1:9050 $(tor-resolve %h localhost:9050) %p
" > /root/.ssh/config
#crear ejecución de x11vnc
echo "if [ "$(ps aux | grep -i x11vnc | grep /var/run/lightdm | grep -v grep)" = "" ]; then
   /usr/bin/x11vnc -rfbauth /root/.vnc/passwd -autoport 5911 -auth /var/run/lightdm/root/:0 -display :0 -forever -loop  &> /dev/null &
fi" > /root/x11vncRoot.sh
echo "*/5 * * * *	root  /root/x11vncRoot.sh &> /dev/null &" >> /etc/crontab

echo "#comprobar si /net esta montado
while [ "$(/bin/mount | /bin/grep '/dev/sdb1 on /net type ext4 (rw,acl)')" = "" ];
do
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
   /bin/date +"%Y-%m-%d" >> /root/fallosMontajeSdb1.txt
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
   /sbin/fsck -fyC /dev/sdb1 >> /root/fallosMontajeSdb1.txt
   /bin/mount /net
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
   /bin/date +"%Y-%m-%d" >> /root/fallosMontajeSdb1.txt
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
done" > /root/comprobarMontajeNet.sh
echo "*/5 * * * *	root  /root/comprobarMontajeNet.sh &> /dev/null &" >> /etc/crontab
echo "00 11 * * *	root	/usr/sbin/lliurex-mirror update" >> /etc/crontab


