#ifconfig ${VAP_INTERFACE} create wlandev ${INTERFACE} wlanmode monitor
ifconfig wlan0 create wlandev wpi0 inet 192.168.0.20 netmask 255.255.255.0

