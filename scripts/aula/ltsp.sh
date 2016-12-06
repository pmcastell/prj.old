#!/bin/bash

BASE_DIR="/opt/ltsp/mini-light-client"

#para solucionar lo del arranque por WOL
sudo echo "#!/bin/bash
#
# Uses 'ethtool' to 'fix' WoL functionality since, for some reason, it's
# turned off during bootup (even when, in BIOS, it is turned on).
#
ethtool -s eth0 wol g

# EOF" > $BASE_DIR/etc/ltsp/wolfix
sudo chmod +x $BASE_DIR/etc/ltsp/wolfix
#/var/lib/tftpboot/ltsp/lts.conf es un enlace a /var/lib/tftpboot/ltsp/i386/lts.conf
echo "RCFILE_01=/etc/ltsp/wolfix" >> /var/lib/tftpboot/ltsp/lts.conf
echo "LDM_FORCE_SESSION=gnome-fallback" >> /var/lib/tftpboot/ltsp/lts.conf


#esto se añade al contab del root, para aue se autoapguen
## mejor hacer chroot y sudo crontab -e
#echo 'CRONTAB_01 = "30 18 * * *   /sbin/poweroff"' >> /var/lib/tftpboot/ltsp/mini-light-client/lts.conf
#sudo vi /var/lib/tftpboot/ltsp/i386/lts.conf # cambiar quitar SHUTDOWN_TIME
sed -i "s/^SHUTDOWN_TIME/#SHUTDOWN_TIME/g" /var/lib/tftpboot/ltsp/lts.conf

#sudo vi /etc/ltsp/ltsp-update-image.excludes # comentar home y /etc/ssh/ssh_host_*_key
sed -i "s/^home/#home/g" /etc/ltsp/ltsp-update-image.excludes
sed -i "s/^root/#root/g" /etc/ltsp/ltsp-update-image.excludes
sed -i "s/^\/etc\/ssh\/ssh_host_\*_key/#\/etc\/ssh_host_\*_key/g" /etc/ltsp/ltsp-update-image.excludes



#if [ "$(cat $BASE_DIR/etc/crontab | grep -v ^# | grep poweroff)" = "" ]; then
#   echo "30 18 * * *   root   /sbin/poweroff" >> $BASE_DIR/etc/crontab
#fi   
sudo chroot $BASE_DIR
sudo adduser ltspadmin
sudo apt-get install ethtool
exit
sudo echo "
#!/bin/bash
if [ "$1" = "" ]; then DISP="7"; else DISP="$1"; fi
PORT=$(( 5911 + $DISP ))
if [ "$(ps aux | grep -i x11vnc | grep /var/run/lightdm | grep -v grep)" = "" ]; then
   while true; do
      /usr/bin/x11vnc -capslock -rfbauth /root/.vnc/passwd -autoport $PORT -auth $(find /var/run/ | grep Xauthority) -display :$DISP  
#&> /dev/null 
   done
fi " > $BASE_DIR/root/x11vncLtsp.sh
sudo chmod +x $BASE_DIR/root/x11vncLtsp.sh
if [ "$(cat $BASE_DIR/etc/crontab | grep x11vncLtsp)" = "" ]; then
   echo "*/5 *	* * *	root	/root/x11vncLtsp.sh" >> $BASE_DIR/etc/crontab
fi

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
