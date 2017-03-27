uso() {
   echo uso: $0 '<hostinger-site>' '[<fich-1> ... <fich-n>]'
   exit 1
}
DIR_BASE="/scripts";
. $DIR_BASE/debug.sh;
DEBUG=true;
if [ $# -lt 1 ]; then uso; fi
DIR="/m/Mios/Instituto/ESO-BACHILLERATO/IAPUnidades"

CLAVE="$($DIR_BASE/getMyPass.sh generica)"; debug CLAVE: $CLAVE
SITIO="$1"; shift; debug SITIO: $SITIO; debug SITIO: $SITIO
for f in u121767690-lwriter-VideoTutorialWriter u448936479-lcalc-VideoTutorialCalc uxxxxxxxxx-limpress-VideoTutorialImpress u996597741-inkscape-VideoTutorialInkscape u839961142-scratch-VideoTutorialScratch; do
   FTP=$(echo $f|awk -F'-' '{print $2;}'); debug FTP: $FTP;
   if [ "$FTP" = "$SITIO" ]; then
      USUARIO=$(echo $f|awk -F'-' '{print $1;}'); SUBDIR=$(echo $f|awk -F'-' '{print $3;}'); debug USUARIO: $USUARIO
      cd $DIR/$SUBDIR;
      if [ "$1" = "" ]; then wput * ftp://$USUARIO:$CLAVE@ftp.$FTP.esy.es/; debug Subimos todo
      else while [ "$1" != "" ]; do wput -u "$1" ftp://$USUARIO:$CLAVE@ftp.$FTP.esy.es/; debug "\$1: $1";shift;  done; fi
   fi
done;   
      
