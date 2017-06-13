#!/bin/bash
appendSiNoEsta() {
   CAD="$1"
   FICH="$2"
   [ ! -f "$FICH" ] && touch $FICH
   if [ "$(cat "$FICH" | grep -F "$CAD")" = "" ]; then
      echo "$CAD" >> "$FICH"
   fi
}

crearRcLocal() {
cp /etc/rc.local /etc/rc.local.orig
cat /etc/rc.local.orig | grep -v '^exit' > /etc/rc.local

echo "
/root/autoexec.sh &

exit 0" >> /etc/rc.local
}
crearAutoexec() {
echo '#!/bin/bash

/bin/echo 1 > /proc/sys/net/ipv4/ip_forward
/sbin/iptables -A POSTROUTING -j MASQUERADE -s 10.2.1.0/24 -t nat

' > /root/autoexec.sh
for IFACE in $(ifconfig | grep -E '^eth' | awk '{print $1;}' | awk -F ':' '{print $1;}' | uniq); do
   echo "ethtool -s $IFACE wol g" >> /root/autoexec.sh
done   
chmod +x /root/autoexec.sh
}

crearDnsmasqConfAula2Srv() {
echo '#
# This file has been generated by n4d-dnsmasq plugin.
#

# DNS CONFIGURATION
# 
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
dhcp-range=10.2.1.230,10.2.1.250,12h
dhcp-host=00:19:66:e9:3b:f6,pc201,10.2.1.201,12h
dhcp-host=00:19:66:e9:3b:7a,pc202,10.2.1.202,12h
dhcp-host=00:19:66:e9:3b:4f,pc203,10.2.1.203,12h
dhcp-host=00:19:66:e9:42:17,pc204,10.2.1.204,12h
dhcp-host=00:19:66:e9:3d:88,pc205,10.2.1.205,12h
dhcp-host=00:19:66:e9:3b:2d,pc206,10.2.1.206,12h
dhcp-host=00:19:66:e9:3d:30,pc207,10.2.1.207,12h
dhcp-host=00:19:66:e9:3b:89,pc208,10.2.1.208,12h
dhcp-host=00:19:66:f0:d1:0c,pc209,10.2.1.209,12h
dhcp-host=00:19:66:e9:3e:7e,pc210,10.2.1.210,12h
dhcp-host=00:19:66:e9:3c:78,pc211,10.2.1.211,12h
dhcp-host=00:19:66:e9:3b:77,pc212,10.2.1.212,12h
dhcp-host=00:19:66:e9:3b:be,pc213,10.2.1.213,12h
dhcp-host=00:19:66:e9:41:f9,pc214,10.2.1.214,12h
dhcp-host=00:19:66:e9:3b:7e,pc215,10.2.1.215,12h
dhcp-host=00:19:66:e9:3f:07,pc216,10.2.1.216,12h
dhcp-host=00:19:66:e9:3b:46,pc217,10.2.1.217,12h
dhcp-host=00:19:66:e9:3e:ed,pc218,10.2.1.218,12h
dhcp-host=00:19:66:e9:3c:59,pc219,10.2.1.219,12h
dhcp-host=00:19:66:e9:3b:ac,pc220,10.2.1.220,12h
dhcp-host=00:19:66:e9:42:07,pc221,10.2.1.221,12h
dhcp-host=00:19:66:e9:42:12,pc222,10.2.1.222,12h
dhcp-host=00:19:66:e9:3c:75,pc223,10.2.1.223,12h
dhcp-host=00:19:66:e9:3b:b4,pc224,10.2.1.224,12h
dhcp-host=00:19:66:e9:3b:32,pc225,10.2.1.225,12h
dhcp-host=00:19:66:e9:3c:89,pc226,10.2.1.226,12h
dhcp-host=00:19:66:e9:3b:e6,pc227,10.2.1.227,12h
#dhcp-host=00:19:66:e9:3b:7b,pc228,10.2.1.228,12h
#dhcp-host=00:19:66:e9:3b:9d,pc229,10.2.1.229,12h




          
# CONF DIRS
conf-dir=/var/lib/dnsmasq/macs
conf-dir=/var/lib/dnsmasq/config
conf-dir=/etc/dnsmasq.d/' > /etc/dnsmasq.conf
}

crearDnsmasqConfAula1Srv() {
echo '#
# This file has been generated by n4d-dnsmasq plugin.
#

# DNS CONFIGURATION
#
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
dhcp-range=10.2.1.150,10.2.1.199,12h
dhcp-host=00:1a:a0:bc:88:f1,pc101,10.2.1.101,12h
dhcp-host=00:1d:60:d8:69:b5,pc102,10.2.1.102,12h
dhcp-host=00:1e:8c:07:da:3b,pc103,10.2.1.103,12h
dhcp-host=00:1d:60:d8:69:c5,pc104,10.2.1.104,12h
dhcp-host=00:1e:8c:07:da:3a,pc105,10.2.1.105,12h
dhcp-host=00:1d:60:b4:54:06,pc106,10.2.1.106,12h
dhcp-host=00:19:66:e9:3b:93,pc107,10.2.1.107,12h
dhcp-host=00:1d:60:d8:69:b7,pc108,10.2.1.108,12h
dhcp-host=00:1d:60:d8:69:ae,pc109,10.2.1.109,12h
dhcp-host=00:1d:60:d8:69:bf,pc110,10.2.1.110,12h
dhcp-host=00:1e:8c:26:40:e1,pc111,10.2.1.111,12h
dhcp-host=70:71:bc:ae:dd:35,pc112,10.2.1.112,12h
dhcp-host=00:1e:8c:26:43:b0,pc113,10.2.1.113,12h
dhcp-host=00:1a:92:d6:d6:fd,pc114,10.2.1.114,12h
#dhcp-host=00:1d:60:b4:53:d5,pc115,10.2.1.115,12h
dhcp-host=00:1d:60:d8:69:b8,pc115,10.2.1.115,12h
dhcp-host=00:1e:8c:26:40:d9,pc116,10.2.1.116,12h
dhcp-host=20:cf:30:55:b3:95,pc117,10.2.1.117,12h
dhcp-host=70:71:bc:ae:e4:35,pc118,10.2.1.118,12h
dhcp-host=70:71:bc:ae:de:1d,pc119,10.2.1.119,12h
dhcp-host=70:71:bc:ae:e1:72,pc120,10.2.1.120,12h
dhcp-host=00:19:66:e9:3b:9d,pc121,10.2.1.121,12h
dhcp-host=00:19:66:e9:3b:7b,pc122,10.2.1.122,12h


# CONF DIRS
conf-dir=/var/lib/dnsmasq/macs
conf-dir=/var/lib/dnsmasq/config
conf-dir=/etc/dnsmasq.d/' > /etc/dnsmasq.conf
}
instalarTorPrivoxy() {
   apt-get -y install tor connect-proxy privoxy
   echo "forward-socks4a / 127.0.0.1:9050 ." >> /etc/privoxy/config
}
crearSshConfig() {
echo "Host 10.2.1.* pc1* pc2* 10.10.10.* localhost 127.0.0.1 172.18.161.* server sp sa1 sa2 sp2 st sp2 sb sm spt
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	User lliurex	
	Forwardx11 yes


Host *
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand connect -4 -S 127.0.0.1:9050 \$(tor-resolve %h localhost:9050) %p
" > /root/.ssh/config
mkdir -p /home/lliurex/.ssh &> /dev/null
cp /root/.ssh/config /home/lliurex/.ssh/
chown -R lliurex:lliurex /home/lliurex
}
crearX11VncRoot() {
echo "if [ \"\$(ps aux | grep -i x11vnc | grep /var/run/lightdm | grep -v grep)\" = \"\" ]; then
   /usr/bin/x11vnc -rfbauth /root/.vnc/passwd -autoport 5911 -auth /var/run/lightdm/root/:0 -display :0 -forever -loop  &> /dev/null &
fi" > /root/x11vncRoot.sh
chmod +x /root/x11vncRoot.sh
echo "*/5     * * * * root    /root/x11vncRoot.sh &> /dev/null &" >> /etc/crontab
mkdir -p /root/.vnc
echo zFpD7reVydg= | base64 > /root/.vnc/passwd
}
crearComprobarMontajeNet() {
echo "#comprobar si /net esta montado
while [ \"\$(/bin/mount | /bin/grep '/dev/sdb1 on /net type ext4 (rw,acl)')\" = \"\" ];
do
   echo -n \"-----------------------\" >> /root/fallosMontajeSdb1.txt
   /bin/date +\"%Y-%m-%d\" >> /root/fallosMontajeSdb1.txt
   echo -n \"-----------------------\" >> /root/fallosMontajeSdb1.txt
   /sbin/fsck -fyC /dev/sdb1 >> /root/fallosMontajeSdb1.txt
   /bin/mount /net
   echo -n \"-----------------------\" >> /root/fallosMontajeSdb1.txt
   /bin/date +\"%Y-%m-%d\" >> /root/fallosMontajeSdb1.txt
   echo -n \"-----------------------\" >> /root/fallosMontajeSdb1.txt
done" > /root/comprobarMontajeNet.sh
chmod +x /root/comprobarMontajeNet.sh
appendSiNoEsta "*/1     * * * * root    /root/comprobarMontajeNet.sh &> /dev/null &" >> /etc/crontab

}
cambiarConfigSamba() {
echo "
[net]
    comment = Public 
    path = /net
    public = yes
    guest ok = yes
    writable = yes
    printable = no
    valid users = jualeo01 jatalaveram alexonrubia franav lliurex" > /etc/samba/sambaMio
appendSiNoEsta "include = /etc/samba/sambaMio" '/etc/samba/smb.conf    '
}
crearNetworkInterfacesAulaSrv_old() {
cp /etc/network/interfaces /etc/network/interfaces.0
echo "# interfaces(5) file used by ifup(8) and ifdown(8)
auto lo
iface lo inet loopback

auto eth0
iface eth0 inet static
	address 10.2.1.254
	netmask 255.255.255.0
	dns-search aula1

auto eth1
iface eth1 inet dhcp

#red alias servidores del centro
auto eth1:1
iface eth1:1 inet static
	address 10.10.10.$1
	netmask 255.255.255.0
	
#red alias impresoras
auto eth1:2
iface eth1:2 inet static
	address 192.168.10.$(( 200 + $1 ))
	netask 255.255.255.0

#red alias puntos de acceso
auto eth1:3
iface eth1:3 inet static
	address 192.168.1.$(( 200 + $1 ))
	netask 255.255.255.0

" > /etc/network/interfaces
}
crear_restrict_login_powermgmt_pkla() {
#deshabilita el reinicio y apagado de la máquina
echo '[Disable lightdm PowerMgmt]
Identity=unix-user:*
Action=org.freedesktop.login1.reboot;org.freedesktop.login1.reboot-multiple-sessions;org.freedesktop.login1.power-off;org.freedesktop.login1.power-off-multiple-sessions;org.freedesktop.login1.suspend;org.freedesktop.login1.suspend-multiple-sessions;org.freedesktop.login1.hibernate;org.freedesktop.login1.hibernate-multiple-sessions
ResultAny=no
ResultInactive=no
ResultActive=no
' >> /etc/polkit-1/localauthority/50-local.d/restrict-login-powermgmt.pkla
}
crear_profile_2() {
echo '#!/bin/sh
sleep 120
/usr/bin/setxkbmap es &
/usr/bin/gsettings set com.canonical.indicator.sound visible true &> /dev/null &
/usr/bin/gsettings set com.canonical.indicator.session suppress-restart-menuitem true &> /dev/null & 
/usr/bin/gsettings set com.canonical.indicator.session suppress-shutdown-menuitem true &> /dev/null &
/usr/bin/gsettings set com.canonical.indicator.session user-show-menu false &> /dev/null &
/usr/bin/gsettings set com.canonical.notify-osd gravity 2 &> /dev/null &

/usr/bin/dconf write /com/canonical/indicator/sound visible true &> /dev/null &
/usr/bin/dconf write /com/canonical/indicator/session suppress-restart-menuitem true &> /dev/null & 
/usr/bin/dconf write /com/canonical/indicator/session suppress-shutdown-menuitem true &> /dev/null &
/usr/bin/dconf write /com/canonical/indicator/session user-show-menu false &> /dev/null &
' > /usr/local/bin/profile-2.sh
chmod +x /usr/local/bin/profile-2.sh
appendSiNoEsta '/usr/local/bin/profile-2.sh &>/dev/null &' '/etc/profile'
}
############################################################

if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?;fi  

crearRcLocal
crearAutoexec
if [ "$(/sbin/ifconfig eth0 | grep 'e0:cb:4e:2c:a2:62')" != "" ]; then crearDnsmasqConfAula2Srv; crearNetworkInterfacesAulaSrv 12; fi
if [ "$(/sbin/ifconfig eth0 | grep 'c8:3a:35:d0:f8:fc')" != "" ]; then crearDnsmasqConfAula1Srv; crearNetworkInterfacesAulaSrv 11; fi
sed -i "s/sleep/#sleep/g" /etc/init/failsafe.conf
#Con tor/privosy podemos redirigir a squid a través de la red tor
instalarTorPrivoxy
#redirigir tráfico a través de tor
crearSshConfig
#crear ejecución de x11vnc
crearX11VncRoot
#comprobar el montaje de /net que falla bastantes veces
###crearComprobarMontajeNet
#cambiar config samba
cambiarConfigSamba
crearNetworkInterfacesAulaSrv_old
#Actualizar mirror del aula/sistema a las 11 de la mañana
appendSiNoEsta "00 11 * * *	root	/usr/sbin/lliurex-mirror update" '/etc/crontab'
appendSiNoEsta "#00 02  * * 1-5 root    /sbin/poweroff" '/etc/crontab'
appendSiNoEsta "#55 15  * * 1-5 root    /sbin/poweroff" '/etc/crontab'


#Quitar del menú de inicio de sesión y del menú "logout" del escritorio la posibilidad de reiniciar o apagar el servidor
crear_restrict_login_powermgmt_pkla 
crear_profile_2
#Instalar
#sudo apt-get install festival wireshark etherwake arp-scan fsarchiver wmctrl xdotool encfs nautilus-dropbox
#scratch2
#moodle
#cambiar en get_max_upload_file_size/moodlelib.php la función get_max_upload_file_size poniendo como primera instrucción return return 31457280;
sudo lliurex-mirror set_architecture_all
sudo /usr/sbin/lliurex-mirror update &>/dev/null &

