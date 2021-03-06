uso() {
   echo $0 puerto maquina
   exit 1
}
if [ "$1" = "-h" ];
then
   uso
elif [ "$1" = "stop" ];
then
   #sudo kill -TERM $(ps aux | grep openvpn | grep -v grep | grep -v sudo | awk '{print $2;}')
   sudo mata openvpn
   exit 0
elif [ "$1" = "start" ];
then
   shift
fi   
if [ "$1" != "" ];
then
   PUERTO=$1
else
   #PUERTO=1194
   PUERTO=443
fi
if [ "$2" != "" ];
then
   MAQUINA=$2
else
   #while [ "$MAQUINA" = "" ];
   for DNS in 8.8.8.8 192.168.0.1 192.168.0.2  80.58.0.33 80.58.61.250 80.58.61.254;
   do
      digHost $DNS inclan.tk
      MAQUINA=$(digHost $DNS inclan.tk)
      if [ "$MAQUINA" != "" ];
      then
         break
      fi
      sleep 1
   done
fi   
if [ "$4" != "" ];
then
   FICH_SALIDA=$4
else
   FICH_SALIDA=/dev/stdout
fi           
echo MAQUINA $MAQUINA
cd /home/usuario/openvpn/
CONFIG=client.ovpn
#sudo eecho openvpn --config $CONFIG --remote $MAQUINA --port $PUERTO &
if [ "$DNS" = "192.168.0.1" -o "$DNS" = "192.168.0.2" ];
then
   #echo 'sudo (eecho openvpn --config $CONFIG --remote '$MAQUINA $PUERTO $3 '--proto tcp-client > '$FICH_ggggg') &'
   (sudo eecho openvpn --config $CONFIG --remote $MAQUINA $PUERTO $3 --proto tcp-client > $FICH_SALIDA) &
elif [ "$3" != "" ];
then
   (sudo eecho openvpn --config $CONFIG --remote $MAQUINA $PUERTO --proto $3 > $FICH_SALIDA)  &
else
   #echo 'sudo (eecho openvpn --config $CONFIG --remote '$MAQUINA $PUERTO '> '$FICH_SALIDA') &'
   (sudo eecho openvpn --config $CONFIG --remote $MAQUINA $PUERTO > $FICH_SALIDA) &
fi  
sleep 5
#sudo eecho 172.16.1.9 iesinclan.dyndns.org >> /etc/hosts
sudo /m/Mios/prj/scripts/quitaLinea.sh /etc/hosts iesinclan.dyndns.org
sudo bash -c 'echo 172.16.1.9 iesinclan.dyndns.org inclan.tk mail.inclan.tk www.inclan.tk irc.inclan.tk ftp.inclan.tk>> /etc/hosts'
sudo /m/Mios/prj/scripts/quitaLinea.sh /etc/hosts squid
sudo /m/Mios/prj/scripts/squid.sh 172.16.1.9
ping -c 4 squid &> /dev/null
if [ $? -le 0 ];
then
    echo sudo bash -c 'echo nameserver 172.16.1.9 > /etc/resolv.conf'
fi    


