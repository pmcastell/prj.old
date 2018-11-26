#fsarchivers restfs ubunt-sda5.fsa id=0,dest=/dev/sda5
uso() {
   echo uso: $0 archivo partición-a-guardar
   exit 1
}
[  "$2" = "" ] && uso
CPUS=$(lscpu | grep 'CPU(s):' | grep -v NUMA | grep -v grep | awk '{print $2;}')
#sudo fsarchiver -v -A -a -z 9 -s 640 -j $CPUS -c - savefs /c/COPIAS/ubuntu-sda5/ubuntu-sda5.fsa /dev/sda5
sudo fsarchiver -x -v -A -a -z 9 -s 640 -j $CPUS savefs $1 $2
UNIDAD=$2
UNIDAD=${UNIDAD:0:8}
echo hdparm -f $2
sudo hdparm -f $2
echo hdparm -f $UNIDAD
sudo hdparm -f $UNIDAD
echo sync
sudo sync
