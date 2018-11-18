#!/bin/bash
codPais() {
   PAIS="$1";shift
   while [ "$1" != "" ]; do  
      PAIS="${PAIS} $1"; shift; 
   done
   while read L; do
      P=$(echo $L | awk '{for(i=1;i<=(NF-4);i++) printf "%s ",$i;}')
      if [ "$P" = "$PAIS " ]; then
         echo $L | awk '{ print tolower($(NF-3));}'
         return
      fi
   done < $SCRIPTS/codigosPaises.txt
}  
 
SCRIPTS=$(dirname $0) 
. $SCRIPTS/uso.sh
[ "$1" = "" ] && uso "Uso: $0 <pais>\nCarga en firefox los urls si los hubiera del país de vpngate"
TEMP="/tmp/vpngate_tmp.txt"
[ "$1" = "-new" ] && rm $TEMP &> /dev/null && shift
[ "$(find $TEMP -mmin -5)" = "" ] && descarga -O $TEMP http://www.vpngate.net/en/ 
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
   VPN_FILE=$(echo -n https://www.vpngate.net/en/$u | sed -e 's/\&amp;/\&/g' | sed -e 's/"//g')
   echo VPN_FILE: $VPN_FILE
   #echo -n http://www.vpngate.net/en/$u | sed -e 's/\&amp;/\&/g' | sed -e 's/"//g'; echo " --- "${SA[$I]}
   OVPN_URL=$(curl "$VPN_FILE" 2>/dev/null | xmllint --html --xpath "//a/@href[contains(.,'.ovpn') and (contains(.,'udp') or contains(.,'tcp'))]" - | sed -e 's/href="/\n/g' | tail -1 | sed -e 's/\&amp;/\&/g' | sed -e 's/"//g')
   #curl "http://www.vpngate.net/${OVPN_FILE}" 2> /dev/null >
   echo "OVPN_URL: $OVPN_URL"
   OVPN_IP=$(echo $OVPN_URL | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}' | awk '{print $1;}' | head -1)
   echo "OVPN_IP: $OVPN_IP"
   echo --------------------------------------------------------
   if [ "$OVPN_IP" != "" ]; then
      VPNGATE_URL="http://www.vpngate.net/${OVPN_URL}"
      if [ "$(ls -l $HOME/freeVpns/ | grep $OVPN_IP)" != "" ]; then
         echo "Ya existe:    $VPNGATE_URL" echo " --- "${SA[$I]}
      else
         echo "No existe:    $VPNGATE_URL" echo " --- "${SA[$I]}
         PAIS=$(codPais $1)
         echo $PAIS
         echo "descargando: $VPNGATE_URL"
         descarga --timeout=5 -q --show-progress -O "$HOME/freeVpns/${PAIS}-$(basename $VPNGATE_URL)" "$VPNGATE_URL"
      fi
   fi
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




