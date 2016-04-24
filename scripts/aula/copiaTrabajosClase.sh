uso() {
   echo uso: $0 '<fichero-lista-usuarios-clase> [<extensiones>]'
   exit 1
}
if [ "$1" = "" ]; then uso; fi
CLASE=$1
if [ "$2" = "" ]; then EXTENSIONES="\.ods$|\.xls$|\.xlsx$|\.svg$"; else EXTENSIONES="$2"; fi
TEMP="/tmp/listaFicheros.txt"
DESTINO_COPIA_TEMP="/tmp/trabajos"
DIR_DEST="/home/$USER/Escritorio/TRABAJOS_ALUMNOS"
sudo rm -rf $TEMP
sudo rm -rf $DESTINO_COPIA_TEMP
mkdir -p $DESTINO_COPIA_TEMP
for ALU in $(cat $CLASE); do
   sudo find /net/server-sync/home/students/$ALU | grep -iE "(${EXTENSIONES})" > $TEMP
   while read f ; do
      sudo cp -v --parents "$f" $DESTINO_COPIA_TEMP
   done < $TEMP
done   
sudo chown -R franav $DESTINO_COPIA_TEMP
mkdir -p $DIR_DEST &>/dev/null
7za a -r $DIR_DEST/trabajos$(date +"%Y-%m-%d").7z $DESTINO_COPIA_TEMP 
#sudo chown franav ./trabajos.7z
##rm $TEMP
