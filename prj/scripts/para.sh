OS=$(uname)
if [ "$OS" = "FreeBSD" ];
then
   sudo /usr/local/etc/rc.d/samba stop
   sudo /usr/local/etc/rc.d/cupsd stop
   sudo /usr/local/etc/rc.d/apache22 stop
   sudo /etc/rc.d/sshd stop
   sudo /usr/local/etc/rc.d/mysql-server stop
   sudo /usr/local/etc/rc.d/rsyncd stop
   sudo /usr/local/etc/rc.d/squid stop
   sudo /usr/local/etc/rc.d/avahi-daemon stop
   vino-preferences
else
   sudo /etc/init.d/smbd stop
   sudo stop smbd
   sudo stop nmbd
   sudo /etc/init.d/cups stop
   sudo stop cups
   sudo /etc/init.d/apache2 stop
   sudo stop apache2
   sudo /etc/init.d/ssh stop
   sudo stop ssh
   sudo /etc/init.d/mysql stop
   sudo stop mysql
   sudo /etc/init.d/rsync stop
   sudo stop rsync
   sudo /etc/init.d/squid stop
   sudo stop squid
   vino-preferences
   sudo /etc/init.d/avahi-daemon stop
   sudo stop avahi-daemon
   sudo mata dhclient
fi
