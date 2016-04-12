uso() {
   printf "\n"
   echo uso: $0 '<fichero> <dispositivo>'
   echo muestro información de la fecha de creación del fichero y otros
   printf "\n"
   exit
}
if [ "$1" = "" -o "$2" = "" ]; then uso; fi
INODE=$(ls -i $1|awk '{print $1;}')
sudo debugfs -R "stat <${INODE}>" $2
