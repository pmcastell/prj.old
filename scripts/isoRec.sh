uso() {
   echo $0 fichero-imagen-iso
}   
if [ "$1" = "" ];
then
   uso
   exit -1
fi   
if [ $(uname) = "FreeBSD" ];
then
   if [ "$1" = "blank" ];
   then
      sudo wodim dev=0,0,0 blank=fast
   else
      sudo umount /dev/cd0
      sudo umount /dev/acd0
      DEVICE=$(sudo wodim --devices | grep -i 'dev' | awk '{ print $2;}')
      DEVICE=$(echo $DEVICE | awk -F\' '{print $1$2;}')
      echo sudo wodim $DEVICE -v -eject $DEVICE $1
      sudo wodim $DEVICE -v -eject $DEVICE $1
   fi
else         
   umount /dev/sr0
   echo wodim -v -eject dev=/dev/sr0 $1
   wodim -v -eject dev=/dev/sr0 $1
fi   
