if [ "$1" = "" ];
then
   echo uso $0 fichero-a-subir-a-uploadstation
fi
DESTINO=$2
wput $1 ftp://reg6543:85c6f42c@ftp.fileserve.com/$DESTINO


