wget http://admin:galileo@192.168.76.2/Wireless_Basic.asp -O - 2> /dev/null | grep SSID | grep -oE '\[.*\]'
