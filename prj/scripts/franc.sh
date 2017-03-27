uso() {
   echo Uso: $0 '<ficheros-a-copiar> <destino>'
   exit 1
}   
if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi   
sshpass -p galileo scpresume $1 franc@192.168.1.9:./$2
