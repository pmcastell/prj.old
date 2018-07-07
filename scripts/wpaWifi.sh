#!/bin/bash
uso() {
   echo uso: $0 '<WIFACE> [<RED>]'
   exit 1
}  
infoWifi() {
   echo "$RED $CHANNEL $SSID"
}  
   
[ "$1" = "-i" ] && INFO="true" && shift   
[ "$1" = "" ] && uso || WIFACE="$1"
[ "$2" = "" ] && RED=192.168.1 || RED="$2"
#if [ "$(sudo iwlist $WIFACE scan | grep MiCasa)" != "" ]; then
#  RED=172.16.1
#  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MiCasa -Dwext &
#el
sudo ip link set dev $WIFACE up
if [ "$(sudo iwlist $WIFACE scan | grep vodafone53D2)" != "" ]; then
  RED=192.168.0
  CHANNEL="13"
  SSID="vodafone53D2"
elif [ "$(sudo iwlist $WIFACE scan | grep MiCasa)" != "" ]; then
  RED=172.16.1
  CHANNEL="6"
  SSID="MiCasa"
elif [ "$(sudo iwlist $WIFACE scan | grep MOVISTAR_E360)" != "" ]; then
  CHANNEL="6"
  SSID="MOVISTAR_E360"
elif [ "$(sudo iwlist $WIFACE scan | grep MARINA24)" != "" ]; then
  CHANNEL="8"
  SSID="MARINA24"
elif [ "$(sudo iwlist $WIFACE scan | grep BPCEUTA)" != "" ]; then
  CHANNEL="1"
  SSID="BPCEUTA"
elif [ "$(sudo iwlist $WIFACE scan | grep Orange-B215)" != "" ]; then
  CHANNEL="N"
  SSID="Orange-B215"
elif [ "$(sudo iwlist $WIFACE scan | grep JAZZTEL_FCC0)" != "" ]; then
  CHANNEL="6"
  SSID="JAZZTEL_FCC0"
elif [ "$(sudo iwlist $WIFACE scan | grep BIBLIO)" != "" ]; then
  CHANNEL="N"
  SSID="BIBLIO"
fi
infoWifi
[ "$INFO" = "true" ] && exit 0

sudo mata wpa_supplicant &>/dev/null
sudo ifconfig $WIFACE up
([ "$SSID" = "BPCEUTA" ] || [ "$SSID" = "" ] ) && ( wicd-gtk &>/dev/null &) && exit 0
sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/${SSID} -Dwext &
exit 0


  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/vodafone53D2 -Dwext &
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_E360 -Dwext  &
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MARINA24 -Dwext &
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/Orange-B215 -Dwext &    
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/JAZZTEL_FCC0 -Dwext &
  #[ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/mio -Dwext &  
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE  -c /m/Mios/Personal/AIRELAB/wicd/BIBLIO -Dwext & #/m/Mios/.../wicd/BIBLIO1 -Dwext &



