#!/bin/bash
uso() {
   echo uso: $0 '<WIFACE> [<RED>]'
   exit 1
}  
infoWifi() {
   echo "$RED $CHANNEL $ssid"
}  
[ "$1" = "-i" ] && INFO="true" && shift   
[ "$1" = "" ] && uso || WIFACE="$1"
[ "$2" = "" ] && RED=192.168.1 || RED="$2"
SCRIPTS="$(dirname $0)"
sudo ip link set dev $WIFACE up
HOSTAPD_CNF="$(ps aux | grep -i hostapd | grep -v grep | tail -1 | awk '{print $NF;}')"
[ "$HOSTAPD_CNF" != "" ] && HOSTAPD_ssid="$(sudo cat $HOSTAPD_CNF | grep ssid | awk -F'=' '{print $2;}')"
#[ "$(uname -a | grep -o raspberry)" = "raspberry" ] && [ "$(iwconfig 2>&1 | grep -i 'Mode:Master')" != "" ] && RASP=true || RASP=false
for((I=0;I<10;I++)); do
    #echo "Valor de I: $I"
    SSIDS=$(sudo iwlist $WIFACE scan | grep SSID)
    for FCONFIG in $(ls ${SCRIPTS}/wicd/[0-9][0-9]*); do
        #echo "FCONFIG: $FCONFIG"
        #ssid=$(basename $FCONFIG | awk -F'.' '{print $2;}' | awk -F'-' '{print $1;}')
        eval $(cat $FCONFIG | grep 'ssid="')
        [ "$ssid" = "$HOSTAPD_ssid" ] && continue 
        RED="$(cat $FCONFIG | grep '#!RED=' | awk -F'#!RED=' '{print $2;}')"
        CHANNEL="$(cat $FCONFIG | grep '#!CHANNEL=' | awk -F'#!CHANNEL=' '{print $2;}')"
        [ "$(echo $SSIDS | grep ${ssid})" != "" ] && break 2
    done
    #I=$(( $I + 1 ))
    #echo "Valor de I: $I"
    #[ "$(echo $SSIDS | grep $ssid)" != "" ] && break
done 
infoWifi
[ "$INFO" = "true" ] && exit 0

sudo mata wpa_supplicant &>/dev/null
sudo ip link set dev $WIFACE up
([ "$ssid" = "BPCEUTA" ] || [ "$ssid" = "" ] ) && ( wicd-gtk &>/dev/null &) && exit 0
sudo eecho wpa_supplicant -B -i $WIFACE -c ${FCONFIG} -Dwext &
sudo ip a add ${RED}.27/24 dev $WIFACE
sudo ip route add default via ${RED}.1
exit 0


  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/vodafone53D2 -Dwext &
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_E360 -Dwext  &
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MARINA24 -Dwext &
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/Orange-B215 -Dwext &    
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/JAZZTEL_FCC0 -Dwext &
  #[ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/mio -Dwext &  
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE  -c /m/Mios/Personal/AIRELAB/wicd/BIBLIO -Dwext & #/m/Mios/.../wicd/BIBLIO1 -Dwext &



