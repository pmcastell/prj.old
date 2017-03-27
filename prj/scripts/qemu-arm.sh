qemu-system-arm -kernel kernel-qemu -cpu arm1176 -m 256 -M versatilepb -no-reboot -serial stdio -append "root=/dev/sda2 panic=1 rootfstype=ext4 rw init=/bin/bash" -hda 2013-09-25-wheezy-raspbian.img


qemu-arm -cpu arm1176 -m 512 -M versatilepb -no-reboot -serial stdio -hda 2014-09-09-wheezy-raspbian.img
