#!/bin/bash

appendSiNoEsta() {
   CAD="$1"
   FICH="$2"
   [ ! -f "$FICH" ] && touch $FICH
   if [ "$(cat "$FICH" | grep -F "$CAD")" = "" ]; then
      echo "$CAD" >> "$FICH"
   fi
}

#--------------------------------------------------------------------------------------------------------------------------------
ssh_rsa() {
appendSiNoEsta 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDCBlkziUqHifjpH3WxWNHWPIOXpzddS1CCd7X3y7beWzCRxJ49IEdwoY8cTYytA14D989oG3EVNM18t+hqEKo/YEf/spW6Frzi+c1TASOFXAwBZ6etHK2eWsn2lZkX85Oht/ZyKsUQEfolcLJfPA0jOuqBnXWn4kYb55cPtUnZRO2VeoRADEpgxty0ohdh5D3Q6gZRd7GSO7ZkJUWooWOjxf4kUSJSbd6mANwa2HfX5HnbgNSK8NUrx++e4uGzJZIyNXuZSz8bOh2xiuBnG7IrB+kTITH8uys+gvLG22k06k9wxtIsR+HEzHgNc2/LUmJlDzDv25+kRfF/MYd+npfF franav@aula1srv franav@aula2srv' '/home/lliurex/.ssh/authorized_keys'
appendSiNoEsta 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDmd//XtHyaFOzg95K3tYMISnTRy3c2d/7wCb7NgAs1hIGs6oL4vyn5Xwyk+WrVPJ4xbdFShXpAHJIZ4KPINvc5b2/s7ik8xzju23dlGfl5Cv5s0A2aDiUHk9geGfhYBfw9Fp0oHpq0oiQ9RZ6rNE0iiRF9ql3stwdPy7Bj+q1GT2xRx+POBikPjh4+Njsy3x2TlIhuSZbrTbuKUyTAL2A/64Zyd38ANFPxxlxU7mIotbR0XRNMQuVtS0PipvonhBwJoO30IJ2o7wcJ14ZnLo3fT3KEora4Cbu7Hk+PXz3a0Djie3AQCZQTEjmxwREHcj//xFSjr269cRtV3y9Vmrid lliurex' '/home/lliurex/.ssh/authorized_keys'
sudo mkdir /root/.ssh &>/dev/null
sudo echo 'ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDmd//XtHyaFOzg95K3tYMISnTRy3c2d/7wCb7NgAs1hIGs6oL4vyn5Xwyk+WrVPJ4xbdFShXpAHJIZ4KPINvc5b2/s7ik8xzju23dlGfl5Cv5s0A2aDiUHk9geGfhYBfw9Fp0oHpq0oiQ9RZ6rNE0iiRF9ql3stwdPy7Bj+q1GT2xRx+POBikPjh4+Njsy3x2TlIhuSZbrTbuKUyTAL2A/64Zyd38ANFPxxlxU7mIotbR0XRNMQuVtS0PipvonhBwJoO30IJ2o7wcJ14ZnLo3fT3KEora4Cbu7Hk+PXz3a0Djie3AQCZQTEjmxwREHcj//xFSjr269cRtV3y9Vmrid lliurex' > /root/.ssh/id_rsa.pub
sudo echo '-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEA5nf/17R8mhTs4PeSt7WDCEp00ct3Nnf+8Am+zYALNYSBrOqC
+L8p+V8MpPlq1TyeMW3RUoV6QBySGeCjyDb3OW9v7O4pPMc47tt3ZRn5eQr+bNAN
mg4lB5PYHhn4WAX8PRadKB6atKIkPUWeqzRNIokRfapd7LcHT8uwY/qtRk9sUcfj
zgYpD44ePjY7Mt8dk5SIbkmW6027ilMkwC9gP+uGcnd/ADRT8cZcVO5iKLW0dF0T
TELlbUtD4qb6J4QcCaDt9CCdqO8HCdeGZy6N309yhKK2uAm7ux5Pj1892tA44ntw
EAmUExI5scERB3I//8RUo69uvXEbVd8vVZq4nQIDAQABAoIBAQDjbxRZJ9G2tGO2
9lohVMyESAXUazev1ucMtnFGls949tSJcbviAbRIVRZDT6ev2uw51ye0iehEkG1V
gCvgju2WDnSjmbu0MN3sSUQfz2wzmTfO5domZ0L/oVSrD/HV28I5/rEEQH9nNoMQ
vD2CslBUfnN2UcPysARVKKtfXqfUMvkbvtDmpGStypxZjZNrMoVEauMwcAw7DTka
zZ7RZQ/Qggw3uL+r69A2h1sK41mLriP2HNrcXHlNni/8gz/YnHCUt73Un8FJHa5M
cI62uXCSZSlh5+EJMsVasJJAQ8JevSq8nefC9tTcT18raDBZyTiUPWaR3xKgYLlz
jTNeuPb5AoGBAPl4lsxGhmv5E3fHpCQdtoEIfidwBS01NtrcSroq++VQ85hWDAF2
vEA0gvGBl/g/xdEmVIO6H0YHmb4n4fpQeFvPUGDFJKxmsaxVTmrwnQNYFChGJkex
6yiEPS4Cavn1WY5V6FelpKmdwZBPtyfvekkxrrAgNyBPtnIN4fUnws/DAoGBAOyA
GSxYIPCPnT7PYzpRBkBh00l5wPp6cpld4c72LiXEoYJj71QRKX8CHbA2vLf0s9Fl
erDlIb1kmlwiAWDJoEcnes7r+LZ5bjnZya/CoYD7QDcMydSxYW/MKd+L3ppR+H45
it5V9YOFkScObQMHUla/ryysrWrDsMIbTh2fxTAfAoGAVeUsXtdljyWqi1Jk9L56
SUFIVoHedmtdIMw6upD0PEe8J9ZwucxNEvpBn6/USHiEvveon9Zk0DLrv6pxx0MP
veTKsxcOAoI1aLXSk0WXekBIaHmG0qay5jdp7T6N4NSWakiwAAWbhX8411xrHsf4
3dzKXgApmul3Uxvukf+HtwsCgYEA2qQioahn634Z9XNtXQCDMwo9d3QkQGzrfXYZ
yGg3iZsbZZxWKfYj0tN19u7j0X1csH2My/VJF/rl6b9WXtCIaL/K/XmSWcAxczIx
D1h0TvX5C+u6u/OxDNZG7/AzP6AAh1FTXPFDJPBanSztAxvFt4VGwZpySH3Z79mm
giSu/CkCgYBW/wO2OaUY8jkDDlFt4Rb6R09uZ7TxaVe7iG4VEAKdTL4xj4xZbu0C
Wr+VGE8uPqlz4AsqzXIJA++ghHDGHgY/WQ3H3QjTCa7TYtJA4j510cTb0MjRydkR
XX/DUocuBEXKRpQCfdqCazCbgFR6gJVJStkseTluyEbY+OGe9//IeA==
-----END RSA PRIVATE KEY-----
' > /root/.ssh/id_rsa
sudo cp /root/.ssh/id_rsa* /home/lliurex/.ssh/
sudo chown -R lliurex:lliurex /home/lliurex
for U in "/root /home/lliurex"; do
   sudo chmod 600 $U/.ssh/id_rsa
   sudo chmod 644 $U/.ssh/id_rsa.pub
done   
}
#--------------------------------------------------------------------------------------------------------------------------------
ssh_config() {

echo 'Host 10.2.1.* pc1* pc2* 10.10.10.* localhost 127.0.0.1 172.18.161.* server
        CheckHostIP no
        UserKnownHostsFile /dev/null
        StrictHostKeyChecking no
        Compression yes
        Protocol 2
        ProxyCommand none
        User lliurex
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
uso() {
   cat<<EOF
   Uso: $0 <nombre-imagen>
EOF
   exit 1
}
errorMensa() {
   [ "$2" != "" ] && ERROR=2 || ERROR=$2
   INTENTOS=1
   while [ "$(mount | grep /root/server)" != "" ] && [ $INTENTOS -lt 5 ]; do 
      sudo umount /root/server; 
      INTENTOS=$(($INTENTOS+1))
   done
   echo $1
   exit $2
}
[ "$1" = "" ] && uso
if [ "$(whoami)" != "root" ]; then  
   #echo "pasamos a root" 
   sudo "$0" "$@"
   exit $?
fi
sudo sshfs lliurex@server:./imagenes /root/server
[ $? -gt 0 ] || [ "$(mount | grep "/root/server")" = "" ] && errorMensa "error montando carpeta en servidor sshfs" 2
[ -d "/root/server/$1" ] && errorMensa "ya existe una imagen con ese nombre" 3
sudo mkdir "/root/server/$1"
sudo fsarchiver -v -A -a -z 9 -s 640 -j 2 --exclude=/var/cache/apt/archives savefs "/root/server/$1/$1.fsa" /dev/sda1
sudo umount /root/server ' > /usr/local/bin/fsarch.sh
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
   /bin/cat /tmp/hosts.old | grep -v 127.0.0 | grep -v 127.0.1  >> /etc/hosts
fi
if [ "$(/bin/cat /etc/hosts | /bin/grep moodle)" = "" ]; then
   /bin/cp /etc/hosts /tmp/hosts.old
   echo "10.10.10.11 moodle" > /etc/hosts
   /bin/cat /tmp/hosts.old | grep -v moodle >> /etc/hosts
fi
rm /tmp/hosts.old &> /dev/null
' > /usr/local/bin/hostname.sh
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

#ejecutar x11vnc por si hay que conectarse directamente al equipo no a través del epoptes
#lo comento porque parece que en los equipos con 1GB va lento
##/usr/bin/x11vnc -auth /var/run/lightdm/root/:0 -forever -loop &> /dev/null &

#activar wake on lan (por si acaso)
#ethtool -s eth0 wol g
' > /root/autoexec.sh
for IFACE in $(ifconfig | grep -E '^eth' | awk '{print $1;}' | awk -F ':' '{print $1;}' | uniq); do
   echo "ethtool -s $IFACE wol g" >> /root/autoexec.sh
done   
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
   appendSiNoEsta '15 15   * * *   root    /sbin/poweroff' '/etc/crontab'
   appendSiNoEsta '15 21   * * *   root    /sbin/poweroff' '/etc/crontab'
}

configScratch() {
echo "<?xml version='1.0' encoding='UTF-8'?>
<mime-info xmlns='http://www.freedesktop.org/standards/shared-mime-info'>
   <mime-type type='application/x-scratch2'>
      <comment></comment>
      <glob pattern='*.sb2'/>
   </mime-type>
</mime-info> 
" > /usr/share/mime/packages/sb2.xml

   sudo find / | grep -i edu.media.mit.scratch2editor.desktop | 
   while read f; do
      appendSiNoEsta 'MimeType=application/x-scratch2' "$f"
   done
   sudo update-desktop-database
   sudo update-mime-database /usr/share/mime
   #reboot PC
}

fich_etc_profile() {
echo '#!/bin/sh
sleep 120
/usr/bin/setxkbmap es &
/usr/bin/gsettings set com.canonical.indicator.sound visible true &> /dev/null &
/usr/bin/gsettings set com.canonical.indicator.session user-show-menu false &> /dev/null &
/usr/bin/dconf write /com/canonical/indicator/sound visible true &> /dev/null &
/usr/bin/dconf write /com/canonical/indicator/session user-show-menu false &> /dev/null &
#/usr/bin/gsettings set com.canonical.indicator.session suppress-restart-menuitem true &> /dev/null &
#/usr/bin/gsettings set com.canonical.indicator.session suppress-shutdown-menuitem true &> /dev/null &
' > /usr/local/bin/profile-2.sh
chmod +x /usr/local/bin/profile-2.sh
appendSiNoEsta '/usr/local/bin/profile-2.sh &' '/etc/profile'

}
epoptes() {
   sed -i 's/^test -f \/etc\/ltsp_chroot || exit 0/#test -f \/etc\/ltsp_chroot || exit 0/g' /etc/init.d/epoptes-client
   sed -i 's/^grep -qs "init=\/sbin\/init-ltsp" \/proc\/cmdline \&\& exit 0/#grep -qs "init=\/sbin\/init-ltsp" \/proc\/cmdline \&\& exit 0/g' /etc/init.d/epoptes-client
   sed -i 's/lliurex-version -t client || exit 0/lliurex-version -t client #|| exit 0/g' /usr/sbin/epoptes-client
   sed -i 's/> \/dev\/nul$/\&> \/dev\/null/g' /usr/sbin/epoptes-client
}   
firefox() {
   appendSiNoEsta 'lockPref("network.proxy.type",5);' '/etc/firefox/syspref.js'
}   
liclipse() {
   sudo chmod -R 777 /usr/share/liclipse/configuration
}   
usuario_lliurex() {
   appendSiNoEsta 'lliurex ALL = NOPASSWD: ALL' '/etc/sudoers'
   mkdir -p /home/lliurex/.ssh
   ssh_rsa
   ssh_config
   chown -R lliurex:lliurex /home/lliurex
   for u in $(ls /home | grep -v lliurex); do
      sudo rm -rf /home/$u
   done   
}
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
#ESTA=$(cat /etc/sudoers | grep "lliurex ALL = NOPASSWD: ALL")
#if [ "$ESTA" = "" ]; then echo lliurex ALL = NOPASSWD: ALL >> /etc/sudoers; fi
pantalla_script
fsarch_script
hostname_script
lockCd_script
unlockCd_script
fich_rc_local
fich_autoexec
fich_60_cdrom_id.rules
fich_crontab
fich_etc_profile
epoptes
firefox
liclipse
usuario_lliurex
/usr/local/bin/hostname.sh
#INSTALAR PAQUETES
fich_apt_ubu
#apt update
#apt install wireshark
#sudo apt install python-suds python-requests-oauthlib cssmin python-webassets
#scp lliurex@server:./Descargas/*intef* /tmp/
#sudo dpkg -i /tmp/intef-exe_2.1.2_all.deb
#realvnc
#liclipse
#scratch 2.0
#instalar con el zero-center
#configScratch
fich_apt_noUbu

