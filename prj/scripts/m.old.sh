SDA3=$(blkid | grep -i 29D1-493A)
rm /m &> /dev/null
if [ "$SDA3" != "" ];
then
   mount /media/HD-PORTATIL-M &> /dev/null
   ln -s /media/HD-PORTATIL-M /m
else
   ln -s /mnt/m /m
   echo cuidado no estás usando la unidad m del portátil
fi
         
