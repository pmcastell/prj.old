#!/bin/bash
. /scripts/uso.sh

hostingerMain() {
    [ "$1" = "-h" ] || [ "$1" = "" ] && uso "Uso: $0 <cuenta> [<fichero-a-subir>]"
    [ ! -f ~/.lftprc ] && echo "set ssl:verify-certificate false" > ~/.lftprc
    [ "$(usuario $1)" == "" ] && Usuario inexistente && exit 2
    USUARIO=$(usuario $1 1)
    CLAVE=$(/scripts/getMyPass.sh hostinger)
    [ "$CLAVE" = "" ] && CLAVE="$(/scripts/getMyPass.sh hostinger)"
    LFTP='lftp -e "'"set ssl:verify-certificate no;"'" '
    if [ "$2" = "" ]; then
       eval $LFTP $USUARIO:$CLAVE@$(usuario $1 2) 
    else 
       SITIO=$(usuario "$1" 2)
       if [ "$(echo $SITIO | grep -i webhost)" != "" ]; then
          eval "$LFTP $USUARIO:$CLAVE@$(usuario "$1" 2) -e \"put "$2";mv "$(basename $2)" public_html/;quit\""
       elif [ "$(echo $SITIO | grep -i atwebpages)" != "" ]; then
          eval "$LFTP $USUARIO:$CLAVE@$(usuario "$1" 2) -e \"cd ganimedes.atwebpages.com; put "$2";quit\""
       else
          eval "$LFTP $USUARIO:$CLAVE@$(usuario "$1" 2) -e \"put "$2";quit\""
       fi
    fi   
}
usuario() {
    [ "$2" = "" ] && CAMPO="2" || CAMPO="$2"
    echo '
u301616277  ftp.inkscape.esy.es      /public_html              http://inkscape.esy.es/ (reg6543@gmail.com)
u358816555  ftp.limpress.esy.es      /public_html              http://limpress.esy.es/ (reg6543@gmail.com)
u448936479  ftp.lcalc.esy.es         /public_html              http://lcalc.esy.es/ (reg6543@gmail.com)
u121767690  ftp.lwriter.esy.es       /public_html              http://lwriter.esy.es/ (reg6543@gmail.com)
u964077031  ftp.ganimedes.esy.es     /public_html              http://ganimedes.esy.es (reg65432@gmail.com)    
ganimedes   files.000webhost.com     /public_html              http://ganimedes.000webhostapp.com (correo6543@gmail.com)  ###
u647043786  ftp.scratch.hol.es       /public_html              http://scratch.hol.es/ (infosmr2@gmail.com) 
u588702550  ftp.javi-moodle.esy.es   /public_html              http://javi-moodle.esy.es (correo6543@gmail.com)  
javi-moodle files.000webhost.com     /public_html              http://javi-moodle.000webhostapp.com/ (reg65432@gmail.com)    ###
2413052     ganimedes.atwebpages.com /ganimedes.atwebpages.com http://ganimedes.atwebpages.com/ (reg6543@gmail.com)     ###
revista-el-ventana-ies-puertas-del-campo files.000webhost.com /public_html http://revista-el-ventana-ies-puertas-del-campo.000webhostapp.com (web@iespuertasdelcampo.es)
' | grep $1 | head -1 | awk '{print $'${CAMPO}';}'
}
(return 2>/dev/null) || hostingerMain "$@"
###exit 0

###echo "
###wget -c -r inkscape.esy.es
###wget -c -r scratch.hol.es
###wget -c -r lcalc.esy.es
###wget -c -r lwriter.esy.es
###wget -c -r limpress.esy.es
###"


#lftp -c 'open -e "mirror /tmp/thedir ftp://nico:mypass@remotehost/~/destination/" ftp://nico:mypass@localhost'
