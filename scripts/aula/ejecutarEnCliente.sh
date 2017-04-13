#!/bin/bash
#--------------------------------------------------------------------------------------------------------------------------------
ssh_rsa() {
sudo echo 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDCBlkziUqHifjpH3WxWNHWPIOXpzddS1CCd7X3y7beWzCRxJ49IEdwoY8cTYytA14D989oG3EVNM18t+hqEKo/YEf/spW6Frzi+c1TASOFXAwBZ6etHK2eWsn2lZkX85Oht/ZyKsUQEfolcLJfPA0jOuqBnXWn4kYb55cPtUnZRO2VeoRADEpgxty0ohdh5D3Q6gZRd7GSO7ZkJUWooWOjxf4kUSJSbd6mANwa2HfX5HnbgNSK8NUrx++e4uGzJZIyNXuZSz8bOh2xiuBnG7IrB+kTITH8uys+gvLG22k06k9wxtIsR+HEzHgNc2/LUmJlDzDv25+kRfF/MYd+npfF franav@aula1srv franav@aula2srv' >> /home/lliurex/.ssh/authorized_keys
}
#--------------------------------------------------------------------------------------------------------------------------------
ssh_config() {
echo 'Host 10.2.1.* pc1* pc2* server
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	ForwardX11 yes
' > /home/lliurex/.ssh/config
}
#--------------------------------------------------------------------------------------------------------------------------------
pantalla_script() {
echo 'sudo x11vnc -noshm -24to32 -connect_or_exit server:$1 -auth /var/run/lightdm/root/:0 -display :0' > /usr/local/bin/pantalla
chmod +x /usr/local/bin/pantalla
}
#--------------------------------------------------------------------------------------------------------------------------------
fsarch_script() {
echo '#!/bin/bash
if [ "$(uname -i)" = "x86_64" ]; then
   sudo sshfs lliurex@server:./imagenes/imagenOrdenadorNuevoKernel4APD /root/server
elif [ "$(uname -i)" = "i686" ]; then
   sudo sshfs lliurex@server:./imagenes/imagenOrdenadorViejo /root/server
else
   echo Error. No sé si el equipo es viejo o nuevo; exit 1
fi   
if [ $? -gt 0 ] || [ "$(mount | grep '\'/root/server\'')" = "" ]; then echo error montando carpeta en servidor sshfs; exit 2; fi
if [ "$(uname -i)" = "x86_64" ]; then
   sudo fsarchiver -v -A -a -z 9 -s 640 -j 2 --exclude=/var/cache/apt/archives --exclude=/home savefs /root/server/ordenadorNuevoKernel4APD.fsa /dev/sda1
elif [ "$(uname -i)" = "i686" ]; then
   sudo fsarchiver -v -A -a -z 9 -s 640 -j 2 --exclude=/var/cache/apt/archives --exclude=/home savefs /root/server/ordenadorViejo.fsa /dev/sda1
fi   
sudo umount /root/server' > /usr/local/bin/fsarch.sh
chmod +x /usr/local/bin/fsarch.sh
}
#--------------------------------------------------------------------------------------------------------------------------------
#--------------------------------------------------------------------------------------------------------------------------------
lockCd_script() {
echo '#!/bin/bash
/usr/bin/eject -i on /dev/cdrom' > /usr/local/bin/lockCd
chmod +x /usr/local/bin/lockCd
}
#--------------------------------------------------------------------------------------------------------------------------------
unlockCd_script() {
echo '#!/bin/bash
/usr/bin/eject -i off /dev/cdrom' > /usr/local/bin/unlockCd
chmod +x /usr/local/bin/unlockCd
}
#--------------------------------------------------------------------------------------------------------------------------------
hostname_script() {
echo '#!/bin/bash
uso() {
   /bin/cat<<EOF
   uso $0 
   Comprueba si el nombre de host coincide con la dirección ip
   P.Ej. si la dir ip es 10.2.1.114 comprueba que el nombre del equipo sea pc114
   y si no lo es lo cambia
EOF
   exit 1
}

if [ "$1" = "-h" ]; then uso; fi  
if [ "$(/usr/bin/whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi    
INTERFAZ=$(/sbin/ifconfig | /bin/grep -E "^eth" | /usr/bin/awk '\'\{print \$1\;\}\'')
DIR="10\.2\.1\."
for((i=1;i<=60;i++)); do 
   PCNUM=$(/sbin/ifconfig $INTERFAZ | /bin/grep -oE "${DIR}[0-9]{1,3}" | /usr/bin/head -1 | /usr/bin/awk -F'\'.\'' '\'\{print \$4\;\}\'')
   if [ "$PCNUM" != "" ]; then break; fi
   /bin/sleep 1
   #/bin/echo i >> /etc/if.txt
   #/usr/bin/uptime >> /etc/if.txt
   #/sbin/ifconfig >> /etc/if.txt
done   
if [ "$(/bin/hostname)" != "pc${PCNUM}" ]; then
   /bin/hostname pc${PCNUM}
   /bin/echo pc${PCNUM} > /etc/hostname
fi   
if [ "$(/bin/cat /etc/hosts | /bin/grep pc${PCNUM})" = "" ]; then
   /bin/cp /etc/hosts /tmp/hosts.old
   echo "127.0.0.1   localhost pc${PCNUM}" > /etc/hosts
   echo "10.10.10.11 moodle" >> /etc/hosts
   /bin/cat /tmp/hosts.old | grep -v 127.0.0 | grep -v 127.0.1 | grep -v moodle >> /etc/hosts
fi' > /usr/local/bin/hostname.sh
  chmod +x /usr/local/bin/hostname.sh
}
#--------------------------------------------------------------------------------------------------------------------------------
fich_autoexec () {
echo '
#Comprobar si el nombre del equipo corresponde con los 3 últimos digitos de la Dir.IP., si no cambiar el nombre del equipo a pc999
#donde 999 son los 3 últimos dígitos de la Dir.IP. asignada por DHCP. También poner dicho nombre en /etc/hostname y añadirlo a
#/etc/hosts como 127.0.0.1, además añadir a éste último fichero 10.10.10.11 moodle
/usr/local/bin/hostname.sh
#Bloquear la unidad de dvd
/usr/bin/eject -i on /dev/cdrom
' > /root/autoexec.sh
chmod +x /root/autoexec.sh
}
#--------------------------------------------------------------------------------------------------------------------------------
fich_rc_local() {
echo "#!/bin/sh -e
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

/root/autoexec.sh

exit 0
" > /etc/rc.local
}
fich_60_cdrom_id.rules () {
echo '
# do not edit this file, it will be overwritten on update

ACTION=="remove", GOTO="cdrom_end"
SUBSYSTEM!="block", GOTO="cdrom_end"
KERNEL!="sr[0-9]*|xvd*", GOTO="cdrom_end"
ENV{DEVTYPE}!="disk", GOTO="cdrom_end"

# unconditionally tag device as CDROM
KERNEL=="sr[0-9]*", ENV{ID_CDROM}="1"

# media eject button pressed
#ENV{DISK_EJECT_REQUEST}=="?*", RUN+="cdrom_id --eject-media $devnode", GOTO="cdrom_end"

# import device and media properties and lock tray to
# enable the receiving of media eject button events
IMPORT{program}="cdrom_id --lock-media $devnode"

KERNEL=="sr0", SYMLINK+="cdrom", OPTIONS+="link_priority=-100"

LABEL="cdrom_end"

' > /etc/udev/rules.d/60-cdrom_id.rules
}
fich_apt_ubu() {
echo 'deb http://mirror/llx1505 trusty main restricted universe multiverse
deb http://mirror/llx1505 trusty-updates main restricted universe multiverse
deb http://mirror/llx1505 trusty-security main restricted universe multiverse
deb http://es.archive.ubuntu.com/ubuntu trusty main universe multiverse restricted
deb http://es.archive.ubuntu.com/ubuntu trusty-updates main universe multiverse restricted
deb http://es.archive.ubuntu.com/ubuntu trusty-security main universe multiverse restricted
' > /etc/apt/sources.list
}
fich_apt_noUbu() {
echo 'deb http://mirror/llx1505 trusty main restricted universe multiverse
deb http://mirror/llx1505 trusty-updates main restricted universe multiverse
deb http://mirror/llx1505 trusty-security main restricted universe multiverse
#deb http://es.archive.ubuntu.com/ubuntu trusty main universe multiverse restricted
#deb http://es.archive.ubuntu.com/ubuntu trusty-updates main universe multiverse restricted
#deb http://es.archive.ubuntu.com/ubuntu trusty-security main universe multiverse restricted
' > /etc/apt/sources.list
}
fich_crontab() {
echo '
15 15	* * *	root	/sbin/poweroff
15 21	* * *	root	/sbin/poweroff
' >> /etc/crontab
}

if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
ESTA=$(cat /etc/sudoers | grep "lliurex ALL = NOPASSWD: ALL")
if [ "$ESTA" = "" ]; then echo lliurex ALL = NOPASSWD: ALL >> /etc/sudoers; fi
mkdir -p /home/lliurex/.ssh
ssh_rsa
ssh_config
chown -R lliurex:lliurex /home/lliurex
sed -i 's/test -f \/etc\/ltsp_chroot || exit 0/#test -f \/etc\/ltsp_chroot || exit 0/g' /etc/init.d/epoptes-client
sed -i 's/grep -qs "init=\/sbin\/init-ltsp" \/proc\/cmdline && exit 0/#grep -qs "init=\/sbin\/init-ltsp" \/proc\/cmdline && exit 0/g'
pantalla_script
fsarch_script
hostname_script
lockCd_script
unlockCd_script
fich_rc_local
fich_autoexec
fich_60_cdrom_id.rules
fich_crontab
/usr/local/bin/hostname.sh
#INSTALAR PAQUETES
fich_apt_ubu
apt update
apt install wireshark
#apt -f install intef-exe_2.1.2_all.deb
#realvnc
#liclipse
fich_apt_noUbu

