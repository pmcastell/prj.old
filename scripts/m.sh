if [ -f /mnt/m/menu.lst ];
then
   echo la unidad m ya está montada
   exit 1
fi
   
if [ -f /dev/ad4s5 ];
then
   sudo mount -o large -t msdosfs /dev/ad4s5 /mnt/m
else
if [ -f /dev/da0s5 ];
then
   sudo mount -o large -t msdosfs /dev/da0s5 /mnt/m
else
if [ -f /dev/da1s5 ];
then
   sudo mount -o large -t msdosfs /dev/da1s5 /mnt/m
fi
fi
fi   
