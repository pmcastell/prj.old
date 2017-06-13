#copiar
/usr/sbin/slapcat -v -l /home/backup/ldap.diff

#restaurar
/etc/init.d/slapd stop
cd /var/lib/ldap
rm -rf *
/usr/sbin/slapadd -l backup.ldif
/etc/init.d/slapd start





#!/bin/sh
2
	LDAPBK=ldap-$( date +%y%m%d-%H%M ).ldif
3
	BACKUPDIR=/home/backups
4
	/usr/sbin/slapcat -v -b "dc=yourDC,dc=local" -l $BACKUPDIR/$LDAPBK
5
	gzip -9 $BACKUPDIR/$LDAPBK

 

You should just change LDAP suffix from “dc=yourDC,dc=local” to your actual one.

To restore you should perform the following steps.

1.  stop slapd daemon:
debian:~# /etc/init.d/slapd stop
2. delete old database (make sure you are in right directory to use rm):
debian:~# cd /var/lib/ldap
rm -rf *
2. Restore database from LDIF file:
debian:~# /usr/sbin/slapadd -l backup.ldif

4. run slapd daemon:

debian:~# /etc/init.d/slapd start


