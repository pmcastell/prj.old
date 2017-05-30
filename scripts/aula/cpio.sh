#!/bin/bash
TEMP=$(tempfile)
rm $TEMP
mkdir $TEMP
sshfs root@10.2.1.253:/TMP $TEMP
cd /net/server-sync/home
(find students/ | cpio -ov -H newc | gzip -9 > $TEMP/students.gz ) &
(find teachers/ | cpio -ov -H newc | gzip -9 > $TEMP/teachers.gz ) 
ls -la $TEMP
echo pulsa una tecla para desmontar y terminar
read
fusermount -u $TEMP
rmdir $TEMP
