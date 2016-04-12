if [ "$(ps aux | grep -i teamviewerd | grep -v grep)" = "" ];
then
    sudo start teamviewerd
else    
    sudo restart teamviewerd
fi
teamviewer &> /dev/null &
sleep 15
CONEXIONES=$(sudo netstat -onatup | grep -i teamviewer | grep -v ESCUCHAR | grep -v '0.0.0.0' | grep -v 127.0.0.1 | awk '{ print $5;}' | awk -F':' '{ print $1;}')
for i in $CONEXIONES;
do
    sudo route add -host $i gw 192.168.1.1
done;    
