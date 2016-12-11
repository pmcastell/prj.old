#!/bin/bash

uso() {
cat<<EOF
   Uso: $0
EOF
   exit 1
}   
if [ "$1" = "-h" ]; then uso; fi
sudo apt-get install hostpad
TEMP=$(tempfile)
cat<<EOF > $TEMP
#uth_algs=1
beacon_int=50
channel=3
country_code=ES
disassoc_low_ack=1
driver=nl80211
hw_mode=g
ht_capab=[HT40+][HT40-][SHORT-GI-40][RX-STBC1]
ieee80211d=1
ieee80211n=1
interface=wlan20
require_ht=0
rsn_pairwise=CCMP
ssid=TOSHIBA
wmm_enabled=1
wpa=2
wpa_key_mgmt=WPA-PSK
wpa_passphrase=TOSHIBA_2016

EOF

sudo mv $TEMP /etc/hostapd/hostapd.conf

