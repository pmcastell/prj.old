uso() {
   echo Uso: $0 '<origen>'
   exit -1
}
if [ "$1" = "" ];
then
   uso
fi      
SAVEIFS=$IFS
IFS=$(echo -en "\n\b")
#for f in $*
#do
#  echo "$f"
#done
for i in $(find $1);
do
   echo $i
done   
IFS=$SAVEIFS
