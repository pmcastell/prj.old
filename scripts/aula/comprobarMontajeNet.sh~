#comprobar si /net esta montado
while [ "$(/bin/mount | /bin/grep '/dev/sdb1 on /net type ext4 (rw,acl)')" = "" ];
do
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
   /bin/date +"%Y-%m-%d" >> /root/fallosMontajeSdb1.txt
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
   /sbin/fsck -fyC /dev/sdb1 >> /root/fallosMontajeSdb1.txt
   /bin/mount /net
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
   /bin/date +"%Y-%m-%d" >> /root/fallosMontajeSdb1.txt
   echo -n "-----------------------" >> /root/fallosMontajeSdb1.txt
done

