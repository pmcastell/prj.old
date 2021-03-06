#!/bin/bash
/etc/init.d/slapd stop
cd /var/lib
find ldap/ | cpio -o -H newc | gzip -9 > /root/var_lib_ldap_$(hostname).gz
cd /etc
find ldap/ | cpio -o -H newc | gzip -9 > /root/etc_ldap_$(hostname).gz
/etc/init.d/slapd start
#restaurar:
cd /var/lib
cat /root/var_lib_ldap_aula1srv.gz | gzip -d | cpio -idv
cd /etc
cat /root/etc_ldap_aula1srv.gz | gzip -d | cpio -idv
chown -R openldap:openldap /var/lib/ldap
chown -R openldap:openldap /etc/ldap/slap.d
