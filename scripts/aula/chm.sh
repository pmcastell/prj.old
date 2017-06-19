uso() {
   echo uso: $0 '<directorio> <permisos>'
   echo Cambia los permisos a un directorio y a todos sus ancestros hasta llegar al raíz
   echo Ejemplo: $0 /tmp/mio/pepe a+rx
   exit 1
}   
if [ "$1" = "" -o "$2" = "" ]; then uso; fi
f=$1
while [[ $f != "/" ]]; do sudo chmod $2 $f; f=$(dirname $f); done;
