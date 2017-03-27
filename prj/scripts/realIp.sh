MAQUINA=www.cualesmiip.es
MAQUINA=checkip.dyndns.org
MAQUINA=portquiz.net
MAQUINA=www.checkip.com
MAQUINA=www.vermiip.es
GATEWAY="$(route -n | grep -vE tun[0-9]+$ | grep ^0\.0\.0\.0 | awk '{print $2;}')"
CADENA_ID="Tu IP es"
CADENA_ID=""
URL="http://$MAQUINA/"
sudo route add -host $MAQUINA gw $GATEWAY
wget $URL -qO -  | strings | grep "$CADENA_ID" | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}' | head -1 
sudo route del -host $MAQUINA
