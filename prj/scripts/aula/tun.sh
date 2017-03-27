sudo ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o ConnectTimeout=10 -p 443 root@79.155.187.251 -w 26:26 -CTf /sbin/ifconfig tun26  10.26.26.1/24 pointopoint 10.26.26.26 up; /bin/sleep 3; 
sudo ifconfig tun26 10.26.26.26/24 pointopoint 10.26.26.1 up
