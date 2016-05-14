MAQUINA=checkip.dyndns.org
MAQUINA=www.cualesmiip.es
GATEWAY="$(route -n | grep -vE tun[0-9]+$ | grep ^0\.0\.0\.0 | awk '{print $2;}')"
CADENA_ID="Tu IP es"
URL="http://$MAQUINA/"
sudo route add -host $MAQUINA gw $GATEWAY
wget $URL -O - 2> /dev/null | strings | grep "$CADENA_ID" | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}'   
sudo route del -host $MAQUINA
