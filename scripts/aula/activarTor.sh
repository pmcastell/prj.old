uso() {
   echo uso: $0 '<on|off>'
   echo on activa salida tor
   echo off desactiva salida tor
   exit
}
###Instalar squid3, privoxy y tor
#sudo apt-get -y install squid3 privoxy tor
###Cambiar configuración de privosy añadiendo la línea:
#sudo bash -c 'echo forward-socks4a / 127.0.0.1:9050 . >> /etc/privoxy/config'
###Y finalmente según quiera activarse o desactivarse la salida a través de tor, pasarle a éste script 
###el parámetro on u off
if [ "$1" = "" ]; then uso; fi
if [ "$(whoami)" != "root" ]; then sudo $0 $@; exit $?; fi
CONF_SQUID="/etc/squid3/squid.conf"
#CONF_SQUID="/tmp/squid.conf"
LINEA1="cache_peer localhost parent 8118 7 no-digest no-query"
LINEA2="never_direct allow all"
if [ "$1" = "on" ]; then
   if [ "$(cat $CONF_SQUID | grep -E "^${LINEA1}$")" = "" ]; then
      echo $LINEA1 >> $CONF_SQUID
   fi
   if [ "$(cat $CONF_SQUID | grep -E "^${LINEA2}$")" = "" ]; then
      echo $LINEA2 >> $CONF_SQUID
   fi
else
   eval sed -i "'/^${LINEA1}$/d'" $CONF_SQUID
   eval sed -i "'/^${LINEA2}$/d'" $CONF_SQUID
fi
sudo service squid3 restart
