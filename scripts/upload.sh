if [ "$1" = "" ];
then
    echo uso $0 uploadstation|fileserve ficheros-y-o-carpetas-a-subir
    exit 1
fi
DESTINO=$3
if [ "$1" = "uploadstation" ];
then
   echo transfiriendo a ftp.uploadstation.com
   wput $2 ftp://reg6543:3af5aab0@ftp.uploadstation.com/$DESTINO
elif [ "$1" = "fileserve" ];
then
   echo transfiriendo a ftp.fileserve.com
   wput $2 ftp://reg6543:85c6f42c@ftp.fileserve.com/$DESTINO
elif [ "$1" = "hotfile" ];
then
   echo transfiriendo a ftp.hotfile.com
   wput $2 ftp://reg6543:basura@ftp.hotfile.com/$DESTINO
fi
    
