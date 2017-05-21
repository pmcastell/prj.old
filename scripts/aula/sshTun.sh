#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 [--tun <tun-dev>] [--dest <host-destino>] [--port <puerto>] [--rutas]
   El parámetro rutas modifica la tabla de rutas para 10.2.1.0 y 10.10.10.0, si no se especifica no se añaden
      dichas rutas
EOF
   exit 1
}      
DEST_HOST="ubuin.linkpc.net"
DEST_PORT="443"
TUN=38
RUTAS=false;
while [ "$1" != "" ]; do
   case $1 in
      --tun) 
         shift; TUN=$1; 
         ;;
      --dest)
         shift; DEST_HOST=$1;
         ;;
      --port)
         shift; DEST_PORT=$1;
         ;;
      --rutas)
         shift; RUTAS=true;
         ;;
      --help | -h) 
         uso
         ;;
   esac
   shift
done            
         

if $RUTAS; then  
sudo /usr/bin/ssh -o UserKnownHostsFile=/dev/null -o ConnectTimeout=30 -o ConnectionAttempts=10 -o StrictHostKeyChecking=no -p $DEST_PORT root@$DEST_HOST -w $TUN:$TUN -CTf /bin/bash -c \'"/bin/ls; /bin/sleep 5 ; /sbin/ifconfig tun$TUN  10.$TUN.$TUN.1/24 pointopoint 10.$TUN.$TUN.$TUN up; /bin/sleep 3; /sbin/route add -net 10.10.10.0/24 gw 10.$TUN.$TUN.$TUN; /sbin/route add -net 10.2.1.0/24 gw 10.$TUN.$TUN.$TUN; /sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24 &> /dev/null; /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24;" \'
else
sudo /usr/bin/ssh -o UserKnownHostsFile=/dev/null -o ConnectTimeout=30 -o ConnectionAttempts=10 -o StrictHostKeyChecking=no -p $DEST_PORT root@$DEST_HOST -w $TUN:$TUN -CTf /bin/bash -c \'"/bin/ls; /bin/sleep 5 ; /sbin/ifconfig tun$TUN  10.$TUN.$TUN.1/24 pointopoint 10.$TUN.$TUN.$TUN up; /bin/sleep 3; /sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24 &> /dev/null; /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24;" \'
fi

if [ "$(/sbin/ifconfig -a | grep tun$TUN)" != "" ]; then
   sudo /sbin/ifconfig tun$TUN 10.$TUN.$TUN.$TUN/24 pointopoint 10.$TUN.$TUN.1 up &> /dev/null
   sudo /sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24 &> /dev/null
   sudo /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.$TUN.$TUN.0/24
fi


