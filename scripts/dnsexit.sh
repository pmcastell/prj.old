uso() {
   echo uso: $0 '<host-a-actualizar>'
   exit
}
   
if [ "$1" = "" ];
then
   uso
fi
LOGIN=reg6543
PASSWORD=basura
HOST=$1
IP_ULTIMA=$(dig $HOST | grep -E "^$HOST" | grep IN | grep A | awk '{print $NF;}')
IP_ACTUAL=$(dirIp)
if [ "$IP_ACTUAL" != "$IP_ULTIMA" ];
then
   eecho wget "http://update.dnsexit.com/RemoteUpdate.sv?login=$LOGIN&password=$PASSWORD&host=$HOST&myip=$IP_ACTUAL" -O /tmp/dnsexit.inf 2> /dev/null
   cat /tmp/dnsexit.inf
   echo ----------------------
else
   echo no hacía falta actualizar: $IP_ACTUAL
fi   
