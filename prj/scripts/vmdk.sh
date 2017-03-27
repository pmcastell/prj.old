uso() {
   echo uso: $0 '<nombre-fichero> <dispositivo>'
   exit 1
}
if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi
VBoxManage internalcommands createrawvmdk -filename $1.vmdk -rawdisk $2 
#-register
