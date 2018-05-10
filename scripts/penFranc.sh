NUM_DEVS=$(ls -l /dev/sd*  | awk '{print $NF;}' | grep -vE '[0-9]' | grep -v '[a-c]' | wc -l)
if [ "$NUM_DEVS" = "" -o $NUM_DEVS -lt 1 ];
then
   echo No se ha encontrado ningún dispositivo donde instalar
   echo Introduce un Pendrive y vuele a intentarlo
   exit 1
fi   
DEVS=$(ls -l /dev/sd*  | awk '{print $NF;}' | grep -vE '[0-9]' | grep -vE '[a-c]')
OPCION=0
while [ $OPCION -lt 1 -o $OPCION -gt $NUM_DEVS ];
do
   I=1
   while [ $I -le $NUM_DEVS ];
   do
      echo $I')' $(ls -l /dev/sd*  | awk '{print $NF;}' | grep -vE '[0-9]' | grep -vE '[a-c]' | head -$I | tail -1)
      I=$(($I+1))
   done   
   echo Elige unidad donde instalar:
   read OPCION
done
DESTINO=$(ls -l /dev/sd*  | awk '{print $NF;}' | grep -vE '[0-9]' | grep -vE '[a-c]' | head -$OPCION | tail -1)
echo Has elegido instalar en: 
echo $( sudo fdisk -l $DESTINO | grep -E '[0-9]+ bytes$' | head -1)
echo ¿Está seguro de que desea continuar?
echo Si continua se borrarán todos los datos en $DESTINO
echo ¿Continuar?'(S/N)'
read CONTINUAR
if [ "$CONTINUAR" != "S" ];
then
   echo Proceso terminado a petición del usuario
   exit 1
fi
ORIGEN=/discoNuevo/franc/PenGuadaEdu10.04
MONTAJE=/tmp/GuadaLinexEdu10.04
GRUB4DOS=/discoNuevo/franc/grub4dos-0.4.4
while [ "$(mount | grep $DESTINO)" != "" ];
do
    sudo umount $(mount  | grep $DESTINO  | grep -v grep | awk '{print $1;}' | head -1)
done   
echo Borrando Pendrive
sudo parted $DESTINO --script -- mklabel msdos       
sudo parted $DESTINO --script -- mkpart primary 1 100%
echo 'Formateando Pendrive (Sistema Ficheros fat32)'
#sudo mkfs.ext3 $DESTINO"1"
sudo mkfs.vfat $DESTINO"1"
if [ $? -gt 0 ];
then
   echo Hubo un error formateando el Pendrive
   exit 1
fi   
sudo sync
sudo mkdir $MONTAJE &> /dev/null
sudo mount $DESTINO"1" $MONTAJE
echo Copiando Ficheros:
sudo cp -rv $ORIGEN/* $MONTAJE/
sudo cp -rv $ORIGEN/.* $MONTAJE/
sudo sync
sudo $GRUB4DOS/bootlace.com --time-out=0 $DESTINO
sudo sync
sudo hdparm -f $DESTINO
sudo umount $MONTAJE
echo Proceso terminado. Puede retirar el Pendrive.

