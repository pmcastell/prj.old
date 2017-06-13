#!/bin/bash

. /scripts/uso.sh
[ "$1" = "" ] && uso "Uso: $0 <pais>\nCarga en firefox los urls si los hubiera del país de vpngate"
TEMP="/tmp/vpngate_tmp.txt"
[ "$1" = "-new" ] && rm $TEMP &> /dev/null && shift
[ "$(find $TEMP -mmin -5)" = "" ] && wget -O $TEMP http://www.vpngate.net/en/ 
clear
URLS=$(cat $TEMP | xmllint --html --xpath "//table[@id='vg_hosts_table_id']/tr[contains(td,'$1')]/td/a[contains(@href,'openvpn')]/@href" --format - 2> /dev/null | sed -e 's/href=/\n/g')
I=0
export SA
cat $TEMP | xmllint --html --xpath "//table[@id='vg_hosts_table_id']/tr[contains(td,'$1')]/td//span[contains(.,'session')]" --format - 2>/dev/null | sed -e 's/<\/span>/\n/g' | sed -e 's/<span .*>//g' |
{
while read s; do
   #echo SA[$I]="$s"
   SA[$I]="$s"
   #echo "SA: $SA, SA[$I]: ${SA[$I]}"
   I=$(($I+1))
done
echo ""
echo "-------------------------------------------------------------------------------------------------------------------------"
I=0   
for u in $URLS; do 
   echo -n http://www.vpngate.net/en/$u | sed -e 's/\&amp;/\&/g' | sed -e 's/"//g'; echo " --- "${SA[$I]}
   I=$(($I+1))
   #echo "I: $I. SA: $SA, SA[$I]: ${SA[$I]}"
done  
echo "-------------------------------------------------------------------------------------------------------------------------"
echo ""
}
exit 0

http://www.vpngate.net/en/do_openvpn.aspx?fqdn=vpn120776072.opengw.net&ip=82.9.237.65&tcp=1318&udp=1862&sid=1494772568895&hid=927181
http://www.vpngate.net/en/"do_openvpn.aspx?fqdn=vpn120776072.opengw.net&amp;ip=82.9.237.65&amp;tcp=1318&amp;udp=1862&amp;sid=1494772568895&amp;hid=927181"
http://www.vpngate.net/en/do_openvpn.aspxfqdn=vpn120776072.opengw.net&amp;ip=82.9.237.65&amp;tcp=1318&amp;udp=1862&amp;sid=1494772568895&amp;hid=927181
