uso() {
   echo uso: $0 '<fich-video>'
   exit 1
}   
 if [ "$1" = "" ];
 then
    uso
fi      
mplayer -endpos 0 $*
