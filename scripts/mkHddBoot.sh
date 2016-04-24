dd if=/dev/zero of=winxpLinux.usb count=5723 bs=1048576
sudo losetup /dev/loop0 winxpLinux.usb
sudo parted /dev/loop0 --script -- mklabel msdos
sudo parted /dev/loop0 --script -- mkpart primary fat32 1 100%
sudo mount -o loop /l/ImagenesCdDvd/winXpLinux.iso /f
sudo sudo mount /dev/loop0p1 /g
cd /f
sudo cp -ruv . /g
cp -rv /f/grub4dos-0.4.4 /tmp/
chmod +x /tmp/grub4dos-0.4.4/bootlace.com
cd /tmp/grub4dos-0.4.4
sudo sync
sudo mv /g/I386 /g/MiniNT
sudo umount /g
sudo umount /f
sudo ./bootlace.com --time-out=0 /dev/loop0
sudo losetup  -d /dev/loop0
kvm -hda winxpLinux.usb -m 1024
