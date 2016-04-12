#uso() {
#   echo $0 '<-buscado> <donde-buscar>'
#   exit 1
#}
#if [ "$1" = "" -o "$2" = "" ];
#then
#   uso
#fi    
BUSCADO=$1
shift
#echo -n | grep -n fallaba porque el echo lo toma como un parámetro
###APARECE=$(echo "abcdhfjdldfjhlkdf $*" | grep "\\$BUSCADO")
###if [ "$APARECE" != "" ];
###then
###   echo $* | awk -F"$BUSCADO" '{ print $2;}' | awk '{ print $1;}'
###fi   
while [ "$1" != "$BUSCADO" -a "$1" != "" ]; do
   shift
done
RES=""
if [ "$1" = "$BUSCADO" ];
then
   while [ 1 ]; do 
      shift
      if [ "$1" = "" ]; then break; fi
      P=${1:0:1}
      if [ "$P" = "-" ]; then break; fi
      RES="$RES ""$1"
   done
fi      
echo $RES
