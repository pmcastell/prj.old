if [ "$1" = "" ];
then
   INTERFAZ=ath0
else
   INTERFAZ=$1
fi
if [ "$(uname)" = "Linux" ];
then
   ifconfig $INTERFAZ down
   iwconfig $INTERFAZ mode managed
   ifconfig $INTERFAZ up
else
   ifconfig $INTERFAZ -monitor -promisc -mediaopt monitor media OFDM/18Mbps
   sleep 1
   ifconfig $INTERFAZ -monitor -promisc -mediaopt monitor media auto mode auto 
fi
