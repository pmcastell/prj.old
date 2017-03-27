#!/bin/bash
#comandos como root
sudo /etc/init.d/network-manager start
while [ ! -f /m/Mios/prj/scripts/redJunta.sh ];
do
    sudo encfs --public /m/.Mios /m/Mios
done
DONDE=0
OPCIONES="1)Casa 2)Ciclos 3)ESO 4)Wifi"
while [ $DONDE -lt 1 -o $DONDE -gt  $(echo $OPCIONES | wc -w) ];
do
   clear
   for i in $OPCIONES; do
      echo $i
   done
   read DONDE
   echo has elegido: $DONDE
done
sudo /etc/init.d/network-manager stop
sudo /etc/init.d/avahi-daemon stop
sudo mata wpa_supplicant
if [ "$DONDE" == "1" ];
then
   sudo /m/Mios/prj/scripts/redJunta.sh
   sudo firewall
elif [ "$DONDE" == "2" ];
then
   sudo /m/Mios/prj/scripts/redInstiCable.sh
   sudo firewall
elif [ "$DONDE" == "3" ];
then   
   eecho sudo wpa_supplicant -B -i $INTERFAZ -c /m/Mios/Personal/wpa/TALLER-INFORMATICA &
   eecho sudo ifconfig $(interfaces | grep -v grep | grep -i wlan | head -1) 172.16.254.6
   eecho sudo route add default gw 172.16.254.254
   x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
elif [ "$DONDE" == "4" ];
then
   menuWpa
fi   
#como usuario usuario
cumples &
if [ -d /m/Mios/Instituto ];
then
    dropbox start -i
else
    echo no se inicia dropbox no está montada la unidad /m
fi    
/usr/bin/qbittorrent &
#java -jar /m/jdown/JDownloader.jar &
#mata xflux
#xflux -l 40 &
gedit /m/Mios/Personal/Privado/PENDIENTE.txt &
#sudo -u usuario gnome-terminal &
#ssh fjcn@shell.cjb.net
sudo alive &
    
$SHELL
#######################################################################################
############### cuidado! no poner nada debajo, no se ejecutará ########################
#######################################################################################



#if [ "$DONDE" == "Casa" ];
#theen
#   sudo vpn
#fi


#sudo ifconfig wlan11 up
#CASA=$(sudo iwlist wlan11 scanning | grep NAN)
#if [ "$CASA" != "" ];
#then
#    DONDE=Casa
#    echo estamos en casa
#fi    


#gksudo 'gnome-terminal -e encfs --public /m/.Mios /m/Mios' &
#gksudo  /m/Mios/prj/scripts/redJunta.sh
###gksudo 'gnome-terminal -e /home/usuario/inicio.py'



#ETH=$(interfaces | grep eth | grep -v grep | head -1)
#if [ "$ETH" == "eth15" ];
#then 
#   DONDE=Casa
#else
#   DONDE=Insti
#fi  
#echo if [ "$DONDE" == "Casa" ];

