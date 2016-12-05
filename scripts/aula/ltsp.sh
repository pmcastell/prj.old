cd /opt/ltsp/mini-light-client
chroot /opt/ltsp/mini-light-client
sudo adduser ltspadmin
exit


#para solucionar lo del arranque por WOL
sudo echo"#!/bin/bash
#
# Uses 'ethtool' to 'fix' WoL functionality since, for some reason, it's
# turned off during bootup (even when, in BIOS, it is turned on).
#
ethtool -s eth0 wol g

# EOF" > /opt/ltsp/mini-light-client/etc/ltsp/wolfix
sudo chmod +x /opt/ltsp/mini-light-client/etc/ltsp/wolfix

echo "RCFILE_01               = /etc/ltsp/wolfix" >> /var/lib/tftpboot/ltsp/i386/lts.conf


#esto se añade al contab del root, para aue se autoapguen
## mejor hacer chroot y sudo crontab -e
#echo 'CRONTAB_01 = "30 18 * * *   /sbin/poweroff"' >> /var/lib/tftpboot/ltsp/mini-light-client/lts.conf
sudo vi /var/lib/tftpboot/ltsp/i386/lts.conf # cambiar quitar SHUTDOWN_TIME
sudo chroot /opt/ltsp/mini-light-client
sudo crontab -e
sudo adduser ltspadmin
sudo apt-get install ethtool
sudo vi /etc/ltsp/ltsp-update-image.excludes # comentar home y /etc/ssh/ssh_host_*_key



#volver a generar imagen de arranque ltsp
sudo ltsp-update-image

exit 0

echo "
Starting arp-scan 1.8.1 with 256 hosts (http://www.nta-monitor.com/tools/arp-scan/)
10.2.1.154	00:03:0d:49:6b:67	Uniwill Computer Corp.
10.2.1.164	00:1c:c0:ec:d3:9a	Intel Corporate
10.2.1.173	00:1c:c0:ec:ea:9c	Intel Corporate
10.2.1.168	ec:55:f9:60:4e:c2	Hon Hai Precision Ind. Co.,Ltd.
"3
