if [ "$1" = "" ];
then
   echo indica la direccion I.P. del servidor de la unidad m
   exit 1
   IP=192.168.1.193
else
   IP=$1
fi      
OS=$(uname)
if [ "$OS" = "Linux" ];
then
   sudo mount.cifs //$IP/m /m -o user=usuario
else 
   sudo mount_smbfs -U usuario -I $IP //$IP/m /mnt/m
fi

