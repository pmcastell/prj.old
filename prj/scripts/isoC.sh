uso() {
   echo Uso: $0 directorio nombre-fichero 
   echo Hace una imagen nombre-fichero.iso de directorio
   exit -1
}   
if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi   
nice -19 mkisofs -f -iso-level 4 -R -o $2 $1
