#!/bin/bash
uso() {
   echo $0 '<fichero-usuarios>' '<dir-ip-origen>'
   exit 1
}

if [ $# -lt 2 ]; then
   uso
fi
TEMP=$(tempfile)
rm $TEMP
mkdir $TEMP
sudo mount.cifs //$2/net $TEMP -o user=franav
while read USUARIO; do
   sudo cp -ruv $TEMP/server-sync/home/students/$USUARIO /net/server-sync/home/students/
   sudo chown -R $USUARIO:nogroup /net/server-sync/home/students/$USUARIO 
done < $1
sudo umount $TEMP
rmdir $TEMP
