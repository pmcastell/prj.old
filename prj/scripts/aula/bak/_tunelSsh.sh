#!/bin/bash
tunel() {
   sudo ssh -p 443 root@ubu.noip.me -w 6:6 -CTf "/sbin/ifconfig tun6 10.6.6.7/24 pointopoint 10.6.6.6 up; /bin/sleep 3; /sbin/route add -net 10.10.10.0/24 gw 10.6.6.6; /sbin/route add -net 10.2.1.0/24 gw 10.6.6.6" 
   sudo ifconfig tun6 10.6.6.6/24 pointopoint 10.6.6.7 up
}
mata() {
   CUENTA=0
   while true; do 
      PID=$(ps ax | grep '6:6' | grep -v grep | awk '{print $1;}')
      if [ "$PID" = "" -o $CUENTA -gt 10 ]; then break; fi 
      if [ $CUENTA -lt 5 ]; then SENIAL="-TERM"; else SENIAL="-9"; fi
      sudo kill $SENIAL $PID
      CUENTA=$(expr $CUENTA + 1)
   done
}

if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi  
if [ $(ps aux | grep 'tunelSsh.sh' | grep '/bin/bash' | grep -v grep | wc -l) -gt 2 ]; then
   exit 2
fi   
while true; do
   if [ "$(wget http://iesninfo.esy.es/tunelSSH.txt -O - 2> /dev/null)" = "si" ]; then
      if [ "$(ping -c 4 10.6.6.7 2>&1 | grep '100% packet loss' | grep -v grep)" != "" ]; then
         mata
         tunel
      fi
   fi
   sleep 300;
done   
      
