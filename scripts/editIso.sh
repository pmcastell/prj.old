cat<<EOF
Edit: Here's what I found. The bad news is that there doesn't seem to be an easy extractor and rewriter like WinISO, but the good news is that it is pretty easy to do this with commands.
Type "mount -o loop /path/to/iso /mnt/iso" (/mnt/iso must be created if it doesn't exist).
cp -a /mnt/iso /tmp/iso
Make all your changes in /tmp/iso.
Use mkisofs to create the modified ISO from the /tmp/iso directory:
mkisofs -R -b isolinux/isolinux.bin -c isolinux/boot.cat -no-emul-boot -boot-load-size 4 -boot-info-table -o <new iso filename> /tmp/iso

I found a solution by myself!

1) Install p7zip-full via synaptic package manager
2) Create a subdirectory to preserve the init subdirectory tree
3) Decompress initrd.lz with "7z e -so ../initrd.lz | cpio -id"

To compress it again go to the init subdirectory and execute:
4) find | cpio -o -H newc > ../newinitrd
5) go up one directory
6) 7z a -m0=lzma:a=1 initrd-mod.lz newinitrda
EOF

