mataSsh() {
    PID="99999999"
    CONT=0
    SIGNAL='-TERM'
    while [ "$PID" != "" ];
    do
      PID=$(ps aux | grep ssh | grep 443 |  grep -v grep | head -1 | awk '{print $2;}')
      if [ "$PID" != "" ];
      then
         eecho kill $SIGNAL $PID 
      fi
      if [ $CONT -gt 4 ];
      then
         SIGNAL='-9'
      fi
    done
}
DEFAULT_GW=""
defaultGw() {
   DEFAULT_GW=$(route -n | grep -E '(eth|wlan)' | grep 192.168.1.1 | grep -v grep | head -1 | awk '{ print $2;}')
   if [ "$DEFAULT_GW" = "" ];
   then
      DEFAULT_GW=$(route -n | grep -E '(eth|wlan)' | grep 192.168.0.1 | grep -v grep | head -1 | awk '{ print $2;}')
   fi
}
   
mataSsh
if [ "$1" = "stop" ];
then   
   touch /tmp/tunelFin
   exit 0
elif [ "$1" = "start" -o "$1" = "" ] && [ -f /tmp/tunelFin ];
then
   sudo rm /tmp/tunelFin   
fi   
CJB_NET=$(digHost 8.8.8.8 shell.cjb.net)
if [ "$CJB_NET" = "" ];
then
    CJB_NET=216.194.70.6
    echo no se ha posido resolver shell.cjb.net usando direccion: $CJB_NET
fi    
TUNEL1="ssh -p 443 -C -L 8080:80.36.87.166:8080 -N usuario@$CJB_NET -o ServerAliveInterval=150"
TUNEL2="ssh -p 443 -C -D 8888 -N usuario@$CJB_NET -o ServerAliveINterval=150"
PASSWORD=$(cat /m/Mios/prj/scripts/passBas.txt | desencripta)
i=1
vpn stop
(sleep 10 && eecho vpn 8080 localhost ) &
while [ ! -f /tmp/tunelFin ]; 
do 
   echo iteracion $i
   CONT=0
   while [ ! -f /tmp/tunelFin -a "$(ps aux | grep ssh | grep 443 |  grep -v grep | head -1 | awk '{print $2;}')" = "" -a $CONT -le 5 ];
   do
      defaultGw
      echo if [ "$DEFAULT_GW" != "" -a "$(route -n | grep $CJB_NET | grep -v grep)" = "" ];
      if [ "$DEFAULT_GW" != "" -a "$(route -n | grep $CJB_NET | grep -v grep)" = "" ];
      then
         eecho sudo route add -host $CJB_NET gw $DEFAULT_GW
      fi
      eecho sshpass -p $PASSWORD $TUNEL1 &
      eecho sshpass -p $PASSWORD $TUNEL2 &
      hablaf -n intentando abrir túneles
      sleep 5
   done
   if [ "$CONT" -ge 5 ];
   then
      hablaf -n no puedo establecer conexion por ssh con shell.cjb.net
      hablaf -n no puedo establecer conexion por ssh con shell.cjb.net
   else 
       #kill -HUP $(ps aux | grep -i openvpn | grep -v grep | head -1 | awk '{ print $2}')
       ERROR=""
       CONT=0
       while [ ! -f /tmp/tunelFin -a  "$ERROR" = "" -o $CONT -lt 4 ];
       do 
          sleep 10
          ERROR=$(ping -c 4 squid | grep '100% packet loss')
          echo ERROR: $ERROR
          if [ "$ERROR" != "" ];
          then
             CONT=$(($CONT + 1))
          else
             CONT=0
          fi
          echo contador: $CONT
       done
       hablaf -n "se ha perdido la conexión"
       mataSsh
       i=$(($i + 1))
    fi
done
mataSsh
sudo vpn stop
