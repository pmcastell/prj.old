TAM2=$(ls -l /media/Iomega_HDD/COPIAS/FreeBSD/FreeBSD.vmdk | awk '{print $5;}')
sleep 30;
I=1
TAM=$(ls -l /media/Iomega_HDD/COPIAS/FreeBSD/FreeBSD.vmdk | awk '{print $5;}')
while [ "$TAM2" != "$TAM" ];
do
   sleep 30;
   TAM2=$TAM
   TAM=$(ls -l /media/Iomega_HDD/COPIAS/FreeBSD/FreeBSD.vmdk | awk '{print $5;}')
   echo TAM: $TAM , TAM2: $TAM2
   I=$(expr $I + 1)
   echo Repeticion $I
done
echo date > /m/horaTerminacionModi.txt
sleep 1
poweroff   
