eecho mount /cdrom
TAM=$(df -B 1 | grep -i cdrom | awk '{ print $3;}')
COUNT=$(expr $TAM \/ 2048)
echo Tamaño Total: $TAM, Bloques: $COUNT
eecho umount /cdrom
echo 'dd if=/dev/sr0 bs=2048 count='$COUNT' | md5'
bash -c 'dd if=/dev/sr0 bs=2048 count='$COUNT' | md5' &
PID=1
while [ "$PID" != "" ];
do
   sleep 30
   PID=$(ps ax | grep -v grep | grep -v bash | grep -i 'dd if=/dev/sr0 bs=2048' | awk '{print $1;}')
   if [ "$PID" != "" ];
   then
      kill -USR1 $PID
   fi
done   
