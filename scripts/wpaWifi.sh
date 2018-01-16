#!/bin/bash
uso() {
   echo uso: $0 '<WIFACE> [<RED>]'
   exit 1
}   
[ "$1" = "-i" ] && INFO="true" && shift   
[ "$1" = "" ] && uso || WIFACE="$1"
[ "$2" = "" ] && RED=192.168.1 || RED="$2"
sudo mata wpa_supplicant &>/dev/null
sudo ifconfig $WIFACE up
#if [ "$(sudo iwlist $WIFACE scan | grep MiCasa)" != "" ]; then
#  RED=172.16.1
#  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MiCasa -Dwext &
#el
if [ "$(sudo iwlist $WIFACE scan | grep vodafone53D2)" != "" ]; then
  RED=192.168.0
  CHANNEL=""
  SSID="vodafone53D2"
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/vodafone53D2 -Dwext &
elif [ "$(sudo iwlist $WIFACE scan | grep MOVISTAR_E360)" != "" ]; then
  CHANNEL="6"
  SSID="MOVISTAR_E360"
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MOVISTAR_E360 -Dwext &
elif [ "$(sudo iwlist $WIFACE scan | grep MARINA24)" != "" ]; then
  CHANNEL="8"
  SSID="MARINA24"
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/MARINA24 -Dwext &
elif [ "$(sudo iwlist $WIFACE scan | grep Orange-B215)" != "" ]; then
  CHANNEL=""
  SSID="Orange-B215"
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/Orange-B215 -Dwext &
elif [ "$(sudo iwlist $WIFACE scan | grep JAZZTEL_FCC0)" != "" ]; then
  CHANNEL="6"
  SSID="JAZZTEL_FCC0"
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/JAZZTEL_FCC0 -Dwext &
  #[ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE -c /m/Mios/Personal/AIRELAB/wicd/mio -Dwext &
elif [ "$(sudo iwlist $WIFACE scan | grep BIBLIO)" != "" ]; then
  CHANNEL=""
  SSID="BIBLIO"
  [ "$INFO" != "true" ] && sudo eecho wpa_supplicant -B -i $WIFACE  -c /m/Mios/Personal/AIRELAB/wicd/BIBLIO -Dwext & #/m/Mios/.../wicd/BIBLIO1 -Dwext &
fi
echo "$RED $CHANNEL $SSID"
