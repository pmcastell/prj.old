LISTA="/tmp/listaFicheros.txt"
SALIDA="/tmp/listaFicherosmd5.txt"
while read FICH; do
   sudo md5sum "$FICH" >> $SALIDA
done < $LISTA
