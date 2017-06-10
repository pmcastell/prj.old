#!/bin/bash

. $(dirname $0)/instiKeys.sh
BASE_DIR="/opt/ltsp/mini-light-client"

#para solucionar lo del arranque por WOL
TEMP=$(tempfile)
cat<<EOF > $TEMP
#!/bin/bash
#
# Uses 'ethtool' to 'fix' WoL functionality since, for some reason, it's
# turned off during bootup (even when, in BIOS, it is turned on).
#
ethtool -s eth0 wol g

# EOF
EOF
#también lo metemos en /etc/rc.local
sudo sed -i 's/exit 0/\/sbin\/ethtool -s eth0 wol g/g' $BASE_DIR/etc/rc.local
sudo bash -c "echo exit 0 >> $BASE_DIR/etc/rc.local"
sudo mv $TEMP $BASE_DIR/etc/ltsp/wolfix
sudo chmod a+rx $BASE_DIR/etc/ltsp/wolfix
sudo chown 0:0 $BASE_DIR/etc/ltsp/wolfix
#/var/lib/tftpboot/ltsp/lts.conf es un enlace a /var/lib/tftpboot/ltsp/i386/lts.conf
if [ "$(sudo cat /var/lib/tftpboot/ltsp/lts.conf | grep RCFILE_01 | grep wolfix)" = "" ]; then
   sudo bash -c 'echo "RCFILE_01=/etc/ltsp/wolfix" >> /var/lib/tftpboot/ltsp/lts.conf'
fi   
#forzamos a usar el escritorio gnome
if [ "$(sudo cat /var/lib/tftpboot/ltsp/lts.conf | grep LDM_FORCE_SESSION | grep gnome-fallback)" = "" ]; then
   sudo bash -c 'echo "LDM_FORCE_SESSION=gnome-fallback" >> /var/lib/tftpboot/ltsp/lts.conf'
fi   


#esto se añade al contab del root, para aue se autoapguen
## mejor hacer chroot y sudo crontab -e
#echo 'CRONTAB_01 = "30 18 * * *   /sbin/poweroff"' >> /var/lib/tftpboot/ltsp/mini-light-client/lts.conf
#sudo vi /var/lib/tftpboot/ltsp/i386/lts.conf # cambiar quitar SHUTDOWN_TIME
#Quitamos apagado automático
sed -i "s/^SHUTDOWN_TIME/#SHUTDOWN_TIME/g" /var/lib/tftpboot/ltsp/lts.conf
#Ponemos apagado a las 18:30
if [ "$(sudo cat $BASE_DIR/etc/crontab | grep -v ^# | grep poweroff)" = "" ]; then
   sudo bash -c "echo 30 20 \* \* \*   root  /sbin/poweroff >> $BASE_DIR/etc/crontab"
fi  

#sudo vi /etc/ltsp/ltsp-update-image.excludes # comentar home y /etc/ssh/ssh_host_*_key
sudo sed -i 's/^home/#home/g' /etc/ltsp/ltsp-update-image.excludes
sudo sed -i 's/^root/#root/g' /etc/ltsp/ltsp-update-image.excludes
sudo sed -i 's/^etc\/ssh\/ssh_host_\*_key/#etc\/ssh\/ssh_host_\*_key/g' /etc/ltsp/ltsp-update-image.excludes

#modificar /etc/sudoers
sudo bash -c "echo ltspadmin  ALL = \(ALL:ALL\) ALL >> $BASE_DIR/etc/sudoers"
sudo bash -c "echo ltspadmin  ALL = NOPASSWD: ALL >> $BASE_DIR/etc/sudoers"
#claves ssh para no tener que poner la clave al iniciar sesión
sudo mkdir -p $BASE_DIR/home/ltspadmin/.ssh
LTSPADMIN_ID=$(cat $BASE_DIR/etc/passwd | grep ltspadmin | awk -F':' '{print $3":"$4;}')
#sudo bash -c "echo $(publicKey) > $BASE_DIR/home/ltspadmin/.ssh/authorized_keys" 
sudo bash -c ". /scripts/aula/instiKeys.sh && publicKey > $BASE_DIR/home/ltspadmin/.ssh/authorized_keys" 
sudo chown -R $LTSPADMIN_ID $BASE_DIR/home/ltspadmin
sudo mkdir -p $BASE_DIR/root/.ssh 
if [ "$(sudo ls $BASE_DIR/root/.ssh/id_rsa 2>/dev/null)" = "" ]; then
   sudo bash -c ". /scripts/aula/instiKeys.sh && privateKey > $BASE_DIR/root/.ssh/id_rsa"
   sudo chmod 600 $BASE_DIR/root/.ssh/id_rsa
else 
   echo "Error ya existe $BASE_DIR/root/.ssh/id_rsa. No se crea el fichero. Revísalo."
fi      
if [ "$(sudo ls $BASE_DIR/root/.ssh/id_rsa.pub 2>/dev/null)" = "" ]; then
   sudo bash -c ". /scripts/aula/instiKeys.sh && publicKey > $BASE_DIR/root/.ssh/id_rsa.pub"
   sudo chmod 644 $BASE_DIR/root/.ssh/id_rsa.pub
else
   echo "Error ya existe /root/.ssh/id_rsa.pub. No se crea el fichero. Revísalo."
fi      

#crear x11vncLtsp.sh
TEMP=$(tempfile)
cat<<EOF > $TEMP
#!/bin/bash

if [ "\$1" = "" ]; then DISP="7"; else DISP="\$1"; fi
PORT=\$(( 5911 + \$DISP ))
if [ "\$(ps aux | grep -i x11vnc | grep /var/run/ | grep Xauthority | grep -v grep)" = "" ]; then
   while true; do
      /usr/bin/x11vnc -capslock -rfbauth /root/.vnc/passwd -autoport \$PORT -auth \$(find /var/run/ | grep Xauthority) -display :\$DISP  &> /dev/null 
   done
fi 
EOF

sudo mv $TEMP $BASE_DIR/root/x11vncLtsp.sh
sudo chmod +x,+r $BASE_DIR/root/x11vncLtsp.sh

if [ "$(cat $BASE_DIR/etc/crontab | grep x11vncLtsp)" = "" ]; then
   sudo bash -c "echo \*/5 \*     \* \* \*   root  /root/x11vncLtsp.sh >> $BASE_DIR/etc/crontab"
fi
sudo mkdir -p $BASE_DIR/root/.vnc
sudo bash -c "echo igRDkZ7OtW8= | base64 -d > $BASE_DIR/root/.vnc/passwd"
sudo chown -R 0:0 $BASE_DIR/root

sudo chroot $BASE_DIR
sudo adduser ltspadmin
sudo gpasswd -a ltspadmin adm 
sudo gpasswd -a ltspadmin lpadmin
sudo chmod +s /sbin/reboot	
sudo apt-get install ethtool openssh-server arp-scan
exit


#volver a generar imagen de arranque ltsp
sudo ltsp-update-image

exit 0






echo "
Starting arp-scan 1.8.1 with 256 hosts (http://www.nta-monitor.com/tools/arp-scan/)
10.2.1.154	00:03:0d:49:6b:67	Uniwill Computer Corp.
10.2.1.164	00:1c:c0:ec:d3:9a	Intel Corporate
10.2.1.173	00:1c:c0:ec:ea:9c	Intel Corporate
10.2.1.168	ec:55:f9:60:4e:c2	Hon Hai Precision Ind. Co.,Ltd.
"
