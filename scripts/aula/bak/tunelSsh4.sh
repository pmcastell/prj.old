#!/bin/bash

ovpnKEY() {
   if [ ! -f /root/key ]; then
      echo "
      #
# 2048 bit OpenVPN static key
#
-----BEGIN OpenVPN Static key V1-----
929c4ef4e78a0c4f3ceab8257362c7de
ab5ef9472d958a8a762b505b466023a0
c331e8a55dc68e29f5f1591d2a7ced55
34dc80d96a30a1f90db8da351c3b12c3
2f8d5f34a4a2f217b8d9e42cb1860d49
edce8483b27354fee03fed03a05bf0d0
5c68020a2bf0cbf158795d4e85902c6e
d5138887b3d0af72510061ae01e13b1e
7938b6687cb5d37512d8ab557663832a
7d9f01ff3c54f9bda0ebc5195bc7aa03
9064f1a7814e791538778922584cb4ba
525dcec5106c5497502a6bda9e0cde82
8ce270b6d15a616bf191e7fe22be90db
f1c2e0997d44749047fa2eb4bb83e9e5
499f82a298f0ed2d5fa221b75a2ce866
ce9bad387185bc12de4fa7be77ee5fab
-----END OpenVPN Static key V1-----" > /root/key
   fi
}   
tunelOVPN () {
   ovpnKEY
   #sudo /usr/sbin/openvpn --connect-retry-max 3 --remote $TUN_OVP_IP --port $IESN_TUN_OVP_PORT --proto tcp-client --dev tun15 --ifconfig 10.7.0.7 10.7.0.6 --secret /root/key
   sudo /usr/sbin/openvpn --connect-retry-max 3 --remote $TUN_OVP_IP --port $TUN_OVP_PORT --proto tcp-client --dev tun$TUN_OVP_DEV --ifconfig $TUN_OVP_IFCONFIG --secret /root/key &
}
tunelSSH() {
   #echo estableciendo tunel
   #sudo ssh -o ConnectTimeout=10 -p $IESN_TUN_SSH_PORT root@$IESN_TUN_SSH_IP -w 6:6 -CTf "/sbin/ifconfig tun6 10.6.6.7/24 pointopoint 10.6.6.6 up; /bin/sleep 3; /sbin/route add -net 10.10.10.0/24 gw 10.6.6.6; /sbin/route add -net 10.2.1.0/24 gw 10.6.6.6" 
   #sudo ifconfig tun6 10.6.6.6/24 pointopoint 10.6.6.7 up &> /dev/null
   for VAR in PORT IP DEV DEV_GW DEV_IP CMD; do eval TUN_SSH_${VAR}=\"$(eval echo \"$(eval echo \$TUN_SSH_${VAR})\")\"; done
   sudo ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o ConnectTimeout=10 -p $TUN_SSH_PORT root@$TUN_SSH_IP -w $TUN_SSH_DEV:$TUN_SSH_DEV -CTf bash -c \'"/sbin/ifconfig tun$TUN_SSH_DEV  $TUN_SSH_DEV_GW/24 pointopoint $TUN_SSH_DEV_IP up; /bin/sleep 3; $TUN_SSH_CMD" \'
   sudo /sbin/ifconfig tun$TUN_SSH_DEV $TUN_SSH_DEV_IP/24 pointopoint $TUN_SSH_DEV_GW up &> /dev/null
   sudo /sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10.${TUN_SSH_DEV}.${TUN_SSH_DEV}.0/24 &> /dev/null
   sudo /sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.${TUN_SSH_DEV}.${TUN_SSH_DEV}.0/24
}

mata() {
   CUENTA=0
   while true; do 
      PID=$(ps ax | grep '6:6' | grep -v grep | awk '{print $1;}')
      if [ "$PID" = "" ]; then PID=$(/bin/ps  ax | /bin/grep -i tun15 | /bin/grep -v /bin/grep | /usr/bin/awk '{print $1;}'); fi
      if [ "$PID" = "" -o $CUENTA -gt 10 ]; then break; fi 
      if [ $CUENTA -lt 5 ]; then SENIAL="-TERM"; else SENIAL="-9"; fi
      sudo kill $SENIAL $PID
      CUENTA=$(expr $CUENTA + 1)
   done
}
desencriptaLinea() {
   CHA=$1
   i=1
   while [ $i -le 16 ];
   do
      CHA=$(echo -n $CHA | base64 -d)
      i=$(expr $i + 1)
   done
   echo $CHA
}
desencripta() {
   if [ "$1" = "" ]; then ENTRADA="/dev/stdin"; else ENTRADA="$1"; fi
   if [ -f $ENTRADA -o "$ENTRADA" = "/dev/stdin" ]; then
      while read LINEA; do
         echo $(desencriptaLinea "$LINEA")
      done < $ENTRADA
   else
      echo $(desencriptaLinea "$1")
   fi
}
procesarParametros() {
   TEMP=$(tempfile)
   HOSTNAME=$(hostname|awk -F'.' '{print $1;}')
   #HOSTNAME="aulasrv1"
   wget -O - http://ubuin.noip.me/indice4.html 2> /dev/null | desencripta > $TEMP
   while read LINEA; do 
      #if [ "$LINEA" != "" -a "$(echo $LINEA | grep -E ^${HOSTNAME} | grep -v grep)" != "" ]; then export "$LINEA"; fi
      #if [ "$LINEA" != "" -a "$(echo $LINEA | grep -E ^GLOBAL_ | grep -v grep)" != "" ]; then   export "$LINEA"; fi
      #if [ "$LINEA" != "" -a "$(echo $LINEA | grep -E ^${HOSTNAME} | grep -v grep)" != "" ] ||
      #    [ "$LINEA" != "" -a "$(echo $LINEA | grep -E ^GLOBAL_ | grep -v grep)" != "" ] then
      if [ "$LINEA" != "" -a "$(echo $LINEA | grep -E "($HOSTNAME|^GLOBAL)" | grep -v grep)" != "" ]; then
         VAR=$(echo $LINEA | awk -F'=' '{print $1;}')
         VALOR=$(echo $LINEA | awk -F'=' '{print $2;}')
         if [ "$(echo $VALOR | egrep '^\$')" != "" -a "$(eval echo \$${VALOR})" != "" ]; then
            #echo VAR: $VAR
            #echo VALOR: $VALOR
            #echo VALOR REAL: $(eval echo \$${VALOR})
            #echo export $VAR="$(eval echo ${VALOR})"
            export $VAR="$(eval echo ${VALOR})"
         else
            #echo export $LINEA
            export "$LINEA"
         fi
      fi
   done < $TEMP
   TUN_SSH=$(eval echo \$${HOSTNAME}_TUN_SSH)
   TUN_SSH_IP=$(eval echo \$${HOSTNAME}_TUN_SSH_IP)
   TUN_SSH_PORT=$(eval echo \$${HOSTNAME}_TUN_SSH_PORT)
   TUN_SSH_DEV=$(eval echo \$${HOSTNAME}_TUN_SSH_DEV)
   TUN_SSH_DEV_IP="10.${TUN_SSH_DEV}.${TUN_SSH_DEV}.${TUN_SSH_DEV}"
   TUN_SSH_DEV_GW="10.${TUN_SSH_DEV}.${TUN_SSH_DEV}.1"
   TUN_SSH_CMD=""
   for((i=1;i<=10;i++)); do 
      VAR_NAME="${HOSTNAME}_TUN_SSH_CMD${i}"
      #echo VAR_NAME: $VAR_NAME
      #VALOR=$(eval echo \$$VAR_NAME)
      #echo VALOR: $VALOR
      #TUN_SSH_CMD="$TUN_SSH_CMD $(eval echo \$${HOSTNAME}_TUN_SSH_CMD${i})"
      #TUN_SSH_CMD="$TUN_SSH_CMD \$${HOSTNAME}_TUN_SSH_CMD${i}"
      #TUN_SSH_CMD="$TUN_SSH_CMD $VALOR"
      TUN_SSH_CMD="$TUN_SSH_CMD $(eval echo \$$VAR_NAME)"
      #echo TUN_SSH_CMD: $TUN_SSH_CMD
   done
   TUN_SSH_CMD=$(eval echo $TUN_SSH_CMD)
   
   TUN_OVP=$(eval echo \$${HOSTNAME}_TUN_OVP)
   TUN_OVP_IP=$(eval echo \$${HOSTNAME}_TUN_OVP_IP)
   TUN_OVP_PORT=$(eval echo \$${HOSTNAME}_TUN_OVP_PORT)
   TUN_OVP_DEV=$(eval echo \$${HOSTNAME}_TUN_OVP_DEV)
   TUN_OVP_DEV_IP="10.${TUN_OVP_DEV}.${TUN_OVP_DEV}.${TUN_OVP_DEV}"
   TUN_OVP_DEV_GW="10.${TUN_OVP_DEV}.${TUN_OVP_DEV}.1"
   TUN_OVP_IFCONFIG="$TUN_OVP_DEV_IP $TUN_OVP_DEV_GW"
   rm $TEMP
}
if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi  
if [ $(ps aux | grep $(basename $0) | grep '/bin/bash' | grep -v grep | wc -l) -gt 2 ]; then
   exit 2
fi   
while true; do
   if [ "$CONEXION_ESTABLECIDA" != "1" ]; then
      procesarParametros
      #echo parámetros procesados
   fi
   CONEXION_ESTABLECIDA=0
   if [ "$GLOBAL_ESPERA" = "" ]; then TUN_ESPERA=300; else TUN_ESPERA=$GLOBAL_ESPERA; fi
   if [ "$TUN_SSH" = "si" ]; then
      #echo tunel ssh: ping -c 4 $TUN_SSH_DEV_GW
      if [ "$(ping -c 4 $TUN_SSH_DEV_GW 2>&1 | grep '100% packet loss' | grep -v grep)" != "" ]; then
         mata
         #echo intentando establecer tunel ssh
         tunelSSH
      else
         CONEXION_ESTABLECIDA=1
      fi
   fi
   if [ "$TUN_OVP" = "si" ]; then
      #echo tunel ovp
      if [ "$(ping -c 4 $TUN_OVP_DEV_GW 2>&1 | grep '100% packet loss' | grep -v grep)" != "" ]; then
         mata
         #echo intentando establecer tunel vpn
         tunelOVPN
      else
         CONEXION_ESTABLECIDA=1
      fi
   fi
   #echo sleep $TUN_ESPERA;
   sleep $TUN_ESPERA;
done   





   #while read LINEA; do 
   #   #EXPORT=$(echo $LINEA | awk -F'=' '{print "export "$1"="$2;}'); 
   #   #VAR=$(echo $LINEA | awk -F'=' '{print $1;}');
   #   #VAL=$(echo $LINEA | awk -F'=' '{print $2;}');
   #   #$EXPORT; 
   #   if [ "$LINEA" != "" -a "$(echo $LINEA | grep $HOSTNAME | grep -v grep)" != "" ]; then export $LINEA; fi
   #   #echo "VAR: $VAR ,  VAL: $VAL"
   #   #echo $EXPORT
   #done < $TEMP
