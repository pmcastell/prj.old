OPT=0
MAX=$(ls /m/Mios/Personal/wpa/ | wc -l)
while [ $OPT -lt 1 -o $OPT -gt $MAX ];
do
   j=1
   for i in $(ls /m/Mios/Personal/wpa/); 
   do 
      echo $j\) $i; 
      j=$(expr $j + 1)
   done
   echo elige una opción
   read OPT
done
INTERFAZ=$(interfaces | grep -i wlan | head -1)
sudo mata wpa_supplicant &>/dev/null
sudo ifconfig $INTERFAZ up
eecho sudo wpa_supplicant -B -i $INTERFAZ -c /m/Mios/Personal/wpa/$(ls /m/Mios/Personal/wpa/ | head -$OPT | tail -1)
eecho sudo dhclient -v $INTERFAZ
#actualiza

