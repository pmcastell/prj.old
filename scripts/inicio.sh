#!/bin/bash

RESOLV="/run/resolvconf/resolv.conf"

debug() {
  echo $1 > /dev/stderr
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
pararServicios() {
   SERVICIOS="network-manager avahi-daemon teamviewerd wpa_supplicant nmbd smbd apache2 mysql rsync squid3 dhclient"
   for s in $SERVICIOS; do
      sudo /usr/sbin/service $s stop &> /dev/null
   done
   sudo /m/Mios/prj/scripts/mata.sh wpa_supplicant &> /dev/null
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
ipConfig() {
   if [ "$2" = "DHCP" ]; then (sudo dhclient $1 &); return; fi
   if [ "$1" != "" ] && [ "$2" != "" ] && [ "$3" != "" ] && [ "$4" != "" ]; then
      IFACE=$1; IP_ADDR="$2/$3"; GW=$4; 
      #MASK=$3; DNS="$5"
   else
      IFACE="$(ip a | grep -E '^[0-9]+' | grep -v "lo:" | awk -F':' '{print $2;}' | grep -v tap | head -1)"
      [ "$(ip a | grep '1c:1b:0d:0d:2d:71')" != "" ] && IP_ADDR="172.124.117.100/16"  || IP_ADDR="172.124.117.99/16" 
      GW="172.124.1.10"
   fi
   for IP in $(ip a show dev $IFACE | grep inet | awk '{print $2;}'); do sudo ip a del $IP dev $IFACE; done
   sudo ip a add $IP_ADDR dev $IFACE
   sudo ip route add default via $GW
   sudo sed -i '/ server/d' /etc/hosts
   sudo bash -c "echo $(echo $IP_ADDR | awk -F'/' '{print $1;}')     server >> /etc/hosts"
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
   sudo /m/Mios/prj/scripts/ponerRandomMAC.sh $WIFACE
   RED=192.168.1
   sudo mata wpa_supplicant &>/dev/null
   sudo ifconfig $WIFACE up
   if [ "$DONDE" = "9" ]; then 
      sudo eecho wpa_supplicant -B -i $WIFACE  -c /var/lib/wicd/configurations/5057a8671925 -Dwext & #/m/Mios/.../wicd/BIBLIO1 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep MiCasa)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MiCasa -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep vodafone53D2)" != "" ]; then
      RED=192.168.0
      sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/vodafone53D2 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep MOVISTAR_E360)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_E360 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep MARINA24)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/f863949068f3 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep Orange-B215)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/1cc63c66b217 -Dwext &
   elif [ "$(sudo iwlist $WIFACE scan | grep JAZZTEL_FCC0)" != "" ]; then
      sudo eecho wpa_supplicant -B -i $WIFACE -c /var/lib/wicd/configurations/00173f54fcc2 -Dwext &
      #sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/mio -Dwext &
   fi
   if [ "$DONDE" = "9" ]; then ipConfig $WIFACE DHCP; 
   else ipConfig $WIFACE "$RED.25" 24 "$RED.1"; ###sudo ifconfig $IFACE:1 172.16.254.7/24; 
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
comun() {
   if [ "$DONDE" != "2" ]; then 
      cumples &
      [ "$(ps aux | grep 'indicator-brightness' | grep -v grep)" = "" ] && /usr/bin/python /opt/extras.ubuntu.com/indicator-brightness/indicator-brightness &
      sudo -u usuario gedit /m/Mios/Personal/Privado/PENDIENTE.txt 2>&1 > /dev/null &
   fi
   [ "$(ps aux | grep -i icewm | grep -v grep)" != "" ] && (mate-volume-control-applet &) && (orage&)
   if [ -f /m/Mios/Instituto/JefeDep.7z ]; then #eecho dropbox start -i
       /home/usuario/.dropbox-dist/dropboxd &
   else
       echo no se inicia dropbox no está montada la unidad /m
   fi    
   mount /l
   #sleep 3
   sudo /scripts/tap0.sh
   for host in 'ubu.noip.me' 'ubuin.ddns.net' 'ubuin.hopto.org'; do
       wget -O - "https://reg6543:basura68@dynupdate.no-ip.com/nic/update?hostname=$host&myip=$(dirIp)" 2>/dev/null &
   done
   /m/Mios/prj/scripts/dnsexit.sh ubuin.linkpc.net &
   /m/Mios/prj/scripts/duckdns.sh ubuin $(realIp) &
}

################################################################################################################################
#if [ "$(whoami)" != "root" ]; then
#   sudo $0 $*
#   exit
#fi   
#apagamos bluetooth ##sudo hciconfig hci0 down
#sudo rfkill block bluetooth
#quitamos proxy 
gsettings set org.gnome.system.proxy mode 'none'
#montamos /m/Mios encriptada
while [ ! -f /m/Mios/prj/scripts/redJunta.sh ]; do
   sudo encfs --public /m/.Mios /m/Mios
done
[ "$(pgrep autokey-gtk)" = "" ] && (autokey-gtk &>/dev/null &)
#Parámetros de vpnbook
if [ "$1" != "" ]; then VPN_BOOK_RED=$1; else VPN_BOOK_RED="de233"; fi
#sudo /m/Mios/prj/scripts/hwEther.sh
sudo ls &> /dev/null
sudo pararServicios &
DONDE=$(menu Casa Ciclos ESO Wifi CasaCable CasaVpn Tic CasaWifi Biblioteca)
sudo /m/Mios/prj/scripts/vpn.sh stop &> /dev/null
case $DONDE in
    1) #Casa
       sudo /m/Mios/prj/scripts/redJunta.sh
       sudo firewall
       ;;
    2) #Ciclos
       ###sudo /m/Mios/prj/scripts/redInstiCable.sh
       sudo killall dhclient
       ipConfig
       sudo firewall
       comun &
       sudo /etc/init.d/epoptes start
       #/usr/bin/epoptes &
       /usr/bin/x11vnc -rfbport 5900 -reopen -viewonly -shared  -forever -loop 2>&1 > /dev/null &
       sleep 30
       /scripts/epoptesInsti.sh &
       $SHELL 
       exit 0
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
       comun &
       #deshabilitar el botón de apertura del grabador de dvd
       /usr/bin/eject -i on /dev/sr0
       sudo alive &> /dev/null &
       #deshabilitar el botón de apertura del grabador de dvd
       /usr/bin/eject -i on /dev/sr0
       java -jar /m/jdown/JDownloader.jar &> /dev/null &
       /usr/bin/qbittorrent &>/dev/null &
       sudo actualiza 
       ;;
esac
TMP_DIRIP="/tmp/direccionIpReal.txt"
while true; do
   DIR_IP=$(dirIp)
   echo "DIR_IP: $DIR_IP"
   echo -n $DIR_IP > $TMP_DIRIP
   if [ "$(cat $TMP_DIRIP | egrep -o '([0-9]{1,3}\.){3}[0-9]{1,3}')" != "" ]; then break; fi
done    
sudo -u usuario $SHELL

#######################################################################################
############### cuidado! no poner nada debajo, no se ejecutará ########################
#######################################################################################



################################################################################################################################ 



#/m/Mios/prj/scripts/dynDns.sh

#/m/Mios/prj/scripts/dnsexit.sh ubuntu64.linkpc.net &
#/m/Mios/prj/scripts/dnsexit.sh iesinclan.linkpc.net &
#/m/Mios/prj/scripts/dnsexit.sh avatar.linkpc.net &

#/usr/bin/qbittorrent &>/dev/null &
#java -jar /m/jdown/JDownloader.jar&>/dev/null &
#mata xflux
#xflux -l 40 &

#sudo -u usuario gnome-terminal &
#ssh fjcn@shell.cjb.net



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

