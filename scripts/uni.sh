uso() {
   echo $0 'origen(force) destino'
   exit 1
}
if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi      
#unison -perms=0  -fastcheck -auto -force /l/PROGRAMS /l/PROGRAMS /media/DANELEC-8GB/PROGRAMS
eecho unison -perms=0  -batch -fastcheck -auto -force $1 $1 $2
sync
