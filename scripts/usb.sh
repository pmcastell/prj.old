if [ "$(mount | grep "/dev/sdc1")" != "" ];
then
   umount /c
else   
   mount /c &>/dev/null 
   nautilus /c 
fi   
