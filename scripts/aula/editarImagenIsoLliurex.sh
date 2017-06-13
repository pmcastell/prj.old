#extraer initrd.lz
lupin_helpers() {
echo '. /scripts/casper-helpers

is_supported_fs(){
    [ -z "${1}" ] && return 1
    case ${1} in
        ext2|ext3|ext4|xfs|jfs|reiserfs|vfat|ntfs|iso9660|btrfs)
            return 0
            ;;
    esac
    return 1
}

wait_for_devs(){
    if [ -e /dev/.initramfs/lupin-waited-for-devs ]; then
        return
    fi
    [ "$quiet" != "y" ] && log_begin_msg "...waiting for devs..."
    udevadm trigger --subsystem-match=block
    udevadm settle
    #TBD, modprobe on demand?
    modprobe ext3
    modprobe ext4
    modprobe reiserfs
    modprobe xfs
    modprobe jfs
    modprobe vfat
    modprobe fuse
    [ "$quiet" != "y" ] && log_end_msg "...devs loaded..."
    touch /dev/.initramfs/lupin-waited-for-devs
}

find_path()
{
    local path="${1}"
    # must match find_path_cleanup
    local default_mountpoint="${2:-/tmpmountpoint}"
    local mountoptions="${3:-ro}"
    local mountpoint=
    local dev devname devfstype
    local trial_number
    FOUNDDEV=
    FOUNDPATH=
    [ -z "${path}" ] && return 1
    wait_for_devs
    mkdir -p "${default_mountpoint}"
    for trial_number in 1 2 3; do
        [ $trial_number -gt 1 ] && sleep 3
        for sysblock in $(echo /sys/block/* | tr '"'"' '"'"' '"'"'\n'"'"' | grep -v /ram | grep -v /loop | grep -v /fd); do
            for dev in $(subdevices "${sysblock}"); do
                devname=$(sys2dev "${dev}")
                devfstype="$(get_fstype ${devname})"
                if is_supported_fs "${devfstype}" ; then
                                    #if device is already mounted, do not remount
                    if grep -q "^${devname} " /proc/mounts; then
                        mountpoint=$(grep "^${devname} " /proc/mounts|cut -d '"'"' '"'"' -f 2)
                        unmount=false                
                    else                
                        mountpoint="${default_mountpoint}"                    
                        try_mount "$devname" "$mountpoint" "$mountoptions" || continue
                        unmount=true    		    
                    fi                
                    if [ -e "${mountpoint}${path}" ]; then
                        FOUNDDEV="${devname}"
                        FOUNDPATH="${mountpoint}${path}"
                        return 0
                    fi
                    [ "${unmount}" = "true" ] && umount ${mountpoint} 2> /dev/null || true
                fi
            done
        done
    done
    return 1
}

find_path_cleanup()
{
    # must match find_path
    local default_mountpoint="${1:-/tmpmountpoint}"
    if grep -q "^[^ ]* $default_mountpoint " /proc/mounts; then
        umount "$default_mountpoint" 2>/dev/null || true
    fi
    rmdir "$default_mountpoint" 2>/dev/null || true
}' > ./scripts/lupin-helpers
chmod a+x ./scripts/lupin-helpers
}




iso_scan() {
echo '#!/bin/sh

PREREQ=""

prereqs()
{
       echo "$PREREQ"
}

case $1 in
# get pre-requisites
    prereqs)
           prereqs
           exit 0
           ;;
esac

. /scripts/casper-functions
. /scripts/lupin-helpers

iso_path=
for x in $(cat /proc/cmdline); do
    case ${x} in
        iso-scan/filename=*)
            iso_path=${x#iso-scan/filename=}
            ;;
    esac
done
if [ "$iso_path" ]; then
    if find_path "${iso_path}" /isodevice rw; then
        echo "LIVEMEDIA=${FOUNDPATH}" >> /conf/param.conf
        if [ -f "${FOUNDPATH}" ]; then
            echo "LIVEMEDIA_OFFSET=0" >> /conf/param.conf
        fi
    else
        panic "
Could not find the ISO $iso_path
This could also happen if the file system is not clean because of an operating
system crash, an interrupted boot process, an improper shutdown, or unplugging
of a removable device without first unmounting or ejecting it.  To fix this,
simply reboot into Windows, let it fully start, log in, run '\'chkdsk /r\'', then
gracefully shut down and reboot back into Windows. After this you should be
able to reboot again and resume the installation.
"
    fi
fi' > ./scripts/casper-premount/20iso_scan
chmod a+x ./scripts/casper-premount/20iso_scan
}
ORDER() {
echo '/scripts/casper-premount/10driver_updates "$@"
[ -e /conf/param.conf ] && . /conf/param.conf
/scripts/casper-premount/20iso_scan "$@"
[ -e /conf/param.conf ] && . /conf/param.conf
' > scripts/casper-premount/ORDER
chmod a+x scripts/casper-premount/ORDER
}

. /scripts/funcionesAux.sh
[ "$1" = "" ] && uso $0 '<imagen-iso>' || IMG_ISO="$1"
MONTAJE="/tmp/$(uuid)"
mkdir $MONTAJE
sudo mount -o loop $IMG_ISO /$MONTAJE
mkdir /tmp/iso
cp -av $MONTAJE/. /tmp/iso
chmod a+w /tmp/iso
mkdir /tmp/isotemp
cd /tmp/isotemp
7za e -so ../iso/casper/initrd.lz | cpio -id
lupin_helpers
iso_scan
ORDER
sudo bash -c 'find . | cpio -o -H newc | lzma -7 > /tmp/iso/casper/initrd.lz'
cd /tmp/iso
sudo mkisofs -R -b isolinux/isolinux.bin -c isolinux/boot.cat -no-emul-boot -boot-load-size 4 -boot-info-table -o /tmp/isotemp/$(basename $IMG_ISO) /tmp/iso
sudo umount /$MONTAJE

exit 0

The "-b" flag makes the disk bootable, using "isolinux/isolinux.bin" as the boot file. The "-no-emul-boot", "-boot-load-size 4", and "-boot-info-table" all are extra settings for making the disk bootable. The "-c isolinux/boot.cat" is a packaging flag. The "-o /tmp/new.iso" is the file it will generate, and the last thing "/tmp/iso" specifies the files to put inside it. The "-R" makes sure the final ISO file is fully compliant.


