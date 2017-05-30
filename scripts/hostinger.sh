#!/bin/bash
. /scripts/uso.sh



usuario() {
[ "$2" = "" ] && CAMPO="1" || CAMPO="$2"
echo '
u301616277  ftp.inkscape.esy.es  (reg6543@gmail.com)
u358816555  ftp.limpress.esy.es  (reg6543@gmail.com)
u448936479  ftp.lcalc.esy.es     (reg6543@gmail.com)
u121767690  ftp.lwriter.esy.es   (reg6543@gmail.com)
u647043786  ftp.scratch.hol.es   (infosmr2@gmail.com)
u964077031  ftp.ganimedes.esy.es (reg65432@gmail.com)
' | grep $1 | awk '{print $'${CAMPO}';}'
}
[ "$1" = "-h" ] || [ "$1" = "" ] && uso "Uso: $0 <cuenta> [<fichero-a-subir>]"
[ ! -f ~/.lftprc ] && echo "set ssl:verify-certificate false" > ~/.lftprc
[ "$(usuario $1)" == "" ] && Usuario inexistente && exit 2
USUARIO=$(usuario $1)
CLAVE=$(/scripts/getMyPass.sh hostinger)
LFTP='lftp -e "'"set ssl:verify-certificate no;"'" '
[ "$2" = "" ] && eval $LFTP $USUARIO:$CLAVE@$(usuario $1 2) || eval "$LFTP $USUARIO:$CLAVE@$(usuario "$1" 2) -e \"put "$2";quit\""

exit 0

echo "
wget -c -r inkscape.esy.es
wget -c -r scratch.hol.es
wget -c -r lcalc.esy.es
wget -c -r lwriter.esy.es
wget -c -r limpress.esy.es
"