uso() {
   echo Uso: $0 essid '[fichero-wep]'
   exit -1
}
#REDES_ABIERTAS="THOMSON WebSTAR Tele2-BERNARDOROLDANRODRIG Maran"
REDES_ABIERTAS="THOMSON Tele2-BERNARDOROLDANRODRIG Maran"
if [ "$1" = "" ];
then
   uso
fi
OS=$(uname)   

if [ "$2" = "" -o "$2" = "-n" ];
then
   FICHERO=/m/Mios/AIRELAB/wep/wep.txt
else
   FICHERO=$2
fi      
if [ "$3" = "" -o "$3" = "-n" ];
then
   IP_LOCAL=$(cat  $FICHERO | grep -iE "^$1" | head -1 | grep -oE "192[.]168[.][0-9]{1,3}[.][0-9]{1,3}")
   if [ "$IP_LOCAL" = "" ];
   then
      IP_LOCAL=192.168.1.6
   fi
else
   IP_LOCAL=$3
fi
#echo Sistema Operativo $OS

if [ "$(echo $IP_LOCAL | grep 192.168.1)" = "" ];
then
   if [ "$OS" = "CYGWIN_NT-5.1" ];
   then
      route add 192.168.1.9 mask 255.255.255.255 $(ipconfig | grep -i "Direcci" | grep -i ip | gawk '{ print $NF;}')
   elif [ "$OS" = "FreeBSD" ];
   then
      echo sudo route add -host 192.168.1.9 $(ifconfig bge0 | grep inet | gawk '{ print $2;}')
      sudo route add -host 192.168.1.9 $(ifconfig bge0 | grep inet | gawk '{ print $2;}')
   else   	  
      sudo route add -host 192.168.1.9 dev $(interfaces | grep -i eth | head -1)
   fi
fi   
echo $REDES_ABIERTAS | grep -i $1
if [ $? -gt 0 ];
then
   echo Redes Abiertas: $REDES_ABIERTAS
   LINEA_RED=$(cat $FICHERO | grep -iE "^$1" | head -1)
   echo $LINEA_RED
   if [ "$LINEA_RED" = "" ];
   then
      echo no encuentro red: $1
      habla no encuentro red $1
      exit 5
   fi   
   if [ $(echo $LINEA_RED | gawk '{ print length($4);}') -gt 14 ];
   then
      KEY=$(cat  $FICHERO | grep -iE "^$1" | head -1 | gawk '{ print $5;}')
      TIPO_CLAVE=128
      echo clave 128
   else
      KEY=$(cat $FICHERO | grep -iE "^$1" | head -1 | gawk '{ print $4;}')
      TIPO_CLAVE=64
      echo clave 64
   fi   
   if [ $? -gt 0 ];
   then
      echo No encuentro esa red en $FICHERO
      uso
   fi   
fi   
(wget 'http://192.168.1.9/main_frame_nologin.htm?page=logout' -O /dev/null 2>&1) > /dev/null
(wget 'http://192.168.1.9/main/login.htm?page=login&GetTimeVal=&URL=galileo' -O /dev/null 2>&1) > /dev/null
(wget 'http://192.168.1.9/main/wireless_setup_cb.htm?page=wireless_setup_CB&wirelessMode=2&brdssid=0&enableWireless=yes&apname=&wMode=on&ssid='$1 -O /dev/null  2>&1) > /dev/null

for SSID in $REDES_ABIERTAS ;
do
   if [ "$1" = "$SSID" ];
   then
      (wget 'http://192.168.1.9/main/wireless_encrypt_disabled_wpa_off_cb.htm?page=wireless_encrypt_disabled_wpa_off_cb&wep=1' -O /dev/null 2>&1) > /dev/null
      redCasaCable 192.168.0.6
      exit 0
   fi
done
   
if [ "$TIPO_CLAVE" = "128" ];
then
   if [ "$KEY" = "XXXXXXXXXXXXX" ];
   then
      KEY=$(cat  $FICHERO | grep -iE "^$1" | head -1 | gawk '{ print $6;}')
      KEY=$(asc2hex -hh $KEY)
   fi
   (wget 'http://192.168.1.9/main/wireless_encrypt_128bit_ascii_wpa_off_cb.htm?page=wireless_encrypt_128bit_ascii_wpa_off_cb&wep=3&selectAuth=1&keyType=3&asciikey2=Z001349ED01EA&asciikey3=Z0002CF84400F&asciikey4=Z0002CFB039FF&activeKey=1&asciikey1='$KEY  -O /dev/null 2>&1) > /dev/null
else
   echo key $KEY
   K1=$(echo $KEY | gawk -F':' '{ print $1;}')
   K2=$(echo $KEY | gawk -F':' '{ print $2;}')
   K3=$(echo $KEY | gawk -F':' '{ print $3;}')
   K4=$(echo $KEY | gawk -F':' '{ print $4;}')
   K5=$(echo $KEY | gawk -F':' '{ print $5;}')
   #ASCII_KEY=$(asc2hex -a $K1$K2$K3$K4$K5)
   ASCII_KEY="%$K1%$K2%$K3%$K4%$K5"
   
   echo ASCII_KEY $ASCII_KEY
   (wget 'http://192.168.1.9/main/wireless_encrypt_64bit_ascii_wpa_off_cb.htm?page=wireless_encrypt_64bit_ascii_wpa_off_cb&wep=2&selectAuth=1&keyType=3&asciikey2=&asciikey3=&asciikey4=&activeKey=1&asciikey1='$ASCII_KEY -O /dev/null 2>&1) > /dev/null
fi   

#if [ "$2" != "-n" -a "$3" != "-n" -a "$4" != "-n" ];
#then
   if [ "$OS" = "CYGWIN_NT-5.1" ];
   then
      echo net start dhcp
      net start dhcp -O /dev/null > /dev/null
      #echo ipconfig /renew
      echo ifconfig /eth 2 /ip $IP_LOCAL /gw $(echo $IP_LOCAL | gawk -F'.' '{ print $1"."$2"."$3".1";}')
      ifconfig /eth 2 /ip $IP_LOCAL /gw $(echo $IP_LOCAL | gawk -F'.' '{ print $1"."$2"."$3".1";}')
      if [ "$(echo $IP_LOCAL | grep 192.168.1)" == "" ];
      then
         echo route add 192.168.1.9 mask 255.255.255.255 $(ipconfig | grep -i "Direcci" | grep -i ip | gawk '{ print $NF;}')
         route add 192.168.1.9 mask 255.255.255.255 $(ipconfig | grep -i "Direcci" | grep -i ip | gawk '{ print $NF;}')
         echo aquí
      fi
   else
     echo redCasaCable $IP_LOCAL
     if [ "$1" = "-n" -o "$2" = "-n" -o "$3" = "-n" -o "$4" = "-n" -o "$5" = "-n" ];
     then
        redCasaCable $IP_LOCAL -n
     else
        redCasaCable $IP_LOCAL
     fi
   fi
#fi   


#(wget 'http://192.168.1.9/main/wireless_encrypt_128bit_man_wpa_off_cb?page=wireless_encrypt_128bit_man_wpa_off_cb&wep=3&selectAuth=1&keyType=1&key1a=3F&key1b=DF&key1c=5A&key1d=75&key1e=A0&key1f=50&key1g=07&activeKey=1&key1h=0D&key1i=57&key1j=F0&key1k=BB&key1l=FD&key1m=F1&key2a=F8&key2b=33&key2c=5D&key2d=C0&key2e=1D&key2f=A4&key2g=11&key2h=54&key2i=9E&key2j=36&key2k=14&key2l=A9&key2m=59&key3a=00&key3b=00&key3c=00&key3d=00&key3e=00&key3f=00&key3g=00&key3h=00&key3i=00&key3j=00&key3k=00&key3l=00&key3m=00&key4a=00&key4b=00&key4c=00&key4d=00&key4e=00&key4f=00&key4g=00&key4h=00&key4i=00&key4j=00&key4k=00&key4l=00&key4m=00'

