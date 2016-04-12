if [ "$1" = "" -o "$2" = "" ];
then
   echo uso $0  '<fichero-de-donde-quitarla-linea> <linea-a-quitar>'
   echo s1: $1 s2: $2
   exit 1
fi
FICHERO=$1
shift
LINEA=$1
shift
while [ "$1" != "" ];
do
   LINEA=$LINEA' '$1
   shift
   echo LINEA: $LINEA
done   
LINEA=$LINEA
cat $FICHERO | grep -v "$LINEA" > /tmp/fichero.tmp.tmp
cat /tmp/fichero.tmp.tmp > $FICHERO 

   
