for indice in indice4 indice3 indice2; do
   TEMP=$(tempfile)
   cat ${indice}.html | encripta > $TEMP
   echo wput -u $TEMP ftp://u964077031.ganimedes:basura68@ftp.ganimedes.esy.es/${indice}.html
   wput -u $TEMP ftp://u296781278.reg6543:basura68@ftp.iesninfo.esy.es/${indice}.html
   rm $TEMP
done
cp index.php.ganimedes index.php
wput -u tunel* index.php indice.html ftp://u964077031.ganimedes:basura68@ftp.ganimedes.esy.es/
cp index.php.iesninfo index.php
wput -u tunel* index.php indice.html ftp://u296781278.reg6543:basura68@ftp.iesninfo.esy.es/
   


