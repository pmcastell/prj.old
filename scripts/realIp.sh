MAQUINA=checkip.dyndns.org
MAQUINA=www.cualesmiip.es
CADENA_ID="Tu IP es"
URL="http://$MAQUINA/"
sudo route add -host $MAQUINA gw r1
wget $URL -O - 2> /dev/null | strings | grep "$CADENA_ID" | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}'   
sudo route del -host $MAQUINA
