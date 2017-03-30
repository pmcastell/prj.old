sgdisk --backup=table /dev/sda
sgdisk --load-backup=table /dev/sdb
#poner uuids aleatorios al disco y a las particiones
sgdisk -G /dev/sdb
