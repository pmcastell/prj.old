#!/bin/bash
ftpPut() {
   CLAVE=$(cat ftpClave.txt | desencripta)
   for f in $1; do wput -B -u "$f" "ftp://$2:$CLAVE@$3"; done
}  
encFtpPut() {
   for f in "$1"; do
      TEMP=$(tempfile)
      #cat ${1} | encripta > $TEMP
      eecho openssl enc -e -aes-256-ctr -k "clave$(date -u +'%Y-%m-%d')"  -in $f -out $TEMP
      INTENTOS=0
      while [ $INTENTOS -lt 5 ]; do
         ftpPut "$TEMP" $2 $3
         if [ $? = 0 ]; then break; fi
      done
      rm $TEMP
   done
}   
    
LISTA="indice5 indice4 indice3 indice2"
LISTA="indice5 indice"
LISTA="indice6 indice5"
for INDICE in $LISTA; do
   encFtpPut "${INDICE}.html" u964077031.ganimedes "ftp.ganimedes.esy.es/${INDICE}.html"
   #encFtpPut "${INDICE}.html" u296781278.reg6543   "ftp.iesninfo.esy.es/${INDICES}.html"
done   
#cp index.php.ganimedes index.php
#ftpPut "tunel* index.php indice.html" u964077031.ganimedes ftp.ganimedes.esy.es


