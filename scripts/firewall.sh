sudo iptables -F
sudo iptables -t nat -F
sudo iptables -t mangle -F
ACCEPT=$(param -accept $@)
DROP=$(param -drop $@)
MASQUERADE=$(param -masquerade $@)
CURSO=""
ACCEPT="172.16.1.0/27 
192.168.76.0/29 
10.9.9.0/24  
172.16.145.0/24 
10.10.10.0/24
192.168.1.1
192.168.1.25
192.168.1.27
192.168.0.1 
172.16.254.0/24 
172.18.161.0/24
172.18.162.0/24
192.168.10.0/24
172.124.240.0/24
172.124.117.0/24
172.124.1.0/24
$ACCEPT"
DROP="172.124.0.0/16 192.168.0.0/16 172.16.0.0/16 $DROP"
MASQUERADE="10.9.9.0/24 192.168.44.0/24 10.1.1.0/24 10.10.10.0/24 172.16.1.0/24 $MASQUERADE"


if [ "$1" != "" -a "$1" != "-accept" -a "$1" != "-drop" ]; then
   CURSO=$1
fi
if [ "$CURSO" = "ciclo1" ]; then
   ACCEPT="172.124.116.0/24 $ACCEPT"
elif [ "$CURSO" = "ciclo2" ]; then
   ACCEPT="172.124.117.0/24 $ACCEPT"
elif [ "$CURSO" = "SMR2" ]; then   
   ACCEPT="172.16.3.0/24 $ACCEPT"
else
   echo ACEEPT: $ACCEPT; ACCEPT=$ACCEPT;   
fi

for a in $ACCEPT; do
   sudo iptables -A INPUT -j ACCEPT -s $a
done
for a in $DROP; do
   sudo iptables -A INPUT -j DROP -s $a
done
for a in $ACCEPT; do
   sudo iptables -A OUTPUT -j ACCEPT -d $a
done
for a in $DROP; do
   sudo iptables -A OUTPUT -j DROP -d $a
done
for a in $MASQUERADE; do
   sudo iptables -A POSTROUTING -t nat -j MASQUERADE -s $a
done
echo 1 >  /proc/sys/net/ipv4/ip_forward 
sudo iptables -nL
sudo iptables -t nat -nL

echo "ACCEPT: $ACCEPT"
exit
#sudo iptables -A PREROUTING -t nat -p tcp -m tcp -d 192.168.1.6/32 --dport 25 -j DNAT --to-destination 10.10.10.9:25
#sudo iptables -A OUTPUT -m owner --gid-owner noi -j DROP
#sudo iptables -A OUTPUT -m owner --uid noi -j DROP

#sudo iptables -A POSTROUTING -t nat -j MASQUERADE -s 172.16.71.0/24 -o ath1
#sudo iptables -A PREROUTING -t nat -p tcp -m tcp -d 192.168.1.6/32 --dport 22 -j DNAT --to-destination 10.10.10.9:25
#sudo iptables -A PREROUTING -t nat -p tcp -m tcp --dport 80 -j DNAT --to-destination 10.10.10.9:80
#sudo iptables -A POSTROUTING -j MASQUERADE -t nat # MASQUERADE para todos
