uso() {
   echo uso: $0 '<fichero-avi> [etiqueta (por defecto XVID)]'
   exit -1
}
if [ "$1" = "" ];
then
   uso
fi
if [ "$2" = "" ];
then
   TAG=XVID
else
   TAG=$2
fi      
echo $TAG | dd conv=notrunc bs=1 seek=112 of=$1
echo $TAG | dd conv=notrunc bs=1 seek=188 of=$1      

