LISTA_DE_SITIOS_BLOQUEADOS="youtube.com www.youtube.com"
uso() {
   echo Bloqueo desdbloqueo de $*
   echo uso: $0 '<on|off>'
   exit 1
}
if [ "$1" = "" ] || [ "$1" != "ON" -a "$1" != "on" -a "$1" != "off" -a "$1" != "OFF" ]; then
   uso $LISTA_DE_SITIOS_BLOQUEADOS
fi   
if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi   
if [ ! -f /etc/hosts.original ]; then
   cp /etc/hosts /etc/hosts.original
fi
if [ "$1" = "off" -o "$1" = "OFF" ]; then
   cp /etc/hosts.original /etc/hosts
elif [ "$1" = ON -o "$1" = "on" ]; then   
   shift
   while [ "$1" != "" ];    do
      LISTA_DE_SITIOS_BLOQUEADOS="$LISTA_DE_SITIOS_BLOQUEADOS $1"
      shift
   done
   for SITIO in $LISTA_DE_SITIOS_BLOQUEADOS; do
      if [ "$(cat /etc/hosts | grep $SITIO)" = "" ];
      then
         echo 127.0.0.1 $SITIO >> /etc/hosts
      fi
   done
fi   
sudo service squid3 stop
sudo service squid3 start
sudo service dnsmasq restart
