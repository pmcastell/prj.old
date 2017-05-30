#!/bin/bash

. /scripts/uso.sh
[ "$1" = "" ] && uso "Uso: $0 <pais>\nCarga en firefox los urls si los hubiera del país de vpngate"

URLS=$(wget -O - http://www.vpngate.net/en/ 2>/dev/null | xmllint --html --xpath "//table[@id='vg_hosts_table_id']/tr[contains(td,'$1')]/td/a[contains(@href,'openvpn')]/@href" --format - 2> /dev/null | sed -e 's/href=/\n/g')
for u in $URLS; do 
   echo http://www.vpngate.net/en/$u | sed -e 's/\&amp;/\&/g' | sed -e 's/"//g'
done   

exit 0

http://www.vpngate.net/en/do_openvpn.aspx?fqdn=vpn120776072.opengw.net&ip=82.9.237.65&tcp=1318&udp=1862&sid=1494772568895&hid=927181
http://www.vpngate.net/en/"do_openvpn.aspx?fqdn=vpn120776072.opengw.net&amp;ip=82.9.237.65&amp;tcp=1318&amp;udp=1862&amp;sid=1494772568895&amp;hid=927181"
http://www.vpngate.net/en/do_openvpn.aspxfqdn=vpn120776072.opengw.net&amp;ip=82.9.237.65&amp;tcp=1318&amp;udp=1862&amp;sid=1494772568895&amp;hid=927181