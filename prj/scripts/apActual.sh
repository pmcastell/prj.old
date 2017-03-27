uso() {
   echo Uso: $0 essid '[fichero-wep]'
   exit -1
}
(wget 'http://192.168.1.9/duplicate.htm?page=duplicate' -O /dev/null 2>&1) > /dev/null
(wget 'http://192.168.1.9/main/login.htm?page=login&GetTimeVal=&URL=galileo' -O /dev/null 2>&1) > /dev/null
wget http://192.168.1.9/main/status_admin_mode.htm -O - 2> /dev/null | grep -A2 SSID | grep "\/TR" | awk -F '>' '{ print $2;}' | awk -F '<' '{ print $1;}'

