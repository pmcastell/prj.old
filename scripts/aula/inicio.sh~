if [ "$(/usr/bin/whoami)" != "root" ];
then
   /usr/bin/sudo $0 $*
   exit
fi
#comprobar si /net está montado
while [ "$(/bin/mount | /bin/grep '/dev/sdb1 on /net type ext4 (rw,acl)')" = "" ];
then
   /sbin/fsck -fyC /dev/sdb1
   /bin/mount /net
fi
    
#
