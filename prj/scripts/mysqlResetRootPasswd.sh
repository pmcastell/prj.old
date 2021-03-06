if [ "$1" = "" ];
then
   echo uso $0 '<nueva-passwd-del-root>'
   exit 1
fi
echo "UPDATE mysql.user SET Password=PASSWORD('$1') WHERE User='root'; " > /tmp/mysql.init
echo "FLUSH PRIVILEGES;" >> /tmp/mysql.init
PR=$(ps aux | grep -i mysql | grep -v grep)
while [ "$PR" != "" ];
do
   sudo eecho /etc/init.d/mysql stop
   PR=$(ps aux | grep -i mysqld | grep -v grep | grep -v mysqlResetRootPasswd)
   echo PR: $PR
   sleep 1
done
sudo mysqld_safe --init-file=/tmp/mysql.init &
PR=$(ps aux | grep -i mysql | grep -v grep)
while [ "$PR" != "" ];
do
   sudo eecho /etc/init.d/mysql stop
   PR=$(ps aux | grep -i mysqld | grep -v grep | grep -v mysqlResetRootPasswd)
   echo PR: $PR
   sleep 1
done
sudo /etc/init.d/mysql restart

