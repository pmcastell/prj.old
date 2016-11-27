#comprobar si /net esta montado
while [ "$(/bin/mount | /bin/grep '/dev/sdb1 on /net type ext4 (rw,acl)')" = "" ];
do
   /sbin/fsck -fyC /dev/sdb1
   /bin/mount /net
   /bin/date +"%Y-%m-%d" >> /root/fallosMontajeSdb1.txt
done

