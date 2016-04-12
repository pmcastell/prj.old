DIR_BASE=/m/Mios/Personal/openvpn
cd $DIR_BASE
#openvpn --config $DIR_BASE/freeVpns/uk-vpngate_wawan.opengw.net_tcp_443.ovpn &

cat $DIR_BASE/freeVpns/usa-vpngate_jawws.opengw.net_tcp_443.ovpn > /tmp/jawws.ovpn
echo remote $(digHost 8.8.8.8 jawws.opengw.net) 443 >> /tmp/jawws.ovpn
sudo openvpn --config /tmp/jawws.ovpn &
#sleep 8
#sudo openvpn --config $DIR_BASE/client.ovpn --remote 80.36.87.166 --port 8080 &
cd -
