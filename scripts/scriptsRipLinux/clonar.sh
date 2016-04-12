if [ $# != 1 ];
then
   echo Uso: $0 nombre
   exit -1
fi
if [ -d /imagenes/$1 ];
then
   echo Imagen ya existente
   echo si deseas reemplazarla
   echo debes borrarla manualmente
   echo pulsa cualquier tecla para continuar...
   read
   exit -1
fi
mkdir /imagenes/$1
discos=$(for f in $(fdisk -l | grep -E "^/dev" | awk '{print $1;'}); do echo ${f:5:3}; done | sort | uniq)
for disk in $discos;
do
   #Salva MBR
   dd if=/dev/$disk of=/imagenes/$1/$disk.mbr bs=512 count=1
   #Salva Info particiones extendidas
   sfdisk -d /dev/$disk > /imagenes/$1/$disk.ext.part
   #Salva imagenes de cada una de las particiones
   for part in $(fdisk -l | grep -E "^/dev/$disk" | awk '{print $1;}');
   do
      echo Haciendo imagen de $part
      #partimage -z1->compresión gzip -b->batch mode -c->don't check partition -f3->quit -d->no pedir descripción -V700 partir en trozos de 700MB
      partimage -z1 -b -c -f3 -d -V700 save /dev/${part:5} /imagenes/$1/${part:5}.gz
   done
done

