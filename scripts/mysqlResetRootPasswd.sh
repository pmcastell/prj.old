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
echo "FLUSH PRIVILEGES;" >> /tmp/mysql.init
espera_parada_mysql
sudo mysqld_safe --init-file=/tmp/mysql.init &
sleep 2
espera_parada_mysql
sudo /etc/init.d/mysql restart

