ifconfig wlan5 down
iwconfig wlan5 mode managed
ifconfig wlan5 up
iwconfig wlan5 channel 6
ifconfig wlan5 down
iwconfig wlan5 mode monitor
airodump-ng wlan5 -c 6 -w /captur/juan/juan
