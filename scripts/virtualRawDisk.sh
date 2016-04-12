uso() {
   echo uso: $0 '<fichero> <tamaño-en-GIGAS>'
   exit 1
}
#cuidado con el tamaño máximo de fichero en fs fat que es 4GB-1=4294967296-1=4294967295
#mejor poner un tamaño de disco máximo de 4GB-1MB=4294967296-1048576=4293918720
if [ $# -lt 2 ]; then uso; fi
FICH_DISCO=$1
TAM=$2
MONTAJE=/dev/loop0
fallocate -l ${TAM}G $FICH_DISCO
losetup $MONTAJE $FICH_DISCO
sudo parted --script $MONTAJE mktable msdos mkpart primary 2048s 100%
sudo partprobe $MONTAJE
sudo mkfs -t fat32 /dev/loop0p1
sudo /m/grub4dos-0.4.4/bootlace.com --time-out=0 /dev/loop0
MONTAJE_TEMP=/tmp/$(uuid)
mkdir $MONTAJE_TEMP
sudo mount /dev/loop0p1 $MONTAJE_TEMP
cp /m/grub4dos-0.4.4/grldr $MONTAJE_TEMP



