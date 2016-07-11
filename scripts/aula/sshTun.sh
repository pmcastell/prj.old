#!/bin/bash

TUN=38
/usr/bin/ssh -o UserKnownHostsFile=/dev/null -o ConnectTimeout=30 -o ConnectionAttempts=10 -o StrictHostKeyChecking=no -p 443 root@ubuin.linkpc.net -w $TUN:$TUN -CTf /bin/bash -c \'"/bin/ls; /bin/sleep 5 ; /sbin/ifconfig tun$TUN  10.$TUN.$TUN.1/24 pointopoint 10.$TUN.$TUN.$TUN up; /bin/sleep 3; /sbin/route add -net 10.10.10.0/24 gw 10.$TUN.$TUN.$TUN; /sbin/route add -net 10.2.1.0/24 gw 10.$TUN.$TUN.$TUN; /sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24 &> /dev/null; /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24;" \'
if [ "$(/sbin/ifconfig -a | grep tun$TUN)" != "" ]; then
   /sbin/ifconfig tun$TUN 10.$TUN.$TUN.$TUN/24 pointopoint 10.$TUN.$TUN.1 up &> /dev/null
fi
   
