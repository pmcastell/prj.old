uso() {
   echo $0 dir1 dir2
   echo compara si los ficheros contenidos en las carpetas tienen el mismo hash md5
   exit 1
}   

if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi
DIFERENCIAS=0
for i in $(find $1);
do
   FICHERO=$(basename $i)
   DIR
   echo chequeando $FICHERO
   if [ -f $2/$FICHERO ];
   then
      md51=$(md5sum $1/$FICHERO)
      md52=$(md5sum $2/$FICHERO)
      if [ "$md51" != "$md52" ];
      then
         Ficheros diferentes: $1/$FICHERO '!=' $2/$FICHERO
         DIFERENCIAS=1
      fi
   else
      echo Fichero inexistente $2/$FICHERO
      DIFERENCIAS=1
   fi
done   
if [ "$DIFERENCIAS" = "1" ];
then
   echo se han encontrado diferencias
fi   
