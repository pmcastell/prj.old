sudo route del default
PASARELA=$(route -n | egrep '^0.0.0.0' | awk '{print $2;}')
sudo route del -net $(route -n | egrep '^0.0.0.0' | grep 128 | grep -v grep | awk '{print $1" netmask "$3}')
sudo route del -net $(route -n | egrep '^128.0.0.0' | awk '{print $1" netmask "$3}')
sudo route add -host 80.36.87.166 gw $PASARELA
sudo route add default gw r1


