#!/bin/bash

DEV=/dev/sdb
parted $DEV --script -- mklabel msdos
parted $DEV --script -- mkpart primary ext4 1 100%
mkfs.ext4 ${DEV}1
#mkdir /tmp/home
#mv /home/* /tmp/home/
UUID=$(blkid | grep $DEV | awk '{print $2;}' | awk -F'"' '{print $2;}')
echo "UUID=$UUID /home               ext4    errors=remount-ro,usrquota,grpquota 0       2" >> /etc/fstab
mount /home
mv /c/home/* /home/


