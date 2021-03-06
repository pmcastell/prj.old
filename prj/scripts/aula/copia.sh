TEMP="/tmp/listaFicheros.txt"
DESTINO_COPIA_TEMP="/tmp/trabajos"
DIR_DEST="/home/$USER/Escritorio/TRABAJOS_ALUMNOS"
sudo rm -rf $TEMP
sudo rm -rf $DESTINO_COPIA_TEMP
mkdir -p $DESTINO_COPIA_TEMP
EXTENSIONES="\.ods$|\.xls$|\.xlsx$|\.svg$"
sudo find /net/server-sync/home/students/ | grep -iE "(${EXTENSIONES})" > $TEMP
echo Lista de ficheros guardado en $TEMP
while read f ; do
   sudo cp -v --parents "$f" $DESTINO_COPIA_TEMP
done < $TEMP
sudo chown -R franav $DESTINO_COPIA_TEMP
mkdir -p $DIR_DEST &>/dev/null
7za a -r $DIR_DEST/trabajos$(date +"%Y-%m-%d").7z $DESTINO_COPIA_TEMP 
#sudo chown franav ./trabajos.7z
##rm $TEMP
