uso() {
   echo $0 '<timepo-de-espera-en-segundos> <mensaje> [<repeticiones>]'
   exit 1
}
if [ "$1" = "" -o "$2" = "" ];
then 
   uso
fi
TIEMPO=$1
shift
MENSAJE=$1
shift
sleep $TIEMPO
if [ "$1" != "" ];
then
   REPETICIONES=$1
   shift
   while [ $REPETICIONES -gt 0 ];
   do
      habla -n $MENSAJE &> /dev/null
      REPETICIONES=$(expr $REPETICIONES - 1)
   done
else   

   while [ 1 ];
   do
     habla -n $MENSAJE &> /dev/null
     sleep 1
   done
fi       
