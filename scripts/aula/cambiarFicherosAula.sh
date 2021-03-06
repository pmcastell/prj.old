#!/bin/bash
uso() {
   echo $0 '[<usuario>|<fichero-usuarios>]' '<dir-ip-origen>'
   exit 1
}

[ $# -lt 2 ] &&  uso

SMB_MOUNT=$(tempfile)
rm $SMB_MOUNT
mkdir $SMB_MOUNT
if [ -f $1 ]; then ENTRADA="$1"; else ENTRADA=$(tempfile); echo $1 > $ENTRADA; fi
sudo mount.cifs //$2/net $SMB_MOUNT -o user=$USER
while read USUARIO; do
   sudo cp -ruv $SMB_MOUNT/server-sync/home/students/$USUARIO/Desktop /net/server-sync/home/students/$USUARIO/
   sudo chown -R $USUARIO:nogroup /net/server-sync/home/students/$USUARIO 
done < $ENTRADA
ls -l $SMB_MOUNT
sudo umount $SMB_MOUNT
rmdir $SMB_MOUNT
