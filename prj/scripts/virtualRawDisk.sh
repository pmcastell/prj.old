uso() {
   echo uso: $0 '<fichero> <tamaño-en-GIGAS> [<path-grub4dos>]'
   exit 1
}
#cuidado con el tamaño máximo de fichero en fs fat que es 4GB-1=4294967296-1=4294967295
#mejor poner un tamaño de disco máximo de 4GB-1MB=4294967296-1048576=4293918720
if [ $# -lt 2 ]; then uso; fi
FICH_DISCO=$1
TAM=$2
PATH_GRUB=$3
MONTAJE=/dev/loop0
fallocate -l ${TAM} $FICH_DISCO
sudo losetup $MONTAJE $FICH_DISCO
sudo parted --script $MONTAJE mktable msdos mkpart primary 2048s 100%
sudo partprobe $MONTAJE
sudo mkfs.msdos -F 32 /dev/loop0p1
sudo /$PATH_GRUB/bootlace.com --time-out=0 /dev/loop0
MONTAJE_TEMP=/tmp/$(uuid)
mkdir $MONTAJE_TEMP
sudo mount /dev/loop0p1 $MONTAJE_TEMP
sudo cp /$PATH_GRUB/grldr $MONTAJE_TEMP
sudo cp -ruv /tmp/sysrcd $MONTAJE_TEMP
sudo mkdir -p $MONTAJE_TEMP/fsImages/lliurex15.04
sudo cp -ruv /net/CopiasSeg/pc102/ $MONTAJE_TEMP/fsImages/lliurex15.04/
sudo cp -ruv /home/franav/aula/imagenLliurex/ $MONTAJE_TEMP
