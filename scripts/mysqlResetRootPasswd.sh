#!/bin/bash
#si falla lo que viene debajo leer: https://stackoverflow.com/questions/39281594/error-1698-28000-access-denied-for-user-rootlocalhost
#cambiar en /etc/mysql/mysql.conf.d/mysqld.cnf
###[mysqld_safe]
### #socket         = /var/run/mysqld/mysqld.sock
###socket          = /tmp/mysqld.sock
###pid-file        = /tmp/mysqld.pid
###nice            = 0
#http://www.ubuntu-es.org/node/56498
#Para los que andan locos buscando la solución, asi como yo estaba...
#Yo tenía ese problema, busque por varios lugares, y tenian el mismo problema, pero no encontraba como resolverlo. Cuando trataba de inicar mysql:
#/# /etc/init.d/mysql start
#3* Starting MySQL database server mysqld [fail]
#y si lo ejecutaba:
# mysql
#ERROR 2002 (HY000): Can't connect to local MySQL server through socket '/var/run/mysqld/mysqld.sock' (2)
#por lo que segui lo que indicó inmolatus:
##apt-get remove --purge mysql-server
##apt-get remove --purge mysql-server-5.0
##apt-get remove --purge mysql-server-4.0
##apt-get autoremove
##apt-get autoclean
##apt-get install mysql-server

if [ "$1" = "" ]; then
   echo uso $0 '<nueva-passwd-del-root>'
   exit 1
fi
espera_parada_mysql() {
   while true; do
      sudo eecho /etc/init.d/mysql stop
      PR=$(ps aux | grep -i mysqld | grep -v grep | grep -v mysqlResetRootPasswd)
      echo PR: $PR
      [ "$PR" == "" ] && break
      sleep 1
   done
}   

echo "UPDATE mysql.user SET Password=PASSWORD('$1') WHERE User='root'; " > /tmp/mysql.init
#lo siguiente es para evitar que mysql sólo permita acceder como root de la base de datos al root del sistema operativo
echo "UPDATE mysql.user SET plugin='mysql_native_password' WHERE User='root';" >> /tmp/mysql.init
echo "FLUSH PRIVILEGES;" >> /tmp/mysql.init

espera_parada_mysql
sudo mysqld_safe --init-file=/tmp/mysql.init &
sleep 2
espera_parada_mysql
sudo /etc/init.d/mysql restart

