if [ "$(ps aux | grep '/usr/local/sbin/openvpn --connect-retry-max 3 --remote www.iesinclan.tk 443 tcp --config /home/franav/openvpn/franc-nuevo2.ovpn' | grep -v grep)" = "" ];
then
   #/usr/local/sbin/openvpn --connect-retry-max 3 --remote www.iesinclan.tk 443 tcp --config /home/franav/openvpn/franc-nuevo2.ovpn 2>&1 /tmp/openvpn.log
   /usr/local/sbin/openvpn --connect-retry-max 3 --remote www.iesinclan.tk 443 tcp --config /home/franav/openvpn/franc-nuevo2.ovpn 2>&1 > /tmp/openvpn.log &
fi

if [ "$(ps aux | grep '/usr/local/sbin/openvpn --connect-retry-max 3 --remote ubu.noip.me 443 tcp --config /home/franav/open/insti.ovpn' | grep -v grep)" = "" ];
then
   #/usr/local/sbin/openvpn --connect-retry-max 3 --remote www.iesinclan.tk 443 tcp --config /home/franav/openvpn/franc-nuevo2.ovpn 2>&1 /tmp/openvpn.log
   #/usr/local/sbin/openvpn --connect-retry-max 3 --remote www.iesinclan.tk 443 tcp --config /home/franav/openvpn/franc-nuevo2.ovpn 2>&1 > /tmp/openvpn.log &
   /usr/local/sbin/openvpn --connect-retry-max 3 --remote ubu.noip.me 443 tcp --config /home/franav/open/insti.ovpn 2>&1 > /tmp/openvpn-ubu.noip.me.log &
fi

