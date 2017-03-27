mata() {
    SENIAL=$2
    PROCESO=$1
    PID=$(ps ax | grep -i $PROCESO | grep -v grep |awk '{print $1;}')
    INTENTOS=1
    echo $PID
    while [ "$PID" != "" -a $INTENTOS -lt 6 ];
    do
       /usr/bin/sudo kill -$SENIAL $PID
       PID=$(ps ax | grep -i $PROCESO | grep -v grep |awk '{print $1;}')
       INTENTOS=$(expr $INTENTOS + 1)
    done   
}    
mata wpa_supplicant TERM
mata wpa_supplicant 9 
mata dhclient TERM
mata dhclient 9 

/usr/bin/sudo /sbin/wpa_supplicant -B -i wlan0 -c /var/lib/andared.wpa -Dwext
/usr/bin/sudo /sbin/dhclient -v wlan0
sleep 5
/usr/bin/sudo /sbin/ifconfig eth0 172.16.1.9 netmask 255.255.224.0
/usr/bin/sudo /sbin/ifconfig eth0:1 192.168.76.9 netmask 255.255.224.0
/usr/bin/sudo route del default
/usr/bin/sudo route add -net 192.168.0.0/20 dev wlan0
/usr/bin/sudo route add default gw 172.16.1.1
/usr/bin/sudo bash -c 'echo nameserver 127.0.0.1 > /etc/resolv.conf'
/usr/bin/sudo bash -c 'echo nameserver 192.168.0.2 >> /etc/resolv.conf'
/usr/bin/sudo bash -c 'echo search iesinclan.tk iesinclan.dyndns.org 41701109.41.andared.ced.junta-andalucia.es >> /etc/resolv.conf'
/usr/bin/sudo bash -c 'echo domain iesinclan.tk >> /etc/resolv.conf'
/usr/bin/sudo /etc/init.d/bind9 restart
/usr/bin/sudo bash -c 'echo 1 > /proc/sys/net/ipv4/ip_forward'
/usr/bin/sudo iptables -F
/usr/bin/sudo iptables -t nat -F
MASQUERADES="10.1.0.0/24 10.2.0.0/24  10.3.0.0/24  10.10.10.0/24 10.100.100.0/24 172.16.0.0/19"
for j in $MASQUERADES;
do
    echo /usr/bin/sudo /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s $j
    /usr/bin/sudo /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s $j
done    
/sbin/iptables -A INPUT -j DROP -s 70.136.231.42
/sbin/iptables -A INPUT -j DROP -s 61.147.70.112

