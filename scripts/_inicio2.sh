#!/bin/bash
#comandos como root
apagaWifi() {
    for i in $(interfaces | grep -i wlan);
    do
       eecho sudo ifconfig $i down
    done   
    sudo mata wpa_supplicant &> /dev/null
}

configEth() {
    #apagaWifi
    sudo firewall
    sudo ifconfig $ETH_IP up
    sudo ifconfig $IFACE:1 172.16.254.7/24
    sudo route add default gw $DEFAULT_GW
    sudo rm /etc/resolv.conf
    for i in $DEFAULT_DNS; do
       sudo bash -c 'echo nameserver '$i' >> /etc/resolv.conf'
    done
    #sudo bash -c 'echo nameserver 8.8.8.8 >> /etc/resolv.conf'
    #/m/Mios/prj/scripts/team.sh &>/dev/null &
}

nameservers() {
   sudo bash -c 'echo nameserver 172.16.1.9 > /etc/resolv.conf'
   sudo bash -c 'echo nameserver 8.8.8.8 >> /etc/resolv.conf'
}

vpnConnect() {
    echo "Parámetros: "$*
    if [ "$2" = "" ];
    then
       echo Error en número de parametros vpnConnect
       exit 1
    fi
    TEMP="/tmp/openvpn-$1-$2.tmp"
    if [ "$1" = "insti" ];
    then
       IP_INSTI=$(digHost 8.8.8.8 iesinclan.tk)
       COMMAND='sudo vpn 443 '$IP_INSTI' '$2
    else
       VPN_BOOK_CONF="/home/usuario/freeVpns/book/$1/vpnbook-"$1"-"$2
       if [ "$2" = "tcp" ];
       then
         COMMAND='sudo openvpn --config '$VPN_BOOK_CONF'443.ovpn'
       else
          COMMAND='sudo openvpn --config '$VPN_BOOK_CONF'25000.ovpn'
       fi
    fi
    while [ 1 ];
    do
       #(sudo openvpn --config $VPN_BOOK_CONF$2"25000.ovpn" > $TEMP) &
       echo $COMMAND
       ( $COMMAND > $TEMP ) &
       CONT=1
       echo .
       while [  ! -f $TEMP  -o "$(cat $TEMP | wc -l)" -lt 18 -a "$(cat $TEMP | grep 'fatal error')" = "" ];
       do
         printf "\rEsperando salida de openvpn: $CONT"
         CONT=$(($CONT +1))
         sleep 1
       done
       if [ "$(cat $TEMP | grep AUTH_FAILED)" != "" -o "$(cat $TEMP | grep 'fatal error')" != "" ];
       then
         echo Falló la autenticación 
         sudo bash -c 'echo nameserver 8.8.8.8 > /etc/resolv.conf' 
         echo DNS: $(cat /etc/resolv.conf)
         echo Descargando contraseña vpnbook':'
         /m/Mios/prj/scripts/bookPass.sh
       else
         echo La autenticación fue correcta
         cat $TEMP
         break
       fi
    done
    echo Terminado: vpnConnect
}
vpn() {
   vpnConnect $VPN_BOOK_RED udp
   sleep 4
   echo Conectando a Instituto por vpn
   if [ "$VPN_BOOK_RED" = "euro1" -o "$VPN_BOOK_RED" = "euro2" -o "VPN_BOOK_RED" = "de233" ];
   then
      #sudo vpn 443 $(digHost 8.8.8.8 iesinclan.tk) udp &
      vpnConnect insti udp 
   else
      #sudo vpn 443 &
      vpnConnect insti tcp
   fi
}   
#apagamos bluetooth
#sudo hciconfig hci0 down
sudo rfkill block bluetooth
#quitamos proxy 
gsettings set org.gnome.system.proxy mode 'none'
#montamos /m/Mios encriptada
while [ ! -f /m/Mios/prj/scripts/redJunta.sh ];
do
    sudo encfs --public /m/.Mios /m/Mios
done
#Parámetros config eth
IFACE=$(interfaces | grep eth | grep -v grep | head -1)
ETH_IP="$IFACE 192.168.1.249 netmask 255.255.255.0"
DEFAULT_GW=192.168.1.1
DEFAULT_DNS='8.8.8.8 8.8.4.4'

#Parámetros de vpnbook
if [ "$1" != "" ];
then
   VPN_BOOK_RED=$1
else   
   VPN_BOOK_RED="de233"
fi

#apagaWifi
#sudo /m/Mios/prj/scripts/hwEther.sh
#sudo /sbin/start network-manager
sudo service teamviewerd stop
DONDE=$(menu Casa Ciclos ESO Wifi CasaCable CasaVpn Tic CasaWifi)
echo has elegido $DONDE
if [ "$DONDE" != "9" ];
then
   sudo /usr/sbin/service network-manager stop
   sudo /usr/sbin/service avahi-daemon stop
   sudo /m/Mios/prj/scripts/mata.sh wpa_supplicant &> /dev/null
   apagaWifi
fi
sudo /m/Mios/prj/scripts/vpn.sh stop &> /dev/null
case $DONDE in
    1) #Casa
       sudo /m/Mios/prj/scripts/redJunta.sh
       sudo firewall
       ;;
    2) #Ciclos
       sudo /m/Mios/prj/scripts/redInstiCable.sh
       /usr/bin/x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
       sudo firewall
       ;;
    3) #ESO
       eecho sudo wpa_supplicant -B -i $(interfaces | grep wlan  | grep -v grep) -c /m/Mios/Personal/wpa/TALLER-INFORMATICA2 &
       eecho sudo ifconfig $(interfaces | grep -v grep | grep -i wlan | head -1) 172.16.254.6
       eecho sudo route add default gw 172.16.254.254
       /usr/bin/x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
       ;;
    4) #Wifi
       menuWpa
       ;;
    5) #CasaCable
       configEth
       #/m/Mios/prj/scripts/tunelCjb.sh &
       sudo rfkill block 0
       sudo hciconfig phy0 down
       vpn
       ;;
    6) #CasaVpn
       configEth
       #sudo openvpn --config /home/usuario/freeVpns/book/uk1/vpnbook-uk1-tcp443.ovpn &
       #sudo openvpn --config /home/usuario/freeVpns/book/euro2/vpnbook-euro2-25000.ovpn &
       sudo openvpn --config $VPN_BOOK_CONF"udp25000.ovpn" &
       sleep 15
       sudo vpn 443 &
       #sudo eecho /m/Mios/prj/scripts/ukVpn.sh &
       ;;
    7) #Tic
       ETH_IP="$IFACE 192.168.13.111 netmask 255.255.248.0"
       DEFAULT_GW=192.168.8.1
       DEFAULT_DNS='192.168.0.1 192.168.0.2'
       
       configEth
       
       sudo route add f0 gw 192.168.8.1
       ##SHELL_CJB_NET=$(digHost f0 shell.cjb.net)
       ##sudo route add $SHELL_CJB_NET gw 192.168.8.1
       
       /m/Mios/prj/scripts/bookPass.sh
       #sudo openvpn --config /home/usuario/freeVpns/book/us2/vpnbook-us2-tcp443.ovpn &
       #sudo openvpn --config $VPN_BOOK_CONF"tcp443.ovpn" &
       vpnConnect $VPN_BOOK_RED tcp
       sleep 4
       echo Conectando a Instituto por vpn
       if [ "$VPN_BOOK_RED" = "euro1" -o "$VPN_BOOK_RED" = "euro2" ];
       then
          #sudo vpn 443 $(digHost 8.8.8.8 iesinclan.tk) udp &
          vpnConnect insti udp 
       else
          #sudo vpn 443 &
          vpnConnect insti tcp
       fi
       echo cambiando dns
       nameservers
       ifconfig $(interfaces | grep wlan  | grep -v grep)
       route -n
       cat /etc/resolv.conf
       ping -c 2 $(tail -2 /etc/resolv.conf | head -1 | awk '{print $2;}')
       ping -c 2 $(tail -1 /etc/resolv.conf | awk '{print $2;}')
       echo "Dir. IP.: $(dirIp2)" $(pais)
       ;;
    8) #CasaWifi
       #sudo /sbin/start network-manager
       WIFACE=$(interfaces | grep wlan | grep -v grep | tail -1)
       sudo ifconfig $WIFACE up
       ###sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/008ef2c6fdca -Dwext &
       #sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/2cb05dc5cdea -Dwext &
       #sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/72e87bad47a4 -Dwext &
       #eecho sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/9c80df44c9d4 &
       #sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/wpa/JAZZTEL2013 -Dwext &
       #sudo wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/wpa/Orange-C9D2 -Dwext &
       #sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/9c80df44c9d4 -Dwext &
       #sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/001310151cf7 -Dwext &
       sudo /sbin/ifconfig $WIFACE up
       echo "iwlist: :" "(sudo iwlist $WIFACE scan | grep Orange-C7C5)"
       echo "iwlist: :" "$(sudo iwlist $WIFACE scan | grep Orange-C7C5)"
       echo "iwlist: :" "$(sudo iwlist $WIFACE scan | grep Orange-B215)"
       RED=192.168.1
       if [ "$(sudo iwlist $WIFACE scan | grep Orange-C7C5)" != "" ]; then
          sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/00173f54fcc2 -Dwext
       elif [ "$(sudo iwlist $WIFACE scan | grep Orange-B215)" != "" ]; then
          sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/1cc63c66b217 -Dwext
       elif [ "$(sudo iwlist $WIFACE scan | grep JAZZTEL_FCC0)" != "" ]; then
          RED=192.168.1
          sudo wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/00173f54fcc2 -Dwext
       fi
       #sudo dhclient $WIFACE &
       #configEth
       
       sudo firewall
       sudo ifconfig $WIFACE $RED.27
       sudo route add default gw $RED.1
       #sleep 10
       #/m/Mios/prj/scripts/bookPass.sh
       ###vpn
       sudo openvpn --config /home/usuario/freeVpns/book/de233/vpnbook-de233-udp25000.ovpn &
       echo cambiando dns
       nameservers
       ifconfig $(interfaces | grep wlan  | grep -v grep)
       route -n
       cat /etc/resolv.conf
       ping -c 2 $(tail -2 /etc/resolv.conf | head -1 | awk '{print $2;}')
       ping -c 2 $(tail -1 /etc/resolv.conf | awk '{print $2;}')
       echo "Dir. IP.: $(dirIp2)" $(pais)
       ;;
esac
dirIp > /tmp/dirIp
#como usuario usuario
cumples &
if [ -d /m/Mios/Instituto ];
then
    #eecho dropbox start -i
    /home/usuario/.dropbox-dist/dropboxd &
else
    echo no se inicia dropbox no está montada la unidad /m
fi    
sleep 5
sudo actualiza &
/m/Mios/prj/scripts/dynDns.sh
/m/Mios/prj/scripts/dnsexit.sh ubuntu64.linkpc.net
/m/Mios/prj/scripts/dnsexit.sh iesinclan.linkpc.net
/m/Mios/prj/scripts/dnsexit.sh avatar.linkpc.net
#/usr/bin/qbittorrent &
#java -jar /m/jdown/JDownloader.jar &
#mata xflux
#xflux -l 40 &
gedit /m/Mios/Personal/Privado/PENDIENTE.txt &
#sudo -u usuario gnome-terminal &
#ssh fjcn@shell.cjb.net
#sudo alive &
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

