RES=$1
shift
while [ "$1" != "" ];
do
   RES=$RES" "$1
   shift
done
echo $RES    
