if [ "$1" = "" ];
then
   echo uso $0 fichero-a-subir-a-uploadstation
fi
DESTINO=$2
wput $1 ftp://reg6543:3af5aab0@ftp.uploadstation.com/$DESTINO


