uso() {
   echo uso: $0 file.img file.cue file.iso
   exit 1
}
if [ "$1" = "" ];
then
   uso
fi      
bchunck $1.img $1.cue $1.iso
