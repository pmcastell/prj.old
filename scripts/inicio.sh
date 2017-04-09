#!/bin/bash

RESOLV="/run/resolvconf/resolv.conf"

debug() {
  echo $1 > /dev/stderr
}  

################################################################################################################################
pararServicios() {
   SERVICIOS="network-manager avahi-daemon teamviewerd wpa_supplicant nmbd smbd apache2 mysql rsync squid3 dhclient"
   for s in $SERVICIOS; do
      sudo /usr/sbin/service $s stop &> /dev/null
   done
   sudo /m/Mios/prj/scripts/mata.sh wpa_supplicant &> /dev/null
}
################################################################################################################################ 
nameservers() {
   sudo rm $RESOLV
   for i in $1; do
      sudo bash -c 'echo nameserver '$i' >> '$RESOLV
   done #sudo bash -c 'echo nameserver 172.16.1.9 > '$RESOLV #sudo bash -c 'echo nameserver 8.8.8.8 >> '$RESOLV
   route -n
   cat $RESOLV
   ping -c 2 $(cat $RESOLV | grep nameserver | head -1 | awk '{print $2;}')
   ping -c 2 $(cat $RESOLV | grep nameserver | tail -1 | awk '{print $2;}')
   echo "Dir. IP.: $(dirIp2)" $(pais)
}
################################################################################################################################
configEth() {
    if [ "$2" = "DHCP" ]; then (sudo dhclient $1 &); return; fi
    IFACE=$1; IP=$2; MASK=$3; GW=$4; DNS="$5"
    if [ "$DNS" = "" ]; then DNS="8.8.8.8 8.8.4.4"; fi
    sudo eecho ifconfig $IFACE $IP/$MASK up
    sudo eecho route add default gw $GW
    nameservers "$DNS"
    sudo firewall
}

################################################################################################################################
vpnConnect() {
    echo "Parámetros: "$*
    if [ "$2" = "" ]; then
       echo Error en número de parametros vpnConnect
       exit 1
    fi
    TEMP="/tmp/openvpn-$1-$2.tmp"
    if [ "$1" = "insti" ]; then
       IP_INSTI=$(digHost 8.8.8.8 iesinclan.tk)
       COMMAND='sudo vpn 443 '$IP_INSTI' '$2
    else
       VPN_BOOK_CONF="/home/usuario/freeVpns/book/$1/vpnbook-"$1"-"$2
       if [ "$2" = "tcp" ]; then
         COMMAND='sudo openvpn --config '$VPN_BOOK_CONF'443.ovpn'
       else
          COMMAND='sudo openvpn --config '$VPN_BOOK_CONF'25000.ovpn'
       fi
    fi
    while true; do
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
       if [ "$(cat $TEMP | grep AUTH_FAILED)" != "" -o "$(cat $TEMP | grep 'fatal error')" != "" ]; then
         echo Falló la autenticación 
         sudo bash -c 'echo nameserver 8.8.8.8 > '$RESOLV 
         echo DNS: $(cat $RESOLV)
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
################################################################################################################################
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
################################################################################################################################
casaBiblio() {
   if [ "$(interfaces | grep wlan | wc -l)" -gt 1 ]; then
      WIFACES=""
      for w in $(interfaces | grep wlan); do
         WIFACES="$WIFACES $w"
      done
      NIFACE=$(menu $WIFACES)
      WIFACE=$(interfaces | grep wlan | head -$NIFACE | tail -1)
   else
      WIFACE=$(interfaces | grep wlan | tail -1)
   fi
   RED=192.168.1
   sudo eecho mata wpa_supplicant &>/dev/null
   sudo ifconfig $WIFACE up
   if [ "$DONDE" = "9" ]; then 
      sudo eecho wpa_supplicant -B -i $WIFACE  -c /var/lib/wicd/configurations/5057a8671925 -Dwext & #/m/Mios/.../wicd/BIBLIO1 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep MOVISTAR_E360)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_E360 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep Orange-B215)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/1cc63c66b217 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep JAZZTEL_FCC0)" != "" ]; then
      sudo wpa_supplicant -B -i wlan20 -c /var/lib/wicd/configurations/00173f54fcc2 -Dwext
      #sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/mio -Dwext &
   fi
   if [ "$DONDE" = "9" ]; then configEth $WIFACE DHCP; 
   else configEth $WIFACE "$RED.27" 24 "$RED.1"; ###sudo ifconfig $IFACE:1 172.16.254.7/24; 
   fi
   ULTIMA_OVPN=$(cat /home/usuario/.bash_history | grep 'sudo openvpn --config' | tail -1)
   if [ "$ULTIMA_OVPN" = "" ]; then
      sudo openvpn --config /home/usuario/freeVpns/book/de233/vpnbook-de233-udp25000.ovpn &
   else
      sudo eecho $ULTIMA_OVPN &
   fi  
   sudo /home/usuario/aula/torRoute.sh $WIFACE >/dev/null &
   sudo firewall #echo cambiando dns #nameservers
   ifconfig $WIFACE
}
################################################################################################################################
#if [ "$(whoami)" != "root" ]; then
#   sudo $0 $*
#   exit
#fi   
#apagamos bluetooth ##sudo hciconfig hci0 down
sudo rfkill block bluetooth
#quitamos proxy 
gsettings set org.gnome.system.proxy mode 'none'
#montamos /m/Mios encriptada
while [ ! -f /m/Mios/prj/scripts/redJunta.sh ]; do
   sudo encfs --public /m/.Mios /m/Mios
done
#Parámetros de vpnbook
if [ "$1" != "" ]; then VPN_BOOK_RED=$1; else VPN_BOOK_RED="de233"; fi
sudo /m/Mios/prj/scripts/hwEther.sh
DONDE=$(menu Casa Ciclos ESO Wifi CasaCable CasaVpn Tic CasaWifi Biblioteca)
pararServicios
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
       configEth $(interfaces | grep eth | head -1) 192.168.1.249 24 192.168.1.1
       #/m/Mios/prj/scripts/tunelCjb.sh &
       sudo rfkill block 0
       sudo hciconfig phy0 down
       vpn
       ;;
    6) #CasaVpn
       configEth $(interfaces | grep eth | head -1) 192.168.1.249 24 192.168.1.1
       #sudo openvpn --config /home/usuario/freeVpns/book/uk1/vpnbook-uk1-tcp443.ovpn &
       #sudo openvpn --config /home/usuario/freeVpns/book/euro2/vpnbook-euro2-25000.ovpn &
       sudo openvpn --config $VPN_BOOK_CONF"udp25000.ovpn" &
       sleep 15
       sudo vpn 443 &
       #sudo eecho /m/Mios/prj/scripts/ukVpn.sh &
       ;;
    7) #Tic
       configEth $(interfaces | grep eth | head -1) 192.168.13.111 255.255.248.0 192.168.8.1 '192.168.0.1 192.168.0.2'
       sudo route add f0 gw 192.168.8.1 ##SHELL_CJB_NET=$(digHost f0 shell.cjb.net)  ##sudo route add $SHELL_CJB_NET gw 192.168.8.1
       /m/Mios/prj/scripts/bookPass.sh #sudo openvpn --config $VPN_BOOK_CONF"tcp443.ovpn" &
       vpnConnect $VPN_BOOK_RED tcp
       sleep 4
       echo Conectando a Instituto por vpn
       if [ "$VPN_BOOK_RED" = "euro1" -o "$VPN_BOOK_RED" = "euro2" ]; then
          #sudo vpn 443 $(digHost 8.8.8.8 iesinclan.tk) udp &
          vpnConnect insti udp 
       else
          #sudo vpn 443 &
          vpnConnect insti tcp
       fi
       ifconfig $(interfaces | grep wlan )
       echo cambiando dns
       nameservers "172.16.1.9 8.8.8.8"
       ;;
    8|9) #CasaWifi o Biblioteca wifi
       casaBiblio
       ;;
esac
#dirIp > /tmp/dirIp
#como usuario usuario
cumples &
if [ -f /m/Mios/Instituto/JefeDep.7z ]; then #eecho dropbox start -i
    /home/usuario/.dropbox-dist/dropboxd &
else
    echo no se inicia dropbox no está montada la unidad /m
fi    
sleep 5
sudo actualiza &
#/m/Mios/prj/scripts/dynDns.sh
wget -O - "https://reg6543:basura68@dynupdate.no-ip.com/nic/update?hostname=ubu.noip.me&myip=$(dirIp)"
/m/Mios/prj/scripts/dnsexit.sh ubuntu64.linkpc.net
/m/Mios/prj/scripts/dnsexit.sh iesinclan.linkpc.net
/m/Mios/prj/scripts/dnsexit.sh avatar.linkpc.net
/m/Mios/prj/scripts/dnsexit.sh ubuin.linkpc.net
#/usr/bin/qbittorrent &>/dev/null &
java -jar /m/jdown/JDownloader.jar&>/dev/null &
#mata xflux
#xflux -l 40 &
sudo -u usuario gedit /m/Mios/Personal/Privado/PENDIENTE.txt &
#sudo -u usuario gnome-terminal &
#ssh fjcn@shell.cjb.net
sudo alive &> /dev/null &
sudo -u usuario $SHELL



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

