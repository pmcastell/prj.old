uso() {
   echo uso: $0 '<directorio> <permisos>'
   echo Ejemplo: $0 /tmp/mio/pepe +r,+x
   exit 1
}   
if [ "$1" = "" -o "$2" = "" ]; then uso; fi
f=$1
while [[ $f != "/" ]]; do sudo chmod $2 $f; f=$(dirname $f); done;
