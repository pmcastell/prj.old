uso() {
   echo uso: $0 '<fichero> <tamaño-en-GIGAS> [<path-grub4dos>]'
   exit 1
}
#cuidado con el tamaño máximo de fichero en fs fat que es 4GB-1=4294967296-1=4294967295
#mejor poner un tamaño de disco máximo de 4GB-1MB=4294967296-1048576=4293918720
#if [ $# -lt 2 ]; then uso; fi
[ "$1" != "" ] && FICH_DISCO="$1" || FICH_DISCO=lliurex-15.04-k4-imagen.raw
[ "$2" != "" ] && TAM="$2" || TAM=8200MB
[ "$3" != "" ] && DIR_BASE="$3" || DIR_BASE="/home/lliurex/imagenes/Arranque"

MONTAJE=/dev/loop0
fallocate -l ${TAM} $FICH_DISCO
sudo losetup $MONTAJE $FICH_DISCO
sudo parted --script $MONTAJE mktable msdos mkpart primary 2048s 100%
sudo partprobe $MONTAJE
sudo mkfs.msdos -F 32 ${MONTAJE}p1
sudo $DIR_BASE/grub4dos-0.4.4/bootlace.com --time-out=0 /dev/loop0
MONTAJE_TEMP=/tmp/$(uuid)
mkdir $MONTAJE_TEMP
sudo mount /dev/loop0p1 $MONTAJE_TEMP

sudo cp grub4dos-0.4.4/grldr $MONTAJE_TEMP
sudo cp autorun* $MONTAJE_TEMP
sudo cp menu.lst $MONTAJE_TEMP
sudo cp listaFicherosArranqueImagenes.txt $MONTAJE_TEMP
sudo touch $MONTAJE_TEMP/B127-A93F
sudo mkdir -p $MONTAJE_TEMP/distros
sudo cp -ruv $DIR_BASE/distros/sysrcd $MONTAJE_TEMP/distros/
sudo mkdir -p $MONTAJE_TEMP/fsImages/lliurex15.04
sudo mkdir -p $MONTAJE_TEMP/fsImages/lliurex15.04-kernel4
sudo cp -ruv grub4dos-0.4.4 $MONTAJE_TEMP/
sudo cp -ruv $DIR_BASE/fsImages/lliurex15.04/ $MONTAJE_TEMP/fsImages/
sudo cp -ruv $DIR_BASE/fsImages/lliurex15.04-kernel4/ $MONTAJE_TEMP/fsImages/
sudo umount $MONTAJE_TEMP
sudo rmdir $MONTAJE_TEMP
sudo losetup -d $MONTAJE

