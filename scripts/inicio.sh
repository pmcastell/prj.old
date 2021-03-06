#!/bin/bash

main() {
    #apagamos bluetooth ##sudo hciconfig hci0 down
    sudo rfkill block bluetooth
    #quitamos proxy 
    /usr/bin/gsettings set org.gnome.system.proxy mode 'none'
    setVars #establecer la variable RESOLV y SCRIPTS
    #[ "$(pgrep autokey-gtk)" = "" ] && (autokey-gtk &>/dev/null &)
    #Parámetros de vpnbook
    if [ "$1" != "" ]; then VPN_BOOK_RED=$1; else VPN_BOOK_RED="ca222"; fi
    #sudo $SCRIPTS/hwEther.sh
    sudo ls &> /dev/null
    pararServicios &
    DONDE=$($SCRIPTS/menu.sh Casa Ciclos ESO Wifi CasaCable CasaVpn CasaAp CasaWifi Biblioteca Tic)
    sudo $SCRIPTS/vpn.sh stop &> /dev/null
    case $DONDE in
        1) #Casa
           sudo $SCRIPTS/redJunta.sh
           sudo $SCRIPTS/firewall.sh
           ;;
        2) Instituto
           ;;
        3) #ESO
           eecho sudo wpa_supplicant -B -i $(interfaces | grep wlan  | grep -v grep) -c /m/Mios/Personal/wpa/TALLER-INFORMATICA2 &
           eecho sudo ifconfig $(interfaces | grep -v grep | grep -i wlan | head -1) 172.16.254.6
           eecho sudo route add default gw 172.16.254.254
           /usr/bin/x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
           ;;
        4) #Wifi
           $SCRIPTS/menuWpa.sh
           ;;
        5) #CasaCable
           configEth $(interfaces | grep eth | head -1) 192.168.1.249 24 192.168.1.1
           #$SCRIPTS/tunelCjb.sh &
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
           #sudo eecho $SCRIPTS/ukVpn.sh &
           ;;
        7) casaAp
           ;;
        8|9) #CasaWifi o Biblioteca wifi
           casaBiblio
           comun &
           #deshabilitar el botón de apertura del grabador de dvd
           sudo /usr/bin/eject -i on /dev/sr0
           sudo alive &> /dev/null &
           #deshabilitar el botón de apertura del grabador de dvd
           sudo /usr/bin/eject -i on /dev/sr0
           java -jar /m/jdown/JDownloader.jar &> /dev/null &
           /usr/bin/qbittorrent &>/dev/null &
           #sudo actualiza 
           ;;
        10) #Tic
           configEth $(interfaces | grep eth | head -1) 192.168.13.111 255.255.248.0 192.168.8.1 '192.168.0.1 192.168.0.2'
           sudo route add f0 gw 192.168.8.1 ##SHELL_CJB_NET=$(digHost f0 shell.cjb.net)  ##sudo route add $SHELL_CJB_NET gw 192.168.8.1
           $SCRIPTS/bookPass.sh #sudo openvpn --config $VPN_BOOK_CONF"tcp443.ovpn" &
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
    esac
    TMP_DIRIP="/tmp/direccionIpReal.txt"
    while true; do
       DIR_IP=$(realIp)
       echo "DIR_IP: $DIR_IP"
       echo -n $DIR_IP > $TMP_DIRIP
       if [ "$(cat $TMP_DIRIP | egrep -o '([0-9]{1,3}\.){3}[0-9]{1,3}')" != "" ]; then break; fi
       sleep 1
    done    
    sudo -u usuario $SHELL
}           
debug() {
  echo $1 > /dev/stderr
}  
mensaje() {
    MENSA="$@"
    $SCRIPTS/hablaf.sh -n "$MENSA" &
    zenity --question --width 800 --height 200 --text "\n\n\n<b><span foreground='blue' font='24'>$MENSA</span></b>" --ok-label="No" --cancel-label="Si"
    [ $? -eq 0 ] && echo false || echo true
}
###########################################################################################
casaAp() {
    WIFACE=$(wifiInterface)
    sudo $SCRIPTS/ponerRandomMAC.sh $WIFACE
    INFO="$(sudo wpaWifi -i $WIFACE)"; RED="$(echo $INFO | awk '{print $1;}')"; 
    ESSID="$(echo $INFO | awk '{print $3;}')"; CHANNEL=""
###    CHANNEL=$(echo $INFO | awk '{print $2;}')
    while true; do 
        CHANNEL="$(sudo iwlist wlan0 scan essid MiCasa 2>/dev/null | grep -B 4 $ESSID | head -1 | awk -F':' '{print $2;}')"
        [ "$CHANNEL" != "" ] && break
    done
####    sudo $SCRIPTS/iwApd.sh $WIFACE $CHANNEL "UbuPort" "172.18.1"
    wpaWifi $WIFACE
    ipConfig $WIFACE "${RED}.25" 24 "$RED.1"
    ifconfig $WIFACE    
    comun &
    ###sudo /home/usuario/aula/torRoute.sh $WIFACE >/dev/null &
    #sudo firewall #echo cambiando dns #nameservers
    sudo /usr/bin/eject -i on /dev/sr0
    sudo alive &> /dev/null &
    #deshabilitar el botón de apertura del grabador de dvd
    sudo /usr/bin/eject -i on /dev/sr0
    java -jar /home/usuario/programas/JDownloader/JDownloader.jar &> /dev/null &
    /usr/bin/qbittorrent &>/dev/null &
    #sudo actualiza 
}           
###########################################################################################
setVars() {
    export RESOLV="/run/resolvconf/resolv.conf"
    if [ "$(basename $(dirname $0))" = "scripts" ]; then
       SCRIPTS="$(dirname $0)"
    elif [ -e /scripts ]; then
       SCRIPTS="/scripts"
    elif [ -e $HOME/Dropbox/Instituto/prj/scripts ]; then
       SCRIPTS="$HOME/Dropbox/Instituto/prj/scripts"
    elif [ -e /m/Mios/prj/scripts ]; then
       SCRIPTS="/m/Mios/prj/scripts"
    elif [ -e /m/Mios/Instituto/prj/scripts ]; then
       SCRIPTS="/m/Mios/Instituto/prj/scripts"
    elif [ -e /mnt/m/Mios/Instituto/prj/scripts ]; then
       SCRIPTS="/mnt/m/Mios/Instituto/prj/scripts"    
    else
       echo "No encuentro la carpeta scripts"
       exit 2
    fi 
    export SCRIPTS="$SCRIPTS"
}
###########################################################################################
Instituto() {
    #Ciclos
    ###sudo $SCRIPTS/redInstiCable.sh
    /scripts/aula/espejo.sh off
    sudo killall dhclient
    ipConfig
    sudo firewall
    comun &
    [ "$(mensaje 'Cargar epoptes???')" = "true" ] && startEpoptes && sleep 3 && (/usr/bin/epoptes &) && xrandr -s 1440x900
    [ "$(mensaje 'Cargar x11vnc???')" = "true" ] && (/usr/bin/x11vnc -rfbport 5900 -reopen -viewonly -shared  -forever -loop 2>&1 > /dev/null &)
    firefox &
    #/scripts/epoptesInsti.sh &
    $SHELL 
    exit 0
}
###########################################################################################
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
###########################################################################################
pararServicios() {
   SERVICIOS="network-manager avahi-daemon teamviewerd wpa_supplicant nmbd smbd apache2 mysql rsync squid3 dhclient"
   for s in $SERVICIOS; do
      #sudo /usr/sbin/service $s stop &> /dev/null
      sudo /bin/systemctl stop $SERVICIOS &> /dev/null
   done
   sudo $SCRIPTS/mata.sh wpa_supplicant &> /dev/null
}
#############/////##############################################################################
configEth() {
    if [ "$2" = "DHCP" ]; then (sudo dhclient $1 &); return; fi
    IFACE=$1; IP=$2; MASK=$3; GW=$4; DNS="$5"
    if [ "$DNS" = "" ]; then DNS="8.8.8.8 8.8.4.4/"; fi
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
      IP_ADDR="172.124.117.99/16"
      [ "$(ip a | grep '1c:1b:0d')" != "" ] && IP_ADDR="172.124.117.100/16"
      ([ "$(ip a | grep '20:cf:30:90:dc:18')" != "" ] || [ "$(ip a | grep 'f4:6d:04')" != "" ] || [ "$(ip a | grep '48:5b:39:f7:5f:33')" != "" ] ) && IP_ADDR="172.124.116.100/16"  
      GW="172.124.1.10"
   fi
   for IP in $(ip a show dev $IFACE | grep inet | awk '{print $2;}'); do eecho sudo ip a del $IP dev $IFACE; done
   sudo eecho ip a add $IP_ADDR dev $IFACE
   sudo eecho ip link set dev $IFACE up
   sudo eecho ip route add default via $GW
   sudo sed -i '/ server/d' /etc/hosts
   sudo bash -c "echo $(echo $IP_ADDR | awk -F'/' '{print $1;}')     server >> /etc/hosts"
}

###########################################################################################
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
         $SCRIPTS/bookPass.sh
       else
         echo La autenticación fue correcta
         cat $TEMP
         break
       fi
    done
    echo Terminado: vpnConnect
}
###########################################################################################
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
###########################################################################################
wifiInterface() {
   if [ "$(interfaces | grep wlan | wc -l)" -gt 1 ]; then
      WIFACES=""
      for w in $(interfaces | grep wlan); do
         WIFACES="$WIFACES $w"
      done
      NIFACE=$($SCRIPTS/menu.sh $WIFACES)
      WIFACE=$($SCRIPTS/interfaces | grep wlan | head -$NIFACE | tail -1)
   else
      WIFACE=$($SCRIPTS/interfaces | grep wlan | tail -1)
   fi
   echo $WIFACE
}
###########################################################################################
casaBiblio() {
   WIFACE=$(wifiInterface)
   sudo $SCRIPTS/ponerRandomMAC.sh $WIFACE
   RED=$(wpaWifi $WIFACE | awk '{print $1;}')
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
###########################################################################################
comun() {
   mkdir /tmp/win10Pr
   sudo rfkill block bluetooth
   sudo rfkill block 0
   #[ "$(pgrep mate-panel)" != "" ] && 
###   mate-keybinding-properties &>/dev/null &
###   mate-appearance-properties &>/dev/null &
###   sleep 1
###   killall mate-keybinding-properties
###   killall mate-appearance-properties
   [ "$(ps aux | grep -i icewm | grep -v grep)" != "" ] && (mate-volume-control-applet &>/dev/null &) && (nemo-desktop &>/dev/null &) && (/usr/lib/x86_64-linux-gnu/xfce4/notifyd/xfce4-notifyd &>/dev/null &) && (orage &>/dev/null &) 
   if [ -f /m/Mios/Instituto/JefeDep.7z ]; then #eecho dropbox start -i
       /home/usuario/.dropbox-dist/dropboxd &>/dev/null &
       /usr/bin/megasync &> /dev/null &
   else
       echo no se inicia dropbox no está montada la unidad /m
   fi    
   #mount /l
   #sleep 3
   #[ ! -L /usr/bin/firefox ] && [ -f /usr/bin/firefox ] && sudo rm /usr/bin/firefox && sudo ln -s /m/Mios/prj/scripts/fire.sh /usr/bin/firefox
   #[ -f "/usr/bin/firefox" ] && sudo rm /usr/bin/firefox
###   sudo rm /usr/bin/firefox &> /dev/null
###   sudo ln -s /m/Mios/prj/scripts/f52.sh /usr/bin/firefox
   #[ "$(uname -r)" != "4.4.0-116-generic" ] && 
   sudo /scripts/tap0.sh
   if [ "$DONDE" != "2" ]; then 
      cumples &
###      [ "$(ps aux | grep 'indicator-brightness' | grep -v grep)" = "" ] && (/opt/extras.ubuntu.com/indicator-brightness/indicator-brightness &)
      #sudo -u usuario 
      gedit /m/Mios/Personal/Privado/PENDIENTE.txt &> /dev/null &
      $SCRIPTS/dnsexit.sh ubuin.linkpc.net &
      REAL_IP=$(a pir realIp)
      $SCRIPTS/duckdns.sh ubuin $REAL_IP &
      #$SCRIPTS/duckdns.sh ceuta6543 $REAL_IP 3a115b52-3c62-42ac-93b4-47ed6ea18423 &
      for host in 'ubuin.ddns.net' 'ubuin.hopto.org'; do
         wget -O - "https://reg6543:basura68@dynupdate.no-ip.com/nic/update?hostname=$host&myip=$REAL_IP" 2>/dev/null &
      done
      rm wget-log* &> /dev/null
   fi
}
###########################################################################################
startEpoptes() {
    . $SCRIPTS/epoptesCerts.sh
    mkdir /tmp/epoptes
    sudo systemctl stop epoptes
    [ "$(ip a | grep '1c:1b:0d:0d:2d:71')" != "" ] && touch /tmp/epoptes/server.crt && epoptesServCiclo2ServerKey > /tmp/epoptes/server.key
    [ "$(ip a | grep '20:cf:30:90:dc:18')" != "" ] && epoptesServCiclo1ServerCrt > /tmp/epoptes/server.crt && epoptesServCiclo1ServerKey > /tmp/epoptes/server.key
    [ -e /tmp/epoptes/server.key ] && sudo mv /tmp/epoptes/* /etc/epoptes/ && sudo chmod 600 /etc/epoptes/server.key && sudo chown root:root -R /etc/epoptes
    sudo systemctl start epoptes
}    
###########################################################################################   
(return 2>/dev/null) || main "$@"

#######################################################################################
############### cuidado! no poner nada debajo, no se ejecutará ########################
#######################################################################################



########################################################################################### 



#$SCRIPTS/dynDns.sh

#$SCRIPTS/dnsexit.sh ubuntu64.linkpc.net &
#$SCRIPTS/dnsexit.sh iesinclan.linkpc.net &
#$SCRIPTS/dnsexit.sh avatar.linkpc.net &

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
#gksudo  $SCRIPTS/redJunta.sh
###gksudo 'gnome-terminal -e /home/usuario/inicio.py'



#ETH=$(interfaces | grep eth | grep -v grep | head -1)
#if [ "$ETH" == "eth15" ];
#then 
#   DONDE=Casa
#else
#   DONDE=Insti
#fi  
#echo if [ "$DONDE" == "Casa" ];



    #montamos /m/Mios encriptada
    ###while [ ! -f $SCRIPTS/redJunta.sh ]; do
    ###   sudo encfs --public /m/.Mios /m/Mios
    ###done
