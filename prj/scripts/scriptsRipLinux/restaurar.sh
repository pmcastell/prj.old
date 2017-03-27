if [ $# != 1 ];
then 
   echo Uso: $0 nombre
   exit -1
fi
if [ ! -d /imagenes/$1 ];
then
   echo Imagen inexistente: comprueba el nombre
   echo y vuelve a intentarlo
   echo pulsa una tecla para continuar
   read
   exit -1
fi

#discos=$(for f in $(fdisk -l | grep -E "^/dev" | awk '{print $1;'}); do echo ${f:5:3}; done | sort | uniq)
discos=$(for f in $(ls /imagenes/$1); do echo ${f:0:3}; done | sort | uniq)
for disk in $discos;
do
   #restaurar MBR
   dd if=/imagenes/$1/$disk.mbr of=/dev/$disk
   #restaurar info particiones extendidas
   sfdisk  /dev/$disk < /imagenes/$1/$disk.ext.part
   #restaurar imagenes de cada una de las particiones
   for part in $(fdisk -l | grep -E "^/dev/$disk" | awk '{print $1; }');
   do
      echo Restaurando imagen de  $part
	  #partimage -z1->compresión gzip -b->batch mode -c->don't check partition -f3->quit -d->no pedir descripción -V700 partir en trozos de 700MB
      partimage -z1 -b -c -f3 -d -V700 restore /dev/${part:5} /imagenes/$1/${part:5}.gz.000
   done
done